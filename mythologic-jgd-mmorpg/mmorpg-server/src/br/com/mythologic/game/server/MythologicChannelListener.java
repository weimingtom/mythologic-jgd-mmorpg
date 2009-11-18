package br.com.mythologic.game.server;

import java.io.Serializable;
import java.nio.ByteBuffer;

import br.com.mythologic.infra.logger.MythologicLogger;
import br.com.mythologic.infra.logger.MythologicSimpleLogger;

import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ChannelListener;
import com.sun.sgs.app.ClientSession;

public class MythologicChannelListener implements ChannelListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2728744545017780334L;
	
	private static final MythologicLogger logger = MythologicSimpleLogger
			.getLogger(MythologicChannelListener.class);

	@Override
	public void receivedMessage(Channel channel, ClientSession sender,
			ByteBuffer message) {

		logger.info("Channel message from {0} on channel {1}", new Object[] {
				sender.getName(), channel.getName() });
		channel.send(sender, message);
	}

}
