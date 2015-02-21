/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-23 下午3:01:39$
 */
package com.attilax.rails;

import java.util.Map.Entry;

import com.attilax.collection.Ireduce;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;

/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-23 下午3:01:39$
 *
 */
public class GeneO6 {

	/**
	 * @param args
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-23 下午3:01:39$
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午3:01:39   2014-6-23 

	}
	//  attilax 老哇的爪子 下午3:01:39   2014-6-23   

	/**
	 * @param templete
	 * @param target
	 * @param add
	 * @author  attilax 老哇的爪子
	 *@since  2014-6-23 下午3:17:31$
	 */
	public String gene(String templete, String target, mapImp li) {
		// attilax 老哇的爪子  下午3:17:31   2014-6-23 
		String f=filex.read(templete);
		
		//Iterator iter = li.entrySet().iterator();
		 String t= listUtil.reduceO6(li, f, new Ireduce<Entry<String,String>, String>() {

			@Override
			public String $(Entry<String,String> o, String lastRetOBj) {
				// attilax 老哇的爪子  下午4:03:40   2014-6-23 
				String key = o.getKey();
				if(key.startsWith("$"))
					key="\\"+key;
				lastRetOBj=lastRetOBj.replaceAll(key, o.getValue());
				return lastRetOBj;
			}
		});
		 filex.save(t, target);
		return t;
		
	}
}

//  attilax 老哇的爪子