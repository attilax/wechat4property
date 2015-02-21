package com.focustar.webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for checkImei complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;checkImei&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;arg0&quot; type=&quot;{http://service.web.dc.gionee.com/}authInfo&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element name=&quot;arg1&quot; type=&quot;{http://service.web.dc.gionee.com/}weixinVO&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "checkImei", propOrder = { "arg0", "arg1" })
public class CheckImei {

	protected AuthInfo arg0;
	protected WeixinVO arg1;

	/**
	 * Gets the value of the arg0 property.
	 * 
	 * @return possible object is {@link AuthInfo }
	 * 
	 */
	public AuthInfo getArg0() {
		return arg0;
	}

	/**
	 * Sets the value of the arg0 property.
	 * 
	 * @param value
	 *            allowed object is {@link AuthInfo }
	 * 
	 */
	public void setArg0(AuthInfo value) {
		this.arg0 = value;
	}

	/**
	 * Gets the value of the arg1 property.
	 * 
	 * @return possible object is {@link WeixinVO }
	 * 
	 */
	public WeixinVO getArg1() {
		return arg1;
	}

	/**
	 * Sets the value of the arg1 property.
	 * 
	 * @param value
	 *            allowed object is {@link WeixinVO }
	 * 
	 */
	public void setArg1(WeixinVO value) {
		this.arg1 = value;
	}

}
