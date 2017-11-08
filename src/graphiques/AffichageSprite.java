package graphiques;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;


public class AffichageSprite extends BufferedImage {
	
	private Graphics2D sprite;

	public AffichageSprite(Color c) {
		super(10, 10, BufferedImage.TYPE_INT_ARGB);
		sprite = createGraphics();
		sprite.setPaint(c);
		sprite.fillRect(0, 0, 10, 10);
	}

	public void dessiner(Graphics g, int x, int y) {
		g.drawImage(this, x, y, null);
	}
}