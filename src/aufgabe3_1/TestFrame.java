package aufgabe3_1;

import java.util.*;

public class TestFrame {
	final public static String TESTDATA = "GR05_TEST.txt"; 	// TSP Datei
	final public static int BESTSOLUTION = 2707; 			// Beste bislang bekannte Loesung
	final public static int CITIES = 5; 					// Anzahl der Staedte
	final public static int ANTS = 5; 						// Anzahl der Ameisen
	final public static int CYCLES = 0; 					// Anzahl der Durchlaeufe
	final public static int STARTNODE = 1; 					// Startpunkt der Ameise
	final public static double ALPHA = 1.0; 				// Einfluss der Pheromonene
	final public static double BETA = 5.0; 					// Einfluss der Weglaenge
	final public static double RHO = 0.5; 					// Evaporationskonstante

	public static void testWegberechnung() {
		System.out.println("\n\n::Test: Berechnung der Weglaenge ueber Nodes::");

		ACO testColony = new ACOImpl();
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));
        List<Node> nodes = Parser.initNodes(connections);

        List<Integer> visitedNodes = new ArrayList<Integer>();
        visitedNodes.add(nodes.get(0).ID);
        visitedNodes.add(nodes.get(1).ID);
        visitedNodes.add(nodes.get(2).ID);
        visitedNodes.add(nodes.get(3).ID);
        visitedNodes.add(nodes.get(4).ID);
        visitedNodes.add(nodes.get(0).ID);
        
        int testlength = testColony.length(visitedNodes, connections);
        System.out.printf("Gesamtlaenge = %d", testlength);
    }
	
    public static void testBewegungDerAmeise() {
    	System.out.println("\n\n::Test: Bewegung einer Ameise::");

        ACO testColony = new ACOImpl();
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));
        List<Node> nodes = Parser.initNodes(connections);

        List<Integer> visitedNodes = new ArrayList<Integer>();
        visitedNodes.add(nodes.get(0).ID);
        
        // Ameise auf Node 0 plazieren
        Ant testAnt = new Ant(0, visitedNodes);
        System.out.println(testAnt.visitedNodes);
        
        // Ameise nach Node 1 bewegen
        testColony.move(testAnt, nodes.get(1));
        System.out.println(testAnt.visitedNodes);
        
        // Ameise nach Node 2 bewegen
        testColony.move(testAnt, nodes.get(2));
        System.out.println(testAnt.visitedNodes);
        
        // Ameise nach Node 4 bewegen
        testColony.move(testAnt, nodes.get(4));
        System.out.println(testAnt.visitedNodes);
    }

    public static void testBesuchteStaedte() {
    	System.out.println("\n\n::Test: ob die Ameise schon alle Staedte besucht hat::");

        ACO testColony = new ACOImpl();
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));
        List<Node> nodes = Parser.initNodes(connections);

        List<Integer> visitedNodes = new ArrayList<Integer>();
        visitedNodes.add(nodes.get(0).ID);
        visitedNodes.add(nodes.get(1).ID);
        visitedNodes.add(nodes.get(2).ID);
        
        Ant testVisitAnt = new Ant(0, visitedNodes);
        
        System.out.println("Noch nicht alles Besucht: " + testColony.tourFinished(testVisitAnt, 5));
        
        testColony.move(testVisitAnt, nodes.get(3));
        testColony.move(testVisitAnt, nodes.get(4));
        
        System.out.println("Schon Alle Besucht: " + testColony.tourFinished(testVisitAnt, 5));
    }

    public static void testClearConnection() {
    	System.out.println("\n\n::Test: Loeschen der besuchten Connections::");

        ACO testColony = new ACOImpl();
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));
        List<Node> nodes = Parser.initNodes(connections);

        List<Integer> visitedNodes = new ArrayList<Integer>();
        visitedNodes.add(nodes.get(0).ID);
        visitedNodes.add(nodes.get(1).ID);
        visitedNodes.add(nodes.get(2).ID);

        Ant testAnt = new Ant(0, visitedNodes);
        
        System.out.println("Armeise vorher: " + testAnt.visitedNodes);
        
        testAnt = testColony.clear(testAnt);
        
        System.out.println("Armeise nachher: " + testAnt.visitedNodes);
    }
    
    public static void testPheromonUpdateOne() {
    	System.out.println("\n\n::Test: von einem PheromonUpdateOne::");

    	ACO testColony = new ACOImpl();
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));
        
        System.out.println("Pheromonmenge bei Connection 1 um 1 erhoehen");
        connections.set(0, testColony.updatePheromones(connections.get(0), 1));
        System.out.println(connections);
        
        System.out.println("Pheromonmenge bei Connection 1 um -3 verringern");
        connections.set(0, testColony.updatePheromones(connections.get(0), -3));
        System.out.println(connections);
    }
    
    public static void testPheromonUpdateAll() {
    	System.out.println("\n\nTest: von einem PheromonUpdateAll::");

        ACO testColony = new ACOImpl();
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));

        System.out.println("Pheromonmenge am Anfang = 0");
        System.out.println(connections);
        
        System.out.println("Pheromonmenge ueberall um 2 erhoehen");
        connections = testColony.evaporate(connections, 2);
        System.out.println(connections);
      
        System.out.println("Pheromonmenge ueberall um 0.5 veringern");
        connections = testColony.evaporate(connections, -0.5);
        System.out.println(connections);
    }

    public static void testFindPath() {
        System.out.println("\nTest der findPath Methode:\n");
        ACO testColony = new ACOImpl();
        
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));
        connections = testColony.evaporate(connections, 2);
        List<Node> nodes = Parser.initNodes(connections);
        System.out.println("Connections :" + connections);
        List<Integer> testConNodes = new ArrayList<Integer>();

