package com.generate.parce.column;

import java.util.ArrayList;
import java.util.List;

import com.generate.parce.TextContentParcerAbs;
import com.generate.parce.bean.Field;

public class SqlStringColumnParcer extends TextContentParcerAbs<Field>
{

	@Override
	protected List<Field> parceString(String query)
	{
	    query = query.replaceAll("//s+", "");
	    query = query.toUpperCase();
	 
	    int afterSelect = query.indexOf("SELECT") + 6;
	    int startFrom = query.indexOf("FROM");
	    
	    String commaSepColumns = query.substring(afterSelect, startFrom);
	    String[] columArr = commaSepColumns.split(",");
	    
	    List<Field> retMap = new ArrayList<Field>();
	    for(String columnName : columArr) {
	      if(!columnName.isEmpty())
	        retMap.add(new Field(columnName.trim()));
	    }
	    
	    return retMap;
	}
	
}
