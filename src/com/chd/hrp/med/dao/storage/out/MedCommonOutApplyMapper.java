/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.storage.out;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室申请单主表
 * @Table:
 * MED_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedCommonOutApplyMapper extends SqlMapper{

	/**
	 * @Description 
	 * 获取主表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryMedApplyMainSeq() throws DataAccessException;

	/**
	 * @Description 
	 * 获取明细表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryMedApplyDetailSeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedApplyMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 修改主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedApplyMain(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 修改明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedApplyDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedApplyMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedApplyDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改之前删除页面移除的明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedApplyDetailForUpdate(Map<String, Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除主表数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedApplyMainBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedApplyDetailBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单主表加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return AssInDetail
	 * @throws DataAccessException
	*/
	public <T> T queryMedApplyMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单明细加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryMedApplyDetailByCode(Map<String,Object> entityMap)throws DataAccessException;	
	
	 /**
	 * @Description 
	 * 批量审核、消审或批量<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditOrUnAuditBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量发送<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBySendBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryMatch(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 仓库药品对应关系集合<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMedStoreInvs(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 新增、修改操作 中途改变响应仓库时 查询询之前表格中已添加的药品 是否存在于当前选择的响应仓库中
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryStoreExistInv(Map<String, Object> item) throws DataAccessException;

	public List<Map<String, Object>> queryStoreInvData(List<Map<String, Object>> detailList) throws DataAccessException;
	
	public List<Map<String, Object>> queryApplyDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryApplyDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	 /**
	 * @Description 
	 * 取消发送
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatebackBySendBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	//科室申领主表批量打印
    public  List<Map<String, Object>> queryApplyPrintTemlateByMainInBatch(Map<String,Object> entityMap) throws DataAccessException;
    
    //入库明细表模板打印
  	public List<Map<String, Object>> queryMedOutPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
  	
  	//入库主表模板打印
  	public Map<String, Object> queryMedOutPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
}
