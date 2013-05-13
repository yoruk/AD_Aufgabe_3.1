package aufgabe3_1;

import java.util.*;

public class ACOImpl implements ACO {
    /**
     * Berechnet die Laenge des Weges, den die Ameise gegangen ist
     * 
     * @param 	visitedNodes      	Liste der besuchten Staedte als Integer
     * @param 	connectionList		Liste aller Connections
     * @return 	                    Laenge des Weges der diese Staedte miteinander verbindet
     */
    public int length(List<Integer> visitedNodes, List<Connection> connectionList) {
        int length = 0;

        // Durchlaufe alle Nodes
        for (int i = 1; i < visitedNodes.size(); i++) {

            // Durchlaufe fuer jeweiligen Node alle Connections 
            for (int j = 0; j < connectionList.size(); j++) {

                // Die Connection suchen, die zwei besuchte Nodes miteinander verbindet
                if((visitedNodes.get(i) == connectionList.get(j).cities.get(0) && visitedNodes.get(i - 1) == connectionList.get(j).cities.get(1))
                || (visitedNodes.get(i) == connectionList.get(j).cities.get(1) && visitedNodes.get(i - 1) == connectionList.get(j).cities.get(0))) {

                    length += connectionList.get(j).length;
                } // if
            } // for
        } // for

        return length;
    }

    /**
     * Erzeugt eine ArrayList von Ants
     * 
     * @param antCount      Anzahl der Ameisen
     * @param startPosition Startpunkt der Ameisen
     * @return              ArrayList mit Ameisen
     */
    public List<Ant> createAnts(int antCount, Node startPosition) {
        List<Ant> ants = new ArrayList<Ant>();
        List<Integer> visitedNodes = new ArrayList<Integer>();

        visitedNodes.add(startPosition.ID);

        for(int i=0; i<antCount; i++) {
            ants.add(new Ant(i+1, visitedNodes));
        }

        return ants;
    }

    /**
     * Bewegt eine Ameise zu einem neuen Node
     * 
     * @param ant   Ameise die bewegt werden soll
     * @param node	Node zu dem sich die Ameise bewegen soll
     * @return      Ameise an der neuen Position
     */
    public Ant move(Ant ant, Node node) {
        List<Integer> nodeList = ant.visitedNodes;
        nodeList.add(node.ID);

        return new Ant(ant.ID, nodeList);
    }

    /**
     * Erzeugt den Startpunkt als neues Ziel fuer die Heimkehr der Ameise
     * 
     * @param ant   Ameise, die nach Hause geschickt werden soll
     * @return      Ameise mit Positionsdaten fuer die Heimkehr
     */
    public Ant goHome(Ant ant) {
        List<Integer> nodeList = ant.visitedNodes;

        if(ant.visitedNodes.get(ant.visitedNodes.size() - 1) != ant.visitedNodes.get(0)) {
        nodeList.add(ant.visitedNodes.get(0));
        }

        return new Ant(ant.ID, nodeList);
    }

    /**
     * Loescht die Liste der besuchten Nodes aus der Ameise
     * 
     * @param ant   Ameise, bei der die Liste geloescht werden soll
     * @return      Neue, saubere Ameise
     */
    public Ant clear(Ant ant) {
        List<Integer> nodeList = new ArrayList<Integer>();
        nodeList.add(ant.visitedNodes.get(0));

        return new Ant(ant.ID, nodeList);
    }

    /**
     * Prueft, ob die Ameise ihre Tour beenden und den Rueckweg antreten muss
     * 
     * @param ant       Ameise, die geprueft werden soll
     * @param numCities Anzahl der Staedte
     * @return          Status der Tour
     */
    public boolean tourFinished(Ant ant, int numCities) {

        // Wenn visitedNodes + Rueckweg groessergleich numCities + 1 ist
        if (ant.visitedNodes.size() >= (numCities + 1)) {
            return true; // true zurueckgeben
        }
        return false; // sonst false
    }

    /**
     * Updatet den Pheromonwert einer Connection, erfordert update der Nodes
     * 
     * @param connection    Connection, die geupdatet werden soll
     * @param alpha			Wert um den Pheromone veraendert werden sollen
     * @return              Geupdatete Connection
     */
    public Connection updatePheromones(Connection oldConnection, double alpha) {
        double newPheromon = 0;

        newPheromon = (oldConnection.pheromon + alpha);

        if (newPheromon < 0 ) {
            newPheromon = 0; // Negative Pheromon-Werte verhindern
        }

        return new Connection(oldConnection.ID, oldConnection.length, newPheromon, oldConnection.cities);
    }

