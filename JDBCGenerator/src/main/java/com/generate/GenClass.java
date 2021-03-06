package com.generate;
import java.io.IOException;
import java.util.List;

import com.generate.Util.ParceJdbcXML;
import com.generate.generation.GenerateDao;
import com.generate.generation.GenerateBean;
import com.generate.generation.GenerateDaoImpl;
import com.generate.generation.GenerateMap;
import com.generate.generation.GenerateMapFactory;
import com.generate.generation.GenerateSetup0;
import com.generate.generation.GenerateSetup1;
import com.generate.generation.GenerateSetup2;
import com.generate.generation.GenerateDaoImplAbs;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;

public class GenClass
{

	public static void main(String args[]) throws IOException, InterruptedException
	{
		List<JdbcTypeWrapper> jdbcTypes = new ParceJdbcXML().xmlParser();

		int process = null == args || args.length > 0 ? Integer.parseInt(args[0]) : 1;
			for (JdbcTypeWrapper jT : jdbcTypes)
			{
				if(process == 0)
					defineClasses(jT);
				if(process == 1)
					generateClasses(jT);
			}
	}
	
	public static void defineClasses(JdbcTypeWrapper jT)
	{

		try
		{
			new GenerateSetup0(jT).define();
			new GenerateBean(jT).define();
			new GenerateDaoImplAbs(jT).define();
			new GenerateSetup1(jT).define();
			new GenerateSetup2(jT).define();
			new GenerateDao(jT).define();
			new GenerateMap(jT).define();
			new GenerateMapFactory(jT).define();
			new GenerateDaoImpl(jT).define();
			new GenerateMap(jT).define();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void generateClasses(JdbcTypeWrapper jT)
	{
		try
		{
			new GenerateSetup0(jT).generate();
			new GenerateBean(jT).generate();
			new GenerateDaoImplAbs(jT).generate();
			new GenerateSetup1(jT).generate();
			new GenerateSetup2(jT).generate();
			new GenerateDao(jT).generate();
			new GenerateMap(jT).generate();
			new GenerateMapFactory(jT).generate();
			new GenerateDaoImpl(jT).generate();
			new GenerateMap(jT).generate();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
