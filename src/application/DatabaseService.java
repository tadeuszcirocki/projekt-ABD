package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

	static void PrintRespond(String query) {
		try {
			Statement stat = conn.createStatement();
			String sql = query;
			ResultSet result = stat.executeQuery(sql);
			ResultSetMetaData resultMeta = result.getMetaData();
			int columnCount = resultMeta.getColumnCount();
			for (int i = 1; i <= columnCount; i++) { // print column name
				System.out.print(resultMeta.getColumnName(i) + " ");
			}
			System.out.println();
			while (result.next()) { // print values

				for (int i = 1; i <= columnCount; i++) {
					System.out.print(result.getString(i) + " ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("PrintError");
			e.printStackTrace();
		}
	}

	static void PrintTab(String tab) {
		PrintRespond("select * from " + tab);
	}

	static void PrintByCity(String city) {
		PrintRespond("select  nazwisko, (year(getdate()) - year(data_ur)) as wiek, miasto from klient k, adres a "
				+ "where k.id_klient=a.id_klient and miasto ='" + city + "'");
	}

	static void TheYoungest() {
		try {
			Statement stat = conn.createStatement();
			String sql = "select (year(getdate()) - year(data_ur)) as wiek from klient";
			ResultSet resultWiek = stat.executeQuery(sql);

			int min = 100;
			int id = 1;
			int minId = 1;
			while (resultWiek.next()) {
				if (resultWiek.getInt(1) < min) {
					min = resultWiek.getInt(1);
					minId = id;
				}
				id++;
			}
			sql = "select nazwisko from klient where id_klient =" + minId;
			resultWiek = stat.executeQuery(sql);
			resultWiek.next();
			System.out.println(resultWiek.getString(1));
		} catch (SQLException e) {
			System.out.println("PrintError");
			e.printStackTrace();
		}
	}

	static void PrintInhabitants() {
		try {
			Statement stat = conn.createStatement();
			Statement stat2 = conn.createStatement();
			String sql = "select distinct miasto from adres";
			ResultSet result = stat.executeQuery(sql);
			String currentCity;
			while (result.next()) { // print values
				currentCity = result.getString(1);
				String sql2 = "select count (nazwisko) from klient k, adres a"
						+ " where a.id_adres = k.id_klient and miasto = '" + currentCity + "'";
				ResultSet result2 = stat2.executeQuery(sql2);
				result2.next();
				System.out.println(currentCity + " | " + result2.getString(1));
			}

		} catch (SQLException e) {
			System.out.println("PrintError");
			e.printStackTrace();
		}
	}
}
