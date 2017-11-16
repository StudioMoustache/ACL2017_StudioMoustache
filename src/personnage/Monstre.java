package personnage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
		this.vitesse = 1;
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

	public boolean testCollisionMur(BufferedImage bi, int deplacementx, int deplacementy) {

		boolean collision = false;
		boolean tour = false;

		int nouveauX = x+deplacementx;
		int nouveauY = y+deplacementy;

		int currentpixelX = nouveauX, currentpixelY = nouveauY;
		int signeX = 1, signeY = 0;

		while (!tour && !collision) {
			if (currentpixelX == nouveauX + sprite.getWidth()-1 && currentpixelY == nouveauY) {
				signeX = 0;
				// 0,0 en haut à gauche de l'image, donc on augmente en y quand on descend
				signeY = 1;
			}

			if (currentpixelX == nouveauX + sprite.getWidth()-1 && currentpixelY == nouveauY + sprite.getHeight()-1) {
				signeX = -1;
				signeY = 0;
			}

			if (currentpixelX == nouveauX && currentpixelY == nouveauY + sprite.getHeight()-1) {
				signeX = 0;
				signeY = -1;
			}

			if ((0x000000FF & bi.getRGB(currentpixelX, currentpixelY)) == 0) {
				collision = true;
			}

			currentpixelX += signeX;
			currentpixelY += signeY;

			if (currentpixelX == nouveauX && currentpixelY == nouveauY) {
				tour = true;
			}
		}
		
		return collision;
	}
	
}
