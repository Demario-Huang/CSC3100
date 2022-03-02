import java.io.*;
import java.util.*;
import java.util.PriorityQueue;


public class Puzzle8 {

	static int zero_initial_x;
	static int zero_initial_y;
	static int [][] initial_state = new int[3][3];
	static int cont = 0;
	static int temp = 0;
	static final int [][] final_state = 
	{
		{1,2,3},
		{4,5,6},
		{7,8,0}
	} ;

	public static class State{
		int[][]  map ;
		int 	current_level;
		int     cost;
		int 	zerox;
		int 	zeroy;
		int		last_move; // represent the direction of the last move. 1 is up, 2 is down, 3 is left, 4 is right
		State   parent;

		public State(int[][] map, int current_level,int zero_x,int zero_y){
			this.map = map;
			this.cost = calculate_cost(this.map,current_level);
			this.zerox = zero_x;
			this.zeroy = zero_y;
			this.parent = null;
			this.last_move = 0;
		}

		public State(int[][] map, int current_level, int zerox, int zeroy, int down, int right, State parent, int last_move){
			this.map = new int[3][3];
			for(int i = 0; i < 3; ++i){
				for(int j = 0; j < 3; ++j){
					this.map[i][j] = map[i][j];
				}
			}
			this.map[zerox + down][zeroy + right] = 0;
			this.map[zerox][zeroy] = map[zerox + down][zeroy + right];
			this.current_level = current_level;
			this.cost = calculate_cost(this.map,current_level);
			this.zerox = zerox + down;
			this.zeroy = zeroy + right;
			this.parent = parent;
			this.last_move = last_move;
		}
	}

	public static int calculate_cost(int[][] map, int current_level){
		int result = 0;

		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				if(map[i][j] != 0){
					int original_row = (map[i][j]-1) / 3;
					int original_col = (map[i][j]-1) % 3;
					int deltax = i -  original_row;
					int deltay = j - original_col;
					if (deltax < 0) deltax = -deltax;
					if (deltay < 0) deltay = -deltay;
					result += deltax + deltay;
				}
			}
		}

		return (result +current_level) ;
	}
    
	public static void IO_In(String[] args) throws Exception {

		BufferedReader reader = java.nio.file.Files.newBufferedReader(java.nio.file.Paths.get(args[0]));

		String line;
		int row = 0;
		while((line = reader.readLine()) != null){
			String[] tokens = line.split(" ");
			for(int j = 0; j < 3; ++j){
				initial_state[row][j] = Integer.parseInt(tokens[j]);
				if(initial_state[row][j] == 0){
					zero_initial_x = row;
					zero_initial_y = j;
				}
			}
			row++;
		}
	}

	public static void PrintMap(State state,BufferedWriter bwriter, int flag)throws Exception{
		for(int i = 0; i < 3; ++i){
			for(int j = 0; j < 3; ++j){
				if(j != 2) bwriter.write(Integer.toString(state.map[i][j]) + " ") ;
				else bwriter.write(Integer.toString(state.map[i][j]));
			}
			if(i !=2 || flag != 1)bwriter.newLine();
		}
		if(flag != 1)bwriter.newLine();
	}

	public static void PrintPath(State state, BufferedWriter bwriter)throws Exception{
		if(state.parent == null){
			PrintMap(state, bwriter,0);
			temp--;
			return;
		}
		cont++;
		temp++; 
		PrintPath(state.parent,bwriter);
		if(temp == 0)PrintMap(state, bwriter,1); // The last state;
		else PrintMap(state, bwriter,0);
		temp--;
	}


	public static void IO_Out(int[][] initial_state, int[][] final_state, int x, int y, String[] args) throws Exception{
		
		int current_level = 0;
		Queue<State> queue = new PriorityQueue<State>(new Comparator<State>() {
			public int compare(State e1, State e2) {
				return (e1.cost - e2.cost);
			}
		});

		State Root = new State(initial_state,current_level, zero_initial_x, zero_initial_y);
		queue.offer(Root);
		int flag = 0;

		FileWriter fwriter = new FileWriter(args[1], false);
		BufferedWriter bwriter = new BufferedWriter(fwriter);


		while(queue.size() != 0 && flag == 0) {
			
			State current_state = queue.poll();


			if(current_state.zerox == 2 && current_state.zeroy == 2){
                int find = 1;
                for(int i = 0; i < 3; ++i){
                    for(int j = 0; j < 3; ++j){
                        if(current_state.map[i][j] != final_state[i][j]){
                            find = 0;
                        }
                    }
                }
                if(find == 1){
                    flag = 1;
					PrintPath(current_state, bwriter);
                } 
            }

			// only move right
			if(current_state.zeroy == 0) {
				if(current_state.last_move != 3) queue.offer(new State(current_state.map, current_state.current_level + 1,current_state.zerox, current_state.zeroy, 0, 1, current_state,4));
			}
			// move left of move right 
			else if(current_state.zeroy == 1){
				if(current_state.last_move != 3)queue.offer(new State(current_state.map, current_state.current_level + 1 ,current_state.zerox, current_state.zeroy, 0, 1,current_state,4));
				if(current_state.last_move != 4)queue.offer(new State(current_state.map, current_state.current_level + 1 ,current_state.zerox, current_state.zeroy, 0, -1,current_state,3));
			}
			// only move left
			else{
				if(current_state.last_move != 4)queue.offer(new State(current_state.map,current_state.current_level + 1,current_state.zerox, current_state.zeroy, 0, -1,current_state,3));
			}

			// only move down
			if(current_state.zerox == 0) {
				if(current_state.last_move != 1)queue.offer(new State(current_state.map,current_state.current_level + 1,current_state.zerox, current_state.zeroy, 1, 0,current_state,2));
			}
			// move up ot move down
			else if(current_state.zerox == 1){
				if(current_state.last_move != 1)queue.offer(new State(current_state.map,current_state.current_level + 1 ,current_state.zerox, current_state.zeroy, 1, 0,current_state,2));
				if(current_state.last_move != 2)queue.offer(new State(current_state.map,current_state.current_level+ 1 ,current_state.zerox, current_state.zeroy, -1, 0,current_state,1));
			}
			// only move up
			else{
				if(current_state.last_move != 2)queue.offer(new State(current_state.map,current_state.current_level+ 1,current_state.zerox, current_state.zeroy, -1, 0,current_state,1));
			}
			
		}

		bwriter.flush();
        bwriter.close();

	}
	
	
	public static void main(String[] args) throws Exception {

		IO_In(args);
		IO_Out(initial_state, final_state, zero_initial_x, zero_initial_y,args);
		System.out.println("The solution take: "+cont + " steps");
    }
}