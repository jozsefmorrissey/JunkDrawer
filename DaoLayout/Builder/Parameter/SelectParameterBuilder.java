

public class SelectParameterBuilder  extends StringParameterBuilder {

  public QueryColumns (String query) {
    initialize();
  }

  public QueryParameterBuilder (String query, String varIndicator) {
        super(query, varIndicator);
        initialize();
    }

  private void initialize() {
    query = query.replaceAll("//s+");
    query = query.toUpper();
    int afterSelect = query.indexOf("SELECT" + 7);
    int startFrom = query.indexOf("FROM");
    String commaSepColumns = query.subString(afterSelect, startFrom);
    this.setStr(commaSepColumns);
    this.build();
  }
}
