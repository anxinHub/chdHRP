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
import com.chd.hrp.hr.entity.sysstruc.HrStoreTabStruc;
/**
 * 
 * @Description:
 * 人员档案库数据集表
 * @Table:
 * HR_STORE_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStoreTabStrucMapper  extends SqlMapper{
	/**
     * 根据档案编码查询
     */
	List<HrStoreTabStruc> queryStoreTabStrucByCode(Map<String, Object> entityMap);
	/**
     * 根据档案编码查询带分页
     */
	List<HrStoreTabStruc> queryStoreTabStrucByCode(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 快速定位
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<Map<String, Object>> queryStoreTabStrucTree(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 */
	List<?> queryStoreTabStrucByTree(Map<String, Object> entityMap);
	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryHrHosTabStruc(Map<String, Object> entityMap);
	/**
	 * 查询档案库数据集配置
	 * @param entityList
	 */

	List<HrStoreTabStruc> query(Map<String, Object> entityMap);
	/**
	 * 查询档案库数据集配置 带分页
	 * @param entityList
	 */
	List<HrStoreTabStruc> query(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 删除档案库数据集配置
	 * @param entityList
	 */
	void deleteBatchHrColStruc(List<HrStoreTabStruc> entityList);
	/**
	 * 修改档案库数据集配置
	 * @param entityList
	 * @return 
	 */
//	int update(Map<String, Object> entityMap);
	/**
	 * 更新跳转查询档案库数据集配置
	 * @param entityList
	 */
	HrStoreTabStruc queryByCodeHrColStruc(Map<String, Object> entityMap);
	/**
	 * 增加档案库数据集配置
	 * @param entityList
	 */
	void addBatchHrColStruc(List<HrStoreTabStruc> addlistVo);
	/**
	 * 修改档案库数据集配置
	 * @param entityList
	 */
	void updateBatchHrColStruc(List<HrStoreTabStruc> modlistVo);
	/**
	 * 添加时候判断数据是否重复
	 * @param addlistVo
	 * @return
	 */
	List<HrStoreTabStruc> queryById(List<HrStoreTabStruc> addlistVo);
	/**
	 * 修改增加删除
	 * @param addlistVo
	 */
	void deleteBatchHrCol(List<HrStoreTabStruc> addlistVo);
	
	/**
	 * 根据档案库分类删除数据配置信息
	 * @param entityMap
	 * @param alllistVo 
	 */
	void deleteHrColStrucBySTypeCode(@Param("vo")Map<String, Object> entityMap,@Param("list")List<HrStoreTabStruc> alllistVo);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrStoreTabStrucByPrint(Map<String, Object> entityMap);
	
	//批量添加
	int addStoreTabBatch(@Param("vo")Map<String, Object> mapVo, @Param("list")List<String> arrlist);
	
}
