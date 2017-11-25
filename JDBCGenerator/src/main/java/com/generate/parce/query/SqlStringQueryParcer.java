package com.generate.parce.query;

import com.generate.parce.QueryParcerAbs;
import com.generate.parce.column.SqlStringColumnParcer;
import com.generate.parce.parameter.SqlStringParamParcer;

public class SqlStringQueryParcer extends QueryParcerAbs
{
	public SqlStringQueryParcer()
	{
		super.setColParcer(new SqlStringColumnParcer());
		super.setParamParcer(new SqlStringParamParcer());
		setTagName("query");
	}
}
