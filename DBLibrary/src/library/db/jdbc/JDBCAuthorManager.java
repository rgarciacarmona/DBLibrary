package library.db.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import library.db.interfaces.AuthorManager;
import library.db.pojos.Author;
import library.db.pojos.Book;

public class JDBCAuthorManager implements AuthorManager {

	private Connection c;
	private ConnectionManager conMan;

	public JDBCAuthorManager(ConnectionManager conMan) {
		this.conMan = conMan;
		this.c = conMan.getConnection();
	}

	@Override
	public void addAuthor(Author a) {
		try {
			String template = "INSERT INTO authors (name, surname) VALUES (?, ?)";
			PreparedStatement pstmt;
			pstmt = c.prepareStatement(template);
			pstmt.setString(1, a.getName());
			pstmt.setString(2, a.getSurname());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("Error in the database");
			e.printStackTrace();
		}

	}

	@Override
	public List<Book> getBooksByAuthor(int authorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> getAuthorByNameSurname(String name, String surname) {
		List<Author> authors = new ArrayList<Author>();
		try {
			String sql = "SELECT * FROM authors WHERE name LIKE ? AND surname LIKE ?";
			PreparedStatement p;
			p = c.prepareStatement(sql);
			p.setString(1, "%" + name + "%");
			p.setString(2, "%" + surname + "%");
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String authorName = rs.getString("name");
				String authorSurname = rs.getString("surname");
				Author a = new Author(id, authorName, authorSurname);
				authors.add(a);
			}
			rs.close();
			p.close();
		} catch (SQLException e) {
			System.out.println("Error in the database");
			e.printStackTrace();
		}
		return authors;
	}

	@Override
	public void changeAuthor(Author a) {
		// TODO Complete the method with this query
		String template = "UPDATE authors SET name = ?, surname = ? WHERE id = ?";

	}

	@Override
	public Author getAuthor(int id) {
		try {
			String sql = "SELECT * FROM authors WHERE id = " + id;
			Statement st;
			st = c.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			Author a = new Author (rs.getInt("id"), rs.getString("name"), rs.getString("surname"));
			return a;
		} catch (SQLException e) {
			System.out.println("Error in the database");
			e.printStackTrace();
		}
		return null;
	}

}
