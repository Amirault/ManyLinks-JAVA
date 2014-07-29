package models;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToMany;
import javax.persistence.Entity;


import play.db.jpa.Model;


/**
 * @author AMIRAULT - PHAM
 *
 */
/*
 * Classe représentant les mosaïques existants sur le site (public ou privé)
 */
@Entity
public class Mosaique extends Model {
	
	public String name;
	
	public Date timeStamp;
	
	public Statut statut;

	public Mosaique(String name, Statut statut) {
		super();
		this.name = name;
		this.statut = statut;
	}
	
	
	
}
