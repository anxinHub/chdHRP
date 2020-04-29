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
import com.chd.hrp.hr.entity.teachingmanagement.standardset.HrZyyNtrainInareaSet;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ZYY_NTRAIN_INAREA_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrZyyNtrainInareaSetService  {
	/**
	 * 删除数据
	 * @param listVo
	 */
	String deleteHrZyyNtrainInareaSet(List<HrZyyNtrainInareaSet> listVo) throws DataAccessException;
	/**
	 * 计算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String math(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询住院医病区满分标准
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	HrZyyNtrainInareaSet queryInareaSet(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importZyyNtrainInareaSet(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryZyyNtrainInareaSetByPrint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 保存
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String addOrUpdate(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String query(Map<String, Object> page)throws DataAccessException;

}
