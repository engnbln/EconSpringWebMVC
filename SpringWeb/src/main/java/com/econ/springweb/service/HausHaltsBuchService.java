package com.econ.springweb.service;

import com.econ.springweb.entity.HausHaltsBuchData;
import com.econ.springweb.entity.MonatlicheListeResponse;

public interface HausHaltsBuchService {

	void hinzufugen(HausHaltsBuchData hausHaltsBuchData) throws Exception;

	MonatlicheListeResponse getAktuellMonatlicheListe() throws Exception;

}
