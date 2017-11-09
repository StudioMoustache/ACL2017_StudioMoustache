package personnage;

import java.awt.Color;
import java.awt.Graphics2D;

import graphiques.AffichageSprite;


public class Monstre {
	private int x;
	private int y;
	private static int vitesse;

	private AffichageSprite sprite = new AffichageSprite(Color.RED);
	
	
	// ----- Constructeurs -----
	
	public Monstre(){
		this.x = 0;
		this.y = 0;
		this.vitesse = 20;
	}
	
	public Monstre(int x, int y) {
		this.x = x;
		this.y = y;
		this.vitesse = 1;
	}
	
	public Monstre(int x, int y, int vitesse){;
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
	}

	
	// ----- Fonctions -----
	
	public void deplacer(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	
	// ----- Getters & Setters ----- 
	
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
	
	public String toString(){
		return "Monstre("+this.getX()+":"+this.getY()+")";
	}

	// fonction d'affichage
	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, x, y);
	}
	
}
