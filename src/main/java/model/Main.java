package model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;

public class Main {

	public static void main(String[] args) {

		String titre = "Dépenses du mois";

		/******************* Jeu de données *****************************/

		/*** Dans un premier temps nous avions utilisé des ArrayList personnalisés ***/

		//ArrayList<Triplet> lt = new ArrayList<Triplet>();
		// Triplet e = new Triplet ("Loyer", 200, "Pour mamie");
		// lt.add(e);

		// Triplet f = new Triplet ("Nourriture", 150, "Pour estomac");
		// lt.add(f);

		// Triplet g = new Triplet ("Vetements", 20, "Pour mon corps");
		// lt.add(g);
		//
		// Triplet h = new Triplet ("Chachou", 60, "Pour le gros chachou");
		// lt.add(h);

		/*** Dans un second nous avons utilisé un tableau d'objets (utile pour les tables) ****/

		Object [][] lt =  {
				{"Loyer", 200f, "Mamie"},
				{"Nourriture", 150f, "Estomac"},
				{"Vetements", 20f, "Corps"},
				{"Croquettes", 60f, "Chachou"},
				{"Medecin", 60f, "Santé"}
		};


		//Instanciation du modèle
		final TIC_Model m = new TIC_Model(titre, lt);
		m.setTotalModel();

		//Instanciation de la vue
		TIC_View v = new TIC_View();
		v.setTIC_Model(m);

		// Instantiation du controlleur
		TIC_Controller c = new TIC_Controller(m, v);
		v.setTIC_Controller(c);

		// Création de l'application
		final JFrame fenetre = new JFrame();

		// Affectation du titre
		fenetre.setTitle("Camembert Interactif Application");

		// Taille
		fenetre.setPreferredSize(new Dimension(900, 600));

		// Affectation de l'opération à effectuer lors de la fermeture de la fenêtre
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Ajout d'un layout pour gérer plusieurs espaces dans la fenêtre
		fenetre.setLayout(new BorderLayout());

		// Table personnalisée pour représenter le modèle m

		DefaultTableModel model = new DefaultTableModel();

		final TableCamembert table = new TableCamembert(model, m);

		table.setModelToModel(model);

		// Ajout de cette table à la fenêtre d'application
		fenetre.getContentPane().add(table, BorderLayout.WEST);

		// Options d'édition
		table.setCellSelectionEnabled(true);		
		ListSelectionModel cellSelectionModel = table.getSelectionModel();		
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Listener pour édition de la table
		CellEditorListener ChangeNotification = new CellEditorListener() {
			public void editingCanceled(ChangeEvent e) {
				System.out.println("The user canceled editing.");
			}
			public void editingStopped(ChangeEvent e) {
				int row = table.getSelectedRow();
				int col = table.getSelectedColumn();
				m.setCellTableau(row, col, table.getValueAt(row, col));
			}
		};
		table.getDefaultEditor(Object.class).addCellEditorListener(ChangeNotification);

		// Bouton pour ajout d'une donnée
		JButton addData = new JButton();
		addData.setText("Add");
		addData.setPreferredSize(new Dimension(70,0));
		fenetre.getContentPane().add(addData, BorderLayout.EAST);
		// Listener pour ajout
		addData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Object t [] = {"Nom", 20f, "Description"};
				m.addValeur(t);
				fenetre.validate();
				table.revalidate();
			}
		});

		// Bouton pour changer le titre
		JButton changeTitre = new JButton();
		changeTitre.setText("Je veux changer le titre");
		fenetre.getContentPane().add(changeTitre, BorderLayout.NORTH);
		// Listener pour changement titre
		changeTitre.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				final JTextField newTitre = new JTextField();
				// Ajout d'une boite de dialogue
				fenetre.getContentPane().add(newTitre, BorderLayout.SOUTH);
				fenetre.validate();
				// Listener de cette boite
				newTitre.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						// Récupération du texte entré
						m.setTitre(newTitre.getText());
						// On retire la boite de dialogue
						fenetre.getContentPane().remove(newTitre);
						fenetre.validate();
					}
				});
			}
		});

		// Ajout de la vue à la fenêtre
		fenetre.getContentPane().add(v, BorderLayout.CENTER);

		// Ajout des patrons user
		m.addObserver(v);
		m.addObserver(table);

		// Pour rendre les composants bien visibles
		fenetre.pack();
		fenetre.setVisible(true);
	}
}
