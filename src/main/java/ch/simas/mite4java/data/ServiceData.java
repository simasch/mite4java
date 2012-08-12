package ch.simas.mite4java.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "services")
public class ServiceData {

	ArrayList<Service> serviceList;

	// XmlElement sets the name of the entities
	@XmlElement(name = "service")
	public ArrayList<Service> getServiceList() {
		return serviceList;
	}

	public void setServiceList(ArrayList<Service> serviceList) {
		this.serviceList = serviceList;
	}

}


