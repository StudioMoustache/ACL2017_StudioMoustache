package principal;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;

import graphiques.AffichageSprite;

/**
 * 
 * Note : les monstres ne sont pas réellement stockes dans un portail. 
 * 		Un portail connait seulement un nombre de monstre
 * 		L'objet Monstre est instancie seulement au moment de le faire apparaitre dans le monde
 * 
 * 		La frequence est la valeur modulo laquelle le portail relache un monstre.
 * 		Soit nbUpdate le nombre d'update deja effectuees sur Monde.
 * 		Ainsi, si nbUpdqte % freqence == 0, le portail relâchera un monstre. (operation effectuee dans Monde)
 * 		Par exemple : si frequence == 4, le portail lachera un monstre tous les 4 Tick.
 */

public class Portail {
	private int x;
	private int y;
	private int nbMonstres;
	private int frequence;
	
	private AffichageSprite sprite = new AffichageSprite(Color.WHITE);

	/**
	 * Constructeurs sans frequence
	 * Un monstre apparaitra a chaque update (= a chaque declenchement de Tick)
	 */
	public Portail(int x, int y, int nbMonstres) {
		this.x = x;
		this.y = y;
		this.nbMonstres = nbMonstres;
		this.frequence = 1;
	}
	
	public Portail(int x, int y, int nbMonstres, int frequence) {
		this.x = x;
		this.y = y;
		this.nbMonstres = nbMonstres;
		this.frequence = frequence;
	}
	
	
	
	// ----- Fonctions -----
	
	
	/**
	 * Decremente le nombre de monstres
	 */
	public void decMonstres() {
		this.nbMonstres -= 1;
	}
	
	
	// ------ Getters & Setters
	
	public int getNbMonstres() {
		return this.nbMonstres;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getFrequence() {
		return this.frequence;
	}

	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, x, y);
	}
}
