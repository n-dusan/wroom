//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.19 at 11:14:48 PM CEST 
//


package com.wroom.vehicleservice.soap.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="vehicle" type="{http://ftn.com/wroom-agent/xsd}VehicleSoap"/&gt;
 *         &lt;element name="operation" type="{http://ftn.com/wroom-agent/xsd}operation"/&gt;
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
    "vehicle",
    "operation"
})
@XmlRootElement(name = "SendVehicleRequest")
public class SendVehicleRequest {

    @XmlElement(required = true)
    protected VehicleSoap vehicle;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Operation operation;

    /**
     * Gets the value of the vehicle property.
     * 
     * @return
     *     possible object is
     *     {@link VehicleSoap }
     *     
     */
    public VehicleSoap getVehicle() {
        return vehicle;
    }

    /**
     * Sets the value of the vehicle property.
     * 
     * @param value
     *     allowed object is
     *     {@link VehicleSoap }
     *     
     */
    public void setVehicle(VehicleSoap value) {
        this.vehicle = value;
    }

    /**
     * Gets the value of the operation property.
     * 
     * @return
     *     possible object is
     *     {@link Operation }
     *     
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * Sets the value of the operation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Operation }
     *     
     */
    public void setOperation(Operation value) {
        this.operation = value;
    }

}