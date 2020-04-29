/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 

public interface MatInvMapper extends SqlMapper{

	/**
	 * @Description 
	 * 获取材料表自增序列
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public int queryMatInvSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取材料表自增序列
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public int queryMatInvSeqCur() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询物资材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询物资材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryByCodes(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询物资材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int add(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询物资材料ByCode<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMatInvSupbyCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 材料供应商结果集<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatInvSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatInvSupList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatInvSupList(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
	
	public List<Map<String, Object>> queryMatInvSupListDisable(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 *  不查停用材料
	 * @return MatInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatInvSupListDisable(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 新增供应商和材料对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int addMatInvSup(List<Map<String,Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 删除供应商和材料对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteMatInvSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除供应商和材料对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteMatInvSupBatch(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据是否默认供应商删除材料供应商信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteMatInvSupByDefault(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量修改材料供应商为非默认<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int updateMatInvSupIsNotDefault(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量审核数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditMatInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取材料分类中最大的材料编码 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatInvMaxByType(Map<String,Object> entityMap) throws DataAccessException;
	public String queryMatInvMaxByTypes(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 复制 时 根据材料名称、规格 查询 材料是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryMatInvExist(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatInvCertInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	public int queryMatInvSupCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 证件信息 查询对应的 供应商信息
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCertSup(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 证件信息 页面 生产厂商下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHosFacDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//入库主表模板打印
	public Map<String, Object> queryMatInvPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	//入库主表批量模板打印
	public List<Map<String, Object>> queryMatInvPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库明细表模板打印
	public List<Map<String, Object>> queryMatInvPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 导入过程中获取已插入的材料ID 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String getInvIdByImp(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查看材料中哪些材料有sup_id
	 * @param invList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvInSup(List<Map<String, Object>> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatInvNotInSup(List<Map<String, Object>> entityMap) throws DataAccessException;

	public void updateMatInvSupIsDefault(List<Map<String, Object>> entityMap) throws DataAccessException;

	//材料改变物资类别查询改类别材料数
	public List<Map<String,Object>> queryChangeMatTypeCode(Map<String, Object> entityMap)throws DataAccessException;
	
	//材料停用查看库存剩余
	public List<Map<String,Object>> queryStopTimeByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
	//台州接口需求
	//查询HRP_MAT_INV表是否存在
	public int queryTableIsExit(Map<String, Object> entityMap)throws DataAccessException;
	
	//查询HRP_MAT_INV表材料是否存在
	public Map<String,Object> queryHrpMatInvByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	//更新HRP_MAT_INV
	public int updateHrpMatInv(Map<String, Object> entityMap)throws DataAccessException;
	
	//新增HRP_MAT_INV
	public int addHrpMatInv(Map<String, Object> entityMap)throws DataAccessException;
	
	//批量更新HRP_MAT_INV现行状态
	public int updateBatchHrpMatInvState(List<Map<String, Object>> entityList)throws DataAccessException;
	//查询材料默认供应商
	public Map<String, Object> queryMatInvSupDefault(Map<String, Object> entityMap) throws DataAccessException; 
	
	//更新HRP_MAT_INV
	public int updateDictMatInv(Map<String, Object> entityMap)throws DataAccessException;
	
	//新增HRP_MAT_INV
	public int addDictMatInv(Map<String, Object> entityMap)throws DataAccessException;

	//查询DICT_MAT_INV表材料是否存在
	public Map<String, Object> queryDictMatInvByCode(Map<String, Object> matInvMap)throws DataAccessException;


	//查询药品用于导入
	public List<Map<String,Object>> queryMatInvListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询药品分类用于导入
	public List<Map<String,Object>> queryMatTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
			
	//查询药品属性用于导入
	public List<Map<String,Object>> queryMatSxListForImport(Map<String,Object> entityMap) throws DataAccessException;	
	
	//查询药品剂型用于导入
	public List<Map<String,Object>> queryMatJxListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询计量单位用于导入
	public List<Map<String,Object>> queryHosUnitListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询生产厂商用于导入
	public List<Map<String,Object>> queryHosFacListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询供应商用于导入
	public List<Map<String,Object>> queryHosSupListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	//查询管理类别用于导入
	public List<Map<String,Object>> queryManaTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	//获取物资类别下最大材料编码
	public Map<String, String> getMaxInvCodeByType(Map<String, Object> map) throws DataAccessException;
}
