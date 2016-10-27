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
        
        Scanner scan = new Scanner(new FileReader("input.txt"));

        //reads the file "input.txt"
        PrintWriter writer = new PrintWriter("Test.java", "UTF-8");
        int parctr = 0, bractr = 0, lineerror = 0;
        boolean error = false, nohanging = false;
        
        while (sc.hasNext()) {
            String str = sc.nextLine();
            Space(str);
            DatatypeInt(str);
            DatatypeChar(str);
            Semicolon(str);
            
            if(str.contains("(")){
                parctr++;
                if(str.contains(")")){
                    parctr--;
                }
            }
            if(str.contains("{")){
                bractr++;
            }
            if(str.contains("}")){
                bractr--;
            }
        }
        
        if(parctr == 0 && bractr == 0){
                nohanging = true;
            }
        
        if(nohanging){
//            System.out.println(parctr + " " + bractr);
        while (scan.hasNext()) {
            String str = scan.nextLine();
            String checksoutpattern = "^System.labas.iprint\\(*\\)*";
            Pattern checksout = Pattern.compile(checksoutpattern);
            Matcher checksoutmatcher = checksout.matcher(str);
            if(checksoutmatcher.find()){
                error = true;
                break;
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
                String solvelengthpattern = "([a-z0-9A-Z]*[+][a-z0-9A-Z]*)";
                Pattern solvelength = Pattern.compile(solvelengthpattern);
                Matcher solvelengthmatcher = solvelength.matcher(hold);
                    if(solvelengthmatcher.find()){
                        String[] holdarr = solvelengthmatcher.group(0).split("[+]");
                        str = str.replaceAll("MakeSukat\\(.*\\);", "System.out.println(" + holdarr[0] + ".length()+" + holdarr[1] + ".length());");
                    }
            }
            
            String commentpattern = "(////.*)";
            Pattern comment = Pattern.compile(commentpattern);
            Matcher commentmatcher = comment.matcher(str);
            if(commentmatcher.find()){
                continue;
            }
            
            writer.println(str);
            lineerror++;
        }
        }
        
        if(parctr == 0 && bractr == 0 && !error){
            
        writer.close();
        List cmdAndArgs = Arrays.asList("cmd", "/c", "run.bat");
        File dir = new File(System.getProperty("user.dir"));

        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(dir);
        Process p = pb.start();
        
        }else{
            if(parctr > 0)
                System.out.println("ERROR Hanging Parenthesis");
            else if(bractr > 0)
                System.out.println("ERROR Hanging Bracket");
            else
                System.out.println("ERROR at " + lineerror);
        }
    }
    static int Space(String x){
        int i=0, j = x.length();
        for(int a = 0;a<j;a++)
        {
            if(x.charAt(a)==' ')
            {
                if(x.charAt(a+1)==' ')
                {
                    System.out.println("White space detected");
                    i=1;
                }
            }
            
        }
        return i;
    }
    
    static int DatatypeInt(String r){
        int i=0, j = r.length();
        for(int a = 0;a<j;a++)
        {
            if(r.matches(r)){
                System.out.println("int detected");
                i=1;
            }
        }
        return i;
    }
    static int DatatypeChar(String r){
        int c=0, j=r.length();
        for(int a=0; a<j; a++)
        {
            if(r.matches("char")){
                System.out.println("char detected");
                c=1;
            }    
        }
        return c;
    }
    static int DatatypeBool(String l){
        int b=0, j=l.length();
////        for(int a=0; a<j; a++)
////        {
            if(l.matches("boolean")){
                System.out.println("boolean detected");
                b=1;
            }
                
        //}
        return b;
    }
    
    
    static int Semicolon(String x){
        int i=0, j = x.length();
        //String[] arr = x.split(" ");  
        if(x.charAt(0)=='i'&&x.charAt(1)=='f'||x.charAt(0)=='{'||x.charAt(0)=='}')
        {
            System.out.println("Its okay to miss a semi");
            i = 0;
        }
        else if(x.charAt(j-1)==';'){
            System.out.println("Semicolon detected");
            i=0;
        }
        else{
            System.out.println("There is no semicolon");
            i = 1;
        }
        return i;
    }
}
