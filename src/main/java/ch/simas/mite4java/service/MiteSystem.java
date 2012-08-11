package ch.simas.mite4java.service;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import ch.simas.mite4java.data.FieldInfo;
import ch.simas.mite4java.data.ProjectData;
import ch.simas.mite4java.data.UserInfo;
import ch.simas.mite4java.utils.Getconnection;

public class MiteSystem {
	
	public Map<String, Object> getMiteRootInfoMap(String subDomain,String apiKey, Map<String, Object> map){
		
		ProjectData projectListData=getMiteProjectList(subDomain,apiKey);
		
		map.put("projectData", projectListData.getProjectList());
		
		ArrayList<FieldInfo> fielList = new ArrayList<FieldInfo>();

		FieldInfo fieldData1 = new FieldInfo();
		fieldData1.setFieldId(1);
		fieldData1.setFieldName("Project");
		fielList.add(fieldData1);

		FieldInfo fieldData2 = new FieldInfo();
		fieldData2.setFieldId(2);
		fieldData2.setFieldName("Service");
		fielList.add(fieldData2);

		FieldInfo fieldData3 = new FieldInfo();
		fieldData3.setFieldId(3);
		fieldData3.setFieldName("Customer");
		fielList.add(fieldData3);

		FieldInfo fieldData4 = new FieldInfo();
		fieldData4.setFieldId(4);
		fieldData4.setFieldName("User");
		fielList.add(fieldData4);

		FieldInfo fieldData5 = new FieldInfo();
		fieldData5.setFieldId(5);
		fieldData5.setFieldName("Hour");
		fielList.add(fieldData5);

		FieldInfo fieldData6 = new FieldInfo();
		fieldData6.setFieldId(6);
		fieldData6.setFieldName("Update Date");
		fielList.add(fieldData6);

		map.put("userInfo", new UserInfo());
		map.put("fieldList", fielList);
		
		return map;
	}
	
	
	
	
	
	
	
	public ProjectData getMiteProjectList(String subDomain,String apiKey){
		
		ProjectData projectListData=null;
		
		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/projects.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		try {
			URL serverAddress = new URL(TARGET_URL);

			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
			connection.connect();
			int rc = connection.getResponseCode();
			if (rc == 200) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
				
				JAXBContext jaxbContext = JAXBContext.newInstance(ProjectData.class);

			    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				projectListData = (ProjectData) jaxbUnmarshaller.unmarshal(br);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return projectListData;
	}

}
