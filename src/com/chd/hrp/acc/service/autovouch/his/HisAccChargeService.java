/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.autovouch.his;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.HrpAccSelect;

public interface HisAccChargeService {

	//查询凭证模板
	public String queryAutoBusiTemplate(Map<String, Object> map)throws DataAccessException;
	
	/**
	 * 查询门诊收费类别-表头
	 * */
	public String queryHisChargeHeadO(Map<String, Object> entityMap) throws DataAccessException;

	
	/**
	 * 查询门诊收费数据
	 * */
	public String queryHisChargeDataO(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisChargeDataOPrint(Map<String, Object> entityMap) throws DataAccessException;
	

	//查询门诊结算数据 
	public String queryHisBalO(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryHisBalOPrint(Map<String, Object> map) throws DataAccessException;
	
	//查询门诊收费结算凭证json
	public String queryHisChargeVouchO(Map<String, Object> map) throws Exception;
	
	//查询住院收费类别-表头
	public String queryHisChargeHeadI(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询住院收费数据
	public String queryHisChargeDataI(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisChargeDataIPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询住院收费结算凭证json
	public String queryHisChargeVouchI(Map<String, Object> map) throws Exception;
	
	//查询住院结算数据
	public String queryHisBalI(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> queryHisBalIPrint(Map<String, Object> map) throws Exception;
	
	//查询住院结算凭证json
	public String queryHisBalVouchI(Map<String, Object> map) throws Exception;

	public String saveHisChargeVouchO(Map<String, Object> map) throws Exception;

	public String queryVouchNo(Map<String, Object> mapVo)throws Exception;
}
