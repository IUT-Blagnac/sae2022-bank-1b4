package application.control;

import java.util.ArrayList;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.AlertUtilities;
import application.tools.EditionMode;
import application.tools.StageManagement;
import application.view.ComptesManagementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Client;
import model.data.CompteCourant;
import model.orm.AccessCompteCourant;
import model.orm.exception.ApplicationException;
import model.orm.exception.DatabaseConnexionException;
import model.orm.exception.Order;
import model.orm.exception.Table;

public class ComptesManagement {

	private Stage primaryStage;
	private ComptesManagementController cmc;
	private DailyBankState dbs;
	private Client clientDesComptes;

	public ComptesManagement(Stage _parentStage, DailyBankState _dbstate, Client client) {

		this.clientDesComptes = client;
		this.dbs = _dbstate;
		try {
			FXMLLoader loader = new FXMLLoader(ComptesManagementController.class.getResource("comptesmanagement.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, root.getPrefWidth()+50, root.getPrefHeight()+10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Gestion des comptes");
			this.primaryStage.setResizable(false);

			this.cmc = loader.getController();
			this.cmc.initContext(this.primaryStage, this, _dbstate, client);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doComptesManagementDialog() {
		this.cmc.displayDialog();
	}

	public void gererOperations(CompteCourant cpt) {
		OperationsManagement om = new OperationsManagement(this.primaryStage, this.dbs, this.clientDesComptes, cpt);
		om.doOperationsManagementDialog();
	}

	/**
     * Permet de créer un compte
     * @return : le compte courant créé
     */
    public CompteCourant creerCompte() {
        CompteCourant currentCompte;
        CompteEditorPane editorComptePane = new CompteEditorPane(this.primaryStage, this.dbs); // Initialisation de la fenêtre
        currentCompte =  editorComptePane.doCompteEditorDialog(this.clientDesComptes, null, EditionMode.CREATION); // Ouverture de la fenêtre en mode création (CompteEditorPaneController)
        
        if(currentCompte == null) 
            return currentCompte; // Permet de quitter la fenêtre (Bouton Annuler)

        if(currentCompte.solde < 50) {
        	Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("ERREUR");
		alert.setHeaderText("Erreur de solde");
		alert.setContentText("Le solde doit être supérieur à 50 euros");

		alert.showAndWait();
        }
        else {
			 try {
            AccessCompteCourant acc = new AccessCompteCourant();
            acc.creerCompte(currentCompte);
        } catch (DatabaseConnexionException e) {
            ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
            ed.doExceptionDialog();
            this.primaryStage.close();
            return null;
        } catch (ApplicationException ae) {
            ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
            ed.doExceptionDialog();
            return null;
        }
			 return currentCompte;
		}
    return null;
    }
    
    /**
     * Permet de créer un compte
     * @return : le compte courant clôturé
     */
    public CompteCourant cloturerCompte(CompteCourant compte) {

        CompteEditorPane cep = new CompteEditorPane(this.primaryStage, this.dbs);
        CompteCourant nvCompte = cep.doCompteEditorDialog(this.clientDesComptes, compte, EditionMode.SUPPRESSION);

        if(nvCompte == null)
            return null;
        
        if(compte.solde != 0){
            return null;
        }

        try {
            AccessCompteCourant acc = new AccessCompteCourant();
            acc.cloturerCompte(compte);
        } catch (DatabaseConnexionException e) {
            ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
            ed.doExceptionDialog();
            this.primaryStage.close();
        } catch (ApplicationException ae) {
            ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
            ed.doExceptionDialog();
        }

        return compte;
    }

	public ArrayList<CompteCourant> getComptesDunClient() {
		ArrayList<CompteCourant> listeCpt = new ArrayList<>();

		try {
			AccessCompteCourant acc = new AccessCompteCourant();
			listeCpt = acc.getCompteCourants(this.clientDesComptes.idNumCli);
		} catch (DatabaseConnexionException e) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, e);
			ed.doExceptionDialog();
			this.primaryStage.close();
			listeCpt = new ArrayList<>();
		} catch (ApplicationException ae) {
			ExceptionDialog ed = new ExceptionDialog(this.primaryStage, this.dbs, ae);
			ed.doExceptionDialog();
			listeCpt = new ArrayList<>();
		}
		return listeCpt;
	}
}
