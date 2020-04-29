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
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabStruc;
import com.chd.hrp.hr.entity.sysstruc.HrStoreCondition;
/**
 * 
 * @Description:
 * 人员档案库人员限定表
 * @Table:
 * HR_STORE_CONDITION
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStoreConditionMapper extends SqlMapper {
     /**
      * 添加档案库人员限定
      * @param addedlistVo
      * @return
      */
	int addStoreCondition(@Param(value="list") List<Map> addedlistVo);
    /**
     * 查询档案库人员限定
     * @param entityMap
     * @return
     */
	List<HrStoreCondition> query(Map<String, Object> entityMap);
    /**
     * 查询档案库人员限定带分页
     * @param entityMap
     * @param rowBounds
     * @return
     */
	List<HrStoreCondition> query(Map<String, Object> entityMap, RowBounds rowBounds);
   /**
    * 删除档案库人员限定
    * @param entityList
    */
	void deleteBatchStoreCondition(List<HrStoreCondition> entityList);
    /**
     * 更新档案库人员限定
     * @param modlistVo
     * @return
     */
	int updateStoreCondition(List<HrStoreCondition> modlistVo);
    /**
     * 查询档案库人员限定详细信息
     * @param entityMap
     * @return
     */
	HrStoreCondition queryStoreConditionByCode(Map<String, Object> entityMap);
	/**
	 * 查询系统结构列名
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrHosColStruc(Map<String, Object> entityMap);
	/**
	 * 条件符号查询
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrHosConSign(Map<String, Object> entityMap);
	/**
	 * 连接符号查询
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrHosJoinSign(Map<String, Object> entityMap);
	/**
	 * 职工分类查询
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrFiledData(Map<String, Object> entityMap);
	/**
	 * 查询系统结构表
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrHosTabStruc(Map<String, Object> entityMap);
	/**
	 * 修改删除
	 * @param addedlistVo
	 */
	void deleteBatchStore(List<Map> addedlistVo);
	
	/**
	 * 根据表构建信息查询代码表构建
	 * @param entityMap
	 * @return
	 */
	HrFiiedTabStruc queryHrFiiedTabStrucByTabStruc(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreConditionByPrint(Map<String, Object> entityMap);
	
	/**
	 * 查询连接条件
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrHosJoinSignName(Map<String, Object> entityMap);
	/**
	 * 查询条件
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrHosConSignName(Map<String, Object> entityMap);
	/**
	 * 查询名称
	 * @param colNameSql
	 * @return
	 */
	String queryColNam(@Param("name") String colNameSql);
	
	int queryCount(@Param("sql") StringBuffer validateSql);
	
	//查询数据表列
	List<Map<String,Object>> queryHrColStrucEntity(Map entityMap);
	
}
