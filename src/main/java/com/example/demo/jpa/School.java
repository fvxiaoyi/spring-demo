package com.example.demo.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
//
//@Entity
//@Table(name = "t_school")
public class School {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@ElementCollection
	@CollectionTable(name = "t_school_stu", joinColumns = { @JoinColumn(name = "t_school_stu_id") })
	private List<StudentObj> author = new ArrayList<>();

	public String getId() {
		return id;
	}

	public List<StudentObj> getAuthor() {
		return author;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAuthor(List<StudentObj> author) {
		this.author = author;
	}

}
