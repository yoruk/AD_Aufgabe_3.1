package aufgabe3_1;

import java.util.List;

public interface ACO {
    public int length(List<Integer> list, List<Connection> connectionList);

    public Ant goHome(Ant ant);

    public Ant clear(Ant ant);

    public boolean tourFinished(Ant ant, int cities);

    public double[] findPath(Ant ant, Node node, int cities, double alpha, double beta);

    public Node randomPathChoice(List<Node> nodes, double[] array);

    public Ant move(Ant ant, Node node);

    public Connection updatePheromones(Connection connection, double alpha);

    public List<Connection> evaporate(List<Connection> list, double rho);

    public List<Ant> createAnts(int antCount, Node startPosition);
}
