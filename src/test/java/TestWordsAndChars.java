import main.CharacterManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestWordsAndChars
{

	@Test
	/*
	  Test that characters assigned are removed from available, duplicates should remain
	 */
	void testCharacterUse1()
	{
		CharacterManager characterManager = new CharacterManager("deedder");

		boolean valid = characterManager.assignCharactersForWordIfValid("deed");

		assertTrue(valid);
		assertEquals(3, characterManager.getRemainingChars().size());

		List<Character> charList = characterManager.getRemainingChars();
		assertAll(
				()-> assertTrue(charList.contains('d')),
				()-> assertTrue(charList.contains('e')),
				()-> assertTrue(charList.contains('r'))
		);
	}

	@Test
	/*
	  Test that characters that do not exist do not un-assign characters
	 */
	void testCharacterUse2()
	{
		CharacterManager characterManager = new CharacterManager("deeddoor");

		boolean valid = characterManager.assignCharactersForWordIfValid("sock");

		assertFalse(valid);
		assertEquals(8, characterManager.getRemainingChars().size());
	}

	@Test
	/*
	  Test that when some characters are valid but the word is not, all characters are left available at completion
	 */
	void testCharacterUse3()
	{
		CharacterManager characterManager = new CharacterManager("deedsock");

		boolean valid = characterManager.assignCharactersForWordIfValid("deer");

		assertFalse(valid);
		assertEquals(8, characterManager.getRemainingChars().size());
	}


}
