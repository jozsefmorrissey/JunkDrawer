${package.name}.${package.extension}

public class QueryColumns implements Builder<Column> {
  String query;
  List<Column> columns = new ArrayList<Column>();

  public QueryColumns (String query) {
      build(query);
  }

  public QueryColumns (String query, String varIndicator) {
      String indReverse = StringUtil.reverse(varIndicaator);
      this.query = query.replaceAll("[".concat(varIndicator).concat("]*[").concat(indReverse).concat("]"), "");
      buildColumns(query);
  }

  protected List<Column> build() {
    query = query.replaceAll("//s+");
    query = query.toUpper();
    int afterSelect = query.indexOf("SELECT" + 7);
    int startFrom = query.indexOf("FROM");
    String commaSepColumns = query.subString(afterSelect, startFrom);
    String[] columArr = commaSepColumns.split(",");
    for(String columnName : columArr) {
      String[] nameValue = columnName.split(":");
      if(nameValue.length == 2)
        columns.add(new Column(nameValue[0], nameValue[1]));
      else
        columns.add(new Column(columnName));
    }
    return columns;
  }

  public List<Column> getColumns() {
    return columns;
  }
}
