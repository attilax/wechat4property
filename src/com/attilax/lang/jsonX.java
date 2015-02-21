/**
 * 
 */
package com.attilax.lang;

import java.util.Map;

import net.sf.json.JSONObject;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;


//import com.attilax.Stream.Mapx;
//import com.attilax.cc.datex;
import com.attilax.io.filex;
import com.attilax.text.strUtil;
import com.attilax.time.dateTimeX;
import com.attilax.time.timeUtil;
import com.attilax.util.DateUtil;
//import com.focustar.elmt.GvMaterial;

/**
 * @author ASIMO
 *
 */
public class jsonX {

	/**
	@author attilax 老哇的爪子
	 * @throws Exception 
	@since   oaq 10_44_45
	 
	 */
	public static void main(String[] args) throws Exception {
		
//		System.out.println(timeUtil.Now_CST());
//	//	javax.servlet.ServletException
//		//	Map<String,String> m =Mapx.<String,String>$().add("equipmentIds", "2756,").add("publish.startTime", "2014-11-27 09:15:59").add("publish.endTime", "2014-11-28 09:20:02").toMap();
//		//  int eqs[] = {2756} ;     
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GvMaterial.class); 
//		SimpleExpression gt1 = Restrictions.gt("failureTime", DateUtil.timestampO9("2014-11-27 09:15:59"));
//	//	detachedCriteria.add(gt1);
//		SimpleExpression lt1 = Restrictions.lt("failureTime", DateUtil.timestampO9("2014-11-28 09:20:02"));
//		Criterion date1exp=Restrictions.and(gt1,lt1);
//		Criterion date2exp=(Criterion) new Closure () {
//
//			@Override
//			public Object execute(Object arg0) throws Exception {
//				
//				SimpleExpression lt1 = Restrictions.gt("failureTime", DateUtil.timestampO9("2014-11-27 09:15:59"));
//				SimpleExpression gt1 = Restrictions.lt("failureTime", DateUtil.timestampO9("2014-11-28 09:20:02"));
//				return Restrictions.and(gt1,lt1);
//			}
//		}.execute(null);
//				
//		
//	//	detachedCriteria.add(lt1);
//		detachedCriteria.add(Restrictions.or(date1exp,date2exp));
//		detachedCriteria.add(Restrictions.in ("equipmentIds", new Integer[]{2765}));
////		　　Criteria criteria = detachedCriteria.getExecutableCriteria(session);
////		　　　return criteria.list(); 
//		System.out.println(detachedCriteria.toString());
//		filex.saveLog(detachedCriteria.toString(), "c:\\queryCritia");

	}

	public static Object get(JSONObject jo, String string) {
		Object obj;
		obj=jo.get(string);
		if(obj==null)
			obj= jo.get(strUtil.UpHeadStr(string));

		if(obj==null)
			throw new ReqParamIsNull("attilax:: req param cant be empty:",string);
		return obj;
	}

}
