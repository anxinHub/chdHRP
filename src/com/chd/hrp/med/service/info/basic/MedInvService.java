/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedInv;
/**
 * 
 * @Description:
 * 08105 药品材料表
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 
public interface MedInvService extends SqlService {

	/**
	 * @Description 
	 * 添加材料供应商数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedInvSup(Map<String,Object> entityMap)throws DataAccessException;
	
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
	public String updateMedInvBatch(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量添加材料供应商数据<BR> 
	 * @param  entityList
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedInvSupBatch(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询药品材料变更表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据ID查询药品材料变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public <T> T  queryDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询药品材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String query(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加药品材料表<BR> 
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
	public String queryMedInvSup(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInvSupList(Map<String, Object> entityMap) throws DataAccessException;
	//不查停用材料
	public String queryMedInvSupListDisable(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 批量审核数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 复制 时 根据材料名称、规格 查询 材料是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInvExist(Map<String, Object> mapVo) throws DataAccessException;

	public String queryMedInvCertInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 证件信息 页面 生产厂商下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosFacDict(Map<String, Object> mapVo) throws DataAccessException;


	//入库模板打印（包含主从表）
	public String queryMedInvByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 材料改变药品类别查询改类别材料数
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryChangeMedTypeCode(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 材料停用验证库存
	 */
	public String queryStopTimeByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 *  查询 用于导出 
	 */
	public List<Map<String,Object>> queryMedInvList(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String  importData(Map<String, Object> entityMap)throws DataAccessException;
}
