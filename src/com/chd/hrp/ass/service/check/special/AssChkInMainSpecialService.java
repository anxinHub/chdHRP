/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.check.special;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(专用设备)
 * @Table:
 * ASS_CHK_IN_MAIN_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkInMainSpecialService extends SqlService {

	public String initAssChkInCardSpecial(Map<String, Object> mapVo)throws DataAccessException;
	
	public String initAssChkInBatchCardSpecial(Map<String, Object> mapVo)throws DataAccessException;

	public String updateConfirmChkInSpecial(List<Map<String, Object>> listVo)throws DataAccessException;

	/**
	 * 新版打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> assChkInMainSpecialByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询所有未确认数据单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String>  queryState(Map<String, Object> entityMap) throws DataAccessException;
	
}
