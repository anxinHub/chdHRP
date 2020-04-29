
package com.chd.hrp.mat.dao.eva;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 
public interface MatEvaTargetMapper extends SqlMapper{
	//指标类别树形查询(包含指标类别页面所有字段)
	public List<Map<String, Object>> queryMatEvaTargetTypeList(Map<String, Object> map) throws DataAccessException;
	//指标类别查询是否已维护指标
	public Integer queryMatEvaTargetBySuperType(Map<String, Object> map) throws DataAccessException;
	//指标类别保存
	public int addMatEvaTargetType(Map<String, Object> map) throws DataAccessException;
	public int updateMatEvaTargetType(Map<String, Object> map) throws DataAccessException;
	//指标类别修改是否末级
	public int updateMatEvaTargetTypeIsLast(Map<String, Object> map) throws DataAccessException;
	//指标类别删除
	public int deleteMatEvaTargetType(Map<String, Object> map) throws DataAccessException;
	//查询指标类别用于导入
	public List<Map<String, Object>> queryMatEvaTargetTypeForImport(Map<String, Object> map) throws DataAccessException;
	
	//标准标度查询
	public List<Map<String, Object>> queryMatEvaScaleList(Map<String, Object> map) throws DataAccessException;
	//标准标度保存
	public int addMatEvaScale(Map<String, Object> map) throws DataAccessException;
	public int updateMatEvaScale(Map<String, Object> map) throws DataAccessException;
	//标准标度删除
	public int deleteMatEvaScale(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//指标查询
	public List<Map<String, Object>> queryMatEvaTargetList(Map<String, Object> map) throws DataAccessException;
	//指标保存
	public int addMatEvaTarget(Map<String, Object> map) throws DataAccessException;
	public int updateMatEvaTarget(Map<String, Object> map) throws DataAccessException;
	//指标删除
	public int deleteMatEvaTarget(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//指标导入
	public int addMatEvaTargetByImp(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//查询指标用于导入
	public List<Map<String, Object>> queryMatEvaTargetForImport(Map<String, Object> map) throws DataAccessException;
		
	//指标标度查询
	public List<Map<String, Object>> queryMatEvaTargetScaleList(Map<String, Object> map) throws DataAccessException;
	//指标标度删除
	public int deleteMatEvaTargetScale(Map<String, Object> map) throws DataAccessException;
	//指标标度保存
	public int addMatEvaTargetScale(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//指标标度引用标准标度
	public int addMatEvaTargetScaleByBZ(Map<String, Object> map) throws DataAccessException;
	//批量删除指标标度
	public int deleteMatEvaTargetScaleBatch(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//批量删除指标后应修改指标变更表为停用
	public int updateMatEvaTargetScaleDBatchByStop(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//批量设置指标标度保存
	public int addMatEvaTargetScaleBatch(@Param(value="map")Map<String, Object> map, @Param(value="targetList")List<Map<String, Object>> targetList, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	
	//指标扣分项查询
	public List<Map<String, Object>> queryMatEvaTargetDeductList(Map<String, Object> map) throws DataAccessException;
	//指标扣分项保存
	public int addMatEvaTargetDeduct(Map<String, Object> map) throws DataAccessException;
	public int updateMatEvaTargetDeduct(Map<String, Object> map) throws DataAccessException;
	//指标扣分项删除
	public int deleteMatEvaTargetDeduct(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//指标扣分项批量删除
	public int deleteMatEvaTargetDeductBatch(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	//判断扣分项是否被供应商评价引用
	public Integer existsMatEvaSupDeductByTargetDeduct(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
}
