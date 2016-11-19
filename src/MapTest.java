import static org.junit.Assert.*;

import org.junit.Test;

public class MapTest {

	@Test
	public void testCorrectSoftWalls() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		int wallCount = 0;
		for (int i = 0; i < newMap.COLUMN; ++i){
			for (int j = 0; j < newMap.ROW; ++j){
				if (board[i][j] == newMap.SOFTWALL){
					wallCount++;
				}
			}
		}
		assertTrue("Not sufficent soft wall values", wallCount == 236);
	}
	@Test
	public void testCorrectPortal() {
		Map newMap = new Map();
		int[][] board = newMap.initialiseBoard();
		
		int portalCount = 0;
		for (int i = 0; i < newMap.COLUMN; ++i){
			for (int j = 0; j < newMap.ROW; ++j){
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
		for (int i = 0; i < newMap.COLUMN; ++i){
			for (int j = 0; j < newMap.ROW; ++j){
				if (board[i][j] == newMap.START){
					startCount++;
				}
			}
		}
		assertTrue("Incorrect start count", startCount == 1);
	}
}
