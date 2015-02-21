/**
 * 
 */
package com.attilax;

import com.attilax.lbs.isIngEx;
import com.attilax.task.RunStat;

/**
 * @author ASIMO
 * 
 */
public class BaseTaskX implements ITaskX {
	public String runState=RunStat.wait;
	/* (non-Javadoc)
	 * @see com.attilax.TaskX#start(java.lang.Object)
	 */
	@Override
	public Object start(Object para) throws isIngEx {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.attilax.TaskX#pause(java.lang.Object)
	 */
	@Override
	public Object pause(Object para) {
		 this.runState=RunStat.pause;
		return null;
	}

	/* (non-Javadoc)
	 * @see com.attilax.TaskX#stop(java.lang.Object)
	 */
	@Override
	public Object stop(Object para) {
		 this.runState=RunStat.stop;
		return null;
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since obn e_1_i
	 */
	private void waiting() {
		while (true) {
			if (this.runState.equals(RunStat.pause))
				core.sleep_safe(3000);
			break;
		}

	}

	/* (non-Javadoc)
	 * @see com.attilax.ITaskX#resume(java.lang.Object)
	 */
	@Override
	public Object resume(Object para) {
		// TODO Auto-generated method stub
		return null;
	}

}
