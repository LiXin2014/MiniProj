=======================================================================
=                                                                     =                                                                 =
=   Celebrity Name Game Mini Project                                  =
=                                                                     =
=======================================================================


What is it?
-----------
This is a program allows users to play a celebrity name game that generates a name-chain from the first and last names of people.

How to run this code
-------------
1, Assumes java is installed in the computer, and all necessary configuration is done.
2, Download the MiniProj.jar and input file inside one folder.
3, Run the jar file from command with "java -jar MiniProj.jar". 
4, You should be able to see output in the command now.

5, (Optional) Overwrite input.txt file with any input you want, and repeat above steps.

Possible input and output
-------------
1, For input "A B B C B E E C H E G H F G",
   Output will be "F G H E C".
2, For input "Elton John John Lennon James Elton Lebron James James Faulkner",
   Output will be "Lebron James Elton John Lennon".
3, For input "Elton John John Lennon James Elton Lebron James James Faulkner Taylor Judy Judy Jolie Jolie Donald Donald Pitt Pitt Taylor", 
   Output will be "Jolie Donald Pitt Taylor Judy Jolie".



Approach
-------------
The approach for this project is as following:
1, We create a directed graph based on relationship between names. For input "Elton John John Lennon James Elton Lebron James James Faulkner". A graph as following created:
   
    James Faulkner                
           /\
           |
     Lebron James                John Lennon          
           |                         |
           \/                        \/ 
     James Elton  --------------> Elton John
2, Now, in order to find the longest path in this graph, we can do a DFS on every node. In DFS for a specific node, we find all the paths from that node.
   And then we can find the longest path in all these paths. But in order to be more efficient, I am maintaining a starters list, which will include nodes 
   that can be a start for a path, then we only need to do DFS on nodes that in starters list. 
   For example:
   For input "Lebron James John Lennon Elton John James Elton James Faulkner", the starters will only be left with one node, which is ideal.
   For input "A B B C B E E C H E G H F G", the starters will be left with one nodes, which is still ideal.
   For input "A B B B B C C D B B D B", the starters will be left with two nodes, which is not ideal, explained in detail in comments of MiniProject.java file.



Analysis
-------------
DFS on a node will take O(V+E), and we are doing DFS for all nodes in starters, suppose the number of nodes in starters is N, then the complexity will be O(N*(V+E)).

N should be less than V in all cases.

Code Structure Description:

-------------

Four files in total.
1, Celebrity.java: A Celebrity class which present a Celebrity name and serves as a node in graph.
2, MiniProject.java: Contains main() function, constructs the graph, DFS on all nodes in starters list, and find the longest path.
3, MiniProjectTest.java: Testing code for MiniProject.java.
4, CelebrityTest.java: Testing code for Celebrity.java.

Testing
-------------
When doing Testing, many bugs were found, for example:
 (1) When setting next list for each node in constructor of MiniProject, we should put toLowerCase(), so (li, Judy) will be add into the next list for (Xin, Li).
 (2) Original logic to remove nodes from starters list (which is removing all nodes in next list for each node in starters) won't work if there's cycle in the graph.
 (3) Standard DFS won't work in graph with Cycle, so tweaked the code a little with a Location class.

Limitations:
-------------
Due to the time constraints there are few things which I wanted to implement but couldn’t.
a) Check the input file for validity. 

b) Come up with a more optimized starter list. 

