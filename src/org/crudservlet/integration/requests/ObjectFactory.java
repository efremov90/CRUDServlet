
package org.crudservlet.integration.requests;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.crudservlet.integration.requests package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CancelRequestRq_QNAME = new QName("http://ru.crud.requests", "CancelRequestRq");
    private final static QName _CreateRequestRs_QNAME = new QName("http://ru.crud.requests", "CreateRequestRs");
    private final static QName _CancelRequestRs_QNAME = new QName("http://ru.crud.requests", "CancelRequestRs");
    private final static QName _CreateRequestRq_QNAME = new QName("http://ru.crud.requests", "CreateRequestRq");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.crudservlet.integration.requests
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateRequestRqType }
     * 
     */
    public CreateRequestRqType createCreateRequestRqType() {
        return new CreateRequestRqType();
    }

    /**
     * Create an instance of {@link ResultType }
     * 
     */
    public ResultType createResultType() {
        return new ResultType();
    }

    /**
     * Create an instance of {@link CancelRequestRqType }
     * 
     */
    public CancelRequestRqType createCancelRequestRqType() {
        return new CancelRequestRqType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelRequestRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ru.crud.requests", name = "CancelRequestRq")
    public JAXBElement<CancelRequestRqType> createCancelRequestRq(CancelRequestRqType value) {
        return new JAXBElement<CancelRequestRqType>(_CancelRequestRq_QNAME, CancelRequestRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ru.crud.requests", name = "CreateRequestRs")
    public JAXBElement<ResultType> createCreateRequestRs(ResultType value) {
        return new JAXBElement<ResultType>(_CreateRequestRs_QNAME, ResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ru.crud.requests", name = "CancelRequestRs")
    public JAXBElement<ResultType> createCancelRequestRs(ResultType value) {
        return new JAXBElement<ResultType>(_CancelRequestRs_QNAME, ResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRequestRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ru.crud.requests", name = "CreateRequestRq")
    public JAXBElement<CreateRequestRqType> createCreateRequestRq(CreateRequestRqType value) {
        return new JAXBElement<CreateRequestRqType>(_CreateRequestRq_QNAME, CreateRequestRqType.class, null, value);
    }

}
