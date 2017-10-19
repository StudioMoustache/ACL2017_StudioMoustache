package personnage;

public class Monstre {
	private int x;
	private int y;
	private static int vitesse;
	
	public Monstre(){
		this.x = 0;
		this.y = 0;
		this.vitesse = 1;
	}
	
	public Monstre(int x, int y, int vitesse){
		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
	}

	
	public void deplacer(int x, int y){ // a changer peut etre
		this.x += x;
		this.y += y;
	}
	
	
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
	
	
}
