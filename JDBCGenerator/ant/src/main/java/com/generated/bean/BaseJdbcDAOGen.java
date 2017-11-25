package com.generated.bean;

import com.generate.Util.StringUtil;
import com.generate.parce.bean.Query;
import com.generate.parce.bean.Substitution;

import java.lang.Object;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class BaseJdbcDAOGen {
  private Log log = LogFactory.getLog(BaseJdbcDAOGen.class);

  @Autowired
  private AbstractMessageSource messageSource;

  private Map<String, Query> queries;

  private JdbcTemplate jdbcTemplate;

  @Autowired
  protected BaseJdbcDAOGen(DataSource dseDataSource) {
    jdbcTemplate = new JdbcTemplate(dseDataSource);
    queries = new HashMap<String, Query>();
  }

  public Log getLog() {
    return log;
  }

  public void setLog(Log log) {
    this.log = log;
  }

  public AbstractMessageSource getMessageSource() {
    return messageSource;
  }

  public void setMessageSource(AbstractMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  protected void addQuery(String name, String queryFormat) {
    Query q = new Query(queryFormat);
    ((Map<String, Query>)queries).put(name, q);
  }

  protected void addSubstitute(String queryName, String columnName) {
    if(((Map<String, Query>)queries).containsKey(queryName))
        ((Map<String, Query>)queries).get(queryName).getSubstitutions().add(new Substitution(columnName));
  }

  protected void addColumn(String queryName, String columnName) {
    if(((Map<String, Query>)queries).containsKey(queryName))
        ((Map<String, Query>)queries).get(queryName).getFields().add(columnName);
  }

  protected void setSubstituteValue(String queryName, String columnName, Object value) {
    if(((Map<String, Query>)queries).containsKey(queryName))
        for(Substitution s : ((Map<String, Query>)queries).get(queryName).getSubstitutions())
        if(s.getColumn().equals(columnName))
        s.setValue(value);
  }

  protected String extractResult(Map<String, Object> results, String col) {
    Object resultObj = results.get(col);
    if(resultObj != null)
        return resultObj.toString();
    return null;
  }

  protected List<Map> exicuteQuery(String queryName, Substitution[] substitutions) {
    long startJavaLogTime = Calendar.getInstance().getTimeInMillis();
    String query = buildQuery(queryName, substitutions);
    List<Map> rows = (List) jdbcTemplate.queryForList(query);
    logRequestTimer(startJavaLogTime, query);
    return rows;
  }

  private List<Map> mockData(String name) {
    Query q = (Query) queries.get(name);
    List<Map> results = new ArrayList<Map>();
    StringBuilder[] words = new StringBuilder[]{new StringBuilder("First"), new StringBuilder("Second"), new StringBuilder("Third")};
    for(int i = 0; i < 3; i++)
        mockDataRowBuilder(results, q, words[i]);
    return results;
  }

  protected List<Map> exicuteMockQuery(String queryName, Substitution[] substitutions) {
    return mockData(queryName);
  }

  private String makeSubstitution(String queryStr, Substitution sub) {
    String varStr = StringUtil.getVarString(sub.getColumn());
    String valueStr = sub.getValue();
    queryStr = queryStr.replace(varStr, valueStr);
    return queryStr;
  }

  protected String buildQuery(String queryName, Substitution[] substitutions) {
    Query q = (Query) queries.get(queryName);
    String queryStr = q.getFormat();
    for(Substitution sub : substitutions)
        queryStr = makeSubstitution(queryStr, sub);
    System.out.println("Query: " + queryStr);
    return queryStr;
  }

  protected int getRowCount(String queryName, Substitution[] substitutions) {
    return exicuteQuery(queryName, substitutions).size();
  }

  protected void logRequestTimer(long startJavaLogTime, String query) {
  }

  private Map mockDataRowBuilder(List results, Query q, StringBuilder word) {
    Map row = new HashMap();
    for(String col : q.getFields())
        mockDataAddWord(row, word, col);
    results.add(row);
    return row;
  }

  protected void mockDataAddWord(Map row, StringBuilder word, String col) {
    row.put(col, word);
    word.append(word);
  }
}
