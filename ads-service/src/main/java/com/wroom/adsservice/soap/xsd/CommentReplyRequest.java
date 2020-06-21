package com.wroom.adsservice.soap.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="comment" type="{http://ftn.com/wroom-agent/xsd}CommentSoap"/&gt;
 *         &lt;element name="parentId" type="{http://ftn.com/wroom-agent/xsd}id"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { 
		"comment" ,
		"parentId"
})
@XmlRootElement(name = "CommentReplyRequest")
public class CommentReplyRequest {

	@XmlElement(required = true)
	protected CommentSoap comment;

	@XmlElement(required = true)
	protected Long parentId;
	
	/**
	 * Gets the value of the comment property.
	 * 
	 * @return possible object is {@link CommentSoap }
	 * 
	 */
	public CommentSoap getComment() {
		return comment;
	}

	/**
	 * Sets the value of the comment property.
	 * 
	 * @param value allowed object is {@link CommentSoap }
	 * 
	 */
	public void setComment(CommentSoap value) {
		this.comment = value;
	}
	
	/**
	 * Gets the value of the parentId property.
	 * 
	 * @return possible object is {@link CommentSoap }
	 * 
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the value of the parentId property.
	 * 
	 * @param value allowed object is {@link CommentSoap }
	 * 
	 */
	public void setParentId(Long value) {
		this.parentId = value;
	}

}
