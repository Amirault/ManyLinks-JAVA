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

import com.sun.mail.imap.protocol.Status;
/*
 * Contrôleur bookmark permettant de gérer la page avec les favoris du membre
 */
public class Bookmarks extends Application {
	
	// Bookmarks/index
    public static void index() {
    	Member membre = connectedUser();
        render(membre);
    }
    
    // Ajout d'un favoris par un membre
    public static void publicationFavori(@Required String url,String name,@Required boolean isPublic) {
    	if (validation.hasErrors()) {// Validation des paramètres transmis
            validation.keep();
            params.flash();
            flash.error("Please correct these errors !");
            renderText("ERREUR");
        }
    	// TODO : validation de l'url
    	// TODO : validation du nom
    	Member currentMember = connectedUser();
		if (currentMember.hasBookmark(url)){// verification si le membre à deja le favori
			renderText("You have already this bookmark");
		}
		else{// Le membre n'a pas le lien en favori
	    	Link newLink;
	    	// voir pour cherche le bon title pour le link
	    	String title = name;
	    	if (isPublic)// verification si le membre veut partager le favori
	    		newLink = new Link(url,title,Statut.partage);
	    	else
	    		newLink = new Link(url,title,Statut.prive);
	    	
	    	// vérification qu'il n'existe pas dans la base avant de l'ajouter
	    	Link realLink = Link.findByURL(newLink.url);
			if (realLink != null){
				newLink = realLink;
			}
			newLink.cptAjout++;
			newLink.save();// On sauvegarde le lien
			
			// Création et ajout du nouveau favoris dans la base du membre
			for (PrivateCategory privateCat : currentMember.getPrivateCategoryList()){
				if (privateCat.getCategory().getName().equals("root")){
					Bookmark newBookmark = new Bookmark(name,newLink,currentMember,privateCat.getCategory());
			    	newBookmark.save();
					break;
				}
				
			}
			renderText("SUCCESS");
		}
    }
    //Ajout d'une catégorie par un membre
    public static void publicationCategory(@Required String name,@Required String description){
    	if (validation.hasErrors()) {// Validation des paramètres transmis
            validation.keep();
            params.flash();
            flash.error("Please correct these errors!");
            renderText("ERREUR");
        }
    	Member currentMember = connectedUser();
    	if (currentMember.hasCategory(name)){
    		renderText("This name has been already used!");
    	}
    	else
    	{
    		Category newCategory = new Category(name,description,Statut.prive);
    		newCategory.save();
    		PrivateCategory privateCategory =  new PrivateCategory(newCategory,currentMember);
    		privateCategory.save();
    		renderText("SUCCESS");
    	}
    }
    // Un membre supprime un favori
    public static void deleteBookmark(Long id){
    	if (id != null){
    		Bookmark bookmark = Bookmark.findById(id);
    		bookmark.getLink().getBookmarkList().remove(bookmark);
    		bookmark.getLink().save();
    		bookmark.getMember().getBookmarkList().remove(bookmark);
    		bookmark.getMember().save();
	    	bookmark.delete();
	    	
	    	renderText("Bookmark deleted");
    	}
    	else
    	{
    		renderText("ERREUR");
    	}
    }
    // Mise à jour d'un faovri par un membre
    public static void updateBookmak(@Required String url,String name,@Required boolean isPublic){
    	Member membre = connectedUser();
    	
    	//membre.favoris
    }
}