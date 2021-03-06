package com.microsoft.bingads.examples.v10;

import java.net.*;
import java.io.*;
import java.rmi.*;
import java.util.Calendar;
import java.util.TimeZone;

import com.microsoft.bingads.*;
import com.microsoft.bingads.v10.campaignmanagement.*;

/**
 * This example demonstrates how to download the comma separated value (CSV) file that contains geographical location information 
 * that can be used with Bing Ads location targeting.
 */
    
public class GeographicalLocations extends ExampleBase {
    
    static AuthorizationData authorizationData;
    static ServiceClient<ICampaignManagementService> CampaignService; 

    // The full path to the geographical locations file.

    private static final java.lang.String LOCAL_FILE= "c:\\geolocations\\geolocations.csv";

    // The language and locale of the geographical locations file available for download.
    // This example uses 'en' (English). Supported locales are 'zh-Hant' (Traditional Chinese), 'en' (English), 'fr' (French), 
    // 'de' (German), 'it' (Italian), 'pt-BR' (Portuguese - Brazil), and 'es' (Spanish). 

    private static final java.lang.String LANGUAGE_LOCALE = "en";
    
    // The only supported file format version is 1.0. 

    private static final java.lang.String VERSION= "1.0";

    public static void main(java.lang.String[] args) {
   	
        try
        {
            authorizationData = new AuthorizationData();
            authorizationData.setDeveloperToken(DeveloperToken);
            authorizationData.setAuthentication(new com.microsoft.bingads.PasswordAuthentication(UserName, Password));
            authorizationData.setCustomerId(CustomerId);
            authorizationData.setAccountId(AccountId);

            CampaignService = new ServiceClient<ICampaignManagementService>(
                    authorizationData,
                    ICampaignManagementService.class);
            
            GetGeoLocationsFileUrlResponse getGeoLocationsFileUrlResponse = getGeoLocationsFileUrl(VERSION, LANGUAGE_LOCALE);

            // Going forward you should track the date and time of the previous download,  
            // and compare it with the last modified time provided by the service.
            Calendar previousSyncTimeUtc = Calendar.getInstance();
            previousSyncTimeUtc.setTimeZone(TimeZone.getTimeZone("GMT"));
            previousSyncTimeUtc.set(2016, 11, 29, 0, 0, 0);

            java.lang.String fileUrl = getGeoLocationsFileUrlResponse.getFileUrl();
            Calendar fileUrlExpiryTimeUtc = getGeoLocationsFileUrlResponse.getFileUrlExpiryTimeUtc();
            Calendar lastModifiedTimeUtc = getGeoLocationsFileUrlResponse.getLastModifiedTimeUtc();

            outputStatusMessage(String.format("FileUrl: %s\n", fileUrl));
            outputStatusMessage(String.format("FileUrlExpiryTimeUtc: %s\n", fileUrlExpiryTimeUtc.getTime().toString()));
            outputStatusMessage(String.format("LastModifiedTimeUtc: %s\n", lastModifiedTimeUtc.getTime().toString()));

            // Download the file if it was modified since the previous download.
            if (lastModifiedTimeUtc.compareTo(previousSyncTimeUtc) < 0)
            {
                downloadFile(fileUrl, LOCAL_FILE);
            }
            
        // Campaign Management service operations can throw AdApiFaultDetail.
        } catch (com.microsoft.bingads.v10.campaignmanagement.AdApiFaultDetail_Exception ex) {
            outputStatusMessage("The operation failed with the following faults:\n");

            for (com.microsoft.bingads.v10.campaignmanagement.AdApiError error : ex.getFaultInfo().getErrors().getAdApiErrors())
            {
                outputStatusMessage("AdApiError\n");
                outputStatusMessage(String.format("Code: %d\nError Code: %s\nMessage: %s\n\n", 
                                error.getCode(), error.getErrorCode(), error.getMessage()));
            }

        // Campaign Management service operations can throw ApiFaultDetail.
        } catch (com.microsoft.bingads.v10.campaignmanagement.ApiFaultDetail_Exception ex) {
            outputStatusMessage("The operation failed with the following faults:\n");

            for (com.microsoft.bingads.v10.campaignmanagement.BatchError error : ex.getFaultInfo().getBatchErrors().getBatchErrors())
            {
                outputStatusMessage(String.format("BatchError at Index: %d\n", error.getIndex()));
                outputStatusMessage(String.format("Code: %d\nMessage: %s\n\n", error.getCode(), error.getMessage()));
            }

            for (com.microsoft.bingads.v10.campaignmanagement.OperationError error : ex.getFaultInfo().getOperationErrors().getOperationErrors())
            {
                outputStatusMessage("OperationError\n");
                outputStatusMessage(String.format("Code: %d\nMessage: %s\n\n", error.getCode(), error.getMessage()));
            }         
        } catch (Exception ex) {
            outputStatusMessage(String.format("Error encountered: %s", ex.getMessage()));
        } 
    }    
        
    // Gets a temporary URL that you can use to download a file that contains the supported geographical location targeting codes.

    static GetGeoLocationsFileUrlResponse getGeoLocationsFileUrl(java.lang.String version, java.lang.String languageLocale) throws RemoteException, Exception
    {
        GetGeoLocationsFileUrlRequest request = new GetGeoLocationsFileUrlRequest();

        request.setLanguageLocale(languageLocale);
        request.setVersion(version);

        return CampaignService.getService().getGeoLocationsFileUrl(request);
    }
    
    // Downloads a file from the remote file URL to a local file URL.
    
    static void downloadFile(java.lang.String fileUrl, java.lang.String localFile) 
    {
        HttpURLConnection connection = null;
        BufferedInputStream reader = null;
        BufferedOutputStream writer = null;
        
        try
        {
            URL url = new URL(fileUrl);
            connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) 
            {
                outputStatusMessage(String.format("Downloaded the geographical locations to %s.\n", localFile));
                reader = new BufferedInputStream(connection.getInputStream());
                writer = new BufferedOutputStream(new FileOutputStream(localFile));

                final int bufferSize = 100 * 1024;
                byte[] buffer = new byte[bufferSize];
                int count = 0;            

                while ((count = reader.read(buffer)) != -1)
                {
                    writer.write(buffer, 0, count);
                }
            } 
            else
            {
                outputStatusMessage(String.format("HTTP Response Code: %s\n", connection.getResponseCode()));  
                outputStatusMessage(String.format("HTTP Response Message: %s\n", connection.getResponseMessage()));  
            } 
        } catch (IOException ex) {
            outputStatusMessage(String.format("IO Exception encountered: %s", ex.getMessage()));
        } catch (Exception ex) {
            outputStatusMessage(String.format("Error encountered: %s", ex.getMessage()));
        } finally {
            try
            {
                if (reader != null) reader.close();
                if (writer != null)
                {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException ex) {
            	outputStatusMessage(String.format("IO Exception encountered: %s", ex.getMessage()));
            }
        }  
    }
}