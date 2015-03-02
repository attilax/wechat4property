package com.focustar.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

//import com.focustar.entity.TMbTextMas;
import com.focustar.util.BaseImpl;

public class MessageImpl extends BaseImpl {

	/**
	 * 获取最新的微信消息
	 * 
	 * @param sid
	 *            记录的ID值，查询记录的开始点，返回结果不包括此点。 如果不传此值，从第一条开始返回。
	 *            可以使用上次最后返回记录的ID，作为下次的参数
	 * 
	 * @return 此接口一次最多返回500条数据
	 * @throws Exception
	 */
	/*public String getList(String sid) throws Exception {
		String json = "";
		Session session = null;
		try {
			String hql = "";
			session = getSession();
			if (sid != null && !sid.equals(""))
				hql = "select TOP 500 d.ID,d.OpenID,d.NickName,d.CreateTime,d.MsgType,d.Content from T_MB_TextMas d where d.ID>" + sid;
			else
				hql = "select TOP 500 d.ID,d.OpenID,d.NickName,d.CreateTime,d.MsgType,d.Content from T_MB_TextMas d";
			Query query = session.createSQLQuery(hql);
			ArrayList<TMbTextMas> detail = new ArrayList();
			List list = query.list();
			if (list != null && list.size() > 0) {
				Iterator<Object[]> it = list.iterator();
				while (it.hasNext()) {
					Object[] obj = it.next();
					TMbTextMas det = new TMbTextMas();
					det.setId(Integer.valueOf(obj[0].toString()));
					det.setOpenId(obj[1].toString());
					if (obj[2] != null) {
						det.setNickName(new String(obj[2].toString().getBytes(), "iso-8859-1"));
					}
					if (obj[3] != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date createTime = sdf.parse(obj[3].toString());
						det.setCreatetime(createTime.getTime());
					}
					if (obj[4] != null) {
						det.setMsgType(obj[4].toString());
					}
					if (obj[5] != null) {
						det.setContent(obj[5].toString());
					}
					detail.add(det);
				}
			}
			session.close();
			if (detail != null && detail.size() > 0) {
				JSONArray jsonObject = JSONArray.fromObject(detail);
				json = jsonObject.toString();
			}
		} catch (Exception e) {
			if(session != null){
				session.close();
			}
			e.printStackTrace();
			throw new Exception("查询消息表发生错误!" + e.getMessage());
		}
		return json;
	}*/
}
