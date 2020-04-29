/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.service.paper;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccEInvoice;

public interface AccEInvoiceService {
	
	public String addAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchAccEInvoice(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccEInvoice queryAccEInvoiceByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
}
