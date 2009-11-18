package br.com.mythologic.game.client;

import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.Random;

import br.com.mythologic.infra.logger.MythologicLogger;
import br.com.mythologic.infra.logger.MythologicSimpleLogger;

import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClient;
import com.sun.sgs.client.simple.SimpleClientListener;

public class CopyOfMythologicClient implements SimpleClientListener {
	
	private static final MythologicLogger logger = MythologicSimpleLogger
	.getLogger(CopyOfMythologicClient.class);

	private SimpleClient simpleClient = new SimpleClient(this);
	
	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		String player = "guest-" + new Random().nextInt(1000);
		//setStatus("Logging in as " + player);
		String password = "guest";
		return new PasswordAuthentication(player, password.toCharArray());

	}

	@Override
	public void loggedIn() {
		logger.debug("loggedIn");
		
	}

	@Override
	public void loginFailed(String arg0) {
		logger.debug("loginFailed");
		
	}

	@Override
	public void disconnected(boolean arg0, String arg1) {
		logger.debug("disconnected");
		
	}

	@Override
	public ClientChannelListener joinedChannel(ClientChannel arg0) {
		logger.debug("joinedChannel");
		return null;
	}

	@Override
	public void receivedMessage(ByteBuffer arg0) {
		logger.debug("receivedMessage");				
	}

	@Override
	public void reconnected() {
		logger.debug("receivedMessage");
		
	}

	@Override
	public void reconnecting() {
		logger.debug("reconnecting");
		
	}

	public static void main(String[] args) throws InterruptedException {
		Properties connectProps = new Properties();
		connectProps.put("host", "localhost");
		connectProps.put("port", "62964");
		//connectProps.put("port", "1139");
		CopyOfMythologicClient client = new CopyOfMythologicClient();
		try {
			client.simpleClient.login(connectProps);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}		
	}
}
