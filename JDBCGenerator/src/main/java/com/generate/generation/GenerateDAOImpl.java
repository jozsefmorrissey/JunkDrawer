package com.generate.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Parameter;
import com.generate.parce.bean.Query;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.generate.parce.bean.Wrapper.ParameterWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;

@SuppressWarnings("restriction")
public class GenerateDAOImpl
{
	private JdbcTypeWrapper jdbcType;
	
	public GenerateDAOImpl(JdbcTypeWrapper jdbcType) throws IOException {
		super();
		this.jdbcType = jdbcType;
		buildDAO();
	}
	
	public void buildDAO() throws IOException {
		
		String s = jdbcType.getAbstractNameFQ();
		TypeSpec.Builder typeImplBuilder = TypeSpec.classBuilder(jdbcType.getJdbcName())
				.addModifiers(Modifier.PUBLIC)
			    .superclass(StringUtil.getType(jdbcType.getAbstractNameFQ()))	
				.addAnnotation(GenUtil.getSuppressAnnotation());
		
		List<Query> qs = jdbcType.getQueries();
		
		for(Query q : qs) {			
			addQueryMethod(typeImplBuilder, q);
		}
						
		addBuildMethod(typeImplBuilder);
		
		addConstructor(typeImplBuilder);
		
		typeImplBuilder.addSuperinterface(StringUtil.getType(jdbcType.getDaoNameFQ()));

				
		GenUtil.buildAndSave(jdbcType.getPackage("jdbc"), typeImplBuilder);
	}

	private void addBuildMethod(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("build")
				.addModifiers(Modifier.PRIVATE);
		
		  methodBuilder.addStatement("$T field", Field.class);
		  methodBuilder.addStatement("$T param", Parameter.class);

			  for(Query curr: jdbcType.getQueries()) {
				  methodBuilder.addStatement("this.addQuery($S, $S)", curr.getName(), curr.getFormat());
				  
				  String newFormat = "param = new Parameter($S, $S, $S, $S)";
				  String format = "this.addParameter($S, $L)";
				  for(Parameter param : curr.getParameters()) {
					  methodBuilder.addStatement(newFormat, param.getSqlVarName(), param.getJavaVarName(), param.getJavaType(), param.getSqlType());
					  methodBuilder.addStatement(format, curr.getName(), "param");
				  }
				  				  
				  newFormat = "field = new Field($S, $S, $S, $S)";
				  format = "this.addField($S, $L)";
				  for(Field col : curr.getFields()) {
					  methodBuilder.addStatement(newFormat, col.getSqlVarName(), col.getJavaVarName(), col.getJavaType(), col.getJavaInitialValue());
					  methodBuilder.addStatement(format, curr.getName(), "field");
				  }
			  }
		
		typeBuilder.addMethod(methodBuilder.build());
	}
	
	private void addConstructor(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addAnnotation(Autowired.class)
				.addParameter(DataSource.class, "dseDataSource")
				.addStatement("super(dseDataSource)")
				.addStatement("build()");

		typeBuilder.addAnnotation(Component.class);
		
		typeBuilder.addMethod(methodBuilder.build());
	}

	private void addMapMethod(TypeSpec.Builder typeImplBuilder)
	{
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("map")
			    .addModifiers(Modifier.PUBLIC)
			    .addParameter(List.class, "results")
			    .addStatement("List<$T> list = new $T<$T>()", StringUtil.getClass(jdbcType.getBeanNameFQ()), ArrayList.class, StringUtil.getClass(jdbcType.getBeanNameFQ()))
			    .addStatement("")
			    .addStatement("for(Map<String, Object> row : ((List<$T<String, Object>>)results))\n"
			    		+ "list.add(mapRow(row))", Map.class)
			    .addStatement("return list")
				.returns(GenUtil.getListType(StringUtil.getClass(jdbcType.getBeanNameFQ())));
		
		typeImplBuilder.addMethod(methodBuilder.build());
	}
	
	//TODO: Create map class
	private void addMapRowMethod(TypeSpec.Builder typeImplBuilder) {
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("mapRow")
			    .addModifiers(Modifier.PRIVATE)
			    .addParameter(Map.class, "row")
			    .addStatement("$T bean = new $T()", StringUtil.getClass(jdbcType.getBeanNameFQ()), StringUtil.getClass(jdbcType.getBeanNameFQ()))
			    .addStatement("String resultStr");

		Set<Field> columns = jdbcType.getFields();
		String format1 = "resultStr = extractResult(row, $S)";
		String format2 = "bean.set$L(resultStr)";
		for(Field col : columns) {
			methodBuilder.addStatement(format1, col);
			methodBuilder.addStatement(format2, StringUtil.convertUpperToPascal(col.getSqlVarName()));			
		}
		
		methodBuilder.addStatement("return bean")
			.returns(StringUtil.getClass(jdbcType.getBeanNameFQ()));
		
		typeImplBuilder.addMethod(methodBuilder.build());
	}

	private void addQueryMethod(TypeSpec.Builder typeImplBuilder, Query q)
	{
		MethodSpec.Builder methodImplBuilder = addParameters(q)
				.addStatement("List<$T<String, Object>> results = (List)$L($S, parameters)", Map.class, "exicuteQuery", q.getName())
			    .addStatement("return new $T().map(results)", StringUtil.getClass(jdbcType.getMapNameFQ()))
				.returns(GenUtil.getListType(StringUtil.getClass(jdbcType.getBeanNameFQ())));
		
		for(Parameter param : q.getParameters()) {
			ParameterWrapper pw = new ParameterWrapper(param);
			methodImplBuilder.addParameter(StringUtil.getType(pw.getJavaType()), StringUtil.convertUpperToCammel(param.getSqlVarName()));
		}
				
		typeImplBuilder.addMethod(methodImplBuilder.build());
	}
	
	

	private Builder addParameters(Query q)
	{
		List<Parameter> params = q.getParameters();
		
		Builder method = MethodSpec.methodBuilder(q.getName())
			    .addModifiers(Modifier.PUBLIC)
			    .addStatement("Parameter[] parameters = new $T[$L]", Parameter.class, params.size());
		
		int count = 0;
		String format = "parameters[$L] = new Parameter($S, $S, $S, $S)";
		for(Parameter param : params) {
			String sqlVar = param.getSqlVarName();
			String javaVar = param.getJavaVarName();
			String javaType = param.getJavaType();
			String sqlType = param.getSqlType();
			method.addStatement(format, count++, sqlVar, javaVar, javaType, sqlType);
		}
		
		return method;
	}
}
