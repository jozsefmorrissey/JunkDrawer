package com.generate.parce.bean.Wrapper;

import com.generate.enums.SQL_TYPE;
import com.generate.parce.bean.Parameter;

public class ParameterWrapper extends JavaAndSqlVarWrapper<Parameter>
{

	public ParameterWrapper(Parameter jasVar)
	{
		super(jasVar);
	}

	public String getSqlType() {		
		return SQL_TYPE.getEnum(this.getSqlType()).toString();
	}
}
