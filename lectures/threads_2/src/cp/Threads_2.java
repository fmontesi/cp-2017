package cp;

/**
 * Some examples on thread synchronization.
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class Threads_2
{
	public static void main( String[] args )
	{
		UnsafeMutableField.run();
		// SynchronizedMutableField.run();
		// LockedMutableField.run();
		// GuardedBlocks.run();
	}
}
