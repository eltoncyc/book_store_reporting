/**
 * 
 */
package bookstore.util;

import java.util.Comparator;

import bookstore.data.Purchase;

/**
 * @author EltonChan
 *
 */
public class CompareByLastName {

	public static class CompareAscendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			// TODO Auto-generated method stub
			return o1.getCustomerLastName().compareTo(o2.getCustomerLastName());
		}
		
	}
	
	public static class CompareDecendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			// TODO Auto-generated method stub
			return o2.getCustomerLastName().compareTo(o1.getCustomerLastName());
		}
		
	}
}
