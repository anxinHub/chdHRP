
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

import com.chd.hrp.mat.entity.MatInvCertSup;
/**
 * 
 * @Description:
 * MAT_INV_CERT
 * @Table:
 * MAT_INV_CERT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatInvCertSupService {

	/**
	 * @Description 
	 * 添加MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	public String addMatInvCertSup(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
	
	/**
	 * @Description 
	 * 更新MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	public String updateMatInvCertSup(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
	/**
	 * @Description 
	 * 批量更新MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMatInvCertSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMatInvCertSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMatInvCertSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvCertSup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象MAT_INV_CERT<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MatInvCertSup queryMatInvCertSupByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取MAT_INV_CERT<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCertSup
	 * @throws DataAccessException
	*/
	public MatInvCertSup queryMatInvCertSupByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 获取材料列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatCertInvList(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询对应关系明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvCertSupDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除对应关系
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMatInvCertSupRela(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询选择的材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatCertInvChoiceInvList(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加 证件及证件供应商对应关系
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String addBatchMatCertSup(List<Map<String, Object>> entityList)	throws DataAccessException;
	
	public String supAuditMatInvCertSupBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatInvCertSupBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatInvCertSupBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * 修改时 查询获取MAT_INV_CERT<BR>（页面回值） 
	 * @param mapVo
	 * @return
	 */
	public MatInvCertSup queryMatInvCertSupByCodeNew(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 证件供应商对应关系
	 * @param page
	 * @return
	 */
	public String queryMatCerSup(Map<String, Object> entityMap);
	
	/**
	 * 查询MatInvCertSup 数据是否被引用（引用不允许删除）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDelDate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 MatInvCertSup（判断证件编号 是否已经存在）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatInvCertSup> queryMatInvCertSupByID(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String  importData(Map<String, Object> entityMap)throws DataAccessException;
}
