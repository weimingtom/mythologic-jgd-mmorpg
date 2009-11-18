package br.com.mythologic.game.server;

import java.io.Serializable;
import java.nio.ByteBuffer;

import br.com.mythologic.infra.logger.MythologicLogger;
import br.com.mythologic.infra.logger.MythologicSimpleLogger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedReference;

public class MythologicClientSessionListener implements ClientSessionListener,
		Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4468108838138262516L;

	private static final MythologicLogger logger = MythologicSimpleLogger
	.getLogger(MythologicClientSessionListener.class);
	
	private final ManagedReference<ClientSession> clientSessionRef;
	private final String clientSessionName;

	public MythologicClientSessionListener(ClientSession clientSession) {
		if (clientSession == null) {
			throw new NullPointerException("null client session");
		} else {
			clientSessionRef = AppContext.getDataManager().createReference(clientSession);
			clientSessionName = clientSession.getName();
		}		
	}
	
	
	

	@Override
	public void disconnected(boolean graceful) {
		String grace = graceful ? "graceful" : "forced";
		logger.debug(
		           "User {0} has logged out {1}",
		           new Object[] { clientSessionName, grace }
		);
	}

	@Override
	public void receivedMessage(ByteBuffer message) {
		logger.debug("Message from {0}", clientSessionName);
		getClientSession().send(message);
		
	}




	public ClientSession getClientSession() {
		return clientSessionRef.get();
	}




	public String getClientSessionName() {
		return clientSessionName;
	}

}
