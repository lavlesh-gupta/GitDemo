package pojo;

public class GetCourse {

	private String url, services, expertise, insturctor, linkedIn;
	private Courses courses;
	//Shortcuts for getting getter and setter is alt+shift+s

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public Courses getCourses() {
		return courses;
	}

	public void setCourses(Courses courses) {
		this.courses = courses;
	}

	public String getInsturctor() {
		return insturctor;
	}

	public void setInsturctor(String insturctor) {
		this.insturctor = insturctor;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

}
