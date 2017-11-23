

public class Columns {
  String name;
  String type;

  public Column(String name) {
    this.name = name;
  }

  public Column (String name, Stirng type) {
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }
}
