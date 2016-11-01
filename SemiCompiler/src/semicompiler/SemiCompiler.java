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
        int parctr = 0, bractr = 0, lineerror = 1;
        boolean error = false, nohanging = false;
        
        while (sc.hasNext()) {
            String str = sc.nextLine();
//                Space(str);
//                DatatypeInt(str);
//                DatatypeChar(str);
//                DatatypeString(str);
//                Semicolon(str);
            
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
//            String checksoutpattern = "^System.labas.iprint\\(*\\)*";
//            Pattern checksout = Pattern.compile(checksoutpattern);
//            Matcher checksoutmatcher = checksout.matcher(str);
//            if(checksoutmatcher.find()){
//                error = true;
//                break;
//            }
            
            String soutpattern = "(System.labas.iprint\\(.*\\);)";
            Pattern sout = Pattern.compile(soutpattern);
            Matcher soutmatcher = sout.matcher(str);
            if(soutmatcher.find()){
                String hold = soutmatcher.group(0).substring(19);
                str = str.replaceAll("System.labas.iprint\\(.*\\);", "System.out.println"+hold);
                writer.println(str);
            }
            
            String commentpattern = "(////.*)";
            Pattern comment = Pattern.compile(commentpattern);
            Matcher commentmatcher = comment.matcher(str);
            if(commentmatcher.find()){
                continue;
            }
            
//            String checklengthpattern = "(^MakeSukat\\(*.*\\)*)";
//            Pattern checklength = Pattern.compile(checklengthpattern);
//            Matcher checklengthmatcher = checklength.matcher(str);
//            if(checklengthmatcher.find()){
//                error = true;
//                break;
//            }
            
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
                        writer.println(str);
                    }
            }
            
            String mdaspattern = "(MakeKompyut\\(.*\\);)";
            Pattern mdas = Pattern.compile(mdaspattern);
            Matcher mdasmatcher = mdas.matcher(str);
            if(mdasmatcher.find()){
                String hold = mdasmatcher.group(0).substring(11);
                str = str.replaceAll("MakeKompyut\\(.*\\);", "System.out.println" + hold);
                writer.println(str);
//                System.out.println((8+2+3)/13);
            }
            
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
                System.out.println("ERROR at Line " + lineerror);
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
            if(r.charAt(a)=='i')
            {
                if(r.charAt(a+1)=='n')
                {
                    if(r.charAt(a+2)=='t')
                    {
                        System.out.println("int detected");
                        i=1;
                    }
                }
            }   
        }
        return i;
    }
    static int DatatypeChar(String r){
        int c=0, j=r.length();
        for(int a=0; a<j; a++)
        {
            if(r.charAt(a)=='c')
            {
                if(r.charAt(a+1)=='h')
                {
                    if(r.charAt(a+2)=='a')
                    {
                        if(r.charAt(a+3)=='r')
                        {
                            System.out.println("char detected");
                            c=1;
                        }
                    }
                }
            }    
        }
        return c;
    }
    static int DatatypeString(String l){
        int b=0, j=l.length();
        for(int a=0; a<j; a++)
        {
            //if(l.matches())
            if(l.charAt(a)=='s')
            {
                if(l.charAt(a+1)=='t')
                {
                    if(l.charAt(a+2)=='r')
                    {
                        if(l.charAt(a+3)=='i')
                        {
                            if(l.charAt(a+4)=='n')
                            {
                                if(l.charAt(a+5)=='g')
                                {
                                    System.out.println("string detected");
                                    b=1;
                                }
                            }
                        }
                    }
                }
            }    
        }
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
