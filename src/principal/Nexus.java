package principal;

public class Nexus {
	protected int x;
	protected int y;
	protected int vie;
	
	public Nexus(int x, int y) {
		this.x = x;
		this.y = y;
		this.vie = 1;
	}
	
	public void retirerVie(int degats) {
		vie -= degats;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public String toString() {
		return "Nexus("+x+";"+y+")";
	}
}
