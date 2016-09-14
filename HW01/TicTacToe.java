import java.util.Scanner;

public class TicTacToe{
	public enum Turn {PLAYER1, PLAYER2}

	public static boolean isRowCrossed(String fields[], int dim){
		for(int i = 0; i < dim; i++){
			boolean result = true;
			for(int j = 0; j < dim - 1; j++){
				if(!fields[i*dim+j].equals(fields[i*dim+j+1])){
					result = false;
					break;
				}
			}
			if(result) return true;
		}
		return false;
	}

	public static boolean isColumnCrossed(String fields[], int dim){
		for(int i = 0; i < dim; i++){
			boolean result = true;
			for(int j = 0; j < dim - 1; j++){
				if(!fields[j*dim+i].equals(fields[(j+1)*dim+i])){
					result = false;
					break;
				}
			}
			if(result) return true;
		}
		return false;
	}

	public static boolean isDiagonalCrossed(String fields[], int dim){
		boolean result = true;
		for(int i = 0; i < dim - 1; i++){
			if(!fields[i*dim+i].equals(fields[(i+1)*dim+i+1])){
				result = false;
				break;
			}
		}
		if(result) return true;
		result = true;
		for(int i = 0; i < dim - 1; i++){
			if(!fields[(dim-1-i)*dim+i].equals(fields[(dim-1-(i+1))*dim+i+1])){
				result = false;
				break;
			}
		}
        return result;
	}

	public static void main(String[] args){
		if(args.length != 3){
			System.out.println("Needs exactly three arguments: dimension, name of player 1 (o), name of player 2 (x)!");
			return;
		}
		Scanner scanner = new Scanner(System.in);
		int input = -1;
		int dim = Integer.parseInt(args[0]);
		int turn_counter = 0;
		String fields[] = new String[dim*dim];
		String player_name = "";
		Turn turn = Turn.PLAYER1;
		boolean finished = false;
		String sign = "";
		for(int i = 0; i < dim*dim; i++){
			fields[i] = Integer.toString(i+1);
		}

		while(input != 0){
			System.out.print("\n\n\n");
			for(int i = 0; i < dim; i++){
				for(int j = 0; j < dim; j++){
					System.out.print(fields[i*dim+j] + "\t");
				}
				System.out.println();
			}

			if(finished){
				if(turn_counter != dim*dim) System.out.println(player_name + " (" + sign + ") won! Input -1 to restart");
				else System.out.println("A draw! Input -1 to restart");
			}
			else{
				if(turn == Turn.PLAYER1){
					sign = "o";
					player_name = args[1];
				}
				else{
					sign = "x";
					player_name = args[2];
				}

				System.out.println(player_name + "'s turn (" + sign + ")");
			}

			System.out.print("Input (0 to quit): ");
			input = scanner.nextInt();

			if(finished && input == -1){
				//restart
				for(int i = 0; i < dim*dim; i++){
					fields[i] = Integer.toString(i+1);
				}
				finished = false;
				turn = Turn.PLAYER1;
				turn_counter = 0;
			}

			if(!finished && (input >= 1) && !fields[input - 1].equals("o") && !fields[input - 1].equals("x")){
				fields[input-1] = sign;
				if(turn == Turn.PLAYER1) turn = Turn.PLAYER2;
				else turn = Turn.PLAYER1;
				turn_counter++;
                finished = (turn_counter == (dim * dim)) || isRowCrossed(fields, dim)
                        || isColumnCrossed(fields, dim) || isDiagonalCrossed(fields, dim);
			}
		}
	}
}
