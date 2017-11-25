package com.generate.generation;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.Modifier;

import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.XmlProps;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@SuppressWarnings("restriction")
public class GenerateDAOBean
{
	private JdbcTypeWrapper jdbcType;

	public GenerateDAOBean(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
		buildBean();
	}

	public void buildBean() throws IOException
	{
		String bn = jdbcType.getBeanName();
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getBeanName()).addModifiers(Modifier.PUBLIC);

		Set<Field> columns = jdbcType.getFields();

		for (Field col : columns)
		{
			FieldWrapper fw = new FieldWrapper(col);
			String fieldName = StringUtil.convertUpperToCammel(fw.getJavaVarName());
			String methodSuffix = StringUtil.convertUpperToPascal(fw.getJavaVarName());

			typeBuilder.addField(String.class, fieldName, Modifier.PRIVATE);

			MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("get".concat(methodSuffix))
					.addModifiers(Modifier.PUBLIC).addStatement("return " + fieldName).returns(String.class);

			typeBuilder.addMethod(methodBuilder.build());

			methodBuilder = MethodSpec.methodBuilder("set".concat(methodSuffix)).addModifiers(Modifier.PUBLIC)
					.addParameter(String.class, fieldName).addStatement("this." + fieldName + " = " + fieldName)
					.returns(void.class);

			typeBuilder.addMethod(methodBuilder.build());
		}

		GenUtil.buildAndSave(jdbcType.getPackage("bean"), typeBuilder);
	}
}
