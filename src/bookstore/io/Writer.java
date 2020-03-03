/**
 * 
 */
package bookstore.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bookstore.ApplicationException;
import bookstore.data.Book;
import bookstore.data.Customer;
import bookstore.data.Purchase;
import bookstore.util.Common;

/**
 * @author EltonChan
 *
 */
public class Writer {
	
	private static final Logger LOG = LogManager.getLogger(Writer.class);
	
	/**
	 * 
	 */
	public Writer() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void writeCustomerReport(Map<String, Customer> customers) throws ApplicationException{
		LOG.debug("writeCustomerReport()");
		String HORIZONTAL_LINE = "-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
		String HEADER_FORMAT = "%3s. %-6s %-12s %-12s %-40s %-25s %-12s %-15s %-40s%s\n";
		String CUSTOMER_FORMAT = "%3d. %06d %-12s %-12s %-40s %-25s %-12s %-15s %-40s%s\n";
		
		PrintWriter outputStream = null;
		File reportFile = new File("customers_report.txt");
		
		try {
			outputStream = new PrintWriter(new FileWriter(reportFile));
			outputStream.println("Customers Report");
			outputStream.println(HORIZONTAL_LINE);
			outputStream.format(HEADER_FORMAT, "#.", "ID", "First Name", "Last Name", "Street", "City", "Postal Code", "Phone", "Email", "Join Date");
			outputStream.println(HORIZONTAL_LINE);
			
			int i = 0;
			for (Map.Entry<String, Customer> entry : customers.entrySet()) {
				Customer customer = entry.getValue();
				LocalDate date = customer.getJoinedDate();
				outputStream.format(CUSTOMER_FORMAT, ++i, customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getStreet(),
						customer.getCity(), customer.getPostalCode(), customer.getPhone(), customer.getEmailAddress(), Common.DATE_FORMAT.format(date));

			}
			
		}catch(IOException e) {
			throw new ApplicationException(e.getMessage());
		}finally {
			outputStream.close();
		}
		
		LOG.info("Created a customers report and saved it as 'customers_report.txt'.");
		
		
	}
	
	public static void writeBookReport(Map<String, Book> books) throws ApplicationException{
		LOG.debug("writeBookReport()");
		String HORIZONTAL_LINE = "----------------------------------------------------------------------------------------------------------------------------------------------";
		String HEADER_FORMAT = "%8s %-12s %-40s %-80s %4s %6s %13s %-60s\n";
		String BOOK_FORMAT = "%8s %-12s %-40s %-80s %4d %6.3f %13d %-60s\n";
		
		PrintWriter outputStream = null;
		File reportFile = new File("books_report.txt");
		
		try {
			outputStream = new PrintWriter(new FileWriter(reportFile));
			outputStream.println("Books Report");
			outputStream.println(HORIZONTAL_LINE);
			outputStream.format(HEADER_FORMAT, "ID", "ISBN", "Authors", "Title", "Year", "Rating", "Ratings Count", "Image URL");
			outputStream.println(HORIZONTAL_LINE);
			

			for (Map.Entry<String, Book> entry : books.entrySet()) {
				Book book = entry.getValue();
				outputStream.format(BOOK_FORMAT, book.getBookId(), book.getIsbn(), book.getAuthor(),
						book.getOriginalTitle(), book.getOriginalPublicationYear(), book.getAverageRating(),
						book.getRatingsCount(), book.getImageURL());

			}
			
		}catch(IOException e) {
			LOG.error(e.getMessage());
			throw new ApplicationException(e.getMessage());
		}finally {
			outputStream.close();
		}
		
		LOG.info("Created a books report and saved it as 'books_report.txt'.");
		
		
	}
	
	public static void writePurchaseReport(List<Purchase> listOfPurchases, boolean total) throws ApplicationException{
		LOG.debug("writePurchaseReport()");
		String HORIZONTAL_LINE = "----------------------------------------------------------------------------------------------------------------------------------------------";
		String HEADER_FORMAT = "%-24s %-80s %6s\n";
		String PURCHASE_FORMAT = "%-24s %-80s $%.2f\n";
		
		PrintWriter outputStream = null;
		File reportFile = new File("purchases_report.txt");
		
		try {
			outputStream = new PrintWriter(new FileWriter(reportFile));
			outputStream.println("Purchases Report");
			outputStream.println(HORIZONTAL_LINE);
			outputStream.format(HEADER_FORMAT, "Name", "Title", "Price");
			outputStream.println(HORIZONTAL_LINE);
			
			double totalPrice = 0;

			for (Purchase purchase:listOfPurchases) {
				
				outputStream.format(PURCHASE_FORMAT, purchase.getCustomerName(), purchase.getBookTitle(), purchase.getPrice());
				totalPrice += purchase.getPrice();
			}
			
			if(total) {
				outputStream.format("%105s $%,.2f", "Total Purchase Dollar Amount:", totalPrice);
			}
			
			
		}catch(IOException e) {
			LOG.error(e.getMessage());
			throw new ApplicationException(e.getMessage());
		}finally {
			outputStream.close();
		}
		
		LOG.info("Created a purchases report and saved it as 'purchases_report.txt'.");
	}
	

}
