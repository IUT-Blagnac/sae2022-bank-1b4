package application.view;

import java.net.URL;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.LoginDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.Employe;

public class LoginDialogController implements Initializable {

	// Etat application
	private DailyBankState dbs;
	private LoginDialog ld;

	// Fenêtre physique
	private Stage primaryStage;

	// Données de la fenêtre

	// Manipulation de la fenêtre
	/**
	 * Permet d'initialiser la fenêtre de l'application
	 * 
	 * @param _primaryStage
	 * @param _ld
	 * @param _dbstate
	 */
	public void initContext(Stage _primaryStage, LoginDialog _ld, DailyBankState _dbstate) {
		this.primaryStage = _primaryStage;
		this.ld = _ld;
		this.dbs = _dbstate;
		this.configure();
	}

	/**
	 * Construit et configure la fermeture de la fenêtre
	 */
	private void configure() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
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
	 * @param e
	 * @return
	 */
	private Object closeWindow(WindowEvent e) {
		this.doCancel();
		e.consume();
		return null;
	}

	// Attributs de la scene + actions
	@FXML
	private TextField txtLogin;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Label lblMessage;

	/**
	 * Permet d'initialiser le controleur
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/**
	 * Permet de quitter la fenêtre login
	 */
	@FXML
	private void doCancel() {
		this.dbs.setEmpAct(null);
		this.primaryStage.close();
	}

	/**
	 * Permet de vérifier si les identifiants (nom d'utilisateur et mot de passe) rentrés par l'utilisateur sont corrects
	 */
	@FXML
	private void doOK() {
		String login = this.txtLogin.getText().trim();
		String password = new String(this.txtPassword.getText().trim());
		if (login.length() == 0 || password.length() == 0) {
			this.afficheErreur("Identifiants incorrects :");
		} else {
			Employe e;
			e = this.ld.chercherParLogin(login, password);
			if (e == null) {
				this.afficheErreur("Identifiants incorrects :");
			} else {
				this.dbs.setEmpAct(e);
				this.primaryStage.close();
			}
		}
	}

	/**
	 * Permet d'afficher un message d'erreur si les identifiants sont erronés
	 * @param texte
	 */
	private void afficheErreur(String texte) {
		this.lblMessage.setText(texte);
		this.lblMessage.setStyle("-fx-text-fill:red; -fx-font-weight: bold;");
	}
}
