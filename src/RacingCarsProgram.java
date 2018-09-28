import java.util.Scanner;

public class RacingCarsProgram {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		// creating objects
		Game game = new Game();
		Car red = new Car();
		Player redP = new Player("Red");
		Car blue = new Car();
		Player blueP = new Player("Blue");
		System.out.println("Give me size of the side\nof the grid:");

		int sideOfGrid = 0;
		// cannot be 2 by 2 as a die can have a value of 6 and thus this renders the
		// game pointless
		while (sideOfGrid <= 2) {
			sideOfGrid = scan.nextInt();
		}

		// creating grid via random generator
		Game.createGrid(sideOfGrid);

		// printing the state of everything
		System.out.println(game);
		System.out.println(red);
		System.out.println(redP);
		System.out.println(blue);
		System.out.println(blueP);

		// play first contest with die roll
		String whoIsFirst = game.playsFirst();

		// start of game
		System.out.println("------GAME STARTS------");
		game.firstMove(whoIsFirst, game, red, redP, blue, blueP);
		game.restOfMoves(game, red, redP, blue, blueP);
		System.out.println("------END OF GAME------");
		// end of game

		// printing the final state
		System.out.println(red);
		System.out.println(redP);
		System.out.println(blue);
		System.out.println(blueP);

		// stating who the winner is
		game.winner(red, blue, redP, blueP);

	}

}
