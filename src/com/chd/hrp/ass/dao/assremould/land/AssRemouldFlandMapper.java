/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.land;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(土地)
 * @Table:
 * ASS_REMOULD_F_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldFlandMapper extends SqlMapper{

	Map<String,Object> collectAssBackLand(Map<String, Object> entityMap);

	int updateBackMoney(Map<String, Object> entityMap);

	List<Map<String, Object>> queryCardNoByFcordNo(Map<String, Object> mapVo);

	int updateConfirmAssRemouldFland(List<Map<String, Object>> listVo);
	
}
