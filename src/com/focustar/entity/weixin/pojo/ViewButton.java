package com.focustar.entity.weixin.pojo;

/** 
 * view类型的菜单
 * key值是用于判断用户点击了哪个click类型的菜单项。而view类型的菜单没有key属性，
 * 目前无法在公众账号后台判断是否有用户点击了view类型的菜单项，也就没办法知道哪个用户点击了view类型的菜单项
 * author:vincente  2013-11-5 
 */
public class ViewButton extends Button {
	
	private String type;//菜单的响应动作类型，目前有click、view两种类型  ,这里用的是view
	
	private String url;//网页链接，用户点击菜单可打开链接，不超过256字节
	   
	 
	/**
	@author attilax 老哇的爪子
		@since  2014-4-25 下午12:24:32$
	
	 * @param string
	 */
	public ViewButton(String string) {
		string=string.trim();
		String[] a=string.split(",");
		this.setName(a[0].trim());
		this.setType(a[1].trim());
		this.setUrl(a[2].trim());
	}
	/**
	@author attilax 老哇的爪子
		@since  2014-4-25 下午12:26:43$
	
	 */
	public ViewButton() {
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public String getUrl() {
		return url;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUrl(String url) {
		this.url = url;
	}  
}
