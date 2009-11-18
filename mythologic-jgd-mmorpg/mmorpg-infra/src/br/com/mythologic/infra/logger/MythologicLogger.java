package br.com.mythologic.infra.logger;

public interface MythologicLogger {

	public abstract void info(String message);

	public abstract void debug(String message);
	
	public abstract void debug(String message, Object[] messageParammeters);
	
	public abstract void debug(String message, Object messageParammeter);

	public abstract void info(String string, Object[] objects);

}