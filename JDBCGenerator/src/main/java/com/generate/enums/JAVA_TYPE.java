package com.generate.enums;

import com.generate.Util.StringUtil;
import com.generate.marker.Validator;

public enum JAVA_TYPE
{
	LONG("long", long.class), INTEGER("int", int.class), STRING("string", String.class);
	
	private String identifier;
	private Class<?> clazz;
	
	private JAVA_TYPE(String identifier, Class<?> clazz)
	{
		this.identifier = identifier;
		this.clazz = clazz;
	}
	
	public static JAVA_TYPE getEnum(String indicator)
	{
		for(JAVA_TYPE sqlT : JAVA_TYPE.values())
		{
			if(sqlT.identifier.toLowerCase().equals(indicator))
				return sqlT;
		}
		
		return null;
	}
	
	public boolean validateInitVal(String indicator, String str)
	{
		JAVA_TYPE javaT = getEnum(indicator);
		return validateInitVal(javaT, str);
	}

	public boolean validateInitVal(JAVA_TYPE javaT, String str)
	{
		Validator val = getValidator(StringUtil.convertUpperToPascal(javaT.name()));
		if(val == null)
			return val.Validate(str);
		
		return false;		
	}
	
	@SuppressWarnings("unchecked")
	public static <U extends Validator>  U getValidator(String str)
	{
		try
		{
			Class<?> clazz = getClass("com.generate.validate." + str + "validator");
			if(clazz != null && clazz.isInstance(Validator.class))
				return (U) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Class<?> getClass(String classStr)
	{
		try
		{
			return Class.forName(classStr);
		} catch (ClassNotFoundException | NullPointerException e)
		{
			//TODO: implement Logger
		}

		return null;
	}
	
	protected String getIdentifier()
	{
		return identifier;
	}

	protected Class<?> getClazz()
	{
		return clazz;
	}

}
