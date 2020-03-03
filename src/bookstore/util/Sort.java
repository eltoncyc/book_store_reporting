package bookstore.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bookstore.ApplicationException;
import bookstore.data.Book;
import bookstore.data.Customer;
import bookstore.data.Purchase;


public class Sort {
	
	private static final Logger LOG = LogManager.getLogger(Sort.class); 
	
	public static Map<String, Customer>sortByJoinedDate(Map<String, Customer> customers, boolean descending) throws ApplicationException{
		LOG.debug("Sorting by joined date.");
		if(customers.size() == 0) {
			throw new ApplicationException("Expect a list of at least one customer.");
		}
		
		Map<String, Customer> sortedCustomers = new LinkedHashMap<String, Customer>();
		
		List<Map.Entry<String, Customer>> list = new LinkedList<Map.Entry<String, Customer>>(customers.entrySet());
		
		if(descending) {
			Collections.sort(list, new CompareByJoinDate.CompareDescendingly());
		}else {
			Collections.sort(list, new CompareByJoinDate.CompareAscendingly());
		}
		for(Map.Entry<String, Customer> customer:list) {

			sortedCustomers.put(Long.toString(customer.getValue().getId()), customer.getValue());
		}
		
		return sortedCustomers;
	}
	
	
	public static Map<String, Book>sortByAuthor(Map<String, Book> books) throws ApplicationException{
		LOG.debug("Sorting by author");
		if(books.size() == 0) {
			throw new ApplicationException("Expect a list of at least one book.");
		}
		
		Map<String, Book> sortedBooks = new LinkedHashMap<String, Book>();
		
		List<Map.Entry<String, Book>> list = new LinkedList<Map.Entry<String, Book>>(books.entrySet());
		
		Collections.sort(list, new CompareByAuthor.CompareAscendingly());
		for(Map.Entry<String, Book> customer:list) {
			sortedBooks.put(customer.getValue().getBookId(), customer.getValue());
		}
		
		return sortedBooks;
	}
	
	public static List<Purchase> sortByLastName(List<Purchase> purchases, boolean descending) throws ApplicationException{
		LOG.debug("Sorting by last name");
		if(purchases.size() == 0) {
			throw new ApplicationException("Expect a list of purcahses.");
		}
		
		if(descending) {
			Collections.sort(purchases, new CompareByLastName.CompareDecendingly());
		}else {
			Collections.sort(purchases, new CompareByLastName.CompareAscendingly());
		}
		
		return purchases;
	}
	
	public static List<Purchase> sortByTitle(List<Purchase> purchases, boolean descending) throws ApplicationException{
		LOG.debug("Sorting by title");
		if(purchases.size() == 0) {
			throw new ApplicationException("Expect a list of purcahses.");
		}
		
		if(descending) {
			Collections.sort(purchases, new CompareByTitle.CompareDescendingly());
		}else {
			Collections.sort(purchases, new CompareByTitle.CompareAscendingly());
		}
		
		return purchases;
	} 
	
	
	
	
}
