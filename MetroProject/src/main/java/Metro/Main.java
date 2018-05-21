package Metro;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import Metro.Network;
import jwetherell.Graph.Edge;
import jwetherell.Graph.Vertex;

public class Main {
	
	public static void main(String[] args) {
		boolean fini = false;
		Network n = new Network();
		n.loadStations("src/test/resources/positions-geographiques-des-stations-du-reseau-ratp.csv");
		n.loadLines("src/test/resources/lignes.csv");
		ArrayList<Vertex<Station>> suppr = new ArrayList<Vertex<Station>>();
		for(Vertex<Station> v : n.getGraph().getVertices()) {
			if(v.getEdges().size()==0) {
				suppr.add(v);
			}
		}
		for(Vertex<Station> v : suppr) {
			n.remove(v.getValue());
		}
		
		JOptionPane.showMessageDialog(null,"Bienvenue dans notre Application du Métro de Paris!");
		while (fini == false){
			String choix = (JOptionPane.showInputDialog(null,"Que voulez vous faire ? \n "
					+ "- (1) Voir l'ensemble des lignes du réseau \n"
					+ "- (2) Voir l'ensemble des stations du réseau \n"
					+ "- (3) Trouver la station la plus proche \n "
					+ "- (4) Trouver son chemin vers une station \n"
					+ "- (5) Trouver son chemin en passant par plusieurs stations \n "
					+ "- (6) Voir l'ensemble des lignes sujettes à incident \n"
					+ "- (7) Voir l'ensemble des Stations sujettes à incident \n"
					+ "- (8) Créer un incident \n"
					+ "- (9) Résoudre un incident \n"
					+ "- (10) Résoudre tous les incidents\n"
					+ "- (11) Vider le réseau\n"
					+ "- (12) Quitter l'application"));
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
				res = new StringBuffer();
				res.append("Voici les stations du réseau :\n");
				int i = 0;
				for(Vertex<Station> Vertex : n.getGraph().getVertices()) {
					res.append(Vertex.getValue().getName());
					if(i<10) {
						res.append(" - ");
						i++;}
					else {
						res.append("\n");
						i=0;
					}
				}
				JOptionPane.showMessageDialog(null,res.toString());
				break;
			case "3" : 
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
			case "4" : 
				True = false;
				Station start = new Station(0,0,"Start");
				Station goal = new Station(0,0,"Goal");
				while(!True) {
					String StartS = (JOptionPane.showInputDialog(null,"Entrez votre Station de départ"));
					try {
						if (!DataChecker.checkString(StartS)) {
							JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
						}else {if(!n.contains(new Station(0,0,StartS))){
							JOptionPane.showMessageDialog(null,"Cette station n'appartient pas au réseau.");
							}else {
								start = n.getVertex(StartS).getValue();
								True = true;
							}
						}
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
					}
				}
				True = false;
				while(!True) {
					String goalS = (JOptionPane.showInputDialog(null,"Entrez votre station d'arrivée"));
					try {
						if (!DataChecker.checkString(goalS)) {
							JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
						}else {if(!n.contains(new Station(0,0,goalS))){
							JOptionPane.showMessageDialog(null,"Cette station n'appartient pas au réseau.");
							}else {
								goal = n.getVertex(goalS).getValue();
								True = true;
							}
						}
					}catch(Exception e) {
						JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
					}
				}
				List<Edge<Station>> e = n.aStar(n.getGraph(), n.getVertex(start), n.getVertex(goal));
				if(e== null || e.size()==0) {
					JOptionPane.showMessageDialog(null,"Aucun chemin n'existe entre ces deux stations");
				}
				res = new StringBuffer();
				res.append("Voici le chemin le plus rapide possible:\n");
				for(Edge<Station> elt : e) {
					if(elt.equals(e.get(e.size()-1))) {
						res.append(elt.getFromVertex());
						res.append(elt.getToVertex());
					}else {
						res.append(elt.getFromVertex());
					}
				}
			break;
			case "5" : 
				True = false;
				String StartS = "test";
				List<Vertex<Station>> array = new ArrayList<Vertex<Station>>();
				while(StartS != "" && StartS != null) {
					while(!True) {
						StartS = (JOptionPane.showInputDialog(null,"Entrez un nom de station"));
						try {
							if (!DataChecker.checkString(StartS)) {
								JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
							}else {if(!n.contains(new Station(0,0,StartS))){
								JOptionPane.showMessageDialog(null,"Cette station n'appartient pas au réseau.");
								}else {
									array.add(n.getVertex(StartS));
									True = true;
								}
							}
						}catch(Exception e2) {
							JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
						}
					}
				}
				List<Edge<Station>> edges = n.multipleBreakPoints(n.getGraph(), array);
				if(edges== null || edges.size()==0) {
					JOptionPane.showMessageDialog(null,"Aucun chemin n'existe entre ces deux stations");
				}
				res = new StringBuffer();
				res.append("Voici le chemin le plus rapide possible:\n");
				for(Edge<Station> elt : edges) {
					if(elt.equals(edges.get(edges.size()-1))) {
						res.append(elt.getFromVertex());
						res.append(elt.getToVertex());
					}else {
						res.append(elt.getFromVertex());
					}
				}
			break;
			case "6" : 
				res = new StringBuffer();
				res.append("Voici les Lignes sujettes à incident du réseau :\n");
				i = 0;
				for(Edge<Station> Edge : n.getLinesWithIncidents()) {
					res.append(Edge.getFromVertex()+"->"+Edge.getToVertex());
					if(i<5) {
						res.append(" - ");
						i++;}
					else {
						res.append("\n");
						i=0;
					}
				}
				JOptionPane.showMessageDialog(null,res.toString());
			break;
			case "7" : 
				res = new StringBuffer();
				res.append("Voici les Stations sujettes à incident du réseau :\n");
				i = 0;
				for(Vertex<Station> v : n.getGraph().getVertices()) {
					if(v.getValue().isIncident()==true) {
						res.append(v.getValue().getName());
						if(i<5) {
							res.append(" - ");
							i++;}
						else {
							res.append("\n");
							i=0;
						}
					}
				}
				JOptionPane.showMessageDialog(null,res.toString());
			break;
			case "8" : 
				True = false;
				start = new Station(0,0,"Start");
				while(!True) {
					StartS = (JOptionPane.showInputDialog(null,"Entrez la station sur laquelle vous voulez créer un incident"));
					try {
						if (!DataChecker.checkString(StartS)) {
							JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
						}else {if(!n.contains(new Station(0,0,StartS))){
							JOptionPane.showMessageDialog(null,"Cette station n'appartient pas au réseau.");
							}else {
								start = n.getVertex(StartS).getValue();
								True = true;
							}
						}
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
					}
				}
				if(n.createIncident(start)) {
					JOptionPane.showMessageDialog(null,"Incident créé avec succès.");
				}else {
					JOptionPane.showMessageDialog(null,"Une erreur s'est produite, l'incident n'a pas été créé.");
				}
			break;
			case "9" : 
				True = false;
				start = new Station(0,0,"Start");
				while(!True) {
					StartS = (JOptionPane.showInputDialog(null,"Entrez la station sur laquelle vous voulez résoudre un incident"));
					try {
						if (!DataChecker.checkString(StartS)) {
							JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
						}else {if(!n.contains(new Station(0,0,StartS))){
							JOptionPane.showMessageDialog(null,"Cette station n'appartient pas au réseau.");
							}else {
								start = n.getVertex(StartS).getValue();
								True = true;
							}
						}
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayer.");
					}
				}
				if(n.solveIncident(start)) {
					JOptionPane.showMessageDialog(null,"Incident résolu avec succès.");
				}else {
					JOptionPane.showMessageDialog(null,"Une erreur s'est produite, l'incident n'a pas été résolu.");
				}
			break;
			case "10" : 
				n.solveAllIncidents();
				JOptionPane.showMessageDialog(null,"Tous les incidents ont été résolus.");
			break;
			case "11" : 
				n.clear();
				JOptionPane.showMessageDialog(null,"Le réseau a été effacé avec succès.");
			break;
			case "12" : 
				fini = true;
				JOptionPane.showMessageDialog(null,"Merci d'avoir utilisé notre Application du Métro de Paris!");
			break;
			default:
				JOptionPane.showMessageDialog(null,"Entrée invalide, veuillez réessayez.");
			}
		}
		
	}
}
