import java.util.concurrent.ThreadLocalRandom;

public class Car {
	private int fuel;
	private int positionOnGrid;

	public Car() {
		this.fuel = 120;
		this.positionOnGrid = 0;
	}

	public void move(int steps) {
		this.positionOnGrid += steps;
	}

	public void gotoStart() {
		this.positionOnGrid = 0;
	}

	public void gainFuel() {
		this.fuel += 20;
	}

	public void stationFill() {
		this.fuel += fuel * (50 / 100);
	}

	public int getFuel() {
		return this.fuel;
	}

	public void removeFuel(int x) {
		this.fuel -= x;
	}

	public int getPositionOnGrid() {
		return this.positionOnGrid;
	}

	public boolean hasFuel() {
		if (this.fuel <= 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		return "Car [fuel=" + fuel + ", positionOnGrid=" + positionOnGrid + "]";
	}

	public void zeroFuel() {
		this.fuel = 0;
	}

	public void randomFuel() {
		this.fuel = ThreadLocalRandom.current().nextInt(0, 120 + 1);
	}

}
