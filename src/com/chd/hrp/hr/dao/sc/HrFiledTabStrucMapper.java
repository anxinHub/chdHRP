/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sc;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrFiledData;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
/**
 * 
 * @Description:
 * 代码结构表
 * @Table:
 * HR_FileD_TAB_STRUC
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrFiledTabStrucMapper extends SqlMapper{

	List<Map<String,Object>> queryHrFiledTabStrucTree(Map<String, Object> entityMap);

	List<Map<String, Object>> queryHrFiledData(Map<String, Object> entityMap);
	
	

	Map<String, Object> queryHrFiledDataById(Map<String, Object> map);

	int saveHrFiledData(List<HrFiledData> addlistVo);

	int updateHrFiledData(List<Map> updatelistVo);

	int deleteHrFiledData(List<Map> listVO);

	List<Map<String, Object>> queryColAndTabName(Map<String, Object> entityMap);

	int deleteHrFiledDataByTabCode(@Param("vo")Map<String, Object> entityMap, @Param("list")List<HrFiledData> listVo);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrFiledDataByPrint(Map<String, Object> entityMap) throws DataAccessException;

	HrFiledTabStruc queryByCodeTab(HrColStruc saveMap);

	HrFiledTabStruc queryByCodeByName(Map<String, Object> entityMap);
	
	HrFiledTabStruc queryByCodePrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryFiledTypeSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryHrFiledDataSql(Map<String, Object> entityMap);

	int updateHrfiledView(Map<String, Object> entityMap);

	HrFiledTabStruc queryHrFiledTabStruc(Map<String, Object> entityMap);

	List<Map<String, Object>> queryOldFiledTabStruc(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryOldColumnByTabCode(Map<String, Object> entityMap)throws DataAccessException;

	List<HrFiledTabStruc> queryHrFiledTabStrucExport(Map<String, Object> entityMap) throws DataAccessException;
}
