package aufgabe3_1;

import java.util.*;

public class Ant {
    final public int ID;
    final public List<Node> route;
    
    public Ant(int ID, ArrayList<Node> route) {
        this.ID = ID;
        this.route = route;
    }

    @Override
    public String toString() {
        return "Ant [ID=" + ID + "]";
    }
}
