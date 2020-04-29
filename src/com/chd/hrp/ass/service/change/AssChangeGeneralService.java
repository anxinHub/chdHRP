/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.change;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050805 资产原值变动(一般设备)
 * @Table:
 * ASS_CHARGE_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChangeGeneralService extends SqlService {
	public String deleteAssChangeSourceGeneral(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAssChangeSourceGeneral(Map<String, Object> entityMap)throws DataAccessException;
	
	public String saveAssChangeSourceGeneral(Map<String, Object> entityMap)throws DataAccessException;
	
	public String updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 新版打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> assChangeGeneralByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询所有未确认数据单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String>  queryState(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public String updateAssChangeMainBillNo(Map<String, Object> entityMap)throws DataAccessException;
	
	
}
