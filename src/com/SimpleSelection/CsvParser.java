package com.SimpleSelection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CsvParser {

    public static List<Integer> read(String fileName) {
        File file= new File(fileName);

        // this gives you a 2-dimensional array of strings
        List<Integer> lines = new ArrayList<>();
        Scanner inputStream;

        try{
            inputStream = new Scanner(file);

            while(inputStream.hasNext()){
                String line= inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                for(int i =0;i<values.length;i++){
                    lines.add(Integer.parseInt(values[i]));
                }
            }

            inputStream.close();
            return lines;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}