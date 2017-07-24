package solver;

import java.util.ArrayList;
import java.util.List;
import controller.CommandLoadFileName;
import search.BFS;
import search.Position;
import search.Solution;
import strips.Action;
import strips.Strips;
import model.Utilities;

/**
 * This class is the class that using all the solver parts and combine them in order to solve a level
 *
 */

public class SokobanSolver {

	static Position PS;
	static Position PD;

	public  String solve(String levelStr) 
	{
		//local variables
		BFS<StateM> bfs = new BFS<StateM>();
		Utilities u = new Utilities();
		CommandLoadFileName c =new CommandLoadFileName();
		List<String> r = new ArrayList<>();
		String fin = new String();
		Position d;
		Move se;
		int col = 0;
		String val;
		model.data.Level l= new model.data.Level(); 
		Position b;
		Solution sol = null;
		int row = 0;
		int counter=0;
		int z=0;
		int col9 =0;
		int t =0;
		
			
			//casting ArrayList<ArrayList<GameObject>> to char[][]
			
			//getting the biggest row in the level
			int max =0;
			for(int i=0;i<levelStr.length();i++)
			{
				if(levelStr.charAt(i)!='9')
				{
					counter ++;
				}
				
				else
				{
					
					if(counter>=max)
					{
						max=counter;
						counter=0;
					}
					
					col9++;
				}
			}
			
			//creating the level matrix
			char [][] level = new char [col9][max];

			//filling the level char matrix from the rrayList<ArrayList<GameObject>> data
			for (int j = 0; j < levelStr.length(); j++)
			{
			
				
					if(levelStr.charAt(j)=='#')
					{
						
						level[z][t] = '#';
						++t;
					}
					
					else if(levelStr.charAt(j)=='@')
					{
						level[z][t] = '@';
						t++;
					}
					
					if(levelStr.charAt(j)=='o')
					{
						level[z][t] = 'o';
						t++;
					}
					
					if(levelStr.charAt(j)==' ')
					{
						level[z][t] = ' ';
						t++;
					}
					
					if(levelStr.charAt(j)=='A')
					{
						level[z][t] = 'A';
						t++;
					}		
					
					if(levelStr.charAt(j)=='9')
					{
						z++;
						t=0;
					}	
			}
			System.out.println();
			for (int i = 0; i < col9; i++) {
				for (int j = 0; j < max; j++) {
					
					System.out.print(level[i][j]);
				}
			
			}
			
			//creating the plannable problem
			SokobanPlannable sokoplan = new SokobanPlannable(level);
			Strips s = new Strips();

			List<Action> list = s.plan(sokoplan.readLevel());
			
			for (int i = 0; i < list.size(); i++)
			{
				 if (list.get(i).toString().startsWith("push")) 
				{

					val = list.get(i).toString().substring(list.get(i).toString().indexOf('=') + 1,
							list.get(i).toString().length());
					row = Integer.parseInt(val.substring(0, val.indexOf(',')));
					col = Integer.parseInt(val.substring(val.indexOf(',') + 1, val.length()));
					d = new Position(row, col);

					val = list.get(i).toString().substring(list.get(i).toString().indexOf('_') + 1,
							list.get(i).toString().indexOf('='));
					row = Integer.parseInt(val.substring(0, val.indexOf(',')));
					col = Integer.parseInt(val.substring(val.indexOf(',') + 1, val.length()));
					b = new Position(row, col);

					Push se2 = new Push(level, PD, b, d);
					sol = bfs.search(se2);

					if (sol != null) 
					{

						for (search.Action a : sol.getActions()) {
							if (a.getMiniaction() != null) {
								for (search.Action bl : a.getMiniaction()) {
									r.add(bl.getName());
								}
							}
							r.add(a.getName());
						}
					}
					
					if (list.size() > i + 1) 
					{
						level[b.getRow()][b.getCol()] = ' ';
						level[PD.getRow()][PD.getCol()] = ' ';
						level[d.getRow()][d.getCol()] = '@';

						val = list.get(i + 1).toString().substring(list.get(i + 1).toString().indexOf('_') + 1,
								list.get(i + 1).toString().indexOf('='));
						row = Integer.parseInt(val.substring(0, val.indexOf(',')));
						col = Integer.parseInt(val.substring(val.indexOf(',') + 1, val.length()));

						level[row][col] = 'A';
					}
				}

				 else if (list.get(i).toString().startsWith("move"))
				{
					val = list.get(i).toString().substring(list.get(i).toString().indexOf('=') + 1,
							list.get(i).toString().length());
					row = Integer.parseInt(val.substring(0, val.indexOf(',')));
					col = Integer.parseInt(val.substring(val.indexOf(',') + 1, val.length()));
					PD = new Position(row, col);

					val = list.get(i).toString().substring(list.get(i).toString().indexOf('_') + 1,
							list.get(i).toString().indexOf('='));
					row = Integer.parseInt(val.substring(0, val.indexOf(',')));
					col = Integer.parseInt(val.substring(val.indexOf(',') + 1, val.length()));

					PS = new Position(row, col);

					se = new Move(level, PS, PD);

					sol = bfs.search(se);

					level[PS.getRow()][PS.getCol()] = ' ';
					level[PD.getRow()][PD.getCol()] = 'A';
					for (search.Action a : sol.getActions())
					{
						System.out.println(a);
						r.add(a.getName());
					
					}
					
				}
				 
				 
			}
			System.out.println(r);
			
			for(int w=0;w<r.size();w++)
			{
				if(r.get(w).equals("move right"))
				{
					fin+='R';
				}
				
				if(r.get(w).equals("move left"))
				{
					fin+='L';
				}
				
				if(r.get(w).equals("move up"))
				{
					fin+='U';
				}
				
				if(r.get(w).equals("move down"))
				{
					fin+='D';
				}
			}
			System.out.println(fin);
			return fin;
		}
}