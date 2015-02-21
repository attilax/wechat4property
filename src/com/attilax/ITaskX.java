/**
 * 
 */
package com.attilax;

import com.attilax.lbs.isIngEx;

/**
 * @author ASIMO
 *
 */
public interface ITaskX {
	
	public Object start(Object para) throws isIngEx ;
	public Object pause(Object para);
	public Object resume(Object para);
	public Object stop(Object para);
//	public Object  run()

}
