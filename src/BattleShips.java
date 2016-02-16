import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BattleShips {
	static int row;
	static int col;

	public static void setBoard(int[][] board) {
		for (int row = 0; row < 5; row++) {
			for (int column = 0; column < 5; column++) {
				board[row][column] = -1;
			}
		}
	}

	public static void showBoard(int[][] board) {
		System.out.println("\n\t\tBattleship Java game\n");
		System.out.println("\t1 \t2 \t3 \t4 \t5");
		System.out.println();

		for (int row = 0; row < 5; row++) {
			System.out.print((row + 1) + "");
			for (int column = 0; column < 5; column++) {
				if (board[row][column] == -1) {
					System.out.print("\t" + "~");
				}
				if (board[row][column] == 0) {
					System.out.print("\t" + "*");
				}
				if (board[row][column] == 1) {
					System.out.print("\t" + "X");
				}
			}
			System.out.println();
			System.out.println();
		}
	}

	public static void setShips(int[][] ships) {
		Random random = new Random();

		for (int ship = 0; ship < 3; ship++) {
			ships[ship][0] = random.nextInt(5);
			ships[ship][1] = random.nextInt(5);

			for (int lastShip = 0; lastShip < ship; lastShip++) {
				if (ships[lastShip][0] == ships[ship][0] && ships[lastShip][1] == ships[ship][1]) {
					do {
						ships[ship][0] = random.nextInt(5);
						ships[ship][1] = random.nextInt(5);
					} while (ships[lastShip][0] == ships[ship][0] && ships[lastShip][1] == ships[ship][1]);
				}
			}
		}
	}

	public static void shoot(int[] shot, int[][] board) {
		Scanner input = new Scanner(System.in);
		System.out.println("\n Attack ");

		System.out.print("row: ");
		row = input.nextInt() - 1;
		System.out.print("column: ");
		col = input.nextInt() - 1;
		System.out.println();
		checkRowColValues(input);
		checkSameShot(board, input);

		shot[0] = row;
		shot[1] = col;

	}

	private static void checkSameShot(int[][] board, Scanner input) {
		if (board[row][col] != -1) {
			do {
				System.out.println("This shot is already taken. Please, enter new coordinates.");
				System.out.println("\n Attack");
				System.out.print("row: ");
				row = input.nextInt() - 1;
				System.out.print("column: ");
				col = input.nextInt() - 1;
				System.out.println();
			} while (board[row][col] != -1);
		}
	}

	private static void checkRowColValues(Scanner input) {
		if (row < 0 || row > 4 || col < 0 || col > 4) {
			do {
				System.out.println("Please, enter coordinates between 1 and 5.");
				System.out.println("\n Attack");
				System.out.print("row: ");
				row = input.nextInt() - 1;
				System.out.print("column: ");
				col = input.nextInt() - 1;
				System.out.println();
			} while (row < 0 || row > 4 || col < 0 || col > 4);
		}
	}

	public static boolean accurateShot(int[] shot, int[][] ships) {
		for (int ship = 0; ship < 3; ship++) {
			if (ships[ship][0] == shot[0] && ships[ship][1] == shot[1]) {
				return true;
			}
		}
		return false;
	}

	public static void hint(int[] shot, int[][] ships, int attempts) {
		System.out.println("\n Hint " + attempts + ":");

		int inRow = 0;
		int inCol = 0;

		for (int ship = 0; ship < 3; ship++) {
			if (ships[ship][0] == shot[0]) {
				inRow++;
			}
			if (ships[ship][1] == shot[1]) {
				inCol++;
			}
		}

		switch (inRow) {
		case 0:
			System.out.println("There isn't any ships in row " + (shot[0] + 1) + ".");
			break;
		case 1:
			System.out.println("There is only 1 ship in row " + (shot[0] + 1) + ".");
			break;
		default:
			System.out.println("There are " + inRow + " ships in row " + (shot[0] + 1) + ".");
			break;
		}

		switch (inCol) {
		case 0:
			System.out.println("There isn't any ships in column " + (shot[1] + 1) + ".");
			break;
		case 1:
			System.out.println("There is only 1 ship in column " + (shot[1] + 1) + ".");
			break;
		default:
			System.out.println("There are " + inCol + " ships in column " + (shot[1] + 1) + ".");
			break;
		}

	}

	public static void changeBoard(int[] shot, int[][] ships, int[][] board) {

		if (accurateShot(shot, ships)) {
			board[shot[0]][shot[1]] = 1;
		} else {
			board[shot[0]][shot[1]] = 0;

		}
	}

	public static void main(String[] args) {
		int[][] board = new int[5][5];
		int[][] ships = new int[3][2];
		int[] shot = new int[2];
		int attempts = 0;
		int countShots = 0;
		boolean canShot = true;

		setBoard(board);
		setShips(ships);

		do {
			showBoard(board);
			try {
				shoot(shot, board);
				canShot = true;
			} catch (InputMismatchException ex) {
				canShot = false;
				System.out.println("The enterred coordinates are not valid. Please, enter new ones.");
			}
			if (canShot) {
				attempts++;

				if (accurateShot(shot, ships)) {
					System.out.printf("You hit a ship located in row %d and column %d.", shot[0] + 1, shot[1] + 1);
					System.out.println();
					countShots++;
				}

				hint(shot, ships, attempts);
				changeBoard(shot, ships, board);
			}
		} while (countShots != 3);

		System.out.println("\n\n\n Battleship Java game finished! You hit 3 ships in " + attempts + " attempts.");
		showBoard(board);

	}

}
