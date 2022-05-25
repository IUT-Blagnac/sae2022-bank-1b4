package application;

import application.tools.ConstantesIHM;
import model.data.AgenceBancaire;
import model.data.Employe;



public class DailyBankState {
	private Employe empAct;
	private AgenceBancaire agAct;
	private boolean isChefDAgence;

	
	/**
	 * Retourne empAct (l'employé)
	 * 
	 * @return l'employé actuel (empAct)
	 */
	public Employe getEmpAct() {
		return this.empAct;
	}

	/**
	 * Initialise un employé actif (employé connecté)
	 * 
	 * @param employeActif
	 */
	public void setEmpAct(Employe employeActif) {
		this.empAct = employeActif;
	}

	/**
	 * Retourne l'agence actuelle (agAct)
	 * @return current Agency (agAct) 
	 */
	public AgenceBancaire getAgAct() {
		return this.agAct;
	}

	/**
	 * Initialise une agence active (Agence connectée)
	 * @param agenceActive
	 */
	public void setAgAct(AgenceBancaire agenceActive) {
		this.agAct = agenceActive;
	}

	/**
	 * Savoir si un employé est un chef d'agence
	 * @return vrai si l'employé est chef d'agence non sinon
	 */
	public boolean isChefDAgence() {
		return this.isChefDAgence;
	}

	
	/**
	 * Initialise le chef d'agence 
	 * @param isChefDAgence 
	 */
	public void setChefDAgence(boolean isChefDAgence) {
		this.isChefDAgence = isChefDAgence;
	}

	/**
	 * Initialise les droits d'accés du chef d'agence
	 * @param droitsAccess
	 */
	public void setChefDAgence(String droitsAccess) {
		this.isChefDAgence = false;

		if (droitsAccess.equals(ConstantesIHM.AGENCE_CHEF)) {
			this.isChefDAgence = true;
		}
	}
}
