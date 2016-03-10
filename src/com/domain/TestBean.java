package com.domain;

public class TestBean {
	private int id;
	private String sex;
	private int age;
	private String hobby;
	private String name;
	private String pending;
	private double weekconsume;
	private double monthconsume;
	private double yearconsume;

	public TestBean(int id, String sex, int age, String content, String time,
			String pending, double weekconsume, double monthconsume,
			double yearconsume) {
		super();
		this.id = id;
		this.sex = sex;
		this.age = age;
		this.hobby = content;
		this.name = time;
		this.pending = pending;
		this.weekconsume = weekconsume;
		this.monthconsume = monthconsume;
		this.yearconsume = yearconsume;
	}

	public double getWeekconsume() {
		return weekconsume;
	}

	public void setWeekconsume(double weekconsume) {
		this.weekconsume = weekconsume;
	}

	public double getMonthconsume() {
		return monthconsume;
	}

	public void setMonthconsume(double monthconsume) {
		this.monthconsume = monthconsume;
	}

	public double getYearconsume() {
		return yearconsume;
	}

	public void setYearconsume(double yearconsume) {
		this.yearconsume = yearconsume;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String amount) {
		this.sex = amount;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int total) {
		this.age = total;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String content) {
		this.hobby = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String time) {
		this.name = time;
	}

	public String getPending() {
		return pending;
	}

	public void setPending(String pending) {
		this.pending = pending;
	}

	public TestBean() {
		super();
	}

	public TestBean(int id, String name, String sex, int age, String hobby) {
		super();
		this.id = id;
		this.sex = sex;
		this.age = age;
		this.hobby = hobby;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Consume [id=" + id + ", amount=" + sex + ", total=" + age
				+ ", content=" + hobby + ", time=" + name + ", pending="
				+ pending + ", weekconsume=" + weekconsume + ", monthconsume="
				+ monthconsume + ", yearconsume=" + yearconsume + "]";
	}

}
