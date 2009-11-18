package br.com.mythologic.game.domain.player;

public interface Player {

	/**
	 * @param name the name to set
	 */
	public abstract void setName(String name);

	/**
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * @param email the email to set
	 */
	public abstract void setEmail(String email);

	/**
	 * @return the email
	 */
	public abstract String getEmail();

}