package principal;

import java.util.ArrayList;

import graphiques.Tick;
import personnage.Hero;
import personnage.Monstre;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;


public class Monde {
	private ArrayList<Monstre> lesMonstres;
	private ArrayList<Portail> lesPortails;
	private Nexus nexus;
	private final int HAUTEUR = 500;
	private final int LARGEUR = 500;
	
	boolean perdu = false;
	
	private int nbUpdates;

	private Rectangle arrierePlan = new Rectangle(0, 0, LARGEUR, HAUTEUR);
	
	// ----- Constructeurs -----
	
	public Monde(){
		this.lesMonstres = new ArrayList<Monstre>();
		this.lesPortails = new ArrayList<Portail>();
		nbUpdates = 0;
		
		this.nexus = new Nexus(50, 0);
		Portail p1 = new Portail(50, 400, 5, 20);
		lesPortails.add(p1);
		
		// On fait bien toutes les initialisations AVANT la creation du Tick qui va declencher les update
		
		// Tick monTick = new Tick(this);
	}
	
	
	
	// ----------------------- FONCTIONS DE INVOCATION MONSTRES ----------------------------
	
	/**
	 * On recupere le premier monstre dans le portail p
	 * On ajoute le monstre m a lesMonstres
	 * Des qu'un monstre figure dans cette liste, il est sur le terrain.
	 */
	public void invoquerMonstre(Portail p){
		if(p.getNbMonstres() > 0) {
			p.decMonstres();
			Monstre m = new Monstre(p.getX(), p.getY());
			lesMonstres.add(m);
		}
	}
	
	/**
	 * Fonction d'automatisation de l'invocation des monstres
	 */
	public void checkInvocationMonstres() {
		for(Portail p : lesPortails) {
			// si il reste des monstres au portail et si c'est l'heure pour lui d'invoquer un monstre
			if(p.getNbMonstres() > 0 && this.nbUpdates % p.getFrequence() == 0) {
				invoquerMonstre(p);
			}
				
		}
	}
	
	// ----------------------- GESTION DES DEPLACEMENTS ----------------------
	
	public void deplacerHero(int x, int y){
		Hero.getHero1().deplacer(x, y);
	}
	
	/**
	 * Fonction de deplacement des monstres
	 * TODO
	 */
	public void deplacementMonstres() {
		for(Monstre m : lesMonstres) {
			m.deplacer(0, -1);
		}
	}
	
	
	// -------------------- GESTION DES COLLISIONS --------------------
	
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
	
	
	// ----------------------------------- UPDATE ET TOSTRING --------------------
	
	// A chaque Tick (rafraichissement), on deplace les monstres et on teste les collisions
	public void update(){
		if(!perdu) {
			nbUpdates += 1;
			deplacementMonstres();
			collisionMonstres();
			checkInvocationMonstres();
			System.out.println(this.toString());
		}else {
			System.out.println("Nexus détruit. Vous avez perdu.");
			System.out.println("PERDU !!");
			System.exit(0);
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
	

	// fonction d'affichage du monde
	public void dessiner(Graphics2D g) {
		g.setBackground(Color.BLACK);
		g.fill(arrierePlan);

		nexus.dessiner(g);

		for (Portail p : lesPortails) {
			p.dessiner(g);
		}

		for (Monstre m : lesMonstres) {
			m.dessiner(g);
		}
	}
}
