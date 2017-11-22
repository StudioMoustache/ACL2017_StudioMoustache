package personnage;

import java.awt.Color;

import graphiques.AffichageSprite;
import principal.Monde;

public class Monstre extends Personnage {
	private int x;
	private int y;
	private int valeurPoint; //le nombre de point que le hero gagne en tuant le monstre
	private static int vitesse;
	private AffichageSprite sprite = new AffichageSprite(Color.RED);
	
	// ----- Constructeurs -----
	
	public Monstre(Monde monde){
		super(0, 0, 20, new AffichageSprite(Color.RED), monde);
		this.valeurPoint=10;
	}
	
	public Monstre(int x, int y, Monde monde) {
		super(x, y, 1, new AffichageSprite(Color.RED), monde);
		this.valeurPoint=10; //le nombre de point que le hero gagne en tuant le monstre est de 10 de base
	}
	
	public Monstre(int x, int y, int vitesse, Monde monde){
		super(x, y, vitesse, new AffichageSprite(Color.RED), monde);
		this.valeurPoint=10;
	}

	// ----- Fonction ------ //

	public void collisionObjectif(int x, int y) {
		monde.collisionMonstreNexus(this, x, y);
	}
	
	// ----- Getters & Setters ----- 
	
	
	public int getValeurPoint(){
		return valeurPoint;
	}
	
	public String toString(){
		return "Monstre("+this.getX()+":"+this.getY()+")";
	}
}
