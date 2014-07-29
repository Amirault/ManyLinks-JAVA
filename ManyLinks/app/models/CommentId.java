package models;

import java.io.Serializable;
import javax.persistence.*;

/* Classe permettant de définir l'identifiant de "Comment"
 * L'identifiant est l'association des autres identifiant des tables associées
 */
@Embeddable
public class CommentId implements Serializable {
	 
	  public long linkId;
	 
	  public long memberId;
	 
	  public int hashCode() {
	    return (int)(linkId + memberId);
	  }
	 
	  public boolean equals(Object object) {
	    if (object instanceof CommentId) {
	    	CommentId otherId = (CommentId) object;
	      return (otherId.linkId == this.linkId) && (otherId.memberId == this.memberId);
	    }
	    return false;
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
	  
	  
	 
	}
