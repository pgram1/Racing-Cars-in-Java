import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
	private static ArrayList<Tile> grid;

	public Game() {
		grid = new ArrayList<Tile>();
	}

	/*
	 * adds tile, if choice is 0, the random generator decides among 1,2 or 3 for a
	 * normal tile with that fuel cost, 0 for an obstacle and 4 for a fuel station.
	 * If choice is 1 it's a Start tile, if choice is 2 it's an End tile
	 */
	public static void addTile(int choice) {
		if (choice == 0) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, 4 + 1);
			if (randomNum == 1 || randomNum == 2 || randomNum == 3) {
				grid.add(new Tile(randomNum));
			} else if (randomNum == 0) {
				grid.add(new Tile(true, false));
			} else {
				grid.add(new Tile(false, true));
			}
		} else if (choice == 1) {
			grid.add(new Tile(true));
		} else if (choice == 2) {
			grid.add(new Tile(false));
		}
	}

	// generates random grid
	public static void createGrid(int side) {
		for (int i = 0; i < side * side; i++) {
			if (i == 0) {
				Game.addTile(1);
			} else if (i == side * side - 1) {
				Game.addTile(2);
			} else {
				Game.addTile(0);
			}
		}
	}

	public int getGetGridSize() {
		return grid.size();
	}

	public Tile getTile(int position) {
		return grid.get(position);
	}

	public int getTileCost(int position) {
		return getTile(position).getCost();
	}

	public boolean isTileEnd(int position) {
		return getTile(position).isEnd();
	}

	public int throwDie() {
		return ThreadLocalRandom.current().nextInt(1, 6 + 1);
	}

	public String playsFirst() {
		int redDie = throwDie();
		int blueDie = throwDie();
		if (redDie == blueDie) {
			return playsFirst();
		} else if (redDie > blueDie) {
			System.out.println("Red starts first(They rolled " + redDie + ")");
			return "Red";
		} else {
			System.out.println("Blue starts first(They rolled " + blueDie + ")");
			return "Blue";
		}
	}

	public void firstMove(String whoIsFirst, Game game, Car red, Player redP, Car blue, Player blueP) {
		if (whoIsFirst.equals("Red")) {
			red.move(game.throwDie());
			red.removeFuel(game.getTileCost(red.getPositionOnGrid()));
			System.out.println(red);
			System.out.println(redP);
			System.out.println(game.getTile(red.getPositionOnGrid()));
			blueP.changeTurn(true);
		} else {
			blue.move(game.throwDie());
			blue.removeFuel(game.getTileCost(blue.getPositionOnGrid()));
			System.out.println(blue);
			System.out.println(blueP);
			System.out.println(game.getTile(blue.getPositionOnGrid()));
			redP.changeTurn(true);
		}
	}

	public boolean restOfMoves(Game game, Car red, Player redP, Car blue, Player blueP) {
		while (true) {
			if (redP.getTurn() && red.hasFuel() && redP.getLostTurn() == -1) {
				int steps = game.throwDie();
				System.out.println("Red rolled " + steps);

				// possible last die drop
				if (red.getPositionOnGrid() + steps > game.getGetGridSize() - 1) {
					red.move(game.getGetGridSize() - red.getPositionOnGrid());
					return false;
				}
				red.move(steps);
				if (game.getTile(red.getPositionOnGrid()).isObstacle()) {
					red.gotoStart();
				} else if (game.getTile(red.getPositionOnGrid()).isStation()) {
					red.stationFill();
				} else
					red.removeFuel(game.getTileCost(red.getPositionOnGrid()));

				System.out.println(red);
				System.out.println(redP);
				System.out.println(game.getTile(red.getPositionOnGrid()));

				// change turn
				redP.changeTurn(false);
				blueP.changeTurn(true);
			} else if (blueP.getTurn() && blue.hasFuel() && blueP.getLostTurn() == -1) {
				int steps = game.throwDie();
				System.out.println("Blue rolled " + steps);

				// possible last die drop
				if (blue.getPositionOnGrid() + steps > game.getGetGridSize() - 1) {
					blue.move(game.getGetGridSize() - blue.getPositionOnGrid());
					return false;
				}
				blue.move(steps);
				if (game.getTile(blue.getPositionOnGrid()).isObstacle()) {
					blue.gotoStart();
				} else if (game.getTile(blue.getPositionOnGrid()).isStation()) {
					blue.stationFill();
				} else
					blue.removeFuel(game.getTileCost(blue.getPositionOnGrid()));

				System.out.println(blue);
				System.out.println(blueP);
				System.out.println(game.getTile(blue.getPositionOnGrid()));

				// change turn
				blueP.changeTurn(false);
				redP.changeTurn(true);
			} else if (redP.getTurn() && redP.getLostTurn() == -1) {
				boolean madeChoice = true;
				do {
					madeChoice = redP.makeChoice(red, redP);
				} while (!madeChoice);
				// change turn
				redP.changeTurn(false);
				blueP.changeTurn(true);

				System.out.println(red);
				System.out.println(redP);
				System.out.println(game.getTile(red.getPositionOnGrid()));

			} else if (blueP.getTurn() && blueP.getLostTurn() == -1) {
				boolean madeChoice = true;
				do {
					madeChoice = blueP.makeChoice(blue, blueP);
				} while (!madeChoice);
				// change turn
				blueP.changeTurn(false);
				redP.changeTurn(true);

				System.out.println(blue);
				System.out.println(blueP);
				System.out.println(game.getTile(blue.getPositionOnGrid()));

			}

			// lost turns
			else if (redP.getTurn() && redP.getLostTurn() != 0 && redP.getLostTurn() != -1) {
				redP.remLostTurn();
				red.gainFuel();
				redP.changeTurn(false);
				blueP.changeTurn(true);

				System.out.println(red);
				System.out.println(redP);
				System.out.println(game.getTile(red.getPositionOnGrid()));

				System.out.println("----------------------------------------------");

			} else if (blueP.getTurn() && blueP.getLostTurn() != 0 && blueP.getLostTurn() != -1) {
				blueP.remLostTurn();
				blue.gainFuel();
				blueP.changeTurn(false);
				redP.changeTurn(true);

				System.out.println(blue);
				System.out.println(blueP);
				System.out.println(game.getTile(blue.getPositionOnGrid()));

				System.out.println("----------------------------------------------");

			}

			if (redP.getLostTurn() == 0) {
				redP.setLostTurn(-1);
			} else if (blueP.getLostTurn() == 0) {
				blueP.setLostTurn(-1);
			}
		}
	}

	public void winner(Car red, Car blue, Player redP, Player blueP) {
		if (red.getPositionOnGrid() == grid.size()) {
			redP.setScore(red.getFuel());
			blueP.setScore(blue.getFuel());
			System.out.println("\n------------------------------\nRed finished!\nScores:\nRed: " + redP.getScore()
					+ "\nBlue: " + blueP.getScore());
		} else {
			redP.setScore(red.getFuel());
			blueP.setScore(blue.getFuel());
			System.out.println("\n------------------------------\nBlue finished!\nScores:\nBlue: " + blueP.getScore()
					+ "\nRed: " + redP.getScore());
		}
	}

	@Override
	public String toString() {
		return "Random Grid [ " + grid + " ]";
	}

}
