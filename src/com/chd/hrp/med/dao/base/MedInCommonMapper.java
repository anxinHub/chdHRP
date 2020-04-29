/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedInResource;
/**
 * 
 * @Description:
 * 常备药品期初入库
 * @Table:
 * MED_IN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedInCommonMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedInMain(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量添加主表数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedInMainBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedInDetailBatch(List<Map<String, Object>> entityList)throws DataAccessException;

	/**
	 * @Description 
	 * 修改主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedInMain(Map<String, Object> entityMap)throws DataAccessException;
	//温州：更新冲账状态
	public int updateMedInMainRaState(Map<String, Object> entityMap)throws DataAccessException;
	public int updateMedInMainRaStateNull(List<Map<String, Object>> entityList)throws DataAccessException;

	/**
	 * @Description 
	 * 修改明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedInDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除主表数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改之前删除页面移除的明细数据<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInDetailForUpdate(Map<String, Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除主表数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInMainBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除明细数据<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInDetailBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单主表加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return <T> T
	 * @throws DataAccessException
	*/
	public <T> T queryMedInMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单明细加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryMedInDetailByCode(Map<String,Object> entityMap)throws DataAccessException;	
	
	/**
	 * @Description 
	 * 查询资金来源
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public MedInResource queryMedInResource(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询资金来源列表
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<MedInResource> queryMedInResourceList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 插入入库资金来源
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertMedInResource(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 插入入库资金来源
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertMedInResourceBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 修改入库资金来源
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedInResource(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 删除入库资金来源
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInResource(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 批量删除入库资金来源
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInResourceBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 获取主表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryMedInMainSeq() throws DataAccessException;

	/**
	 * @Description 
	 * 获取明细表自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long queryMedInDetailSeq() throws DataAccessException;

	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  Map(group_id,hos_id,copy_code,in_ids)<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmCommonIn(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @param <T>
	 * @Description 
	 * 根据Ids加载入库单<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryMedInMainByIds(Map<String,Object> entityMap)throws DataAccessException;	
	
	/**
	 * @param <T>
	 * @Description 
	 * 入库单明细加载<BR> 
	 * @param  entityMap <BR>
	 * 参数为主键
	 * @return List<AssInDetail>
	 * @throws DataAccessException
	*/
	public List<?> queryMedInDetailByIds(Map<String,Object> entityMap)throws DataAccessException;	
	
	/**
	 * @Description 
	 * 获取上一张入库单ID
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInBefore(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取下一张入库单ID
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInNext(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 入库确认更改状态
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedInMainConfirm(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * 维护发票页面跳转查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiInMainByInvoice(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 批量判断入库单是否可删除、修改
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMedInMainStateBatch(List<Map<String,Object>> entityList) throws DataAccessException;
	
	//关系表
	public int addRelaBatch(List<?> entityMap)throws DataAccessException;

	public String confirmCommonInBack(Map<String, Object> entityMap) throws DataAccessException;

	public int deleteMedInAmountBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public List<Map<String, Object>> queryInvInDetail(Map<String, Object> mapVo);

}
