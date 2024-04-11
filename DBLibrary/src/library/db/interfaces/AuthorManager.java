package library.db.interfaces;

import java.util.List;

import library.db.pojos.Author;
import library.db.pojos.Book;

public interface AuthorManager {

	public void addAuthor(Author a);
	public List<Book> getBooksByAuthor(int authorId);
	public List<Author> getAuthorByNameSurname(String name, String surname);
	public void changeAuthor(Author a);
	public Author getAuthor(int id);
}
