package com.generate.generation;

import java.io.IOException;
import java.util.Collection;

import javax.lang.model.element.Modifier;

import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

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
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getBeanName()).addModifiers(Modifier.PUBLIC);

		Collection<Field> fields = jdbcType.getFields();

		for (Field field : fields)
		{
			FieldWrapper fw = new FieldWrapper(field);
			String fieldName = StringUtil.convertUpperToCammel(fw.getJavaVarName());
			String methodSuffix = StringUtil.convertUpperToPascal(fw.getJavaVarName());

			typeBuilder.addField(fw.getJavaTypeClass(), fieldName, Modifier.PRIVATE);

			MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("get".concat(methodSuffix))
					.addModifiers(Modifier.PUBLIC).addStatement("return " + fieldName).returns(fw.getJavaTypeClass());

			typeBuilder.addMethod(methodBuilder.build());

			methodBuilder = MethodSpec.methodBuilder("set".concat(methodSuffix)).addModifiers(Modifier.PUBLIC)
					.addParameter(fw.getJavaTypeClass(), fieldName).addStatement("this." + fieldName + " = " + fieldName)
					.returns(void.class);

			typeBuilder.addMethod(methodBuilder.build());
		}

		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getBeanPkg()), typeBuilder);
	}
}
