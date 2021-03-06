//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.21 at 06:56:27 PM CEST 
//


package com.wroom.adsservice.soap.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CommentSoap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommentSoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="localId" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="title" type="{http://ftn.com/wroom-agent/xsd}commentTitle"/&gt;
 *         &lt;element name="content" type="{http://ftn.com/wroom-agent/xsd}commentContent"/&gt;
 *         &lt;element name="commentDate" type="{http://ftn.com/wroom-agent/xsd}date"/&gt;
 *         &lt;element name="deleted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="approved" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="rate" type="{http://ftn.com/wroom-agent/xsd}rate"/&gt;
 *         &lt;element name="replyId" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="reply" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="adId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="clientId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="clientUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommentSoap", propOrder = {
    "id",
    "localId",
    "title",
    "content",
    "commentDate",
    "deleted",
    "approved",
    "rate",
    "replyId",
    "reply",
    "adId",
    "clientId",
    "clientUsername"
})
public class CommentSoap {

    protected Long id;
    protected Long localId;
    @XmlElement(required = true)
    protected String title;
    
    @XmlElement(required = true)
    protected String content;
    
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected Date commentDate;
    
    protected boolean deleted;
    
    protected boolean approved;
    
    protected int rate;
    
    protected Long replyId;
    
    protected Boolean reply;
    
    protected long adId;
    
    protected Long clientId;
    
    @XmlElement(required = true)
    protected String clientUsername;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the localId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLocalId() {
        return localId;
    }

    /**
     * Sets the value of the localId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLocalId(Long value) {
        this.localId = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the commentDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * Sets the value of the commentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCommentDate(Date value) {
        this.commentDate = value;
    }

    /**
     * Gets the value of the deleted property.
     * 
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the value of the deleted property.
     * 
     */
    public void setDeleted(boolean value) {
        this.deleted = value;
    }

    /**
     * Gets the value of the approved property.
     * 
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Sets the value of the approved property.
     * 
     */
    public void setApproved(boolean value) {
        this.approved = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     */
    public int getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     */
    public void setRate(int value) {
        this.rate = value;
    }

    /**
     * Gets the value of the replyId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Long getReplyId() {
        return replyId;
    }

    /**
     * Sets the value of the replyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReplyId(Long value) {
        this.replyId = value;
    }

    /**
     * Gets the value of the reply property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReply() {
        return reply;
    }

    /**
     * Sets the value of the reply property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReply(Boolean value) {
        this.reply = value;
    }

    /**
     * Gets the value of the adId property.
     * 
     */
    public long getAdId() {
        return adId;
    }

    /**
     * Sets the value of the adId property.
     * 
     */
    public void setAdId(long value) {
        this.adId = value;
    }

    /**
     * Gets the value of the clientId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setClientId(Long value) {
        this.clientId = value;
    }

    /**
     * Gets the value of the clientUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientUsername() {
        return clientUsername;
    }

    /**
     * Sets the value of the clientUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientUsername(String value) {
        this.clientUsername = value;
    }

}
