package com.generate.parce.column;

import java.util.List;

import org.w3c.dom.Element;

import com.generate.exception.XmlParceException;
import com.generate.parce.TagParcerAbs;
import com.generate.parce.bean.Field;

public class DefaultColumnParcer extends TagParcerAbs<Field>
{
	TagColumnParcer tcp = new TagColumnParcer();
	SqlStringColumnParcer sscp = new SqlStringColumnParcer();
	
	List<Field> columns;
	int index = 0;
	
	@Override
	public Field parce(Element node) throws XmlParceException
	{
		if(columns == null)
		{
			while(!node.getTagName().equals("query"))
				node = (Element) node.getParentNode();
			
			parceAll(node);
		}
		
		if(index == columns.size())
		{
			index = 0;
			return null;
		}
		
		return columns.get(index++);
	}

	@Override
	public List<Field> parceAll(Element node) throws XmlParceException
	{
		List<Field> tagList = tcp.parceAll(node);
		List<Field> sqlList = sscp.parceAll(node);
		
		columns = new Field().merge(tagList, sqlList);
		return columns;
	}
}
