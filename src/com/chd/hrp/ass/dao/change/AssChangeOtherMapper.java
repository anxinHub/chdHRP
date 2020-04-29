/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.change;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产原值变动(其他固定资产)
 * @Table:
 * ASS_CHARGE_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChangeOtherMapper extends SqlMapper{
	public int updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 原值变动主表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAssChangeOtherPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	 
	/**
	 * 原值变动主表批量模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssChangeOtherPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 原值变动明细表模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssChangeOtherPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryState(Map<String,Object> entityMap) throws DataAccessException;
	
	
	int updateAssChangeMainBillNo(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
