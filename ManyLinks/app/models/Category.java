package models;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import play.db.jpa.Model;

/**
 * 
 */

/**
 * @author AMIRAULT - PHAM
 *
 */
/*
 * Classe représentant les catégories existantes sur le site (public ou privé)
 */
@Entity
@DiscriminatorValue("category")
public class Category extends Model {
	
	public String name;
	
	public String description;
	
	public Statut statut;
	
	@OneToMany(mappedBy="category")
	public List<PrivateCategory> privateCategoryList; 
	
	@OneToMany(mappedBy="category")
	public List<Bookmark> bookmarkList; 
	

//	List<Category> 	childCategoryList;
	
	public Category(String name, String description,Statut statut) {
		super();
		this.name = name;
		this.description = description;
		this.statut = statut;
		this.privateCategoryList = new ArrayList<PrivateCategory>();
		this.bookmarkList = new ArrayList<Bookmark>();
	//	this.childCategoryList = new ArrayList<Category>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PrivateCategory> getPrivateCategoryList() {
		return privateCategoryList;
	}

	public void setPrivateCategoryList(List<PrivateCategory> privateCategoryList) {
		this.privateCategoryList = privateCategoryList;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}
	public List<Bookmark> getBookmarkList() {
		return bookmarkList;
	}

	public void setBookmarkList(List<Bookmark> bookmarkList) {
		this.bookmarkList = bookmarkList;
	}
/*
	public List<Category> getChildCategoryList() {
		return childCategoryList;
	}

	public void setChildCategoryList(List<Category> childCategoryList) {
		this.childCategoryList = childCategoryList;
	}*/
}
