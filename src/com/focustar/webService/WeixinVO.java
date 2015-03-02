package com.focustar.webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for weixinVO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;weixinVO&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element name=&quot;imei&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}string&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "weixinVO", propOrder = { "imei" })
public class WeixinVO {

	protected String imei;

	/**
	 * Gets the value of the imei property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getImei() {
		return imei;
	}

	/**
	 * Sets the value of the imei property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setImei(String value) {
		this.imei = value;
	}

}
