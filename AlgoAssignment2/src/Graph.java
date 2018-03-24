import java.util.ArrayList;
/*
 * Name: Alexander Bi
 * EID: adb2969
 */

public class Graph implements Program2{
	// n is the number of ports
	private int n;
    
	// Edge is the class to represent an edge between two nodes
	// node is the destination node this edge connected to
	// time is the travel time of this edge
	// capacity is the capacity of this edge
	// Use of this class is optional. You may make your own, and comment
	// this one out.
	
	private class Edge{
		public int sourceNode;
		public int destNode;
		public int time;
		public int capacity;
		public Edge(int s, int d, int t, int c){
			sourceNode = s;
			destNode = d;
			time = t;
			capacity = c;
		}
		
		// prints out an Edge.
		public String toString() {
			return "" + destNode;
		}
		
		public int getSourceNode()
		{
			return sourceNode;
		}
	}
	
	private class Vertex{
		int portNumber;
		int priority;
		
		public Vertex(int v, int p)
		{
			portNumber = v;
			priority = p;
		}
		
		public int getPriority()
		{
			return priority;
		}
		
		public int getPortNumber()
		{
			return portNumber;
		}
		
	
	}

	// Here you have to define your own data structure that you want to use
	// to represent the graph
	// Hint: This include an ArrayList or many ArrayLists?
	// ....
	
	ArrayList<ArrayList<Edge>> adjList; // adjacency list of Edge objects
	
	

	// This function is the constructor of the Graph. Do not change the parameters
	// of this function.
	//Hint: Do you need other functions here?
	public Graph(int x) {
		n = x;
		adjList = new ArrayList<ArrayList<Edge>>();
		
		for (int i = 0; i<x; i++)
		{
			ArrayList <Edge> adjListElement = new ArrayList<Edge>();
			adjList.add(adjListElement);
		}
	}
    
	// This function is called by Driver. The input is an edge in the graph.
	// Your job is to fix this function to generate your graph.
	// Do not change its parameters or return type.
	// Hint: Here is the place to build the graph with the data structure you defined.
	public void inputEdge(int port1, int port2, int time, int capacity) {
		
		Edge e = new Edge (port1, port2, time, capacity);
		ArrayList<Edge> dummy = adjList.get(port1);
		dummy.add(e);
		
		Edge flippedEdge = new Edge (port2, port1, time, capacity);
		ArrayList <Edge> dummy2 = adjList.get(port2);
		dummy2.add(flippedEdge);
		
		return;
	}

	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		
		int s = sourcePort;
		int d = destPort;
		int[] dist = new int[n]; 			// array of distances
		ArrayList<Vertex> priorityQueue = new ArrayList<Vertex>(); // Priority queue of Vertex objects
		
		
		for (int i =0; i<n; i++)
		{
			dist[i]= Integer.MAX_VALUE;		// Initialize all distances to infinity
		
 		}
		
		dist[sourcePort] = 0; // Distance from source to source is 0
		
		for (int i = 0; i<n; i++)
		{
			Vertex v = new Vertex (i, Integer.MAX_VALUE); // initialize new Vertex object with priority 0
			priorityQueue.add(v);
		}
		
		while (priorityQueue.size() > 0)
		{
			// u = vertex in Q with the smallest distance in dist[]
			int index_smallest_dist = priorityQueue.get(0).portNumber; // the port number of the first item in the priority queue
			int smallest_dist = dist[index_smallest_dist]; // initialize the smallest distance to the distance from source to first element of the priority queue
			
			
			if (index_smallest_dist == destPort)
			{
				break;
			}
			// if dist[u] is not infinity and u is not sink
			if (dist[index_smallest_dist] != Integer.MAX_VALUE)
			{
				
				priorityQueue.remove(0); //remove the first element in the priority queue
				
				ArrayList <Integer> neighbors = getNeighbors(index_smallest_dist); 
				// for each neighbor v of u
				int alt; // length of alternative route
				for (int i = 0; i< neighbors.size(); i++)
				{
					int u = index_smallest_dist;
					int v = neighbors.get(i);
					alt = dist[index_smallest_dist] + getTravelTime(u, v);// distance from u to neighbor(i)
					if (alt < dist[v])
					{
						dist[v] = alt;
						//previousNodes[v] = u;
						updatePriority(v, dist[v], priorityQueue); // update vertex v with a priority of dist[v]
					}
				}
			}
		}
		
		
		return dist[destPort];
	}

	// finds distance from node u to node v
	public int getTravelTime (int start, int end) 
	{
		int u = start;
		int v = end;
		
		int t = Integer.MAX_VALUE;
		int row_index;
		for (int i = 0; i < adjList.size(); i++)
		{
			if (adjList.get(i).get(0).sourceNode == start) // get the first Edge object in each row of the adjacency list
			{
				row_index = i; 
				
				for (int a = 0; a < adjList.get(row_index).size(); a++)
				{
					if (adjList.get(row_index).get(a).destNode == end)
					{
						t = adjList.get(row_index).get(a).time;
					}
				}
			}
		}
		
		return t;
	}
	
	
	// updates the priority of a vertex in the priority queue
	public void updatePriority(int v, int dist_v, ArrayList<Vertex> queue)
	{
		int vertex = v; 
		int newPriority = dist_v;
		int indexOfVertexToMove = -1;
		ArrayList<Vertex> pq = queue;
		
		for (int i = 0; i<pq.size(); i++)
		{
			if (pq.get(i).portNumber == v)
			{
				indexOfVertexToMove = i;
			}
		}
		
		Vertex vertex_being_moved = pq.get(indexOfVertexToMove); // extract the vertex that needs to be moved up in the queue
		vertex_being_moved.priority = newPriority; // update that vertex's priority
		pq.remove(indexOfVertexToMove);	// remove the Vertex from the queue
		
		
		// calculate the index of where the Vertex should be moved
		int new_vertex_index = pq.size(); 
		
		while (newPriority < pq.get(new_vertex_index-1).getPriority() )
		{
			new_vertex_index--; 
			if (new_vertex_index <= 0) break;
			
		}
		
		pq.add(new_vertex_index, vertex_being_moved);
		
	}
	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		return -1;
	}

	// This function returns the neighboring ports of node.
	// This function is used to test if you have contructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
		
		for (int i = 0; i<adjList.get(node).size(); i++)
		{
			edges.add(adjList.get(node).get(i).destNode);
		}
		
		return edges;
	}

	public int getNumPorts() {
		return n;
	}
}
