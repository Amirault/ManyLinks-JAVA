package models;

import javax.persistence.*;

import java.util.*;

import play.*;
import play.db.jpa.*;
import play.libs.*;
import play.data.validation.*;

/**
 * 
 */

/**
 * @author AMIRAULT - PHAM
 *
 */
/*
 * Classe représentant les liens existants sur le site (public ou privé)
 */
@Entity
@DiscriminatorValue("link")
public class Link extends Model{
	
	@Required
	public String url;
	
	public String title;
	
	public int rate;
	
	public String miniature;
	
	public Statut statut;
	
	public Date timeStamp;
	
	public int cptClic;
	
	public int cptAjout;
	
	@OneToMany(mappedBy="link")
	public List<Comment> commentList;
	
	@OneToMany(mappedBy="link")
	public List<Bookmark> bookmarkList;
	
	@OneToMany(mappedBy="link")
	public List<Rate> rateList;
	
	public Link(String url, String title, Statut statut) {
		super();
		this.url = url;
		this.title = title;
		this.statut = statut;
		this.rate = 0;
		this.commentList = new ArrayList<Comment>();
		this.bookmarkList = new ArrayList<Bookmark>();
		this.rateList = new ArrayList<Rate>();
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public int getCptClic() {
		return cptClic;
	}

	public void setCptClic(int cptClic) {
		this.cptClic = cptClic;
	}

	public int getCptAjout() {
		return cptAjout;
	}

	public void setCptAjout(int cptAjout) {
		if (this.cptAjout == 5){
			// le favori deviens publique car plusieurs membre l'on ajouté
			this.statut = Statut.partage;
		}
		this.cptAjout = cptAjout;
	}

	public String getUrl() {
		return url;
	}

	public String getMiniature() {
		return miniature;
	}
	
	public static Link findByURL(String url) {
        return find("url", url).first();
    } 
	
	public static List<Link> findMostRecent(Statut statut){
		return find("rate >= 0 and statut = ?   order by timeStamp", statut).fetch();
	}
	
	public static List<Link> findTopAjout(){
		return find("rate >= 0 and statut = ? order by cptAjout desc ", Statut.partage).fetch(10);
	}

	
	public static List<Link> findTopRate(){
		return find("statut = ? order by rate desc", Statut.partage).fetch(10);
	}

	public static List<Link> findByKeyword(String s){
		return find("(url like ? or title like ?) and statut = ?", "%"+s+"%", "%"+s+"%", Statut.partage).fetch();
	}

	public static List<Link> findByKeywordLimit(String s, int limit){
		return find("(url like ? or title like ?) and statut = ?", "%"+s+"%", "%"+s+"%", Statut.partage).fetch(limit);
	}
	public int getRate() {
		return rate;
	}

	public void setRate(int val) {
		this.rate = this.rate + val;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public String getIcone() {
		return "http://www.google.com/s2/favicons?domain="+this.url;
	}

	public List<Bookmark> getBookmarkList() {
		return bookmarkList;
	}

	public void setBookmarkList(List<Bookmark> bookmarkList) {
		this.bookmarkList = bookmarkList;
	}

	public List<Rate> getRateList() {
		return rateList;
	}

	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
	}
	
	
	 
}
