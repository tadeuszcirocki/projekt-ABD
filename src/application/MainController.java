package application;

import java.sql.ResultSet;

import com.sun.glass.ui.Menu;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainController {
	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtUserName;
	@FXML
	private TextField txtPassword;
	@FXML
	private Menu menu;
	@FXML
	private MenuItem tableMenu;
	@FXML
	private TextField txtAdd;
	@FXML
	private TextField txtDelete;
	@FXML
	private Label lblDelete;
	@FXML
	private Label lblAdd;

	String currentTableString = null; // ktora tabela jest aktualnie wybrana

	public void Login(ActionEvent event) {
		Stage stage = (Stage) lblStatus.getScene().getWindow(); // okno z logowaniem zeby pozniej zamknac
		DatabaseService.Connect();
		String typ = DatabaseService.LoggingReturnsType(txtUserName.getText(), txtPassword.getText());
		if (typ.equals("admin")) {
			CreateScene("Admin");
			// CreateTableScene("Pole");
			stage.close();
		} else if (typ.equals("user")) {
			CreateScene("User");
			stage.close();
		} else if (typ.equals("owner")) {
			CreateScene("Owner");
			stage.close();
		} else
			lblStatus.setText("Niepoprawne dane logowania");
	}

	public void Delete(ActionEvent event) {
		try {
			int id = Integer.parseInt(txtDelete.getText());
			boolean flag = DatabaseService.Delete(currentTableString, id);
			if (flag) {
				lblDelete.setText("usuniêto");
			} else {
				lblDelete.setText("nie uda³o siê usun¹æ");
			}
		} catch (Exception e) {
			lblDelete.setText("proszê o numer");
		}
	}

	public void Add(ActionEvent event) {
		try {
			boolean flag = DatabaseService.Add(currentTableString, txtAdd.getText());
			if (flag) {
				lblDelete.setText("dodano");
			} else {
				lblDelete.setText("nie uda³o siê dodaæ");
			}
		} catch (Exception e) {
			lblDelete.setText("niepowodzenie");
		}
	}

	public void tableUzytkownicy(ActionEvent event) { // akcje dla menu
		CreateTableScene("Uzytkownicy");
	}

	public void tablePracownik(ActionEvent event) {
		CreateTableScene("Pracownik");
	}

	public void tableNasiona(ActionEvent event) {
		CreateTableScene("Nasiona");
	}

	public void tableAkcja(ActionEvent event) {
		CreateTableScene("Akcja");
	}

	public void tableMaszyna(ActionEvent event) {
		CreateTableScene("Maszyna");
	}

	public void tableNawoz(ActionEvent event) {
		CreateTableScene("Nawoz");
	}

	public void tableSilos(ActionEvent event) {
		CreateTableScene("Silos");
	}

	public void tablePole(ActionEvent event) {
		CreateTableScene("Pole");
	}

	public void tableRoslina(ActionEvent event) {
		CreateTableScene("Roslina");
	}

	private void CreateScene(String name) { // DRY
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Views/" + name + ".fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.setX(500);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ObservableList<ObservableList> data;
	private TableView tableview;

	public void CreateTableScene(String tableName) {
		Stage stage = new Stage();
		currentTableString = tableName;
		tableview = new TableView();
		data = FXCollections.observableArrayList();
		try {

			// SQL FOR SELECTING ALL OF CUSTOMER
			String SQL = String.format("SELECT * from %s", tableName);
			// ResultSet
			ResultSet rs = DatabaseService.conn.createStatement().executeQuery(SQL);

			/**
			 * ******************************** TABLE COLUMN ADDED DYNAMICALLY *
			 *********************************
			 */
			for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) { // tu zmiana by³a
				// We are using non property style for making dynamic table
				final int j = i;
				TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
				col.setCellValueFactory(
						new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
							public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
								return new SimpleStringProperty(param.getValue().get(j).toString());
							}
						});

				tableview.getColumns().addAll(col);
			}

			/**
			 * ****************************** Data added to ObservableList *
			 *******************************
			 */
			while (rs.next()) {
				// Iterate Row
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					// Iterate Column
					row.add(rs.getString(i));
				}

				data.add(row);

			}

			// FINALLY ADDED TO TableView
			tableview.setItems(data);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on Building Data");
		}
		// Main Scene
		Scene scene = new Scene(tableview); // rozmiar tu ustawic
		stage.setX(1200);
		stage.setScene(scene);
		stage.show();
	}

}
