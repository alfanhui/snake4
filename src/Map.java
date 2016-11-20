import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
	/* define lengths of board */
	public final int BOARDSIZE = 4800;
	public final int COLUMN = 80;
	public final int ROW = 60;
	public final int SOFTWALL_LOCATION = 49;
	public final int GAME_VERSION = 4;

	/* constant fields for defining map contents */
	public static final int SPACE = 0;
	public static final int SOFTWALL = 1;
	public static final int SNAKE = 2;
	public static final int PORTAL = 3;
	public static final int FOOD = 4;
	public static final int START = 5;
	public static final int HARDWALL = 6;

	public List<Pair> freeIndexes = new ArrayList<Pair>();
	public List<Pair> startingWallSide = new ArrayList<Pair>();
	public List<Pair> endingWallSide = new ArrayList<Pair>();

	public int[][] initialiseBoard(){
		int [][] snakeBoard = new int [ROW][COLUMN];
        System.out.println(' ');
		snakeBoard = addSoftWalls(snakeBoard);
		
		snakeBoard = addSoftCentralWall1(snakeBoard);
		getStartingSidePositions1(snakeBoard);
		getEndingSidePositions1(snakeBoard);
		/*snakeBoard = addSoftCentralWall1(snakeBoard);
		getStartingSidePositions1(snakeBoard);
		getEndingSidePositions1(snakeBoard);
		
		snakeBoard = addSoftCentralWall2(snakeBoard);
		getStartingSidePositions2(snakeBoard);
		getEndingSidePositions2(snakeBoard);
		
		snakeBoard = addSoftCentralWall3(snakeBoard);
		getStartingSidePositions3(snakeBoard);
		getEndingSidePositions3(snakeBoard);
		
		snakeBoard = addSoftCentralWall4(snakeBoard);
				getStartingSidePositions4(snakeBoard);
				getEndingSidePositions4(snakeBoard);
			}
		}*/

		//snakeBoard = addHardEdgeWallBottom(snakeBoard);

		freeIndexes = generateFreePositions(snakeBoard);

		snakeBoard = addPortals(snakeBoard, freeIndexes);
		snakeBoard = addStart(snakeBoard, freeIndexes);

		// add snake to starting position
		//snakeBoard = addSnake(snakeBoard);
		return snakeBoard;
	}
	/* Add hard wall to specified row/column of board */
	public int[][] addHardEdgeWallBottom(int[][] snakeBoard) {
		for (int j = 0; j < COLUMN; ++j){
			snakeBoard[ROW-1][j] = HARDWALL;
		}		
		return snakeBoard;
	}
	public int[][] addHardEdgeWallTop(int[][] snakeBoard) {
		for (int j = 0; j < COLUMN; ++j){
			snakeBoard[0][j] = HARDWALL;
		}	
		return snakeBoard;
	}
	public int[][] addHardEdgeWallLeft(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			snakeBoard[i][0] = HARDWALL;
		}	
		return snakeBoard;
	}
	public int[][] addHardEdgeWallRight(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			snakeBoard[i][COLUMN-1] = HARDWALL;
		}	
		return snakeBoard;
	}

	/* Get starting position on board */
	public Pair getDefaultStartPlace(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (snakeBoard[i][j] == START){
					return new Pair(i,j);
				}
			}
		}
		return null;
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
	public int[][] addSoftCentralWall1(int[][] snakeBoard) {
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
	public int[][] addSoftCentralWall3(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i == 39 && j <= 59){
					snakeBoard[i][j] = SOFTWALL;
				} else if (i < 39 && j == 59){
					snakeBoard[i][j] = SOFTWALL;
				}
			}
		}
		return snakeBoard;
	}
	public int[][] addSoftCentralWall4(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i >= 20 && i <= 39 && j == 29){
					snakeBoard[i][j] = SOFTWALL;
				} else if (i >= 20 && i <= 39 && j == 59){
					snakeBoard[i][j] = SOFTWALL;
				}
			}
		}
		return snakeBoard;
	}
	
	/* Create list of free positions on the snake board */
	public List<Pair> generateFreePositions(int[][] snakeBoard) {
		List<Pair> freeIndexes = new ArrayList<Pair>();
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (snakeBoard[i][j] != SOFTWALL && snakeBoard[i][j] != HARDWALL){
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
			endAreaPortal = new Random().nextInt(endingWallSide.size());
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

	/* Return free location where food can be placed */
	public Pair getFoodLocation(int[][] snakeBoard) {
		int foodIndex = new Random().nextInt(freeIndexes.size());
		Pair foodPosition = freeIndexes.get(foodIndex);

		freeIndexes.remove(foodPosition);
		return foodPosition;
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
	public int[][] addSnake(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++i){
				if (snakeBoard[i][j] == START){
					snakeBoard[i][j] = SNAKE;
					return snakeBoard;
				}
			}
		}
		return null;
	}

	/* Get all positions on starting/ending side of board and add to array list */
	public void getStartingSidePositions1(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i > 0 && i < ROW-1 && j > 0 && j < SOFTWALL_LOCATION){
					Pair newCoord = new Pair(i,j);
					startingWallSide.add(newCoord);
				}
			}
		}		
	}
	public void getEndingSidePositions1(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i > 0 && i < ROW-1 && j < COLUMN-1 && j > SOFTWALL_LOCATION){
					Pair newCoord = new Pair(i,j);
					endingWallSide.add(newCoord);
				}
			}
		}

	}

	public void getStartingSidePositions2(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i < 39 && j < SOFTWALL_LOCATION && i > 0 && j > 0){
					Pair newCoord = new Pair(i,j);
					startingWallSide.add(newCoord);
				} else if (i >= 39 && i < ROW-1 && j > 0 && j < SOFTWALL_LOCATION-4) {
					Pair newCoord = new Pair(i,j);
					startingWallSide.add(newCoord);
				}
			}
		}
	}
	public void getEndingSidePositions2(int[][] snakeBoard) {
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

	public void getStartingSidePositions3(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i > 0 && i < 39 && j > 0 && j < 59){
					Pair newCoord = new Pair(i,j);
					startingWallSide.add(newCoord);
				}
			}
		}		
	}
	public void getEndingSidePositions3(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (i > 39 && i < ROW-1 && j > 0 && j <= 59){
					Pair newCoord = new Pair(i,j);
					endingWallSide.add(newCoord);
				} else if (i > 0 && i < 59 && j < 59 && j > 79){
					Pair newCoord = new Pair(i,j);
					endingWallSide.add(newCoord);
				}
			}
		}		
	}

	public void getStartingSidePositions4(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (snakeBoard[i][j] == SPACE){
					Pair newCoord = new Pair(i,j);
					startingWallSide.add(newCoord);
				}
			}
		}		
	}
	public void getEndingSidePositions4(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				if (snakeBoard[i][j] == SPACE){
					Pair newCoord = new Pair(i,j);
					endingWallSide.add(newCoord);
				}
			}
		}		
	}

	
	/* Print game board (ignore) */
	public void printBoard(int[][] snakeBoard) {
		for (int i = 0; i < ROW; ++i){
			for (int j = 0; j < COLUMN; ++j){
				System.out.print(snakeBoard[i][j] + " ");
			}System.out.println();
		}

	}
}
