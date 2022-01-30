package hu.webuni.hr.ferencjozsef.model;

public enum Education {
	GRADUATION("érettségi"), COLLEGE("főiskola"), UNIVERSITY("egyetem"), PHD("PhD");

	private String name;
	
	Education(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
