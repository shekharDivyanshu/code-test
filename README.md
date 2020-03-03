# code-test

# Problem : https://en.wikipedia.org/wiki/Six_Degrees_of_Kevin_Bacon

# Description : 
   Calculate the minimal degrees of separation between two actors. 
   For example, according to the data set supplied below, Pierce Brosnan and Tilda Swinton are 2 degrees apart, because  Brosnan was in a movie with Meryl Streep, and Streep was in a different movie with Tilda Swinton.
   
# Solution :
- [x] Create un-directed graph with all actors and movie title. such that two actors are connected with movie title node.
- [x] Apply BFS to find shortest path between two node.
- [x] Minimal degree = total number of movie node between two node along its shortest path.

# Main class : 
- [x] src/main/java/com/coding/exercise/codetest/KevinBaconProblem.java
