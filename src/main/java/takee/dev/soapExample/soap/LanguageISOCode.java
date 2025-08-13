
package takee.dev.soapExample.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


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
 *         &lt;element name="sLanguageName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "sLanguageName"
})
@XmlRootElement(name = "LanguageISOCode")
public class LanguageISOCode {

    @XmlElement(required = true)
    protected String sLanguageName;

    /**
     * Gets the value of the sLanguageName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSLanguageName() {
        return sLanguageName;
    }

    /**
     * Sets the value of the sLanguageName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSLanguageName(String value) {
        this.sLanguageName = value;
    }

}
