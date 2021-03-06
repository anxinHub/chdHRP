﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.house;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldFHouse;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldFdetailHouse;
/**
 * 
 * @Description:
 * 050805 资产改造竣工明细表(专用设备)
 * @Table:
 * ASS_REMOULD_F_DETAIL_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldFdetailHouseMapper extends SqlMapper{

	List<Map<String, Object>> queryByFcordNoMap(Map<String, Object> mapVo);

	List<AssRemouldFHouse> queryByAssCardNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryByFcordNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryAssRemouldFDetailHouse(Map<String, Object> mapVo);

	int updatechangePriceByCardNo(Map<String, Object> mapVo);

	int deleteBatchByCardNo(List<Map<String, Object>> listVo);

	Map<String, Object> queryByAssCardNoMap(Map<String, Object> mapVo);

	List<Map<String, Object>> queryCardNoByFcordNo(Map<String, Object> mapVo);

	List<AssRemouldFdetailHouse> queryByDisANo(Map<String, Object> mapVo);

	

	
}
