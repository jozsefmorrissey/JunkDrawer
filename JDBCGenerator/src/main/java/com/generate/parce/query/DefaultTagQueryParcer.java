package com.generate.parce.query;

import com.generate.parce.QueryParcerAbs;
import com.generate.parce.column.DefaultColumnParcer;
import com.generate.parce.parameter.DefaultParameterParser;

public class DefaultTagQueryParcer extends QueryParcerAbs
{
	public DefaultTagQueryParcer()
	{
		super.setColParcer(new DefaultColumnParcer());
		super.setParamParcer(new DefaultParameterParser());
		setTagName("query");
	}
}
