package graphiques;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import principal.Monde;

public class MultipleKeyListener implements KeyListener {
	private Monde m;
	
	private final TreeSet<Integer>pressed =new TreeSet<Integer>();
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getExtendedKeyCode() == 80) // code de p pour la pause
			m.setPause();
	}

	@Override
	public void keyPressed(KeyEvent e) {
        pressed.add(e.getExtendedKeyCode());
        //System.out.println(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		pressed.remove(e.getExtendedKeyCode());
		
	}
	
	public String toString(){
		String s="coucou";
		Iterator<Integer> it=pressed.iterator();
		while(it.hasNext()){
			s+=""+it.next();	
		}
		return s;
		
		
	}
	
	public void deplacement(){
		if(pressed.contains(37)){
			m.deplacerHero(-1, 0);
		}
		if(pressed.contains(38)){
			m.deplacerHero(0, -1);
		}
		if(pressed.contains(39)){
			m.deplacerHero(1, 0);
		}
		if(pressed.contains(40)){
			m.deplacerHero(0, 1);
		}
		if(pressed.contains(80)) { // lettre p pour la pause
			m.setPause();
		}
	}

	public void setMonde(Monde m) {
		this.m = m;
	}
	
}
