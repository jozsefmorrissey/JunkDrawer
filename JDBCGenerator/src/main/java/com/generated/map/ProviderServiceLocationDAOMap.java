package com.generated.map;

import com.generated.bean.ProviderServiceLocation;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProviderServiceLocationDAOMap {
  public List<ProviderServiceLocation> map(List results) {
    List<ProviderServiceLocation> list = new ArrayList<ProviderServiceLocation>();
    ;
    for(Map<String, Object> row : ((List<Map<String, Object>>)results))
        list.add(mapRow(row));
    return list;
  }

  private ProviderServiceLocation mapRow(Map<String, Object> row) {
    ProviderServiceLocation bean = new ProviderServiceLocation();
    String resultStr;
    resultStr = extractResult(row, "HELP_WANTED");
    bean.setHelpwanted(resultStr);
    resultStr = extractResult(row, "PARTY_POOPER");
    bean.setPartyPooPer(resultStr);
    resultStr = extractResult(row, "BILL_VERMIN");
    bean.setBillvermin(resultStr);
    return bean;
  }

  protected String extractResult(Map<String, Object> results, String sqlVarName) {
    Object resultObj = results.get(sqlVarName);
    if(resultObj != null)
        return resultObj.toString();
    return null;
  }
}
