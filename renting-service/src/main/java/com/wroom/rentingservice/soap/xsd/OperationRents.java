//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.20 at 11:44:27 AM CEST 
//


package com.wroom.rentingservice.soap.xsd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operationRents.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="operationRents"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="OCCUPY"/&gt;
 *     &lt;enumeration value="ACCEPT"/&gt;
 *     &lt;enumeration value="DECLINE"/&gt;
 *     &lt;enumeration value="COMPLETE"/&gt;
 *     &lt;enumeration value="PAY"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "operationRents")
@XmlEnum
public enum OperationRents {

    OCCUPY,
    ACCEPT,
    DECLINE,
    COMPLETE,
    PAY;

    public String value() {
        return name();
    }

    public static OperationRents fromValue(String v) {
        return valueOf(v);
    }

}
