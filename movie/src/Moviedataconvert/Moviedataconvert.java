package Moviedataconvert;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rishabh
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
public class Moviedataconvert {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("data/u.data"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/movies.csv"));
        String line;
        while((line=br.readLine())!=null){
            String[] values = line.split("\\t",-1);
            bw.write(values[0]+","+values[1]+","+values[2]+"\n");
            
           }
        br.close();
        bw.close();
            
        
        
    }
    
}
