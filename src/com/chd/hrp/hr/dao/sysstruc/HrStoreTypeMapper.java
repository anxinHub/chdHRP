/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrStoreType;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
/**
 * 
 * @Description:
 * 人员档案库分类
 * @Table:
 * HR_STORE_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStoreTypeMapper extends SqlMapper{

	void deleteBatchHrStoreType(List<HrStoreType> entityList);
    /**
     * 查询档案库配置表
     * @param entityMap
     * @return
     */
	List<HrTabStruc> queryHrHosTabStruc(Map<String, Object> entityMap);
	
	/**
	 * 左侧树形
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStoreTabStrucTree(Map<String, Object> entityMap);
	/**
	 * 查指定name已经存在的记录
	 * @param entityMap
	 * @return
	 * @author yangyunfei
	 */
	HrStoreType queryExistsByName(Map<String, Object> entityMap);
	HrStoreType queryByCodeName(Map<String, Object> entityMap);
	
	/**
	 * 查人员档案库分类
	 * @param map
	 * @return
	 * @author yangyunfei
	 */
	List<Map<String, Object>> queryDicStoreType(Map<String, Object> map);
	
}
