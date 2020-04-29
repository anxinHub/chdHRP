/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.info.basic;
import java.text.ParseException;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatVenCertDetail;
/**
 * 
 * @Description:
 * cert_state
1：在用
0：停用



 * @Table:
 * MAT_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatVenCertDetailService {

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
	public String addMatVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
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
	public String addBatchMatVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
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
	public String updateMatVenCertDetail(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
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
	public String updateBatchMatVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
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
	public String deleteMatVenCertDetail(Map<String,Object> entityMap)throws DataAccessException;
	
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
	public String deleteBatchMatVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
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
	public String addOrUpdateMatVenCertDetail(Map<String,Object> entityMap)throws DataAccessException;

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
	public String queryMatVenCertDetail(Map<String,Object> entityMap) throws DataAccessException;
	
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
	public MatVenCertDetail queryMatVenCertDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取cert_state
1：在用
0：停用


<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatVenCertDetail
	 * @throws DataAccessException
	*/
	public MatVenCertDetail queryMatVenCertDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 审核/消审/驳回/取消驳回
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateVenCertDetailState(List<Map<String, Object>> listVo)throws DataAccessException;
	
	/**
	 * 删除 证件附件 清空 文件路径
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void updateVenCertFilePath(Map<String, Object> mapVo) throws DataAccessException;
}
