package com.coeui.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.compute.Compute;
import com.google.api.services.compute.model.InstanceGroupAggregatedList;
import com.google.api.services.compute.model.InstanceGroupsScopedList;

@Service
public class GoogleZoneFinder {

	private static final String PROJECT_ID = "sapient-si-dsst-184990";

	Logger logger = LoggerFactory.getLogger(this.getClass());	


	public ArrayList<String> printInstances() {
		logger.info("GoogleZoneFinder.....................................");
		Compute computeService = null;	
		 ArrayList<String> list=new ArrayList<String>();
		try {
			computeService = createComputeService();
			Compute.InstanceGroups.AggregatedList request = computeService.instanceGroups().aggregatedList(PROJECT_ID);		
			InstanceGroupAggregatedList response;
			do {
				response = request.execute();	
				if (response.getItems() == null) {
					continue;
				}
				for (Map.Entry<String, InstanceGroupsScopedList> item : response.getItems().entrySet()) {
				    if(item.getValue().keySet().contains("instanceGroups")){
		        		list.add(item.getKey().split("/")[1]);
		        	}
				}
				request.setPageToken(response.getNextPageToken());
			} while (response.getNextPageToken() != null);

		} catch (GeneralSecurityException | IOException e) {
			logger.error("GoogleZoneFinder error......{}",e.getMessage());
		}		
		return list;
	}

	public Compute createComputeService() throws GeneralSecurityException, IOException {
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();	    
	    GoogleCredential credential = GoogleCredential.getApplicationDefault();	    
	    if (credential.createScopedRequired()) {
	      credential =
	          credential.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
	    }
	    return new Compute.Builder(httpTransport, jsonFactory, credential)
	        .setApplicationName("Google-ComputeSample/0.1")
	        .build();

	}
}
