/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.nursing;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrLevelSet;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_LEVEL_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrLevelSetMapper extends SqlMapper{

   /**
    * 
    * 添加的时候删除
    * @param entityList
    */
	void deleteHLevelSet(Map<String, Object> entityMap);
	/**
	 * 删除目标设置
	 * @param entityList
	 */
	void deleteLevelSet(List<HrLevelSet> entityList);
	/**
	 * 生成不带分页
	 * @param entityMap
	 * @return
	 */
	List<HrLevelSet> generateLevelSet(Map<String, Object> entityMap);
	/**
	 * 生成带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrLevelSet> generateLevelSet(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 继承不带分页
	 * @param entityMap
	 * @return
	 */
	int copyLevelSet(Map<String, Object> entityMap);
	/**
	 * 继承带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrLevelSet> copyLevelSet(Map<String, Object> entityMap, RowBounds rowBounds);
	List<HrLevelSet> queryByCodeLevelSet(Map<String, Object> entityMap);

	
}
