/**
 * 
 */
package bookstore.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import bookstore.data.Book;

/**
 * @author EltonChan
 *
 */
public class CompareByAuthor {

	/**
	 * 
	 */
	public static class CompareAscendingly implements Comparator<Map.Entry<String, Book>> {
		
		@Override
		public int compare(Map.Entry<String, Book> b1, Map.Entry<String, Book> b2) {
			return b1.getValue().getAuthor().compareTo(b2.getValue().getAuthor());
		}
		
		
	}
	
	public static class CompareDescendingly implements Comparator<Map.Entry<String, Book>>{

		@Override
		public int compare(Entry<String, Book> b1, Entry<String, Book> b2) {
			return b2.getValue().getAuthor().compareTo(b1.getValue().getAuthor());
		}
		
	}
	
	
	
}
