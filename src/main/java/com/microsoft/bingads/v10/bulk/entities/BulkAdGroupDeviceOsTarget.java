package com.microsoft.bingads.v10.bulk.entities;

import com.microsoft.bingads.v10.bulk.BulkServiceManager;
import com.microsoft.bingads.v10.bulk.BulkFileReader;
import com.microsoft.bingads.v10.bulk.BulkFileWriter;
import com.microsoft.bingads.v10.bulk.BulkOperation;

/**
 * Represents a device OS target that is associated with an ad group.
 *
 * This class exposes the {@link BulkDeviceOsTarget#setDeviceOsTarget} and {@link BulkDeviceOsTarget#getDeviceOsTarget}
 * methods that can be used to read and write fields of the Ad Group DeviceOS Target record in a bulk file.
 *
 * <p>
 *     One {@link BulkAdGroupDeviceOsTarget} exposes a read only list of {@link BulkAdGroupDeviceOsTargetBid}.
 *     Each {@link BulkAdGroupDeviceOsTargetBid} instance corresponds to one Ad Group DeviceOS Target record in the bulk file.
 *     If you upload a {@link BulkAdGroupDeviceOsTarget},
 *     then you are effectively replacing any existing bids for the corresponding device OS target.
 * </p>
 *
 * <p>
 *     For more information, see Ad Group DeviceOS Target at
 *     <a href="http://go.microsoft.com/fwlink/?LinkID=620259">http://go.microsoft.com/fwlink/?LinkID=620259</a>.
 * </p>
 *
 * @see BulkServiceManager
 * @see BulkOperation
 * @see BulkFileReader
 * @see BulkFileWriter
 */
public class BulkAdGroupDeviceOsTarget extends BulkDeviceOsTarget<BulkAdGroupDeviceOsTargetBid> {

    /**
     * Initializes a new instanced of the BulkAdGroupDeviceOsTarget class.
     */
    public BulkAdGroupDeviceOsTarget() {
        super(BulkAdGroupDeviceOsTargetBid.class);
    }

    /**
     * Gets the identifier of the ad group that the target is associated.
     *
     * <p>
     *     Corresponds to the 'Parent Id' field in the bulk file.
     * </p>
     */
    public Long getAdGroupId() {
        return getEntityId();
    }

    /**
     * Sets the identifier of the ad group that the target is associated.
     *
     * <p>
     *     Corresponds to the 'Parent Id' field in the bulk file.
     * </p>
     */
    public void setAdGroupId(Long adGroupId) {
        setEntityId(adGroupId);
    }

    /**
     * Gets the name of the ad group that the target is associated.
     *
     * <p>
     *     Corresponds to the 'Ad Group' field in the bulk file.
     * </p>
     */
    public String getAdGroupName() {
        return getEntityName();
    }

    /**
     * Sets the name of the ad group that the target is associated.
     *
     * <p>
     *     Corresponds to the 'Ad Group' field in the bulk file.
     * </p>
     */
    public void setAdGroupName(String adGroupName) {
        setEntityName(adGroupName);
    }

    /**
     * Gets the name of the campaign that target is associated.
     *
     * <p>
     *     Corresponds to the 'Campaign' field in the bulk file.
     * </p>
     */
    public String getCampaignName() {
        return getParentEntityName();
    }

    /**
     * Sets the name of the campaign that target is associated.
     *
     * <p>
     *     Corresponds to the 'Campaign' field in the bulk file.
     * </p>
     */
    public void setCampaignName(String campaignName) {
        setParentEntityName(campaignName);
    }

    /**
     * Reserved for internal use.
     */
    @Override
    BulkAdGroupDeviceOsTargetBid createBid() {
        return new BulkAdGroupDeviceOsTargetBid();
    }    
}
