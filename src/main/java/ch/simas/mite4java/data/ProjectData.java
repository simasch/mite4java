package ch.simas.mite4java.data;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "projects")
public class ProjectData {

	ArrayList<Project> projectList;

	// XmlElement sets the name of the entities
	@XmlElement(name = "project")
	public ArrayList<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList<Project> projectList) {
		this.projectList = projectList;
	}

}
