import java.util.Scanner;

public class Player {
	private int score;
	private boolean turn;
	private int lostTurn;
	private String carColor;

	public Player(String carColor) {
		this.score = 0;
		this.turn = false;
		this.lostTurn = -1;
		this.carColor = carColor;
	}

	public int getScore() {
		return score;
	}

	public boolean getTurn() {
		return turn;
	}

	public int getLostTurn() {
		return lostTurn;
	}

	public void setScore(int newScore) {
		this.score += newScore;
	}

	public void setLostTurn(int lostTurn) {
		this.lostTurn = lostTurn;
	}

	public void changeTurn(boolean turn) {
		this.turn = turn;
	}

	public boolean makeChoice(Car color, Player colorP) {
		color.zeroFuel();
		Scanner scan2 = new Scanner(System.in);
		int choice;
		do {
			System.out.println("----------------------------------------\nYou ran out of fuel " + colorP.getCarColor()
					+ ". You can choose to:\n1.Skip up to 6 turns, gaining 20 fuel with each skip\n2.Go to start and gain random amount of fuel");
			choice = scan2.nextInt();
		} while (!(choice == 1 ^ choice == 2));

		if (choice == 1) {
			int turns = 0;
			do {
				System.out.println("How many turns do you want to skip?");
				turns = scan2.nextInt();
			} while (!(turns >= 1 && turns <= 6));
			colorP.setLostTurn(turns);
			return true;
		} else {
			color.gotoStart();
			color.randomFuel();
			return true;
		}
	}

	public String getCarColor() {
		return carColor;
	}

	public void remLostTurn() {
		this.lostTurn -= 1;

	}

	@Override
	public String toString() {
		return "Player [score=" + score + ", turn=" + turn + ", lostTurn=" + lostTurn + ", carColor=" + carColor + "]";
	}

}
