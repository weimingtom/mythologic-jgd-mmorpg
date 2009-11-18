package br.com.mythologic.game.domain.character;

import br.com.mythologic.game.domain.character.attributes.Gender;
import br.com.mythologic.game.domain.thing.Thing;

/**
 * Character
 *
 */
public interface Character extends Thing {
	/**
	 * Get the character gender
	 * @return the character gender
	 */
	public Gender getGender();
}
