package com.example.demo.jpa;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//@Entity
//@Table(name = "t_book")
public class Book {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@Embedded
	private StudentObj author;

	public Book() {
		super();
	}

	public Book(String stuId, String stuName) {
		super();
		this.author = new StudentObj(stuId, stuName);
	}

	public String getId() {
		return id;
	}

	public StudentObj getAuthor() {
		return author;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAuthor(StudentObj author) {
		this.author = author;
	}

}
