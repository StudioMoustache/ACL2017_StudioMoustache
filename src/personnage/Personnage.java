package personnage;

import graphiques.AffichageSprite;
import principal.Monde;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Personnage {
	protected int x;
	protected int y;
	protected int vitesse;

	// Un personnage connait le monde dans lequel il se trouve
	// Et celui-ci ne peut changer d'instance
	final protected Monde monde;

	// Classe permettant de dessiner le personnage
	final protected AffichageSprite sprite;

	// ------ CONSTRUCTEUR ------ //

	public Personnage(int x, int y, int vitesse, int type, Monde monde) {
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
		this.sprite = this.setSprite(type);
		this.monde = monde;
	}

	// ------ GETTERS & SETTERS ------ //

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

	public AffichageSprite setSprite(int type){
		AffichageSprite sprite = null;
		switch(type){
			case 1 :	sprite = new AffichageSprite(Color.BLUE,9); break;
			case 2 : 	sprite = new AffichageSprite(Color.ORANGE,8); break;
			case 3 : 	sprite = new AffichageSprite(Color.RED,10); break;
		}
		return sprite;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

	// ----- FONCTION ABSTRAITE ----- //

	/**
	 * appelle la fonction de collision avec l'objectif du personnage
	 * pour verifier la collision avec son objectif
	 * pour un hero : collision avec les monstres
	 * pour un monstre : collision avec le(s) nexus
	 */
	public abstract void collisionObjectif();

	/**
	 * Fonction qui parcourt la trajectoire du personnage pour verifier
	 * s'il y a collision à chaque incrementation de sa position
	 * @param bi           image contenant la carte du niveau courant
	 * @param directionx direction selon l'axe des abscisses
	 * @param directiony direction selon l'axe des ordonnées
	 */
	public void deplacementTrajectoire(BufferedImage bi, int directionx, int directiony) {
		boolean collision = false;
		int xArrivee = this.x + (directionx*this.vitesse);
		int yArrivee = this.y + (directiony*this.vitesse);

		while (!collision && (x != xArrivee || y != yArrivee)) {
			// Appel de la fonction de verification de collision avec les murs
			collision = monde.testCollisionMur(x, y, directionx, directiony, sprite.getWidth());

			// Appel de la fonction de verification de collision avec :
			// - soit les monstres (pour le hero)
			// - soit le nexus (pour les monstres)
			this.collisionObjectif();

			if (!collision) { // Il n'y a pas collision, on ajoute a la position courante
							  // la direction en x et en y
				this.x += directionx;
				this.y += directiony;
			}
		}
	}

	// fonction d'affichage
	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, this.x, this.y);
	}
}
