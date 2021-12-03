package uk.ac.dundee.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

public class FileIO {
    public int[][] readArrayFromFile(String boardResourcePath) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        ArrayList<ArrayList<Integer>> outterArrayList = new ArrayList<ArrayList<Integer>>();
        try {
            URL url = getClass().getClassLoader().getResource(boardResourcePath);
            fileReader = new FileReader(url.getPath());
            bufferedReader = new BufferedReader(fileReader);
            String nextLine;
            ArrayList<Integer> innerArrayList;
            String[] tempStringArray;
            while ((nextLine = bufferedReader.readLine()) != null) {
                tempStringArray = nextLine.split(" ");
                innerArrayList = new ArrayList<Integer>();
                for (int j = 0; j < tempStringArray.length; j++) {
                    innerArrayList.add(Integer.parseInt(tempStringArray[j]));
                }
                outterArrayList.add(innerArrayList);
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
        
        //Convert to int[][]
        return multiArrayListToMultiIntArray(outterArrayList);
    }
    
    public int[][] multiArrayListToMultiIntArray(ArrayList<ArrayList<Integer>> outterArrayList){
        if (outterArrayList.isEmpty()) return new int[0][0];

        int[][] array = new int[outterArrayList.size()][outterArrayList.get(0).size()];
        for(int i = 0; i< outterArrayList.size(); i++){
            array[i] = ArrayUtils.toPrimitive((Integer[])outterArrayList.get(i).toArray(new Integer[outterArrayList.get(i).size()]));
        }        
        return array;
    }

}
