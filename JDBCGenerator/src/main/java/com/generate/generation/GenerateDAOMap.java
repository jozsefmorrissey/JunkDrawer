package com.generate.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Modifier;

import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@SuppressWarnings("restriction")
public class GenerateDAOMap
{
	private JdbcTypeWrapper jdbcType;

	public GenerateDAOMap(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
		buildMap();
	}

	public void buildMap() throws IOException
	{
		String bn = jdbcType.getMapName();
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapName()).addModifiers(Modifier.PUBLIC);

		addMapMethod(typeBuilder);
		addMapRowMethod(typeBuilder);
		buildExtractResult(typeBuilder);
		
		GenUtil.buildAndSave(jdbcType.getPackage("map"), typeBuilder);
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
			    .addParameter(GenUtil.getMapType(String.class, Object.class), "row")
			    .addStatement("$T bean = new $T()", StringUtil.getClass(jdbcType.getBeanNameFQ()), StringUtil.getClass(jdbcType.getBeanNameFQ()))
			    .addStatement("String resultStr");

		Set<Field> columns = jdbcType.getFields();
		String format1 = "resultStr = extractResult(row, $S)";
		String format2 = "bean.set$L(resultStr)";
		for(Field col : columns) {
			FieldWrapper fw = new FieldWrapper(col);
			methodBuilder.addStatement(format1, col.getSqlVarName());
			methodBuilder.addStatement(format2, StringUtil.convertUpperToPascal(fw.getJavaVarName()));			
		}
		
		methodBuilder.addStatement("return bean")
			.returns(StringUtil.getClass(jdbcType.getBeanNameFQ()));
		
		typeImplBuilder.addMethod(methodBuilder.build());
	}
	
	private void buildExtractResult(TypeSpec.Builder typeBuilder)
	{
		MethodSpec.Builder methodBuilder;
		methodBuilder = MethodSpec.methodBuilder("extractResult")
			    .addModifiers(Modifier.PROTECTED)
			    .addParameter(GenUtil.getMapType(String.class, Object.class), "results")
			    .addParameter(String.class, "sqlVarName")
			    .addStatement("Object resultObj = results.get(sqlVarName)")
			    .addStatement("if(resultObj != null)\nreturn resultObj.toString()")
			    .addStatement("return null")
			    .returns(String.class);
				
	
		typeBuilder.addMethod(methodBuilder.build());
	}
}
