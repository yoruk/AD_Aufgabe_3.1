package aufgabe3_1;

import java.io.*;
import java.util.*;

//Tour(int ID, int length, int omon, int IDcity1, int IDcity2)
//Node(int ID, ArrayList<Tour> trail)

public class Parser {
    final public static int omon = 0;

    public static int[] input(String filePath) {
        int[] resultArray = null;
        String line; // Zu verarbeitende Zeile
        String lineSplit[]; // Werte ohne Trennzeichen
        List<String> list = new LinkedList<String>(); // Alle Werte ohne Trennzeichen

        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            
            while ((line = br.readLine()) != null) {
            	if((line.charAt(0) == '0')) {
            		while(!(line.equals("EOF"))) {

                        // Trennzeichen " " entfernen
                        lineSplit = line.split(" ");

                        // Werte ohne Trennzeichen in Liste abspeichern
                        for(String element : lineSplit) {
                            if(!element.isEmpty()) {
                                list.add(element);
                            } // if
                        } // for

                        // Naechste Zeile holen
                        line = br.readLine();
                    } // while

                } // if
            } // while

            resultArray = new int[list.size()];

            // Liste nach (int) resultArray parsen
            for(int i = 0; i < resultArray.length; i++) {
                resultArray[i] = Integer.parseInt(list.get(i));
            } // for
            
            br.close();
            
        } catch(Exception e) {
            System.err.println(e);
            System.exit(0);
        }
        
        return resultArray;
    }

    /**
     * Erzeugt eine ArrayList von Connections
     * 
     * @param arr
     *            Int Array mit denn Längen zwischen denn nodes
     * @return Eine ArrayList von Connections
     */
    public static List<Connection> initConnections(int[] arr) {
        List<Connection> tour = new ArrayList<Connection>();

        int IDcity1 = 1;
        int IDcity2 = 1;
        int ID = 1;

        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == 0) {
                IDcity1++;
                IDcity2 = 1;
            } else {
                if(IDcity1 == IDcity2) {
                    IDcity2++;
                }
                
                tour.add(new Connection(ID, arr[i], omon, IDcity1, IDcity2));
                IDcity2++;
                ID++;
            }
        }
        
        return tour;
    }

    /**
     * Erzeugt eine ArrayListe aus Node
     * 
     * @param connections
     *            ArrayListe von Connections
     * @return ArrayList von Nodes
     */
    public static List<Node> initNodes(List<Connection> connections) {
        List<Node> node = new ArrayList<Node>();
        Set<Integer> set = new HashSet<Integer>();

        for(int i = 0; i < connections.size(); i++) {
        	set.add(connections.get(i).IDcity1);
            set.add(connections.get(i).IDcity2);
        }

        for(int i = 0; i < set.size(); i++) {
            List<Connection> cityTour = new ArrayList<Connection>();
            for(int k = 0; k < connections.size(); k++) {
                if(connections.get(k).IDcity1 == (i + 1) || connections.get(k).IDcity2 == (i + 1)) {
                    cityTour.add(connections.get(k));
                }
            }
            node.add(new Node(i + 1, cityTour));
        }

        return node;
    }
}