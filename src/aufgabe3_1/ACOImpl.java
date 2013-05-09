package aufgabe3_1;
import java.util.*;

public class ACOImpl implements ACO {

    /**
     * Berechnet die leange des weges welchen die Armeise genommen hat
     * 
     * @param   list List der wege
     * @return  laenge des weges
     */
    public int way(List<Connection> list) {
    	//TODO
        return -1;
    }

    public List<Connection> goHome(Ant ant) {
        //TODO
    	return null;
    }

    /**
     * Loescht die Liste der Besuchten Nodes aus der Armeise
     * 
     * @param ant   Armeise wo die Liste geloescht werden soll
     * @return      geloeschte Liste
     */
    public List<Connection> clear(Ant ant) {
    	//TODO
        return null;
    }

    /**
     * Prueft den Status der Route
     * 
     * @param ant   Armeise wo der Status geprueft werden soll
     * @return      den Status
     */
    public boolean checkAnt(Ant ant, int Citys) {
    	//TODO
        return false;
    }

    /**
     * Ermittelt den Weg welcher die Ameise gehen soll
     * 
     * @param node  position der Armeise
     * @return      pfad welchen die armeise gehen soll
     */
    public Connection findPath(Node node) {
    	//TODO
        return null;
    }

    /**
     * Bewegt eine Armeise
     * 
     * @param ant   pfad welchen die Armeise gehen soll
     * @return      Armeise am der neuen Position
     */
    public Ant move(Connection connection) {
    	//TODO
        return null;
    }

    /**
     * Updaten der Pheromone einer Connection
     * 
     * @param connection    Connection die Upgedetet werden soll
     * @return Upgedatet    Connection
     */
    public Connection update(Connection connection) {
    	//TODO
        return null;
    }

    /**
     * Veringert alle Pheromone
     * 
     * @param list  Liste mit allem Connections
     * @return      Liste von allen Connections mit den Reduzierten Pheromon werten
     */
    public List<Connection> updateAll(List<Connection> list) {
    	//TODO
        return null;
    }

    public List<Ant> createAnts(int antCount, Node startPosition) {
        List<Ant> ants = new ArrayList<Ant>();
        ArrayList<Node> visitedNodes = new ArrayList<Node>();
        
        visitedNodes.add(startPosition);
        
        for(int i = 0; i < antCount; i++) {

            ants.add(new Ant(i + 1, visitedNodes));
        }

        return ants;
    }

    public void run(String path, int citys, int rounds, int countAnt, int startPositionID) {
        List<Connection> connections = Parser.initConnections(Parser.input(path));
        List<Node> nodes = Parser.initNodes(connections);
        List<Ant> ants = createAnts(countAnt, nodes.get(startPositionID - 1));

        System.out.println(ants.toString());
        //System.out.println(connection.toString());
        //System.out.println(node.toString());

        int i = 0;
        while (rounds < i) {
        	//TODO
        	
            // tmpConnection = findPath(altConnection);
            // ant = move(connection);
            // Altconnection = ...(tmpConnection);
            i++;
        }
    }
}