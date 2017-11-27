package com.generate.generation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Modifier;

import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDAOMap
{
	private JdbcTypeWrapper jdbcType;
	private Class<?> bean;
	
	String factoryClassName;
	String factoryName;

	public GenerateDAOMap(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
		bean = StringUtil.getClass(jdbcType.getBeanNameFQ());
		factoryClassName = jdbcType.getMapFactoryName();
		factoryName = StringUtil.unCapitalizeIndex(factoryClassName, 0);
		buildMap();
	}

	public void buildMap() throws IOException
	{

		
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapName())
			    .superclass(jdbcType.getMapSuperClass(bean))	
				.addSuperinterface(jdbcType.getMapInterface(bean))
				.addField(jdbcType.getClass(factoryClassName, String.class), factoryName)
				.addModifiers(Modifier.PUBLIC);

		addMapRowMethod(typeBuilder);
		
		GenUtil.buildAndSave(jdbcType.getImplPackage("map"), typeBuilder);
	}
	
	private void addMapRowMethod(TypeSpec.Builder typeImplBuilder) {
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("mapRow")
			    .addModifiers(Modifier.PUBLIC)
			    .addParameter(GenUtil.getMapType(String.class, Object.class), "row")
			    .addParameter(String.class, "sqlVarName")
			    .addStatement("$T bean = new $T()", bean, bean);

		Collection<Field> fields = jdbcType.getFields();
		String format = "bean.set$L($L.get$T().$L(row, $S))";
		for(Field field : fields) {
			FieldWrapper fw = new FieldWrapper(field);

			methodBuilder.addStatement(format, StringUtil.convertUpperToPascal(fw.getJavaVarName()),
					factoryName,
					jdbcType.getMapClass(StringUtil.getClass(fw.getJavaType()).getSimpleName()),
					jdbcType.getMapMethod(fw.getJavaType()),
					fw.getSqlVarName());			
		}
		
		methodBuilder.addStatement("return bean")
			.returns(bean);
		
		typeImplBuilder.addMethod(methodBuilder.build());
	}
}
