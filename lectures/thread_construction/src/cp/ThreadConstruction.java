package cp;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadConstruction
{
	private static class MyThread extends Thread {
		public void run()
		{
			System.out.println( "Hello" );
		}
	}
	
	public interface RunnableExtended extends Runnable {
		public void myRun();
	}
	
	private static class MyRunnable implements Runnable {
		@Override
		public void run()
		{
			System.out.println( "Hello" );
		}
	}
	
	private static void myRunMethod()
	{
		System.out.println( "Hello" );
	}
	
	public static void main( String[] args )
	{
		// All equivalent!
		// Choice depends on code reuse and engineering considerations.
		
		new Thread( () -> {
			System.out.println( "Hello" );
		} ).start();
		new MyThread().start();
		new Thread( new MyRunnable() ).start();
		new Thread( new Runnable() {
			public void run() {
				System.out.println( "Hello" );
			}
		} );
		new Thread( ThreadConstruction::myRunMethod ).start();
	}
}
