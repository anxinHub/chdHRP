/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.repair;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.repair.AssRepairTeamDict;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_TEAM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRepairTeamDictMapper extends SqlMapper{

	List<Map<String,Object>> queryRepUser(Map<String, Object> mapVo, RowBounds rowBounds);

	List<Map<String,Object>> queryRepUser(Map<String, Object> mapVo);

	List<Map<String, Object>> querySysUserNotExists(Map<String, Object> mapVo);

	List<Map<String, Object>> querySysUserNotExists(Map<String, Object> mapVo, RowBounds rowBounds);

	int addRepairUser(Map<String, Object> mapVo);

	int queryMaxSortCode(Map<String, Object> map);

	void deleteAssRepairUser(@Param(value="map")Map<String, Object> map, @Param(value="list")List<String> listVo);

	Map<String, Object> queryUserById(Map<String, Object> mapVo);

	int updateAssRepairUser(Map<String, Object> mapVo);

	int deleteInUseRepUserByUserid(Map<String, Object> mapVo);

	int deleteInUseRepTeamByTeamCode(Map<String, Object> mapVo);

	List<String> queryRepUserByTeamCode(Map<String, Object> mapVo);

}
