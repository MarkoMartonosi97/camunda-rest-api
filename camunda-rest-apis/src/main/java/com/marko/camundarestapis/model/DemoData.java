package com.marko.camundarestapis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//ovo samo sluzi da se demonstrira da je moguce
//koristiti i sopstvene beanove, mapiranje ce biti obaveljeno automatski
//a JavaBean moze biti i procesna varijabla

@Entity
public class DemoData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String txt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
}
