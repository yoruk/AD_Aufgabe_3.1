package aufgabe3_1;

import java.util.*;

public class ACOImpl implements ACO {
    /**
     * Berechnet die Laenge des Weges, den die Ameise gegangen ist
     * 
     * @param 	visitedNodes      	Liste der besuchten Staedte als Integer
     * @param 	connectionList		Liste aller Connections
     * @return 	Laenge des Weges der diese Staedte miteinander verbindet
     */
    public int length(List<Integer> visitedNodes, List<Connection> connectionList) {
    	int length = 0;

    	// durchlaufe alle nodes
        for(int i=1; i<visitedNodes.size(); i++) {
        	
        	// fuer jeden node alle connections durchlaufen
            for(int j=0; j<connectionList.size(); j++) {

            	// die connection suchen die zwei besuchte nodes miteinander verbindet
                if((visitedNodes.get(i) == connectionList.get(j).cities.get(0) && visitedNodes.get(i-1) == connectionList.get(j).cities.get(1)) ||
                   (visitedNodes.get(i) == connectionList.get(j).cities.get(1) && visitedNodes.get(i-1) == connectionList.get(j).cities.get(0))) {

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
        nodeList.add(ant.visitedNodes.get(0));

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
        if (ant.visitedNodes.size() >= numCities) {
            return true;
        }
        
        return false;
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
            newPheromon = 0;
        }
        
        return new Connection(oldConnection.ID, oldConnection.length, newPheromon, oldConnection.cities);
    }
    
    /**
     * Verringert die Pheromonwerte aller Connections, erfordert update der Nodes
     * 
     * @param oldList   Liste aller Connections
     * @param rho       Evaporations-Koeffizient
     * @return          Neue Liste aller Connections mit reduzierten Pheromonwerten
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

            Connection temp = node.trails.get(i);
            //System.out.println(temp);
            List<Integer> tempCities = temp.cities;
            if (!(ant.visitedNodes.containsAll(tempCities))) {

                nenner += Math.pow(node.trails.get(i).pheromon, alpha) * Math.pow(1.0 / node.trails.get(i).length, beta);

                propabilities[i] = 1.0;
            }
        } // for
        
        for (int j = 0; j < propabilities.length; j++) {
            if (propabilities[j] == 1.0) {
                double zaehler = Math.pow(node.trails.get(j).pheromon, alpha)
                               * Math.pow(1.0 / node.trails.get(j).length, beta);

                propabilities[j] = zaehler / nenner;
            } // if
        } // for
        
        // Schieben auf richtigen Index wert
        for(int i = propabilities.length-1; i > 0; i--) {
            if(i >= node.ID){
                propabilities[i] = propabilities[i-1];
                propabilities[i-1] = 0;
            }
        }

        return propabilities;
    }

    public Node randomPathChoice(List<Node> nodes, double[] array){
        Random random = new Random();
        double tempRandom = random.nextDouble();

        double[] copyArray = array.clone();
        
        Arrays.sort(array);
        
        double index = -1;
        double tempWin = 0.0;
        for(int i = 0; i < array.length;i++) {
            tempWin += array[i];
            if(tempRandom <= tempWin){
                index = array[i];
                i = array.length;
            }
        }
        
        for(int i = 0; i < copyArray.length; i++) {
            if(copyArray[i] == index) {
                index = i;
                i = copyArray.length;
            }
        }
        System.out.println("\ntest: " + index);
        
        for(int i = 0; i < nodes.size(); i++) {
            if(index != -1 && nodes.get(i).ID == index+1) {
                return nodes.get(i);
            }
        }
        return null;
    }
}