package main;

import java.util.ArrayList;

public class CharacterManager
{
	private ArrayList<Character> originalChars;
	private ArrayList<Character> remainingChars;

	public CharacterManager(String characterString)
	{
		this.originalChars = new ArrayList<>();
		this.remainingChars = new ArrayList<>();

		for(int i = 0; i < characterString.length(); i++)
		{
			char c = characterString.charAt(i);
			originalChars.add(c);
			remainingChars.add(c);
		}
	}

	/**
	 * Check if a character has at least one instance available in the remaining characters list
	 * @param c Character to be checked against remainingChars
	 * @return TRUE if the character is available, FALSE otherwise
	 */
	private boolean isCharAvailable(Character c)
	{
		return remainingChars.contains(c);
	}

	/**
	 * Check to see if the character is available and remove it if it is
	 * @return TRUE is the character was available and has been removed FALSE if not
	 */
	private boolean useChar(Character c)
	{
		boolean available = isCharAvailable(c);

		if(available)
		{
			remainingChars.remove(c);
		}

		return available;
	}

	/**
	 * Make all characters available from the initial set
	 */
	public void reset()
	{
		remainingChars = new ArrayList<>(originalChars);
	}

	/**
	 * Process a given word / word fragment to ensure the rules of the word square are maintained
	 * and the letters are available for usage; If the word is not valid its characters will not be assigned after completion
	 * of the method call
	 * @param word the word with the characters being checked
	 * @return TRUE if the word/fragment can be used, FALSE otherwise
	 */
	public boolean assignCharactersForWordIfValid(String word)
	{
		StringBuilder charsUsed = new StringBuilder();

		boolean wordValid = true;

		//Letters have to be assigned due to the problem of duplicates
		for (int i = 0; i < word.length(); i++)
		{
			char c = word.charAt(i);
			if (useChar(c))
			{
				charsUsed.append(c);
			}
			else
			{
				wordValid = false;
			}
		}

		//If the word is not valid for any reason, release the characters assigned back to the CharacterManager
		if(!wordValid)
		{
			releaseCharacters(charsUsed.toString());
		}

		return wordValid;
	}

	void releaseCharacters(String word)
	{
		for(int i = 0; i < word.length(); i++)
		{
			remainingChars.add(word.charAt(i));
		}
	}

	public ArrayList<Character> getRemainingChars()
	{
		return remainingChars;
	}
}
