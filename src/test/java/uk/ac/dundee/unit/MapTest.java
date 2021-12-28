package uk.ac.dundee.unit;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.dundee.Map;

public class MapTest {

	@Test
	public void testCorrectSoftWalls() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard("boards/board1.txt", false, false);
		
		int wallCount = 0;
		for (int i = 0; i < board.length; ++i){
			for (int j = 0; j < board[i].length; ++j){
				if (board[i][j] == Map.SOFTWALL){
					wallCount++;
				}
			}
		}
		assertTrue("Not sufficent soft wall values", wallCount == 334);
	}
	@Test
	public void testCorrectPortal() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard("boards/board1.txt", false, false);
		
		int portalCount = 0;
		for (int i = 0; i < board.length; ++i){
			for (int j = 0; j < board[i].length; ++j){
				if (board[i][j] == Map.PORTAL){
					portalCount++;
				}
			}
		}
		assertTrue("No portals present", portalCount == 2);
	}
	// @Test
	// public void testCorrectStart() {
	// 	Map newMap = new Map();
	// 	int[][] board = newMap.initialiseBoard();
		
	// 	int startCount = 0;
	// 	for (int i = 0; i < newMap.ROW; ++i){
	// 		for (int j = 0; j < newMap.COLUMN; ++j){
	// 			if (board[i][j] == Map.SNAKE_START){
	// 				startCount++;
	// 			}
	// 		}
	// 	}
	// 	assertTrue("Incorrect start count", startCount == 1);
	// }
	@Test
	public void testCorrectHardWall() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard("boards/board1.txt", false, false);
		
		int hardWallCount = 0;
		for (int i = 0; i < board.length; ++i){
			for (int j = 0; j < board[i].length; ++j){
				if (board[i][j] == Map.HARDWALL){
					hardWallCount++;
				}
			}
		}
		assertTrue("Incorrect start count", hardWallCount == 0);
	}

	// @Test
	// public void testCorrectStartingList() {
	// 	Map newMap = new Map();
	// 	newMap.initialiseBoard();
		
	// 	System.out.println("Starting list Size is " + newMap.startingWallSide.size());
	// 	assertTrue("Incorrect starting list size", newMap.startingWallSide.size() == 2784);
	// }
	// @Test
	// public void testCorrectEndingList() {
	// 	Map newMap = new Map();
	// 	newMap.initialiseBoard();
		
	// 	System.out.println("Ending list Size is " + newMap.endingWallSide.size());
	// 	assertTrue("Incorrect ending list size", newMap.endingWallSide.size() == 1682);
	// }

	// @Test
	// public void testCorrectPrintout() {
	// 	Map newMap = new Map();
	// 	int[][] board = newMap.initialiseBoard();
	// 	newMap.printBoard(board);
	// }
}
