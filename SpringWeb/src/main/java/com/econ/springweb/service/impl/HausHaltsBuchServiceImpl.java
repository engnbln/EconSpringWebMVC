package com.econ.springweb.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.econ.springweb.entity.HausHaltsBuchData;
import com.econ.springweb.entity.HausHaltsBuchData.TYPE;
import com.econ.springweb.entity.MonatlicheListeResponse;
import com.econ.springweb.service.HausHaltsBuchService;

@Named
@Service
public class HausHaltsBuchServiceImpl implements HausHaltsBuchService {

	private static final Logger logger = Logger.getLogger(HausHaltsBuchServiceImpl.class);

	// key monat
	// Cache für Ein- und Auszahlung
	private Map<String, List<HausHaltsBuchData>> hinzufugenList = new HashMap<String, List<HausHaltsBuchData>>();

	// key monat
	// Cache für kassenbestand
	private Map<String, Double> kassenbestandMap = new HashMap<String, Double>();

	// Ein- oder Auszahlung save to cache
	@Override
	public void hinzufugen(HausHaltsBuchData hausHaltsBuchData) throws Exception {

		logger.debug("hinzufugen methode aufgerufen Type: " + hausHaltsBuchData.getType() + " Betrag: "
				+ hausHaltsBuchData.getBetrag());
		validateHausHaltsBuchData(hausHaltsBuchData);
		addHinzufugen(hausHaltsBuchData);
	}

	// Return current monat list from cache
	@Override
	public MonatlicheListeResponse getAktuellMonatlicheListe() throws Exception {

		logger.debug("getAktuellMonatlicheListe methode aufgerufen");
		String key = getMonateKey(null);

		List<HausHaltsBuchData> list = hinzufugenList.get(key);
		MonatlicheListeResponse monatlicheListeResponse;
		if (list == null) {
			monatlicheListeResponse = new MonatlicheListeResponse(true, "Kein list für den Monat : " + key);
			monatlicheListeResponse.setMonat(key);
			return monatlicheListeResponse;
		}

		monatlicheListeResponse = new MonatlicheListeResponse(true, "Erflog");
		monatlicheListeResponse.setMonat(key);
		monatlicheListeResponse.setKassenbestand(kassenbestandMap.get(key));
		monatlicheListeResponse.setList(list);

		logger.debug("Antwort ->> Monat: " + monatlicheListeResponse.getMonat() + " Kassenbestand: "
				+ monatlicheListeResponse.getKassenbestand());
		return monatlicheListeResponse;

	}

	private void validateHausHaltsBuchData(HausHaltsBuchData hausHaltsBuchData) throws Exception {
		if (hausHaltsBuchData.getType() == null || hausHaltsBuchData.getType().equals(TYPE.Unknown)) {
			throw new Exception("Type ist erforderlich.");
		} else if (hausHaltsBuchData.getBetrag() < 1) {
			throw new Exception("Betrag muss größer als 0 sein.");
		} else if (hausHaltsBuchData.getType().equals(TYPE.Auszahlung)
				&& (hausHaltsBuchData.getKategorie() == null || hausHaltsBuchData.getKategorie().isEmpty())) {
			throw new Exception("Kategorie ist erforderlich für die Auszahlung.");
		}
	}

	// adding to cache
	private void addHinzufugen(HausHaltsBuchData hausHaltsBuchData) {

		String key = getMonateKey(hausHaltsBuchData.getDate());

		List<HausHaltsBuchData> list = hinzufugenList.get(key);

		if (list == null) {
			list = new ArrayList<HausHaltsBuchData>();
		}
		list.add(hausHaltsBuchData);

		hinzufugenList.put(key, list);

		Double kassenbestand = kassenbestandMap.get(key);

		if (kassenbestand == null) {
			kassenbestand = Double.valueOf(0);
		}

		if (hausHaltsBuchData.getType() == TYPE.Einzahlung) {
			kassenbestand = kassenbestand + hausHaltsBuchData.getBetrag();
		} else if (hausHaltsBuchData.getType() == TYPE.Auszahlung) {
			kassenbestand = kassenbestand - hausHaltsBuchData.getBetrag();
		}
		kassenbestandMap.put(key, kassenbestand);
	}

	// z.B Jan 2018
	private String getMonateKey(Date date) {
		SimpleDateFormat month_date = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);

		// für Einzahlung
		if (date == null) {
			date = Calendar.getInstance().getTime();
		}

		return month_date.format(date);
	}

}
