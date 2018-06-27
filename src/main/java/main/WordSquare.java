package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class WordSquare {

    private static final String WORDLIST_FILENAME = "enable1.txt";
	private static final String USAGE_FILENAME = "usage.txt";

	private static Logger logger = Logger.getLogger("WordSquareMain");

    public static void main(String[] args)
    {
        //validate inputs; expected length and character string
        if(args.length != 2)
        {
            printUsage();
            return;
        }

        int order;
        String characterInput = args[1];

        try
        {
            order = Integer.parseInt(args[0]);
        }
        catch (NumberFormatException nfe)
        {
            System.err.println("First argument should be word square order, represented by int");
            printUsage();
            return;
        }

        if(characterInput.length() != order*order)
        {
            System.err.println("Length of character input should have length equal to order^2");
            printUsage();
            return;
        }

        logger.info("CLI inputs validated");
        logger.info("Beginning load of words file");
        WordsManager wordsManager = new WordsManager(WORDLIST_FILENAME, order, characterInput);

        logger.info("Preparing word square solver");
        Solver solver = new Solver(order, wordsManager, 0, wordsManager.getAvailableWordCount());
        ArrayList<String> solution = solver.runSolver();
        logger.info("Solver finished");

        if(solution != null)
        {
            System.out.println();
			System.out.println("Solution Found");
            System.out.println("////////////////////////////");
            for(String s : solution)
            {
                System.out.println(s);
            }
            System.out.println("////////////////////////////");
        }
        else
        {
            System.out.println("////////////////////////////");
            System.out.println("No Solution Found");
            System.out.println("////////////////////////////");
        }
    }

    private static void printUsage()
    {
    	try
		{
			Stream<String> lines = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(USAGE_FILENAME))).lines();
			lines.forEach(System.out::println);
			lines.close();
		}
		catch (Exception e)
		{
			printFallbackUsage(e);
		}
	}

	private static void printFallbackUsage(Exception e)
	{
		System.err.println("Could not load detailed usage information");
		if(e != null)
		{
			e.printStackTrace();
		}

		System.out.println("Expected syntax: ");
		System.out.println("java -jar wordsquare-{version}.jar <Order> <Character Sequence> ");
	}

}
