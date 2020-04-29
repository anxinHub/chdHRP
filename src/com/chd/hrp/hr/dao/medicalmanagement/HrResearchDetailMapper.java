/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrResearch;
import com.chd.hrp.hr.entity.medicalmanagement.HrResearchDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_RESEARCH_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrResearchDetailMapper extends SqlMapper{

	int addhrResearchDetail(HrResearchDetail hrResearchDetail);
     /**
      * 删除
      * @param entityList
     * @param mapVo 
      */
	void deleteBatchResearch(@Param(value="list") List<HrResearch> entityList,@Param(value="map") Map<String, Object> mapVo);
	/**
	 * 修改删除
	 * @param alllistVo
	 */
	void deleteBatchUpdate(List<HrResearchDetail> alllistVo);
	
}
