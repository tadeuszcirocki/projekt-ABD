package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

	static Connection conn;

	static void Connect() {

		String dbURL = "jdbc:sqlserver://ZENBOOK\\TADEUSZSQL;databaseName=PROJEKT;";
		String user = "sa";
		String pass = "password";

		try {
			conn = DriverManager.getConnection(dbURL, user, pass);

		} catch (SQLException ex) {
			System.out.println("ConnectError");
			ex.printStackTrace();
		}
	}

	static String LoggingReturnsType(String login, String password) {
		String typ = null;
		try {
			Statement stat = conn.createStatement();
			String sql = String.format("select * from Uzytkownicy where Login='%s' and Haslo='%s'", login, password);
			ResultSet result = stat.executeQuery(sql);
			while (result.next()) {
				typ = result.getString("Typ_uzytkownika");
			}
			return typ;
		} catch (SQLException e) {
			e.printStackTrace();
			return "èle";
		}
	}

	static boolean Delete(String tableName, int id) {
		try {
			Statement stat = conn.createStatement();
			String sql = String.format("delete from %s where id=%d", tableName, id);
			int affectedRows = stat.executeUpdate(sql);
			return (affectedRows == 1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	static boolean Add(String tableName, String values) { // values('Janusz','Koralik','123333')
		try {
			Statement stat = conn.createStatement();
			values = values.replaceAll(" ", "','");
			values = "'" + values + "'";
			String sql = String.format("insert into %s values (%s)", tableName, values);
			int affectedRows = stat.executeUpdate(sql);

			return (affectedRows == 1);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
