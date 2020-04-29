/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.training.setting;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.setting.HrGenreType;
/**
 * 
 * @Description:
 * 培训类别
 * @Table:
 * HR_TITLE_LEVEL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrGenreTypeMapper extends SqlMapper{
   /**
    * 删除培训类别
    * @param entityList
    */
	void deleteGenreType(List<HrGenreType> entityList);
    /**
     * 添加查询
     * @param entityMap
     * @return
     */
    List<HrGenreType> queryGenreTypeById(Map<String, Object> entityMap);
	List<HrGenreType> queryGenreTypeByIdName(Map<String, Object> entityMap);
	int queryUseGenreType(HrGenreType hrGenreType);
	List<HrGenreType> queryByCodeName(Map<String, Object> saveMap);
	
}
