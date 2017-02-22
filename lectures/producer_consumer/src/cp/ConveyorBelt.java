package cp;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.IntStream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ConveyorBelt
{
	private static class Product {
		private final String name;
		private final String attributes;
		public Product( String name, String attributes )
		{
			this.name = name;
			this.attributes = attributes;
		}
		
		public String toString()
		{
			return name + ". " + attributes;
		}
	}
	
	private static void produce( int prodNumber )
	{
		IntStream.range( 0, 100 ).forEach( i -> {
			Product prod = new Product( "Water", "Producer " + prodNumber + " : " + Integer.toString( i ) );
			BELT.add( prod );
			try {
				System.out.println( prod );
				BARRIER.await();
			} catch( InterruptedException | BrokenBarrierException e ) {}
		} );
	}
	
	private static final CyclicBarrier BARRIER = new CyclicBarrier( 4 );
	private static final BlockingDeque< Product > BELT = new LinkedBlockingDeque<>();
	
	public static void run()
	{
		/*
		Four Packagers consume products to create packages.
		There is a ConveyorBelt that stops, waiting for four packages to be put on it.
		The four packagers need to synchronise and put stuff on the belt at the same time.
		*/
		new Thread( () -> produce( 1 ) ).start();
		new Thread( () -> produce( 2 ) ).start();
		new Thread( () -> produce( 3 ) ).start();
		new Thread( () -> produce( 4 ) ).start();
	}
}
