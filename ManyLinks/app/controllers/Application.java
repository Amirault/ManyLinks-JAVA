package controllers;

import models.*;
import notifiers.*;

import play.*;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;
/*
 * Contrôleur Application définissant  la navigation, l'inscription,la connexion
 * et  les variables et méthode global à utiliser
 */
public class Application extends Controller {
	
	@Before
    static void globals() {
		/* argument permettant de savoir sur l'utilisateur est connecté*/
        renderArgs.put("connected", connectedUser());
    }
	
    public static void index() {
        render();
    }
    // Page d'acceuil par défault
    public static void accueil() {
        render();
    }
    // Page de connexion / Application/login
    public static void login() {
        render();
    }
    // Page d'inscription / Application/inscription
    public static void inscription() {
        render();
    }
    // Validation suite à l'inscription de l'utilisateur
    public static void validationInscription(@Required @Email String email, @Required @MinSize(4) String pseudo,@Required @MinSize(3) String mdp, @Equals("mdp") String mdp2) {
        if (validation.hasErrors()) {// Validation des paramètres transmis
            validation.keep();
            params.flash();
            flash.error("Please correct these errors !");
            inscription();
        }
        // Creation du nouveau membre
        Member newMember = new Member(email, pseudo,mdp, TypeMembre.Utilisateur);
        newMember.save();
        // Création de la catégorie privé par defaut (racine)
        Category cat = new Category("root","dossier root",Statut.prive);
        cat.save();
        PrivateCategory privateCat = new PrivateCategory(cat,newMember); 
        privateCat.save(); // Sauvegarde de la catégorie par défaut
        try {
            if (Notifier.welcome(newMember)) {
                flash.success("Your account is created. Please check your emails ...");
                login();
            }
        } catch (Exception e) {
            Logger.error(e, "Mail error");
        }
        flash.error("Oops ... (the email cannot be sent)");
        login();
    }
    // Un utilisateur veut s'authentifier
    public static void authentification(@Required @Email String email, @Required @MinSize(3) String mdp) {
    	if (validation.hasErrors()) {// Validation des paramètres transmis
            validation.keep();
            params.flash();
            flash.error("Please correct these errors !");
            login();
        }
    	Member utilisateur = Member.findByEmail(email);
       if (utilisateur == null || !utilisateur.checkPassword(mdp)) {
            flash.error("Wrong email or wrong password");
            flash.put("email", email);
            login();
        } else if (utilisateur.needConfirmation != null) {
            flash.error("This account is not confirmed");
            flash.put("notconfirmed", utilisateur.needConfirmation);
            flash.put("email", email);
            login();
        }
        connect(utilisateur);/* on connecte l'utilisteur */
        flash.success("Welcome back %s !", utilisateur.getPseudo().toString());
        if (utilisateur.getType().equals(TypeMembre.Utilisateur)){
        	accueil();
        }
    }
    // Enregistre l'utilisateur comme connecté 
    static void connect(Member utilisateur) {
        session.put("logged", utilisateur.id);
    }
    // Permet de récuperer le membre courant (connecté)
    static Member connectedUser() {
        String userId = session.get("logged");
        if (userId == null){
        	return null;
        }
        else
        {
        	return (Member) Member.findById(Long.parseLong(userId));
        }
    }
    // Demande de déconnexion du membre
    public static void deconnexionUser(){
    	session.clear();
    	index();
    }

}