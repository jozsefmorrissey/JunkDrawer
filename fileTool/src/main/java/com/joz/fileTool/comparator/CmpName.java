package com.joz.fileTool.comparator;

import java.util.Comparator;

import com.joz.fileTool.marker.HasName;

public class CmpName implements Comparator<HasName>{

	@Override
	public int compare(HasName arg0, HasName arg1) {
		return arg0.getName().compareTo(arg1.getName());
	}

}
