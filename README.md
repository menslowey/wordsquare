# wordsquare
Java implementation to solve the creation of a word square

This tool operates off of the command line and is intended for use as a .jar
Outputs to stdout either a String list (line terminated by word) to display the word square solution found or "No Solution Found"

Accepts and Requires two inputs:
  Order         - the Word Square Order, the program will attempt to create an n * n square to satisfy the word square order
  Character Set - the allowed set of character to create words in the square, must be equal in length to Order^2; each character is used only once

For more information on word squares see: https://en.wikipedia.org/wiki/Word_square

This tool functions by relying on the properties of word squares and the dictionary file found in the resources folder.
First stage processing after inputs are validated reduces the available word space maximally using length and character restrictions
based off of the order and character set inputs
The resulting reduced word list is then deterministically processed to try and solve the square. Character usage is tracked and as word
attempts are made/accepted/rejected updated. Recursion is used to process each additional word.
Current accepted words in the solution (which may be rejected in future recursions as a non-valid solution) are used to limit
the validity of future words and ensure the word square constraints are met.

# Example usage:

java -jar wordsquare-1.0.jar 4 eeeeddoonnnsssrv
