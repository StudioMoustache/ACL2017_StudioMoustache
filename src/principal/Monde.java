package principal;

import java.util.ArrayList;

import graphiques.Tick;
import personnage.Hero;
import personnage.Monstre;

public class Monde {
	private ArrayList<Monstre> lesMonstres;
	private Nexus nexus;
	private final int HAUTEUR = 50;
	private final int LARGEUR = 50;
	
	boolean perdu = false;
	
	public Monde(){
		this.lesMonstres = new ArrayList<Monstre>();
		this.nexus = new Nexus(50, 0);
		//creation d'un monstre pour le test
		Monstre m1 = new Monstre(0, 0, 1, "m1");
		Monstre m2 = new Monstre(50, 50, 1, "m2");
		lesMonstres.add(m1);
		lesMonstres.add(m2);
		
		Tick monTick = new Tick(this);
	}
	
	public void ajouterMonstre(Monstre m){
		lesMonstres.add(m);
	}
	
	public void deplacerHero(int x, int y){
		Hero.getHero1().deplacer(x, y);
	}
	
	public void deplacementMonstre2Test() {
		if(lesMonstres.size()>1) // pour eviter le crash lors de la destruction du monstre pdt le test
			lesMonstres.get(1).deplacer(0, -1);
	}
	
	public void update(){
		if(!perdu) {
			deplacementMonstre2Test();
			collisionMonstres();
			System.out.println(this.toString());
		}else {
			System.out.println("Nexus détruit. Vous avez perdu.");
			System.out.println("PERDU !!");
			System.exit(0);
		}
	}
	
	// Rassemblement du balayage des monstres en une seule fonction ne le faire qu'une fois par fonction de collision
	public void collisionMonstres() {
		// foreach impossible car conflit d'utilisation quand on delete le monstre
		for(int i = 0; i < lesMonstres.size(); i++) {
			Monstre m = lesMonstres.get(i);		
			collisionHeroMonstre(m, i);
			collisionMonstreNexus(m, i);
		}
	}
	
	public void collisionHeroMonstre(Monstre m, int i) { // volontairement simpliste pour l'instant. HitBox a prendre en compte plus tard
		if(m.getX() == Hero.getHero1().getX() && m.getY() == Hero.getHero1().getY())
			lesMonstres.remove(i);
	
	}

	public void collisionMonstreNexus(Monstre m, int i) {
		if(m.getX() == this.nexus.getX() && m.getY() == nexus.getY()) {
			lesMonstres.remove(i);
			this.perdu = true;
		}
	}
	
	public String toString(){
		String toReturn = "";	
		toReturn += nexus.toString()+"\n";
		toReturn += Hero.getHero1().toString()+"\n";
		for(Monstre m : lesMonstres)
			toReturn += m.toString()+"\n";
		return toReturn;
	}
	
	// TODO ajouterMonstre en designant un portail
	
	
	
}
