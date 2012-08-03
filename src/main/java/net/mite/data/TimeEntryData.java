package net.mite.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "time-entries")
public class TimeEntryData {

	ArrayList<TimeEntry> timeEntryList;

	// XmlElement sets the name of the entities
	@XmlElement(name = "time-entry")
	public ArrayList<TimeEntry> getTimeEntryList() {
		return timeEntryList;
	}

	public void setTimeEntryList(ArrayList<TimeEntry> timeEntryList) {
		this.timeEntryList = timeEntryList;
	}

}
