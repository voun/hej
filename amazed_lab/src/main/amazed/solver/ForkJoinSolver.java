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

/**
 * <code>ForkJoinSolver</code> implements a solver for
 * <code>Maze</code> objects using a fork/join multi-thread
 * depth-first search.
 * <p>
 * Instances of <code>ForkJoinSolver</code> should be run by a
 * <code>ForkJoinPool</code> object.
 */

public class ForkJoinSolver
    extends SequentialSolver
{
    /**
     * Creates a solver that searches in <code>maze</code> from the
     * start node to a goal.
     *
     * @param maze   the maze to be searched
     */
    public ForkJoinSolver(Maze maze)
    {
        super(maze);
		this.visited = new ConcurrentSkipListSet<Integer>();
		running = new AtomicBoolean(true);
		this.player = maze.newPlayer(start);
		//this.originalStart = start;
    }
 
/**
	public ForkJoinSolver(Maze maze, int start,int originalstart, Set<Integer> visited,AtomicBoolean running,int forkAfter, int player, Stack<Integer> frontier, Map<Integer, Integer> predecessor) //Not new player
    {
        super(maze);
		this.start = start;
		this.visited = visited;	
		this.running = running;
		this.player = player;
		this.forkAfter = forkAfter;
		this.frontier = frontier;
		this.predecessor = predecessor;
		this.originalStart = originalStart;
    }
**/

	

	public ForkJoinSolver(Maze maze, int start, Set<Integer> visited,AtomicBoolean running,int forkAfter) //New player
    {
        super(maze);
		this.start = start;
		this.visited = visited;	
		this.running = running;
		this.forkAfter = forkAfter;
		this.flag = true;
		this.player = maze.newPlayer(start);
		//this.originalStart = start;
    }
	
    /**
     * Creates a solver that searches in <code>maze</code> from the
     * start node to a goal, forking after a given number of visited
     * nodes.
     *
     * @param maze        the maze to be searched
     * @param forkAfter   the number of steps (visited nodes) after
     *                    which a parallel task is forked; if
     *                    <code>forkAfter &lt;= 0</code> the solver never
     *                    forks new tasks
     */
    public ForkJoinSolver(Maze maze, int forkAfter)
    {
        this(maze);
        this.forkAfter = forkAfter;
    }
	
	
	private boolean flag = false;
	private AtomicBoolean running;
	private int player;
	//private int originalStart;
    /**
     * Searches for and returns the path, as a list of node
     * identifiers, that goes from the start node to a goal node in
     * the maze. If such a path cannot be found (because there are no
     * goals, or all goals are unreacheable), the method returns
     * <code>null</code>.
     *
     * @return   the list of node identifiers from the start node to a
     *           goal node in the maze; <code>null</code> if such a path cannot
     *           be found.
     */
    @Override
    public List<Integer> compute()
    {
        return parallelDepthFirstSearch();
    }

    private List<Integer> parallelDepthFirstSearch() // ändra till concurrentSet i Sequential
    {
		int steps = 0;
        frontier.push(start);
        while (!frontier.empty() && running.get()) 
		{
            int current = frontier.pop();
            if (maze.hasGoal(current)) 
			{
                maze.move(player, current);
				running.set(false);
                return pathFromTo(start, current);
            }
            if (flag || visited.add(current))
			{
				flag = false;
                maze.move(player, current);
				steps++;
                for (int nb: maze.neighbors(current)) 
				{
                    frontier.push(nb);
                    if (!visited.contains(nb))
                        predecessor.put(nb, current);
                }
				Set<Integer> notVisitedNB = maze.neighbors(current);
				notVisitedNB.removeAll(visited);
				if (notVisitedNB.size() >= 2 && steps >= forkAfter)
				{
					ArrayList<ForkJoinSolver> list = new ArrayList<ForkJoinSolver>();
					notVisitedNB.remove(notVisitedNB.iterator().next());
					for(int nb : notVisitedNB)
					{
						if(visited.add(nb))
						{
							ForkJoinSolver solver = new ForkJoinSolver(maze, nb, this.visited,running,this.forkAfter);
							solver.fork();
							list.add(solver);
						}						
					}	
					
					//ForkJoinSolver solv = new ForkJoinSolver(maze,me,this.originalStart,this.visited,running,this.forkAfter, this.player, this.start, this.frontier, this.predecessor);
					List<Integer> path = this.compute();
					if( path != null)
						return path;
	
					for(ForkJoinSolver solver : list)
					{
						path = solver.join();
						if (path != null)
						{
							List<Integer> newPath = pathFromTo(start,current);
							newPath.addAll(path);
							return newPath;
						}
					}	
				return null;	
				}
            }
        }
        return null;		
    }
}
