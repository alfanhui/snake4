package uk.ac.dundee.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;


public class FileIO {
    public int[][] readArrayFromFile(int GRID_SIZE_X, int GRID_SIZE_Y) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        int[][] array = new int[GRID_SIZE_X][GRID_SIZE_Y];
        try {
            URL url = getClass().getClassLoader().getResource("boards/board1.txt");
            fileReader = new FileReader(url.getPath());
            bufferedReader = new BufferedReader(fileReader);
            String[] tempStringArray = new String[GRID_SIZE_Y];
            String nextLine = bufferedReader.readLine();

            for (int i = 0; i < array.length; i++) {
                tempStringArray = nextLine.split(" ");
                for (int j = 0; j < tempStringArray.length; j++) {
                    array[i][j] = Integer.parseInt(tempStringArray[j]);
                }
                nextLine = bufferedReader.readLine();
            }
        }

        catch (IOException e) {
            System.out.println("Cannot read file!");
        }
        try {
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Could not close file");
        }
        return array;
    }
}
