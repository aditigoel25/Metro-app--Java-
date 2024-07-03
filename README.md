Metro App
Overview:
The Metro App is a Java application that simulates a metro system. It provides functionalities for users to interact with the metro map, including displaying the map, listing all stations, and finding the shortest path between stations based on distance or time. The application uses a graph data structure to represent the metro map, with vertices representing stations and edges representing the connections between them.

Features:
List All Stations: Displays a list of all stations in the metro map.
Show Metro Map: Displays the entire metro map with connections and distances.
Get Shortest Distance: Finds the shortest distance between two stations.
Get Shortest Time: Finds the shortest travel time between two stations.
Get Shortest Path (Distance): Displays the shortest path between two stations based on distance.
Get Shortest Path (Time): Displays the shortest path between two stations based on travel time.

Graph Data Structure:
The metro map is represented using a graph where:
Each station is a vertex.
Each connection between stations is an edge with a weight representing the distance or time.

Key Classes:
Graph_M: Main class representing the metro graph. Contains methods for adding/removing vertices and edges, and for performing various graph-related operations.
Vertex: Inner class representing a station (vertex) in the metro graph.
DijkstraPair: Inner class used in Dijkstra's algorithm for finding the shortest path.
Heap: Generic class implementing a heap data structure used in Dijkstra's algorithm.

