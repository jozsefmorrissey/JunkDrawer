package com.generate.parce.query;

import com.generate.parce.QueryParcerAbs;
import com.generate.parce.column.TagColumnParcer;
import com.generate.parce.parameter.TagParameterParcer;

public class TagQueryParcer extends QueryParcerAbs
{
	public TagQueryParcer()
	{
		super.setColParcer(new TagColumnParcer());
		super.setParamParcer(new TagParameterParcer());
		setTagName("query");
	}
}
