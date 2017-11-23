

public class LongMap extends Map<Long> {
  public Long map(Map<String, Object> row, String key) {
    if (row == null)
      return null;
    return Long.parseLong(row.get(key));
  }
}
