/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semicompiler;

import java.io.File;
import java.io.FileInputStream;
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
        
        File file = new File("input.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        
        //reads the file "input.txt"
        PrintWriter writer = new PrintWriter("Test.java", "UTF-8");
        int parctr = 0, bractr = 0, lineerror = 1;
        boolean error = false, nohanging = false;
        
        while (sc.hasNext()) {
            String str = sc.nextLine();
            
            DatatypeInt(str);
            DatatypeChar(str);
            DatatypeString(str);
            DatatypeFloat(str);
            DatatypeDouble(str);
            Space(str);
            Semicolon(str);
            DatatypeLine(str);
            
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
                System.out.println("White space detected");
                i=1;
            }   
        }
    return i;
    }
    static int DatatypeLine (String r) {
        String dNewline = new String ("");
        if (r.matches("")) {
            System.out.println("NEW LINE");
        }
        else {
            System.out.println("NO NEW LINE");
        }
        return 1;
    }
    static int DatatypeInt (String r) {
        String dInt = new String ("int");
        if (r.matches("(.*)int(.*)")) {
            System.out.println("int detected");
        }
        else {
            System.out.println("no int datatype detected");
        }
        return 1;
    }
    static int DatatypeChar(String r) {
        String dChar = new String ("char");
        if (r.matches("(.*)char(.*)")) {
            System.out.println("char detected");
        }
        else {
            System.out.println("no char datatype detected");
        }
        return 1;
    }
    static int DatatypeString(String l) {
        String dString = new String ("string");
        if (l.matches("(.*)string(.*)")) {
            System.out.println("string detected");
        }
        else {
            System.out.println("no string datatype detected");
        }
        return 1;
    }
    static int DatatypeFloat(String l) {
        String dFloat = new String ("float");
        if (l.matches("(.*)float(.*)")) {
            System.out.println("float detected");
        }
        else {
            System.out.println("no float datatype detected");
        }
        return 1;
    }
    static int DatatypeDouble(String l) {
        String dDouble = new String ("double");
        if (l.matches("(.*)double(.*)")) {
            System.out.println("double detected");
        }
        else {
            System.out.println("no double datatype detected");
        }
        return 1;
    }
    static int Semicolon(String x) {
        String dSemicolon = new String (";");
        if (x.matches("(.*);(.*)")) {
            System.out.println("; detected");
        }
        else {
            System.out.println("no ; detected");
        }
        return 1;
    }
}
