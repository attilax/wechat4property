package com.focustar.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AwardConstant {
	//中奖角度
	public static Map<String,List<Integer[]>>   AWARD_RORATE_MAP = new TreeMap<String,List<Integer[]>>();
	
	static{
		//一等奖角度
		Integer[] angle = {301,329};
		List<Integer[]>  angleList1 = new ArrayList<Integer[]>();
		angleList1.add(angle);
		
		AWARD_RORATE_MAP.put("award1", angleList1);
		//二等奖角度
		Integer[]   angle2 = {31,59};
		List<Integer[]>  angleList2 = new ArrayList<Integer[]>();
		angleList2.add(angle2);
		
		AWARD_RORATE_MAP.put("award2", angleList2);
		
		//三等奖角度
		Integer[] angle3 = {121,149};
		List<Integer[]>  angleList3 = new ArrayList<Integer[]>();
		angleList3.add(angle3);
		
		AWARD_RORATE_MAP.put("award3", angleList3);
		
		//jeig ff addd   and recomm hamyard 
		//没有中奖角度
		Integer[] angle4 = {210,240};
		List<Integer[]>  angleList4 = new ArrayList<Integer[]>();
		angleList4.add(angle4);
		AWARD_RORATE_MAP.put("award4", angleList4);
		/*
		
		Integer[]  otherAngle1 = {0,30};
		//Integer[]   angle2 = {31,59};
		Integer[]  otherAngle2 = {60,90};
		Integer[]  otherAngle3 = {90,120};
		//Integer[] angle3 = {121,149};
		Integer[]  otherAngle4 = {150,180};
		Integer[]  otherAngle5 = {180,210};
		Integer[]  otherAngle6 = {210,240};
		Integer[]  otherAngle7 = {240,270};
		Integer[]  otherAngle8 = {270,300};
		//Integer[] angle = {301,329};
		Integer[]  otherAngle9 = {330,360};
		
		List<Integer[]>  angleList4 = new ArrayList<Integer[]>();
		angleList4.add(otherAngle1);
		angleList4.add(otherAngle2);
		angleList4.add(otherAngle3);
		angleList4.add(otherAngle4);
		angleList4.add(otherAngle5);
		angleList4.add(otherAngle6);
		angleList4.add(otherAngle7);
		angleList4.add(otherAngle8);
		angleList4.add(otherAngle9);
		AWARD_RORATE_MAP.put("award4", angleList4);*/
		
	}
	 

}
