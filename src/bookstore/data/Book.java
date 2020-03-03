/**
 * 
 */
package bookstore.data;

/**
 * @author EltonChan
 *
 */
public class Book {
	
	private String bookId;
	private String isbn;
	private String author;
	private int originalPublicationYear;
	private String originalTitle;
	private double averageRating;
	private int ratingsCount;
	private String imageURL;
	
	public static class Builder{
		// Required
		private final String bookId;
		private final String isbn;
		//Optional
		private String author;
		private int originalPublicationYear;
		private String originalTitle;
		private double averageRating;
		private int ratingsCount;
		private String imageURL;
		
		public Builder(String bookId, String isbn) {
			this.bookId = bookId;
			this.isbn = isbn;
		}
		
		/**
		 * 
		 * @param author
		 * @return
		 */
		public Builder setAuthor(String author) {
			this.author = author;
			return this;
		}
		
		/**
		 * 
		 * @param year
		 * @return
		 */
		public Builder setOriginalPublicationYear(int year) {
			this.originalPublicationYear = year;
			return this;
		}
		
		/**
		 * 
		 * @param title
		 * @return
		 */
		public Builder setOriginalTitle(String title) {
			this.originalTitle = title;
			return this;
		}
		
		/**
		 * 
		 * @param rating
		 * @return
		 */
		public Builder setAverageRating(double rating) {
			this.averageRating = rating;
			return this;
		}
		
		/**
		 * 
		 * @param count
		 * @return
		 */
		public Builder setRatingsCount(int count) {
			this.ratingsCount = count;
			return this;
		}
		
		/**
		 * 
		 * @param url
		 * @return
		 */
		public Builder setImageURL(String url) {
			this.imageURL = url;
			return this;
		}
		
		/**
		 * 
		 * @return
		 */
		public Book build() {
			return new Book(this);
		}
		
	}
	
	public Book(Builder builder) {
		bookId = builder.bookId;
		isbn = builder.isbn;
		author = builder.author;
		originalPublicationYear = builder.originalPublicationYear;
		originalTitle = builder.originalTitle;
		averageRating = builder.averageRating;
		ratingsCount = builder.ratingsCount;
		imageURL = builder.imageURL;
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
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @param isbn the isbn to set
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the originalPublicationYear
	 */
	public int getOriginalPublicationYear() {
		return originalPublicationYear;
	}

	/**
	 * @param originalPublicationYear the originalPublicationYear to set
	 */
	public void setOriginalPublicationYear(int originalPublicationYear) {
		this.originalPublicationYear = originalPublicationYear;
	}

	/**
	 * @return the originalTitle
	 */
	public String getOriginalTitle() {
		return originalTitle;
	}

	/**
	 * @param originalTitle the originalTitle to set
	 */
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	/**
	 * @return the averageRating
	 */
	public double getAverageRating() {
		return averageRating;
	}

	/**
	 * @param averageRating the averageRating to set
	 */
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	/**
	 * @return the ratingsCount
	 */
	public int getRatingsCount() {
		return ratingsCount;
	}

	/**
	 * @param ratingsCount the ratingsCount to set
	 */
	public void setRatingsCount(int ratingsCount) {
		this.ratingsCount = ratingsCount;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", isbn=" + isbn + ", author=" + author + ", originalPublicationYear="
				+ originalPublicationYear + ", originalTitle=" + originalTitle + ", averageRating=" + averageRating
				+ ", ratingsCount=" + ratingsCount + ", imageURL=" + imageURL + "]";
	}
	
	

}
