//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.26 at 08:53:55 PM CEST 
//


package xwsagent.wroomagent.soap.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;


/**
 * <p>Java class for RentReportSoap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RentReportSoap"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="localId" type="{http://ftn.com/wroom-agent/xsd}id" minOccurs="0"/&gt;
 *         &lt;element name="date" type="{http://ftn.com/wroom-agent/xsd}date"/&gt;
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="traveledMiles" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RentReportSoap", propOrder = {
    "id",
    "localId",
    "date",
    "note",
    "traveledMiles"
})
public class RentReportSoap {

    protected Long id;
    protected Long localId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected Date date;
    protected String note;
    protected Double traveledMiles;

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
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the traveledMiles property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTraveledMiles() {
        return traveledMiles;
    }

    /**
     * Sets the value of the traveledMiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTraveledMiles(Double value) {
        this.traveledMiles = value;
    }

}