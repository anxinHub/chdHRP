/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.house;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(专用设备)
 * @Table:
 * ASS_CHK_IN_MAIN_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkInMainHouseMapper extends SqlMapper{

	int updateInMoney(Map<String, Object> entityMap)throws DataAccessException;

	int updateConfirmChkInHouse(List<Map<String, Object>> listVo)throws DataAccessException;

	Integer queryBydept(Map<String, Object> entityMap);

	Integer queryByRdept(Map<String, Object> entityMap);
	
	List<Map<String, Object>> queryAssChkInHouseByAssInNo(Map<String, Object> map);

	List<Map<String, Object>> queryAssChkInHouseDetailByAssInNo(Map<String, Object> map);

	List<String> queryAssChkInHouseStates(Map<String, Object> mapVo);

}
