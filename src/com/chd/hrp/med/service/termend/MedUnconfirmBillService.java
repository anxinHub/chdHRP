package com.chd.hrp.med.service.termend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MedUnconfirmBillService  extends SqlService {

	public String updateUnconfirmBill(Map<String, Object> page) throws DataAccessException;
}
