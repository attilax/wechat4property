package com.attilax.biz.award;

import com.attilax.db.Hbx;
import com.focustar.entity.ActAward;
import com.focustar.entity.ActAward;
import com.focustar.entity.Activity;
import com.focustar.util.BaseImpl;

public class awdT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Activity act=new Activity();
		act.setType("1");
		act.setJoinCount(5);
		
		 BaseImpl px=new BaseImpl();
	    
		Hbx hbx=new Hbx();
		hbx.session= px.getSession();
		
	    hbx.save(act);
	     ActAward awd=new ActAward();
	     awd.setAwardCount(9);
	     awd.setActivityId(act.getId());
	     awd.setRate(1);
	     if(act.getId()!=null)
	   hbx.save(awd);
	     System.out.println("---");

	}

}
