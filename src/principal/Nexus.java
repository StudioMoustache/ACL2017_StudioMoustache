package principal;

/**
 * 
 * L'élément à défendre à tout prix. Il perd de la vie si un monstre le touche.
 *
 */
public class Nexus {
	protected int x;
	protected int y;
	protected int vie;
	

	// ----- Constructeur -------
	public Nexus(int x, int y) {
		this.x = x;
		this.y = y;
		this.vie = 1;
	}
	
	
	// ----- Fonctions -----
	
	/**
	 * Retire la valeur degats a la vie du nexus.
	 * Utile si des "super monstres" retirent plus de vie
	 */
	public void retirerVie(int degats) {
		vie -= degats;
	}
	
	// ----- Getters & Setters
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	/**
	 * Retourne les coordonnees du nexus
	 */
	public String toString() {
		return "Nexus("+x+":"+y+")";
	}
}
