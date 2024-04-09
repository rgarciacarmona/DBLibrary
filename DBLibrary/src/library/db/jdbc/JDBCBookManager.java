package library.db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import library.db.interfaces.BookManager;
import library.db.pojos.Author;
import library.db.pojos.Book;

public class JDBCBookManager implements BookManager {

	private Connection c;
	
	public JDBCBookManager() {
		this.connect();
		this.createTables();
	}
	
	private void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/library.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
		} catch (ClassNotFoundException cnfE) {
			System.out.println("Databases libraries not loaded");
			cnfE.printStackTrace();
		} catch (SQLException sqlE) {
			System.out.println("Error with database");
			sqlE.printStackTrace();
		}
	}
	
	public void close() {
		try {
			c.close();
		} catch (SQLException e) {
			System.out.println("Error closing the database");
			e.printStackTrace();
		}
	}
	
	private void createTables() {
		// TODO If the tables are already created, skip this step
		try {
			// Create the tables
			Statement createTables1 = c.createStatement();
			String create1 = "CREATE TABLE authors ( "
					+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			createTables1.executeUpdate(create1);
			createTables1.close();
			Statement createTables2 = c.createStatement();
			String create2 = "CREATE TABLE books ( "
					+ " isbn INTEGER PRIMARY KEY,"
					+ " title TEXT NOT NULL,"
					+ " publicationDate DATE NOT NULL,"
					+ " author_id INTEGER REFERENCES authors(id))";
			createTables2.executeUpdate(create2);
			createTables2.close();
			Statement createTables3 = c.createStatement();
			String create3 = "CREATE TABLE borrowers ( "
					+ " id INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " name TEXT NOT NULL,"
					+ " surname TEXT NOT NULL)";
			createTables3.executeUpdate(create3);
			createTables3.close();
			Statement createTables4 = c.createStatement();
			String create4 = "CREATE TABLE borrows ( "
					+ " book_id INTEGER REFERENCES books(id),"
					+ " borrower_id INTEGER REFERENCES borrowers(id),"
					+ " PRIMARY KEY (book_id, borrower_id))";
			createTables4.executeUpdate(create4);
			createTables4.close();
		} catch (SQLException sqlE) {
			System.out.println("Error in query");
			sqlE.printStackTrace();
		}
	}
	@Override
	public void addBook(Book b) {
		try {
			String query = "INSERT INTO books (isbn, title, publicationDate) " + "VALUES (?,?,?);";
			PreparedStatement insert = c.prepareStatement(query);
			insert.setInt(1, b.getIsbn());
			insert.setString(2, b.getTitle());
			insert.setDate(3, b.getPublicationDate());
			insert.executeUpdate();
			insert.close();
		} catch (SQLException sqlE) {
			System.out.println("Error in query");
			sqlE.printStackTrace();
		}
	}

	@Override
	public List<Book> searchBookByAuthor(Author a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> searchBookByTitle(String title) {
		List<Book> books = new ArrayList<Book>();
		try {
			String sql = "SELECT * FROM books WHERE title = ?";
			PreparedStatement search = c.prepareStatement(sql);
			ResultSet rs = search.executeQuery();
			while(rs.next()) {
				Integer isbn = rs.getInt("isbn");
				String bookTitle = rs.getString("title");
				Date pubDate = rs.getDate("publicationDate");
				Book newBook = new Book(isbn, bookTitle, pubDate);
				books.add(newBook);
			}
			return books;
		} catch (SQLException e) {
			System.out.println("Error looking for a book");
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public void deleteBook(Book b) {
		// TODO Auto-generated method stub

	}

}
