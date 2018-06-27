package main;

import java.util.ArrayList;
import java.util.logging.Logger;

public class Solver
{
	private Logger logger;

	private CharacterManager charManager;
	private int order;
	private WordsManager wordsManager;

	private ArrayList<String> solutionWords;

	private int startWordStartIndex;
	private int startWordEndIndex;

	public Solver(int order, WordsManager wordsManager, int startRange, int endRange)
	{
		this.order = order;
		this.charManager = new CharacterManager(wordsManager.getCharacterString());
		this.wordsManager = wordsManager;
		this.startWordStartIndex = startRange;
		this.startWordEndIndex = endRange;

		logger = Logger.getLogger(getClass().getSimpleName() + "_" + startRange + "_" + endRange);
	}

	public ArrayList<String> runSolver()
	{
		logger.info("Solver running: " + logger.getName());
		//Pick a word as a starting point
		//The assumption is from the word manager to only offer valid words

		for(int i = startWordStartIndex; i < startWordEndIndex; i++)
		{
			String startWord = wordsManager.getWord(i);
			if(startWord != null && startWord.length() > 0)
			{
				if(makeAttempt(startWord))
				{
					return solutionWords;
				}
				else
				{
					charManager.reset();
				}
			}
		}

		logger.info("No solution found");
		return null;
	}

	private boolean makeAttempt(String startWord)
	{
		logger.entering("Solver", "makeAttempt", startWord);
		solutionWords = new ArrayList<>();

		if(!charManager.assignCharactersForWordIfValid(startWord))
		{
			return false;
		}

		solutionWords.add(0, startWord);

		return nextWord(1);
	}

	private boolean nextWord(int wordNum)
	{
		logger.entering("Solver", "nextWord", wordNum);
		//Previous words inform the needed characters for future words

		StringBuilder startsWith = new StringBuilder();
		for (int i = 0; i < wordNum; i++)
		{
			String previousWord = solutionWords.get(i);
			if (previousWord == null)
			{
				throw new IllegalStateException("Reached word without building previous words: " + wordNum);
			}
			startsWith.append(previousWord.charAt(wordNum));
		}

		String startsWithString = startsWith.toString();

		for (int i = 0; i < wordsManager.getAvailableWordCount(); i++)
		{
			String word = wordsManager.getWord(i);

			if(!word.startsWith(startsWithString))
			{
				continue;
			}

			if (charManager.assignCharactersForWordIfValid(word))
			{
				solutionWords.add(wordNum, word);

				if(wordNum == order-1) //again due to zero indexing
				{
					return true;
				}
				else
				{
					if(!nextWord(wordNum+1))
					{
						//Since this word was previously accepted, its characters must be released
						charManager.releaseCharacters(solutionWords.get(wordNum));
						solutionWords.remove(wordNum);
					}
					else
					{
						return true;
					}
				}
			}
		}

		//we have completed all permutations and could not find a valid word, a previous word has to change
		return false;
	}

}
