package com.generate.generation;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.lang.model.element.Modifier;

import org.reflections.Reflections;

import com.dataAccess.map.DAOMap;
import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.Wrapper.FieldWrapper;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDAOMapFactory
{
	private JdbcTypeWrapper jdbcType;

	public GenerateDAOMapFactory(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
		buildBean();
	}

	public void buildBean() throws IOException
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapFactoryName()).addModifiers(Modifier.PUBLIC);

		
		Reflections r = new Reflections();
		Set<Class<? extends DAOMap>> maps = r.getSubTypesOf(DAOMap.class);
		
		for(Class<?> m : maps)
		{
			if(!m.getSimpleName().equals("DAOMapAbs"))
			{
				typeBuilder.addField(FieldSpec.builder(m, m.getSimpleName(), Modifier.PRIVATE).build());
				GenUtil.addGetterAndSetter(typeBuilder, m.getSimpleName(), m);
			}
		}

		GenUtil.buildAndSave(jdbcType.getPackage("factory"), typeBuilder);
	}
}
