/**
 * 
 */
package com.attilax.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import www.Hbx4shp;
//import www.attilax.goods.MallGoods;
//import www.attilax.lang.ref.RefX;


import www.attilax.car.carT;

import com.attilax.core;
//import com.attilax.acc.AccX;
import com.attilax.api.Handler;
import com.attilax.api.HandlerChain;
import com.attilax.io.pathx;
import com.attilax.lang.jsonX;
import com.attilax.lang.ref.FldMapX;
import com.attilax.lang.ref.RefX;
 
import com.attilax.net.ResponseX;
import com.attilax.net.requestImp;
import com.attilax.time.timeUtil;
import com.kunpeng.www.dao.CarDAOImp;
//import com.attilax.withdraw.Withdraw;
import com.kunpeng.www.domain.CarVo;
import com.kunpeng.www.server.AppAction;

/**
 * @author ASIMO
 *
 */
public class ApiInier {

		/**
		@author attilax 老哇的爪子
		@since   p1q d_40_37
		 
		 */
	public void ini() {
		HandlerChain.mp.put("addCar",  new Handler() {

			@Override
			public Object handleReq(Object arg) throws Exception {
				JSONObject jo=JSONObject.fromObject(arg.toString());
				CarVo car=new CarVo();
				Map fldMpper=	FldMapX.frmTxt(pathx.classPath(carT.class)+"/carMap.txt");
				System.out.println(core.toJsonStrO88(fldMpper));
				RefX.copyProperties(jo, car,fldMpper);	 
				CarDAOImp cdc=new CarDAOImp();
						cdc.save(car);

				// System.out.println(core.toJsonStrO88(g));
				 // g.setShopId(jo.getInt("shop_id"));
			//  Hbx4shp.getStance().save(g);
				return "";
			}});
		
		
		HandlerChain.reg("SendReg".toLowerCase(),  new Handler(){

			@Override
			public Object handleReq(Object arg) throws Exception {
				
				JSONObject jo=JSONObject.fromObject(arg);
				requestImp ri=new requestImp();
				 
				ri.setParam("username", jsonX.get(jo,"cp"));
				ri.setParam("password", jsonX.get(jo,"pwd"));
				ri.setParam("nickname", jsonX.get(jo,"username"));
				AppAction aa=new AppAction(ri, null);
				aa.doSendReg_ex();
				return "";
			}});
		

		HandlerChain.reg("SendLogin".toLowerCase(),  new Handler(){

			@Override
			public Object handleReq(Object arg) throws Exception {
				
				JSONObject jo=JSONObject.fromObject(arg);
				requestImp ri=new requestImp();
				 
				ri.setParam("username", jsonX.get(jo,"username"));
				ri.setParam("password", jsonX.get(jo,"pwd"));
			 //	ri.setParam("nickname", jsonX.get(jo,"username"));
			 	ri.setParam("u_password", jsonX.get(jo,"pwd"));
			 	
			 	
				AppAction aa=new AppAction(ri, null);
				aa.doLogin_ex();
				return "";
			}});
		
		
//		
//		HandlerChain.reg("draw",  new Handler(){
//
//			@Override
//			public Object handleReq(Object arg) throws Exception {
//				JSONObject jo=JSONObject.fromObject(arg);
//				Map  FldmapTable=new HashMap();
//				FldmapTable.put("alipay","acc");
//				Withdraw o=new Withdraw();
//				RefX.copyProperties(jo, o);
//				o.setAddtime(timeUtil.getTimestamp());
//				FldMapX.process(jo,o,FldmapTable);
//				  Hbx4shp.getStance().save(o);
//				return "";
//			}});
//		
//		HandlerChain.reg("recharge",  new Handler(){
//
//			@Override
//			public Object handleReq(Object arg) throws Exception {
//				JSONObject jo = null;
//				try {
//					  jo=JSONObject.fromObject(arg);
//				} catch (Exception e) {
//				 	 throw new RuntimeException("json fmt err",e);
//				}
//		 
//			
//				int uid=jo.getInt("uid");
//				double amt=jo.getDouble("amount");
//				AccX ac=IocX.getBean(AccX.class);
//				ac.recharge(uid, amt);
//			List li=	ac.findByUid(uid);
//				return core.toJsonStrO88(li);
//			}});
		
//		HandlerChain.reg("amount",  new Handler(){
//
//			@Override
//			public Object handleReq(Object arg) throws Exception {
//				JSONObject jo=JSONObject.fromObject(arg);
//				int uid=jo.getInt("uid");
//				AccX ac=IocX.getBean(AccX.class);
//			List li=	ac.findByUid(uid);
//				return  core.toJsonStrO88(li);
//			}});
//		
 	Set<String> keyset=HandlerChain.mp.keySet();
		for (String ky : keyset) {
			String unikey=ky.toLowerCase().trim();
			if(HandlerChain.mp.get(unikey)==null )
				HandlerChain.reg(unikey,(Handler) HandlerChain.mp.get(ky));
		}
		
	}

}
