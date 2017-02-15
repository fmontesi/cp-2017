package cp;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Fun with lists!
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ListExample
{
	private static class NodeIterator<T> implements Iterator<T>
	{
		private Node<T> node;
		public NodeIterator( Node<T> node )
		{
			this.node = node;
		}

		public boolean hasNext()
		{
			return node.next() != null;
		}
		
		public T next()
		{
			node = node.next();
			return node.data();
		}			
	}
	
	private static class Node<T> implements Iterable<T> {
		private Node<T> next = null;
		private final T data;
		
		public Node( T data )
		{
			this.data = data;
		}
		
		public T data()
		{
			return data;
		}
		
		public void setNext( Node<T> next )
		{
			this.next = next;
		}
		
		public Node next()
		{
			return next;
		}
		
		public Iterator<T> iterator()
		{
			Node<T> fake = new Node<>( null );
			fake.setNext( this );
			return new NodeIterator<>( fake );
		}
	}
		
	private static void listAllDoWhile( Node node )
	{
		// DANGER! What if node is null?
		do {
			System.out.println( node.data() );
			node = node.next();
		} while( node != null );
	}
	
	private static void listAllWhile( Node<?> node )
	{
		while( node != null ) {
			System.out.println( node.data() );
			node = node.next();
		}
	}
	
	private static void listAllRecursive( Node<? extends Object> node )
	{
		// Print data from all nodes
		if ( node != null ) {
			System.out.println( node.data() );
			listAllRecursive( node.next() );
		}
	}
	
	private static void printAll( Iterator<?> it )
	{
		while( it.hasNext() ) {
			System.out.println( it.next() );
		}
	}
	
	private static class MyIntegerConsumer implements Consumer<Integer>
	{
		public void accept( Integer x )
		{
			System.out.println( x );
		}
	}
	
	private static void printInt( Integer x )
	{
		System.out.println( x );
	}
	
	public static void main( String[] args )
	{
		Node<Integer> n1 = new Node<>( 1 ); // Actually: Node<Integer> n1 = new Node<Integer>( 2 );
		Node<Integer> n2 = new Node<>( 2 );
		Node<Integer> n3 = new Node<>( 3 );
		
		n1.setNext( n2 );
		n2.setNext( n3 );
		
		for( Integer x : n1 ) {
			System.out.println( x );
		}
		
		Consumer<Integer> myConsumer = new MyIntegerConsumer();
		n1.iterator().forEachRemaining( myConsumer );
		
		n1.iterator().forEachRemaining( new Consumer<Integer>() {
			public void accept( Integer x ) {
				System.out.println( x );
			}
		} );
		
		n1.iterator().forEachRemaining(
			x -> { System.out.println( x ); }
		);
		
		n1.iterator().forEachRemaining(
			ListExample::printInt
		);
		
		n1.iterator().forEachRemaining(	System.out::println );
		
//		printAll( n1.iterator() );
		
//		listAllRecursive( n1 );
//		listAllWhile( n1 );
//		listAllDoWhile( n1 );
//		
//		LinkedList<Integer> list = new LinkedList<Integer>();
//		list.add( 1 );
//		list.add( 2 );
//		list.add( 3 );
//		ArrayList<Integer> alist = new ArrayList<Integer>();
//		alist.add( 1 );
//		alist.add( 2 );
//		alist.add( 3 );
//		HashSet<Integer> set = new HashSet<Integer>();
//		set.add( 1 );
//		set.add( 2 );
//		set.add( 3 );
//		
//		Iterator<Integer> it = set.iterator();
//		printAll( it );
//		
//		printAll( list.iterator() );
//		printAll( alist.iterator() );
	}
}
