/**
 * @author attilax 老哇的爪子
	@since  2014-9-2 上午01:04:38$
 */
package com.attilax.db;
import com.attilax.core;
 import static  com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
/**
 * @author  attilax 老哇的爪子
 *@since  2014-9-2 上午01:04:38$
 */
public class DbxMybatis  extends DBX{
	//  attilax 老哇的爪子 上午01:04:38   2014-9-2   
	public List<Map> execSql(String sql)   
	{
		  String resource = "com/attilax/db/ibatiascfg.xml";
          Reader reader;


          try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			//  attilax 老哇的爪子 上午01:07:27   2014-9-2   
			e.printStackTrace();
			core.log(e);
			throw new CantFindCfgFileEx("CantFindCfgFileEx");
		}


          SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                  .build(reader);
          SqlSession sqlSession = sqlSessionFactory.openSession();
 
          
          List<Map> li2=    sqlSession.selectList("findRecords",sql);
       //   System.out.println(core.toJsonStrO88(li2));
	return li2;
	}
}

//  attilax 老哇的爪子