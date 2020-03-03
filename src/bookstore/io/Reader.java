/**
 * 
 */
package bookstore.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bookstore.ApplicationException;
import bookstore.data.Book;
import bookstore.data.Customer;
import bookstore.data.Purchase;
import bookstore.util.Validator;
/**
 * @author EltonChan
 *
 */
public class Reader {
	public static final String RECORD_DELIMITER = ":";
	public static final String FIELD_DELIMITER = "\\|";
	private static final Logger LOG = LogManager.getLogger();
	
	/**
	 * 
	 */
	public Reader() {
		// TODO Auto-generated constructor stub
	}
	
	public static class BookReader{
		/**
		 * 
		 * @return
		 * @throws ApplicationException
		 */
		public static Map<String, Book> readBookFile(File file) throws ApplicationException{
//			File file = new File("books500.csv");
			FileReader in;
			Iterable<CSVRecord>records;
			try {
				in = new FileReader(file);
				records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
				
			}catch(IOException e) {
				LOG.error(e.getMessage());
				throw new ApplicationException(e);
			}
			
			Map<String, Book>books = new HashMap<>();
			LOG.debug("Reading"+file.getAbsolutePath());
			for(CSVRecord record:records) {
				String id = record.get("book_id");
				String isbn = record.get("isbn");
				String author = record.get("authors");
				String publicationYearStr = record.get("original_publication_year");
				int publicationYear = Integer.valueOf(publicationYearStr);
				String title = record.get("original_title");
				String ratingStr = record.get("average_rating");
				double rating = Double.valueOf(ratingStr);
				String ratingsCountStr = record.get("ratings_count");
				int ratingsCount = Integer.valueOf(ratingsCountStr);
				String imageURL = record.get("image_url");
				Book theBook 
				= new Book.Builder(id, isbn).setAuthor(author).setOriginalPublicationYear(publicationYear)
				.setOriginalTitle(title).setAverageRating(rating).setRatingsCount(ratingsCount).setImageURL(imageURL).build();
				
				books.put(id,theBook);
			}
			LOG.info("Collected books data.");
			return books;
		}
	}
	
	public static class PurchaseReader{
		/**
		 * 
		 * @return
		 * @throws ApplicationException
		 */
		public static List<Purchase> readPurchaseFile(File file, Map<String, Customer> customers, Map<String, Book> books) throws ApplicationException{
//			File file = new File("purchases.csv");
			FileReader in;
			Iterable<CSVRecord>records;
			try {
				in = new FileReader(file);
				records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
				
			}catch(IOException e) {
				LOG.error(e.getMessage());
				throw new ApplicationException(e);
			}
			
			List<Purchase> purchases = new ArrayList<>();
			LOG.debug("Reading"+file.getAbsolutePath());
			for(CSVRecord record:records) {
				String purchaseId = record.get("id");
				String customerId = record.get("customer_id");
				String bookId = record.get("book_id");
				String priceStr = record.get("price");
				double price = Double.valueOf(priceStr);
				
				Customer theCustomer = getCustomer(customerId, customers);
				Book theBook = getBook(bookId, books);
				
				Purchase thePurchase = 
						new Purchase.Builder(purchaseId).setCustomerId(customerId)
						.setCustomerFirstName(theCustomer.getFirstName()).setCustomerLastName(theCustomer.getLastName())
						.setBookId(bookId).setBookTitle(theBook.getOriginalTitle())
						.setPrice(price).build();
				
				purchases.add(thePurchase);
			}
			LOG.info("Collected purchases data.");
			return purchases;
		}
		
		/**
		 * 
		 * @param customerId
		 * @return
		 * @throws ApplicationException
		 */
		private static Customer getCustomer(String customerId, Map<String, Customer> listOfCustomers) throws ApplicationException {
			
//			Map<String, Customer> listOfCustomers = CustomerReader.readCustomerFile();
			if(listOfCustomers.containsKey(customerId)){
				return listOfCustomers.get(customerId);
			}
			return new Customer.Builder(Long.parseLong(customerId), "").build();
				
		}
		
		/**
		 * 
		 * @param bookId
		 * @return
		 * @throws ApplicationException
		 */
		private static Book getBook(String bookId, Map<String, Book>listOfBooks ) throws ApplicationException{
//			Map<String, Book> listOfBooks = BookReader.readBookFile();
			if(listOfBooks.containsKey(bookId)) {
				return listOfBooks.get(bookId);
			}
			return new Book.Builder(bookId, "").build();
		}
		
		
	}
	
	public static class CustomerReader{
		
		/**
		 * Read the customer input data.
		 * 
		 * @param data
		 *            The input data.
		 * @return A list of customers.
		 * @throws ApplicationException
		 */
		public static Map<String, Customer>readCustomerFile(File customerDataFile) throws ApplicationException {
//			File customerDataFile = new File("customers.dat");
			BufferedReader customerReader = null;
//			List<Customer> customers = new ArrayList<>();
			Map<String, Customer> customers = new HashMap<>();
			LOG.debug("Reading" + customerDataFile.getAbsolutePath());
			try {
				customerReader = new BufferedReader(new FileReader(customerDataFile));

				String line = null;
				line = customerReader.readLine(); // skip the header line
				while ((line = customerReader.readLine()) != null) {
					Customer customer = readCustomerString(line);
//					customers.add(customer);
					customers.put(Long.toString(customer.getId()), customer);
//					LOG.debug("Added " + customer.toString() + " as " + customer.getId());
				}
			} catch (IOException e) {
				LOG.error(e.getMessage());
				throw new ApplicationException(e.getMessage());
			} finally {
				try {
					if (customerReader != null) {
						customerReader.close();
					}
				} catch (IOException e) {
					LOG.error(e.getMessage());
					throw new ApplicationException(e.getMessage());
				}
			}
			LOG.info("Collected customers data.");
			return customers;
		}

		/**
		 * Parse a Customer data string into a Customer object;
		 * 
		 * @param row
		 * @throws ApplicationException
		 */
		private static Customer readCustomerString(String data) throws ApplicationException {
			String[] elements = data.split(FIELD_DELIMITER);
			if (elements.length != Customer.ATTRIBUTE_COUNT) {
				throw new ApplicationException(
						String.format("Expected %d but got %d: %s", Customer.ATTRIBUTE_COUNT, elements.length, Arrays.toString(elements)));
			}

			int index = 0;
			long id = Integer.parseInt(elements[index++]);
			String firstName = elements[index++];
			String lastName = elements[index++];
			String street = elements[index++];
			String city = elements[index++];
			String postalCode = elements[index++];
			String phone = elements[index++];
			// should the email validation be performed here or in the customer class? I'm leaning towards putting it here.
			String emailAddress = elements[index++];
			if (!Validator.validateEmail(emailAddress)) {
				throw new ApplicationException(String.format("Invalid email: %s", emailAddress));
			}
			String yyyymmdd = elements[index];
			if (!Validator.validateJoinedDate(yyyymmdd)) {
				throw new ApplicationException(String.format("Invalid joined date: %s for customer %d", yyyymmdd, id));
			}
			int year = Integer.parseInt(yyyymmdd.substring(0, 4));
			int month = Integer.parseInt(yyyymmdd.substring(4, 6));
			int day = Integer.parseInt(yyyymmdd.substring(6, 8));

			return new Customer.Builder(id, phone).setFirstName(firstName).setLastName(lastName).setStreet(street).setCity(city).setPostalCode(postalCode)
					.setEmailAddress(emailAddress).setJoinedDate(year, month, day).build();
		}
		
	}
	
	

}
