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
import com.chd.hrp.hr.entity.sysstruc.HrStoreQueSet;
/**
 * 
 * @Description:
 * 人员档案库查询条件设置
 * @Table:
 * HR_STORE_QUE_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrStoreQueSetMapper extends SqlMapper{
	
	List<HrStoreQueSet> queryHrStoreQueSet(Map<String, Object> entityMap) throws DataAccessException;

	void deleteBatchByColStruc(List<HrColStruc> entityList) throws DataAccessException;

	//删除老数据
	void deleteHrStoreQueSetBatch(@Param("vo")Map<String, Object> mapVo, @Param("list")List<String> arrList);

	//插入选择要同步的数据
	void addHrStoreQueSetBatch(@Param("vo")Map<String, Object> mapVo, @Param("list")List<String> arrList);
}
