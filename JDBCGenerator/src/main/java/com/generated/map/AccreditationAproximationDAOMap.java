package com.generated.map;

import com.generated.bean.AccreditationAproximation;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccreditationAproximationDAOMap {
  public List<AccreditationAproximation> map(List results) {
    List<AccreditationAproximation> list = new ArrayList<AccreditationAproximation>();
    ;
    for(Map<String, Object> row : ((List<Map<String, Object>>)results))
        list.add(mapRow(row));
    return list;
  }

  private AccreditationAproximation mapRow(Map<String, Object> row) {
    AccreditationAproximation bean = new AccreditationAproximation();
    String resultStr;
    resultStr = extractResult(row, "SPAGGETIOS");
    bean.setSpaggetioS(resultStr);
    resultStr = extractResult(row, "BRUCE_WILLIS");
    bean.setBrucewillis(resultStr);
    resultStr = extractResult(row, "HELICOPTER");
    bean.setHelicopter(resultStr);
    resultStr = extractResult(row, "DOWN_HAWK_BLACK");
    bean.setDownhawkblack(resultStr);
    return bean;
  }

  protected String extractResult(Map<String, Object> results, String sqlVarName) {
    Object resultObj = results.get(sqlVarName);
    if(resultObj != null)
        return resultObj.toString();
    return null;
  }
}
