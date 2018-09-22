package com.crm.qa.models;

/**
 * The Class LoginTestModel.
 */
public class LoginTestModel {

	/** The fname. */
	@Column(name = "fName")
	public String fname;

	/** The lname. */
	@Column(name = "lName")
	public String lname;

	/** The on date month. */
	@Column(name = "onDateMonth")
	public String onDateMonth;

	/** The on date day. */
	@Column(name = "onDateDay")
	public int onDateDay;

	/** The departing frm. */
	@Column(name = "departingFrm")
	public String departingFrm;

	/** The arriving in. */
	@Column(name = "arrivingIn")
	public String arrivingIn;

	/** The return month. */
	@Column(name = "returnMonth")
	public String returnMonth;

	/** The return date. */
	@Column(name = "returnDate")
	public int returnDate;

	/** The creditcard number. */
	@Column(name = "CNUM")
	public String cNum;
}
