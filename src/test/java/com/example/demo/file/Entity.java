package com.example.demo.file;

public class Entity {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Entity [name=" + name + "]";
	}

	public Entity(String name) {
		super();
		this.name = name;
	}

}
