package com.focustar.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.focustar.dao.impl.GroupImpl;
import com.focustar.entity.TMbGroup;

public class TestServlet extends HttpServlet {
	
	//public static List<TMbGroup> groupList = new ArrayList<TMbGroup>();
	
	private static GroupImpl groupDao = new GroupImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*GroupImpl groupDao = new GroupImpl();
		List<TMbGroup>  groupList = groupDao.selectGroups();
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		resp.setContentType("application/json");
		
		PrintWriter out = resp.getWriter();
		
		String json = JSONObject.toJSONString(groupList,SerializerFeature.DisableCircularReferenceDetect);
		
		out.print(json);
		
		out.flush();
		
		out.close();*/
		
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		/*req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String groupid = req.getParameter("groupid");
		String groupname = req.getParameter("groupname");
		String remark = req.getParameter("remark");
		String parentIdStr = req.getParameter("parentId");
		String latitudeStr = req.getParameter("latitude");
		String longitudeStr = req.getParameter("longitude");
		
		System.out.println(groupid);
		System.out.println(groupname);
		System.out.println(remark);
		System.out.println(parentIdStr);
		System.out.println(latitudeStr);
		System.out.println(longitudeStr);
		
		if(groupid != null && groupname != null && remark != null && parentIdStr != null && latitudeStr != null && longitudeStr != null){
		
			TMbGroup group = new TMbGroup();
			group.setGroupid(Integer.parseInt(groupid));
			group.setGroupname(groupname);
			group.setRemark(remark);
			group.setLatitude(Double.parseDouble(latitudeStr));
			group.setLongitude(Double.parseDouble(longitudeStr));
			if(parentIdStr != null){
				TMbGroup parent = new TMbGroup();
				parent.setGroupid(Integer.parseInt(parentIdStr));
				group.setParent(parent);
			}
			group.setCreatetime(new Date());
			
			addGroup(group);
		
		}
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		
		Map<String,String> result = new TreeMap<String,String>();
		result.put("result", "ok");
		String resStr = JSONObject.toJSONString(result);
		out.print(resStr);
		
		out.flush();
		
		out.close();*/
		
		
	}
	
	public static void addGroup(TMbGroup upGroup){
		
		//groupList.add(upGroup);
		
		//System.out.println("容量  >> "+groupList.size());
		
		//if(groupList.size() >= 100){
			
			
			
			List<TMbGroup> glist = new ArrayList<TMbGroup>();
			glist.add(upGroup);
			groupDao.updateGroup(glist);
				
			
			
			
		//}
		
	}
	

}
