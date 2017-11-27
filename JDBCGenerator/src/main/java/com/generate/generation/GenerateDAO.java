package com.generate.generation;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.generate.Util.GenUtil;
import com.generate.Util.StringUtil;
import com.generate.parce.bean.Parameter;
import com.generate.parce.bean.Query;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.generate.parce.bean.Wrapper.ParameterWrapper;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDAO
{
	private JdbcTypeWrapper jdbcType;
	
	public GenerateDAO(JdbcTypeWrapper jdbcType) throws IOException {
		super();
		this.jdbcType = jdbcType;
		buildDAO();
	}
	
	public void buildDAO() throws IOException {
		
		TypeSpec.Builder typeInfBuilder = TypeSpec.interfaceBuilder(jdbcType.getDaoName())
			    .addModifiers(Modifier.PUBLIC);
		

		List<Query> qs = jdbcType.getQueries();

		for(Query q : qs) {
			
			addQueryMethod(typeInfBuilder, q.getName(), q);
		}
		
		GenUtil.buildAndSave(jdbcType.getPackage("dao"), typeInfBuilder);
	}

	private void addQueryMethod(TypeSpec.Builder typeInfBuilder,
			String methodName, Query q)
	{
		MethodSpec.Builder methodInfBuilder = MethodSpec.methodBuilder(methodName)
			    .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
				.returns(GenUtil.getListType(StringUtil.getClass(jdbcType.getBeanNameFQ())));
		
		for(Parameter param : q.getParameters()) {
			ParameterWrapper pw = new ParameterWrapper(param);
			methodInfBuilder.addParameter(pw.getJavaTypeClass(), StringUtil.convertUpperToCammel(param.getSqlVarName()));
		}
		
		typeInfBuilder.addMethod(methodInfBuilder.build());
	}
}
