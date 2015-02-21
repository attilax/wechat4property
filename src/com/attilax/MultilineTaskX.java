/**
 * 
 */
package com.attilax;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.attilax.lbs.isIngEx;
import com.attilax.task.RunStat;

/**
 * @author ASIMO
 * 
 */
public class MultilineTaskX  extends BaseTaskX {
	
	public	int linecount;

	public	int nowLine;
	
	public LinkedBlockingQueue<String> rztQueue = new LinkedBlockingQueue<String>();
	
	private List<String> checkBefRun(String path, String rzt) throws isIngEx
	{
		return null;
		
	}
	public String runState=RunStat.wait;
	/* (non-Javadoc)
	 * @see com.attilax.TaskX#start(java.lang.Object)
	 */
	@Override
	public Object start(Object para) {
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
	public void waiting() {
		while (true) {
			if (this.runState.equals(RunStat.pause))
				core.sleep_safe(3000);
			break;
		}

	}

}
