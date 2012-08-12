package ch.simas.mite4java.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.simas.mite4java.data.Customer;
import ch.simas.mite4java.data.Project;
import ch.simas.mite4java.data.ReportFilter;
import ch.simas.mite4java.data.Service;
import ch.simas.mite4java.data.UserInfo;
import ch.simas.mite4java.service.MiteSystem;
import ch.simas.mite4java.service.XlsxExpoter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ReportController {

	@RequestMapping("/getprojects")
	public String getProjects(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		MiteSystem miteSystem = new MiteSystem();

		String serverResp = miteSystem.getProjectXmlResponse(subDomain, apiKey);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/getservices")
	public String getServices(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		MiteSystem miteSystem = new MiteSystem();

		String serverResp = miteSystem.getServiceXmlResponse(subDomain, apiKey);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/getcustomers")
	public String getCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		MiteSystem miteSystem = new MiteSystem();

		String serverResp = miteSystem.getCustomerXmlResponse(subDomain, apiKey);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/getreport")
	public String getReport(Map<String, Object> map) {

		return "connectreport";
	}

	@RequestMapping("/getselectedreportentrytxls")
	public String exportReportxlsx(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {

		String apiKey = request.getParameter("miteApiKey");

		String subDomain = request.getParameter("subDomainName");

		MiteSystem miteSystem = new MiteSystem();

		map = miteSystem.getMiteAllMainInfoMap(subDomain, apiKey, map);

		return "exporttimeentryxlsx";
	}

	@RequestMapping(value = "/gettimeentryselectedfilelds", method = RequestMethod.POST)
	public String getTimeEntrySelectedFields(HttpServletRequest request, @ModelAttribute("userInfo") UserInfo userInfo, BindingResult result) {

		String customerFl = null;
		String filterreqParam = "";

		try {
			customerFl = request.getParameter("customerFl");
		} catch (Exception e) {
		}

		if (customerFl != null)
			filterreqParam += "&customerFl=" + customerFl;

		String projectFl = null;
		try {
			projectFl = request.getParameter("projectFl");
		} catch (Exception e) {
		}

		if (projectFl != null)
			filterreqParam += "&projectFl=" + projectFl;

		String serviceFl = null;
		try {
			serviceFl = request.getParameter("serviceFl");
		} catch (Exception e) {
		}

		if (serviceFl != null)
			filterreqParam += "&serviceFl=" + serviceFl;

		String customerSelected = null;
		try {
			customerSelected = request.getParameter("customerSelected");
		} catch (Exception e) {
		}

		if (customerSelected != null)
			filterreqParam += "&customerSelected=" + customerSelected;

		String projectSelected = null;
		try {
			projectSelected = request.getParameter("projectSelected");
		} catch (Exception e) {
		}

		if (projectSelected != null)
			filterreqParam += "&projectSelected=" + projectSelected;

		String serviceSelected = null;
		try {
			serviceSelected = request.getParameter("serviceSelected");
		} catch (Exception e) {
		}

		if (serviceSelected != null)
			filterreqParam += "&serviceSelected=" + serviceSelected;

		return "redirect:/gettimeentries?subdomain=" + userInfo.getSubDomainName() + "&apikey=" + userInfo.getMiteApiKey() + "&selectedfields=" + userInfo.getSelectedFields() + filterreqParam;

	}

	@RequestMapping("/gettimeentries")
	public String getTimeEntries(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");
		String selectedFields = request.getParameter("selectedfields");

		String customerFl = null;
		try {
			customerFl = request.getParameter("customerFl");
		} catch (Exception e) {
		}

		String projectFl = null;
		try {
			projectFl = request.getParameter("projectFl");
		} catch (Exception e) {
		}

		String serviceFl = null;
		try {
			serviceFl = request.getParameter("serviceFl");
		} catch (Exception e) {
		}

		String customerSelected = null;
		try {
			customerSelected = request.getParameter("customerSelected");
		} catch (Exception e) {
		}

		String projectSelected = null;
		try {
			projectSelected = request.getParameter("projectSelected");
		} catch (Exception e) {
		}

		String serviceSelected = null;
		try {
			serviceSelected = request.getParameter("serviceSelected");
		} catch (Exception e) {
		}

		ReportFilter repFltr = new ReportFilter();

		repFltr.setCustomerFl(customerFl);
		repFltr.setCustomerSelected(customerSelected);
		repFltr.setProjectFl(projectFl);
		repFltr.setProjectSelected(projectSelected);
		repFltr.setServiceFl(serviceFl);
		repFltr.setServiceSelected(serviceSelected);

		OutputStream out = response.getOutputStream();

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment; filename=TimeEntryList.xlsx");// fileName);

		XlsxExpoter xlsxExporter = new XlsxExpoter();

		xlsxExporter.exportTimeEntryData(subDomain, apiKey, selectedFields, repFltr, out);

		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/postcustomers")
	public String postCustomers(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		Customer cust = new Customer();

		cust.setName("Moodster Inc.");
		cust.setNote("Shilly-shally!");
		cust.setArchived(false);
		cust.setHourlyRate(40);

		MiteSystem miteSystem = new MiteSystem();
		String serverResp = miteSystem.postCustomerXmlResponse(subDomain, apiKey, cust);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/postprojects")
	public String postProjects(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		Project proj = new Project();

		proj.setName("Relaunch microsite");
		proj.setNote("abcd");
		proj.setArchived(false);
		proj.setHourlyRate(65);
		proj.setCustomerId(1);
		proj.setBudget(250);

		MiteSystem miteSystem = new MiteSystem();
		String serverResp = miteSystem.postProjectXmlResponse(subDomain, apiKey, proj);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

	@RequestMapping("/postservices")
	public String postServices(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");

		Service serv = new Service();

		serv.setName("Coding3");
		serv.setNote("ABCD");
		serv.setArchived(false);
		serv.setHourlyRate(400);

		MiteSystem miteSystem = new MiteSystem();
		String serverResp = miteSystem.postServiceXmlResponse(subDomain, apiKey, serv);

		response.setContentType("text/xml");
		response.getOutputStream().write(((serverResp)).getBytes());
		response.getOutputStream().flush();

		return null;

	}

}
