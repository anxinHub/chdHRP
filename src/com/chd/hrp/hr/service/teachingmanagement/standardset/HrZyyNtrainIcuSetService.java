/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.teachingmanagement.standardset;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainIcuSet;
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainInareaSet;
/**
 * 
 * @Description:
 * 
 * @Table:住院医师规培轮转成绩标准设置（急诊ICU）
 * HR_ZYY_NTRAIN_ICU_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrZyyNtrainIcuSetService{
	/**
	 * 多条件查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrZyyNtrainIcuSet queryByCode(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询所有
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrZyyNtrainIcuSet(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException;
	/**
	 * 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String delete(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除数据
	 * @param listVo
	 */
	String deleteHrZyyNtrainIcuSet(List<HrZyyNtrainIcuSet> listVo);
	/**
	 * 计算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String math(Map<String, Object> entityMap) throws DataAccessException,Exception;
	/**
	 * 查询ICU满分标准
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrZyyNtrainIcuSet queryIcuSet (Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importZyyNtrainIcuSet(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryZyyNtrainIcuSetByPrint(Map<String, Object> page)throws DataAccessException;
}
