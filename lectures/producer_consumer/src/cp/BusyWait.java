package cp;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class BusyWait
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
	private static final Deque< Product > THE_LIST = new LinkedList<>();
	
	private static void produce( Deque< Product > list, String threadName )
	{
		IntStream.range( 0, 1000 ).forEach(
			n -> {
				Product prod = new Product( "Water bottle " + n, threadName );
				synchronized( list ) {
					list.add( prod );
				}
				synchronized( list ) {
					list.add( new Product( "Flower bouquet " + n, threadName ) );
				}
			}
		);
	}
	
	private static final AtomicInteger counter = new AtomicInteger( 0 );
	
	private static void consume( Deque< Product > list, String threadName, CountDownLatch latch )
	{
//		IntStream.range( 0, 100000 ).forEach(
//			n -> {
//				synchronized( list ) {
//					if ( !list.isEmpty() ) {
//						Product prod = list.removeFirst();
//						System.out.println( prod );
//					}
//				}
//			}
//		);
		boolean keepRun = true;
		while( keepRun ) {
			synchronized( list ) {
				if ( !list.isEmpty() ) {
					Product prod = list.removeFirst();
					System.out.println( threadName + " consuming " + prod.toString() );
				} else if ( latch.getCount() == 0 ) {
					keepRun = false;
				} else {
					counter.incrementAndGet();
				}
			}
		}
	}
	
	private static final int NUM_PRODUCERS = 3;
	
	public static void run()
	{
//		Thread p1 = new Thread( () -> {
//			produce( THE_LIST, "P1" );
//		} );
//		
//		Thread p2 = new Thread( () -> {
//			produce( THE_LIST, "P2" );
//		} );
//		
//		Thread p3 = new Thread( () -> {
//			produce( THE_LIST, "P3" );
//		} );
//		
//		Thread p4 = new Thread( () -> {
//			produce( THE_LIST, "P4" );
//		} );
//		
//		Thread c1 = new Thread( () -> {
//			consume( THE_LIST, "C1", null );
//		} );
//		
//		Thread c2 = new Thread( () -> {
//			consume( THE_LIST, "C2", null );
//		} );
//				
//		p1.start();
//		p2.start();
//		p3.start();
//		p4.start();
//		c1.start();
//		c2.start();
		
		CountDownLatch latch = new CountDownLatch( NUM_PRODUCERS );
		CountDownLatch latch2 = new CountDownLatch( NUM_PRODUCERS );
		IntStream.range( 0, NUM_PRODUCERS ).forEach(
		i -> {
			new Thread( () -> {
				produce( THE_LIST, "Producer" + i );
				latch.countDown();
			} ).start();
			new Thread( () -> {
				consume( THE_LIST, "Consumer" + i, latch );
				latch2.countDown();
			} ).start();
		} );
		
		try {
			latch2.await();
		} catch( InterruptedException e ) {}
		
		System.out.println( "Wasted cycles: " + counter.get() );
		
		/*
		IntStream.range( 0, NUM_PRODUCERS ).forEach( i -> { ... } );
		
		does the same thing as
		
		for( int i = 0; i < NUM_PRODUCERS; i++ ) {
			...
		}
		*/
	}
}
