package com.focustar.webService;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the com.focustar.webService package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _CheckImeiResponse_QNAME = new QName("http://service.web.dc.gionee.com/", "checkImeiResponse");
	private final static QName _CheckImei_QNAME = new QName("http://service.web.dc.gionee.com/", "checkImei");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: com.focustar.webService
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link WeixinVO }
	 * 
	 */
	public WeixinVO createWeixinVO() {
		return new WeixinVO();
	}

	/**
	 * Create an instance of {@link CheckImeiResponse }
	 * 
	 */
	public CheckImeiResponse createCheckImeiResponse() {
		return new CheckImeiResponse();
	}

	/**
	 * Create an instance of {@link CheckImei }
	 * 
	 */
	public CheckImei createCheckImei() {
		return new CheckImei();
	}

	/**
	 * Create an instance of {@link AuthInfo }
	 * 
	 */
	public AuthInfo createAuthInfo() {
		return new AuthInfo();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link CheckImeiResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.web.dc.gionee.com/", name = "checkImeiResponse")
	public JAXBElement<CheckImeiResponse> createCheckImeiResponse(CheckImeiResponse value) {
		return new JAXBElement<CheckImeiResponse>(_CheckImeiResponse_QNAME, CheckImeiResponse.class, null, value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}{@link CheckImei }
	 * {@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://service.web.dc.gionee.com/", name = "checkImei")
	public JAXBElement<CheckImei> createCheckImei(CheckImei value) {
		return new JAXBElement<CheckImei>(_CheckImei_QNAME, CheckImei.class, null, value);
	}

}
