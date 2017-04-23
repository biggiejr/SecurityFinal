package objects;

import operations.SaltingPasswords;

/**
 *
 * @author Mato
 */
public class User {
	int id;
	String userName, password;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		SaltingPasswords.hash(password.toCharArray(), SaltingPasswords.getNextSalt());
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" + "userName=" + userName + ", password=" + password + '}';
	}

}
