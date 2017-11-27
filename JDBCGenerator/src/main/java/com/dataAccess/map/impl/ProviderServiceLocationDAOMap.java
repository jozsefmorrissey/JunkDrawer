package com.dataAccess.map.impl;

import com.dataAccess.bean.ProviderServiceLocation;
import com.dataAccess.factory.MapFactory;
import com.dataAccess.map.DAOMap;
import com.dataAccess.map.DAOMapAbs;
import java.lang.Object;
import java.lang.String;
import java.util.Map;

public class ProviderServiceLocationDAOMap extends DAOMapAbs<ProviderServiceLocation> implements DAOMap<ProviderServiceLocation> {
  MapFactory mapFactory;

  public ProviderServiceLocation mapRow(Map<String, Object> row, String sqlVarName) {
    ProviderServiceLocation bean = new ProviderServiceLocation();
    bean.setHello(mapFactory.getStringDAOMap().mapRow(row, "HELP_WANTED"));
    bean.setPartyPooPer(mapFactory.getStringDAOMap().mapRow(row, "PARTY_POOPER"));
    bean.setBillvermin(mapFactory.getStringDAOMap().mapRow(row, "BILL_VERMIN"));
    return bean;
  }
}
