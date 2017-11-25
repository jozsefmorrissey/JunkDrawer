package com.generated.jdbc;

import com.generate.parce.bean.Substitution;
import com.generated.bean.AccreditationAproximation;
import com.generated.bean.BaseJdbcDAOGen;
import com.generated.dao.AccreditationAproximationDAO;
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
public class JDBCAccreditationAproximationDAO extends BaseJdbcDAOGen implements AccreditationAproximationDAO {
  @Autowired
  public JDBCAccreditationAproximationDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<AccreditationAproximation> getStuffedBunnies(String breadButter) {
    Substitution[] substitutions = new Substitution[1];
    substitutions[0] = new Substitution("BREAD_BUTTER", breadButter, 0);
    List<Map<String, Object>> results = (List)exicuteQuery("getStuffedBunnies", substitutions);
    return this.map(results);
  }

  public List<AccreditationAproximation> getGarbTastic(String billVermin, Long partyPooper) {
    Substitution[] substitutions = new Substitution[2];
    substitutions[0] = new Substitution("BILL_VERMIN", billVermin, 0);
    substitutions[1] = new Substitution("PARTY_POOPER", partyPooper, 0);
    List<Map<String, Object>> results = (List)exicuteQuery("getGarbTastic", substitutions);
    return this.map(results);
  }

  public List<AccreditationAproximation> map(List results) {
    List<AccreditationAproximation> pslList = new ArrayList<AccreditationAproximation>();
    ;
    for(Map<String, Object> row : ((List<Map<String, Object>>)results))
        mapRow(row);
    return pslList;
  }

  private AccreditationAproximation mapRow(Map row) {
    AccreditationAproximation bean = new AccreditationAproximation();
    String resultStr;
    resultStr = extractResult(row, "SKITTLES");
    bean.setSkittleS(resultStr);
    resultStr = extractResult(row, "DOWN_HAWK_BLACK");
    bean.setDownHawkBlack(resultStr);
    resultStr = extractResult(row, "HELICOPTER");
    bean.setHelicopter(resultStr);
    resultStr = extractResult(row, "BRUCE_WILLIS");
    bean.setBruceWillis(resultStr);
    resultStr = extractResult(row, "SPAGGETIOS");
    bean.setSpaggetioS(resultStr);
    return bean;
  }

  private void build() {
    this.addQuery("getStuffedBunnies", " Select HELICOPTER, DOWN_HAWK_BLACK, BRUCE_WILLIS, SPAGGETIOS from darkDungion where $$DOWN_HAWK_BLACK$$ = DOWN_HAWK_BLACK AND $$SKITTLES$$ = SKITTLES ");
    this.addSubstitute("getStuffedBunnies", "BREAD_BUTTER");
    this.addColumn("getStuffedBunnies", "DOWN_HAWK_BLACK");
    this.addColumn("getStuffedBunnies", "SKITTLES");
    this.addQuery("getGarbTastic", " Select HELICOPTER, DOWN_HAWK_BLACK, BRUCE_WILLIS, SPAGGETIOS from darkDungion where $$BILL_VERMIN$$ = BILL_VERMIN AND $$PARTY_POOPER$$ = PARTY_POOPER ");
    this.addSubstitute("getGarbTastic", "BILL_VERMIN");
    this.addSubstitute("getGarbTastic", "PARTY_POOPER");
    this.addColumn("getGarbTastic", "HELICOPTER");
    this.addColumn("getGarbTastic", "DOWN_HAWK_BLACK");
    this.addColumn("getGarbTastic", "BRUCE_WILLIS");
    this.addColumn("getGarbTastic", "SPAGGETIOS");
  }
}
