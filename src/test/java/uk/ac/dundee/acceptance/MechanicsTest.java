package uk.ac.dundee.acceptance;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.dundee.Pair;
import uk.ac.dundee.Snake4;

public class MechanicsTest {
    
    private Snake4 game;
    private java.util.List<Pair> gameSnake;

    @Test
    public void move1UpTest(){
        game = new Snake4("boards/spaces4x4.txt", true, false, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(1,1), gameSnake.get(0));
        // game.setDirection('u'); default start direction is up
        gameSnake = game.move();
        assertEquals(new Pair(0,1), gameSnake.get(game.getSnake().size()-1));
    }

    @Test
    public void move1LeftTest(){
        game = new Snake4("boards/spaces4x4.txt", true, false, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(1,1), gameSnake.get(0));
        game.setDirection('l');
        gameSnake = game.move();
        assertEquals(new Pair(1,0), gameSnake.get(game.getSnake().size()-1));
    }

    @Test
    public void move1RightTest(){
        game = new Snake4("boards/spaces4x4.txt", true, false, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(1,1), gameSnake.get(0));
        game.setDirection('r');
        gameSnake = game.move();
        assertEquals(new Pair(1,2), gameSnake.get(game.getSnake().size()-1));
    }

    @Test
    public void move1DownTest(){
        game = new Snake4("boards/spaces4x4.txt", true, false, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(1,1), gameSnake.get(0));
        game.setDirection('d');
        gameSnake = game.move();
        assertEquals(new Pair(2,1), gameSnake.get(game.getSnake().size()-1));
    }

    @Test
    public void moveIntoPortalFromLeftTest(){
        game = new Snake4("boards/portalsLeft.txt", true, true, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(1,2), gameSnake.get(0));
        game.setDirection('l');
        gameSnake = game.move();
        assertEquals(new Pair(1,3), gameSnake.get(game.getSnake().size()-1));
    }

    @Test
    public void moveIntoPortalFromRightTest(){
        game = new Snake4("boards/portalsRight.txt", true, true, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(1,3), gameSnake.get(0));
        game.setDirection('r');
        gameSnake = game.move();
        assertEquals(new Pair(1,2), gameSnake.get(game.getSnake().size()-1));
    }

    @Test
    public void moveIntoPortalFromAboveTest(){
        game = new Snake4("boards/portalsAbove.txt", true, true, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(2,1), gameSnake.get(0));
        // game.setDirection('u'); default start direction is up
        gameSnake = game.move();
        assertEquals(new Pair(0,4), gameSnake.get(game.getSnake().size()-1));
    }

    @Test
    public void moveIntoPortalFromBelowTest(){
        game = new Snake4("boards/portalsBelow.txt", true, true, false);
        gameSnake = game.getSnake();
        assertEquals(new Pair(0,4), gameSnake.get(0));
        game.setDirection('d');
        gameSnake = game.move();
        assertEquals(new Pair(2,1), gameSnake.get(game.getSnake().size()-1));
    }
}