    /**
     * Verringert die Pheromonwerte aller Connections (erfordert Update der Nodes)
     * 
     * @param oldList   Liste aller aktueller Connections
     * @param rho       Evaporations-Koeffizient
     * @return          Neue Liste aller Connections mit reduziertem Pheromonwert
     */
    public List<Connection> evaporate(List<Connection> oldList, double rho) {
        List<Connection> newPheromones = new ArrayList<Connection>();

        for(int i=0; i<oldList.size(); i++) {
            newPheromones.add(updatePheromones(oldList.get(i), rho));
        } // for

        return newPheromones;
    }

    /**
     * Ermittelt den Weg, welchen die Ameise gehen soll
     * 
     * @param ant   Ameise, fuer die der Weg ermittelt werden soll
     * @param node  Position der Ameise
     * @return      Pfad, welchen die Ameise gehen soll
     */
    public double[] findPath(Ant ant, Node node, int numCities, double alpha, double beta) {
        double[] propabilities = new double[numCities];
        double nenner = 0.0;

        for (int i = 0; i < node.trails.size(); i++) {

            Connection currentConnection = node.trails.get(i); // Aktuelle Connections
            List<Integer> currentCities = currentConnection.cities; // Aktuelle Cities der Connections

            // Wenn die Staedte in currentCities noch nicht besucht worden sind, Nenner berechnen
            if (!(ant.visitedNodes.containsAll(currentCities))) {

                nenner += Math.pow(node.trails.get(i).pheromon, alpha) * Math.pow(1.0 / node.trails.get(i).length, beta);

                propabilities[i] = 1.0; // propabilities Index zur spaeteren Berechnung taggen
            } // if
        } // for

        // Fuer getaggte Propabilities den Zaehler und das Endergebnis berechnen
        for (int j = 0; j < propabilities.length; j++) {
            if (propabilities[j] == 1.0) {

                double zaehler = Math.pow(node.trails.get(j).pheromon, alpha) * Math.pow(1.0 / node.trails.get(j).length, beta);

                propabilities[j] = zaehler / nenner;
            } // if
        } // for

        // Index der abgelegten Propabilities korrigieren, weil Trails < numCities
        for (int i = propabilities.length - 1; i > 0; i--) {
            if (i >= node.ID) {
                propabilities[i] = propabilities[i - 1];
                propabilities[i - 1] = 0;
            } // if
        } // for

        return propabilities;
    }

    /**
     * Waehlt aus den Wahrscheinlichkeiten zufaellig einen Wert aus
     * 
     * @param   nodes Liste mit den verfuegbaren Nodes
     * @param   array Array mit den Wahrscheinlichkeiten (Propabilities)
     * @return  Node, der auf Basis der Wahrscheinlichkeiten zufaellig gewaehlt wurde
     */
    public Node randomPathChoice(List<Node> nodes, double[] array){
        double random = (new Random().nextDouble()); // Neuer Random double Wert
        double[] copyArray = array.clone(); // Kopie vom Original Array erstellen
        Arrays.sort(copyArray); // Kopie vom Original Array aufsteigend sortieren
        double sumPropabilities = 0.0; // Aufsummierte Propabilities
        double foundPropability = 0.0; // Propability, die kleinergleich Random ist
        int resultIndex = -1; // Index von foundPropability im Original Array

        /*
         * Schritt [1]: Aufsteigend sortiertes Probabilities Array durchlaufen
         * und die Wahrscheinlichkeiten in tempResult aufsummieren, bis der
         * Propability-Wert gefunden wurde, bei dem die Summe der
         * Wahrscheinlichkeiten noch kleinergleich random ist. Den gefundenen
         * Propability-Wert in foundPropability speichern.
         * 
         * Schritt [2]: Im Originalen Array das Vorkommen von foundPropability
         * finden und den Index in resultIndex speichern.
         * 
         * Schritt [3]: In der Node Liste den indexrichtigen Node, passend zum
         * gefundenen Propability-Wert im uebergebenem Array, finden und
         * ausgeben.
         */

        // Schritt [1]:
        for (int i = 0; i < copyArray.length; i++) {
            sumPropabilities += copyArray[i];
            if (random <= sumPropabilities) {
                foundPropability = copyArray[i]; // Propability-Wert speichern
                i = copyArray.length; // for-Schleife abbrechen lassen
            }
        }

        // Schritt [2]:
        for (int i = 0; i < array.length; i++) {
            if (array[i] == foundPropability) {
                resultIndex = i; // Index von foundPropability im Original Array
                i = array.length; // for-Schleife abbrechen lassen
            }
        }

        // Schritt [3]:
        for (int i = 0; i < nodes.size(); i++) {
            if ((resultIndex != -1) && (nodes.get(i).ID == resultIndex + 1)) {
                return nodes.get(i); // Gefundenen, indexrichtigen Node ausgeben 
            }
        }
        return null;
    }
}