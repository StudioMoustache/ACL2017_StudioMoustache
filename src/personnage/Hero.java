package personnage;

import java.awt.Color;

import graphiques.AffichageSprite;
import principal.Monde;

public class Hero extends Personnage {

	private int score;
	
	// ----- Constructeurs ------
	
	public Hero(Monde monde){
		super(0, 0, 10, new AffichageSprite(Color.BLUE), monde);
		this.score = 0;
	}
	
	public Hero(int x, int y, int vitesse, Monde monde){
		super(x, y, vitesse, new AffichageSprite(Color.BLUE), monde);
		this.score = 0;
	}

	// ----- Fonction ------ //

	// Fonction expliquee dans la classe abstraite Personnage
	public void collisionObjectif() {
		monde.collisionHeroMonstres(this);
	}

	/**
	 * methode pour mettre a jour les points du hero
	 * @param point le nombre de point a ajouter au score
	 */
	public void gainPoint(int points){
		this.score+=points;
	}
	
	public int getScore(){
		return this.score;
	}

	public String toString(){
		return "Hero("+x+":"+y+")";
	}
	
}