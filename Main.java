import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Graph_M g = new Graph_M();
        Graph_M.Create_Metro_Map(g);
        System.out.println("\n\t\t\t****WELCOME TO THE METRO APP*****");
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            System.out.println("\t\t\t\t~~LIST OF ACTIONS~~\n\n");
            System.out.println("1. LIST ALL THE STATIONS IN THE MAP");
            System.out.println("2. SHOW THE METRO MAP");
            System.out.println("3. GET SHORTEST DISTANCE FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("4. GET SHORTEST TIME TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("5. GET SHORTEST PATH (DISTANCE WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("6. GET SHORTEST PATH (TIME WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("7. EXIT THE MENU");
            System.out.print("\nENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 7) : ");
            int choice = -1;
            try {
                choice = Integer.parseInt(inp.readLine());
            } catch(Exception e) {
                // default will handle
            }
            System.out.print("\n***********************************************************\n");
            if(choice == 7)
            {
                System.exit(0);
            }
            switch(choice)
            {
                case 1:
                    g.display_Stations();
                    break;

                case 2:
                    g.display_Map();
                    break;
                case 3:
                    ArrayList<String> keys= new ArrayList<>(Graph_M.vtces.keySet());
                    String codes[]= Graph_M.printCodelist();
                    System.out.println("\n1. TO ENTER SERIAL NO. OF STATIONS\n2. TO ENTER CODE OF STATIONS\n3. TO ENTER NAME OF STATIONS\n");
                    System.out.println("ENTER YOUR CHOICE:");
                    int ch = Integer.parseInt(inp.readLine());
                    int j;
                    String st1= "", st2="";
                    System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
                    if(ch==1){//Entered serial no. of stations
                        st1= keys.get(Integer.parseInt(inp.readLine())-1);
                        st2=keys.get(Integer.parseInt(inp.readLine())-1);
                    }
                    else if(ch==2){//code for stations
                        String a,b;
                        a= (inp.readLine()).toUpperCase();
                        for (j=0;j<keys.size();j++)
                            if (a.equals(codes[j]))
                                break;
                        st1 = keys.get(j);
                        b = (inp.readLine()).toUpperCase();
                        for (j=0;j<keys.size();j++)
                            if (b.equals(codes[j]))
                                break;
                        st2 = keys.get(j);
                    }
                    else if(ch==3){
                        st1 = inp.readLine();
                        st2 = inp.readLine();
                    }
                    else
                    {
                        System.out.println("Invalid choice");
                        System.exit(0);
                    }
                    HashMap<String, Boolean> processed = new HashMap<>();
                    if(!g.containsVertex(st1) || !g.containsVertex(st2) || !g.hasPath(st1, st2, processed))
                        System.out.println("THE INPUTS ARE INVALID");
                    else
                        System.out.println("SHORTEST DISTANCE FROM "+st1+" TO "+st2+" IS "+g.dijkstra(st1, st2, false)+"KM\n");
                    break;
                case 4:
                    System.out.print("ENTER THE SOURCE STATION: ");
                    String sat1 = inp.readLine();
                    System.out.print("ENTER THE DESTINATION STATION: ");
                    String sat2 = inp.readLine();

                    HashMap<String, Boolean> processed1= new HashMap<>();
                    System.out.println("SHORTEST TIME FROM ("+sat1+") TO ("+sat2+") IS "+g.dijkstra(sat1, sat2, true)/60+" MINUTES\n\n");
                    break;
                case 5: System.out.println("ENTER THE SOURCE AND DESTINATION STATIONS");
                    String s1 = inp.readLine();
                    String s2 = inp.readLine();

                    HashMap<String, Boolean> processed2 = new HashMap<>();
                    if (!g.containsVertex(s1) || !g.containsVertex(s2) || !g.hasPath(s1, s2, processed2)) {
                        System.out.println("THE INPUTS ARE INVALID");
                    } else {
                        ArrayList<String> str = g.get_Interchanges(g.Get_Minimum_Distance(s1, s2));
                        int len = str.size();
                        System.out.println("SOURCE STATION : " + s1);
                        System.out.println("DESTINATION STATION : " + s2);
                        System.out.println("DISTANCE : " + str.get(len - 1) + " km");
                        System.out.println("NUMBER OF INTERCHANGES : " + str.get(len - 2));
                        System.out.println("~~~~~~~~~~~~~");
                        System.out.println("START  ==>  " + str.get(0));
                        for (int i = 1; i < len - 3; i++) {
                            System.out.println(str.get(i));
                        }
                        System.out.println(str.get(len - 3) + "   ==>    END");
                        System.out.println("~~~~~~~~~~~~~");
                    }
                    break;


                case 6:
                    System.out.print("ENTER THE SOURCE STATION: ");
                    String ss1 = inp.readLine();
                    System.out.print("ENTER THE DESTINATION STATION: ");
                    String ss2 = inp.readLine();

                    HashMap<String, Boolean> processed3 = new HashMap<>();
                    if(!g.containsVertex(ss1) || !g.containsVertex(ss2) || !g.hasPath(ss1, ss2, processed3))
                        System.out.println("THE INPUTS ARE INVALID");
                    else
                    {
                        ArrayList<String> str = g.get_Interchanges(g.Get_Minimum_Time(ss1, ss2));
                        int len = str.size();
                        System.out.println("SOURCE STATION : " + ss1);
                        System.out.println("DESTINATION STATION : " + ss2);
                        System.out.println("TIME : " + str.get(len-1)+" MINUTES");
                        System.out.println("NUMBER OF INTERCHANGES : " + str.get(len-2));
                        //System.out.println(str);
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        System.out.print("START  ==>  " + str.get(0) + " ==>  ");
                        for(int i=1; i<len-3; i++)
                        {
                            System.out.println(str.get(i));
                        }
                        System.out.print(str.get(len-3) + "   ==>    END");
                        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    }
                    break;
                default:  //If switch expression does not match with any case,
                    //default statements are executed by the program.
                    //No break is needed in the default case
                    System.out.println("Please enter a valid option! ");
                    System.out.println("The options you can choose are from 1 to 6. ");

            }







        }
    }}

