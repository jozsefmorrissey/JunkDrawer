package com.generated.jdbc;

import com.generate.parce.bean.Substitution;
import com.generated.bean.BaseJdbcDAOGen;
import com.generated.bean.ProviderServiceLocation;
import com.generated.dao.ProviderServiceLocationDAO;
import java.lang.Long;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@SuppressWarnings({"unchecked", "rawtypes"})
@Component
public class JDBCProviderServiceLocationDAO extends BaseJdbcDAOGen implements ProviderServiceLocationDAO {
  @Autowired
  public JDBCProviderServiceLocationDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<ProviderServiceLocation> getNonSense(String billVermin, Long partyPooper) {
    Substitution[] substitutions = new Substitution[2];
    substitutions[0] = new Substitution("BILL_VERMIN", billVermin, 0);
    substitutions[1] = new Substitution("PARTY_POOPER", partyPooper, 0);
    List<Map<String, Object>> results = (List)exicuteQuery("getNonSense", substitutions);
    return this.map(results);
  }

  public List<ProviderServiceLocation> getEverything(String breadButter) {
    Substitution[] substitutions = new Substitution[1];
    substitutions[0] = new Substitution("BREAD_BUTTER", breadButter, 0);
    List<Map<String, Object>> results = (List)exicuteQuery("getEverything", substitutions);
    return this.map(results);
  }

  public List<ProviderServiceLocation> map(List results) {
    List<ProviderServiceLocation> pslList = new ArrayList<ProviderServiceLocation>();
    ;
    for(Map<String, Object> row : ((List<Map<String, Object>>)results))
        mapRow(row);
    return pslList;
  }

  private ProviderServiceLocation mapRow(Map row) {
    ProviderServiceLocation bean = new ProviderServiceLocation();
    String resultStr;
    resultStr = extractResult(row, "BILL_VERMIN");
    bean.setBillVermin(resultStr);
    resultStr = extractResult(row, "HELP_WANTED");
    bean.setHelpWanted(resultStr);
    resultStr = extractResult(row, "PARTY_POOPER");
    bean.setPartyPooPer(resultStr);
    return bean;
  }

  private void build() {
    this.addQuery("getNonSense", " Select HELP_WANTED, PARTY_POOPER, BILL_VERMIN from diningRoom where $$BILL_VERMIN$$ = BILL_VERMIN AND $$PARTY_POOPER$$ = PARTY_POOPER ");
    this.addSubstitute("getNonSense", "BILL_VERMIN");
    this.addSubstitute("getNonSense", "PARTY_POOPER");
    this.addColumn("getNonSense", "HELP_WANTED");
    this.addColumn("getNonSense", "PARTY_POOPER");
    this.addColumn("getNonSense", "BILL_VERMIN");
    this.addQuery("getEverything", " Select HELP_WANTED, PARTY_POOPER, BILL_VERMIN from diningRoom where $$BREAD_BUTTER$$ = BREAD_BUTTER ");
    this.addSubstitute("getEverything", "BREAD_BUTTER");
    this.addColumn("getEverything", "HELP_WANTED");
    this.addColumn("getEverything", "BILL_VERMIN");
  }
}
