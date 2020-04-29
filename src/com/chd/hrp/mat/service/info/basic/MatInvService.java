/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 *    
 * @Description:
 * 04105 物资材料表
 * @Table:
 * MAT_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 
public interface MatInvService extends SqlService {

	/** 
	 * @Description 
	 * 添加材料供应商数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatInvSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addimp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量导入材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addImpBatch(List<Map<String,Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量修改材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateMatInvBatch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量添加材料供应商数据<BR> 
	 * @param  entityList
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatInvSupBatch(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询物资材料变更表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据ID查询物资材料变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public <T> T  queryDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询物资材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String query(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加物资材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String add(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询材料供应商<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvSup(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvSupList(Map<String, Object> entityMap) throws DataAccessException;
	//不查停用材料
	public String queryMatInvSupListDisable(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 批量审核数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatInv(Map<String, Object> entityMap)throws DataAccessException;
	//public String unAuditMatInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 复制 时 根据材料名称、规格 查询 材料是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvExist(Map<String, Object> mapVo) throws DataAccessException;

	public String queryMatInvCertInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 证件信息 页面 生产厂商下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosFacDict(Map<String, Object> mapVo) throws DataAccessException;


	//入库模板打印（包含主从表）
	public String queryMatInvByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 材料改变物资类别查询改类别材料数
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryChangeMatTypeCode(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 材料停用验证库存
	 */
	public String queryStopTimeByCode(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 打印所用
	 */
	public List<Map<String, Object>> printQuery(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查材料默认供应商
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatInvSupDefault(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 审核
	 * @param entityMap
	 * @return
	 * @throws DataAccessException 
	 */
	public String auditMatInvById(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 接口
	 * 添加材料修改材料时候触发
	 * 1.获取添加材料信息 
	 * 2.获取修改材料信息
	 * @author sjy
	 * @version 1.0
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String triggerMatInv(Map<String, Object> entityMap,int i) throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String  importData(Map<String, Object> entityMap)throws DataAccessException;
}
