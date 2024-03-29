package wargame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.Serializable;






/**
 * <b>InfoPartie regroupe toutes les informations faisant état de l'avancement de la partie</b>
 * Elle est caractérisée par :
 * <ul>
 * <li>Un variable nb tours pour le nombre de tours</li>
 * <li>Une variable char joueur qui indique si le joueur est un gentil ou un méchant</li>
 * <li>Une variable int nbHeros qui indique le nombre de héros présents dans la partie</li>
 * <li>Une variable int nbMonstre qui indique le nombre d'ennemis présents dans la partie</li>
 * </ul>
 *
 */

public class InfoPartie implements IConfig, Serializable {
	private static final long serialVersionUID = -2099696797421917005L;
	/**
	 * nombre de tours
	 */
	private int nbTours;
	
	/**
	 * Type joueur 
	 */
	private char joueur;
	
	/**
	 * nombre de heros, nombre de monstre
	 */
	private int nbHeros, nbMonstres;
	
	/**
	 * Carte de la partie
	 */
	private Carte carte;
	
	/**
	 * Constructeur InfoPartie qui initialise les caractéristiques de la classe.
	 * @param carte
	 * 		Carte du jeu
	 * @param nbHeros
	 * 		nombre de heros
	 * @param nbMonstres
	 * 		nombre de monstre
	 */
	public InfoPartie(Carte carte, int nbHeros, int nbMonstres) {
		this.nbTours = 1;
		this.joueur = GENTILS;
		this.nbHeros = nbHeros;
		this.nbMonstres = nbMonstres;
		this.carte = carte;
	}

	/**
	 * Accesseur du nombre de tours.
	 * @return int
	 */
	public int getNbTours() { return nbTours; }
	/**
	 * Accesseur du type de joueur.
	 * @return char
	 */
	public char getJoueur() { return joueur; }
	/**
	 * Accesseur du nombre de héros présents dans la partie.
	 * @return int
	 */
	public int getNbHeros() { return nbHeros; }
	/**
	 * Accesseur du nombre de monstres présents dans la partie.
	 * @return int
	 */
	public int getNbMonstres() { return nbMonstres; }

	/**
	 * Mutateur servant à modifier le nombre de tours
	 * @param nbTours
	 * 		nombre de tours
	 */
	public void setNbTours(int nbTours) { this.nbTours = nbTours; }
	/**
	 * Mutateur servabt à modifier le type du joueur 
	 * @param joueur
	 * 		type joueur
	 */
	public void setJoueur(char joueur) { this.joueur = joueur; }
	
	/**
	 * Mutateur servant à modifiant le nombre de héros présents dans la partie.
	 * @param nbHeros
	 * 		nombre de heros
	 */
	 
	public void setNbHeros(int nbHeros) { this.nbHeros = nbHeros; }
	
	/**
	 * Mutateur servant à modifier le nombre de monstres présents dans la partie.
	 * @param nbMonstres
	 * 		nombre de monstre
	 */
	public void setNbMonstres(int nbMonstres) { this.nbMonstres = nbMonstres; }
	
	/**
	 * Permet d'inscrire les infos relatifs à l'avancement dans la partie dans l'interface graphique
	 * @param g
	 * 		Graphics g
	 */
	public void seDessiner(Graphics g) {
		String nbToursS = "Nombre de tours : " + nbTours,
			   joueurS = "Joueur : " + ((joueur == GENTILS) ? "vous" : "adversaire"),
			   nbHerosS = "Nombre de héros : " + nbHeros,
			   nbMonstresS = "Nombre de Monstres : " + nbMonstres,
			   typeAttaqueS = "Type de l'attaque : " + ((carte.typeAttaque == CORPS_CORPS) ? "attaque frontale" : "attaque à distance");
		g.setFont(new Font("Courier", Font.BOLD, 13));
	    FontMetrics metrics = g.getFontMetrics(g.getFont());
		g.setColor(Color.white);
		g.drawString(nbToursS, 10, 10 * 2);
		g.drawString(joueurS, 10, 10 * 3 + metrics.getHeight() * 1);
		g.drawString(nbHerosS, 10, 10 * 4 + metrics.getHeight() * 2);
		g.drawString(nbMonstresS, 10, 10 * 5 + metrics.getHeight() * 3);
		g.drawString(typeAttaqueS, 10, 10 * 6 + metrics.getHeight() * 4);
	}
	
}
