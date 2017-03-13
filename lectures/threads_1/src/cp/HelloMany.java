package cp;

import java.util.ArrayList;
import java.util.List;

/**
 * Uses many threads to access a shared string builder.
 * The resulting string may contain garbage!
 * Why?
 * 
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class HelloMany
{
	// This is not thread-safe (if you remove synchronized)!
	public static void run()
	{
		final StringBuilder builder = new StringBuilder();
		List<Thread> list = new ArrayList<>();
		for( int i = 0; i < 10000; i++ ) {
			final int k = i;
			list.add( new Thread( () -> {
				synchronized( builder ) {
					builder.append( "Hello from t" + k + "\n" );
				}
			} ) );
		}
		for( Thread t : list ) {
			t.start();
		}
		try {
			for( Thread t : list ) {
				t.join();
			}
		} catch( InterruptedException e ) {}
		System.out.println( builder.toString() );
	}
}
