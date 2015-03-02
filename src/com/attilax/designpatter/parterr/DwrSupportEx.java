package com.attilax.designpatter.parterr;

public class DwrSupportEx extends  Exception {
	public DwrSupportEx( ) {
		// TODO Auto-generated constructor stub
	}
	public DwrSupportEx(String message2, Throwable e) {
		// TODO Auto-generated constructor stub
	}
	public DwrSupportEx(String json) {
		super(json);
	}

}
