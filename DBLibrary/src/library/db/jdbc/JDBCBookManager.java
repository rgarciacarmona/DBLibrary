package library.db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import library.db.interfaces.BookManager;
import library.db.pojos.*;

public class JDBCBookManager implements BookManager {

	private Connection c;
	private ConnectionManager conMan;
	
	public JDBCBookManager(ConnectionManager conMan) {
		this.conMan = conMan;
		this.c = conMan.getConnection();
	}
	
	@Override
	public void addBook(Book b) {
		try {
			String query = "INSERT INTO books (isbn, title, publicationDate, author_id) "
							+ "VALUES (?,?,?,?);";
			PreparedStatement insert = c.prepareStatement(query);
			insert.setInt(1, b.getIsbn());
			insert.setString(2, b.getTitle());
			insert.setDate(3, b.getPublicationDate());
			insert.setInt(4, b.getAuthor().getId());
			insert.executeUpdate();
			insert.close();
		} catch (SQLException sqlE) {
			System.out.println("Error in query");
			sqlE.printStackTrace();
		}
	}

	@Override
	public List<Book> searchBookByTitle(String title) {
		List<Book> books = new ArrayList<Book>();
		try {
			String sql = "SELECT * FROM books WHERE title LIKE ?";
			PreparedStatement search = c.prepareStatement(sql);
			search.setString(1, "%" + title + "%");
			ResultSet rs = search.executeQuery();
			while(rs.next()) {
				Integer isbn = rs.getInt("isbn");
				String bookTitle = rs.getString("title");
				Date pubDate = rs.getDate("publicationDate");
				Integer authorId = rs.getInt("author_id");
				Author author = conMan.getAuthorMan().getAuthor(authorId);
				Book newBook = new Book(isbn, bookTitle, pubDate, author);
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
	public void deleteBook(int bookId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Book getBook(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
