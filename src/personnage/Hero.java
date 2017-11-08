package personnage;

import java.awt.Color;
import java.awt.Graphics2D;

import graphiques.AffichageSprite;

public class Hero {
	private int x;
	private int y;
	private static int vitesse;
	
	private static Hero hero1 = new Hero(250,250,1);
	private static Hero hero2 = new Hero(250,250,1);

	private AffichageSprite sprite = new AffichageSprite(Color.BLUE);
	
	// ----- Constructeurs ------
	
	private Hero(){
		this.x = 0;
		this.y = 0;
		this.vitesse = 1;
	}
	
	private Hero(int x, int y, int vitesse){
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
	}
	
	
	// ----- Fonctions -----
	
	public void deplacer(int x, int y){ // a changer peut etre
		this.x += x;
		this.y += y;
	}
	
	// ----- Getters & Setters
	
	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public static Hero getHero1(){
		return hero1;
	}
	
	public static Hero getHero2(){
		return hero2;
	}
	
	public String toString(){
		return "Hero("+hero1.getX()+":"+hero1.getY()+")";
	}

	// fonction d'affichage
	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, x, y);
	}
}
