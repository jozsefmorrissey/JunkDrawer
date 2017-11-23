

public class StringMap extends Map<String> {
  public String map(Map<String, Object> row, String key) {
    Object obj = row.get(key);
    if(obj != null)
      return obj.toString();
    return null;
  }
}
