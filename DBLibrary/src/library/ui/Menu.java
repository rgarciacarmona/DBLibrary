package library.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import library.db.interfaces.*;
import library.db.jdbc.ConnectionManager;
import library.db.jdbc.JDBCAuthorManager;
import library.db.jdbc.JDBCBookManager;
import library.db.jdbc.JDBCBorrowerManager;
import library.db.pojos.*;

public class Menu {

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private static BookManager bookMan;
	private static AuthorManager authorMan;
	private static BorrowerManager borrowMan;

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("Welcome to the library!");
		// Manager setup
		ConnectionManager conMan = new ConnectionManager();
		bookMan = conMan.getBookMan();
		authorMan = conMan.getAuthorMan();
		borrowMan = conMan.getBorrowMan();
		// TODO delete book (delete by id)
		// TODO change author (change by example)
		// TODO borrow book (n-to-n insertion)
		// TODO return book (n-to-n removal)
		// TODO Go back to the menu after executing an option
		// Instead of quitting
		System.out.println("Choose your desired option");
		System.out.println("1. Add a new book");
		System.out.println("2. Search a book by its title");
		System.out.println("3. Add a new author");
		System.out.println("4. Add a new borrower");
		System.out.println("5. Modify an author");
		System.out.println("0. Exit");
		int choice = Integer.parseInt(r.readLine());
		switch (choice) {
		case 1: {
			addBook();
			break;
		}
		case 2: {
			searchBooksByTitle();
			break;
		}
		case 3: {
			addAuthor();
			break;
		}
		case 4: {
			addBorrower();
			break;
		}
		case 5: {
			modifyAuthor();
			break;
		}
		case 0: {
			conMan.close();
			return;
		}
		}
	}

	// Get the book data from the user, create a book object, and call the addBook
	// method of the DB manager
	private static void addBook() throws NumberFormatException, IOException {
		System.out.println("Please, write the book info:");
		System.out.println("ISBN (without dashes):");
		Integer isbn = Integer.parseInt(r.readLine());
		System.out.println("Title:");
		String title = r.readLine();
		System.out.println("Publication date (DD-MM-YYYY format):");
		LocalDate localDate = LocalDate.parse(r.readLine(), formatter);
		Date date = Date.valueOf(localDate);
		// Show and add authors
		listAuthors();
		Integer authorId = Integer.parseInt(r.readLine());
		Author author = authorMan.getAuthor(authorId);
		Book book = new Book(isbn, title, date, author);
		bookMan.addBook(book);
	}

	private static void listAuthors() throws IOException {
		System.out.println("Author name (press enter to search all): ");
		String name = r.readLine();
		System.out.println("Author surname (press enter to search all): ");
		String surname = r.readLine();
		System.out.println("These are the available authors, choose one by typing their id:");
		List<Author> authors = authorMan.getAuthorByNameSurname(name, surname);
		System.out.println(authors);
	}

	private static void searchBooksByTitle() throws IOException {
		System.out.println("Please, type the book title");
		String title = r.readLine();
		List<Book> books = bookMan.searchBookByTitle(title);
		for (Book book : books) {
			System.out.println(book);
		}
	}

	private static void addAuthor() throws NumberFormatException, IOException {
		System.out.println("Please, write the author info:");
		System.out.println("Name:");
		String name = r.readLine();
		System.out.println("Surname:");
		String surname = r.readLine();
		Author author = new Author(name, surname);
		authorMan.addAuthor(author);
	}

	private static void addBorrower() throws NumberFormatException, IOException {
		System.out.println("Please, write the borrower info:");
		System.out.println("Name:");
		String name = r.readLine();
		System.out.println("Surname:");
		String surname = r.readLine();
		Borrower b = new Borrower(name, surname);
		borrowMan.addBorrower(b);
	}

	private static void modifyAuthor() throws NumberFormatException, IOException {
		// Search for an author to be modified
		// User selects the author to be modified
		Author a = null;
		System.out.println("Here are the actual author's values");
		System.out.println("Press enter to keep them or type a new value.");
		System.out.println("Name (" + a.getName() + "): ");
		String newName = r.readLine();
		System.out.println("Surname (" + a.getSurname() + "): ");
		String newSurname = r.readLine();
		if (!newName.equals("")) {
			// If I don't keep
			a.setName(newName);
		}
		if (newSurname.equals("")) { // If I keep
		}
		else { // If I don't keep
			a.setSurname(newSurname);
		}
		authorMan.changeAuthor(a);
	}

}
