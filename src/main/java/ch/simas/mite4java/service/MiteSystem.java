package ch.simas.mite4java.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ch.simas.mite4java.data.Customer;
import ch.simas.mite4java.data.CustomerData;
import ch.simas.mite4java.data.FieldInfo;
import ch.simas.mite4java.data.Project;
import ch.simas.mite4java.data.ProjectData;
import ch.simas.mite4java.data.Service;
import ch.simas.mite4java.data.ServiceData;
import ch.simas.mite4java.data.UserInfo;
import ch.simas.mite4java.utils.Getconnection;

public class MiteSystem {

	public Map<String, Object> getMiteAllMainInfoMap(String subDomain, String apiKey, Map<String, Object> map) {

		ProjectData projectListData = getMiteProjectList(subDomain, apiKey);
		
		ServiceData serviceListData = getMiteServiceList(subDomain, apiKey);
		
		CustomerData customerListData = getMiteCustomerList(subDomain, apiKey);

		if(projectListData!=null)
		   map.put("projectData", projectListData.getProjectList());
		if(serviceListData!=null)
		   map.put("serviceData", serviceListData.getServiceList());
		if(customerListData!=null)
		   map.put("customerData", customerListData.getCustomerList());

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

	public ProjectData getMiteProjectList(String subDomain, String apiKey) {

		ProjectData projectListData = null;

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
	
	public ServiceData getMiteServiceList(String subDomain, String apiKey) {

		ServiceData serviceListData = null;

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/services.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		try {
			URL serverAddress = new URL(TARGET_URL);

			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
			connection.connect();
			int rc = connection.getResponseCode();
			if (rc == 200) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));

				JAXBContext jaxbContext = JAXBContext.newInstance(ServiceData.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				serviceListData = (ServiceData) jaxbUnmarshaller.unmarshal(br);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serviceListData;
	}
	
	
	public CustomerData getMiteCustomerList(String subDomain, String apiKey) {

		CustomerData customerListData = null;

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/customers.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		try {
			URL serverAddress = new URL(TARGET_URL);

			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
			connection.connect();
			int rc = connection.getResponseCode();
			if (rc == 200) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));

				JAXBContext jaxbContext = JAXBContext.newInstance(CustomerData.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				customerListData = (CustomerData) jaxbUnmarshaller.unmarshal(br);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerListData;
	}

	public String getProjectXmlResponse(String subDomain, String apiKey) {
		String serverResp = "";

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
				serverResp = readAll(br);
				System.out.println("Response:" + serverResp);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serverResp;
	}
	
	

	public String getServiceXmlResponse(String subDomain, String apiKey) {
		String serverResp = "";

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/services.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		try {

			URL serverAddress = new URL(TARGET_URL);

			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
			connection.connect();
			int rc = connection.getResponseCode();
			if (rc == 200) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
				serverResp = readAll(br);
				System.out.println("Response:" + serverResp);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serverResp;
	}

	public String getCustomerXmlResponse(String subDomain, String apiKey) {
		String serverResp = "";

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/customers.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		try {

			URL serverAddress = new URL(TARGET_URL);

			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
			connection.connect();
			int rc = connection.getResponseCode();
			if (rc == 200) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
				serverResp = readAll(br);
				System.out.println("Response:" + serverResp);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return serverResp;
	}

	public String postProjectXmlResponse(String subDomain, String apiKey, Project proj) {
		String serverResp = "";

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/projects.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		JAXBContext jaxbContext;

		Marshaller jaxbMarshaller;

		StringWriter writer = new StringWriter();

		try {

			jaxbContext = JAXBContext.newInstance(Project.class);

			jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(proj, writer);

			jaxbMarshaller.marshal(proj, System.out);

		} catch (JAXBException e) {

			e.printStackTrace();
		}

		String xmlData = writer.toString();

		serverResp = excutePost(TARGET_URL, xmlData);

		return serverResp;
	}

	public String postServiceXmlResponse(String subDomain, String apiKey, Service serv) {
		String serverResp = "";

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/services.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		JAXBContext jaxbContext;

		Marshaller jaxbMarshaller;

		StringWriter writer = new StringWriter();

		try {

			jaxbContext = JAXBContext.newInstance(Service.class);

			jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(serv, writer);

			jaxbMarshaller.marshal(serv, System.out);

		} catch (JAXBException e) {

			e.printStackTrace();
		}

		String xmlData = writer.toString();

		serverResp = excutePost(TARGET_URL, xmlData);

		return serverResp;
	}

	public String postCustomerXmlResponse(String subDomain, String apiKey, Customer cust) {
		String serverResp = "";

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/customers.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		JAXBContext jaxbContext;

		Marshaller jaxbMarshaller;

		StringWriter writer = new StringWriter();

		try {

			jaxbContext = JAXBContext.newInstance(Customer.class);

			jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(cust, writer);

			jaxbMarshaller.marshal(cust, System.out);

		} catch (JAXBException e) {

			e.printStackTrace();
		}

		String xmlData = writer.toString();

		serverResp = excutePost(TARGET_URL, xmlData);

		return serverResp;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static String excutePost(String targetURL, String xmlData) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml");

			connection.setRequestProperty("Content-Length", "" + Integer.toString(xmlData.length()));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(xmlData);
			wr.flush();
			wr.close();

			// Get Response
			String serverResp = "";
			int rc = connection.getResponseCode();
			if (rc == 201) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
				serverResp = readAll(br);
				System.out.println("Response:" + serverResp);
			} else {
				System.out.println("HTTP error:" + rc);
			}
			return serverResp.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
