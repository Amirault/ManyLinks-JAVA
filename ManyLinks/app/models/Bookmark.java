package models;

import javax.persistence.*;

import java.util.*;

import play.*;
import play.db.jpa.*;
import play.libs.*;
import play.data.validation.*;
/*
 * Classe associative Link <-> Member
 * Représente les liens d'un membre
 */
@Entity
@IdClass(BookmarkId.class)
public class Bookmark extends GenericModel {

	@Id
	public long linkId;
	@Id
	public long memberId;
	@Id
	public long categoryId;
	
	// Plusieurs "bookmark" peuvent être associé à un "link"
	@ManyToOne
	@JoinColumn(name = "linkId", updatable = false, insertable = false)
	public Link link;
	
	// Plusieurs "bookmark" peuvent être associé à une "Category"
	@ManyToOne
	@JoinColumn(name = "categoryId", updatable = false, insertable = false)
	public Category category;
	
	// Plusieurs "bookmark" peuvent être associé à un "member"
	@ManyToOne
	@JoinColumn(name = "memberId", updatable = false, insertable = false)
	public Member member;
	
	@Column
	public String name;

	/*
	 * 
	 */
	public Bookmark(String name,Link link,Member member,Category category) {
		super();
		this.name = name;
		// On créer l'association 
		this.link = link;
		this.member = member;
		this.memberId = member.getId();
		this.linkId = link.getId();
		this.category = category;
		this.categoryId = category.getId();
		this.category.getBookmarkList().add(this);
		this.category.save();
		this.member.getBookmarkList().add(this);
		this.member.save();
		this.link.getBookmarkList().add(this);
		this.link.save();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
