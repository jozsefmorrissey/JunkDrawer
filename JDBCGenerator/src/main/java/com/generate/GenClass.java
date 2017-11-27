package com.generate;
import java.io.IOException;
import java.util.List;

import com.generate.Util.ParceJdbcXML;
import com.generate.generation.GenerateDAO;
import com.generate.generation.GenerateDAOImpl;
import com.generate.generation.GenerateDAOMap;
import com.generate.generation.GenerateDAOSetup1;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;

public class GenClass
{

	public static void main(String args[]) throws IOException, InterruptedException
	{
		List<JdbcTypeWrapper> jdbcTypes = new ParceJdbcXML().xmlParser();
//		XmlProps props = ParcePropertiesXML.xmlParser();
//				
//		for(int i = 0; i < args.length; i++) {
//			System.out.println(i + ") " + args[i]);
//		}
//		
//		
//		
//		int process = null == args || args.length > 0 ? Integer.parseInt(args[0]): 3;
			for (JdbcTypeWrapper jT : jdbcTypes)
			{
					determineOperation(0, jT);
			}
//		new GenerateDaoImplAbs(jdbcTypes.get(0), props);
//		new GenerateDAOBean(jdbcTypes.get(0));
//		new GenerateDAO(jdbcTypes.get(0), props);
//		new GenerateDAOImpl(jdbcTypes.get(0), props);

	}

	public static void determineOperation(int i, JdbcTypeWrapper jT)
	{
		try
		{
			switch (i)
			{
			case 0:
//				new GenerateDAOSetup0(jT);
				new GenerateDAOSetup1(jT);
//				new GenerateDAOSetup2(jT);
//				new GenerateDAOBean(jT);
//				new GenerateDaoImplAbs(jT);
//				new GenerateDAOMapFactory(jT);
				break;
			case 1:
				new GenerateDAO(jT);
				break;
			case 3:
				new GenerateDAOMap(jT);
				break;
			case 4:
				new GenerateDAOImpl(jT);
				break;
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
