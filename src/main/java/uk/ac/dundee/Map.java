package uk.ac.dundee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.ac.dundee.utils.FileIO;

public class Map {
	/* define lengths of board */
	public final int BOARDSIZE = 4800;
	public final int SOFTWALL_LOCATION = 49;
	public final int GAME_VERSION = 4;

	/* constant fields for defining map contents */
	public static final int PORTAL_1_SPACE = 0;
	public static final int SOFTWALL = 1;
	public static final int SNAKE = 2;
	public static final int PORTAL = 3;
	public static final int FOOD = 4;
	public static final int SNAKE_START = 5;
	public static final int HARDWALL = 6;
	public static final int PORTAL_2_SPACE = 7;
	public static final int SNAKE_HEAD = 8;
	public static final int BLANK = 9;

	public List<Pair> freeIndexes = new ArrayList<Pair>();
	public List<Pair> startingWallSide = new ArrayList<Pair>();
	public List<Pair> endingWallSide = new ArrayList<Pair>();

	public int[][] initialiseBoard(String boardPath, boolean useDefaultStart, boolean useDefaultPortals) {
		int[][] snakeBoard;
		FileIO fileIO = new FileIO();
		snakeBoard = fileIO.readArrayFromFile(boardPath);

		freeIndexes = generateFreePositions(snakeBoard);

		if (!useDefaultPortals) {
			snakeBoard = addPortals(snakeBoard, freeIndexes);
		}
		if (!useDefaultStart) {
			snakeBoard = addRandomSnakeStart(snakeBoard, freeIndexes);
		}

		// add snake to starting position
		return snakeBoard;
	}

	// /* Add hard wall to specified row/column of board */
	// public int[][] addHardEdgeWallBottom(int[][] snakeBoard) {
	// for (int j = 0; j < snakeBoard[0].length; ++j){
	// snakeBoard[snakeBoard.length-1][j] = HARDWALL;
	// }
	// return snakeBoard;
	// }

	// public int[][] addHardEdgeWallTop(int[][] snakeBoard) {
	// for (int j = 0; j < snakeBoard[0].length; ++j){
	// snakeBoard[0][j] = HARDWALL;
	// }
	// return snakeBoard;
	// }

	// public int[][] addHardEdgeWallLeft(int[][] snakeBoard) {
	// for (int i = 0; i < snakeBoard.length; ++i){
	// snakeBoard[i][0] = HARDWALL;
	// }
	// return snakeBoard;
	// }

	// public int[][] addHardEdgeWallRight(int[][] snakeBoard) {
	// for (int i = 0; i < snakeBoard.length; ++i){
	// snakeBoard[i][snakeBoard[0].length-1] = HARDWALL;
	// }
	// return snakeBoard;
	// }

