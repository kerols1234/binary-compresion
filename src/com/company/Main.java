package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.lang.Math;


public class Main {

    public static void main(String[] args) {
        try {
            floatingPiontCompresion("file.txt");
            floatingPiontDecompresion("comp.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void floatingPiontDecompresion(String fileName) throws IOException {
        HashMap<Character, Double> map = new HashMap<>();
        HashMap<Integer, Character> c = new HashMap<>();
        String str, data = "";
        double Size = 0;
        String code = "";
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        str = br.readLine();
        Size = Double.parseDouble(str);
        str = br.readLine();
        code = str;
        String[] t = new String[2];
        int j = 0;
        while ((str = br.readLine()) != null) {
            t = str.split(" ");
            map.put(t[0].charAt(0), Double.parseDouble(t[1]));
            c.put(j, t[0].charAt(0));
            j++;
        }
        br.close();
        fr.close();

        double[] arr = new double[c.size() + 1];
        arr[0] = 0;
        double smallest = 1.1;
        arr[1] = map.get(c.get(0)) / Size;
        for (int i = 0; i < c.size(); i++) {
            arr[i + 1] = (map.get(c.get(i)) / Size) + arr[i];
            if(smallest > ((map.get(c.get(i)) / Size))){
                smallest = (map.get(c.get(i)) / Size);
            }
        }
        int k = (int)(Math.log(1/smallest)/Math.log(2)) + 1;

        j = 0;
        double low = 0, high = 1, range = 1;
        int z = 0,b = 0;
        double f;
        f=Integer.parseInt(code.substring(b, k), 2);
        f=f/Math.pow(2,k);
        do {
            for (int i = 0; i < c.size(); i++) {
                if (arr[i] <= f && arr[i+1] > f) {
                    z = i;
                }
            }
            data += c.get(z);
            high = arr[z + 1];
            low = arr[z];
            range = high - low;
            if(high <= (0.5) || low >= (0.5)){
                while (high <= (0.5) || low >= (0.5)) {
                    if (high <= 0.5) {
                        high = high * 2;
                        low = low * 2;
                    } else if (low >= 0.5) {
                        high = (high - 0.5) * 2;
                        low = (low - 0.5) * 2;
                    }
                    b++;
                }
                f=Integer.parseInt(code.substring(b, k+b), 2);
                f=f/Math.pow(2,k);
            }else{
                f = (f - low)/range;
            }
            j++;
        } while (j < Size);

        FileWriter fileWriter = new FileWriter("decomp.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write( data);

        bufferedWriter.close();
        fileWriter.close();
    }


    public static void floatingPiontCompresion(String fileName) throws IOException {

        HashMap<Character, Double> map = new HashMap<>();
        HashMap<Integer, Character> c = new HashMap<>();
        ArrayList<Character> arr1 = new ArrayList<Character>();
        String str, data = "";
        double Size = 0;
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        while ((str = br.readLine()) != null) {
            for (int i = 0; i < str.length(); i++) {
                if (map.containsKey(str.charAt(i))) {
                    map.put(str.charAt(i), map.get(str.charAt(i)) + 1);
                } else {
                    map.put(str.charAt(i), (double) 1);
                    c.put(c.size(), str.charAt(i));
                }
            }
            data += str;
        }
        Size = data.length();
        br.close();
        fr.close();

        double[] arr = new double[c.size() + 1];
        arr[0] = 0;
        arr[1] = map.get(c.get(0)) / Size;
        double smallest = 1.1;
        for (int i = 0; i < c.size(); i++) {
            arr[i + 1] = (map.get(c.get(i)) / Size) + arr[i];
            if(smallest > ((map.get(c.get(i)) / Size))){
                    smallest = (map.get(c.get(i)) / Size);
            }
        }
        int j = 0;
        double low = 0, high = 1, range = 1;
        int z = 0;
        do {
            for (int i = 0; i < c.size(); i++) {
                if (data.charAt(j) == c.get(i)) {
                    z = i;
                }
            }
            high = low + (range * arr[z + 1]);
            low = low + (range * arr[z]);
            while (high <= (0.5) || low >= (0.5)) {
                if (high <= 0.5) {
                    high = high * 2;
                    low = low * 2;
                    arr1.add('0');
                } else if (low >= 0.5) {
                    high = (high - 0.5) * 2;
                    low = (low - 0.5) * 2;
                    arr1.add('1');
                }
            }
            range = high - low;
            j++;
        } while (j < data.length());

        int k = (int)(Math.log(1/smallest)/Math.log(2)) + 1;
        arr1.add('1');
        for(int i = 1; i < k; i++){
            arr1.add('0');
        }
        FileWriter fileWriter = new FileWriter("comp.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(String.valueOf(Size) + '\n');
        for (int i = 0; i < arr1.size(); i++) {
            bufferedWriter.write(arr1.get(i));
        }
        bufferedWriter.write('\n');
        for (int i = 0; i < c.size(); i++) {
            bufferedWriter.write(c.get(i) + " " + map.get(c.get(i)) + '\n');
        }


        bufferedWriter.close();
        fileWriter.close();
    }

}