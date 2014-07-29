package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import play.db.jpa.GenericModel;
/*
 * Classe associative Category <-> Member
 * Représente les categories (privé) publié par un membre
 */
@Entity
@IdClass(PrivateCategoryId.class)
public class PrivateCategory extends GenericModel {

	@Id
	public long categoryId;
	@Id
	public long memberId;
	
	@ManyToOne
	@JoinColumn(name = "categoryId", updatable = false, insertable = false)
	public Category category;
	
	@ManyToOne
	@JoinColumn(name = "memberId", updatable = false, insertable = false)
	public Member member;
	

	public PrivateCategory(Category category, Member member) {
		super();
		this.category = category;
		this.member = member;
		this.categoryId = category.getId();
		this.memberId = member.getId();
		member.getPrivateCategoryList().add(this);
		member.save();
		category.getPrivateCategoryList().add(this);
		category.save();
	}

	

	public long getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}



	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	
}
