//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.27 at 03:52:40 AM CEST 
//


package xwsagent.wroomagent.soap.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="requestLocalId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="rentReport" type="{http://ftn.com/wroom-agent/xsd}RentReportSoap"/&gt;
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
    "requestLocalId",
    "rentReport"
})
@XmlRootElement(name = "RentReportSoapRequestResponse")
public class RentReportSoapRequestResponse {

    protected Long requestLocalId;
    @XmlElement(required = true)
    protected RentReportSoap rentReport;

    /**
     * Gets the value of the requestLocalId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRequestLocalId() {
        return requestLocalId;
    }

    /**
     * Sets the value of the requestLocalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRequestLocalId(Long value) {
        this.requestLocalId = value;
    }

    /**
     * Gets the value of the rentReport property.
     * 
     * @return
     *     possible object is
     *     {@link RentReportSoap }
     *     
     */
    public RentReportSoap getRentReport() {
        return rentReport;
    }

    /**
     * Sets the value of the rentReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link RentReportSoap }
     *     
     */
    public void setRentReport(RentReportSoap value) {
        this.rentReport = value;
    }

}
