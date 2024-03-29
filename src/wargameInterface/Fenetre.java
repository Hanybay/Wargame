package wargameInterface;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import wargame.Carte;
import wargame.IConfig;
import wargame.MethodesAuxiliaires;

public class Fenetre extends JFrame implements IConfig {
	public class BarreMenu extends JMenuBar {
		private static final long serialVersionUID = 1L;
		
		private JMenu partieM, affichageM, optionsM, aideM, chargerM;
		private JMenuItem nouvelle, quitter, pleinEcran, sauvegarder;
		private List<JMenuItem> sauvegardes;
		private Fenetre f;

		/**
		 * Constructeur de la barreMenu
		 * @param f
		 * 		fenetre f de barre menu
		 */
		public BarreMenu(Fenetre f) {
			super();
			this.f = f;
			sauvegardes = new ArrayList<JMenuItem>();
			
			partieM = new JMenu("Partie");
			partieM.setMnemonic(KeyEvent.VK_P);
			affichageM = new JMenu("Affichage");
			affichageM.setMnemonic(KeyEvent.VK_A);
			optionsM = new JMenu("Options");
			optionsM.setMnemonic(KeyEvent.VK_O);
			aideM = new JMenu("Aide");
			aideM.setMnemonic(KeyEvent.VK_H);
			
			nouvelle = new JMenuItem("Nouvelle partie");
			sauvegarder = new JMenuItem("Sauvgarder la partie");
			quitter = new JMenuItem("Quitter");
			pleinEcran = new JMenuItem("Plein écran");
			
			chargerM = new JMenu("Charger partie");
			sauvegardes = chercherSauvegardes();
			for (JMenuItem uneSauvegarde : sauvegardes)
				chargerM.add(uneSauvegarde);

			partieM.add(nouvelle);
			partieM.add(sauvegarder);
			partieM.add(chargerM);
			partieM.add(quitter);

			affichageM.add(pleinEcran);

			this.add(partieM);
			this.add(affichageM);
			this.add(optionsM);
			this.add(aideM);

			nouvelle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.nouvellePartie();
				}
			});
			quitter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de quitter", "Quitter", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
						f.quitter();
					}
				}
			});
			pleinEcran.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.passerPleinEcran();
				}
			});
			sauvegarder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.sauvegarderPartie();
				}
			});
			for (JMenuItem uneSauvegarde : sauvegardes)
				uneSauvegarde.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						f.chargerPartie(uneSauvegarde.getText());
					}
				});
		}
		
		/**
		 * Ajoute un JMenuItem sauvegarde à la liste
		 * @param fileName
		 * 		nom du fichier
		 */
		public void ajouterSauvegarde(String fileName) {
			JMenuItem newSave = new JMenuItem(fileName);
			newSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					f.chargerPartie(fileName);
				}
			});
			chargerM.add(newSave);
		}
		
		
		/**
		 * Renvoie une liste de boutons liés aux sauvegardes
		 * @return Les items du menu
		 */
		public List<JMenuItem> chercherSauvegardes() {
			List<JMenuItem> saves = new ArrayList<JMenuItem>();
			File folder = new File("./sauvegardes/");
			folder.mkdirs();
			for (File file : folder.listFiles()) {
				if (!file.isDirectory()) {
					saves.add(new JMenuItem(file.getName().substring(0, file.getName().length() - 4)));
					nomSauvegardes.add(file.getName().substring(0, file.getName().length() - 4));
				}
			}
			return saves;
		}
	}

	private static final long serialVersionUID = 1L;
	/**
	 * panneau de la partie
	 */
	protected PanneauPartie partie;
	
	/**
	 * Barre menu
	 */
	private BarreMenu mb;
	/**
	 * Graphics device
	 */
	private GraphicsDevice device;
	
	/**
	 * Nom de la sauvegarde
	 */
	private Set<String> nomSauvegardes;

	/**
	 * Constructeur d'une fenêtre de jeu
	 * @param name
	 * 		nom de la fenêtre du jeu
	 */
	public Fenetre(String name) {
		super(name);
		
		DialogueNouvellePartie dialogue = new DialogueNouvellePartie(this);
		if (!dialogue.showDialogue()) System.exit(0);
		
		nomSauvegardes = new LinkedHashSet<>();
		Carte carte = new Carte(null, dialogue.getLarg(), dialogue.getHaut(), dialogue.getNbH(), dialogue.getNbM());
		partie = new PanneauPartie(this, carte);
		device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		mb = new BarreMenu(this);

		this.setJMenuBar(mb);
		this.setContentPane(partie);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack();
		this.setLocationRelativeTo(null); 
		this.setVisible(true);
	}

	/**
	 * Change le panneau de la partie
	 * @param p
	 * 		panneau partie du jeu
	 */
	public void changerPartie(PanneauPartie p) {
		this.getContentPane().removeAll();
		this.invalidate();
		this.partie = p;
		this.setContentPane(partie);
		this.validate();
	}
	
	/**
	 * Lance une nouvelle partie
	 */
	public void nouvellePartie() {
		DialogueNouvellePartie dialogue = new DialogueNouvellePartie(this);
		if (!dialogue.showDialogue()) return;
		Carte carte = new Carte(null, dialogue.getLarg(), dialogue.getHaut(), dialogue.getNbH(), dialogue.getNbM());
		changerPartie(new PanneauPartie(this, carte));
	}
	
	/**
	 * Sauvegarde la partie
	 */
	public void sauvegarderPartie() {
		String nom = JOptionPane.showInputDialog(this, "Nom de la sauvegarde : "),
			   nomFichier = nom;
		while(MethodesAuxiliaires.threadVivant(partie.carte.getListeThreads()));
		FileOutputStream fichier = null;
		ObjectOutputStream output = null;
		if (nom == null) return;
		if (nom.equals("")) {
			LocalDateTime myDateObj = LocalDateTime.now();
			DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			nom = myDateObj.format(myFormatObj);
		}
		nomFichier = "./sauvegardes/" + nom + ".ser";
		try {
			fichier = new FileOutputStream(nomFichier);
			output = new ObjectOutputStream(fichier);
			output.writeObject(this.partie.carte);
			output.close();
			fichier.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		nomSauvegardes.add(nom);
		mb.ajouterSauvegarde(nom);
	}
	
	/**
	 * Charge une sauvegarde
	 * @param nom de la sauvegare
	 */
	public void chargerPartie(String nom) {
		FileInputStream fichier = null;
		ObjectInputStream lecture = null;
		Carte carte = null;
		PanneauPartie p = null;
		nom = "./sauvegardes/" + nom + ".ser";
		try {
			fichier = new FileInputStream(nom);
			lecture = new ObjectInputStream(fichier);
			carte = (Carte)lecture.readObject();
			lecture.close();
			fichier.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		catch (ClassNotFoundException ex) {
			System.out.println("La class Carte n'existe pas");
			ex.printStackTrace();
		}
		p = new PanneauPartie(this, carte);
		changerPartie(p);
	}
	/**
	 * Quitte la partie
	 */
	public void quitter() {
		this.dispose();
	}
	
	/**
	 * Passe en plein écran si c'est possible
	 */
	public void passerPleinEcran() {
		if (this.device.isFullScreenSupported()) {
			// f.setUndecorated(true);
			this.device.setFullScreenWindow(this);
			int haut = this.device.getFullScreenWindow().getHeight(),
					larg = this.device.getFullScreenWindow().getWidth();
			Carte.hauteurMap = haut - 80;
			Carte.largeurMap = larg - LARGEUR_MINI_MAP - 75;
			// Carte.recalculerMapAff();
			// partie.setDimensions();
			this.revalidate();
			this.repaint();
		}
	}
	
	/**
	 * Affiche une fenetre de dialogue pour le chargement de sauvegardes depuis le menu
	 */
	public void demanderQuelleSauvegarde() {
		String nomFichier = (String)JOptionPane.showInputDialog(this, "Quelle sauvegarde ?", "Charger une partie", 
							JOptionPane.QUESTION_MESSAGE, null, nomSauvegardes.toArray(), nomSauvegardes.iterator());
		if (nomFichier == null) return;
		chargerPartie(nomFichier);
	}
	/**
	 * Affiche la notice d'aide du jeu
	 */
	public void afficherAide() {
	}
}
