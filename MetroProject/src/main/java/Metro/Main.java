package Metro;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import Metro.Network;

public class Main {
	
	public static void main(String[] args) {
		boolean fini = false;
		Network n = new Network();
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		
		JOptionPane.showMessageDialog(null,"Bienvenue dans notre Application du Métro de Paris!");
		while (fini == false){
			String choix = (JOptionPane.showInputDialog(null,"Que voulez vous faire ? \n "
					+ "- (1) Voir l'ensemble des lignes du réseau \n"
					+ "- (2) Trouver la station la plus proche \n "
					+ "- (3) Trouver son chemin vers une station \n"
					+ "- (4) Trouver son chemin en passant par plusieurs stations \n "
					+ "- (5) Voir l'ensemble des lignes sujettes à incident \n"
					+ "- (6) Voir l'ensemble des Stations sujettes à incident \n"
					+ "- (7) Créer un incident \n"
					+ "- (8) Résoudre un incident \n"
					+ "- (9) Résoudre tous les incidents\n"
					+ "- (10) Vider le réseau\n"
					+ "- (11) Quitter l'application"));
			switch(choix){
			case "1" : 
				StringBuffer res = new StringBuffer();
				res.append("Voici les lignes du réseau :\n");
				for(ArrayList<String> array : n.getLines()) {
					res.append("< - ");
					for(String elt : array) {
						res.append(elt);
						res.append(" - ");
					}
					res.append(">\n");
				}
				JOptionPane.showMessageDialog(null,res.toString());
				break;
			case "2" : 
				boolean True = false;
				Float latitude = 0f;
				Float longitude = 0f;
				while(!True) {
					String latitudeS = (JOptionPane.showInputDialog(null,"Entrez votre latitude (entre 90 et -90 inclus)"));
					try {
						if (!DataChecker.checkCoordinate(Float.parseFloat(latitudeS))) {
							JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
						}else {
							latitude = Float.parseFloat(latitudeS);
							True = true;
						}
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
					}
				}
				True = false;
				while(!True) {
					String longitudeS = (JOptionPane.showInputDialog(null,"Entrez votre longitude (entre 90 et -90 inclus)"));
					try {
						if (!DataChecker.checkCoordinate(Float.parseFloat(longitudeS))) {
							JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
						}else {
							longitude = Float.parseFloat(longitudeS);
							True = true;
						}
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
					}
				}
				String name = n.findNearestStation(latitude, longitude).getValue().getName();
				JOptionPane.showMessageDialog(null,"Le nom de la station la plus proche est :\n"+name);
			break;
			case "3" : ;
			break;
			case "4" : ;
			break;
			case "5" : ;
			break;
			case "6" : ;
			break;
			case "7" : ;
			break;
			case "8" : ;
			break;
			case "9" : ;
			break;
			case "10" : ;
			break;
			case "11" : 
				fini = true;
				JOptionPane.showMessageDialog(null,"Merci d'avoir utilisé notre Application du Métro de Paris!");
			break;
			default:
				JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayez.");
			}
		}
		
	}
}
