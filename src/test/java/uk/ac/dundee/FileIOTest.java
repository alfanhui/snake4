package uk.ac.dundee;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import uk.ac.dundee.utils.FileIO;

public class FileIOTest {
    
    FileIO fileIO = new FileIO();

    @Test
    public void multiArrayListToMultiIntArrayConvertsCorrectly(){
        ArrayList<ArrayList<Integer>> outterArrayList = new ArrayList<ArrayList<Integer>>();
        
        outterArrayList.add(new ArrayList<>(Arrays.asList(new Integer[]{1, 1, 1})));
        outterArrayList.add(new ArrayList<>(Arrays.asList(new Integer[]{2, 3, 2})));
        outterArrayList.add(new ArrayList<>(Arrays.asList(new Integer[]{1, 1, 1})));

        int[][] array;
        array = fileIO.multiArrayListToMultiIntArray(outterArrayList);

        assertArrayEquals(array[0], new int[]{1, 1, 1});
        assertArrayEquals(array[1], new int[]{2, 3, 2});
        assertArrayEquals(array[2], new int[]{1, 1, 1});
    }
}
