package cp;



import java.nio.file.Path;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public interface Result
{
	/**
	 * The file ({@link Path}) of this result
	 * @return file ({@link Path}) of this result
	 */
	public Path path();
	
	/**
	 * The sum of all numbers found in this file
	 * @return sum of all numbers found in this file
	 */
	public int sum();
}
