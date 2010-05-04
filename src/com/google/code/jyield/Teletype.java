// Michael Aaron Safyan. Copyright (C) 2009. Simplified BSD License.
package com.yourcompanyname.projectname;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.EOFException;

/**
 * This class simplifies console-based input and output.
 * @author Michael Aaron Safyan (michaelsafyan&#64;gmail.com)
 */
public class Teletype
{
    /**
     * Reads a string from standard input.
     * @return A string read from standard input.
     */
    public static String readString() {
        return readString("Please enter a string: ");
    }
    
    /**
     * Reads a string from standard input, using a custom prompt.
     * @param prompt The message that should be displayed to the user.
     * @return A string read from standard input.
     */
    public static String readString(String prompt) {
        String result = null;
        if ( (prompt!=null) && (!prompt.equals("")) ){
            System.out.print(prompt);
        }
        try{
            result = STDIN().readLine();
            if ( result == null ){
                throw new EOFException();
            }
        }catch(Exception error){
            error.printStackTrace();
            System.exit(1);
        }
        return result;
    }
    
    /**
     * Reads an integer from standard input.
     * @return An integer read from standard input.
     */
    public static int readInt() {
        return readInt("Please enter an integer: ");
    }
    
    /**
     * Reads an integer from standard input, using a custom prompt.
     * @param prompt The message that should be displayed to the user.
     * @return An integer read from standard input.
     */
    public static int readInt(String prompt){
        boolean done = true;
        int result = 0;
        do{
            String next = readString(prompt);
            try{
                result = Integer.parseInt(next);
            }catch(Exception error){
                done = false;
            }
        }while(!done);
        return result;
    }
    
    /**
     * Reads a double from standard input.
     * @return A double value read from standard input.
     */
    public static double readDouble() {
        return readDouble("Please enter a real number: ");
    }
    
    /**
     * Reads a double from standard input, using a custom prompt.
     * @param prompt The message that should be displayed to the user.
     * @return An double read from standard input. 
     */
    public static double readDouble(String prompt){
        boolean done = true;
        double result = 0.0;
        do{
            String next = readString(prompt);
            try{
                result = Double.parseDouble(next);
            }catch(Exception error){
                done = false;
            }
        }while(!done);
        return result;
    }
    
    /**
     * Reads a boolean from standard input.
     * @return A boolean read from standard input.
     */
    public static boolean readBool(){
        return readBool("Please enter a true/false value [Y/N]: ");
    }
    
    /**
     * Reads a boolean from standard input.
     * @param prompt The message that should be displayed to the user.
     * @return A boolean read from standard input.
     */
    public static boolean readBool(String prompt){
        boolean done = false;
        boolean result = false;
        do{
            String next = readString(prompt);
            if ( next.equalsIgnoreCase("y") || next.equalsIgnoreCase("yes") || next.equalsIgnoreCase("t") || next.equalsIgnoreCase("true") || next.equals("1") ){
                result = true;
                done = true;
            }else if ( next.equalsIgnoreCase("n") || next.equalsIgnoreCase("no") || next.equalsIgnoreCase("f") || next.equalsIgnoreCase("false") || next.equals("0") ){
                result = false;
                done = true;
            }
        }while(!done);
        return result;
    }
    
    /**
     * Prints a value to standard output.
     * @param val A string to print to standard output.
     */
    public static void print(String val){
        System.out.print(val);
    }
    
    /**
     * Prints a value to standard output and appends a newline.
     * @param val A string to print to standard output.
     */
    public static void println(String val){
        System.out.println(val);
    }
    
    // Reports STDIN (System.in) as a BufferedReader
    private static BufferedReader STDIN() {
        if ( _reader == null ){
            try{
                _reader = new BufferedReader(new InputStreamReader(System.in));
            }catch(Exception error){
                error.printStackTrace();
                System.exit(1);
            }
        }
        return _reader;
    }
    
    // STDIN
    private static BufferedReader _reader = null;
}
