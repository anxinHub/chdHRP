package com.chd.hrp.ass.dao.repair.assmassrepairsche;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;

public interface AssMassRepairScheMapper extends SqlMapper{

	public List<Map<String, Object>> queryAssrRepTeamTree(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryAssRepUser(Map<String, Object> mapVo);

	public int updateAssRepairUser(Map<String, Object> mapVo);

	public Map<String, Object> queryAssRepairUserByCode(Map<String, Object> mapVo);

	public int updateWorkDay(Map<String, Object> mapVo);

	public int addRepUser(Map<String, Object> mapVo);


	public int queryMaxSortCode(Map<String, Object> mapVo);

	public int initRepUser(Map<String, Object> mapVo);

	public int deleteRepUser(List<Map> listVo);

	public Map<String, Object> queryAssRepairScheByCode(Map<String, Object> mapVo);

	public int updateUserSort(List<Map> listVo);

}
