package ch.simas.mite4java.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customers")
public class CustomerData {

	ArrayList<Customer> customerList;

	// XmlElement sets the name of the entities
	@XmlElement(name = "customer")
	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}

}
