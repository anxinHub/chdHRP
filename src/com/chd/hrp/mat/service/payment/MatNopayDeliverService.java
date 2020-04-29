/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.payment;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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
 
public interface MatNopayDeliverService {
	
	/**
	 * @Description 
	 * 根据主表ID查询明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatNopayDeliverDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量审核、消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateStateMatNopayDeliver(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * 查询数据  期初未付款送货单入库
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatNopayDeliver(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据主键加载数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public <T> T queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 添加数据  期初未付款送货单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addMatNopayDeliver(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改页面 回值查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatNopayDeliverMainUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 上一张 、下一张 送货单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatNopayDeliverBeforeOrNextNo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新数据  期初未付款送货单入库
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatNopayDeliver(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 批量删除数据  期初未付款送货单入库
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMatNopayDeliverBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 复制
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String copyMatNopayDeliverBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 查询期初未付款 送货单 是否已生成发票
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBillOrNot(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 导入数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> impData(Map<String, Object> mapVo) throws DataAccessException;


	/**
	 * @Description 生成发票
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public Map<String, Object> addMatBillByBill(Map<String,Object> entityMap) throws DataAccessException;
	public Map<String, Object> addMatBillByBillBatch(List<Map<String, Object>> entityList) throws DataAccessException;
}
