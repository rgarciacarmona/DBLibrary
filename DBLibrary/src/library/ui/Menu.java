package library.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import library.db.interfaces.*;
import library.db.jdbc.JDBCBookManager;
import library.db.pojos.Book;

public class Menu {

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	private static BookManager bookMan;

	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("Welcome to the library!");
		bookMan = new JDBCBookManager();
		System.out.println("Choose your desired option");
		System.out.println("1. Add a new book");
		System.out.println("2. Search a book by its title");
		System.out.println("0. Exit");
		int choice = Integer.parseInt(r.readLine());
		switch (choice) {
		case 1: {
			addBook();
			break;
		}
		case 2: {
			// TODO Search for existing books by title
			break;
		}
		case 0: {
			return;
		}
		}
	}

	private static void addBook() throws NumberFormatException, IOException {
		System.out.println("Please, write the book info:");
		System.out.println("ISBN (without dashes):");
		Integer isbn = Integer.parseInt(r.readLine());
		System.out.println("Title:");
		String title = r.readLine();
		System.out.println("Publication date (DD-MM-YYYY format):");
		LocalDate localDate = LocalDate.parse(r.readLine(), formatter);
		Date date = Date.valueOf(localDate);
		Book book = new Book(isbn, title, date);
		bookMan.addBook(book);
	}

}
