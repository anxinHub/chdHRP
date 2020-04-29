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
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrStoreColSet;
/**
 * 
 * @Description:
 * 人员档案库数据列设置
 * @Table:
 * HR_STORE_COL_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStoreColSetMapper extends SqlMapper{

	List<HrStoreColSet> queryHrStoreColSet(Map<String, Object> entityMap) throws DataAccessException;

	void deleteBatchByColStruc(List<HrColStruc> entityList) throws DataAccessException;

	//删除之前存在的表示数据列
	void deleteHrStoreColSetBatch(@Param("vo")Map<String, Object> mapVo, @Param("list")List<String> arrList);

	//把选中的表数据列同步到目标的档案库中
	void addHrStoreColSetBatch(@Param("vo")Map<String, Object> mapVo, @Param("list")List<String> arrList);
	
}
