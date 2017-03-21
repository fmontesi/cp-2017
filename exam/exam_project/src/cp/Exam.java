package cp;

import java.nio.file.Path;
import java.util.List;

/**
 * 
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class Exam
{
	/**
	 * This method recursively visits a directory to find all the text files contained in it and its subdirectories.
	 * 
	 * You should consider only files ending with a .txt suffix. You are guaranteed that they will be text files.
	 * 
	 * You can assume that each text file contains a (non-empty) comma-separated sequence of
	 * (positive) numbers. For example: 100,200,34,25
	 * There won't be any new lines, spaces, etc., and the sequence never ends with a comma.
	 * 
	 * The search is recursive: if the directory contains subdirectories,
	 * these are also searched and so on so forth (until there are no more
	 * subdirectories).
	 * 
	 * This method returns a list of results. The list contains a result for each text file that you find.
	 * Each {@link Result} stores the path of its text file, and the highest number (maximum) found inside of the text file.
	 * 
	 * @param dir the directory to search
	 * @return a list of results ({@link Result}), each giving the highest number found in a file
	 */
	public static List< Result > findAll( Path dir )
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Finds a file that contains at most (no more than) n numbers and such that all
	 * numbers in the file are equal or greater than min.
	 * 
	 * This method searches only for one (any) file in the directory
	 * (parameter dir) such that the condition above is respected.
	 * As soon as one such occurrence is found, the search can be
	 * stopped and the method can return immediately.
	 * 
	 * As for method {@code findAll}, the search is recursive.
	 */
	public static Result findAny( Path dir, int n, int min )
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Computes overall statistics about the occurrences of numbers in a directory.
	 * 
	 * This method recursively searches the directory for all numbers in all files and returns
	 * a {@link Stats} object containing the statistics of interest. See the
	 * documentation of {@link Stats}.
	 */
	public static Stats stats( Path dir )
	{
		throw new UnsupportedOperationException();
	}
}
