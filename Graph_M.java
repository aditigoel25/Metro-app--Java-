import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Graph_M {
    public class vertex{
        HashMap<String,Integer> nbrs= new HashMap<>();
    }
    static HashMap<String, vertex> vtces;
    public Graph_M(){
       vtces= new HashMap<>();
    }
    public int numVertex(){
        return this.vtces.size();
    }
    public boolean containsVertex(String vname){
        return this.vtces.containsKey(vname);

    }
    public void addVertex(String vname){
        vertex vtx= new vertex();
        vtces.put(vname,vtx);
    }
    public void removeVertex(String vname){
        vertex vtx= vtces.get(vname);
        ArrayList<String> keys= new ArrayList<>(vtx.nbrs.keySet());
        for(String key: keys){
            vertex nbrVtx= vtces.get(key);
            nbrVtx.nbrs.remove(vname);
        }
        vtces.remove(vname);
    }
    public int numEdges(){
        ArrayList<String> keys= new ArrayList<>(vtces.keySet()); //Add names of vertices to list
        int count=0;
        for(String key:keys){
            vertex vtx= vtces.get(key);//retrieve key(station)
            count=count+ vtx.nbrs.size();// count the number of neighbours
        }
        return count/2;
    }
    public boolean containsEdge(String vname1, String vname2){
        vertex vtx1= vtces.get(vname1);
        vertex vtx2= vtces.get(vname2);
        if(vtx1==null|| vtx2==null|| !vtx1.nbrs.containsKey(vname2)){ //if neighbours of vtx1 do not contain vname2
            return false;
        }
        return true;

    }
    public void addEdge(String vname1, String vname2, int value){//value can be dist or time
        vertex vtx1= vtces.get(vname1);
        vertex vtx2= vtces.get(vname2);
        if(vtx1==null|| vtx2==null|| vtx1.nbrs.containsKey(vname2)){
            return;
        }
        vtx1.nbrs.put(vname2,value);
        vtx2.nbrs.put(vname1,value);
    }
    public void removeEdge(String vname1, String vname2, int value){//value can be dist or time
        vertex vtx1= vtces.get(vname1);
        vertex vtx2= vtces.get(vname2);
        if(vtx1==null|| vtx2==null|| !vtx1.nbrs.containsKey(vname2)){
            return;
        }
        vtx1.nbrs.remove(vname2,value);
        vtx2.nbrs.remove(vname1,value);
    }
    public void display_Map()
    {
        System.out.println("\t Delhi Metro Map");
        System.out.println("\t------------------");
        System.out.println("----------------------------------------------------\n");
        ArrayList<String> keys = new ArrayList<>(vtces.keySet());

        for (String key : keys)
        {
            String str = key + " =>\n";
            vertex vtx = vtces.get(key);
            ArrayList<String> vtxnbrs = new ArrayList<>(vtx.nbrs.keySet());

            for (String nbr : vtxnbrs)
            {
                str = str + "\t" + nbr + "\t";
                if (nbr.length()<16)
                    str = str + "\t";
                if (nbr.length()<8)
                    str = str + "\t";
                str = str + vtx.nbrs.get(nbr) + "\n";
            }
            System.out.println(str);
        }
        System.out.println("\t------------------");
        System.out.println("---------------------------------------------------\n");

    }
    public void display_Stations()
    {
        System.out.println("\n***********************************************************************\n");
        ArrayList<String> keys = new ArrayList<>(vtces.keySet());
        int i=1;
        for(String key : keys)
        {
            System.out.println(i + ". " + key);
            i++;
        }
        System.out.println("\n***********************************************************************\n");
    }
    public boolean hasPath(String vname1, String vname2, HashMap<String, Boolean> processed)
    {//Uses DFS to find path
        if(containsEdge(vname1,vname2)){
            return true;
        }
        processed.put(vname1,true);
        vertex vtx= vtces.get(vname1);
        ArrayList<String> nbrs= new ArrayList<>();
        for(String nbr:nbrs){
            if(!processed.containsKey(nbr)){
                if(hasPath(nbr,vname2,processed)){
                    return true;
                }
            }
        }
        return false;
    }
    private class DijkstraPair implements Comparable<DijkstraPair> {
        String vname;//Comparable class is used for comparison and you need to override the compareTo method to make that comparision
        String psf;//Path so far (eg: A->4->B)
        int cost;

        @Override
        public int compareTo(DijkstraPair o){
            return o.cost-this.cost;  //this compares cost of the current object to the object passed in the method. This will give all the costs in descending order
        }//You are returning o.cost - this.cost, which means the objects will be ordered in descending order of cost.
        // This is because if o.cost is greater than this.cost, the result will be positive, indicating that o should come before this in the sorted order.
//This is because DijkstraPair is not an abstract class and implements Comparable interface which has an abstract
       // method compareTo. In order to make our class concrete(a class which provides implementation for all its methods)
			//we have to override the method compareTo
    }
    public int dijkstra(String src, String des, boolean nan){
        int val=0;
        ArrayList<String> ans= new ArrayList<>();
        HashMap<String, DijkstraPair> map= new HashMap<>();
        Heap<DijkstraPair> heap = new Heap<>();
        for(String key: vtces.keySet()){
            DijkstraPair np= new DijkstraPair();
            np.vname= key;
            np.cost=0;
            if(key.equals(src)){
                np.cost=0;
                np.psf=key;
            }
            map.put(key,np);
            heap.add(np);
        }
        while(!heap.isEmpty()){
            DijkstraPair rp= new DijkstraPair();
            if(rp.vname.equals(des)){
                val= rp.cost;
                break;
            }
            map.remove(rp.vname);
            ans.add(rp.vname);

            vertex v= vtces.get(rp.vname);
            for(String nbr: v.nbrs.keySet()){
                if(map.containsKey(nbr)){
                int oc= map.get(nbr).cost;
                int edgeCost= v.nbrs.get(nbr);
                int nc;
                if(nan){
                    nc= rp.cost+ 120+ 40*edgeCost;
                }
                else{
                    nc= rp.cost+ edgeCost;
                }
                if(nc<oc){
                    DijkstraPair gp= map.get(nbr);
                    gp.psf= rp.psf+ nbr;
                    gp.cost= nc;
                    heap.updatePriority(gp);
                }
                }

            }
        }
        return val;
    }
    private class Pair{
        String vname;
        String psf;
        int min_dis;
        int min_time;
    }
    public String Get_Minimum_Distance(String src, String dst){
        int min= Integer.MAX_VALUE;
        String ans=""; //Stores path of the min dist
        HashMap<String, Boolean> processed = new HashMap<>();// stores stations that have been visited
        LinkedList<Pair> stack= new LinkedList<>();// USED FOR DFS
        Pair sp = new Pair();
        sp.vname= src;
        sp.psf= src+ " ";
        sp.min_dis=0;
        sp.min_time=0;
        // put the new pair in stack
        stack.addFirst(sp);
        while(!stack.isEmpty()){
           Pair rp= stack.removeFirst();
           if(processed.containsKey(rp.vname)){
               continue;
           }
           processed.put(rp.vname,true);
           if(rp.vname.equals(dst)){//If the current station is the destination (dst), compare the path's distance with the minimum distance found so far.
               int temp= rp.min_dis;
               if(temp<min){
                   ans= rp.psf;
                   min=temp;
               }
               continue;
           }
           vertex rpvtx= vtces.get(rp.vname);
           ArrayList<String> nbrs= new ArrayList<>(rpvtx.nbrs.keySet());
           for(String nbr: nbrs){
               if(!processed.containsKey(nbr)){
                   Pair np= new Pair();
                   np.vname= nbr;
                   np.psf= rp.psf+ nbr+ " ";
                   np.min_dis= rp.min_dis+ rpvtx.nbrs.get(nbr);//rpvtx.nbrs.get(nbr) is the direct distance from the current vertex to the neighbor.
                   stack.addFirst(np);
               }

           }

        }
        ans= ans+ Integer.toString(min);
        return ans;

    }
    public String Get_Minimum_Time(String src, String dst)
    {
        int min = Integer.MAX_VALUE;
        String ans = "";
        HashMap<String, Boolean> processed = new HashMap<>();
        LinkedList<Pair> stack = new LinkedList<>();

        // create a new pair
        Pair sp = new Pair();
        sp.vname = src;
        sp.psf = src + "  ";
        sp.min_dis = 0;
        sp.min_time = 0;

        // put the new pair in queue
        stack.addFirst(sp);

        // while queue is not empty keep on doing the work
        while (!stack.isEmpty()) {

            // remove a pair from queue
            Pair rp = stack.removeFirst();

            if (processed.containsKey(rp.vname))
            {
                continue;
            }

            // processed put
            processed.put(rp.vname, true);

            //if there exists a direct edge b/w removed pair and destination vertex
            if (rp.vname.equals(dst))
            {
                int temp = rp.min_time;
                if(temp<min) {
                    ans = rp.psf;
                    min = temp;
                }
                continue;
            }

            vertex rpvtx = vtces.get(rp.vname);
            ArrayList<String> nbrs = new ArrayList<>(rpvtx.nbrs.keySet());

            for (String nbr : nbrs)
            {
                // process only unprocessed nbrs
                if (!processed.containsKey(nbr)) {

                    // make a new pair of nbr and put in queue
                    Pair np = new Pair();
                    np.vname = nbr;
                    np.psf = rp.psf + nbr + "  ";
                    np.min_time = rp.min_time + 120 + 40*rpvtx.nbrs.get(nbr);
                    stack.addFirst(np);
                }
            }
        }
        Double minutes = Math.ceil((double)min / 60);
        ans = ans + Double.toString(minutes);
        return ans;
    }
   
    public ArrayList<String> get_Interchanges(String str) {
        ArrayList<String> arr = new ArrayList<>(); // An ArrayList to store the processed path.
        String res[] = str.split("  "); // An array of strings obtained by splitting the input path string by double spaces (" ").
        arr.add(res[0]); // Add the first station to the result array.
        int count = 0; // A counter to keep track of the number of interchanges.
    
        for (int i = 1; i < res.length - 1; i++) {
            int index = res[i].indexOf('~');
            String s = res[i].substring(index + 1); // Extract the substring starting from the character after '~'. This substring represents the line identifier.
    
            if (s.length() == 2) { // Checks if the station is on an interchange line (assuming line identifiers are two characters long).
                String prev = res[i - 1].substring(res[i - 1].indexOf('~') + 1);
                String next = res[i + 1].substring(res[i + 1].indexOf('~') + 1); // prev: Line identifier of the previous station. next: Line identifier of the next station.
    
                if (prev.equals(next)) {
                    arr.add(res[i]);
                } else {
                    arr.add(res[i] + " ==> " + res[i + 1]);
                    i++;
                    count++;
                }
            } else {
                arr.add(res[i]);
            }
        }
        arr.add(res[res.length - 1]); // Add the last station
        arr.add("Number of interchanges: " + count); // Add the count of interchanges
        return arr;
    }
    
    
    public static void Create_Metro_Map(Graph_M g)

    {
        g.addVertex("Noida Sector 62~B");
        g.addVertex("Botanical Garden~B");
        g.addVertex("Yamuna Bank~B");
        g.addVertex("Rajiv Chowk~BY");
        g.addVertex("Vaishali~B");
        g.addVertex("Moti Nagar~B");
        g.addVertex("Janak Puri West~BO");
        g.addVertex("Dwarka Sector 21~B");
        g.addVertex("Huda City Center~Y");
        g.addVertex("Saket~Y");
        g.addVertex("Vishwavidyalaya~Y");
        g.addVertex("Chandni Chowk~Y");
        g.addVertex("New Delhi~YO");
        g.addVertex("AIIMS~Y");
        g.addVertex("Shivaji Stadium~O");
        g.addVertex("DDS Campus~O");
        g.addVertex("IGI Airport~O");
        g.addVertex("Rajouri Garden~BP");
        g.addVertex("Netaji Subhash Place~PR");
        g.addVertex("Punjabi Bagh West~P");

        g.addEdge("Noida Sector 62~B", "Botanical Garden~B", 8);
        g.addEdge("Botanical Garden~B", "Yamuna Bank~B", 10);
        g.addEdge("Yamuna Bank~B", "Vaishali~B", 8);
        g.addEdge("Yamuna Bank~B", "Rajiv Chowk~BY", 6);
        g.addEdge("Rajiv Chowk~BY", "Moti Nagar~B", 9);
        g.addEdge("Moti Nagar~B", "Janak Puri West~BO", 7);
        g.addEdge("Janak Puri West~BO", "Dwarka Sector 21~B", 6);
        g.addEdge("Huda City Center~Y", "Saket~Y", 15);
        g.addEdge("Saket~Y", "AIIMS~Y", 6);
        g.addEdge("AIIMS~Y", "Rajiv Chowk~BY", 7);
        g.addEdge("Rajiv Chowk~BY", "New Delhi~YO", 1);
        g.addEdge("New Delhi~YO", "Chandni Chowk~Y", 2);
        g.addEdge("Chandni Chowk~Y", "Vishwavidyalaya~Y", 5);
        g.addEdge("New Delhi~YO", "Shivaji Stadium~O", 2);
        g.addEdge("Shivaji Stadium~O", "DDS Campus~O", 7);
        g.addEdge("DDS Campus~O", "IGI Airport~O", 8);
        g.addEdge("Moti Nagar~B", "Rajouri Garden~BP", 2);
        g.addEdge("Punjabi Bagh West~P", "Rajouri Garden~BP", 2);
        g.addEdge("Punjabi Bagh West~P", "Netaji Subhash Place~PR", 3);
    }
    public static String[] printCodelist(){
        System.out.println("List of station along with their codes:\n");
        ArrayList<String> keys= new ArrayList<>(vtces.keySet());
        int i=1; int j=0; int m=1; //Counters: i is for indexing the stations, j is for iterating through characters of a token, and m is used for formatting.
        StringTokenizer stname;
        String temp= " "; //temp: Temporarily stores tokens of the station name.
        String codes[]= new String[keys.size()];
        char c;
        for(String key: keys){
            stname= new StringTokenizer(key);// divides key into tokens
            codes[i-1]= "";
            j=0;
            while(stname.hasMoreTokens()){
                temp = stname.nextToken();
                c= temp.charAt(0);
                while (c>47 && c<58) // if char is a number
                {
                    codes[i-1]+= c;
                    j++;
                    c = temp.charAt(j);//Updates c with the next character.
                }
                if ((c<48 || c>57) && c<123) // if char is a number and symbol
                    codes[i-1]+= c;
            }
            if (codes[i-1].length() < 2)
                codes[i-1]+= Character.toUpperCase(temp.charAt(1)); // print first two letters of name(New York -> NY)
            System.out.print(i + ". " + key + "\t");
            if (key.length()<(22-m))
                System.out.print("\t");
            if (key.length()<(14-m))
                System.out.print("\t");
            if (key.length()<(6-m))
                System.out.print("\t");
            System.out.println(codes[i-1]);
            i++;
            if (i == (int)Math.pow(10,m))
                m++;

            }
        return codes;
        }


    }







