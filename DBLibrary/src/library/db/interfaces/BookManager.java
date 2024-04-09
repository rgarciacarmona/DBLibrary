package library.db.interfaces;

import java.util.List;

import library.db.pojos.*;

public interface BookManager {

	public void addBook(Book b);
	public List<Book> searchBookByAuthor(Author a);
	public List<Book> searchBookByTitle(String title);
	public void deleteBook(Book b);
	
}
