/**
 *   Copyright(c) 2012 DuoKan TV Group
 *    
 *   SyncTask.java
 *
 *   @author tianli(tianli@duokan.com)
 *
 *   2012-12-13 
 */
package com.xiaomi.mitv.tvplayer.global;

/**
 * @author tianli
 *
 */
public class SyncTask implements Runnable{
	
	private Object mResult = null;
	public Task mJob;
	private boolean mIsCompleted = false;
	
	public SyncTask(Task job){
		this.mJob = job;
	}
	
	public Object runTask(int timeout){
		try{
			if(mJob != null){
				synchronized (this) {
					if(!mIsCompleted){
						new Thread(this).start();
						wait(timeout);
					}
				}
			}
		}catch(Exception e){
		}
		return mResult;
	}

	@Override
	public void run() {
		if(mJob != null){
			try{
				mResult = mJob.doIt();
			}catch(Exception e){
			}
			synchronized (this) {
				mIsCompleted = true;
				notifyAll();
			}
		}
	}
	
	public synchronized void cancel(){
		mIsCompleted = true;
		notifyAll();
	}
	
	public static interface Task{
		public Object doIt();
	}
}
