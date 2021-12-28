package uk.ac.dundee.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileIO {
    public int[][] readArrayFromFile(String boardResourcePath) {
        ArrayList<ArrayList<Integer>> outterArrayList = new ArrayList<ArrayList<Integer>>();

        try (InputStream stream = this.getClass().getClassLoader().getResourceAsStream(boardResourcePath);
                InputStreamReader isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);) {

            br.lines().forEach(nextLine -> {
                String[] tempStringArray = nextLine.split(" ");
                ArrayList<Integer> innerArrayList = new ArrayList<Integer>();
                for (int j = 0; j < tempStringArray.length; j++) {
                    innerArrayList.add(Integer.parseInt(tempStringArray[j]));
                }
                outterArrayList.add(innerArrayList);
            });
        } catch (IOException e) {
            System.out.println("Cannot read file!" + e.getMessage());
        }
        // Convert to int[][]
        return multiArrayListToMultiIntArray(outterArrayList);
    }

    public int[][] multiArrayListToMultiIntArray(ArrayList<ArrayList<Integer>> outterArrayList) {
        if (outterArrayList.isEmpty())
            return new int[0][0];

        int[][] array = new int[outterArrayList.size()][outterArrayList.get(0).size()];
        for (int i = 0; i < outterArrayList.size(); i++) {
            array[i] = outterArrayList.get(i).stream().mapToInt(j -> j).toArray();
        }
        return array;
    }

}
