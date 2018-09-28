
public class Tile {
	private boolean isStart;
	private boolean isEnd;
	private int cost;
	private boolean isObstacle;
	private boolean isStation;

	// makes a normal tile with some cost
	public Tile(int cost) {
		this.isStart = false;
		this.isEnd = false;
		this.cost = cost;
		this.isObstacle = false;
		this.isStation = false;
	}

	// makes a tile, first parameter controls obstacle and second controls station
	public Tile(boolean obst, boolean sta) {
		this.isStart = false;
		this.isEnd = false;
		this.cost = 0;
		this.isObstacle = obst;
		this.isStation = sta;
	}

	// makes special tile, true is Start Tile and false is End Tile
	public Tile(boolean special) {
		this.isStart = special;
		this.isEnd = !special;
		this.cost = 0;
		this.isObstacle = false;
		this.isStation = false;
	}

	public boolean isStart() {
		return isStart;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public int getCost() {
		return cost;
	}

	public boolean isObstacle() {
		return isObstacle;
	}

	public boolean isStation() {
		return isStation;
	}

	@Override
	public String toString() {
		return "Tile [isStart=" + isStart + ", isEnd=" + isEnd + ", cost=" + cost + ", isObstacle=" + isObstacle
				+ ", isStation=" + isStation + "]";
	}

}
