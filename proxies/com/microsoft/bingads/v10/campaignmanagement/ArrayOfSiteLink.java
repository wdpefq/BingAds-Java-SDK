
package com.microsoft.bingads.v10.campaignmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfSiteLink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSiteLink">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SiteLink" type="{https://bingads.microsoft.com/CampaignManagement/v10}SiteLink" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSiteLink", propOrder = {
    "siteLinks"
})
public class ArrayOfSiteLink {

    @XmlElement(name = "SiteLink", nillable = true)
    protected List<SiteLink> siteLinks;

    /**
     * Gets the value of the siteLinks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the siteLinks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSiteLinks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SiteLink }
     * 
     * 
     */
    public List<SiteLink> getSiteLinks() {
        if (siteLinks == null) {
            siteLinks = new ArrayList<SiteLink>();
        }
        return this.siteLinks;
    }

}
