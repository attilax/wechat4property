package com.attilax.anno;

import java.lang.reflect.Field;

import com.attilax.io.filex;
import com.attilax.ref.cantFindMatchFieldException;
import com.attilax.ref.refx;
import com.focustar.elmt.GvMaterial;

public class pojoTest {

	public static void main(String[] args) throws cantFindMatchFieldException {
		
//		java.lang.String
//		class java.lang.String
//		String
//		java.lang.String
		System.out.println("aa".getClass().getName());//java.lang.String
		System.out.println("aa".getClass().toString());//java.lang.String
		System.out.println("aa".getClass().getSimpleName());//java.lang.String
		System.out.println("aa".getClass().getCanonicalName());//java.lang.String
		
		Field[] flds = GvMaterial.class.getDeclaredFields();
		Field playtime=refx.getField("playtime", GvMaterial.class);
		Conditional cdt = playtime.getAnnotation(Conditional.class);
		System.out.println(cdt);
		//t1();
	}

	private static void t1() {
		String sql=filex.read("c:\\pojo.sql","gbk");
		System.out.println(sql);
		// TODO Auto-generated method stub
	//	pojo.class.()
		Field[] flds = pojo.class.getDeclaredFields();
		for (Field field : flds) {
			System.out.println(field.getName());
			Validate vld = field.getAnnotation(Validate.class);
		  if (vld != null) {
			  	System.out.println(vld);			
				System.out.println(vld.value());
				System.out.println(vld.regexp());
			 }
		}
		System.out.println("---");
		  System.exit(0);
	}

}
