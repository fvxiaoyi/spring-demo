package com.example.demo.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//@Entity
//@Table(name = "t_score")
public class Score {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	private BigDecimal score = BigDecimal.ONE;

	@OneToMany
	@JoinColumn(name = "scoreId")
	private List<ScoreDetail> details = new ArrayList<>();

	public void addDetail(ScoreDetail detail) {
		this.details.add(detail);
	}

	public void add() {
		setScore(getScore().add(BigDecimal.ONE));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public List<ScoreDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ScoreDetail> details) {
		this.details = details;
	}

}
