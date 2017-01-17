package database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sql_Interface {
	
	static String table_of_tables = "DATA_TABLES";
	
	static String[] ALL= {"*"};

	public static void main(String args[]){
		connect();
	      String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                  "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
		List<Object> props = new ArrayList<Object>(){{
			add(new String ("ID"));
			add(new Integer(56));
			add(new String ("NAME"));
			add(new String("Paul"));
			add(new String ("AGE"));
			add(new Integer(32));
			add(new String ("ADDRESS"));
			add(new String("California"));
			add(new String ("SALARY"));
			add(new Double(20000.00));
		}};

		//insert_into_table("COMPANY", props); 
		create_table_of_tables();
		//insert_into_table();
		//fetch_from_table();
		//update_table_data();
		//delete_table_data();

	}
	
	public static void connect(){
		    Connection c = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:test.db");
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    System.out.println("Opened database successfully");
	}
	
	public static ResultSet retrieve_table_info(String[] elements){		
		return fetch_from_table(elements, table_of_tables);				
	}
	
	public static void create_table_of_tables(){
		//format name of table and elements of table seperated by 
		String[][] elem_arr =    {{"NAME","TEXT","NOT NULL"},
				    				{"ELEMENTS","TEXT","NOT NULL"}};
	      
		create_table(table_of_tables, elem_arr);
	}
	
	public static void update_table_of_tables(String new_table_name, String[] names, String[] types, String[] null_allowed){
		String name_str = join_by_comma(names);
		System.out.println(names[0]);
		System.out.println(names[1]);
		List<Object> elements = new ArrayList<Object>(){{
			add(new String ("NAME"));
			add(new_table_name);
			add(new String ("ELEMENTS"));
			add(name_str);
		}};
		
		insert_into_table(table_of_tables, elements);
	}
	
	/**
	 * I choose to send the different attributes or elements of the table as a 2 dimentional array
	 * to simplify parsing to maintain a table of tables and attributes and to confine sql string
	 * creation.
	 * @param table_name
	 * @param elements example format: String[][] sql_arr =  {{"Name 1","Data Type 1", ""},
	    		  				    						  {"Name 2","Data Type 2","NOT NULL"}}; 
	 */
	public static void create_table(String table_name, String[][] elements){
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      /*		  
	      		"CREATE TABLE TABLE_LIST"
	      		
	      		
	      		  "CEATE TABLE INVENTORY"+
	    		  "(ID INT PRIMARY KEY      NOT NULL" +
	    		  "CATAGORY       TEXT     NOT NULL" +
	    		  "TYPE           TEXT     NOT NULL" +
	    		  "LOCATION       TEXT     NOT NULL" +
	    		  "QUANTITY       INT      NOT NULL" +
	    		  "CONTAINER_SIZE CHAR(50) NOT NULL)";
	    		  */

	      String sql = "CREATE TABLE " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT";
	      String[] names = new String[elements.length], types = new String[elements.length], null_allowed = new String[elements.length]; 
	      for(int i = 0; i < elements.length; i++){
	    	  sql += ",";
	    	  names[i] = elements[i][0];
	    	  types[i]  = elements[i][1];
	    	  null_allowed[i] = elements[i][2];
	    	  for(int j = 0; j<elements[0].length; j++){
	    			  System.out.println(elements[i][j]);
	    			  sql += " " + elements[i][j];
	    	  }

	      }

	      sql += ")";
	      System.out.println(sql);

	      stmt.execute(sql);
	      if(table_name != table_of_tables)
	    	  update_table_of_tables(table_name, names, types, null_allowed);
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	


	public static void insert_into_table(String table_name, List<Object> properties){
		List<Object> list = new ArrayList<Object>();
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      
	      String prop_str = string_manipulation.create_properties_values_string(properties);
	      	      
	      String sql = "INSERT INTO " + table_name + " " + prop_str + ";";
	      System.out.println(sql);

	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Records created successfully");
	}
	
	public static ResultSet fetch_from_table(String[] elements_arr, String table_name){
		String elements = join_by_comma(elements_arr);
		Connection c = null;
	    Statement stmt = null;
	    ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT " + elements + " FROM " + table_name + ";" );
	      /*while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "AGE = " + age );
	         System.out.println( "ADDRESS = " + address );
	         System.out.println( "SALARY = " + salary );
	         System.out.println();
	      }*/
	      //rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    return rs;
	}
	
	public static void update_table_data(){
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "UPDATE COMPANY set SALARY = 25000.00 where ID=1;";
	      stmt.executeUpdate(sql);
	      c.commit();

	      print_table_data(stmt);
	      
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	}
	
	public static void delete_table_data(){
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:test.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      String sql = "DELETE from COMPANY where ID=2;";
	      stmt.executeUpdate(sql);
	      c.commit();

	      print_table_data(stmt);
	     
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	}
	
	public static void print_table_data(Statement stmt){
		try{
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "AGE = " + age );
	         System.out.println( "ADDRESS = " + address );
	         System.out.println( "SALARY = " + salary );
	         System.out.println();
	      }
	      rs.close();
		}
		catch(Exception e){
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		}
		
	}
	
	public static String join_by_comma(String[] str){
		return String.join(",", str);
	}
}
