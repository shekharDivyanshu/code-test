package com.coding.exercise.codetest;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Patient Ping coding exercise
 * Kevin-Bacon problem solution.
 * Wiki : https://en.wikipedia.org/wiki/Six_Degrees_of_Kevin_Bacon
 *
 * @Author Divyanshu Shekhar
 */
public class KevinBaconProblem {

    /**
     * Retrieved the minimal degree between two nodes in and undirected
     * graph.
     * In graph two actor node is connected with a movie node.
     * therefor total minimal degree between two actor node is the count of total
     * movie node between them along shortest path.
     *
     * @param graph
     * @param movieSet
     * @param startNode
     * @param endNode
     * @param <T>
     * @return
     */
    public static <T> int getMinimalDegree(Graph<T> graph, Set<T> movieSet, Node<T> startNode, Node<T> endNode){
        Node<T> node = shortestPath(graph, startNode, endNode);

        if(null == node) return -1;

        int minimalDegree = 0;

        while(null != node.getParent()){
            Node<T> parent = node.getParent();
            if(movieSet.contains(parent.value)) minimalDegree++;
            node = parent;
        }
        return minimalDegree;
    }

    /**
     * Retrieved Degree path in string format joined with "->"
     *
     * @param graph
     * @param startNode
     * @param endNode
     * @param <T>
     * @return
     */
    public static <T> String getDegreeString(Graph<T> graph, Node<T> startNode, Node<T> endNode){
        Node<T> returnNode = shortestPath(graph, startNode, endNode);
        if(null == returnNode) return "";

        Stack<T> stack = new Stack<>();
        if(startNode != returnNode.getParent()) {
            while(null != returnNode.getParent()){
                stack.push(returnNode.value);
                Node<T> parent = returnNode.getParent();
                returnNode = parent;
            }
            stack.push(returnNode.value);
        }
        StringBuilder builder = new StringBuilder();
        while(!stack.isEmpty()){
            builder.append(stack.pop());
            if(!stack.isEmpty()){
                builder.append(" -> ");
            }
        }
        return builder.toString();
    }

    /**
     * Algorithm to find shortest path between two nodes of a graph.
     * Details : Breadth-First-Search traversal starts
     * from startNode . On each layer of node is not visited it gets added to
     * queue for further traversal.
     * It returns as soon as it finds the endNode.
     *
     * Graph is un - directed graph.
     * Graph is made up of actors and movieName. Two actor node is connected
     * with a movie node.
     *
     * @param graph
     * @param startNode
     * @param endNode
     * @param <T>
     * @return
     */
    private static <T> Node<T> shortestPath(Graph<T> graph, Node<T> startNode, Node<T> endNode){
        if(null == graph || null == startNode || null == endNode) return null;
        graph.reset();
        if(startNode == endNode) {
            endNode.setParent(startNode);
            return endNode;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        startNode.setVisited(true);
        queue.offer(startNode);
        while(!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i<size; i++){
                Node<T> currentNode = queue.poll();
                if(currentNode == endNode){
                    break;
                }else{
                    List<Node<T>> edges = currentNode.getEdges();
                    for(Node<T> edge : edges){
                        if(!edge.isVisited()){
                            edge.setVisited(true);
                            edge.setParent(currentNode);
                            queue.offer(edge);
                        }
                    }
                }
            }
        }
        return endNode;
    }

    /**
     *
     * @param startNode
     * @param endNode
     * @param <T>
     */
    private static <T> void printResult(Graph<T> graph, Set<T> movieSet, Node<T> startNode, Node<T> endNode){
        System.out.println(
                String.format(
                        "Degree of Separation between %s and %s is %d ",
                        startNode.value,
                        endNode.value,
                        getMinimalDegree(graph, movieSet, startNode, endNode)
                )
        );
        System.out.println(
                String.format(
                        "Degree in String format between %s and %s is %s",
                        startNode.value,
                        endNode.value,
                        getDegreeString(graph, startNode, endNode)
                ));

    }


