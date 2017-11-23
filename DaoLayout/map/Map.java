

public abstract Map<T> {
  public List<T> map(List<Map<String, Object>> result, T key) {
    List<T> retList = new ArrayList<T>();
    for(Map<String, Object> row : result)
      retList.add(map(row))
    return retList;
  }
  public abstract T map(Map<String, Object> row, T key);
}
