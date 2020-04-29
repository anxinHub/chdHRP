/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.paper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccEInvoice;

/**
* @Title. @Description.
* 票据管理主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEInvoiceMapper extends SqlMapper{
	
	public int addAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	public int deleteBatchAccEInvoice(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccEInvoice queryAccEInvoiceByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccEInvoice(Map<String,Object> entityMap) throws DataAccessException;
}
