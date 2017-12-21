package principal;

import java.awt.Color;
import java.awt.Graphics2D;

import graphiques.AffichageSprite;
import personnage.Monstre;

/**
 *
 * Note : les monstres ne sont pas r�ellement stockes dans un portail.
 * 		Un portail connait seulement un nombre de monstre
 * 		L'objet Monstre est instancie seulement au moment de le faire apparaitre dans le monde
 *
 * 		La frequence est la valeur modulo laquelle le portail relache un monstre.
 * 		Soit nbUpdate le nombre d'update deja effectuees sur Monde.
 * 		Ainsi, si nbUpdqte % freqence == 0, le portail rel�chera un monstre. (operation effectuee dans Monde)
 * 		Par exemple : si frequence == 4, le portail lachera un monstre tous les 4 Tick.
 */

public class Portail {
	final private static int dimensionSprite = 8;

	private int x;
	private int y;
	private int nbMonstres;
	private int frequence;

	final private AffichageSprite sprite = new AffichageSprite(Color.GRAY, dimensionSprite);

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

	/**
	 * Recharge les monstres
	 */

	public void rechargerPortail(int vague) {
		this.nbMonstres = 10+vague;
	}

	/**
	 * Invoque un monstre dans le portail courant
	 * @param  int   positionX     position en X dans le monde
	 * @param  int   positionY     position en Y dans le monde
	 * @param  Monde monde         Instance du monde dans lequel est le monstre
	 * @return       [description]
	 */
	public Monstre invoquerMonstre(int positionX, int positionY, int type, Monde monde) {
		this.nbMonstres--;
		return new Monstre(positionX, positionY, type, monde);
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
	
	public int distanceXOiseau(int nexusX){
		
		return Math.abs(this.x-nexusX);
	}
	public int distanceYOiseau(int nexusY){
		return Math.abs(this.y-nexusY);
	}
}
