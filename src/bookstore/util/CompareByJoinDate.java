/**
 * 
 */
package bookstore.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import bookstore.data.Customer;

/**
 * @author EltonChan
 *
 */
public class CompareByJoinDate {

	public static class CompareAscendingly implements Comparator<Map.Entry<String, Customer>>{

		@Override
		public int compare(Entry<String, Customer> o1, Entry<String, Customer> o2) {
			// TODO Auto-generated method stub
			return o1.getValue().getJoinedDate().compareTo(o2.getValue().getJoinedDate());
		}
		
	}
	
	public static class CompareDescendingly implements Comparator<Map.Entry<String, Customer>>{

		@Override
		public int compare(Entry<String, Customer> o1, Entry<String, Customer> o2) {
			// TODO Auto-generated method stub
			return o2.getValue().getJoinedDate().compareTo(o1.getValue().getJoinedDate());
		}
		
	}

}
