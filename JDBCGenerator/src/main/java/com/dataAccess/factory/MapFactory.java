package com.dataAccess.factory;

import com.dataAccess.map.impl.DoubleDAOMap;
import com.dataAccess.map.impl.LongDAOMap;
import com.dataAccess.map.impl.StringDAOMap;

public class MapFactory {
  private DoubleDAOMap DoubleDAOMap;

  private LongDAOMap LongDAOMap;

  private StringDAOMap StringDAOMap;

  public DoubleDAOMap getDoubleDAOMap() {
    return DoubleDAOMap;
  }

  public void setDoubleDAOMap(DoubleDAOMap DoubleDAOMap) {
    this.DoubleDAOMap = DoubleDAOMap;
  }

  public LongDAOMap getLongDAOMap() {
    return LongDAOMap;
  }

  public void setLongDAOMap(LongDAOMap LongDAOMap) {
    this.LongDAOMap = LongDAOMap;
  }

  public StringDAOMap getStringDAOMap() {
    return StringDAOMap;
  }

  public void setStringDAOMap(StringDAOMap StringDAOMap) {
    this.StringDAOMap = StringDAOMap;
  }
}
