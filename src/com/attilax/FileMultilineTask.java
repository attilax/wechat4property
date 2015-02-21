/**
 * 
 */
package com.attilax;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.attilax.io.filex;
import com.attilax.lbs.isIngEx;
import com.attilax.task.RunStat;
import com.attilax.ui.MsgBox;

/**
 * for file svs
 * @author ASIMO
 * 
 */
public abstract class FileMultilineTask  extends BaseTaskX {
	
	public	int linecount;

	public	int nowLine;
	public String souFile;
	public String tagFile;
	public int startline;
	 List<String> li;
	public LinkedBlockingQueue<String> rztQueue = new LinkedBlockingQueue<String>();
	
	public Object start(Object para) throws isIngEx {
		
		run();
		return para;
	}
	
	public void run() throws isIngEx {
		li = checkBefRun(souFile, tagFile);
		int n = 0;
		linecount = li.size();
		this.runState=RunStat.run;
		for (final String line : li) {
			if(this.runState.equals(RunStat.pause))
				waiting();
			if(this.runState.equals(RunStat.stop))
			{
			
				break;
				
			}
			n++;
			nowLine = n;			
			//System.out.println("now line:" + String.valueOf(n));
			if(nowLine<startline)
				continue;
			FutureTask<String> fut=new FutureTask(new Callable<String>() {

				@Override
				public String call() throws Exception {
					//addIndex, outfile, fc4norzt,
					runSingle(  line);
					return "ok";
				}
			}) ;
			core.submit(fut, "runSingle"+filex.getUUidName());
			try {
				System.out.println(fut.get(20, TimeUnit.SECONDS));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				//  fut.cancel(true);
				e.printStackTrace();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			 

		}
		//end for
		runAfterEvent();
		this.runState=RunStat.stop;

	}
	
			/**
		@author attilax 老哇的爪子
		@since   obn e_w_42
		 
		 */
	public abstract void runAfterEvent();

		/**
		@author attilax 老哇的爪子
		@since   obn e_u_9
		 
		 */
	public  abstract void runSingle(String line);

	public abstract List<String> checkBefRun(String path, String rzt) throws isIngEx;
	public String runState=RunStat.wait;
	/* (non-Javadoc)
	 * @see com.attilax.TaskX#start(java.lang.Object)
	 */
 

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
	public Object stop(Object stopAftEvent) {
		
		 this.runState=RunStat.stop;
		 Closure c=(Closure) stopAftEvent;
		 try {
			 return	c.execute(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
