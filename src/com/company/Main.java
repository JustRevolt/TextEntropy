package com.company;

import dnl.utils.text.table.TextTable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

//E:\University\3course\AppliedMath\lab1\program\src\com\company\War_and_Peace

public class Main {

    public static void main(String[] args) {
        byte[] text = connectToFile();
        TextAnalyzer analyzer = new TextAnalyzer(text);
        analyzer.analyse();
        printResults(analyzer);
    }

    private static byte[] connectToFile(){
        byte[] buffer = new byte[0];
        boolean connection = false;
        Scanner in = new Scanner(System.in);
        String fileName = "error";

        while (!connection) {
            System.out.println("Enter file name for analysis:");
            try {
                fileName = in.nextLine();
                FileInputStream fis = new FileInputStream(fileName);
                buffer = new byte[fis.available()];
                fis.read(buffer, 0, fis.available());
                connection = true;
                fis.close();
            } catch (FileNotFoundException e) {
                System.out.println("!!!File with name \"" + fileName + "\" not found!!!");
                System.out.println("!!!Try again!!!");
                connection = false;
            } catch (IOException e) {
                System.out.println("!!!Error connecting to file with name \"" + fileName + "\"!!!");
                System.out.println("!!!Try again!!!");
                connection = false;
            } catch (NoSuchElementException e){
                System.out.println("-----Closing program-----");
                System.exit(0);
            }
        }
        return buffer;
    }

    private static void printResults(TextAnalyzer analyzer){
        System.out.println("-----------------------------");
        System.out.println("Symbols in the text:"+analyzer.getTextLength());
        System.out.println();
        System.out.println("-----Characters of symbols-----");
        String[] columnNames1 = {"Space", "a/A", "b/B", "c/C", "d/D", "e/E", "f/F", "g/G", "h/H", "i/I", "j/J", "k/K", "l/L", "m/M"};
        String[] columnNames2 = {"n/N", "o/O", "p/P", "q/Q", "r/R", "s/S", "t/T", "u/U", "v/V", "w/W", "x/X", "y/Y", "z/Z", "Other"};
        Double[][] data1 = new Double[3][14];
        data1[0] = Arrays.copyOfRange(analyzer.getNumberOfSymbols(), 0,analyzer.getNumberOfSymbols().length/2);
        data1[1] = Arrays.copyOfRange(analyzer.getProbabilityOfSymbols(), 0,analyzer.getProbabilityOfSymbols().length/2);
        data1[2] = Arrays.copyOfRange(analyzer.getEntropyOfSymbols(), 0,analyzer.getEntropyOfSymbols().length/2);
        Double[][] data2 = new Double[3][14];
        data2[0] = Arrays.copyOfRange(analyzer.getNumberOfSymbols(),analyzer.getNumberOfSymbols().length/2, analyzer.getNumberOfSymbols().length);
        data2[1] = Arrays.copyOfRange(analyzer.getProbabilityOfSymbols(), analyzer.getProbabilityOfSymbols().length/2, analyzer.getProbabilityOfSymbols().length);
        data2[2] = Arrays.copyOfRange(analyzer.getEntropyOfSymbols(), analyzer.getEntropyOfSymbols().length/2, analyzer.getEntropyOfSymbols().length);
        TextTable tt1 = new TextTable(columnNames1,data1);
        TextTable tt2 = new TextTable(columnNames2,data2);
        tt1.printTable();
        tt2.printTable();
        System.out.println();
        System.out.println("Text entropy: "+analyzer.getTextEntropy()); ;
        System.out.println("Text entropy with pairs: "+analyzer.getTextEntropyWithPairs());
    }


}



