package com.econ.springweb.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.econ.springweb.entity.BaseResponse;
import com.econ.springweb.entity.HausHaltsBuchData;
import com.econ.springweb.entity.MonatlicheListeResponse;
import com.econ.springweb.service.HausHaltsBuchService;

//Der Controller wird für Anfragen von Postman geschrieben

@Controller
@RequestMapping("/restservice")
public class HausHaltsBuchRestController {

	private static final Logger logger = Logger.getLogger(HausHaltsBuchRestController.class);

	@Autowired
	private HausHaltsBuchService hausHaltsBuchService;

	// Ein- oder Auszahlung wird in Cache geschrieben
	@RequestMapping(value = "/hinzufugen", method = RequestMethod.POST)
	public @ResponseBody BaseResponse hinzufugen(@RequestBody HausHaltsBuchData hausHaltsBuchData) {

		logger.info("/hinzufügen Request Type: " + hausHaltsBuchData.getType() + " Betrag: " + hausHaltsBuchData.getBetrag());
		try {

			hausHaltsBuchService.hinzufugen(hausHaltsBuchData);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new BaseResponse(false, e.getMessage());
		}

		return new BaseResponse(true, "Erfolg");

	}

	// bringt die aktuell monat liste Liste von cache
	@RequestMapping(value = "/aktuellmonatliste", method = RequestMethod.GET)
	public @ResponseBody MonatlicheListeResponse getMonatList() {
		logger.info("/monatliste Request");
		
		try {
			return hausHaltsBuchService.getAktuellMonatlicheListe();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return new MonatlicheListeResponse(false, e.getMessage());
		}
	}

}