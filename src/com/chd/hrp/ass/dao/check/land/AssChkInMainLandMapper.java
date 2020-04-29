/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.land;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(土地)
 * @Table:
 * ASS_CHK_IN_MAIN_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkInMainLandMapper extends SqlMapper{

	int updateInMoney(Map<String, Object> entityMap)throws DataAccessException;

	int updateConfirmChkInLand(List<Map<String, Object>> listVo)throws DataAccessException;

	Integer queryBydept(Map<String, Object> entityMap);

	Integer queryByRdept(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssChkInLandByAssInNo(Map<String, Object> map);

	List<Map<String, Object>> queryAssChkInLandDetailByAssInNo(Map<String, Object> map);

	List<String> queryAssChkInLandStates(Map<String, Object> mapVo);
	
}
