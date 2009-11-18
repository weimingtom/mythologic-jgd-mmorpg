package br.com.mythologic.game.server;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ChannelManager;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ManagedReference;

public class MythologicChannelSessionListener extends MythologicClientSessionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6015345680082123742L;

	public MythologicChannelSessionListener(ClientSession clientSession) {
		super(clientSession);
		// TODO Auto-generated constructor stub
	}
	
	public MythologicChannelSessionListener(ClientSession clientSession, ManagedReference<Channel> channel1) {
		super(clientSession);
		// Join the session to all channels. We obtain the channel
		// in two different ways, by reference and by name.
		ChannelManager channelMgr = AppContext.getChannelManager();
		// We were passed a reference to the first channel.		
		channel1.get().join(clientSession);
		// We look up the second channel by name.
		Channel channel2 = channelMgr.getChannel("a");
		channel2.join(clientSession);

	}
}
