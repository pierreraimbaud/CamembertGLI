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

		//ArrayList<Triplet> lt = new ArrayList<Triplet>();
		Object [][] lt =  {
				{"Loyer", 200f, "Mamie"},
				{"Nourriture", 150f, "Estomac"},
				{"Vetements", 20f, "Corps"},
				{"Croquettes", 60f, "Chachou"},
				{"Medecin", 60f, "Santé"}
		};

		// Triplet e = new Triplet ("Loyer", 200, "Pour mamie");
		// lt.add(e);

		// Triplet f = new Triplet ("Nourriture", 150, "Pour estomac");
		// lt.add(f);

		// Triplet g = new Triplet ("Vetements", 20, "Pour mon corps");
		// lt.add(g);
		//
		// Triplet h = new Triplet ("Chachou", 60, "Pour le gros chachou");
		// lt.add(h);

		final TIC_Model m = new TIC_Model(titre, lt);
		m.setTotalModel();

		// création de l'application
		final JFrame fenetre = new JFrame();

		// affectation du titre
		fenetre.setTitle("Camembert Interactif Application");

		// affectation de l'opération à effectuer lors de la fermeture de la fenêtre
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// taille et position
		fenetre.setPreferredSize(new Dimension(900, 600));

		TIC_View v = new TIC_View();
		v.setTIC_Model(m);

		fenetre.setLayout(new BorderLayout());

		//ArrayList<Triplet> l = m.getTableau();
		//String[] columnNames = { "titre", "valeur", "description"};
		//final JTable table = new JTable(m.getTableau(), columnNames);
		DefaultTableModel model = new DefaultTableModel(); 
		final TableCamembert table = new TableCamembert(model, m);
		table.setModelToModel(model);
		fenetre.getContentPane().add(table, BorderLayout.WEST);
		table.setCellSelectionEnabled(true);
		ListSelectionModel cellSelectionModel = table.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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

		JButton addData = new JButton();
		addData.setText("Add");
		addData.setPreferredSize(new Dimension(70,0));
		fenetre.getContentPane().add(addData, BorderLayout.EAST);
		addData.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Object t [] = {"Nom", 20f, "Description"};
				m.addValeur(t);
				fenetre.validate();
				table.revalidate();
			}
		});

		JButton changeTitre = new JButton();
		changeTitre.setText("Je veux changer le titre");
		fenetre.getContentPane().add(changeTitre, BorderLayout.NORTH);

		changeTitre.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				final JTextField newTitre = new JTextField();
				fenetre.getContentPane().add(newTitre, BorderLayout.SOUTH);
				fenetre.validate();

				newTitre.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						m.setTitre(newTitre.getText());
						fenetre.getContentPane().remove(newTitre);
						fenetre.validate();
					}
				});
			}

		});

		fenetre.getContentPane().add(v, BorderLayout.CENTER);

		TIC_Controller c = new TIC_Controller(m, v);
		v.setTIC_Controller(c);

		m.addObserver(v);
		m.addObserver(table);

		// rendre la fenêtre visible, pack fait en sorte que tous les composants de l'application soient à
		// leur preferredSize, ou au dessus
		fenetre.pack();
		fenetre.setVisible(true);
	}
}
