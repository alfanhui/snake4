import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
	/* define lengths of board */
	public final int BOARDSIZE = 4800;
	public final int COLUMN = 80;
	public final int ROW = 60;
	public final int SOFTWALL_LOCATION = 49;
	
	/* constant fields for defining map contents */
	public static final int SPACE = 0;
	public static final int SOFTWALL = 1;
	public static final int SNAKE = 2;
	public static final int PORTAL = 3;
	public static final int FOOD = 4;
	public static final int START = 5;
	public static final int HARDWALL = 6;
	
	public List<Pair> startingWallSide = new ArrayList<Pair>();
	public List<Pair> endingWallSide = new ArrayList<Pair>();
	
	public int[][] initialiseBoard(){
		int [][] snakeBoard = new int [ROW][COLUMN];
		
		snakeBoard = addSoftWalls(snakeBoard);
		
		/* only one of these should be uncommented at a time */
		//snakeBoard = addSoftCentralWall(snakeBoard);
		snakeBoard = addSoftCentralWall2(snakeBoard);
		
		getStartingSidePositions(snakeBoard);
		getEndingSidePositions(snakeBoard);
		
		List<Pair> freeIndexes = generateFreePositions(snakeBoard);
		
		snakeBoard = addPortals(snakeBoard, freeIndexes);
		snakeBoard = addStart(snakeBoard, freeIndexes);	
		snakeBoard = addFood(snakeBoard, freeIndexes);
		
		// add snake to starting position
		//snakeBoard = addSnake(snakeBoard, freeIndexes);
		
		return snakeBoard;		
	}
	/* Add soft (wrap-around) boundary walls to the snake board */
	public int [][] addSoftWalls (int [][] snakeBoard){
		/* add soft walls to edges of board - top/bottom/left/right */
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i == 0 || i == ROW-1 || j == 0 || j == COLUMN-1){
					snakeBoard [i][j] = SOFTWALL;
				}
			}
		}		
		return snakeBoard;
		
	}
	
	/* Add soft central wall to the snake board (vertical only) */
	public int[][] addSoftCentralWall(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				snakeBoard [i][SOFTWALL_LOCATION] = SOFTWALL;
				}
			}
		return snakeBoard;
	}
	
	/* Add soft central wall to the snake board (jagged) */
	public int[][] addSoftCentralWall2(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i < 39) {
					snakeBoard [i][SOFTWALL_LOCATION] = SOFTWALL;
				} else if (i == 39 && j <= SOFTWALL_LOCATION && j >= SOFTWALL_LOCATION-4) {
					snakeBoard [i][j] = SOFTWALL;
				} else if (i > 39) {
					snakeBoard [i][SOFTWALL_LOCATION-4] = SOFTWALL;
				}
			}
		}
		return snakeBoard;
	}
	
	/* Add hard wall to the snake board (incomplete-location of hard wall?) */
	public int[][] addHardWall(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				
			}
		}
		return snakeBoard;
	}

	/* Create list of free positions on the snake board */
	public List<Pair> generateFreePositions(int[][] snakeBoard) {
		List<Pair> freeIndexes = new ArrayList<Pair>();
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (snakeBoard[i][j] != SOFTWALL && noWallWithin2Spaces(snakeBoard, i ,j)){
					// add position to list
					Pair empty = new Pair(i, j);
					freeIndexes.add(empty);				
				}
			}
		}
		return freeIndexes;
	}
	
	/* Add two portals to the snake board */
	public int[][] addPortals(int[][] snakeBoard, List<Pair> freeIndexes) {
		int starterAreaPortal, endAreaPortal;
		while (true){
			starterAreaPortal = new Random().nextInt(startingWallSide.size());
			endAreaPortal = new Random().nextInt(freeIndexes.size());
			if (starterAreaPortal != endAreaPortal ){
				break;
			}
		}		
		Pair portalPos1 = freeIndexes.get(starterAreaPortal);
		Pair portalPos2 = freeIndexes.get(endAreaPortal);		
		snakeBoard[portalPos1.row][portalPos1.column] = PORTAL;		
		snakeBoard[portalPos2.row][portalPos2.column] = PORTAL;		
		freeIndexes.remove(starterAreaPortal);
		freeIndexes.remove(endAreaPortal);
		
		return snakeBoard;
	}
	
	/* Add default starting position to the snake board (ensure away from walls) */
	public int[][] addStart(int[][] snakeBoard, List<Pair> freeIndexes) {
		int randomStart;
		Pair startPosition;
		while (true){
			randomStart = new Random().nextInt(freeIndexes.size());	
			startPosition = freeIndexes.get(randomStart);
			if (startPosition.row > 2 && startPosition.row < 77 && startPosition.column > 2 
					&& startPosition.column < 57){
				break;
			}
		}
		snakeBoard[startPosition.row][startPosition.column] = START;
		freeIndexes.remove(startPosition);
		return snakeBoard;
	}
	
	/* Add food to board */
	public int[][] addFood(int[][] snakeBoard, List<Pair> freeIndexes) {
		int foodIndex = new Random().nextInt(freeIndexes.size());
		Pair foodPosition = freeIndexes.get(foodIndex);
		
		snakeBoard[foodPosition.row][foodPosition.column] = FOOD;
		freeIndexes.remove(foodPosition);
		
		return snakeBoard;
	}
	
	/* Get array list of portal locations */
	public List<Pair> getPortals (int[][] snakeBoard){
		List<Pair> portals = new ArrayList<Pair>();
		
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++i){
				if (snakeBoard[i][j] == PORTAL){
					// add portal to list
					Pair newPortal = new Pair(i, j);
					portals.add(newPortal);
				}
			}
		}
		return portals;
		
	}
	
	/* Add snake to board to start */
	public int[][] addSnake(int[][] snakeBoard, List<Pair> freeIndexes) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Get all positions on starting side of board and add to array list */
	public void getStartingSidePositions(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i < 39 && j < SOFTWALL_LOCATION && i > 0 && j > 0){	
					Pair newCoord = new Pair(i,j);
					startingWallSide.add(newCoord);
				} else if (i >= 39 && i < ROW-1 && j > 0 && j < SOFTWALL_LOCATION-4) {
					Pair newCoord = new Pair(i,j);
					startingWallSide.add(newCoord);
				}
			}System.out.println();
		}
		
	}
	
	/* Get all positions on ending side of board and add to array list */
	public void getEndingSidePositions(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i <= 39 && i > 0 && j > SOFTWALL_LOCATION && j < COLUMN-1){
					Pair newCoord = new Pair(i,j);
					endingWallSide.add(newCoord);
				} else if (i > 39 && i < ROW-1 && j > SOFTWALL_LOCATION-4 && j < COLUMN-1) {
					Pair newCoord = new Pair(i,j);
					endingWallSide.add(newCoord);
				}
			}
		}		
	}
	
	public boolean noWallWithin2Spaces(int[][] snakeBoard, int i, int j) {
		
		/*if (snakeBoard[i-2][j] == SOFTWALL || snakeBoard[i+2][j] == SOFTWALL || 
				snakeBoard[i][j-2] == SOFTWALL || snakeBoard[i][j+2] == SOFTWALL ||
				snakeBoard[i-2][j-2] == SOFTWALL || snakeBoard[i-2][j+2] == SOFTWALL ||
				snakeBoard[i+2][j-2] == SOFTWALL || snakeBoard[i+2][j+2] == SOFTWALL){
			return false;
		}*/		
		return true;
	}
	
}
/*if (snakeBoard[i][j] == SOFTWALL){System.out.print("x");} 
 * else {System.out.print("0");
}*/

//System.out.print(" ("+i+","+j+") ");
//System.out.print("0");