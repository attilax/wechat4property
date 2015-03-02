/**
 * 
 */
package com.attilax.designpatter.commandPkg;

import com.attilax.Closure;
import com.attilax.Closure2;
import com.attilax.api.HandlerChain;
import com.focustar.downtask.GvDownloadTaskSvs;
import com.focustar.listener.cmd_vod;

/**
 * @author ASIMO
 *
 */
public class t {

	/**
	@author attilax 老哇的爪子
	@since   ob2 m_t_56
	 
	 */
	public static void main(String[] args) {
		Command.reg("aa",  new Closure2 () {

			@Override
			public Object execute(Object arg0) {
				// TODO Auto-generated method stub
				return "hh";
			}
		});
		Invoker ivk=new Invoker(new Command("aa"));
	 Object t=	ivk.action();
	 System.out.println(t);

	}

}