//        testConNodes.add(nodes.get(0).ID);
//        testConNodes.add(nodes.get(1).ID);
//        testConNodes.add(nodes.get(2).ID);
//        testConNodes.add(nodes.get(3).ID);
        testConNodes.add(nodes.get(4).ID);

        Ant testConAnt = new Ant(0, testConNodes);
        System.out.println(testConAnt.visitedNodes);
//        System.out.println(nodes.get(2));

        double[] result = testColony.findPath(testConAnt, nodes.get(4), CITIES, ALPHA, BETA);

        double ergebniss = 0;
        for (double element : result) {
            System.out.print("[" + element + "] ");
            ergebniss += element;
        }
        System.out.println("\nErgebnis " + ergebniss);
    }
    
    public static void testrandomChoice() {
        System.out.println("\nTest der randomChoice Methode:\n");
        ACO testColony = new ACOImpl();
        
        List<Connection> connections = Parser.initConnections(Parser.parseTestFile(TESTDATA));
        connections = testColony.evaporate(connections, 2);
        List<Node> nodes = Parser.initNodes(connections);
        
        List<Integer> testConNodes = new ArrayList<Integer>();

        testConNodes.add(nodes.get(0).ID);
//        testConNodes.add(nodes.get(1).ID);
        testConNodes.add(nodes.get(2).ID);
        testConNodes.add(nodes.get(3).ID);
//        testConNodes.add(nodes.get(4).ID);
        
        Ant testConAnt = new Ant(0, testConNodes);
        System.out.println(testConAnt.visitedNodes);
        
        double[] result = testColony.findPath(testConAnt, nodes.get(3), CITIES, ALPHA, BETA);
        double ergebniss = 0;
        for (double element : result) {
            System.out.print("[" + element + "] ");
            ergebniss += element;
        }
        System.out.println("\nErgebnis " + ergebniss);
        
        System.out.println("\nBömm: " + testColony.randomPathChoice(nodes, result));
    }

    public static void main(String[] args) {
        testWegberechnung();
        testBewegungDerAmeise();
        testBesuchteStaedte();
        testClearConnection();
        testPheromonUpdateOne();
        testPheromonUpdateAll();
//        testFindPath();
//        testrandomChoice();

//        int i = 0;
//
//        while (CYCLES < i) {
//
//            // TODO: Eine statische Variable mit dem aktuell besten Wert
//            // anlegen, oder zwei Werten: Beste Tour und deren Laenge.
//            // Diese durch die ACOImpl pro Takt updaten lassen, wenn
//            // das Ergebnis besser/kleiner wird, als die vorangegangenen Werte.
//            // Dazu die BESTSOLUTION Konstante einblenden zum Vergleich.
//
//            // TODO: Reihenfolge der Methodenaufrufe klaeren
//
//            // tmpConnection = findPath(altConnection);
//            // ant = move(connection);
//            // Altconnection = ...(tmpConnection);
//
//            i++;
//
//        } // while
    } // main

}
