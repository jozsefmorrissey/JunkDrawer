${package.name}.${package.extension}

public class Query {
  String queryFormat;
  ColumnBuilder columnBuilder;
  ParameterBuilder parameterBuilder;

  public Query(String queryFormat) {
    this.queryFormat = queryFormat;
    columnBuilder = new QueryColumnBuilder(queryFormat);
    parameterBuilder = new SelectParameterBuilder(queryFormat);
  }

  public Query(String queryFormat, ColumnBuilder columnBuilder, ParameterBuilder parameterBuilder) {
    this.queryFormat = queryFormat;
    this.columnBuilder = columnBuilder;
    this.parameterBuilder = parameterBuilder;
  }

  public void build() {
    columnBuilder.build();
    parameterBuilder.build();
  }

  public List<Column> getColumns() {
    return this.columns.getColumns();
  }

  public List<Parameters> getParameters() {
    return this.parameters.getParameters();
  }
}
