package com.attilax.lang;

import java.util.HashMap;
import java.util.Map;

import com.focustar.programme.entity.GvProgramme;

public class objConvert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GvProgramme p=new GvProgramme();
		p.setDescribe("descxxxx");
		Map map=new HashMap();
		//map = core.copyPropertiesO8q(map, p);
		//core.copyProperties(map, p);
	 	map =core.toMap(p);
		 core.print_wzFmt(map);
		 

	}

}
