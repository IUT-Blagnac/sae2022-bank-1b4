package application.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ResourceBundle;

import application.DailyBankState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.orm.exception.ApplicationException;

public class ExceptionDialogController implements Initializable {

	// Etat application
	private DailyBankState dbs;

	// Fenêtre physique
	private Stage primaryStage;

	// Données de la fenêtre
	private ApplicationException ae;
	// Manipulation de la fenêtre

	/**
	 * Initialise la fenêtre de l'application
	 * @param _primaryStage
	 * @param _dbstate
	 * @param _ae
	 */
	public void initContext(Stage _primaryStage, DailyBankState _dbstate, ApplicationException _ae) {
		this.primaryStage = _primaryStage;
		this.dbs = _dbstate;
		this.ae = _ae;
		this.configure();
	}

	/**
	 * Construit et configure la fermeture de la fenêtre
	 */
	private void configure() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
		this.lblTitre.setText(this.ae.getMessage());
		this.txtTable.setText(this.ae.getTableName().toString());
		this.txtOpe.setText(this.ae.getOrder().toString());
		this.txtException.setText(this.ae.getClass().getName());
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		this.ae.printStackTrace(pw);
		this.txtDetails.setText(sw.toString());
	}

	/**
	 * Permet d'afficher la fenêtre
	 */
	public void displayDialog() {
		this.primaryStage.showAndWait();
	}

	// Gestion du stage
	/**
	 * Permet de procéder à une fermeture de la fenêtre
	 * 
	 * @param e
	 * @return
	 */
	private Object closeWindow(WindowEvent e) {
		return null;
	}

	// Attributs de la scene + actions
	@FXML
	private Label lblTitre;
	@FXML
	private TextField txtTable;
	@FXML
	private TextField txtOpe;
	@FXML
	private TextField txtException;
	@FXML
	private TextArea txtDetails;

	/**
	 * Initialise le controleur
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Permet de quitter la fenêtre
	 */
	@FXML
	private void doOK() {
		this.primaryStage.close();
	}
}
