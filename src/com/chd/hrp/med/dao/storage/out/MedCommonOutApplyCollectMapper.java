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
 
public interface MedCommonOutApplyCollectMapper extends SqlMapper{

	/**
	 * @param <T>
	 * @Description 
	 * 出库单主表组装<BR> 
	 * @param  entityMap <BR>
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryOutMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询管理部门对应的仓库信息<BR> 
	 * @param  entityMap <BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryStoreByDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 调拨单主表组装<BR> 
	 * @param  entityMap <BR>
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryTranMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取选中的明细数据<BR> 
	 * @param  entityMap <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> querySelectDetailForOut(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室需求计划主表组装<BR> 
	 * @param  entityMap <BR>
	 * @return List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryReqMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室需求计划明细表组装<BR> 
	 * @param  entityMap <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryReqDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 药品退回主表查询<BR> 
	 * @param  entityList <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMainByBack(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 药品退回明细表查询<BR> 
	 * @param  entityList <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDetailByBack(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 药品退回修改明细状态<BR> 
	 * @param  entityMap <BR>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDetailByBack(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 汇总生成采购或需求计划前校验是否含停用药品<BR> 
	 * @param  entityList <BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsContainsInvIsStop(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 汇总生成科室需求计划查询<BR> 
	 * @param  entityList <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryReqByCollect(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 汇总生成采购计划查询<BR> 
	 * @param  entityList <BR>
	 * @return List<？>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryPurByCollect(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 采购计划主表序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedPurMainSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划明细序列<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedPurDetailSeqNext() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加采购计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedPurMainByApp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加采购计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedPurMainByAppBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加采购计划明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedPurDetailByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除采购计划主表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedPurMainByApp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除采购计划明细表<BR> 
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedPurDetailByApp(List<Map<String, Object>> entityList) throws DataAccessException;

	public List<Map<String, Object>> queryMedApplyCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedApplyCloseInvInfo(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 取消关闭药品
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateMedApplyCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	
}
