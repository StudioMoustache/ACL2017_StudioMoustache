package personnage;

import java.awt.Color;

import graphiques.AffichageSprite;


public class Monstre extends Personnage {
	
	// ----- Constructeurs -----
	
	public Monstre(){
		super(0, 0, 20, new AffichageSprite(Color.RED));
	}
	
	public Monstre(int x, int y) {
		super(x, y, 1, new AffichageSprite(Color.RED));
	}
	
	public Monstre(int x, int y, int vitesse){
		super(x, y, vitesse, new AffichageSprite(Color.RED));
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
}
