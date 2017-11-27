package com.generate.parce.bean;

public class JavaAndSqlVar<T extends JavaAndSqlVar<T>> extends MergeBean<T>
{
	private String sqlVarName;
	private String javaVarName;
	private String javaType;
	
	public JavaAndSqlVar()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public JavaAndSqlVar(String sqlName)
	{
		super();
		this.sqlVarName = sqlName;
	}
	public JavaAndSqlVar(String sqlVarName, String javaVarName, String javaType)
	{
		super();
		this.sqlVarName = sqlVarName;
		this.javaVarName = javaVarName;
		this.javaType = javaType;
	}
	
	public T merge(T obj)
	{
		if(obj == null)
			return (T) new JavaAndSqlVar(this.sqlVarName, this.javaVarName, this.javaType);
		
		T merged = (T) new JavaAndSqlVar();
		
		merged.setJavaType(mergeParam(this.javaType, obj.getJavaType()));
		merged.setJavaVarName(mergeParam(this.javaVarName, obj.getJavaVarName()));
		merged.setSqlVarName(mergeParam(this.sqlVarName, obj.getSqlVarName()));
		
		return merged;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U> U stringGetTieBreaker(String field1, String field2)
	{
		return (U) field1;
	}
	
	public boolean shouldMerge(JavaAndSqlVar obj)
	{
		boolean sqlVarEq = this.sqlVarName.equals(obj.sqlVarName);
		boolean thisJVarNull = this.javaVarName == null;
		boolean aJVarNull = thisJVarNull && obj.javaVarName == null;

		boolean sqlEqAJVarNotSet = sqlVarEq;
		boolean jVarSetEq = this.javaVarName != null && this.javaVarName.equals(obj.javaVarName);

		return sqlEqAJVarNotSet || jVarSetEq;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
		result = prime * result + ((javaVarName == null) ? 0 : javaVarName.hashCode());
		result = prime * result + ((sqlVarName == null) ? 0 : sqlVarName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaAndSqlVar other = (JavaAndSqlVar) obj;
		if (javaType == null)
		{
			if (other.javaType != null)
				return false;
		} else if (!javaType.equals(other.javaType))
			return false;
		if (javaVarName == null)
		{
			if (other.javaVarName != null)
				return false;
		} else if (!javaVarName.equals(other.javaVarName))
			return false;
		if (sqlVarName == null)
		{
			if (other.sqlVarName != null)
				return false;
		} else if (!sqlVarName.equals(other.sqlVarName))
			return false;
		return true;
	}
	@Override
	public String toString()
	{
		return "JavaToSqlVar [sqlVarName=" + sqlVarName + ", javaVarName=" + javaVarName + ", javaType=" + javaType
				+ "]";
	}
	public String getSqlVarName()
	{
		return sqlVarName;
	}
	public void setSqlVarName(String sqlVarName)
	{
		this.sqlVarName = sqlVarName;
	}
	public String getJavaVarName()
	{
		return javaVarName;
	}
	public void setJavaVarName(String javaVarName)
	{
		this.javaVarName = javaVarName;
	}
	public String getJavaType()
	{
		return javaType;
	}
	public void setJavaType(String javaType)
	{
		this.javaType = javaType;
	}
}
