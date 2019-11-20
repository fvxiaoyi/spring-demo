package com.example.demo.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_catalog", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Catalog {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "detail_name")
	@Column(name = "detail_value")
	@CollectionTable(name = "t_catlog_detail", joinColumns = { @JoinColumn(name = "catlog_id") })
	private Map<String, String> val = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getVal() {
		return val;
	}

	public void setVal(Map<String, String> val) {
		this.val = val;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
