package net.mite.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import net.mite.utils.Getconnection;
import net.mite.data.Customer;
import net.mite.data.Project;
import net.mite.data.Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Controller
public class ContactController {


	@RequestMapping("/getmiteprojects")
	public String getMiteProjects(HttpServletRequest request,HttpServletResponse response) throws IOException {

	String apiKey = request.getParameter("apikey");
	String subDomain = request.getParameter("subdomain");
	
	String TARGET_HTTPS_SERVER =subDomain+".mite.yo.lk";
	String TARGET_URL = "https://"+subDomain+".mite.yo.lk/projects.xml?api_key="+apiKey;
	
	//int    TARGET_HTTPS_PORT   = 443; 
	
	
	Getconnection  gcon = new Getconnection(TARGET_HTTPS_SERVER,"true");
	
	gcon.getCon();
	        
	try {
        int tmplp = 0;
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
	public String getServices(HttpServletRequest request,HttpServletResponse response) throws IOException {

	String apiKey = request.getParameter("apikey");
	String subDomain = request.getParameter("subdomain");
	
	String TARGET_HTTPS_SERVER =subDomain+".mite.yo.lk";
	String TARGET_URL = "https://"+subDomain+".mite.yo.lk/services.xml?api_key="+apiKey;
	
	//int    TARGET_HTTPS_PORT   = 443; 
	
	
	Getconnection  gcon = new Getconnection(TARGET_HTTPS_SERVER,"true");
	
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
	
	
	
	
	
	
	  private static String readAll(Reader rd) throws IOException {
	        StringBuilder sb = new StringBuilder();
	        int cp;
	        while ((cp = rd.read()) != -1) {
	            sb.append((char) cp);
	        }
	        return sb.toString();
	    }
	
	  
	  @RequestMapping("/postcustomers")
	  public String postCustomers(HttpServletRequest request,HttpServletResponse response) throws IOException {

	  		String apiKey = request.getParameter("apikey");
	  		String subDomain = request.getParameter("subdomain");
	  		
	  		String TARGET_HTTPS_SERVER =subDomain+".mite.yo.lk";
	  		String TARGET_URL = "https://"+subDomain+".mite.yo.lk/customers.xml?api_key="+apiKey;
	  		
	  		Getconnection  gcon = new Getconnection(TARGET_HTTPS_SERVER,"true");
	  		
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
				
			
			// output pretty printed
		
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
				jaxbMarshaller.marshal(cust, writer);
			
				jaxbMarshaller.marshal(cust, System.out);


			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  	        
 	    
	  	    String xmlData=writer.toString();

	  	    xmlData.replace("</hourlyRate>", "</hourly-rate>");

	  	    String serverResp = excutePost(TARGET_URL, xmlData);
	  	            
	  	    response.setContentType("text/xml");
	  	    response.getOutputStream().write(((serverResp)).getBytes());
	  	    response.getOutputStream().flush();

	  		return null;
	  		
	  		}  
	
	  @RequestMapping("/postprojects")
	  public String postProjects(HttpServletRequest request,HttpServletResponse response) throws IOException {

	  		String apiKey = request.getParameter("apikey");
	  		String subDomain = request.getParameter("subdomain");
	  		
	  		String TARGET_HTTPS_SERVER =subDomain+".mite.yo.lk";
	  		String TARGET_URL = "https://"+subDomain+".mite.yo.lk/projects.xml?api_key="+apiKey;
	  		
	  		Getconnection  gcon = new Getconnection(TARGET_HTTPS_SERVER,"true");
	  		
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
				
			
			// output pretty printed
		
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
				jaxbMarshaller.marshal(proj, writer);
			
				jaxbMarshaller.marshal(proj, System.out);


			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  	        

	  	     String xmlData=writer.toString();

	  	    String serverResp = excutePost(TARGET_URL, xmlData);
	  	            
	  	    response.setContentType("text/xml");
	  	    response.getOutputStream().write(((serverResp)).getBytes());
	  	    response.getOutputStream().flush();

	  		return null;
	  		
	  		}  
	  
	  @RequestMapping("/postservices")
public String postServices(HttpServletRequest request,HttpServletResponse response) throws IOException {

		String apiKey = request.getParameter("apikey");
		String subDomain = request.getParameter("subdomain");
		
		String TARGET_HTTPS_SERVER =subDomain+".mite.yo.lk";
		String TARGET_URL = "https://"+subDomain+".mite.yo.lk/services.xml?api_key="+apiKey;
		
		Getconnection  gcon = new Getconnection(TARGET_HTTPS_SERVER,"true");
		
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
			
		
		// output pretty printed
	
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	
			jaxbMarshaller.marshal(serv, writer);
		
			jaxbMarshaller.marshal(serv, System.out);


		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    String xmlData=writer.toString();

	    String serverResp = excutePost(TARGET_URL, xmlData);
	            
	    response.setContentType("text/xml");
	    response.getOutputStream().write(((serverResp)).getBytes());
	    response.getOutputStream().flush();

		return null;
		
		}
	  
	  
	  
	  
	  
	  
public static String excutePost(String targetURL, String xmlData)
	  {
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "text/xml");
				
	      connection.setRequestProperty("Content-Length", "" + Integer.toString(xmlData.length()));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
	      wr.writeBytes (xmlData);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      String serverResp="";
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

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
	
}
