package ch.simas.mite4java.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.simas.mite4java.data.Customer;
import ch.simas.mite4java.data.FieldInfo;
import ch.simas.mite4java.data.Project;
import ch.simas.mite4java.data.Service;
import ch.simas.mite4java.data.TimeEntryData;
import ch.simas.mite4java.data.UserInfo;
import ch.simas.mite4java.utils.Getconnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

@Controller
public class ContactController {

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

	@RequestMapping("/getreportentrytxls")
	public String listContacts(Map<String, Object> map) {

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

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/time_entries.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		try {

			URL serverAddress = new URL(TARGET_URL);

			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
			connection.connect();
			int rc = connection.getResponseCode();
			if (rc == 200) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));

				JAXBContext jaxbContext = JAXBContext.newInstance(TimeEntryData.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				TimeEntryData timeEntryListData = (TimeEntryData) jaxbUnmarshaller.unmarshal(br);

				System.out.println(timeEntryListData.getTimeEntryList().get(0).getProjectName());

				String delims = ";";
				String[] tokens = selectedFields.split(delims);

				for (int i = 0; i < tokens.length; i++)
					System.out.println(tokens[i]);

				Workbook wb = new XSSFWorkbook();

				Sheet sheet = wb.createSheet("Fonts");

				Font font0 = wb.createFont();
				font0.setColor(IndexedColors.BROWN.getIndex());

				CellStyle csl = wb.createCellStyle();
				csl.setWrapText(true);
				csl.setFont(font0);
				csl.setWrapText(true);

				Row row0 = sheet.createRow(0);
				int k = 0;
				String EntryTimeFields[] = { "", "Project", "Service", "Customer", "User", "Hours", "Update Date" };

				if (tokens.length > 1) {
					for (int i = 0; i < tokens.length; i++) {

						Cell cell0 = row0.createCell(k);

						cell0.setCellValue(EntryTimeFields[Integer.valueOf(tokens[i])]);
						cell0.setCellStyle(csl);
						k++;

					}

				} else {

					for (int i = 1; i < EntryTimeFields.length; i++) {

						Cell cell0 = row0.createCell(k);

						cell0.setCellValue(EntryTimeFields[i]);
						cell0.setCellStyle(csl);
						k++;

					}

				}

				int rowsCount = timeEntryListData.getTimeEntryList().size();

				
				for (int i = 1; i <= rowsCount; i++) {

					CellStyle cs = wb.createCellStyle();
					cs.setWrapText(true);

					Row row = sheet.createRow(i);
					k = 0;
					
					if (tokens.length > 1) {

						for (int j = 0; j < tokens.length; j++) {

							if (Integer.valueOf(tokens[j].toString()) == 1) {
								Cell cell11 = row.createCell(k);
								cell11.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getProjectName());
								cell11.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 2) {
								Cell cell12 = row.createCell(k);
								cell12.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getServiceName());
								cell12.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 3) {
								Cell cell13 = row.createCell(k);
								cell13.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getCustomerName());
								cell13.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 4) {
								Cell cell14 = row.createCell(k);
								cell14.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUserName());
								cell14.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 5) {
								Cell cell15 = row.createCell(k);
								double hours = timeEntryListData.getTimeEntryList().get(i - 1).getMinutes() / 60.00;
								cell15.setCellValue(hours);
								cell15.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 6) {
								Cell cell16 = row.createCell(k);
								cell16.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUpdatedAt());
								cell16.setCellStyle(cs);
								k++;
							}

						}

					} else {

						Cell cell11 = row.createCell(0);
						cell11.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getProjectName());
						cell11.setCellStyle(cs);

						Cell cell12 = row.createCell(1);
						cell12.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getServiceName());
						cell12.setCellStyle(cs);

						Cell cell13 = row.createCell(2);
						cell13.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getCustomerName());
						cell13.setCellStyle(cs);

						Cell cell14 = row.createCell(3);
						cell14.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUserName());
						cell14.setCellStyle(cs);

						Cell cell15 = row.createCell(4);
						double hours = timeEntryListData.getTimeEntryList().get(i - 1).getMinutes() / 60.00;
						cell15.setCellValue(hours);
						cell15.setCellStyle(cs);

						Cell cell16 = row.createCell(5);
						cell16.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUpdatedAt());
						cell16.setCellStyle(cs);
					}
				}

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Type", "application/force-download");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setHeader("Content-Disposition", "attachment; filename=TimeEntryList.xlsx");// fileName);

				OutputStream out = response.getOutputStream();
				wb.write(out);
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
