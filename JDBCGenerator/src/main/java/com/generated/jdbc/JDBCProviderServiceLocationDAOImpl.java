package com.generated.jdbc;

import com.generate.parce.bean.Field;
import com.generate.parce.bean.Parameter;
import com.generated.bean.GenerateDaoImplAbs;
import com.generated.bean.ProviderServiceLocation;
import com.generated.dao.JDBCProviderServiceLocationDAO;
import com.generated.map.ProviderServiceLocationDAOMap;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class JDBCProviderServiceLocationDAOImpl extends GenerateDaoImplAbs implements JDBCProviderServiceLocationDAO {
  @Autowired
  public JDBCProviderServiceLocationDAOImpl(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<ProviderServiceLocation> getNonSense(String partyPooper, String billVermin) {
    Parameter[] parameters = new Parameter[2];
    parameters[0] = new Parameter("PARTY_POOPER", null, "Hello", "java.lang.Long");
    parameters[1] = new Parameter("BILL_VERMIN", null, null, null);
    List<Map<String, Object>> results = (List)exicuteQuery("getNonSense", parameters);
    return new ProviderServiceLocationDAOMap().map(results);
  }

  public List<ProviderServiceLocation> getEverything(String breadButter) {
    Parameter[] parameters = new Parameter[1];
    parameters[0] = new Parameter("BREAD_BUTTER", null, null, null);
    List<Map<String, Object>> results = (List)exicuteQuery("getEverything", parameters);
    return new ProviderServiceLocationDAOMap().map(results);
  }

  private void build() {
    Field field;
    Parameter param;
    this.addQuery("getNonSense", "Hello world!\n"
            + "Select HELP_WANTED, PARTY_POOPER, BILL_VERMIN \n"
            + "from diningRoom \n"
            + "where $$BILL_VERMIN$$ = BILL_VERMIN \n"
            + "AND $$PARTY_POOPER$$ = PARTY_POOPER");
    param = new Parameter("PARTY_POOPER", null, "Hello", "java.lang.Long");
    this.addParameter("getNonSense", param);
    param = new Parameter("BILL_VERMIN", null, null, null);
    this.addParameter("getNonSense", param);
    field = new Field("HELP_WANTED", null, null, null);
    this.addField("getNonSense", field);
    field = new Field("PARTY_POOPER", null, null, null);
    this.addField("getNonSense", field);
    field = new Field("BILL_VERMIN", null, null, null);
    this.addField("getNonSense", field);
    this.addQuery("getEverything", "Select HELP_WANTED, PARTY_POOPER, BILL_VERMIN \n"
            + "from diningRoom \n"
            + "where $$BREAD_BUTTER$$ = BREAD_BUTTER");
    param = new Parameter("BREAD_BUTTER", null, null, null);
    this.addParameter("getEverything", param);
    field = new Field("HELP_WANTED", null, null, null);
    this.addField("getEverything", field);
    field = new Field("PARTY_POOPER", null, null, null);
    this.addField("getEverything", field);
    field = new Field("BILL_VERMIN", null, null, null);
    this.addField("getEverything", field);
  }
}
