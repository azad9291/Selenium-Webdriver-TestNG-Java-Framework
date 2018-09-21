package com.crm.qa.models;

public class LoginTestModel {
	
	@Column(name = "fName")
	public String fname;
	
	@Column(name = "lName")
	public String lname;
	
	@Column(name = "onDateMonth")
	public String onDateMonth;
	
	@Column(name = "onDateDay")
	public int onDateDay;
	
	@Column(name = "departingFrm")
	public String departingFrm;
	
	@Column(name = "arrivingIn")
	public String arrivingIn;
	
	@Column(name = "returnMonth")
	public String returnMonth;
	
	@Column(name = "returnDate")
	public int returnDate;
	
	@Column(name = "CNUM")
	public String cNum;
}
