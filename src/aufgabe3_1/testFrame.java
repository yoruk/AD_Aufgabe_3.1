package aufgabe3_1;

public class testFrame {
    final public static String TESTDATA = "GR21_TEST.txt";
    final public static int CITIES = 21;
    final public static int ANTS = 5;
    final public static int CYCLES = 100;
    final public static int STARTNODE = 1;    

    public static void main(String[] args) {
    	ACO aco = new ACOImpl();
    
        aco.run(TESTDATA, CITIES, CYCLES, ANTS, STARTNODE);
    }
}
