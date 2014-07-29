/**
 * 
 */
package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;
/**
 * @author AMIRAULT - PHAM
 *
 */
/*
 * Classe associative Link <-> Member
 * Représente les commentaires des membres sur un lien
 */
@Entity
@IdClass(CommentId.class)
public class Comment extends GenericModel{

	@Id
	public long linkId;
	@Id
	public long memberId;
	
	@ManyToOne
	@JoinColumn(name = "linkId", updatable = false, insertable = false)
	public Link link;
	
	@ManyToOne
	@JoinColumn(name = "memberId", updatable = false, insertable = false)
	public Member member;
	
	public Date timeStamp;
	public String contenu;


	public Comment(Date timeStamp, String contenu,Link link,Member member) {
		super();
		this.timeStamp = timeStamp;
		this.contenu = contenu;
		this.link = link;
		this.member = member;
		this.memberId = member.getId();
		this.linkId = link.getId();
		this.member.getCommentList().add(this);
		this.member.save();
		this.link.getCommentList().add(this);
		this.link.save();
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	public static List<Comment> findByFavori(long lien){
		return find("linkId", lien).fetch();
	}
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public long getLinkId() {
		return linkId;
	}

	public void setLinkId(long linkId) {
		this.linkId = linkId;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	
}
