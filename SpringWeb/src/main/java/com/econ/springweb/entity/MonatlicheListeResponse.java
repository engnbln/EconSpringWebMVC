package com.econ.springweb.entity;

import java.util.List;

public class MonatlicheListeResponse extends BaseResponse {

	public MonatlicheListeResponse(boolean success, String message) {
		super(success, message);
	}

	private String monat;
	private double kassenbestand;
	private List<HausHaltsBuchData> list;

	public String getMonat() {
		return monat;
	}

	public void setMonat(String monat) {
		this.monat = monat;
	}

	public double getKassenbestand() {
		return kassenbestand;
	}

	public void setKassenbestand(double kassenbestand) {
		this.kassenbestand = kassenbestand;
	}

	public List<HausHaltsBuchData> getList() {
		return list;
	}

	public void setList(List<HausHaltsBuchData> list) {
		this.list = list;
	}

}
