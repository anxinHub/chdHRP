package com.chd.webservice.server.hrp;

import javax.jws.WebService;

@WebService
public interface MatService {

	public String queryHosSupDict(String group_id, String hos_id, String sup_id);
}
