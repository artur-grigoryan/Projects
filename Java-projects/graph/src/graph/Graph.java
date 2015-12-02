package graph;

import java.util.ArrayList;

public class Graph {
	private ArrayList<Integer> vertices;
	ArrayList<ArrayList<Integer>> edges;
	public Graph(){
		vertices=new ArrayList<Integer>();
		edges=new ArrayList<ArrayList<Integer>>();
		}
		public void addVertice(int n){
			vertices.add(n);
			edges.add(new ArrayList<Integer>());
			
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
