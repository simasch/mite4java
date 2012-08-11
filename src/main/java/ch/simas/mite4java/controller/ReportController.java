package ch.simas.mite4java.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.simas.mite4java.data.Customer;
import ch.simas.mite4java.data.Project;
import ch.simas.mite4java.data.Service;
import ch.simas.mite4java.data.UserInfo;
import ch.simas.mite4java.service.MiteSystem;
import ch.simas.mite4java.service.XlsxExpoter;
import ch.simas.mite4java.utils.Getconnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Controller
public class ReportController {

	@RequestMapping("/getprojects")
	public String getProjects(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

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
				String serverResp = readAll(br);
				System.out.println("Response:" + serverResp);

				response.setContentType("text/xml");
				response.getOutputStream().write(((serverResp)).getBytes());
				response.getOutputStream().flush();

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping("/getservices")
	public String getServices(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

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
				String serverResp = readAll(br);
				System.out.println("Response:" + serverResp);

				response.setContentType("text/xml");
				response.getOutputStream().write(((serverResp)).getBytes());
				response.getOutputStream().flush();

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping("/getcustomers")
	public String getCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

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
				String serverResp = readAll(br);
				System.out.println("Response:" + serverResp);

				response.setContentType("text/xml");
				response.getOutputStream().write(((serverResp)).getBytes());
				response.getOutputStream().flush();

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping("/getreport")
	public String getReport(Map<String, Object> map) {

		
		return "connectreport";
	}

	
	
	@RequestMapping("/getselectedreportentrytxls")
	public String exportReportxlsx(HttpServletRequest request, HttpServletResponse response,Map<String, Object> map) {
		
		String apiKey = request.getParameter("miteApiKey");
		
		String subDomain = request.getParameter("subDomainName");
		
		MiteSystem miteSystem=new MiteSystem();
		
		map=miteSystem.getMiteRootInfoMap(subDomain, apiKey,map);

		return "exporttimeentryxlsx";
	}

	@RequestMapping(value = "/gettimeentryselectedfilelds", method = RequestMethod.POST)
	public String getTimeEntrySelectedFields(HttpServletRequest request, @ModelAttribute("userInfo") UserInfo userInfo, BindingResult result) {

		return "redirect:/gettimeentries?subdomain=" + userInfo.getSubDomainName() + "&apikey=" + userInfo.getMiteApiKey() + "&selectedfields=" + userInfo.getSelectedFields();

	}

	@RequestMapping("/gettimeentries")
	public String getTimeEntries(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");
		String selectedFields = request.getParameter("selectedfields");
		
		OutputStream out = response.getOutputStream();
		
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=TimeEntryList.xlsx");// fileName);

		XlsxExpoter xlsxExporter=new XlsxExpoter();
		
		xlsxExporter.exportTimeEntryData(subDomain, apiKey,selectedFields,out);

		response.getOutputStream().flush();

		return null;

	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	@RequestMapping("/postcustomers")
	public String postCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/customers.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		Customer cust = new Customer();

		cust.setName("Moodster Inc.");
		cust.setNote("Shilly-shally!");
		cust.setArchived(false);
		cust.setHourlyRate(40);

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

		xmlData.replace("</hourlyRate>", "</hourly-rate>");

		String serverResp = excutePost(TARGET_URL, xmlData);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/postprojects")
	public String postProjects(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/projects.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		Project proj = new Project();

		proj.setName("Relaunch microsite");
		proj.setNote("abcd");
		proj.setArchived(false);
		proj.setHourlyRate(65);
		proj.setCustomerId(1);
		proj.setBudget(250);

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

		String serverResp = excutePost(TARGET_URL, xmlData);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/postservices")
	public String postServices(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/services.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		Service serv = new Service();

		serv.setName("Coding3");
		serv.setNote("ABCD");
		serv.setArchived(false);
		serv.setHourlyRate(400);

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

		String serverResp = excutePost(TARGET_URL, xmlData);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

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
