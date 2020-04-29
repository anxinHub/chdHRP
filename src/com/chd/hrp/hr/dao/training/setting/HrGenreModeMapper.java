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
import com.chd.hrp.hr.entity.training.setting.HrGenreMode;
/**
 * 
 * @Description:
 * 培训方式
 * @Table:
 * HR_TITLE_LEVEL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrGenreModeMapper extends SqlMapper{
   /**
    * 删除培训方式
    * @param entityList
    */
	void deleteGenreMode(List<HrGenreMode> entityList);
    /**
     * 添加查询
     * @param entityMap
     * @return
     */
    List<HrGenreMode> queryGenreModeById(Map<String, Object> entityMap);
	List<HrGenreMode> queryGenreModeByIdName(Map<String, Object> entityMap);
	int queryUseGenreMode(HrGenreMode hrgenreMode);
	List<HrGenreMode> queryByCodeName(Map<String, Object> saveMap);
	
}
