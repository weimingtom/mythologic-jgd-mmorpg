package br.com.mythologic.game.domain.thing.wear.bag;

import java.util.Collection;

import br.com.mythologic.game.domain.thing.wear.Wearable;

public interface Bag extends Wearable, Baggable {
	/**
	 * Add an item to the bag
	 * @param item
	 * @return how much space left in the bag
	 */
	public int add (Baggable item);
	
	/**
	 * Remove an item from the bag
	 * @param item
	 * @return how much space left in the bag
	 */
	public int remove (Baggable item);
		
	/**
	 * All the content of the bag
	 * @return content of the bag
	 */
	public Collection<Baggable> content();
	
}