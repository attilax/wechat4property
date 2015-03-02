/**
 * 
 */
package AaaCfg;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import net.sf.json.JSONObject;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.hibernate.exception.SQLGrammarException;

import com.attilax.Closure;
import com.attilax.Closure2;
import com.attilax.SafeVal;
import com.attilax.core;
import com.attilax.errEventProcess;
import com.attilax.db.DbX;
import com.attilax.designpatter.commandPkg.Command;
import com.attilax.designpatter.commandPkg.Invoker;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.ioc.IocX;
import com.attilax.orm.OrmX;
import com.attilax.persistence.Hbx;
import com.attilax.persistence.PX;

import com.attilax.text.strUtil;
import com.attilax.util.Mapx;

/**
 * @author ASIMO
 * 
 */
@RemoteProxy(name = "dwrC")
public class DwrC {

	/**
	 * @author attilax 老哇的爪子
	 * @throws IOException
	 * @since ob2 h_s_0
	 */
	public static void main(String[] args) throws IOException {
		// System.out.println(URLEncoder.encode("../programme/programmeManager!showEdit4rvw?progarmmeId=29",
		// "utf-8"));
		// PageContext pageContext = null;
		// GvProgramme pub=(GvProgramme)pageContext.getAttribute("programme");
		// JspWriter out = null;
		// out.write( ReviewX.stateFmt(pub.getRevi().getState()));
	}

	@SuppressWarnings("all")
	@RemoteMethod
	public Object execOb(final Map<String, String> m) {
		try {
			Class class1;
			// DwrCInier dcir=IocX.getBean(DwrCInier.class);
			// dcir.execute(m);

			Command.reg("orm", new Closure2() {

				@Override
				public Object execute(Object arg0) {
					Map m2 = convert2atiormMap(m);
					Object sql = new OrmX().convert2sql(core.toJsonStrO88(m2));

					Hbx hbx = AaaCfg.IocX.getBean(Hbx.class);
					try {
						List li = hbx.exe(sql);
						if (isQueryOP(m)) {
							DbX dx = AaaCfg.IocX.getBean(DbX.class);
							int count = dx.getCount(sql.toString());
							return easyuiFmtRzt(li, count);
						} else
							return li;
					} catch (SQLGrammarException e) {
						filex.saveLog(sql.toString(), "c:\\e");
						throw new RuntimeException(sql.toString(), e);
					}

					// return s;
				}
			});

			Invoker ivk = new Invoker(new Command(m.get("_meth")));
			return ivk.action();
		} catch (Exception e) {
			filex.saveLog(e, "c:\\e");

			String jsonStrO88 = core.toJsonStrO88(e);
			return jsonStrO88;
		}

	}

	/**
	 * @author attilax 老哇的爪子
	 * @since p32 l_9_f
	 */
	protected Object easyuiFmtRzt(List li, int count) {
		Map m = new HashMap();
		m.put("rows", li);
		m.put("total", count);
		return m;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since p32 l_6_d
	 */
	protected boolean isQueryOP(Map<String, String> m) {
		JSONObject metadata = JSONObject.fromObject(m.get("_metadata"));

		Object op = metadata.get("op");

		return new OrmX().isQueryOP(op);
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since p31 h_37_2
	 */
	protected Map convert2atiormMap(Object arg0) {

		Map m = (Map) arg0;
		Map m_filted_fld = filtField(m);
		JSONObject metadata = JSONObject.fromObject(m.get("_metadata"));
		Map m2 = new HashMap();
		m2.put("datatype", metadata.get("table"));
		m2.put("op", metadata.get("op"));
		m2.put("field", m_filted_fld);
		return m2;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since p31 h_43_3
	 */
	private Map filtField(Map m) {
		JSONObject metadata = JSONObject.fromObject(m.get("_metadata"));
		String table = (String) metadata.get("table");
		Connection conn = (Connection) core.retry3(new Closure() {

			@Override
			public Object execute(Object arg0) throws Exception {

				Connection conn2 = AaaCfg.IocX.getBean(Connection.class);
				boolean b = DbX.checkConn(conn2);
				if (b)
					return conn2;
				else
					throw new RuntimeException(" conn is close maybe ");
			}
		}, new errEventProcess() {

			@Override
			public Object execute(Object arg0) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		}, "c:\\getconn");

		List li = DbX.getColsList(conn, table);
		Map m2 = Mapx.grep(m, li);
		return m2;
	}

	@RemoteMethod
	@Deprecated
	public Object execOb2(String param) {
		return param;

		// Command.reg("saveReviewRzt", new Closure2() {
		//
		// @Override
		// public Object execute(Object arg0) {
		// ReviewX rx = IocX.getBean(ReviewX.class);
		// return rx.save(m);
		// }
		// });
		// Invoker ivk = new Invoker(new Command(m.get("meth")));
		// return ivk.action();

	}

	// findbyx

}
