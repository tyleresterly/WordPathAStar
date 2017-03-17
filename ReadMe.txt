WordPath:
__________

This program finds and outputs the shortest path from the one word to the another word such that:
	a) Each word in the path must be a word in the given dictionary.
	b) Each word in the path is different from the next word in the path by only a
	   single modification.

Allowed modifications are:
	1) Adding one letter to a word (at the beginning, end or anywhere within).
	2) Removing one letter from a word.
	3) Replacing one letter with any other letter used in the dictionary 



______
Usage:
______
The main entry point is in the "AStar.java" class.
The program takes 2n+1 arguments from the command line. The first argument is a text file containing the dictionary 
to be used by the program. The other inputs are the pairs of strings that will have their shortest path calculated.

Example:
java -jar AStarWordPath_TylerEsterly.jar OpenEnglishWordList.txt green black

Outputs: 

green greek reek reck beck back black


_______
Credits:
________
the levenshteinDistance method in the AStar class is taken directly from 
https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance
with no modifcations at all.
