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
import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdSum;
/**
 * 
 * @Description:
 * 进修总结
 * @Table:
 * HR_FURSTD_SUM
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */

public interface HrFurstSummedMapper extends SqlMapper{
    /**
     * 查询单号
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryAppNo(Map<String, Object> entityMap);
    /**
     * 删除
     * @param entityList
     * @param mapVo 
     */
	void deleteFurstdApplication(@Param(value="list") List<HrFurstdSum> entityList,@Param(value="map") Map<String, Object> mapVo);
	/**
	 * 提交
	 * @param hrFurstSummed
	 */
	void confirmHrFurstSummed(HrFurstdSum hrFurstSummed);
	/**
	 * 查询单号对应信息
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryFurstdApp(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryFurstSummedByPrint(Map<String, Object> entityMap);

}
