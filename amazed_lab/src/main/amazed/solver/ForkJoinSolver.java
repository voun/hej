package amazed.solver;

import amazed.maze.Maze;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.Iterator;

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
		this.flag = false;
		num++;
    }

	   
	public ForkJoinSolver(Maze maze, int start, ConcurrentSkipListSet<Integer> visited,boolean flag)
    {
        super(maze);
		this.start = start;
		this.visited = visited;	
		this.flag = flag;	
		num++;	
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
	private boolean flag;
	private static volatile boolean running = true;
	private static int num = 0;
	private ForkJoinSolver parent;
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
		//running = true;
        return parallelDepthFirstSearch();
    }

    private List<Integer> parallelDepthFirstSearch()
    {
		
		//System.out.println("hej");
        int player = maze.newPlayer(start);
        frontier.push(start);
        while (!frontier.empty() && running) 
		{
            int current = frontier.pop();
            if (maze.hasGoal(current)) 
			{
                maze.move(player, current);
                return pathFromTo(start, current);
            }
            if (flag || visited.add(current)) 
			{
				flag = false;
                maze.move(player, current);
                for (int nb: maze.neighbors(current)) 
				{
                    frontier.push(nb);
                    if (!visited.contains(nb))
                        predecessor.put(nb, current);
                }
            }

			Set<Integer> notVisitedNB = maze.neighbors(current);
			notVisitedNB.removeAll(visited);
			if (notVisitedNB.size() >= 2)
			{
				ArrayList<ForkJoinSolver> list = new ArrayList<ForkJoinSolver>();
				for(int nb : maze.neighbors(current))
				{

					if(visited.add(nb))
					{
						//System.out.println("hej");
						ForkJoinSolver solver = new ForkJoinSolver(maze, nb, this.visited,true);
						solver.fork();
						list.add(solver);
					}	
				}	
				Iterator<ForkJoinSolver> iter = list.iterator(); //cyclic iterator	try{this.wait();}
				while(iter.hasNext())
				{
					//System.out.println(running);
					ForkJoinSolver solver = iter.next();
					if(solver.isDone())
					{
						List<Integer> path = solver.join();
						if (path.size() > 0)
						{
							running = false;
							List<Integer> newPath = pathFromTo(start,current);
							newPath.addAll(path);
							return newPath;
						}
						else
							iter.remove();

						try{this.wait();}
						catch(Exception e){}
					}
					if(!iter.hasNext()) 
						iter = list.iterator(); //restart att beginning of list
				}	
			return new ArrayList<Integer>();	
			}
        }
		//System.out.println("hej");
        return new ArrayList<Integer>();
		
    }
}
