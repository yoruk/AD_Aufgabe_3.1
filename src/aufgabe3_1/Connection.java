package aufgabe3_1;

public class Connection {
    final public int ID;
    final public int length;
    final public int IDcity1;
    final public int IDcity2;
    final public int omon;
    
    public Connection(int ID, int length, int omon, int IDcity1, int IDcity2) {
        this.ID = ID;
        this.length = length;
        this.omon = omon;
        this.IDcity1 = IDcity1;
        this.IDcity2 = IDcity2;
    }

    @Override
    public String toString() {
        return "Connection [ID=" + ID + ", length=" + length + ", IDcity1=" + IDcity1 + ", IDcity2=" + IDcity2 + ", omon=" + omon + "]\n";
    }
}
