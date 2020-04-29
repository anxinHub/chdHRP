/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.dura.check;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description: 耐用品科室盘点 
 * @Table: MAT_DURA_DEPT_(CHECK/CHECK_D) 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 
public interface MatDuraCheckDeptService{
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String query(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String add(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String update(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityList
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 加载主表数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public <T> T queryMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 加载明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量审核或消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditOrUnAuditBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmBatch(List<Map<String, Object>> entityList)throws DataAccessException;

	/**
	 * @Description 
	 * 模板打印（包含主从表）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDataByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询数据 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatDuraCheckByDeptId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 引入科室材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatDuraCheckByDeptInvDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 生成出入库单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String addInOut(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 盘点主页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatDuraDeptMain(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 盘盈 盘亏单主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDuraDeptMain(Map<String, Object> entityMap) throws DataAccessException;
	//public <T> T queryDuraDeptMain(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 盘盈 盘亏单明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatDuraDeptDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 审核、销审 盘盈单  盘亏单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String auditOrUnAuditDeptMainBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 盘盈 盘亏单出入库确认
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String confirmMatDuraDeptMain(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 盘点单打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatDuraDeptByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询当前账表材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvByBalance(Map<String, Object> entityMap) throws DataAccessException;

}
