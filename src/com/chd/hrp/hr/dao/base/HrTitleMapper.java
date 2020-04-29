/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.base;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrTitle;
/**
 * 
 * @Description:
 * 职称信息
 * @Table:
 * HR_TITLE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTitleMapper extends SqlMapper{
    /**
     * 删除职称信息
     * @param entityList
     */
	void deleteBatchTitle(List<HrTitle> entityList);
    /**
     * 左侧树形查询
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryTitleInfoTree(Map<String, Object> entityMap);
	/**
	 * 查询上级部门
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> querySupCode(Map<String, Object> entityMap);
	/**
	 * 添加查询
	 * @param entityMap
	 * @return
	 */
	List<HrTitle> queryTitleById(Map<String, Object> entityMap);
	/**
	 * 查询上级编码是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryBySupCode(Map<String, Object> entityMap) throws DataAccessException;
	List<HrTitle> queryTitleByIdName(Map<String, Object> entityMap);
	/**
	 * 查询title下级有没有职称信息 
	 * @param hrTitle
	 * @return
	 */
	int querySupCodeBy(HrTitle hrTitle);
	List<HrTitle> queryBySuperCode(Map<String, Object> entityMap);
	List<HrTitle> querySuperCode(Map<String, Object> entityMap);
	void updateBatchStop(@Param(value="map") Map<String, Object> entityMap,@Param(value="list") List<HrTitle> listDept);
	void updateIsLast(Map<String, Object> entityMap);
	
}
