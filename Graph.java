
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;


public class Graph {
	private HashMap<String, Vertex> vertices;
	private HashMap<String, Edge> edges;
	
	public Graph() {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
		
	}
	public void addEdge(String source, String destination) {
		
		if (edges.get(source + "-" + destination) == null && edges.get(destination + "-" + source) == null) {
			Vertex source_v, destination_v;

			if (vertices.get(source) == null) {
				source_v = new Vertex(source);
				vertices.put(source, source_v);
			} else
				source_v = vertices.get(source);

			if (vertices.get(destination) == null) {
				destination_v = new Vertex(destination);
				vertices.put(destination, destination_v);
			} else
				destination_v = vertices.get(destination);

			Edge edge = new Edge(source_v, destination_v);
			source_v.addEdge(edge);
			destination_v.addEdge(edge);
			edges.put(source + "-" + destination, edge);
		} 
		else {
			System.out.println("This edge has already added!");
		}
	}
	
	 //Calculates closeness and betweenness.
	 public void  calculateClosenessAndBetweenness(String name) {
		 Iterator<Vertex> vertex = vertices.values().iterator();
		 HashMap<Vertex,Integer> closenessValues = new HashMap<Vertex,Integer>();
		 HashMap<Vertex,Integer> betweennessValues = new HashMap<Vertex,Integer>();
		 
		 Vertex[] vertArr = new Vertex[vertices.size()];
		 int iterate = 0;
		//Accessing array takes O(1) time and in this code accessing will be made many times so saves time to just store in an array.
		while(vertex.hasNext()) {
			vertArr[iterate] = vertex.next();
			iterate++;
		}	
		
		 int count2 = 0;
		 double maxCloseness = 0;
		 int maxBetweenness = 0;
		 Vertex maxCVertex = null, maxBVertex = null; 
		 
		 //Loops through 2 vertices to calculate closeness and betweenness.
		 for(int j = 0; j < vertArr.length; j++) {
			 for(int k = j + 1; k < vertArr.length; k++) {				 
				 Integer parameter = findDistance(vertArr[j],vertArr[k],betweennessValues);					
				 if(closenessValues.get(vertArr[k])!= null)
					 	closenessValues.put(vertArr[k],closenessValues.get(vertArr[k]) + parameter);
					else
						closenessValues.put(vertArr[k],parameter);
					count2+=parameter;
							
			 }
			 	 //Finds closeness of one vertex and compares it to maxCloseness to find the max.
				 if(closenessValues.get(vertArr[j]) != null) {
					 double closeness = 1/(count2 + (double) closenessValues.get(vertArr[j]));
					 if(maxCloseness < closeness) {
						 maxCloseness = closeness;
						  maxCVertex = vertArr[j];
					 }
					
				 	}						
				 else {
					 double closeness = 1/((double) count2);
					 if(maxCloseness < closeness) {
						 maxCloseness = closeness;
						 maxCVertex = vertArr[j];
					 }
					
				 	}						
				 count2 = 0;
							 
		 }
		 //Gets betweenness values and compares to find max.
		 Iterator<Integer> iterate2 = betweennessValues.values().iterator();
		 while(iterate2.hasNext()) {
			 int betweenness = iterate2.next();
			 if(maxBetweenness < betweenness) {
				 maxBetweenness = betweenness;
			
			 }
			
		 }
		 //Finds the max betweenness vertex.
		 Iterator<Vertex> getMaxBetweenness = vertices.values().iterator();
		 while(getMaxBetweenness.hasNext()) {
			 Vertex currentVertex = getMaxBetweenness.next();
			 if(betweennessValues.get(currentVertex) != null) {
				 int betweenness = betweennessValues.get(currentVertex);
				 if(betweenness == maxBetweenness) {
					 maxBVertex = currentVertex;
					 break;
				 }
			 }
			 
		 }
		
		 System.out.println(name + " --- " + "Highest node for betweenness: " + maxBVertex.getName() + " Value: " + maxBetweenness);
		 System.out.println(name + " --- " + "Highest node for closeness: " + maxCVertex.getName() + " Value: " + maxCloseness);
		
		 
	 }
	 //Finds distance between two nodes and finds the nodes between that distances and adds it to betweennessValues.
	 private Integer findDistance(Vertex source, Vertex destination,HashMap<Vertex,Integer> betweennessValues)
	    {
		 HashMap<Vertex, Vertex> path = new HashMap<Vertex,Vertex>();
		 HashMap<Vertex, Integer> distance = new HashMap<Vertex,Integer>();
		 
		 //Uses bfs to find the path and uses the vertices in the path to determine betweenness values.
	     if(bfs(source,destination,path,distance)) {	    	
	         Vertex traceBack = destination;
	      	       
	         while (path.containsKey(traceBack)) {
	        	 //path2.add(pred2.get(crawl2));
	        	 if(betweennessValues.get(traceBack) == null) {
	        		 betweennessValues.put(traceBack, 1);
	        	 }
	        	 else {
	        		 betweennessValues.put(traceBack, betweennessValues.get(traceBack) + 1);
	        	 }	        	
	        	 traceBack = path.get(traceBack);
	         }	      
		 	}	           
	     return distance.get(destination);	
	    }
	 	
	//Bfs search algorithm with slight alterations to find the shortest path.
	private boolean bfs(Vertex source,Vertex dest,HashMap<Vertex,Vertex> pred, HashMap<Vertex, Integer> dist)
		{
		LinkedList<Vertex> queue = new LinkedList<Vertex>();
		HashMap<Vertex, Boolean> visited = new HashMap<Vertex,Boolean>();							
		 Iterator<Vertex> vertex = vertices.values().iterator();
		 while(vertex.hasNext()) {
			 Vertex currentVertex = vertex.next();
			 visited.put(currentVertex, false);
			 dist.put(currentVertex, Integer.MAX_VALUE);
		 }
					
		visited.replace(source, true);
		
		dist.replace(source, 0);
		queue.add(source);
		
		while (!queue.isEmpty()) {
			Vertex u = queue.remove();
			for (int i = 0; i < u.getEdges().size(); i++) {
				Vertex checkVertex;
				if(u.getEdges().get(i).getDestination() == u) {
					checkVertex = u.getEdges().get(i).getSource();				
				}
				else {
					checkVertex = u.getEdges().get(i).getDestination();	
				}
				if (!visited.get(checkVertex)) {
				   visited.replace(checkVertex, true);
				   dist.replace((checkVertex), dist.get(u)+1);				  
				  
				   pred.put(checkVertex,u);
				   queue.add(checkVertex);							  
				  
				   if (checkVertex == dest) {
					   return true;
				   }
				      
				}
			}
		}
		
		return false;
		}
	
	
	public void print() {

		System.out.println("Source\tDestination");
		for (Edge e : edges.values()) {
			System.out.println("" + e.getSource().getName() + "\t" + e.getDestination().getName() + "\t\t"  + " ");
		}
	}
	

	public Iterable<Vertex> vertices() {
		return vertices.values();
	}

	public Iterable<Edge> edges() {
		return edges.values();
	}

	public int size() {
		return vertices.size();
	}

}
