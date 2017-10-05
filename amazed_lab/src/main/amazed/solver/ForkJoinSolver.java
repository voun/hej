package amazed.solver;

import amazed.maze.Maze;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
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
    }

	   
	public ForkJoinSolver(Maze maze, int start, ConcurrentSkipListSet<Integer> visited,AtomicBoolean running)
    {
        super(maze);
		this.start = start;
		this.visited = visited;	
		this.running = running;
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
	private AtomicBoolean running;
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
		int player = maze.newPlayer(start);
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
            if (visited.add(current))
			{
                maze.move(player, current);
                for (int nb: maze.neighbors(current)) 
				{
                    frontier.push(nb);
                    if (!visited.contains(nb))
                        predecessor.put(nb, current);
                }
				Set<Integer> notVisitedNB = maze.neighbors(current);
				notVisitedNB.removeAll(visited);
				if (notVisitedNB.size() >= 2)
				{
					ArrayList<ForkJoinSolver> list = new ArrayList<ForkJoinSolver>();
					int me = notVisitedNB.iterator().next();
					notVisitedNB.remove(me);
					for(int nb : notVisitedNB)
					{
						ForkJoinSolver solver = new ForkJoinSolver(maze, nb, this.visited,running);
						solver.fork();
						list.add(solver);				
					}	
					

					ForkJoinSolver solv = new ForkJoinSolver(maze,me,this.visited,running);
					List<Integer> path = solv.compute();

					if( path != null)
					{
						List<Integer> newPath = pathFromTo(start,current);
						newPath.addAll(path);
						return newPath;
					}
	
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
