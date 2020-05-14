package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtUserName;
	@FXML
	private TextField txtPassword;

	public void Login(ActionEvent event) {
		Stage stage = (Stage) lblStatus.getScene().getWindow(); // okno z logowaniem zeby pozniej zamknac
		DatabaseService.Connect();
		String typ = DatabaseService.LoggingReturnsType(txtUserName.getText(), txtPassword.getText());
		if (typ.equals("admin")) {
			CreateScene("Admin");
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

	private void CreateScene(String name) { // DRY
		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Views/" + name + ".fxml"));
			Scene scene = new Scene(root, 300, 300);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
