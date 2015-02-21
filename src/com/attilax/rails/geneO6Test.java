/**
 *  * @author  attilax 老哇的爪子
 *@since  2014-6-23 下午3:02:05$
 */
package com.attilax.rails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.attilax.collection.Fun_4reduce;
import com.attilax.collection.Ireduce;
import com.attilax.collection.list;
import com.attilax.collection.listUtil;
import com.attilax.io.filex;
import com.attilax.text.strUtil;
import com.attilax.util.Funcx;

import antlr.collections.List;

import m.coll.hashmapAti;
import m.numpkg.numUtil;

/**
 * * @author attilax 老哇的爪子
 * 
 * @since 2014-6-23 下午3:02:05$
 * 
 */
public class geneO6Test {

	/**
	 * @param args
	 * @author attilax 老哇的爪子
	 * @since 2014-6-23 下午3:02:05$
	 */
	@SuppressWarnings("all")
	public static void main(String[] args) {
		// attilax 老哇的爪子 下午3:02:05 2014-6-23
//		list li = new list<Object>(13);
//		System.out.println(li.size());
//		Map m = new HashMap();

		String templete = "E:\\workspace\\GialenWeiXin\\WebRoot\\mobile\\gridTemp.htm";
		String target = "E:\\workspace\\GialenWeiXin\\WebRoot\\mobile\\grid.htm";
		GeneO6 c = new GeneO6();
		String varPlacedStr = new foreachProcessor() {

			@Override
			public String $(Object t) {
				// attilax 老哇的爪子 下午3:13:19 2014-6-23
				String templete = "E:\\workspace\\GialenWeiXin\\WebRoot\\mobile\\gridTempForitem.htm";
				final String f=filex.read(templete);
				list<Integer> li = new list<Integer>(12);

				return listUtil.reduceO6(li.toList(), "",
						new Ireduce<Integer, String>() {

							@Override
							public String $(Integer o, String lastRetOBj) {
								// attilax 老哇的爪子 下午3:48:10 2014-6-23
								String curTxt=f;							
								curTxt=curTxt.replaceAll("\\${idx_pad}", com.attilax.util.numUtil.pad0(o,2));
								curTxt=curTxt.replaceAll("\\${idx}", o.toString());
								return lastRetOBj+curTxt;
							}

						});
			}

		}.$("");
		c.gene(templete, target, new mapImp<String, String>().add(
				"$foreach", varPlacedStr));
		System.out.println("----f");

	}
	// attilax 老哇的爪子 下午3:02:05 2014-6-23
}

// attilax 老哇的爪子