/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semicompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcgarcia
 */
public class SemiCompiler {


    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Scanner sc = new Scanner(new FileReader("input.txt"));

        //reads the file "input.txt"
        PrintWriter writer = new PrintWriter("Test.java", "UTF-8");
        int parctr = 0, bractr = 0;
        
        while (sc.hasNext()) {
            String str = sc.nextLine();
            
            if(str.contains("(")){
                parctr++;
            }
            if(str.contains(")")){
                parctr--;
            }
            if(str.contains("{")){
                bractr++;
            }
            if(str.contains("}")){
                bractr--;
            }
            
            String soutpattern = "(System.labas.iprint\\(.*\\);)";
            Pattern sout = Pattern.compile(soutpattern);
            Matcher soutmatcher = sout.matcher(str);
            if(soutmatcher.find()){
                String hold = soutmatcher.group(0).substring(19);
                str = str.replaceAll("System.labas.iprint\\(.*\\);", "System.out.println"+hold);
            }
            
            String lengthpattern = "(MakeSukat\\(.*\\);)";
            Pattern length = Pattern.compile(lengthpattern);
            Matcher lengthmatcher = length.matcher(str);
            if(lengthmatcher.find()){
                String hold = lengthmatcher.group(0).substring(9);
                System.out.println("hold = " + hold);
                String solvelengthpattern = "([a-z0-9A-Z]*[+][a-z0-9A-Z]*)";
                Pattern solvelength = Pattern.compile(solvelengthpattern);
                Matcher solvelengthmatcher = solvelength.matcher(hold);
                    if(solvelengthmatcher.find()){
                        String[] holdarr = solvelengthmatcher.group(0).split("[+]");
                        str = str.replaceAll("MakeSukat\\(.*\\);", "System.out.println(" + holdarr[0] + ".length()+" + holdarr[1] + ".length());");
                    }
            }
            
            writer.println(str);
        }
        
        if(parctr == 0 && bractr == 0){
            
        writer.close();
        List cmdAndArgs = Arrays.asList("cmd", "/c", "run.bat");
        File dir = new File(System.getProperty("user.dir"));

        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(dir);
        Process p = pb.start();
        
        }
    }
    
}