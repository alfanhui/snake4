import static org.junit.Assert.*;

import org.junit.Test;

public class MapTest {

	@Test
	public void testCorrectSoftWalls() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		int wallCount = 0;
		for (int i = 0; i < newMap.ROW; ++i){
			for (int j = 0; j < newMap.COLUMN; ++j){
				if (board[i][j] == newMap.SOFTWALL){
					wallCount++;
				}
			}
		}//System.out.println("Wall:" + wallCount);
		assertTrue("Not sufficent soft wall values", wallCount == 338); //334 with vertical wall
	}
	@Test
	public void testCorrectPortal() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		int portalCount = 0;
		for (int i = 0; i < newMap.ROW; ++i){
			for (int j = 0; j < newMap.COLUMN; ++j){
				if (board[i][j] == newMap.PORTAL){
					portalCount++;
				}
			}
		}
		assertTrue("No portals present", portalCount == 2);
	}
	@Test
	public void testCorrectStart() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		int startCount = 0;
		for (int i = 0; i < newMap.ROW; ++i){
			for (int j = 0; j < newMap.COLUMN; ++j){
				if (board[i][j] == newMap.START){
					startCount++;
				}
			}
		}
		assertTrue("Incorrect start count", startCount == 1);
	}
	@Test
	public void testCorrectHardWall() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		int hardWallCount = 0;
		for (int i = 0; i < newMap.ROW; ++i){
			for (int j = 0; j < newMap.COLUMN; ++j){
				if (board[i][j] == newMap.HARDWALL){
					hardWallCount++;
				}
			}
		}
		assertTrue("Incorrect start count", hardWallCount == 0);
	}
	@Test
	public void testCorrectStartingList() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		System.out.println("Size is " + newMap.startingWallSide.size());
		assertTrue("Incorrect starting list size", newMap.startingWallSide.size() == 2704);
	}
	@Test
	public void testCorrectEndingList() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		System.out.println("Size is " + newMap.endingWallSide.size());
		assertTrue("Incorrect ending list size", newMap.endingWallSide.size() == 1758);
	}
	@Test
	public void testCorrectPrintout() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		newMap.printBoard(board);
		
	}
}
