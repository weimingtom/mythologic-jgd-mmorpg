package br.com.mythologic.infra.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MythologicSimpleLogger implements MythologicLogger {
	
	private final Logger logger;
	
	@SuppressWarnings("unchecked")
	private MythologicSimpleLogger (Class classToLog){
		logger = Logger.getLogger(classToLog.getName());
	}	
	
	/* (non-Javadoc)
	 * @see br.com.mythologic.infra.logger.MythologicLogger#info(java.lang.String)
	 */
	public void info (String message){
		logger.log(Level.INFO, message);
	}
	
	/* (non-Javadoc)
	 * @see br.com.mythologic.infra.logger.MythologicLogger#debug(java.lang.String)
	 */
	public void debug (String message){
		logger.log(Level.INFO, message);
	}
		
	@SuppressWarnings("unchecked")
	public static MythologicLogger getLogger (Class classToLog){
		return new MythologicSimpleLogger(classToLog);		
	}

	@Override
	public void debug(String message, Object[] messageParammeters) {
		logger.log(Level.INFO, message, messageParammeters);
	}

	@Override
	public void debug(String message, Object messageParammeter) {
		logger.log(Level.INFO, message, messageParammeter);
		
	}

	@Override
	public void info(String message, Object[] messageParammeters) {
		logger.log(Level.INFO, message, messageParammeters);		
	}
}
