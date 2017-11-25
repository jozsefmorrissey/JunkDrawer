package com.generated.jdbc;

import com.generate.parce.bean.Substitution;
import com.generated.bean.BaseJdbcDAOGen;
import com.generated.bean.user_info;
import com.generated.dao.user_infoDAO;
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
public class JDBCuser_infoDAO extends BaseJdbcDAOGen implements user_infoDAO {
  @Autowired
  public JDBCuser_infoDAO(DataSource dseDataSource) {
    super(dseDataSource);
    build();
  }

  public List<user_info> getUserByEmail(String email) {
    Substitution[] substitutions = new Substitution[1];
    substitutions[0] = new Substitution("email", email, 0);
    List<Map<String, Object>> results = (List)exicuteQuery("getUserByEmail", substitutions);
    return this.map(results);
  }

  public List<user_info> map(List results) {
    List<user_info> list = new ArrayList<user_info>();
    ;
    for(Map<String, Object> row : ((List<Map<String, Object>>)results))
        list.add(mapRow(row));
    return list;
  }

  private user_info mapRow(Map row) {
    user_info bean = new user_info();
    String resultStr;
    resultStr = extractResult(row, "password");
    bean.setPassword(resultStr);
    resultStr = extractResult(row, "name");
    bean.setName(resultStr);
    resultStr = extractResult(row, "email");
    bean.setEmail(resultStr);
    return bean;
  }

  private void build() {
    this.addQuery("getUserByEmail", " Select email, password, name from user_info where $$email$$ = email ");
    this.addSubstitute("getUserByEmail", "email");
    this.addColumn("getUserByEmail", "name");
    this.addColumn("getUserByEmail", "password");
    this.addColumn("getUserByEmail", "email");
  }
}