    /**
     *
     * Create a undirected graph.
     * @param args
     */
    public static void main( String[] args){
        /**
         *  Input Data
         *  TODO: For enhancement : Replace it with Json format and use Gson library to convert to java object.
         */
        Map<String, List<String>> movieData = new HashMap<>();
        movieData.put(GoldenEye, Arrays.asList(Pierce_Brosnan, Sean_Bean, Izabella_Scorupco, Judi_Dench, Desmond_Llewelyn, Minnie_Driver));
        movieData.put(Mama_Mia, Arrays.asList(Meryl_Streep, Amanda_Seyfried, Pierce_Brosnan, Colin_Firth, Stellan_Skarsgard));
        movieData.put(Good_Will_Hunting, Arrays.asList(Matt_Damon, Robin_Williams, Ben_Affleck, Stellan_Skarsgard, Minnie_Driver));
        movieData.put(Ronin, Arrays.asList(Robert_De_Niro, Jean_Reno, Natascha_McElhone, Sean_Bean, Stellan_Skarsgard, Jonathan_Pryce));
        movieData.put(Awakenings, Arrays.asList(Robin_Williams, Robert_De_Niro, Julie_Kavner, John_Heard, Penelope_Ann_Miller, Max_von_Sydow));
        movieData.put(Adaptation, Arrays.asList(Nicolas_Cage, Meryl_Streep, Chris_Cooper, Cara_Seymour, Tilda_Swinton, Ron_Livingston, Maggie_Gyllenhaal, Judy_Greer));
        movieData.put(National_Treasure, Arrays.asList(Nicolas_Cage, Sean_Bean, Harvey_Keitel, Jon_Voight, Justin_Bartha, Diane_Kruger, Christopher_Plummer));
        movieData.put(Mission_Impossible, Arrays.asList(Tom_Cruise, Jon_Voight, Emmanuelle_Beart, Ving_Rhames, Vanessa_Redgrave, Henry_Czerny, Jean_Reno, Emilio_Estevez));

        Set<String> movieSet = movieData.keySet().stream().collect(Collectors.toSet());

        /**
         *  Start creating un directed graph where each actor is connect to each other via movie node.
         *  Actor - Movie - Actor
         */

        Graph<String> graph = new Graph<>();

        // add nodes to graph
        movieData.entrySet().stream().forEach(p -> {
            Node<String> movieNode = new Node(p.getKey());
            graph.addNode(movieNode);

            // add node for each actor also
            p.getValue().stream().forEach(k ->{
                Node<String> actorNode = graph.getNode(k);
                if(null == actorNode) actorNode = new Node<>(k);
                graph.addNode(actorNode);
                movieNode.addEdge(actorNode);
                // since this is undirected graph actor node also has edge to movie node
                actorNode.addEdge(movieNode);
            });
        });

        /**
         * Test Cases
         */
        Node<String> startNode = graph.getNode(Pierce_Brosnan);
        Node<String> endNode = graph.getNode(Tilda_Swinton);
        printResult(graph, movieSet, startNode, endNode);

        startNode = graph.getNode(Pierce_Brosnan);
        endNode = graph.getNode(Meryl_Streep);
        printResult(graph, movieSet, startNode, endNode);

    }

    private static final String GoldenEye = "GoldenEye";
    private static final String Mama_Mia ="Mama Mia!";
    private static final String Good_Will_Hunting = "Good Will Hunting";
    private static final String Ronin = "Ronin";
    private static final String Awakenings = "Awakenings";
    private static final String Adaptation = "Adaptation";
    private static final String National_Treasure = "National Treasure";
    private static final String Mission_Impossible ="Mission: Impossible";

    private static final String Pierce_Brosnan = "Pierce Brosnan";
    private static final String Sean_Bean = "Sean Bean";
    private static final String Izabella_Scorupco = "Izabella Scorupco";
    private static final String Judi_Dench = "Judi Dench";
    private static final String Desmond_Llewelyn = "Desmond Llewelyn";
    private static final String Minnie_Driver = "Minnie Driver";
    private static final String Meryl_Streep = "Meryl Streep";
    private static final String Amanda_Seyfried = "Amanda Seyfried";
    private static final String Colin_Firth = "Colin Firth";
    private static final String Stellan_Skarsgard = "Stellan Skarsgard";
    private static final String Matt_Damon = "Matt Damon";
    private static final String Robin_Williams = "Robin Williams";
    private static final String Ben_Affleck = "Ben Affleck";
    private static final String Jean_Reno = "Jean Reno";
    private static final String Jonathan_Pryce = "Jonathan Pryce";
    private static final String Robert_De_Niro = "Robert De Niro";
    private static final String Natascha_McElhone = "Natascha McElhone";
    private static final String Julie_Kavner = "Julie Kavner";
    private static final String John_Heard = "John Heard";
    private static final String Penelope_Ann_Miller = "Penelope Ann Miller";
    private static final String Max_von_Sydow = "Max von Sydow";
    private static final String Nicolas_Cage = "Nicolas Cage";
    private static final String Chris_Cooper = "Chris Cooper";
    private static final String Cara_Seymour = "Cara Seymour";
    private static final String Tilda_Swinton = "Tilda Swinton";
    private static final String Ron_Livingston = "Ron Livingston";
    private static final String Maggie_Gyllenhaal = "Maggie Gyllenhaal";
    private static final String Judy_Greer = "Judy Greer";
    private static final String Harvey_Keitel = "Harvey Keitel";
    private static final String Jon_Voight = "Jon Voight";
    private static final String Justin_Bartha = "Justin Bartha";
    private static final String Diane_Kruger = "Diane Kruger";
    private static final String Christopher_Plummer = "Christopher Plummer";
    private static final String Tom_Cruise = "Tom Cruise";
    private static final String Emmanuelle_Beart = "Emmanuelle Beart";
    private static final String Ving_Rhames = "Ving Rhames";
    private static final String Vanessa_Redgrave = "Vanessa Redgrave";
    private static final String Henry_Czerny = "Henry Czerny";
    private static final String Emilio_Estevez = "Emilio Estevez";

}



