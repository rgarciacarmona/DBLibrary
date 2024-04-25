package library.db.jpa;

import java.util.List;

import javax.persistence.*;

import library.db.interfaces.UserManager;
import library.db.pojos.Role;
import library.db.pojos.User;

public class JPAUserManager implements UserManager {

	private EntityManager em;
	
	public JPAUserManager() {
		em = Persistence.createEntityManagerFactory("library-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		// Create default roles
		// If they don't exist already
		try {
			this.getRole("librarian");
		} catch(NoResultException e) {
			this.createRole(new Role("librarian"));
			this.createRole(new Role("borrower"));
		}
	}
	
	@Override
	public void register(User u) {
		em.getTransaction().begin();
		em.persist(u);
		em.getTransaction().commit();
	}

	@Override
	public void createRole(Role r) {
		em.getTransaction().begin();
		em.persist(r);
		em.getTransaction().commit();
	}

	@Override
	public Role getRole(String name) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE name LIKE ?", Role.class);
		q.setParameter(1, name);
		Role r = (Role) q.getSingleResult();
		return r;
	}
	
	@Override
	public List<Role> getAllRoles() {
		Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
		List<Role> roles = (List<Role>) q.getResultList();
		return roles;
	}

	@Override
	public void assignRole(User u, Role r) {
		em.getTransaction().begin();
		u.setRole(r);
		r.addUser(u);
		em.getTransaction().commit();
	}

	@Override
	public User login(String username, String password) {
		User u = null;
		Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND password = ?", User.class);
		q.setParameter(1, username);
		q.setParameter(2, password);
		try {
			u = (User) q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return u;
	}

}
