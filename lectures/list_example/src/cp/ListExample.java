package cp;

/**
 * A really good bad example about lists.
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ListExample
{
	private static class Node <T> {
		private Node next = null;
		private final T data;
		
		public Node( T data )
		{
			this.data = data;
		}
		
		public T data()
		{
			return data;
		}
		
		public void setNext( Node next )
		{
			this.next = next;
		}
		
		public Node next()
		{
			return next;
		}
	}
	
	private static void listAllDoWhile( Node node )
	{
		
	}
	
	private static void listAllWhile( Node node )
	{
		
	}
	
	private static void listAllRecursive( Node node )
	{
		// Print data from all nodes
		if ( node != null ) {
			System.out.println( node.data() );
			listAllRecursive( node.next() );
		}
	}
	
	public static void main( String[] args )
	{
		Node<Integer> n1 = new Node<Integer>( 5 );
		Node n2 = new Node( 4 );
		Node n3 = new Node( 6 );
		
		Node<String> nS = new Node<String>( "Hello" );
		n1.setNext( n2 );
		n2.setNext( n3 );
		
		listAllRecursive( n1 );
	}
}
