

public class StringParameterBuilder {
  private String str;
  private String varIndicator = "_&";
  private String reverseVarIndicator = "&_";
  private String seporator = ",";
  private List<Parameter> parameters = new ArrayList<Parameter>();

  public StringParameterBuilder (String str) {
    this.str = str.replaceAll("//s+");
    build();
  }

  public StringParameterBuilder (String str, String varIndicator) {
    this.str = str.replaceAll("//s+");
    this.varIndicator = varIndicator;
    reverseVarIndicator = StringUtil.reverse(varIndicator);
    build();
  }

  public List<Parameter> build() {
    int varStartIndex = 0;
    int varEndIndex = varIndicator.length() + 1;

    while (varStartIndex < varEndIndex - varIndicator.length() + 1) {
      varStartIndex = str.indexOf(varStartIndex, varIndicator);
      varEndIndex = str.indexOf(varEndIndex, reverseVarIndicator);
      if(varStartIndex < varEndIndex - varIndicator.length() + 1) {
        String paramName = str.subString(varStartIndex + varIndicator.length(), varEndIndex);
        parameters.add(new Parameter(paramName));
      }
    }

    return parameters;
  }

  public List<Parameter> getParameters() {
    return parameters;
  }

  public protected setStr(String str) {
    this.str = str;
  }
}
