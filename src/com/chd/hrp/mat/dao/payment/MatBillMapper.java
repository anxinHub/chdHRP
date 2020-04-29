/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.payment;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/** 
 * 
 * @Description:
 * 采购发票
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatBillMapper extends SqlMapper{
    
	//取主表序列
	public Long queryBillMainSeq() throws DataAccessException;
	//取明细表序列
	public Long queryBillDetailSeq() throws DataAccessException;
	
	//主查询
	public List<Map<String, Object>> queryMatBillList(Map<String, Object> map)throws DataAccessException;
	public List<Map<String, Object>> queryMatBillList(Map<String, Object> map, RowBounds rowBounds)throws DataAccessException;
	
	//加载主表
	public Map<String, Object> queryMatBillMainById(Map<String, Object> map)throws DataAccessException;
	//加载明细表
	public List<Map<String, Object>> queryMatBillDetailById(Map<String, Object> map) throws DataAccessException;
	
	//根据发票号查询发票信息
	public Map<String, Object> queryMatBillMainByBillNo(Map<String, Object> map) throws DataAccessException;
	//查询发票号是否重复
	public int queryBillNoExists(Map<String, Object> map) throws DataAccessException;
	
	//保存主表
	public int addMatBillMain(Map<String, Object> map) throws DataAccessException;
	//修改主表
	public int updateMatBillMainById(Map<String, Object> map) throws DataAccessException;
	//保存明细表
	public int addMatBillDetail(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//根据发票ID修改发票主表金额
	public int updateMatBillMoneyById(Map<String, Object> map)throws DataAccessException;
	
	//校验发票状态
	public int existsMatBillState(Map<String, Object> map) throws DataAccessException;
	public int existsMatBillStateBatch(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//删除发票
	public int deleteMatBill(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//删除发票明细（保存时使用）
	public int deleteMatBillDetailById(Map<String, Object> map) throws DataAccessException;
	
	//查询入库单用于添加发票明细
	public List<Map<String, Object>> queryMatInByBill(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatInByBill(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryMatInDetailByBill(Map<String, Object> map) throws DataAccessException;
	//根据所选入库单生成采购发票明细
	public List<Map<String, Object>> queryMatBillDetailByIn(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//审核or消审
	public int updateMatBillState(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//批量修改备注
	public int updateMatBillNote(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//反写入库单发票信息
	public int updateMatInMainInvoiceBatch(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//反写专购品发票信息
	public int updateMatSpecialMainInvoiceBatch(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//判断发票是否已生成凭证
	public String existsVouchByMatBill(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//判断入库单是否已生成凭证
	public String existsVouchByMatIn(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;

	//根据主表主键查询发票所设计到的入库单
	public List<Map<String, Object>> queryMatInIdByBillId(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//主表模板打印
	public Map<String, Object> queryMatBillMainPrintByTemlate(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatBillMainPrintByTemlateBatch(Map<String,Object> entityMap) throws DataAccessException;
	//明细表模板打印
    public List<Map<String, Object>> queryMatBillDetailPrintByTemlate(Map<String,Object> entityMap) throws DataAccessException;

	//查询 发票是否已付款 
	public int queryPayOrNot(Map<String, Object> mapVo)throws DataAccessException;
}
