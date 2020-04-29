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
 * 050805 资产原值变动(土地)
 * @Table:
 * ASS_CHARGE_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChangeLandService extends SqlService {
	String deleteAssChangeSourceLand(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	String queryAssChangeSourceLand(Map<String, Object> entityMap)throws DataAccessException;
	
	String saveAssChangeSourceLand(Map<String, Object> entityMap)throws DataAccessException;
	
	String updateConfirm(List<Map<String, Object>> entityMap)throws DataAccessException;

	Map<String, Object> printAssChangeLandData(Map<String, Object> map) throws DataAccessException;

	List<String> queryAssChangeLandStates(Map<String, Object> mapVo);
}
