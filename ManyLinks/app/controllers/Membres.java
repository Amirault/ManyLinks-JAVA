package controllers;
import models.*;
import notifiers.*;
import play.*;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.libs.Codec;
import play.mvc.*;

import java.util.*;
/*
 * Contrôleur Membres permettant de gérer la page avec la gestion du compte du membre
 * Herite du contrôleur (général) Application
 */
public class Membres extends Application {
	
	// page Membres/GestionCompte
	public static void gestionCompte() {
		// Renvoi le membre connecté à la page de gestion de compte
		Member membre = connectedUser();
        render(membre);
    }
	// Le membre modifie ces informations
	public static void modifCompteMembre(@Required @MinSize(4) String pseudo,@Required String ancienMdp, @MinSize(3) String mdp, @Equals("mdp") String mdp2){
		/*
		 *  Permet de valider les modifications effectué par le membre sur son compte
		 */
		if (validation.hasErrors()) {// Validation des paramètres transmis
            validation.keep();
            params.flash();
            flash.error("Please correct these errors!");
            renderText("error");
        }
		else
		{
			Member membre = connectedUser();
			if (Codec.hexMD5(ancienMdp).equals(membre.getMdpEncoded()))
			{
				membre.setPseudo(pseudo);
				membre.setMdpEncoded(mdp);
				membre.save();
				renderText("success");
			}
			else
			{
				renderText("password");
			}
		}
	}
	// Le membre supprime son compte
	public static void supprimerCompteMembre() {
		Member membre = connectedUser();
		membre.delete();
		session.clear();
    }

}