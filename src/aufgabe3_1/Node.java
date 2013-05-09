package aufgabe3_1;

import java.util.*;

public class Node {
	final public int ID;
    final public List<Connection> trail;

    public Node(int ID, List<Connection> tour) {
        this.ID = ID;
        this.trail = tour;
    }

    @Override
    public String toString() {
        return "Node [ID=" + ID + ", trail=\n" + trail + "]\n";
    }
}
