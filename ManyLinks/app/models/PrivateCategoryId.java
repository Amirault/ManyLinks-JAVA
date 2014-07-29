package models;

import java.io.Serializable;

import javax.persistence.*;

/* Classe permettant de définir l'identifiant de "PrivateCategory"
 * L'identifiant est l'association des autres identifiant des tables associées
 */
@Embeddable
public class PrivateCategoryId implements Serializable {
	 
	  public long categoryId;
	 
	  public long memberId;
	 
	  public int hashCode() {
	    return (int)(categoryId + memberId);
	  }
	 
	  public boolean equals(Object object) {
	    if (object instanceof PrivateCategoryId) {
	    	PrivateCategoryId otherId = (PrivateCategoryId) object;
	      return (otherId.categoryId == this.categoryId) && (otherId.memberId == this.memberId);
	    }
	    return false;
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
	  
	  
	 
	}
