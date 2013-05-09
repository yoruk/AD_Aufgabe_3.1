package aufgabe3_1;

import java.util.List;

public interface ACO {
	public int way(List<Connection> list);
	public List<Connection> goHome(Ant ant);
	public List<Connection> clear(Ant ant);
	public boolean checkAnt(Ant ant, int Citys);
	public Connection findPath(Node node);
	public Ant move(Connection connection);
	public Connection update(Connection connection);
	public List<Connection> updateAll(List<Connection> list);
	public List<Ant> createAnts(int antCount, Node startPosition);
}
