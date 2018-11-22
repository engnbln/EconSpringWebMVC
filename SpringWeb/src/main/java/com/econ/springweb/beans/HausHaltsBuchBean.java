package com.econ.springweb.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.econ.springweb.entity.HausHaltsBuchData;
import com.econ.springweb.entity.HausHaltsBuchData.TYPE;
import com.econ.springweb.entity.MonatlicheListeResponse;
import com.econ.springweb.service.HausHaltsBuchService;

//Bean für jsf pages
@Named
@Scope("session")
public class HausHaltsBuchBean {

	private static final Logger logger = Logger.getLogger(HausHaltsBuchBean.class);

	@Inject
	HausHaltsBuchService hausHaltsBuchService;

	private MonatlicheListeResponse monatlicheListeResponse;

	private List<String> types;
	private String type;
	private double betrag;
	private String kategorie;
	private String memo;
	private Date date;

	// Anfangswerte definieren
	@PostConstruct
	public void init() {
		logger.debug("init");
		types = new ArrayList<String>();
		types.add("Einzahlung");
		types.add("Auszahlung");
		date = Calendar.getInstance().getTime();
		getAktuellMonatlicheListe();
	}

	public void refresh() throws Exception {
		logger.debug("refresh");
		type = null;
		betrag = 0;
		kategorie = null;
		memo = null;
		date = Calendar.getInstance().getTime();
		getAktuellMonatlicheListe();
	}

	// Ein- oder Auszahlung buchen
	public void hinzufugen(ActionEvent actionEvent) throws Exception {
		logger.debug("hinzufügen  type : " + type + " betrag: " + betrag);
		try {
			HausHaltsBuchData hausHaltsBuchData = new HausHaltsBuchData();
			hausHaltsBuchData.setType(TYPE.valueOf(type));
			hausHaltsBuchData.setBetrag(betrag);
			hausHaltsBuchData.setKategorie(kategorie);
			hausHaltsBuchData.setMemo(memo);
			hausHaltsBuchData.setDate(date);
			hausHaltsBuchService.hinzufugen(hausHaltsBuchData);

			// Gehe zur Hauptseite, falls erfolgreich
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(origRequest.getContextPath() + "/success.xhtml");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("hinzufugen:error", new FacesMessage(e.getMessage()));
			logger.error(e);
		}
	}

	// bringt die monatliche Liste von cache
	public void getAktuellMonatlicheListe() {
		logger.debug("getCurrentMonatList");
		try {
			monatlicheListeResponse = hausHaltsBuchService.getAktuellMonatlicheListe();
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void setHausHaltsBuchService(HausHaltsBuchService hausHaltsBuchService) {
		this.hausHaltsBuchService = hausHaltsBuchService;
	}

	public MonatlicheListeResponse getMonatlicheListeResponse() {
		return monatlicheListeResponse;
	}

	public void setMonatlicheListeResponse(MonatlicheListeResponse monatlicheListeResponse) {
		this.monatlicheListeResponse = monatlicheListeResponse;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

}