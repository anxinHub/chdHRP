/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.mednopaydeliver;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 08105 药品药品表
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedNopayDeliverService {
	
	/**
	 * @Description 
	 * 根据主表ID查询明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedNopayDeliverDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量审核、消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateStateMedNopayDeliver(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * 查询数据  期初未付款送货单入库
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedNopayDeliver(Map<String, Object> entityMap) throws DataAccessException;
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
	public String addMedNopayDeliver(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改页面 回值查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedNopayDeliverMainUpdate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 上一张 、下一张 送货单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedNopayDeliverBeforeOrNextNo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 更新数据  期初未付款送货单入库
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedNopayDeliver(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 批量删除数据  期初未付款送货单入库
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMedNopayDeliverBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 复制
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String copyMedNopayDeliverBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 查询期初未付款 送货单 是否已生成发票
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBillOrNot(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedNopayDeliveryByPrintTemlate1(Map<String, Object> entityMap)throws DataAccessException;
}
