package br.com.mythologic.game.server.timer;

import java.io.Serializable;

import br.com.mythologic.infra.logger.MythologicLogger;
import br.com.mythologic.infra.logger.MythologicSimpleLogger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.Task;

public class MythologicSimpleTask implements Task, ManagedObject, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1753871792240844786L;
	
	private static final MythologicLogger logger = MythologicSimpleLogger.getLogger(MythologicSimpleTask.class);
			

	private MythologicSimpleTask(){}
	
	@Override
	public void run() throws Exception {		
		logger.debug("run");
		AppContext.getDataManager().markForUpdate(this);
		long timestamp = System.currentTimeMillis();
		logger.debug("MythologicSimpleTask: task running at {0,number,#}",
				new Object[] {timestamp} );	
	}
	
	
	public static MythologicSimpleTask createSimpleTask() {
		MythologicSimpleTask timedTask = new MythologicSimpleTask();
		return timedTask;
	}
	
	

}
