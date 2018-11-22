package com.econ.springweb.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

//Ein- und Auszahlung Daten typ
//Request Data
public class HausHaltsBuchData implements Serializable {

	private static final long serialVersionUID = 5400993459776088344L;

	private TYPE type;
	private double betrag;
	private String kategorie;
	private String memo;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date date;

	public HausHaltsBuchData() {
		setType(TYPE.Unknown);
		setBetrag(0);
		setKategorie(null);
		setMemo(null);
		setDate(Calendar.getInstance().getTime());
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public double getBetrag() {
		return betrag;
	}

	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Zahlung Type
	public enum TYPE {
		Einzahlung, Auszahlung, Unknown;
	}
}
