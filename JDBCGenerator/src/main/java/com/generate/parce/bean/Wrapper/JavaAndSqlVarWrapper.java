package com.generate.parce.bean.Wrapper;

import com.generate.Util.StringUtil;
import com.generate.enums.JAVA_TYPE;
import com.generate.parce.bean.JavaAndSqlVar;

public class JavaAndSqlVarWrapper<T extends JavaAndSqlVar<T>>
{
	T jasVar;
	
	protected T getJasVar()
	{
		return jasVar;
	}
	
	public String getSqlVarName() {
		return jasVar.getSqlVarName();
	}

	public JavaAndSqlVarWrapper(T jasVar)
	{
		this.jasVar = jasVar;
	}
	
	public String getJavaVarName()
	{
		if(!jasVar.stringIsEmpty(jasVar.getJavaVarName()))
			return jasVar.getJavaVarName();
		
		return StringUtil.convertUpperToCammel(jasVar.getSqlVarName());
	}

	public String getJavaType()
	{
		return getJavaTypeClass().getName();
	}

	public String getJavaInitialValue()
	{
		if(!jasVar.stringIsEmpty(jasVar.getJavaType()))
			return jasVar.getJavaType();
		
		return null;
	}
	
	public Class<?> getJavaTypeClass()
	{
		Class<?> c = JAVA_TYPE.getClass(jasVar.getJavaType());
		if(c != null)
			return c;

		return String.class;
	}
}
