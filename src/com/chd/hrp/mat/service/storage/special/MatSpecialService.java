/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.special;

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
 
public interface MatSpecialService  extends SqlService {
	
	/**
	 * @Description 
	 * 根据主表ID查询明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatSpecialDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMatSpecialBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量冲账<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String offsetMatSpecialBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量审核、消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateState(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String addAffiRela(List<Map<String, Object>> entityMap)throws DataAccessException;


	/**
	 * @Description 
	 * 订单结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryOrder(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 订单明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryOrderDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 代销出库单结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAffiOut(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 协议结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryProtocol(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询数据  专购品入库
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSpecial(Map<String, Object> page) throws DataAccessException;
	/**
	 * 修改页面 回值查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatSpecialMainUpdate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据专购品主表ID 查询专购品  出入库单Id、出入库单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatInOutData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 上一张 、下一张 专购品查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSpecialBeforeOrNextNo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据 专购品主表Id 查询 专购品明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySpecialDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 代销出库单 明细数据  查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiDetail(Map<String, Object> page) throws DataAccessException;
	/**
	 * 更改代销出库单  是否已生成专购品  状态
	 * @param entityMap
	 * @return 
	 * @throws DataAccessException
	 */
	public int updateAffiOutState(Map<String, Object> entityMap) throws DataAccessException;
	
	//入库模板打印（包含主从表）
	public String queryMatSpecialByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销使用生成专购品
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String addByAffiOut(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销生成专购品 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiSpecial(Map<String, Object> entityMap)  throws DataAccessException;

	/**
	 * @Description 
	 * 更新发票号<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateMatSpecialInvoice(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSpecailInvBatch(Map<String, Object> entityMap) throws DataAccessException;
	/***
	 * 添加
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatSpecailInvDetail(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 解析前台表格数据为josn格式
	 * @param entityMap
	 * @param entityMap2 
	 * @return
	 * @throws DataAccessException
	 */
	public String importMatCommonInsup(Map<String, Object> entityMap, Map<String, Object> entityMap2) throws DataAccessException;

	public Map<String,Object> queryMatSpecialByPrintTemlate1(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量复制<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyMatSpecialBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

}
