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

		map = miteSystem.getMiteRootInfoMap(subDomain, apiKey, map);

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

		XlsxExpoter xlsxExporter = new XlsxExpoter();

		xlsxExporter.exportTimeEntryData(subDomain, apiKey, selectedFields, out);

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
