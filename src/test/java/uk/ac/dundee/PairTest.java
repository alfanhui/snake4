package uk.ac.dundee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class PairTest {

    @Test
    public void equalsTest(){
        Pair a = new Pair(1, 2);
        Pair b = new Pair(10, 20);
        Pair c = new Pair(1, 2);
        Pair d = new Pair(1, 3);
        assertEquals(a, c);
        assertNotEquals(a, b);
        assertNotEquals(a, d);
    }
}
