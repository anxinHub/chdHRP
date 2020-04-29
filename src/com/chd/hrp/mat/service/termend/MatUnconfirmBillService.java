package com.chd.hrp.mat.service.termend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatUnconfirmBillService  extends SqlService {

	public String updateUnconfirmBill(Map<String, Object> page) throws DataAccessException;
}
