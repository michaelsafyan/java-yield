// Michael Safyan; Copyright (C) 2010; Simplified BSD License.
package com.google.code.jyield;
import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * This is the project's main class, providing the main program entry point
 * as well as the ability to parse the program's commandline arguments.
 *
 * @author Michael Aaron Safyan (michaelsafyan&#64;gmail.com)
 */
public class Main
{
    /**
     * Runs the project in interactive mode.
     */
    public static void interactiveMain(){
        commandlineMain();
    }
    
    /**
     * Runs the project in commandline mode.
     */
    public static void commandlineMain(){
        GeneratorToIterableConverter<Integer> converter = new TrivialInterfaceConverter<Integer>();
        Generator<Integer> example = new Generator<Integer>()
            {
                public void generate(Yieldable<Integer> yieldable) {
                    int count=0;
                    while (true){
                        yieldable.yield(count++);
                    }
                }
            };
        for ( int i : converter.convertGeneratorToIterable(example)) {
            System.out.println(i);
        }
    }

    /**
     * Reports the project's pretty name.
     * @return The project's pretty name.
     */
    public static String getProjectName(){
        return "Java Yield";
    }

    /**
     * Reports the project's POSIX code name.
     * @return The project's POSIX code name.
     */
    public static String getProjectCodeName(){
        return "java-yield";
    }

    /**
     * Reports the reverse-domain of the project vendor.
     * @return The reverse-domain of the project vendor.
     */
    public static String getProjectVendorID(){
        return "com.google.code";
    }

    /**
     * Reports the pretty name of the project vendor.
     * @return The pretty name of the project vendor.
     */
    public static String getProjectVendorName(){
        return "Google Code";
    }

    /**
     * Reports the reverse-domain application identifier.
     * @return The reverse-domain application identifier.
     */
    public static String getProjectID(){
        return getProjectVendorID()+"."+getProjectCodeName();
    }

    /**
     * Reports the project's major (compatibility) version.
     * @return The project's major (compatibility) version.
     */
    public static int getProjectMajorVersion(){
        return 1;
    }

    /**
     * Reports the project's minor (feature) version.
     * @return The project's minor (feature) version.
     */
    public static int getProjectMinorVersion(){
        return 0;
    }

    /**
     * Reports the project's patch (bug-fix) version.
     * @return The project's patch (bug-fix) version.
     */
    public static int getProjectPatchVersion(){
        return 0;
    }

    /**
     * Reports the project version as a version triplet string.
     * @return The project version as a version triplet string.
     */
    public static String getProjectVersion(){
        return Integer.toString(getProjectMajorVersion())+"."+Integer.toString(getProjectMinorVersion())+"."+Integer.toString(getProjectPatchVersion());
    }

    /**
     * Reports the year or range of years during which this project was created, edited, or modified.
     * @return The year or range of years during which this project was created, edited, or modified.
     */
    public static String getProjectCopyrightYears(){
        return "2010";
    }

    /**
     * Reports the commands necessary for opening/invoking this program.
     * @return The commands necessary for opening/invoking this program.
     */
    public static String getProjectInvocation(){
        if ( _invocation == null ){
            File jarfile = null;
            try{
                jarfile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                if ( !jarfile.exists() ){
                    jarfile = null;
                }
            }catch(Exception error){
                // IGNORE
            }
            if ( jarfile != null ){
                _invocation = "java -jar "+jarfile.toString();
            }else{
                _invocation = "java "+Main.class.getCanonicalName();
            }
        }
        return _invocation;
    }

    /**
     * Prints a help message to the user to allow him/her to
     * successfully interact with this program on the commandline.
     */
    public static void printHelpMessage(){
        System.out.println("Usage: "+getProjectInvocation()+" [options]");
        System.out.println("Options:");
        System.out.println("    --help                       Displays this help message.");
        System.out.println("    --version                    Prints the program version.");
        System.out.println("    --version-triplet            Prints the undecorated program version.");
        System.out.println("    --appid                      Prints the unique application identifier.");
        System.out.println("    --prefset <key>=<val>        Sets the given preference.");
        System.out.println("    --prefdel <key>              Unsets the given preference.");
        System.out.println("    --prefget <key>              Prints the given preference.");
        System.out.println("    --preflist                   Lists all preferences that are set.");
        System.out.println("    --loglevel <level>           Sets the current logging level.");
        System.out.println("    --loglevel <logger>=<level>  Sets the logging level for the given logger.");
        System.out.println("    --interactive                Run in interactive commandline mode.");
        System.out.println("Log Levels:");
        System.out.println("    all");
        System.out.println("    trace");
        System.out.println("    debug");
        System.out.println("    info");
        System.out.println("    warn");
        System.out.println("    error");
        System.out.println("    fatal");
        System.out.println("    off");
    }

    /**
     * Prints the decorated version information about this program.
     */
    public static void printVersionMessage(){
        System.out.println(getProjectName()+" v"+getProjectVersion());
        System.out.println(getProjectVendorName()+"; Copyright (C) "+getProjectCopyrightYears());
    }

    /**
     * Prints the barebones version information about this program.
     */
    public static void printVersionTripletMessage(){
        System.out.println(getProjectVersion());
    }

    /**
     * Prints this program's unique application identifier.
     */
    public static void printApplicationIdentifier(){
        System.out.println(getProjectID());
    }

    /**
     * This is the main program entry point. Modify this function to change
     * the behavior of this program or to add support for additional arguments.
     * @param args The commandline arguments passed into this program.
     */
    public static void main(String[] args){
        Logger rootlogger = Logger.getRootLogger();
        rootlogger.addAppender(new ConsoleAppender(new PatternLayout("[%-5p] %m%n")));
        rootlogger.setLevel(Level.INFO);
        boolean done=false;
        boolean gui=false;
        boolean interactive=false;
        int idx=0;
        while ( idx < args.length ){
            String arg = args[idx];
            if ( (arg.equalsIgnoreCase("help")) || (arg.equalsIgnoreCase("-help")) || (arg.equalsIgnoreCase("--help")) || (arg.equalsIgnoreCase("-h")) || (arg.equalsIgnoreCase("--")) || (arg.equals("-?")) || (arg.equals("--?")) || (arg.equals("?")) ){
                printHelpMessage();
                System.exit(0);
            }else if ( (arg.equalsIgnoreCase("version")) || (arg.equalsIgnoreCase("-version")) || (arg.equalsIgnoreCase("--version")) ){
                printVersionMessage();
                System.exit(0);
            }else if ( (arg.equalsIgnoreCase("-version-triplet")) || (arg.equalsIgnoreCase("--version-triplet")) ){
                printVersionTripletMessage();
                System.exit(0);
            }else if ( arg.equalsIgnoreCase("-prefset") || arg.equalsIgnoreCase("--prefset") ){
                // Verify that there is another argument
                if ( (idx+1) >= args.length ){
                    _logger.fatal("Option \""+arg+"\" requires a parameter.");
                    System.exit(1);
                }

                // Increment the index
                idx++;

                // Get the parameter
                String param = args[idx];

                // If it contains an '=' set the key
                int eqidx = param.indexOf("=");
                if ( eqidx == -1 ){
                    unsetPreference(param);
                }else{
                    setPreference(param.substring(0, eqidx),param.substring(eqidx+1));
                }
                done=true;
            }else if ( arg.equalsIgnoreCase("-prefget") || arg.equalsIgnoreCase("--prefget") ){
                 // Verify that there is another argument
                if ( (idx+1) >= args.length ){
                    _logger.fatal("Option \""+arg+"\" requires a parameter.");
                    System.exit(1);
                }

                // Increment the index
                idx++;

                // Get the parameter
                String param = args[idx];

                // Print the value
                printPreference(param);
                done=true;
            }else if ( arg.equalsIgnoreCase("-prefdel") || arg.equalsIgnoreCase("--prefdel") ){
                 // Verify that there is another argument
                if ( (idx+1) >= args.length ){
                    _logger.fatal("Option \""+arg+"\" requires a parameter.");
                    System.exit(1);
                }

                // Increment the index
                idx++;

                // Get the parameter
                String param = args[idx];

                // Unset the preference
                unsetPreference(param);
                done=true;
            }else if ( arg.equalsIgnoreCase("-preflist") || arg.equalsIgnoreCase("--preflist") ){
                printAllPreferences();
                done=true;
            }else if ( arg.equalsIgnoreCase("-loglevel") || arg.equalsIgnoreCase("--loglevel") ){
                 // Verify that there is another argument
                if ( (idx+1) >= args.length ){
                    _logger.fatal("Option \""+arg+"\" requires a parameter.");
                    System.exit(1);
                }

                // Increment the index
                idx++;

                // Get the parameter
                String param = args[idx];
                
                // Find the '=' and set the logging level
                int eqidx = param.indexOf('=');
                if ( eqidx != -1 ){
                    setLogLevel(param.substring(0,eqidx),param.substring(eqidx+1));
                }else{
                    setLogLevel(null,param);
                }
            }else if ( arg.equalsIgnoreCase("-appid") || arg.equalsIgnoreCase("--appid") || arg.equalsIgnoreCase("-application-identifier") || arg.equalsIgnoreCase("--application-identifier") ){
                printApplicationIdentifier();
                System.exit(0);
            }else if ( arg.equalsIgnoreCase("-interactive") || arg.equalsIgnoreCase("--interactive") ){
                if ( gui ){
                    _logger.fatal("Cannot specify both \"--gui\" and \"--interactive\" simultaneously.");
                    System.exit(1);
                }
                if ( interactive ){
                    _logger.warn("Option \""+arg+"\" already specified. Ignoring.");
                }
                interactive=true;
            }else{
                _logger.warn("Unrecognized option: \""+arg+"\". Ignoring");
            }
            idx++;
        }
        
        if ( !done ){
            if ( interactive ){
                interactiveMain();
            }else {
                commandlineMain();
            }
        }
    }

    // Converts a user-inputted key into a valid format for a
    // preference key. Since preference keys should appear to the
    // user as "a.b.c" instead of "a/b/c", slashes need to be replaced
    // with dots, and dots need to be used to navigate to the correct node.
    private static String asKey(String key){
        return key.replace('/', '.').replace('\\', '.');
    }

    // Removes the preference information associated with the given key.
    // The key may be a simple key, or a preference path to the key.
    private static void unsetPreference(String key){
        String properlyseparatedkey = asKey(key);
        Preferences node = _prefs;
        int lastsep=-1;
        int nextsep=properlyseparatedkey.indexOf(".");
        while ( nextsep != -1 ){
          node = node.node(properlyseparatedkey.substring(lastsep+1,nextsep));
          lastsep = nextsep;
          nextsep = properlyseparatedkey.indexOf(".", lastsep+1);
        }
        node.remove(properlyseparatedkey.substring(lastsep+1));
        try{
            node.flush();
        }catch(BackingStoreException error){
            _logger.error("Unable to unset preference \""+key+"\".", error);
        }
    }

    // Sets a preference association for the given key. The key
    // may be a simple key or a preference path to a key.
    private static void setPreference(String key, String val){
        String properlyseparatedkey = asKey(key);
        Preferences node = _prefs;
        int lastsep=-1;
        int nextsep=properlyseparatedkey.indexOf(".");
        while ( nextsep != -1 ){
          node = node.node(properlyseparatedkey.substring(lastsep+1,nextsep));
          lastsep = nextsep;
          nextsep = properlyseparatedkey.indexOf(".", lastsep+1);
        }
        node.put(properlyseparatedkey.substring(lastsep+1), val);
        try{
            node.flush();
        }catch(BackingStoreException error){
            _logger.error("Unable to set preference \""+key+"\".", error);
        }
    }

    // Prints the preference data associated with the given key. The key
    // may be a simple key or a complex preference path to a key.
    //
    private static void printPreference(String key){
        String properlyseparatedkey = asKey(key);
        Preferences node = _prefs;
        int lastsep=-1;
        int nextsep=properlyseparatedkey.indexOf(".");
        while ( nextsep != -1 ){
          node = node.node(properlyseparatedkey.substring(lastsep+1,nextsep));
          lastsep = nextsep;
          nextsep = properlyseparatedkey.indexOf(".", lastsep+1);
        }
        System.out.println(node.get(properlyseparatedkey.substring(lastsep+1),"undefined"));
    }

    // Prints all the preference data
    private static void printAllPreferences(){
        printPrefs(_prefs,"");
    }

    // Recursively prints the contents of the given preference node,
    // prefixing all keys in the node with the given prefix. This function
    // is a helper of "printAllPreferences()", which implements "--preflist".
    private static void printPrefs(Preferences node, String prefix){
        String[] keys = null;
        try {
            keys = node.keys();
        } catch (BackingStoreException error) {
            _logger.error("Unable to access keys for \""+prefix+"*\".",error);
        }
        if ( keys != null ){
            for ( String key : keys ){
                System.out.println(prefix+key+"="+node.get(key, "undefined"));
            }
        }

        String[] children = null;
        try {
            children = node.childrenNames();
        }catch(BackingStoreException error){
            _logger.error("Unable to access children for \""+prefix+"*\".",error);
        }
        if ( children != null ){
            for ( String child : children ){
                printPrefs(node.node(child),prefix+child+".");
            }
        }
    }

    // Sets the root logger to the given level.
    private static void setLogLevel(String loggername, String level){
        Logger logger = (((loggername==null)||(loggername.equals("")))?(Logger.getRootLogger()):(Logger.getLogger(loggername)));
        if ( level.equalsIgnoreCase("all") ){
            logger.setLevel(Level.ALL);
        }else if ( level.equalsIgnoreCase("trace") ){
            logger.setLevel(Level.TRACE);
        }else if ( level.equalsIgnoreCase("debug") ){
            logger.setLevel(Level.DEBUG);
        }else if ( level.equalsIgnoreCase("info") ){
            logger.setLevel(Level.INFO);
        }else if ( level.equalsIgnoreCase("warn") ){
            logger.setLevel(Level.WARN);
        }else if ( level.equalsIgnoreCase("error") ){
            logger.setLevel(Level.ERROR);
        }else if ( level.equalsIgnoreCase("fatal") ){
            logger.setLevel(Level.FATAL);
        }else if ( level.equalsIgnoreCase("off") || level.equalsIgnoreCase("none") ){
            logger.setLevel(Level.OFF);
        }else{
            _logger.fatal("Unrecognized logging level: \""+level+"\".");
            System.exit(1);
        }
    }

    private static Preferences _prefs = Preferences.userNodeForPackage(Main.class);
    private static Logger _logger = Logger.getLogger(Main.class);
    private static String _invocation = null;
}
