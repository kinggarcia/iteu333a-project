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

        while (sc.hasNext()) {
            String str = sc.nextLine();
            //replaces kung with if
            str = str.replaceAll("kung", "if");
            
            String digitpattern = "(System.labas.iprint\\(\".*\"\\))";
            Pattern digit = Pattern.compile(digitpattern);
            Matcher digitmatcher = digit.matcher(str);
            if(digitmatcher.find()){
                str = str.replaceFirst("System.labas.iprint", "System.out.println");
            }

//            str = str.replaceAll("(System.labas.iprint\\(\"[a-z]*\"\\))", "System.out.println\\(\"[a-z]*\"\\)");
            //System.out.println("");
            writer.println(str);
        }
        
        writer.close();
        List cmdAndArgs = Arrays.asList("cmd", "/c", "run.bat");
        File dir = new File(System.getProperty("user.dir"));

        ProcessBuilder pb = new ProcessBuilder(cmdAndArgs);
        pb.directory(dir);
        Process p = pb.start();
    }
    
}