package com.generate.generation;

import java.io.IOException;
import java.util.Collection;

import javax.lang.model.element.Modifier;

import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.enums.JAVA_TYPE;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDAOSetup2
{
	private JdbcTypeWrapper jdbcType;

	public GenerateDAOSetup2(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
		buildBean();
	}

	public void buildBean() throws IOException
	{
		buildStringMap();
		buildLongMap();
	}
	private void buildLongMap()
	{
		String type = "Long";
		genNumberMap(type);
		
		type = "Double";
		genNumberMap(type);
	}

	private void genNumberMap(String type)
	{
		
		
		Class<?> clazz = JAVA_TYPE.getClass(type);
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(jdbcType.getMapMethod(null))
				.addModifiers(Modifier.PUBLIC)
				.addParameter(GenUtil.getRowType(), "results")
				.addParameter(String.class, "sqlVarName")
				.addStatement("try{return $T.parse$T(sqlVarName)", clazz, clazz)
				.addStatement("}catch(NumberFormatException e){/*TODO: log*/}")
				.addStatement("return $T.MIN_VALUE", clazz)
				.returns(clazz);

		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapName(clazz.getSimpleName()))
				.addModifiers(Modifier.PUBLIC)
			    .superclass(jdbcType.getMapSuperClass(clazz))	
				.addSuperinterface(jdbcType.getMapInterface(clazz))
				.addMethod(methodBuilder.build());

		GenUtil.buildAndSave(jdbcType.getImplPackage("map"), typeBuilder);
	}

	private void buildStringMap()
	{
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(jdbcType.getMapMethod(null))
				.addModifiers(Modifier.PUBLIC)
				.addParameter(GenUtil.getRowType(), "results")
				.addParameter(String.class, "sqlVarName")
				.addStatement("$T resultObj = results.get(sqlVarName)", Object.class)
				.addStatement("if(resultObj != null)\nreturn resultObj.toString()")
				.addStatement("return null")
				.returns(String.class);

		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapName("String"))
				.addModifiers(Modifier.PUBLIC)
			    .superclass(jdbcType.getMapSuperClass(String.class))	
				.addSuperinterface(jdbcType.getMapInterface(String.class))
				.addMethod(methodBuilder.build());

		GenUtil.buildAndSave(jdbcType.getImplPackage(jdbcType.getMapPkg()), typeBuilder);
	}
}
