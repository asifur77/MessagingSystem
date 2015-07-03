package com.spr.model;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shops")
public class Shop {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	
	private String reply;

	@Column(name = "employees_number")
	private String emplNumber;

	private String dtime;
	
	private String send_time;
	
	private boolean status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = "Hello: " + reply;
	}

	public String getEmplNumber() {
		return emplNumber;
	}

	public void setEmplNumber(String emplNumber) {
		this.emplNumber = emplNumber;
	}
	
	public String getdtime() {
		return dtime;
	}

	public void setdtime() {
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		this.dtime = timeStamp;
	}
	
	public String getsend_time() {
		return send_time;
	}

	public void setsend_time(String message) {
		
		String timeStamp = "1980/01/01 00:00:01";
		if(message.isEmpty() == false)
			timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		this.send_time = timeStamp;
	}
	
	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