	/* Get starting position on board */
	public Pair getDefaultStartPlace(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (snakeBoard[i][j] == SNAKE_START) {
					return new Pair(i, j);
				}
			}
		}
		return null;
	}

	/* Add soft (wrap-around) boundary walls to the snake board */
	public int[][] addSoftWalls(int[][] snakeBoard) {
		/* add soft walls to edges of board - top/bottom/left/right */
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (i == 0 || i == snakeBoard.length - 1 || j == 0 || j == snakeBoard[0].length - 1) {
					snakeBoard[i][j] = SOFTWALL;
				}
			}
		}
		return snakeBoard;
	}

	/* Add soft central wall to the snake board (vertical only) */
	public int[][] addSoftCentralWall1(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				snakeBoard[i][SOFTWALL_LOCATION] = SOFTWALL;
			}
		}
		return snakeBoard;
	}

	// /* Add soft central wall to the snake board (jagged) */
	// public int[][] addSoftCentralWall2(int[][] snakeBoard) {
	// for (int i = 0; i < snakeBoard.length; ++i){
	// for (int j = 0; j < snakeBoard[0].length; ++j){
	// if (i < 39) {
	// snakeBoard [i][SOFTWALL_LOCATION] = SOFTWALL;
	// } else if (i == 39 && j <= SOFTWALL_LOCATION && j >= SOFTWALL_LOCATION-4) {
	// snakeBoard [i][j] = SOFTWALL;
	// } else if (i > 39) {
	// snakeBoard [i][SOFTWALL_LOCATION-4] = SOFTWALL;
	// }
	// }
	// }
	// return snakeBoard;
	// }

	// public int[][] addSoftCentralWall3(int[][] snakeBoard) {
	// for (int i = 0; i < snakeBoard.length; ++i){
	// for (int j = 0; j < snakeBoard[0].length; ++j){
	// if (i == 39 && j <= 59){
	// snakeBoard[i][j] = SOFTWALL;
	// } else if (i < 39 && j == 59){
	// snakeBoard[i][j] = SOFTWALL;
	// }
	// }
	// }
	// return snakeBoard;
	// }

	// public int[][] addSoftCentralWall4(int[][] snakeBoard) {
	// for (int i = 0; i < snakeBoard.length; ++i){
	// for (int j = 0; j < snakeBoard[0].length; ++j){
	// if (i >= 20 && i <= 39 && j == 29){
	// snakeBoard[i][j] = SOFTWALL;
	// } else if (i >= 20 && i <= 39 && j == 59){
	// snakeBoard[i][j] = SOFTWALL;
	// }
	// }
	// }
	// return snakeBoard;
	// }

	/* Create list of free positions on the snake board */
	public List<Pair> generateFreePositions(int[][] snakeBoard) {
		List<Pair> freeIndexes = new ArrayList<Pair>();
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (snakeBoard[i][j] == PORTAL_1_SPACE || snakeBoard[i][j] == PORTAL_2_SPACE) {
					// add position to list
					freeIndexes.add(new Pair(i, j));
				}
			}
		}
		return freeIndexes;
	}

	/* Create list of free positions on the snake board for portal 1 */
	public List<Pair> generatePortal1Positions(int[][] snakeBoard) {
		List<Pair> freeIndexes = new ArrayList<Pair>();
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (snakeBoard[i][j] == PORTAL_1_SPACE) {
					// add position to list
					freeIndexes.add(new Pair(i, j));
				}
			}
		}
		return freeIndexes;
	}

	/* Create list of free positions on the snake board for portal 1 */
	public List<Pair> generatePortal2Positions(int[][] snakeBoard) {
		List<Pair> freeIndexes = new ArrayList<Pair>();
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (snakeBoard[i][j] == PORTAL_2_SPACE) {
					// add position to list
					freeIndexes.add(new Pair(i, j));
				}
			}
		}
		return freeIndexes;
	}

	/* Add two portals to the snake board */
	public int[][] addPortals(int[][] snakeBoard, List<Pair> freeIndexes) {
		List<Pair> freePortal1Indexes = generatePortal1Positions(snakeBoard);
		List<Pair> freePortal2Indexes = generatePortal2Positions(snakeBoard);

		Pair portalPos1 = new Pair();
		Pair portalPos2 = new Pair();
		if (freePortal2Indexes.isEmpty()) {
			do {
				portalPos1 = freeIndexes.get(new Random().nextInt(freeIndexes.size()));
				portalPos2 = freeIndexes.get(new Random().nextInt(freeIndexes.size()));
			} while (!portalPos1.equals(portalPos2));
		} else {
			portalPos1 = freePortal1Indexes.get(new Random().nextInt(freePortal1Indexes.size()));
			portalPos2 = freePortal2Indexes.get(new Random().nextInt(freePortal2Indexes.size()));
		}
		snakeBoard[portalPos1.row][portalPos1.column] = PORTAL;
		snakeBoard[portalPos2.row][portalPos2.column] = PORTAL;
		freeIndexes.remove(portalPos1);
		freeIndexes.remove(portalPos2);
		return snakeBoard;
	}

	/* Add default starting position to the snake board (ensure away from walls) */
	public int[][] addRandomSnakeStart(int[][] snakeBoard, List<Pair> freeIndexes) {
		int randomStart;
		Pair startPosition;

		while (true) {
			randomStart = new Random().nextInt(freeIndexes.size());
			startPosition = freeIndexes.get(randomStart);
			if (startPosition.row > 2 && startPosition.row < snakeBoard.length && startPosition.column > 2
					&& startPosition.column < snakeBoard[0].length - 2) {
				break;
			}
		}
		snakeBoard[startPosition.row][startPosition.column] = SNAKE_START;

		freeIndexes.remove(startPosition);
		return snakeBoard;
	}

	/* Return free location where food can be placed */
	public int[][] setNewFoodLocation(int[][] snakeBoard, Pair oldFoodLocation) {
		Pair foodPosition = getNewFoodLocation(snakeBoard);
		freeIndexes.add(oldFoodLocation);
		freeIndexes.remove(foodPosition);
		snakeBoard[foodPosition.row][foodPosition.column] = FOOD;
		snakeBoard[oldFoodLocation.row][oldFoodLocation.column] = PORTAL_1_SPACE;
		return snakeBoard;
	}

	public Pair getNewFoodLocation(int[][] snakeBoard) {
		freeIndexes = generateFreePositions(snakeBoard);
		Pair foodPosition = freeIndexes.get(new Random().nextInt(freeIndexes.size()));
		return foodPosition;
	}

	/* Get array list of portal locations */
	public List<Pair> getPortals(int[][] snakeBoard) {
		List<Pair> portals = new ArrayList<Pair>();
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (snakeBoard[i][j] == PORTAL) {
					// add portal to list
					//System.out.println("PORTAL=" + i + ":" + j);
					Pair newPortal = new Pair(i, j);
					portals.add(newPortal);
				}
			}
		}
		return portals;

	}

	/* Add snake to board to start */
	public int[][] addSnake(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++i) {
				if (snakeBoard[i][j] == SNAKE_START) {
					snakeBoard[i][j] = SNAKE;
					return snakeBoard;
				}
			}
		}
		return null;
	}

	/* Get all positions on starting/ending side of board and add to array list */
	public void getStartingSidePositions1(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (i > 0 && i < snakeBoard.length - 1 && j > 0 && j < SOFTWALL_LOCATION) {
					Pair newCoord = new Pair(i, j);
					startingWallSide.add(newCoord);
				}
			}
		}
	}

	public void getEndingSidePositions1(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (i > 0 && i < snakeBoard.length - 1 && j < snakeBoard[0].length - 1 && j > SOFTWALL_LOCATION) {
					Pair newCoord = new Pair(i, j);
					endingWallSide.add(newCoord);
				}
			}
		}

	}

	public void getStartingSidePositions2(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (i < 39 && j < SOFTWALL_LOCATION && i > 0 && j > 0) {
					Pair newCoord = new Pair(i, j);
					startingWallSide.add(newCoord);
				} else if (i >= 39 && i < snakeBoard.length - 1 && j > 0 && j < SOFTWALL_LOCATION - 4) {
					Pair newCoord = new Pair(i, j);
					startingWallSide.add(newCoord);
				}
			}
		}
	}

	public void getEndingSidePositions2(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (i <= 39 && i > 0 && j > SOFTWALL_LOCATION && j < snakeBoard[0].length - 1) {
					Pair newCoord = new Pair(i, j);
					endingWallSide.add(newCoord);
				} else if (i > 39 && i < snakeBoard.length - 1 && j > SOFTWALL_LOCATION - 4
						&& j < snakeBoard[0].length - 1) {
					Pair newCoord = new Pair(i, j);
					endingWallSide.add(newCoord);
				}
			}
		}
	}

	public void getStartingSidePositions3(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (i > 0 && i < 39 && j > 0 && j < 59) {
					Pair newCoord = new Pair(i, j);
					startingWallSide.add(newCoord);
				}
			}
		}
	}

	public void getEndingSidePositions3(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (i > 39 && i < snakeBoard.length - 1 && j > 0 && j <= 59) {
					Pair newCoord = new Pair(i, j);
					endingWallSide.add(newCoord);
				} else if (i > 0 && i < 59 && j < 59 && j > 79) {
					Pair newCoord = new Pair(i, j);
					endingWallSide.add(newCoord);
				}
			}
		}
	}

	public void getStartingSidePositions4(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (snakeBoard[i][j] == PORTAL_1_SPACE) {
					Pair newCoord = new Pair(i, j);
					startingWallSide.add(newCoord);
				}
			}
		}
	}

	public void getEndingSidePositions4(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				if (snakeBoard[i][j] == PORTAL_1_SPACE) {
					Pair newCoord = new Pair(i, j);
					endingWallSide.add(newCoord);
				}
			}
		}
	}

	/* Print game board (ignore) */
	public void printBoard(int[][] snakeBoard) {
		for (int i = 0; i < snakeBoard.length; ++i) {
			for (int j = 0; j < snakeBoard[0].length; ++j) {
				System.out.print(snakeBoard[i][j]);
			}
			System.out.println();
		}

	}
}
