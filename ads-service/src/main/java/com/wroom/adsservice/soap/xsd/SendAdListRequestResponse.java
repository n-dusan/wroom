//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.07 at 10:12:00 PM CEST 
//


package com.wroom.adsservice.soap.xsd;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ad" type="{http://ftn.com/wroom-agent/xsd}AdSoap" maxOccurs="unbounded"/&gt;
 *         &lt;element name="companyEmail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "ad",
    "companyEmail"
})
@XmlRootElement(name = "SendAdListRequestResponse")
public class SendAdListRequestResponse {

    @XmlElement(required = true)
    protected List<AdSoap> ad;
    @XmlElement(required = true)
    protected String companyEmail;

    /**
     * Gets the value of the ad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdSoap }
     * 
     * 
     */
    public List<AdSoap> getAd() {
        if (ad == null) {
            ad = new ArrayList<AdSoap>();
        }
        return this.ad;
    }

    public void setAd(List<AdSoap> ads) {
        this.ad = ads;
    }

    /**
     * Gets the value of the companyEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyEmail() {
        return companyEmail;
    }

    /**
     * Sets the value of the companyEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyEmail(String value) {
        this.companyEmail = value;
    }

}
