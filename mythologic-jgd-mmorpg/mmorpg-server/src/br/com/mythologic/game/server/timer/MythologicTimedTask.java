package br.com.mythologic.game.server.timer;

import java.io.Serializable;

import br.com.mythologic.infra.logger.MythologicLogger;
import br.com.mythologic.infra.logger.MythologicSimpleLogger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;

public class MythologicTimedTask implements Task, ManagedObject, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7397745493634352452L;

	private static final MythologicLogger logger = MythologicSimpleLogger.getLogger(MythologicTimedTask.class);
	
	/** The delay before the first run of the task. */
	public static final int DELAY_MS = 5000;
	/** The time to wait before repeating the task. */
	public static final int PERIOD_MS = 500;
	
	/** The timestamp when this task was last run. */
	private static long lastTimestamp = System.currentTimeMillis();
	
	private ManagedReference<MythologicSimpleTask> subTaskRef = null;

	private MythologicTimedTask(){}
	
	@Override
	public void run() throws Exception {		
		logger.debug("run");		
		AppContext.getDataManager().markForUpdate(this);
		if (subTaskRef != null) {
			getSubTask().run();
		}
		long timestamp = System.currentTimeMillis();				
		long delta = timestamp - lastTimestamp;
		lastTimestamp = timestamp;		
		logger.debug("MythologicTimer: task running at {0,number,#} delta = {1,number,#}",
				new Object[] {lastTimestamp, delta} );	
	}
	
	public MythologicSimpleTask getSubTask() {
		if (subTaskRef == null) {
			return null;
		} else {
			return subTaskRef.get();
		}
	}

	public void setSubTask(MythologicSimpleTask subTask) {
		if (subTask == null) {
			subTaskRef = null;
		} else {
			subTaskRef = AppContext.getDataManager().createReference(subTask); 
		}		
	}
	
	public static MythologicTimedTask createTimedTask() {
		MythologicTimedTask timedTask = new MythologicTimedTask();
		AppContext.getTaskManager().schedulePeriodicTask(
				timedTask, MythologicTimedTask.DELAY_MS, MythologicTimedTask.PERIOD_MS);
		return timedTask;
	}
}
