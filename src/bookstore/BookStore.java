package bookstore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import bookstore.data.Book;
import bookstore.data.Customer;
import bookstore.data.Purchase;
import bookstore.io.Reader;
import bookstore.io.Report;


/**
 * @author EltonChan
 *
 */
public class BookStore {

	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static final String BOOKS_DATA_FILENAME = "books500.csv";
	private static final String PERSONS_DATA_FILENAME = "customers.dat";
	private static final String PURCHASES_DATA_FILENAME = "purchases.csv";
	private Map<String, Customer> customers;
	private Map<String, Book> books;
	private List<Purchase> purchases;
	
	static {
		configureLogging();
	}
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Bcmc Constructor. Processes the commandline arguments
	 * ex. -inventory -make=honda -by_count -desc -total -service
	 * 
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	public BookStore(String[] args) throws ApplicationException, ParseException {

		LOG.info("Created Bcmc");
		
		BookOptions.process(args);
		
	}
	
	/**
	 * @param customers the customers to set
	 */
	public void setCustomers(Map<String, Customer> customers) {
		this.customers = customers;
	}

	/**
	 * @param books the books to set
	 */
	public void setBooks(Map<String, Book> books) {
		this.books = books;
	}

	/**
	 * @param purchases the purchases to set
	 */
	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	/**
	 * Entry point to GIS
	 * 
	 * @param args
	 * @throws ApplicationException 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws ApplicationException  {
		Instant startTime = Instant.now();
		LOG.info(startTime);
		
		File bookFile = new File(BOOKS_DATA_FILENAME);
		File customerFile = new File(PERSONS_DATA_FILENAME);
		File purchaseFile = new File(PURCHASES_DATA_FILENAME);
		
		if(!bookFile.exists() || !customerFile.exists() || !purchaseFile.exists()) {
			System.out.print("Required file(s) missing.");
			System.exit(-1);
		}
		
		Map<String, Customer> customers = Reader.CustomerReader.readCustomerFile(customerFile);
		Map<String, Book> books = Reader.BookReader.readBookFile(bookFile);
		List<Purchase> purchases = Reader.PurchaseReader.readPurchaseFile(purchaseFile, customers, books);
		
		
		// start the Book System
		try {
			BookStore book = new BookStore(args);
			if (BookOptions.isHelpOptionSet()) {
				BookOptions.Value[] values = BookOptions.Value.values();
				System.out.format("%-5s %-15s %-10s %s%n", "Option", "Long Option", "Has Value", "Description");
				for (BookOptions.Value value : values) {
					System.out.format("-%-5s %-15s %-10s %s%n", value.getOption(), ("-" + value.getLongOption()), value.isHasArg(),
							value.getDescription());
				}

				return;
			}
			book.setBooks(books);
			book.setCustomers(customers);
			book.setPurchases(purchases);
			book.run();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.debug(e.getMessage());
		}

		Instant endTime = Instant.now();
		LOG.info(endTime);
		LOG.info(String.format("Duration: %d ms", Duration.between(startTime, endTime).toMillis()));
	}

	/**
	 * Configures log4j2 from the external configuration file specified in LOG4J_CONFIG_FILENAME.
	 * If the configuration file isn't found then log4j2's DefaultConfiguration is used.
	 */
	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.out.println(String.format("WARNING! Can't find the log4j logging configuration file %s; using DefaultConfiguration for logging.",
					LOG4J_CONFIG_FILENAME));
			Configurator.initialize(new DefaultConfiguration());
		}
	}

	/**
	 * @throws ApplicationException
	 * @throws FileNotFoundException
	 * 
	 */
	private void run() throws ApplicationException, FileNotFoundException {
		LOG.debug("run()");
		generateReports();
	}

	/**
	 * Generate the reports from the input data
	 * 
	 * @throws FileNotFoundException
	 * @throws ApplicationException 
	 */
	private void generateReports() throws FileNotFoundException, ApplicationException {
		LOG.debug("generating the reports");
		

		if (BookOptions.isCustomersOptionSet()) {
			LOG.debug("generating the customer report");
			// for program args: -c -J -d
			System.out.println("Cutomer Report: " + BookOptions.isCustomersOptionSet());
			System.out.println("Cutomer Join Date: " + BookOptions.isByJoinDateOptionSet());
			System.out.println("Cutomer Join Date DESC: " + BookOptions.isDescendingOptionSet());
			new Report().reportCustomers(customers, BookOptions.isByJoinDateOptionSet(), BookOptions.isDescendingOptionSet());
			
			
		}

		if (BookOptions.isBooksOptionSet()) {
			LOG.debug("generating the book report");
			System.out.println("Books Report: " + BookOptions.isBooksOptionSet());
			System.out.println("Books Author: " + BookOptions.isByAuthorOptionSet());
			new Report().reportBooks(books, BookOptions.isByAuthorOptionSet());
			
		}

		if (BookOptions.isPurchasesOptionSet()) {
			LOG.debug("generating the purchase report");
			System.out.println("Purchases Report: "+ BookOptions.isPurchasesOptionSet());
			System.out.println("Purchases Total: "+ BookOptions.isTotalOptionSet());
			System.out.println("Purchase Customer Last Name: " +BookOptions.isByLastnameOptionSet());
			System.out.println("Purchase Book Title: "+BookOptions.isByTitleOptionSet());
			System.out.println("Purchase Sort DESC: " + BookOptions.isDescendingOptionSet());
			System.out.println("Purcahses Customer Filter: "+BookOptions.getCustomerId());
			
			new Report().reportPurchases(purchases, 
					BookOptions.isTotalOptionSet(), 
					BookOptions.isByLastnameOptionSet(), 
					BookOptions.isByTitleOptionSet(), 
					BookOptions.getCustomerId(), 
					BookOptions.isDescendingOptionSet());
			
			
			
		}
	}


}
