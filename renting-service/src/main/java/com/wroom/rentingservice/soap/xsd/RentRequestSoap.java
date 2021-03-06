//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.22 at 03:06:37 AM CEST 
//


package com.wroom.rentingservice.soap.xsd;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for RentRequestSoap complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="RentRequestSoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="localId" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="requestedUserId" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://ftn.com/wroom-agent/xsd}status"/&gt;
 *         &lt;element name="fromDate" type="{http://ftn.com/wroom-agent/xsd}date"/&gt;
 *         &lt;element name="toDate" type="{http://ftn.com/wroom-agent/xsd}date"/&gt;
 *         &lt;element name="requestedUserUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ad" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="rentReport" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="bundle" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="ownerUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RentRequestSoap", propOrder = {
        "id",
        "localId",
        "requestedUserId",
        "status",
        "fromDate",
        "toDate",
        "requestedUserUsername",
        "ad",
        "rentReport",
        "bundle",
        "ownerUsername"
})
public class RentRequestSoap {

    protected Long id;
    protected Long localId;
    protected Long requestedUserId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Status status;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected Date fromDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected Date toDate;
    @XmlElement(required = true)
    protected String requestedUserUsername;
    protected Long ad;
    protected Long rentReport;
    protected Long bundle;
    @XmlElement(required = true)
    protected String ownerUsername;

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
     * Gets the value of the requestedUserId property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getRequestedUserId() {
        return requestedUserId;
    }

    /**
     * Sets the value of the requestedUserId property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setRequestedUserId(Long value) {
        this.requestedUserId = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return
     *     possible object is
     *     {@link Status }
     *
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value
     *     allowed object is
     *     {@link Status }
     *
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the fromDate property.
     *
     * @return
     *     possible object is
     *     {@link Date }
     *
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Sets the value of the fromDate property.
     *
     * @param value
     *     allowed object is
     *     {@link Date }
     *
     */
    public void setFromDate(Date value) {
        this.fromDate = value;
    }

    /**
     * Gets the value of the toDate property.
     *
     * @return
     *     possible object is
     *     {@link Date }
     *
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * Sets the value of the toDate property.
     *
     * @param value
     *     allowed object is
     *     {@link Date }
     *
     */
    public void setToDate(Date value) {
        this.toDate = value;
    }

    /**
     * Gets the value of the requestedUserUsername property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRequestedUserUsername() {
        return requestedUserUsername;
    }

    /**
     * Sets the value of the requestedUserUsername property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRequestedUserUsername(String value) {
        this.requestedUserUsername = value;
    }

    /**
     * Gets the value of the ad property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getAd() {
        return ad;
    }

    /**
     * Sets the value of the ad property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setAd(Long value) {
        this.ad = value;
    }

    /**
     * Gets the value of the rentReport property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getRentReport() {
        return rentReport;
    }

    /**
     * Sets the value of the rentReport property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setRentReport(Long value) {
        this.rentReport = value;
    }

    /**
     * Gets the value of the bundle property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getBundle() {
        return bundle;
    }

    /**
     * Sets the value of the bundle property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setBundle(Long value) {
        this.bundle = value;
    }

    /**
     * Gets the value of the ownerUsername property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Sets the value of the ownerUsername property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOwnerUsername(String value) {
        this.ownerUsername = value;
    }

}