import java.util.ArrayList;

public interface Program2 {

    public int findTimeOptimalPath(int sourcePort, int destPort);
    public int findCapOptimalPath(int sourcePort, int destPort);
    public ArrayList<Integer> getNeighbors(int node);
    public void inputEdge(int port1, int port2, int time, int capacity);
    public int getNumPorts();
}
