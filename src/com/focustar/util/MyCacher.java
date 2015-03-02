package com.focustar.util;


import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;


public class MyCacher {
	private static MemcachedClientBuilder builder = new XMemcachedClientBuilder("127.0.0.1:11211");
	private  static MyCacher instance = new MyCacher();
	private final static int  MAX_CACHE_TIME = 30 * 24 * 60 * 60; //30天
	private final static int  DEFAULT_CACHE_TIME = 60 * 10; //默认缓存10分钟
	private static MemcachedClient mem = null;
	private static boolean isInit = false;
	
	private int tmp_cache_time = -1;
	private int tmp_update_time = -1;
	
	private Logger logger = Logger.getLogger(MyCacher.class);
	
	static{
		try {
			builder.setConnectionPoolSize(2);
			mem = builder.build();
			mem.setMergeFactor(50);
			//mem.setFailureMode(false);
			//关闭合并请求
			mem.setOptimizeMergeBuffer(false);
			mem.setEnableHeartBeat(false);
			mem.flushAll();
			isInit = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private MyCacher(){
	}
	
	public static MyCacher getInstance(){
		return instance;
	}
	
	
	public boolean putCache(String key,Object value){
		boolean flag = false;
		if(isInit){
			try {
				if(isInit){
					if(tmp_cache_time == -1){
						tmp_cache_time = DEFAULT_CACHE_TIME;
					}
					flag = mem.set(key,tmp_cache_time, value);
					logger.info("缓存 key="+key+" , value="+value+" , cache_time="+tmp_cache_time);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	public boolean updateCache(String key){
		boolean flag = false;
		try {
			if(isInit){
				if(tmp_update_time == -1){
					tmp_update_time = DEFAULT_CACHE_TIME;
				}
				flag = mem.touch(key, DEFAULT_CACHE_TIME);
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean updateCache(String key,int time){
		if(time < 0 || time > MAX_CACHE_TIME){
			time = DEFAULT_CACHE_TIME;
			logger.info(key+":最大缓存时间只能是 "+MAX_CACHE_TIME+" 秒");
		}else{
			tmp_update_time = time;
		}
		return updateCache(key);
	}
	
	public Object getCache(String key){
		try {
			if(isInit)
				return mem.get(key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean putCache(String key,Object value,int time){
		if(time < 0 || time > MAX_CACHE_TIME){
			time = DEFAULT_CACHE_TIME;
			logger.info(key+":最大缓存时间只能是 "+MAX_CACHE_TIME+" 秒");
		}else{
			tmp_cache_time = time;
		}
		return putCache(key,value);
	}
	
	public boolean removeCache(String key){
		boolean flag = false;
			try {
				if(isInit){
					flag = mem.delete(key);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (MemcachedException e) {
				e.printStackTrace();
			}
		return flag;
	}
	
	public boolean replaceCache(String key,Object obj,int seconds){
		
		boolean flag = false;
			try {
				if(isInit){
					mem.replace(key, seconds,obj);
					flag = true;
				}
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MemcachedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return flag;
	}

	public static MemcachedClient getMem() {
		return mem;
	}
	
	

}
