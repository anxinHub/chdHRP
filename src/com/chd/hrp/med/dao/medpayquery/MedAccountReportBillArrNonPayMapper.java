package com.chd.hrp.med.dao.medpayquery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 票到款未付明细表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAccountReportBillArrNonPayMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedAccountReportBillArrNonPay(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryMedAccountReportBillArrNonPayMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedAccountReportBillArrNonPayMain(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询报表<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedAccountReportBillArrNonPay(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMedPayBillDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedPayBillDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
