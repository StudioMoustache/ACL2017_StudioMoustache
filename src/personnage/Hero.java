package personnage;

import java.awt.Color;

import graphiques.AffichageSprite;
import principal.Monde;

public class Hero extends Personnage {
	
	private static Hero hero1;
	private static Hero hero2;

	private int score;
	
	// ----- Constructeurs ------
	
	private Hero(Monde monde){
		super(0, 0, 10, new AffichageSprite(Color.BLUE), monde);
		this.score = 0;
	}
	
	private Hero(int x, int y, int vitesse, Monde monde){
		super(x, y, vitesse, new AffichageSprite(Color.BLUE), monde);
		this.score = 0;
	}

	// ----- Fonction ------ //

	// Initialisation des champs statiques, nécessaire puisque le monde est dans le constructeur
	public static void initialisationHeros(Monde monde) {
		hero1 = new Hero(250, 250, 5, monde);
		hero2 = new Hero(250, 250, 5, monde);
	}

	// Fonction expliquée dans la classe abstraite Personnage
	public void collisionObjectif(int x, int y) {
		monde.collisionHeroMonstres(this, x, y);
	}
	
	// ----- Getters & Setters ----- //
	
	public static Hero getHero1(){
		return hero1;
	}
	
	public static Hero getHero2(){
		return hero2;
	}

	/**
	 * méthode pour mettre à jour les points du héro
	 * @param point le nombre de point à ajouter au score
	 */
	public void gainPoint(int points){
		this.score+=points;
	}
	
	public int getScore(){
		return this.score;
	}

	public String toString(){
		return "Hero("+hero1.getX()+":"+hero1.getY()+")";
	}
	
}