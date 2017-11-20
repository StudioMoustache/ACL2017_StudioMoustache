package personnage;

import java.awt.Color;

import graphiques.AffichageSprite;

public class Hero extends Personnage {
	
	private static Hero hero1 = new Hero(250,250, 10);
	private static Hero hero2 = new Hero(250,250, 10);
	
	// ----- Constructeurs ------
	
	private Hero(){
		super(0, 0, 10, new AffichageSprite(Color.BLUE));
	}
	
	private Hero(int x, int y, int vitesse){
		super(x, y, vitesse, new AffichageSprite(Color.BLUE));
	}
	
	// ----- Getters & Setters ----- //
	
	public static Hero getHero1(){
		return hero1;
	}
	
	public static Hero getHero2(){
		return hero2;
	}
	
	public String toString(){
		return "Hero("+hero1.getX()+":"+hero1.getY()+")";
	}
}