package br.com.mythologic.game.domain.character;

import br.com.mythologic.game.domain.character.attributes.Gender;
import br.com.mythologic.game.domain.thing.Thing;

public interface Character extends Thing {
	public String getName();
	public Gender getGender();	
	
	
}
