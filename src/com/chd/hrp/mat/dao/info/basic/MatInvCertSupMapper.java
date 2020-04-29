
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 

public interface MatInvCertSupMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatInvCertSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatInvCertSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatInvCertSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return MatInvCertSup
	 * @throws DataAccessException
	*/
	public int updateBatchMatInvCertSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatInvCertSup(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatInvCertSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集MAT_INV_CERT<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvCertSup> queryMatInvCertSup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集MAT_INV_CERT<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvCertSup> queryMatInvCertSup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取MAT_INV_CERT<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatInvCertSup
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
	 * 查询结果集MAT_INV_CERT<BR>全部（不分页   left join MAT_INV_CERT_TYPE ）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatInvCertSup> queryMatInvCertSupNew(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询结果集MAT_INV_CERT<BR>全部（分页   left join MAT_INV_CERT_TYPE ）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatInvCertSup> queryMatInvCertSupNew(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 修改时 查询获取MAT_INV_CERT<BR>(页面回值)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public MatInvCertSup queryMatInvCertSupByCodeNew(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 MatInvCertSup（判断证件编号 是否已经存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatInvCertSup> queryMatInvCertSupByID(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询MatInvCertSup 数据是否被引用（引用不允许删除）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDelDate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 获取材料列表
	 */
	public List<Map<String,Object>> queryMatCertInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMatCertInvList(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 获取序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMatInvCertNextId() throws DataAccessException;
	
	/**
	 * 查询 选择的材料
	 */
	public List<Map<String,Object>> queryMatCertInvChoiceInvList(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public int supAuditOrUnAuditBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * 批量审核或消审<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	 */
	public int auditOrUnAuditBatch(List<Map<String, Object>> entityList)throws DataAccessException;

	/**
	 * @Description 
	 * 查询证件信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCertListForImport(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 查询证件类别信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCertTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 查询生产厂商信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryFacListForImport(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 查询材料信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryInvListForImport(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 查询供应商信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> querySupListForImport(Map<String,Object> entityMap) throws DataAccessException;
}
