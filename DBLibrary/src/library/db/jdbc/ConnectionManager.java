package library.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import library.db.interfaces.*;

public class ConnectionManager {

	private Connection c;
	private BookManager bookMan;
	private AuthorManager authorMan;
	private BorrowerManager borrowMan;
	// Design pattern Singleton

	public Connection getConnection() {
		return c;
	}
	
	public ConnectionManager() {
		this.connect();
		this.bookMan = new JDBCBookManager(this);
		this.authorMan = new JDBCAuthorManager(this);
		this.borrowMan = new JDBCBorrowerManager(this);
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
			if (sqlE.getMessage().contains("already exist")){
				System.out.println("No need to create the tables; already there");
			}
			else {
				System.out.println("Error in query");
				sqlE.printStackTrace();
			}
		}
	}

	public BookManager getBookMan() {
		return bookMan;
	}

	public AuthorManager getAuthorMan() {
		return authorMan;
	}

	public BorrowerManager getBorrowMan() {
		return borrowMan;
	}
	
	
	
}
