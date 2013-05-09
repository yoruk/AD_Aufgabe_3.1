package aufgabe3_1;

import java.util.List;

public class testFrame {
    final public static String TESTDATA = "GR21_TEST.txt";
    final public static int CITIES = 21;
    final public static int ANTS = 5;
    final public static int CYCLES = 100;
    final public static int STARTNODE = 1;    

    public static void main(String[] args) {
    	ACO aco = new ACOImpl();

    	List<Connection> connections = Parser.initConnections(Parser.input(TESTDATA));
    	List<Node> nodes = Parser.initNodes(connections);
    	List<Ant> ants = aco.createAnts(ANTS, nodes.get(STARTNODE - 1));
    	
    	System.out.println(ants.toString());
    	
    	int i = 0;
    	while (CYCLES < i) {
    		//TODO
    		
    		// tmpConnection = findPath(altConnection);
    		// ant = move(connection);
    		// Altconnection = ...(tmpConnection);
    		i++;
    	}
    }
}
