/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccMpayVeri;

/**
* @Title. @Description.
* 工资套<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccMpayVeriService {

	public String addAccMpayVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAccMpayVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAccMpayVeri(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccMpayVeri queryAccMpayVeriByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteAccMpayVeri(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchAccMpayVeri(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAccMpayVeriR(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccVeriAll(Map<String, Object> entityMap) throws DataAccessException;
}
