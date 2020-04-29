/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.info.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.info.basic.MedJx;
import com.chd.hrp.med.entity.info.basic.MedSx;
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
 

public interface MedInvMapper extends SqlMapper{

	/**
	 * @Description 
	 * 获取材料表自增序列
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public int queryMedInvSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取材料表自增序列
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public int queryMedInvSeqCur() throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询药品材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询药品材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryByCodes(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询药品材料表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int add(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询药品材料ByCode<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMedInvSupbyCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 材料供应商结果集<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedInvSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedInvSupList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedInvSupList(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
	
public List<Map<String, Object>> queryMedInvSupListDisable(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商列表<BR> 
	 * @param  entityMap<BR>
	 *  不查停用材料
	 * @return MedInvDict
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedInvSupListDisable(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * @Description 
	 * 新增供应商和材料对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int addMedInvSup(List<Map<String,Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 删除供应商和材料对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteMedInvSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除供应商和材料对应关系<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteMedInvSupBatch(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据是否默认供应商删除材料供应商信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteMedInvSupByDefault(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量修改材料供应商为非默认<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public int updateMedInvSupIsNotDefault(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	 /**
		 * @Description 
		 * 批量审核数据<BR> 
		 * @param  entityMap
		 * @return int
		 * @throws DataAccessException
		*/
		public int auditMedInv(List<Map<String, Object>> entityMap)throws DataAccessException;
		/**
		 * @Description 
		 * 获取材料分类中最大的材料编码 
		 * @param  entityMap
		 * @return String
		 * @throws DataAccessException
		*/
		public String queryMedInvMaxByType(Map<String,Object> entityMap) throws DataAccessException;

		/**
		 * 复制 时 根据材料名称、规格 查询 材料是否已存在
		 * @param mapVo
		 * @return
		 * @throws DataAccessException
		 */
		public int queryMedInvExist(Map<String, Object> mapVo) throws DataAccessException;

		public List<Map<String, Object>> queryMedInvCertInfo(Map<String, Object> entityMap) throws DataAccessException;
		
		public int queryMedInvSupCode(Map<String, Object> mapVo) throws DataAccessException;
		
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
		public Map<String, Object> queryMedInvPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		//入库主表批量模板打印
		public List<Map<String, Object>> queryMedInvPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
		
		//入库明细表模板打印
		public List<Map<String, Object>> queryMedInvPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

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
		public List<Map<String, Object>> queryMedInvInSup(List<Map<String, Object>> entityMap) throws DataAccessException;
		public List<Map<String, Object>> queryMedInvNotInSup(List<Map<String, Object>> entityMap) throws DataAccessException;

		public void updateMedInvSupIsDefault(List<Map<String, Object>> entityMap) throws DataAccessException;

		//材料改变药品类别查询改类别材料数
		public List<Map<String,Object>> queryChangeMedTypeCode(Map<String, Object> entityMap)throws DataAccessException;
		
		//材料停用查看库存剩余
		public List<Map<String,Object>> queryStopTimeByCode(Map<String, Object> entityMap)throws DataAccessException;
		
		//获取药品属性
		public List<MedSx> queryMedSx(Map<String,Object> entityMap) throws DataAccessException;
		
		//获取药品剂型
		public List<MedJx> queryMedJx(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询 用于导出
		public List<Map<String,Object>> queryMedInvList(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询药品用于导入
		public List<Map<String,Object>> queryMedInvListForImport(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询药品分类用于导入
		public List<Map<String,Object>> queryMedTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
				
		//查询药品属性用于导入
		public List<Map<String,Object>> queryMedSxListForImport(Map<String,Object> entityMap) throws DataAccessException;	
		
		//查询药品剂型用于导入
		public List<Map<String,Object>> queryMedJxListForImport(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询计量单位用于导入
		public List<Map<String,Object>> queryHosUnitListForImport(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询生产厂商用于导入
		public List<Map<String,Object>> queryHosFacListForImport(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询供应商用于导入
		public List<Map<String,Object>> queryHosSupListForImport(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询管理类别用于导入
		public List<Map<String,Object>> queryManaTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;

		public Integer queryMaxInv_id() throws DataAccessException;
}
