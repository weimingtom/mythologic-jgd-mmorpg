package br.com.mythologic.game.server;

import java.io.Serializable;
import java.util.Properties;

import br.com.mythologic.infra.logger.MythologicLogger;
import br.com.mythologic.infra.logger.MythologicSimpleLogger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;

public class MythologicServer implements AppListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3309662096769026515L;

	private static final MythologicLogger logger = MythologicSimpleLogger
			.getLogger(MythologicServer.class);

	
	private ManagedReference<SwordWorldRoom> roomRef = null;
	
	@Override
	public void initialize(Properties properties) {
		logger.debug("initialize");
		// Create the Room
		SwordWorldRoom room =
		    new SwordWorldRoom("Plain Room", "a nondescript room");
		// Create the Sword
		SwordWorldObject sword =
		    new SwordWorldObject("Shiny Sword", "a shiny sword.");
		// Put the Sword to the Room
		room.addItem(sword);
		// Keep a reference to the Room
		setRoom(room);
		logger.info("SwordWorld Initialized");


	}

	public SwordWorldRoom getRoom() {
	    if (roomRef == null)
	        return null;
	    return roomRef.get();
	}

	
	private void setRoom(SwordWorldRoom room) {
		DataManager dataManager = AppContext.getDataManager();
		if (room == null) {
		    roomRef = null;
		    return;
		}
		roomRef = dataManager.createReference(room);
	}

	@Override
	public ClientSessionListener loggedIn(ClientSession session) {
		logger.debug("loggedIn");
		logger.debug("User {0} logged in", new Object[] {session.getName()});
		// Delegate to a factory method on SwordWorldPlayer,
		// since player management really belongs in that class.
		SwordWorldPlayer player = SwordWorldPlayer.loggedIn(session);
		// Put player in room
		player.enter(getRoom());
		// return player object as listener to this client session
		return player;

	}

}
