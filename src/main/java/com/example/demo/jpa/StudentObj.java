package com.example.demo.jpa;

import javax.persistence.Embeddable;

@Embeddable
public class StudentObj {

	private String stuId;
	private String stuName;

	public StudentObj(String stuId, String stuName) {
		super();
		this.stuId = stuId;
		this.stuName = stuName;
	}

	public StudentObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStuId() {
		return stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

}
