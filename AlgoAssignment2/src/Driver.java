import java.io.File;
import java.util.Scanner;

/*
	This Driver file will be replaced by ours during our grading.
*/
public class Driver {
	private static String filename; // input file name 
	private static boolean testTimeOptimal; // set to true by -d flag
	private static boolean testCapacityOptimal; // set to true by -w flag
	private static Program2 testGraph; // instance of your graph
	private static void usage() { // error message
		System.err.println("usage: java Driver [-d] [-w] <filename>");
		System.err.println("\t-d\tTest Dijkstra implementation");
		System.err.println("\t-w\tTest the widest path implementation");
		System.exit(1);
	}
	public static void main(String[] args) throws Exception{
		parseArgs(args); // parse the arguments to main
		parseInputFile(filename); 
        int sourcePort = 0; // default, change for testing
        int destPort = testGraph.getNumPorts() -1; // default, change for testing
		
		// Print out neighbors of every port
		if (testGraph.getNumPorts() < 20) {
			System.out.println("Neighbors:");
			for (int i = 0; i < testGraph.getNumPorts(); i++) {
				System.out.println(i + ": " + testGraph.getNeighbors(i));
			}
		}

		if (testTimeOptimal) {
			System.out.println(testGraph.findTimeOptimalPath(sourcePort, destPort));
		}
		if (testCapacityOptimal) {
			System.out.println(testGraph.findCapOptimalPath(sourcePort, destPort));
		}
	
	}

	public static void parseArgs(String[] args) {
		if (args.length == 0) {
            usage();
        }
        
        filename = "";
        testTimeOptimal = false;
        testCapacityOptimal = false;
        boolean flagsPresent = false;
        
        for (String s : args) {
            if(s.equals("-d")) {
                flagsPresent = true;
                testTimeOptimal = true;
            } else if(s.equals("-w")) {
                flagsPresent = true;
                testCapacityOptimal = true;
            } else if(!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }
        
        if(!flagsPresent) {
            testTimeOptimal = true;
            testCapacityOptimal = true;
        }
	}
	public static void parseInputFile(String filename) 
			throws Exception {
		int n = 0, m = 0;
        Scanner sc = new Scanner(new File(filename));	
		String[]  inputSize = sc.nextLine().split(" ");
		n = Integer.parseInt(inputSize[0]);
		m = Integer.parseInt(inputSize[1]);
		testGraph = new Graph(n);
		for (int i = 0; i < m; i++) {
			String[] pair = sc.nextLine().split(" ");
			int d1 = Integer.parseInt(pair[0]);
			int d2 = Integer.parseInt(pair[1]);
			int t = Integer.parseInt(pair[2]);
			int c = Integer.parseInt(pair[3]);
			testGraph.inputEdge(d1,d2,t,c);
		}
	}
}
