package graphiques;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;


public class AffichageSprite extends BufferedImage {
	
	private Graphics2D sprite;

	private int width = 10;
	private int height = 10;

	public AffichageSprite(Color c) {
		super(10, 10, BufferedImage.TYPE_INT_ARGB);
		// création de l'instance de Graphics2D
		sprite = createGraphics();
		// Ajout de la couleur de dessin
		sprite.setPaint(c);
		// Dessin d'un rectangle dans l'instance de Graphics2D
		sprite.fillRect(0, 0, width, height);
	}

	public AffichageSprite(Color c,int dimensionSprite) {
		super(dimensionSprite, dimensionSprite, BufferedImage.TYPE_INT_ARGB);
		this.width = this.height = dimensionSprite;
		sprite = createGraphics();
		sprite.setPaint(c);
		sprite.fillRect(0, 0, width, height);
	}

	public void dessiner(Graphics g, int x, int y) {
		g.drawImage(this, x, y, null);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}