package library.db.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import library.db.interfaces.BorrowerManager;
import library.db.pojos.Book;
import library.db.pojos.Borrower;

public class JDBCBorrowerManager implements BorrowerManager {

	private Connection c;
	
	public JDBCBorrowerManager(Connection c) {
		this.c = c;
	}
	
	@Override
	public void addBorrower(Borrower b) {
		try {
			String template = "INSERT INTO borrowers (name, surname) VALUES (?, ?)";
			PreparedStatement pstmt;
			pstmt = c.prepareStatement(template);
			pstmt.setString(1, b.getName());
			pstmt.setString(2, b.getSurname());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Error in the database");
			e.printStackTrace();
		}
	}

	@Override
	public void borrowBook(int borrowerId, int bookId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void returnBook(int borrowerId, int bookId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Book> getBorrowedBooks(int borrowerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Borrower getBorrower(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
