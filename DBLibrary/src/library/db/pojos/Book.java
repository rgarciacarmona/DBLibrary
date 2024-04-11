package library.db.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -155901317805078989L;

	private Integer isbn;
	private String title;
	private Date publicationDate;
	private Author author;
	private List<Borrower> borrowers;

	public Book() {
		super();
		this.borrowers = new ArrayList<Borrower>();
	}
	

	public Book(Integer isbn, String title, Date publicationDate) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.publicationDate = publicationDate;
		this.borrowers = new ArrayList<Borrower>();
	}

	public Book(Integer isbn, String title, Date publicationDate, Author author) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.publicationDate = publicationDate;
		this.author = author;
		this.borrowers = new ArrayList<Borrower>();
	}


	public Integer getIsbn() {
		return isbn;
	}

	public void setIsbn(Integer isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Date getPublicationDate() {

		return

		publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public List<Borrower> getBorrowers() {
		return borrowers;
	}

	public void setBorrowers(List<Borrower> borrower) {
		this.borrowers = borrower;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(isbn, other.isbn);
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", publicationDate=" + publicationDate + ", author=" + author
				+ "]";
	}

}
