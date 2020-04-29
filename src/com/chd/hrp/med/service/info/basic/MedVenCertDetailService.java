/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.info.basic;
import java.text.ParseException;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedVenCertDetail;
/**
 * 
 * @Description:
 * cert_state
1：在用
0：停用



 * @Table:
 * MED_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedVenCertDetailService {

	/**
	 * @Description 
	 * 添加cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	public String addMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
	/**
	 * @Description 
	 * 批量添加cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	public String updateMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
	/**
	 * @Description 
	 * 批量更新cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedVenCertDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedVenCertDetail queryMedVenCertDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedVenCertDetail
	 * @throws DataAccessException
	*/
	public MedVenCertDetail queryMedVenCertDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
