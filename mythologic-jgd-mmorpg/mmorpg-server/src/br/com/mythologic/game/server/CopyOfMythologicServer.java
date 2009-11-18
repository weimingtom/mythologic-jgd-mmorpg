package br.com.mythologic.game.server;

import java.io.Serializable;
import java.util.Properties;

import br.com.mythologic.infra.logger.MythologicLogger;
import br.com.mythologic.infra.logger.MythologicSimpleLogger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ChannelManager;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.Delivery;
import com.sun.sgs.app.ManagedReference;

public class CopyOfMythologicServer implements AppListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3309662096769026515L;

	private static final MythologicLogger logger = MythologicSimpleLogger
			.getLogger(CopyOfMythologicServer.class);

	/* The name of the first channel {@value #CHANNEL_1_NAME} */
	static final String CHANNEL_1_NAME = "Foo";
	/* The name of the second channel {@value #CHANNEL_2_NAME} */
	static final String CHANNEL_2_NAME = "Bar";
	/**
	 * The first {@link Channel}. The second channel is looked up
	 * by name.
	 */
	private ManagedReference<Channel> channel1 = null;
	
	@Override
	public void initialize(Properties properties) {
		logger.debug("initialize");
		//MythologicTimedTask timedTask = MythologicTimedTask.createTimedTask();
		//timedTask.setSubTask(MythologicSimpleTask.createSimpleTask());
		//logger.debug("Created task: {0}", new Object[] { timedTask });
		ChannelManager channelMgr = AppContext.getChannelManager();
		// Create and keep a reference to the first channel.
		Channel c1 = channelMgr.createChannel(CHANNEL_1_NAME,
		                                      null,
		                                      Delivery.RELIABLE);
		channel1 = AppContext.getDataManager().createReference(c1);
		// We don't keep a reference to the second channel, to demonstrate
		// looking it up by name when needed. Also, this channel uses a
		// {@link ChannelListener} to filter messages.
		channelMgr.createChannel(CHANNEL_2_NAME,
		                         new MythologicChannelListener(),
		                         Delivery.RELIABLE);

	}

	@Override
	public ClientSessionListener loggedIn(ClientSession clientSession) {
		logger.debug("loggedIn");
		logger.debug("User {0} logged in", new Object[] {clientSession.getName()});
		//return new MythologicClientSessionListener(clientSession);
		return new MythologicChannelSessionListener(clientSession, channel1);
	}

}
