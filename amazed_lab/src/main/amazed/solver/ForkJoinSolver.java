package amazed.solver;

import amazed.maze.Maze;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import amazed.maze.*;


public class ForkJoinSolver
    extends SequentialSolver
{
	private boolean justSpawned = false; //True if player just spawned, false otherwise
	private AtomicBoolean running; //thread-safe boolean. False when someone has found the goal and true otherwise
	private int player; //player id

   public ForkJoinSolver(Maze maze, int forkAfter)
    {
        this(maze);
        this.forkAfter = forkAfter; //regulates amount of parallelism
    }

    public ForkJoinSolver(Maze maze)
    {
        super(maze);
		this.visited = new ConcurrentSkipListSet<Integer>(); //thread safe set of visited cells.
		running = new AtomicBoolean(true);
		this.player = maze.newPlayer(start); //spawns a new player on cell with id start
    }

	public ForkJoinSolver(Maze maze, int start, Set<Integer> visited,AtomicBoolean running,int forkAfter) //New player
    {
		//set instance variables
        super(maze);
		this.start = start;
		this.visited = visited;	
		this.running = running;
		this.forkAfter = forkAfter;
		this.justSpawned = true; //True since new player. Allows new player to move
		this.player = maze.newPlayer(start);
    }
	

    @Override
    public List<Integer> compute()
    {
        return parallelDepthFirstSearch();
    }

    private List<Integer> parallelDepthFirstSearch()
    {
		int steps = 0;
        frontier.push(start);
        while (!frontier.empty() && running.get()) //loop when stack of neighbours not empty and when the goal has not been found
		{
            int current = frontier.pop();
            if (maze.hasGoal(current)) //checks if next position is goal
			{
                maze.move(player, current);
				running.set(false); //if goal is found, set running to false
                return pathFromTo(start, current);//return path from player's start position to current position
            }
            if (justSpawned || visited.add(current)) //checks if next position has already been visited. If not, add it to set of visited nodes.
			{
				/*Below where we spawn a new thread, we add the node on which we spawn to the set of visited nodes before spawning a new player.
				So without the flag justSpawned the above if-statement will evaluate to false for new players and they will not be able to move. 
				We can circumvent this problem by using the boolean justSpawned.*/
				justSpawned = false; 
                maze.move(player, current); //moves player to node
				steps++;
                for (int nb: maze.neighbors(current)) //adds neighbours to frontier stack
				{
                    frontier.push(nb);
                    if (!visited.contains(nb)) //if neighbour is not in the set, then its predecessor node is the current node.
                        predecessor.put(nb, current);
                }
				Set<Integer> notVisitedNB = maze.neighbors(current); //get set of neighbours of current node
				notVisitedNB.removeAll(visited); //removes visited neighbours
				if (notVisitedNB.size() >= 2 && steps >= forkAfter) //checks if it is time to fork
				{
					ArrayList<ForkJoinSolver> list = new ArrayList<ForkJoinSolver>(); 
					notVisitedNB.remove(notVisitedNB.iterator().next()); //removes one element from the set (to make sure that the original thread has room to move)
					for(int nb : notVisitedNB)
					{
						if(visited.add(nb)) //spawns new threads at neighbours that have not been visited and adds neighbour to set of visited nodes
						{
							ForkJoinSolver solver = new ForkJoinSolver(maze, nb, this.visited,running,this.forkAfter);
							solver.fork();
							list.add(solver);
						}						
					}	
					List<Integer> path = this.compute(); //current thread continues search
					if( path != null) //if path to goal has been found, return it
						return path; 
	
					for(ForkJoinSolver solver : list)
					{
						path = solver.join(); 
						if (path != null) 
						{
							List<Integer> newPath = pathFromTo(start,current); //path from original start position to forking position
							newPath.addAll(path); //concatenates newPath with path from forking position to goal.
							return newPath;
						}
					}	
				return null; //if no one has found the goal, return null.
				}
            }
        }
        return null; //if stack is empty or running is false, return null.
    }
}
