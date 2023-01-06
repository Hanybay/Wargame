package wargameInterface;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;
import wargame.InfoPartie;

public class PanneauInfoPartie extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	private final InfoPartie infoPartie;
	
	// Constructeurs
	public PanneauInfoPartie(Carte carte) {
		this.infoPartie = carte.getInfoPartie();
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(Carte.LARGEUR_MAP, 110));
	}
	
	// Méthodes graphiques
	public void paintComponent(Graphics g) {
		infoPartie.seDessiner(g);
	}
}
