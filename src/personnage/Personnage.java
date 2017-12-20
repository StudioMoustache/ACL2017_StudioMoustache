package personnage;

import graphiques.AffichageSprite;
import principal.Monde;

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
	protected AffichageSprite sprite;

	// ------ CONSTRUCTEUR ------ // 

	public Personnage(int x, int y, int vitesse, AffichageSprite sprite, Monde monde) {
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
		this.sprite = sprite;
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

	// ----- FONCTION DE COLLISION AVEC MUR ----- //

	/**
	 * Fonction qui test pour la position courante du personnage
	 * s'il est en collision avec un mur
	 * @param  bi image contenant la carte du niveau courant
	 * @return    true s'il y a collision, false sinon
	 */
	private boolean testCollisionMur(BufferedImage bi, int directionx, int directiony) {
		boolean collision = false;
		boolean tour = false;

		int xTest = x + directionx;
		int yTest = y + directiony;

		int currentpixelX = xTest, currentpixelY = yTest;
		int signeX = 1, signeY = 0;

		// cette boucle fait le tour de la peripherie du carre du personnage pour detecter une collision
		// signeX = 1, on parcourt les X de gauche à droite
		// signeY = 1, on parcourt les Y de haut en bas (inversé avec swing)
		while (!tour && !collision) {
			// Parcours de la partie droite du carre
			if (currentpixelX == xTest + sprite.getWidth()-1 && currentpixelY == yTest) {
				signeX = 0;
				// 0,0 en haut à gauche de l'image, donc on augmente en y quand on descend
				signeY = 1;
			}

			// Parcours de la partie basse du carre
			if (currentpixelX == xTest + sprite.getWidth()-1 && currentpixelY == yTest + sprite.getHeight()-1) {
				signeX = -1;
				signeY = 0;
			}

			// Parcours de la partie gauche du carre
			if (currentpixelX == xTest && currentpixelY == yTest + sprite.getHeight()-1) {
				signeX = 0;
				signeY = -1;
			}	

			// Le pixel courant est dans une partie noire de la map = collision
			if ((0x000000FF & bi.getRGB(currentpixelX, currentpixelY)) == 0) {
				collision = true;
			} else {
				currentpixelX += signeX;
				currentpixelY += signeY;
			}

			// Le pixel courant est le pixel de depart, donc on a fait un tour, pas de collision detectee
			if (currentpixelX == xTest && currentpixelY == yTest) {
				tour = true;
			}
		}
		
		return collision;
	}

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
			collision = this.testCollisionMur(bi, directionx, directiony);

			// Appel de la fonction de verification de collision avec : 
			// - soit les monstres (pour le hero)
			// - soit le nexus (pour les monstres)
			this.collisionObjectif();

			if (!collision) { // Il n'y a pas collision, on ajoute a la position courante
							  // la direction en x et en y
				this.x += directionx;
				this.y += directiony;
			}
/*
			// On est arrivee a la position max de la trajectoire donc 
			// on ne teste plus la collision
			if (this.x == xArrivee && this.y == yArrivee) {
				collision = false;
			}

			*/
		}
	}

	// fonction d'affichage
	public void dessiner(Graphics2D g) {
		sprite.dessiner(g, this.x, this.y);
	}
}