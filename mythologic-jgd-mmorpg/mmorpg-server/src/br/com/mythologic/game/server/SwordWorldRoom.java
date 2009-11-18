package br.com.mythologic.game.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;

public class SwordWorldRoom extends SwordWorldObject {
	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;
	/** The {@link Logger} for this class. */
	private static final Logger logger = Logger.getLogger(SwordWorldRoom.class
			.getName());
	/** The set of items in this room. */
	private final Set<ManagedReference<SwordWorldObject>> items = new HashSet<ManagedReference<SwordWorldObject>>();
	/** The set of players in this room. */
	private final Set<ManagedReference<SwordWorldPlayer>> players = new HashSet<ManagedReference<SwordWorldPlayer>>();

	/**
	 * Creates a new room with the given name and description, initially empty
	 * of items and players.
	 * 
	 * @param name
	 *            the name of this room
	 * @param description
	 *            a description of this room
	 */
	public SwordWorldRoom(String name, String description) {
		super(name, description);
	}

	/**
	 * Adds an item to this room.
	 * 
	 * @param item
	 *            the item to add to this room.
	 * @return {@code true} if the item was added to the room
	 */
	public boolean addItem(SwordWorldObject item) {
		logger
				.log(Level.INFO, "{0} placed in {1}",
						new Object[] { item, this });
		// NOTE: we can't directly save the item in the list, or
		// we'll end up with a local copy of the item. Instead, we
		// must save a ManagedReference to the item.
		DataManager dataManager = AppContext.getDataManager();
		dataManager.markForUpdate(this);
		return items.add(dataManager.createReference(item));
	}

	/**
	 * Adds a player to this room.
	 * 
	 * @param player
	 *            the player to add
	 * @return {@code true} if the player was added to the room
	 */
	public boolean addPlayer(SwordWorldPlayer player) {
		logger.log(Level.INFO, "{0} enters {1}", new Object[] { player, this });
		DataManager dataManager = AppContext.getDataManager();
		dataManager.markForUpdate(this);
		return players.add(dataManager.createReference(player));
	}

	/**
	 * Removes a player from this room.
	 * 
	 * @param player
	 *            the player to remove
	 * @return {@code true} if the player was in the room
	 */
	public boolean removePlayer(SwordWorldPlayer player) {
		logger.log(Level.INFO, "{0} leaves {1}", new Object[] { player, this });
		DataManager dataManager = AppContext.getDataManager();
		dataManager.markForUpdate(this);
		return players.remove(dataManager.createReference(player));
	}

	/**
	 * Returns a description of what the given player sees in this room.
	 * 
	 * @param looker
	 *            the player looking in this room
	 * @return a description of what the given player sees in this room
	 */
	public String look(SwordWorldPlayer looker) {
		logger.log(Level.INFO, "{0} looks at {1}",
				new Object[] { looker, this });
		StringBuilder output = new StringBuilder();
		output.append("You are in ").append(getDescription()).append(".\n");
		List<SwordWorldPlayer> otherPlayers = getPlayersExcluding(looker);
		if (!otherPlayers.isEmpty()) {
			output.append("Also in here are ");
			appendPrettyList(output, otherPlayers);
			output.append(".\n");
		}
		if (!items.isEmpty()) {
			output.append("On the floor you see:\n");
			for (ManagedReference<SwordWorldObject> itemRef : items) {
				SwordWorldObject item = itemRef.get();
				output.append(item.getDescription()).append('\n');
			}
		}
		return output.toString();
	}

	/**
	 * Appends the names of the {@code SwordWorldObject}s in the list to the
	 * builder, separated by commas, with an "and" before the final item.
	 * 
	 * @param builder
	 *            the {@code StringBuilder} to append to
	 * @param list
	 *            the list of items to format
	 */
	private void appendPrettyList(StringBuilder builder,List<? extends SwordWorldObject> list) {
		if (list.isEmpty()) {
			return;
		}
		int lastIndex = list.size() - 1;
		SwordWorldObject last = list.get(lastIndex);
		Iterator<? extends SwordWorldObject> it = list.subList(0, lastIndex)
				.iterator();
		if (it.hasNext()) {
			SwordWorldObject other = it.next();
			builder.append(other.getName());
			while (it.hasNext()) {
				other = it.next();
				builder.append(" ,");
				builder.append(other.getName());
			}
			builder.append(" and ");
		}
		builder.append(last.getName());
	}

	/**
	 * Returns a list of players in this room excluding the given player.
	 * 
	 * @param player
	 *            the player to exclude
	 * @return the list of players
	 */
	private List<SwordWorldPlayer> getPlayersExcluding(SwordWorldPlayer player) {
		if (players.isEmpty()) {
			return Collections.emptyList();
		}
		ArrayList<SwordWorldPlayer> otherPlayers = new ArrayList<SwordWorldPlayer>(
				players.size());
		for (ManagedReference<SwordWorldPlayer> playerRef : players) {
			SwordWorldPlayer other = playerRef.get();
			if (!player.equals(other)) {
				otherPlayers.add(other);
			}
		}
		return Collections.unmodifiableList(otherPlayers);
	}
}
