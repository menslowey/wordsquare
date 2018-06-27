import main.WordsManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestWordInput
{
	@Test
	/*
	Test that words get loaded from resource file successfully
	 */
	void testInput1()
	{
		WordsManager wordsManager = new WordsManager("testfile1.txt", 4, "eeeeddoonnnsssrv");

		assertEquals(4, wordsManager.getWordList().size());
	}

	@Test
	/*
	Test that the length requirement is being enforced
	 */
	void testInput2()
	{
		WordsManager wordsManager = new WordsManager("testfile1.txt", 6, "eeeeddoonnnsssrv");

		assertEquals(0, wordsManager.getWordList().size());
	}

	@Test
	/*
	Test that the character requirements are being enforced
	 */
	void testInput3()
	{
		WordsManager wordsManager = new WordsManager("testfile1.txt", 4, "gggggggggggggggqqqqqqq");

		assertEquals(0, wordsManager.getWordList().size());
	}
}
