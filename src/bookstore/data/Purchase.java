/**
 * 
 */
package bookstore.data;

/**
 * @author EltonChan
 *
 */
public class Purchase {
	private String purchaseId;
	private String customerId;
	private String customerFirstName;
	private String customerLastName;
	private String bookId;
	private String bookTitle;
	private double price;
	
	public static class Builder{
		private final String purchaseId;
		
		private String customerId;
		private String customerFirstName;
		private String customerLastName;
		private String bookId;
		private String bookTitle;
		private double price;
		
		public Builder(String purchaseId) {
			this.purchaseId = purchaseId;
		}
		
		public Builder setCustomerId(String customerId) {
			this.customerId = customerId;
			return this;
		}
		
		public Builder setCustomerFirstName(String customerFirstName) {
			this.customerFirstName = customerFirstName;
			return this;
		}
		
		public Builder setCustomerLastName(String customerLastName) {
			this.customerLastName = customerLastName;
			return this;
		}
		
		public Builder setBookId(String bookId) {
			this.bookId = bookId;
			return this;
		}
		
		public Builder setBookTitle(String bookTitle) {
			this.bookTitle = bookTitle;
			return this;
		}
		
		public Builder setPrice(double price) {
			this.price = price;
			return this;
		}
		
		public Purchase build() {
			return new Purchase(this);
		}
		
	}
	
	
	
	/**
	 * 
	 */
	public Purchase(Builder builder) {
		// TODO Auto-generated constructor stub
		purchaseId = builder.purchaseId;
		customerId = builder.customerId;
		bookId = builder.bookId;
		price = builder.price;
		customerFirstName = builder.customerFirstName;
		customerLastName = builder.customerLastName;
		bookTitle = builder.bookTitle;
	}
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	/**
	 * @return the purchaseId
	 */
	public String getPurchaseId() {
		return purchaseId;
	}
	/**
	 * @param purchaseId the purchaseId to set
	 */
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	/**
	 * @return the customerFirstName
	 */
	public String getCustomerFirstName() {
		return customerFirstName;
	}
	/**
	 * @param customerFirstName the customerFirstName to set
	 */
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}
	/**
	 * @return the customerLastName
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}
	/**
	 * @param customerLastName the customerLastName to set
	 */
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}
	/**
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}
	/**
	 * @param bookTitle the bookTitle to set
	 */
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	
	public String getCustomerName() {
		return customerFirstName+" "+customerLastName;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", customerId=" + customerId + ", customerFirstName="
				+ customerFirstName + ", customerLastName=" + customerLastName + ", bookId=" + bookId + ", bookTitle="
				+ bookTitle + ", price=" + price + "]";
	}
	
	

}
