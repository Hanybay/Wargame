package wargameInterface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import wargame.Carte;
import wargame.IConfig;

public class PanneauMiniMap extends JPanel implements IConfig {
	private static final long serialVersionUID = 1L;
	// Infos
	public final Carte CARTE;
	
	/**
	 * Constructeur du panneau de la mini-map
	 * @param carte
	 */
	public PanneauMiniMap(Carte carte) {
		super();
		this.CARTE = carte;
		this.setBackground(COULEUR_VIDE);
		this.setPreferredSize(new Dimension(carte.getLargMM(), CARTE.getHautMM()));
	}
		
	/**
	 * Methode paintcomponent responsable du dessin graphique
	 * Graphics g
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		CARTE.seDessinerMM(g2d);
	}
}
