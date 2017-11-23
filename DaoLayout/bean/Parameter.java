public class Parameter {
  String name;
  String value;

  public Parameter (String name) {
    this.name = name;
  }

  public Parameter (String name, Stirng value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }
}
