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
 * Contrôleur Library permettant de gérer la page avec tout les favoris publics
 *  
 */
public class Library extends Application {
	// page Library/index
	public static void index() {
        List<Link> listOfLinks = Link.findMostRecent(Statut.partage);
		List<Link> listOfLinksTopAjout = Link.findTopAjout();
		List<Link> listOfLinksTopRate = Link.findTopRate();
		//Retourn
       render(listOfLinksTopAjout,listOfLinks, listOfLinksTopRate);
    }
	// prise en compte du vote de l'utilisateur
	public static void vote(@Required Long id,@Required Integer val) {
		if(validation.hasErrors()){// Validation des paramètres transmis
		   params.flash(); // ajout des paramètres http pour le scope flash
		   validation.keep(); //conserve les erreurs pour la prochaine requete
	       renderText("ERROR");
		}
		else{
    		Link link = Link.findById(id);
    		if (connectedUser().hasRateLink(link.getId())){;
    			renderText("Your rate as already been registred !");
    		}
    		else{
    			Rate rate = new Rate(val,link,connectedUser());
		    	rate.save();
		    	renderText("SUCCESS");
    		}
    	}
    }
	
	// envoyer la liste de commentaires du lien à la vue appelante
	public static void listOfComment(@Required long idLink){
		if(validation.hasErrors()){// Validation des paramètres transmis
		   params.flash(); 
		   validation.keep(); 
	       renderText("ERROR");
		}
		else{
			List<Comment> listOfComment = Comment.findByFavori(idLink);
			if(listOfComment.size() > 0){
				render(listOfComment);
			}
			renderText("No Comments");
		}
	}
	
	// envoyer la liste de tous les liens à la vue appelante
	public static void listOflinks(@Required String keyword){
		List<Link> listOfLinks;
		if(keyword == null || keyword.isEmpty()){
			listOfLinks = Link.findMostRecent(Statut.partage);
		}
		else{
			listOfLinks = Link.findByKeyword(keyword);	
		}
		render(listOfLinks);
	}
	// envoyer la liste des liens les plus ajoutés à la vue appelante en JSON
	public static void listOflinksJSON(@Required String keyword, int limit){
		List<Link> listOfLinks = Link.findByKeywordLimit(keyword, limit);
		renderJSON(listOfLinks);
	}
	
	// envoyer la liste des liens les plus ajoutés à la vue appelante
	public static void listOfLinksTopAjout(){
		List<Link> listOfLinksTopAjout = Link.findTopAjout();
		render(listOfLinksTopAjout);
	}
	
	// envoyer la liste des liens ayant le plus de votes à la vue appelante
	public static void listOfLinksTopRate(){
		List<Link> listOfLinksTopRate = Link.findTopRate();
		render(listOfLinksTopRate);
	}
	
	// ajouter un commentaire
	public static void setComment(@Required String contenu, @Required long idLink){
		if(validation.hasErrors()){// Validation des paramètres transmis
		   params.flash();
		   validation.keep(); 
	       renderJSON(false);
		}
		else{
			
			Link link = Link.findById(idLink);
			if (!connectedUser().hasComment(link.getUrl())){
		    	Comment c = new Comment(new Date(), contenu,link , connectedUser());
		    	link.getCommentList().add(c);
		    	connectedUser().getCommentList().add(c);
		    	c.save();
		    	renderJSON(true);
			}
			else{
				renderJSON(false);
			}
		}
		
    }
}