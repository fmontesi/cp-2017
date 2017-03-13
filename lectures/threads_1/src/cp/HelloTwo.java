package cp;

/**
 * Starts two threads that print Hello on screen and then waits for them to terminate.
 * The order in which the Hello messages appear can change at each run,
 * depending on what the OS decides!
 * 
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class HelloTwo
{	
	public static void run()
	{
		Thread t1 = new Thread( () -> {
			System.out.println( "Hello from t1" );
		} );
		Thread t2 = new Thread( () -> {
			System.out.println( "Hello from t2" );
		} );
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch( InterruptedException e ) {}
		System.out.println( "Hello from main" );
	}
}
