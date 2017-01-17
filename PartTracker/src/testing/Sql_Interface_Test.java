package testing;

import static org.junit.Assert.*;

import org.junit.Test;
import database.Sql_Interface;

public class Sql_Interface_Test {

	@Test
	public void test() {
		test_create_table();
	}
	//TODO: make valid dataTypes!!!!!!!!!!!!!!
	public void test_create_table(){
		String[][] sql_arr =   {{"Name 1","Data Type 1", ""},
								{"Name 2","Data Type 2", ""},	
								{"Name 3","Data Type 3", ""},
								{"Name 4","Data Type 4", ""},
								{"Name 5","Data Type 5", ""},
								{"Name 6","Data Type 6", ""},
								{"Name 7","Data Type 7", ""},
								{"Name 8","Data Type 8","NOT NULL"}}; 
		String table_name = "table_name";
		
		database.Sql_Interface.create_table(table_name, sql_arr);
		assert(1==1);
	}

}
