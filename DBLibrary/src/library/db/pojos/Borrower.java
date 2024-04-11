package library.db.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Borrower implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2219428108887054952L;
	private Integer id;
	private String name;
	private String surname;
	private List<Book> books;
	public Borrower() {
		super();
		this.books = new ArrayList<Book>();
	}
	
	
	public Borrower(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
		this.books = new ArrayList<Book>();
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrower other = (Borrower) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "Borrower [id=" + id + ", name=" + name + ", surname=" + surname + ", books=" + books + "]";
	}
	
	
}
