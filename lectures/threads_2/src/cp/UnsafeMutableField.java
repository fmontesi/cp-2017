package cp;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class UnsafeMutableField
{
	private static class Counter {
		public int i = 0;
	}
	
	public static void run()
	{
		Counter c = new Counter();
		
		Thread t1 = new Thread( () -> {
			int k = 0;
			while( k++ < 10000 ) {
				c.i++;
				// c.i = c.i + 1;
				// First, read c.i
				// For example, if c.i == 5, c.i = 5 + 1;
				// We solve the addition: c.i = 6;
				// Store the value in c.i: c.i is now 6
			}
		} );

		Thread t2 = new Thread( () -> {
			int j = 0;
			while( j++ < 10000 ) {
				c.i--;
				// c.i = c.i - 1;
				// First, read c.i
				// For example, if c.i == 5, c.i = 5 - 1;
				// We solve the subtraction: c.i = 4;
				// Store the value in c.i: c.i is now 4
			}
		} );
		
		try {
			t1.start();
			t2.start();
			t1.join();
			t2.join();
		} catch( InterruptedException e ) {}
		
		System.out.println( c.i );
	}
}
