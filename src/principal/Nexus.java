package principal;

import graphiques.AffichageSprite;
import java.awt.Color;
import java.awt.Graphics2D;


/**
 *
 * L'element a defendre a tout prix. Il perd de la vie si un monstre le touche.
 *
 */
public class Nexus {
	protected int x;
	protected int y;
	protected int vie;

	final private AffichageSprite sprite = new AffichageSprite(Color.GREEN);


	// ----- Constructeur -------
	public Nexus(int x, int y) {
		this.x = x;
		this.y = y;
		this.vie = 3;
	}


	// ----- Fonctions -----

	/**
	 * Retire la valeur degats a la vie du nexus.
	 * Utile si des "super monstres" retirent plus de vie
	 */
	public void retirerVie(int degats) {
		vie -= degats;
	}

	/**
	 * Permet de savoir si le nexus a encore des points de vie
	 * @return true si il a encore des points de vie, false sinon
	 */
	public boolean estVivant() {
		return vie > 0;
	}

	// ----- Getters & Setters

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

	public int getVie(){
		return vie;
	}

	/**
	 * Retourne les coordonnees du nexus
	 */
	public String toString() {
		return "Nexus("+x+":"+y+")";
	}

	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, x, y);
	}
}
