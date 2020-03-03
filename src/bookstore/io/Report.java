/**
 * 
 */
package bookstore.io;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bookstore.ApplicationException;
import bookstore.data.Book;
import bookstore.data.Customer;
import bookstore.data.Purchase;
import bookstore.util.Common;
import bookstore.util.Sort;

/**
 * @author EltonChan
 *
 */
public class Report {
	
	private static final Logger LOG = LogManager.getLogger(Report.class);
	
	/**
	 * 
	 */
	public Report() {
		// TODO Auto-generated constructor stub
	}
	
	public void reportCustomers(Map<String, Customer> customers, boolean byJoinDate, boolean descending) throws ApplicationException{
		LOG.debug("reportCustomers()");
		String HORIZONTAL_LINE = "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
		String HEADER_FORMAT = "%3s. %-6s %-12s %-12s %-40s %-25s %-12s %-15s %-40s%s\n";
		String CUSTOMER_FORMAT = "%3d. %06d %-12s %-12s %-40s %-25s %-12s %-15s %-40s%s\n";
		
		
		if(byJoinDate) {
			customers= Sort.sortByJoinedDate(customers, descending);
			LOG.info("Sorted the customers by join date.");
		}
		
		System.out.println("Customers Report");
		System.out.println(HORIZONTAL_LINE);
		System.out.print(String.format(HEADER_FORMAT, "#.", "ID", "First Name", "Last Name", "Street", "City", "Postal Code", "Phone", "Email", "Join Date"));
		System.out.println(HORIZONTAL_LINE);
		
		int i = 0;
		for (Map.Entry<String, Customer> entry : customers.entrySet()) {
			Customer customer = entry.getValue();
			LocalDate date = customer.getJoinedDate();
			System.out.print(String.format(CUSTOMER_FORMAT, ++i, customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getStreet(),
					customer.getCity(), customer.getPostalCode(), customer.getPhone(), customer.getEmailAddress(), Common.DATE_FORMAT.format(date)));
		}

		
		
		Writer.writeCustomerReport(customers);
		
	}
	
	public void reportBooks(Map<String, Book> books, boolean byAuthor ) throws ApplicationException{
		LOG.debug("reportBooks()");
		String HORIZONTAL_LINE = "----------------------------------------------------------------------------------------------------------------------------------------------";
		String HEADER_FORMAT = "%8s %-12s %-40s %-80s %4s %6s %13s %-60s\n";
		String BOOK_FORMAT = "%8s %-12s %-40s %-80s %4d %6.3f %13d %-60s\n";
	
		if(byAuthor) {
			books = Sort.sortByAuthor(books);
			LOG.info("Sorted the books by author.");
		}
		
		System.out.println("Books Report");
		System.out.println(HORIZONTAL_LINE);
		System.out.print(String.format(HEADER_FORMAT, "ID", "ISBN", "Authors", "Title", "Year", "Rating", "Ratings Count", "Image URL"));
		System.out.println(HORIZONTAL_LINE);
		

		for (Map.Entry<String, Book> entry : books.entrySet()) {
			Book book = entry.getValue();
			System.out.print(String.format(BOOK_FORMAT, book.getBookId(), book.getIsbn(), book.getAuthor(),
					book.getOriginalTitle(), book.getOriginalPublicationYear(), book.getAverageRating(),
					book.getRatingsCount(), book.getImageURL()));
		}
		
		
		Writer.writeBookReport(books);
	}
	
	
	public void reportPurchases(List<Purchase> listOfPurchases, boolean total,boolean byLastName, boolean byTitle, String customerId, boolean descending) throws ApplicationException{
		LOG.debug("reportPurchases()");
		String HORIZONTAL_LINE = "----------------------------------------------------------------------------------------------------------------------------------------------";
		String HEADER_FORMAT = "%-24s %-80s %6s\n";
		String PURCHASE_FORMAT = "%-24s %-80s $%.2f\n";
		
		List<Purchase> purchases = new ArrayList<Purchase>();
		
		if(customerId != null && customerId != "") {
			for(Purchase purchase:listOfPurchases) {
				if(purchase.getCustomerId().equals(customerId)) {
					purchases.add(purchase);
				}
			}
		}else {
			purchases = listOfPurchases;
		}
		
		if(byLastName) {
			purchases = Sort.sortByLastName(purchases, descending);
			LOG.info("Sorted the purchases by customers' last name.");
		}
		
		if(byTitle) {
			purchases = Sort.sortByTitle(purchases, descending);
			LOG.info("Sorted the purchases by books' title");
		}
		
		System.out.println("Purchases Report");
		System.out.println(HORIZONTAL_LINE);
		System.out.print(String.format(HEADER_FORMAT, "Name", "Title", "Price"));
		System.out.println(HORIZONTAL_LINE);
		
		double totalPrice = 0;

		for (Purchase purchase:purchases) {
			
			System.out.print(String.format(PURCHASE_FORMAT, purchase.getCustomerName(), purchase.getBookTitle(), purchase.getPrice()));
			totalPrice += purchase.getPrice();
		}
		
		if(total) {
			System.out.println(String.format("%105s $%,.2f", "Total Purchase Dollar Amount:", totalPrice));
		}
		
		
		
		Writer.writePurchaseReport(purchases, total);
		
	}
}
