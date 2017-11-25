package com.generate.parce.bean.Wrapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.generate.Util.StringUtil;
import com.generate.parce.bean.Field;
import com.generate.parce.bean.JdbcType;
import com.generate.parce.bean.Query;

public class JdbcTypeWrapper extends JdbcType
{
	String beanPkg = "bean";
	String daoPkg = "dao";
	String mapPkg = "map";
	String jdbcPkg = daoPkg.concat(".jdbc");
	
	public JdbcTypeWrapper(String name, String abstractName, String pckage, List<Query> queries)
	{
		super(name, abstractName, pckage, queries);
	}

	public String getAbstractName()
	{
		String userName = super.getAbstractName();
		if(userName != null && !userName.isEmpty())
			return userName;

		return "GenerateDaoImplAbs";
	}
	
	public String getPackage(String extention)
	{
		String userPackage = super.getPackage();
		if(userPackage != null && !userPackage.isEmpty())
			return super.getPackage().concat(".").concat(extention);
		
		return "com.generated.".concat(extention);
	}
	
	public String getPackage()
	{
		return getPackage("");
	}
	
	public String getAbstractNameFQ()
	{
		String packagePrefix = getPackage().concat("bean.");
		String userAbsName = packagePrefix.concat(super.getAbstractName());
		if(StringUtil.getClass(userAbsName) != null)
			return userAbsName;

		if(StringUtil.getClass(packagePrefix.concat("GenerateDaoImplAbs")) != null)
			return packagePrefix.concat("GenerateDaoImplAbs");
		
		return null;
	}
	
	public String createFQName(String name, String subPkg) {
		return getPackage().concat(subPkg.concat(".")).concat(name);
	}
	
	public String getJdbcName()
	{
		return "JDBC".concat(super.getName()).concat("DAOImpl");
	}

	public String getJdbcNameFQ()
	{
		return createFQName(getJdbcName(), jdbcPkg);
	}

	public String getDaoName()
	{
		return "JDBC".concat(super.getName()).concat("DAO");
	}
	
	public String getDaoNameFQ()
	{
		return createFQName(getDaoName(), daoPkg);
	}

	public String getMapName()
	{
		return super.getName().concat("DAOMap");
	}
	
	public String getMapNameFQ()
	{
		return createFQName(getMapName(), mapPkg);
	}
	
	public String getBeanName()
	{
		return super.getName();
	}
	
	public String getBeanNameFQ()
	{
		return createFQName(getBeanName(), beanPkg);
	}
	
	public Set<Field> getFields()
	{
		Set<Field> unique = new HashSet<Field>();
		
		for(Query q : this.getQueries())
		{
			List<Field> fields = q.getFields();
			unique.addAll(fields);
		}
		
		return unique;
	}
}


















