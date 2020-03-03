package bookstore.util;

import java.util.Comparator;

import bookstore.data.Purchase;

public class CompareByTitle {

	public static class CompareAscendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			return o1.getBookTitle().compareTo(o2.getBookTitle());
		}
		
	}
	
	public static class CompareDescendingly implements Comparator<Purchase>{

		@Override
		public int compare(Purchase o1, Purchase o2) {
			return o2.getBookTitle().compareTo(o1.getBookTitle());
		}
		
	}

}
