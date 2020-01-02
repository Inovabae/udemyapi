package br.com.inovabae.udemy_api_client;

import java.util.List;
import java.util.Locale;

public class Course {
	private Integer id;
	private String title;
	private String url;
	private Boolean is_paid;	
	private ValorMonetario price;
	private Locale language;
	private List<Instructor> instructors;

	public List<Instructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<Instructor> instructors) {
		this.instructors = instructors;
	}

	public Locale getLanguage() {
		return language;
	}

	public void setLanguage(Locale language) {
		this.language = language;
	}

	private List<Instructor> visible_instructors;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIs_paid() {
		return is_paid;
	}

	public void setIs_paid(Boolean is_paid) {
		this.is_paid = is_paid;
	}

	public ValorMonetario getPrice() {
		return price;
	}

	public void setPrice(ValorMonetario price) {
		this.price = price;
	}

	public List<Instructor> getVisible_instructors() {
		return visible_instructors;
	}

	public void setVisible_instructors(List<Instructor> visible_instructors) {
		this.visible_instructors = visible_instructors;
	}

}
