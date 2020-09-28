
package org.crudservlet.integration.requests;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for CancelRequestRqType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CancelRequestRqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestUUID" type="{http://ru.crud.requests}UUID36Type"/>
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */

@XmlRootElement(name = "CancelRequestRq", namespace = "http://ru.crud.requests")

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelRequestRqType", namespace = "http://ru.crud.requests", propOrder = {
        "requestUUID",
        "comment"
})
public class CancelRequestRqType {

    @XmlElement(name = "RequestUUID", required = true)
    protected String requestUUID;
    @XmlElement(name = "Comment", required = true)
    protected String comment;

    /**
     * Gets the value of the requestUUID property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRequestUUID() {
        return requestUUID;
    }

    /**
     * Sets the value of the requestUUID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRequestUUID(String value) {
        this.requestUUID = value;
    }

    /**
     * Gets the value of the comment property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setComment(String value) {
        this.comment = value;
    }

}
