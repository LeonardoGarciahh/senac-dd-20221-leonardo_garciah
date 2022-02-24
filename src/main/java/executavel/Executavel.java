package executavel;

import view.Menu;

import java.sql.SQLException;

import view.Login;

public class Executavel {

	public static void main(String[] args) throws SQLException {
		Login menuLogin = new Login();
		menuLogin.apresentarMenuLogin();

	}

}
