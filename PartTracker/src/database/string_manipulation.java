package database;
import java.util.List;

public class string_manipulation {
	public static String implode_array(String divisor, List<Object> array){
		return implode_array(divisor, array, false);
	}
	
	public static String implode_array_indicate_text(String divisor, List<Object> array){
		return implode_array(divisor, array, true);
	}
	
	private static String implode_array(String divisor, List<Object> array, boolean indicate_text){
	      String ret_str = "(";
	      for(int index = 0; index < array.size(); index++){
	    	  if(index != 0)
	    		  ret_str += divisor;
	    	  Object obj = array.get(index);
	    	  if(!indicate_text || ((obj instanceof Integer || obj instanceof Double || obj instanceof Float || obj instanceof Long) && indicate_text))
	    		  ret_str += obj;
	    	  else
	    		  ret_str += "'" + obj + "'";
	      }
	      ret_str += ")";
	      return ret_str;
	}
	
	public static String create_properties_values_string(List<Object> array){
		String properties = "(", values = "(";
		for(int index = 0; index < array.size(); index +=2){
	    	  if(index != 0)
	    		  properties += ",";
    		  properties += array.get(index);

		}
		properties += ")";
		for(int index = 1; index < array.size(); index +=2){
	    	  if(index != 1)
	    		  values += ",";
	    	  Object obj = array.get(index);
	    	  if(obj instanceof Integer || obj instanceof Double || obj instanceof Float || obj instanceof Long)
	    		  values += obj;
	    	  else
	    		  values += "'" + obj + "'";
		}
		values += ")";
		
		return properties + " VALUES " + values;
	}
}
