package xwsagent.wroomagent.soap.xsd;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "CommentSoap", propOrder = {
	    "id",
	    "title",
	    "content",
	    "commentDate",
	    "deleted",
	    "approved",
	    "rate",
	    "requestedUserUsername",
	    "clientId",
	    "ad",
	    "replyId",
	    "reply"
	})
	public class CommentSoap {

		@XmlElement(required = true)
	    protected long id;
	    
	    @XmlElement(required = true)
	    protected String title;
	    
	    @XmlElement(required = true)
	    @XmlSchemaType(name = "date")
	    protected String content;
	    
	    @XmlElement(required = true)
	    @XmlSchemaType(name = "date")
	    protected Date commentDate;
	    
	    @XmlElement(required = true)
	    @XmlSchemaType(name = "date")
	    protected boolean deleted;
	    
	    @XmlElement(required = true)
	    @XmlSchemaType(name = "date")
	    protected boolean approved;
	    
	    @XmlElement(required = true)
	    @XmlSchemaType(name = "date")
	    protected Integer rate;
	    
	    @XmlElement(required = true)
	    protected String requestedUserUsername;
	    
	    @XmlElement(required = true)
	    protected Long clientId;
	    
		protected long ad;
	    
	    @XmlElement(required = true)
	    @XmlSchemaType(name = "date")
	    protected Long replyId;
	    
	    @XmlElement(required = true)
	    @XmlSchemaType(name = "date")
	    protected boolean reply;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Date getCommentDate() {
			return commentDate;
		}

		public void setCommentDate(Date commentDate) {
			this.commentDate = commentDate;
		}

		public boolean isDeleted() {
			return deleted;
		}

		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}

		public boolean isApproved() {
			return approved;
		}

		public void setApproved(boolean approved) {
			this.approved = approved;
		}

		public Integer isRate() {
			return rate;
		}

		public void setRate(Integer rate) {
			this.rate = rate;
		}

		public String getRequestedUserUsername() {
			return requestedUserUsername;
		}

		public void setRequestedUserUsername(String requestedUserUsername) {
			this.requestedUserUsername = requestedUserUsername;
		}

		public long getAd() {
			return ad;
		}

		public void setAd(long ad) {
			this.ad = ad;
		}

		public Long getReplyId() {
			return replyId;
		}

		public void setReplyId(Long replyId) {
			this.replyId = replyId;
		}

		public boolean isReply() {
			return reply;
		}

		public void setReply(boolean reply) {
			this.reply = reply;
		}
		
		public Long getClientId() {
			return clientId;
		}

		public void setClientId(Long clientId) {
			this.clientId = clientId;
		}
	    
}
