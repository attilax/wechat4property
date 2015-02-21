/**
 * @author attilax 老哇的爪子
	@since  2014年5月11日 下午5:30:42$
	
	
 */
package com.focustar.share;

import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.attilax.core;
import com.attilax.io.filex;
import com.attilax.io.pathx;
import com.attilax.net.HttpRequest;
import com.attilax.net.websitex;
import com.attilax.qrcode.qrcodex;
import com.focustar.entity.Share;
import com.focustar.thread.TokenThread;
import com.focustar.util.BaseImpl;

/**
 * @author  attilax 老哇的爪子
 *@since  2014年5月11日 下午5:30:42$
 */
public class sharex extends BaseImpl {

	/**
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午5:30:42$
	
	 */
	public sharex() {
		// TODO Auto-generated constructor stub
	}

	//  attilax 老哇的爪子 下午5:30:42   2014年5月11日   

	/**
	@author attilax 老哇的爪子
		@since  2014年5月11日 下午5:30:42$
	
	 * @param args
	 */
	public static void main(String[] args) {
		// attilax 老哇的爪子  下午5:30:42   2014年5月11日 
		sharex c=new sharex();
	  c.gene("atioid");
	  System.out.println("--");
	}
	
	protected int addShare(String uid) {
		// 下午02:24:17 2014-4-28
		Session session = getSession();
		Transaction tx = session.beginTransaction();
	Share o=new Share();
	o.setOpenid(uid);
	 
		session.save(o);
		tx.commit();
		return o.getId();

	}
	
	public   String gene(String openid) {
	int shareid=	addShare(openid);
		String qrcodedir = "qrcodeO5/"+filex.getUUidName()+".jpg";
	
		String path=pathx.webAppPath() + "/" + qrcodedir;
		filex.createAllPath(path);
		core.log("--qrcode path:"+path);
	// attilax 老哇的爪子  下午5:30:42   2014年5月11日 
	  qrcodex. gene(String.valueOf(shareid), path, 250, 250);
	  return qrcodedir;
	}
	
	public String geneQrcodeFromWechat(String openid)
	{
		if(openid==null)
		{
			core.warn("--openid is null o510:");
			return "";
		}
		int shareid=	addShare(openid);
		String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+TokenThread.accTok.getToken();
		String postData="{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+String.valueOf(shareid)+"}}}";
	core.log("-url:"+url);
	core.log("-postdata:"+postData);
	
		String webTxt=HttpRequest.sendPost(url, postData);
		core.log("--return webtxt:"+webTxt);
		JSONObject jo=JSONObject.fromObject(webTxt);
		String tik=jo.getString("ticket");
		String tik2=URLEncoder.encode(tik);
		return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+tik2;
		
	}
	
	 
}

//  attilax 老哇的爪子