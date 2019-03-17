package com.coeui.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coeui.model.CountryInfo;

@Service("countryService")
public class CountryService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private GoogleZoneFinder googleZoneFinder;
	
	private static final Map<String, CountryInfo> map = new HashMap<String, CountryInfo>();
	
	

	static {		   
		map.put("northamerica-northeast1", new CountryInfo("Montréal, Canada","https://upload.wikimedia.org/wikipedia/commons/d/d9/Flag_of_Canada_%28Pantone%29.svg"));
		map.put("us-central1", new CountryInfo("Council Bluffs, Iowa, USA","https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg"));
		map.put("us-west1", new CountryInfo("The Dalles, Oregon, USA","https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg"));
		map.put("us-east4", new CountryInfo("Ashburn, Virginia, USA","https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg"));
		map.put("us-east1", new CountryInfo("Moncks Corner, South Carolina, USA","https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg"));
		map.put("southamerica-east1", new CountryInfo("São Paulo, Brazil", "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg"));
		map.put("europe-west1", new CountryInfo("St. Ghislain, Belgium", "https://upload.wikimedia.org/wikipedia/commons/6/65/Flag_of_Belgium.svg"));
		map.put("europe-west2", new CountryInfo("London, U.K.", "https://upload.wikimedia.org/wikipedia/en/a/ae/Flag_of_the_United_Kingdom.svg"));
		map.put("europe-west3",new CountryInfo("Frankfurt, Germany", "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"));
		map.put("europe-west4", new CountryInfo("Eemshaven, Netherlands","https://upload.wikimedia.org/wikipedia/commons/2/20/Flag_of_the_Netherlands.svg"));
		map.put("europe-north1", new CountryInfo("Zurich, Finland","https://upload.wikimedia.org/wikipedia/commons/b/bc/Flag_of_Finland.svg"));
		map.put("asia-south1", new CountryInfo("Mumbai, India", "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg"));
		map.put("asia-southeast1", new CountryInfo("Jurong West, Singapore", "https://upload.wikimedia.org/wikipedia/commons/4/48/Flag_of_Singapore.svg"));
		map.put("asia-east1", new CountryInfo("Changhua County, Taiwan","https://upload.wikimedia.org/wikipedia/commons/7/72/Flag_of_the_Republic_of_China.svg"));
		map.put("asia-northeast1", new CountryInfo("Tokyo, Japan", "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg"));
		map.put("australia-southeast1", new CountryInfo("Sydney, Australia", "https://upload.wikimedia.org/wikipedia/en/b/b9/Flag_of_Australia.svg"));
			
	 };
	
	
	
	
	public List<CountryInfo> getCountryInfo(){	
		
		ArrayList<CountryInfo> list=new ArrayList<>();
		
		String flag=null;
		
		CountryInfo countryInfo =null;
		
		ArrayList<String> deployedZoneList =googleZoneFinder.printInstances();	
		
		
		for (String location : deployedZoneList) {
			
			
			logger.info("location.....................................{}",location);
			
			flag=location.substring(0, location.lastIndexOf('-'));
			
			countryInfo=map.get(flag);
			
			logger.info("countryInfo.....................................{}",countryInfo);
			
			list.add(countryInfo);
		}
		
		logger.info("getCountryInfo list size.....................................{}",list.size());
	
		return list;
	}
}
