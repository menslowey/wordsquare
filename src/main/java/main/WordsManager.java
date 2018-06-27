package main;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Stream;

public class WordsManager
{
	//Set and Array to allow contains lookup and random access lookup for starting words
	private ArrayList<String> wordList;
	private String characterString;

	public WordsManager(String resourceFileName, int lengthRequired, String allowed)
	{
		wordList = new ArrayList<>();
		this.characterString = allowed;

		//Get the distinct characters to count their occurence
		HashMap<Character, Integer> allowedCounts = getCharacterCounts(allowed);

		Stream<String> lines = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(resourceFileName))).lines();
		lines.forEach(line -> {
				if(line.length() == lengthRequired
						&& StringUtils.containsOnly(line, allowed))
				{
					if(withinCount(line, allowedCounts))
					{
						wordList.add(line);
					}
				}
		});

		lines.close(); //just in case but this shouldn't be necessary
	}

	synchronized String getWord(int wordNum)
	{
		if(wordNum >= 0 && wordNum < wordList.size())
		{
			return wordList.get(wordNum);
		}
		return null;
	}

	public synchronized int getAvailableWordCount()
	{
		return wordList.size();
	}

	private HashMap<Character, Integer> getCharacterCounts(String input)
	{
		HashMap<Character, Integer> countMap = new HashMap<>();

		HashSet<Character> uniqueChars = new HashSet<>();
		for(int i = 0; i < input.length(); i++)
		{
			uniqueChars.add(input.charAt(i));
		}

		for (Character c : uniqueChars)
		{
			countMap.put(c, StringUtils.countMatches(input, c));
		}

		return countMap;
	}

	private boolean withinCount(String input, HashMap<Character, Integer> allowedCounts)
	{
		HashMap<Character, Integer> inputCounts = getCharacterCounts(input);

		for (Character c : inputCounts.keySet())
		{
			Integer countActual = inputCounts.get(c);
			Integer allowedCount = allowedCounts.get(c);

			if(allowedCount == null || countActual > allowedCount)
			{
				return false;
			}
		}

		return true;
	}

	/////////////////////////

	String getCharacterString()
	{
		return characterString;
	}

	public synchronized ArrayList<String> getWordList()
	{
		return wordList;
	}
}
