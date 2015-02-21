/**
 * @author attilax 老哇的爪子
	@since  o7f i45m$
 */
package com.attilax;

import com.attilax.core;
import com.attilax.collection.CollectionUtils;
import com.attilax.collection.listUtil;
import com.attilax.crud.ListPageUtil;
import com.attilax.io.filex;
import com.attilax.util.dirx;
import static com.attilax.core.*;
import java.util.*;
import java.net.*;
import java.io.*;
/** @author attilax 老哇的爪子
 * @since o7f i45m$ */
public class jsLibDirGener {

	/** @author attilax 老哇的爪子
	 * @since o7f i45m$
	 * 
	 * @param args */
	public static void main(String[] args) {
		// attilax 老哇的爪子 i45m o7f
		String s = "core,collection,anno,crud,ui,fileup,html,log,net,secury,sso,text,reg,valide,util";
		List li = listUtil.fromStr(s);
		CollectionUtils.each_safe(li, new Closure() {

			@Override public Object execute(Object arg0) {
				// attilax 老哇的爪子 h57s o7f
				String p = "D:\\workspace\\vodx\\WebRoot\\com_attilax";
				String s = p + "\\" + arg0.toString() + "\\t.htm";
				core.log(s);
				filex.createAllPath(s);

				return arg0;

			}
		});

	}
	// attilax 老哇的爪子 i45m o7f
}

// attilax 老哇的爪子