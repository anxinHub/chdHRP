/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedData;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabStruc;
/**
 * 
 * @Description:
 * 代码结构表
 * @Table:
 * HR_FIIED_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrFiiedTabStrucMapper extends SqlMapper{

	List<Map<String,Object>> queryHrFiiedTabStrucTree(Map<String, Object> entityMap);

	List<Map<String, Object>> queryHrFiiedData(Map<String, Object> entityMap);

	Map<String, Object> queryHrFiiedDataById(Map<String, Object> map);

	int saveHrFiiedData(List<HrFiiedData> addlistVo);

	int updateHrFiiedData(List<Map> updatelistVo);

	int deleteHrFiiedData(List<Map> listVO);

	List<Map<String, Object>> queryColAndTabName(Map<String, Object> entityMap);

	int deleteHrFiiedDataByTabCode(@Param("vo")Map<String, Object> entityMap, @Param("list")List<HrFiiedData> listVo);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrFiiedDataByPrint(Map<String, Object> entityMap) throws DataAccessException;

	HrFiiedTabStruc queryByCodeTab(HrColStruc saveMap);

	HrFiiedTabStruc queryByCodeByName(Map<String, Object> entityMap);
	
}
