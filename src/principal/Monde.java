package principal;

import java.util.ArrayList;

import personnage.Hero;
import personnage.Monstre;

public class Monde {
	private ArrayList<Monstre> lesMonstres;
	private final int HAUTEUR = 50;
	private final int LARGEUR = 50;
	
	
	public Monde(){
		this.lesMonstres = new ArrayList<Monstre>();
	}
	
	public void ajouterMonstre(Monstre m){
		lesMonstres.add(m);
	}
	
	public void deplacerHero(int x, int y){
		Hero.getHero1().deplacer(x, y);
	}
	
	public void update(){
		
	}
	
	public String toString(){
		return Hero.getHero1().toString();
	}
	
	// TODO ajouterMonstre en designant un portail
	
	
	
}
