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
 * @Description:
 * 付款单
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatPayMapper extends SqlMapper{
    
	//取主表序列
	public Long queryPayMainSeq() throws DataAccessException;
	//取明细表序列
	public Long queryPayDetailSeq() throws DataAccessException;
	
	//主查询
	public List<Map<String, Object>> queryMatPayList(Map<String, Object> map)throws DataAccessException;
	public List<Map<String, Object>> queryMatPayList(Map<String, Object> map, RowBounds rowBounds)throws DataAccessException;
	
	//加载主表
	public Map<String, Object> queryMatPayMainById(Map<String, Object> map)throws DataAccessException;
	//加载明细表
	public List<Map<String, Object>> queryMatPayDetailById(Map<String, Object> map) throws DataAccessException;
	
	//保存主表
	public int addMatPayMain(Map<String, Object> map) throws DataAccessException;
	//修改主表
	public int updateMatPayMainById(Map<String, Object> map) throws DataAccessException;
	//保存明细表
	public int addMatPayDetail(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	
	//校验发票状态
	public int existsMatPayState(Map<String, Object> map) throws DataAccessException;
	public int existsMatPayStateBatch(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//删除发票
	public int deleteMatPay(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//删除发票明细（保存时使用）
	public int deleteMatPayDetailById(Map<String, Object> map) throws DataAccessException;
	
	//查询发票用于添加发票明细
	public List<Map<String, Object>> queryMatBillByPay(Map<String, Object> map) throws DataAccessException;
	public List<Map<String, Object>> queryMatBillByPay(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryMatBillDetailByPay(Map<String, Object> map) throws DataAccessException;
	//根据所选入库单生成付款单明细
	public List<Map<String, Object>> queryMatPayDetailByBill(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//审核or消审
	public int updateMatPayState(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//批量修改备注
	public int updateMatPayNote(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//判断付款单是否已生成凭证
	public String existsVouchByMatPay(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//审核or消审后修改发票的付款金额
	public int updateMatBillMoneyByPay(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;

	//主表模板打印
	public Map<String, Object> queryMatPayMainPrintByTemlate(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatPayMainPrintByTemlateBatch(Map<String,Object> entityMap) throws DataAccessException;
	//明细表模板打印
    public List<Map<String, Object>> queryMatPayDetailPrintByTemlate(Map<String,Object> entityMap) throws DataAccessException;

    //查询付款单明细打印数据-主表
	public Map<String, Object> queryMatPayDetailByPrintByMain(Map<String, Object> entityMap) throws DataAccessException;
	//查询付款单明细打印数据-明细
	public List<Map<String, Object>> queryMatPayDetailByPrintByDetail(Map<String, Object> entityMap) throws DataAccessException;
}
