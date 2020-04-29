/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.termend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccTermendTemplateDetail;

/**
* @Title. @Description.
* 期末处理模板明细<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTermendTemplateDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 期末处理模板明细<BR> 添加
	 * @param  List<entityMap> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccTermendTemplateDetail(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理模板明细<BR> 查询所有数据
	 * @param  entityMap
	 * @return List<AccTermendTemplateDetail>
	 * @throws DataAccessException
	*/
	public List<AccTermendTemplateDetail> queryAccTermendTemplateDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 期末处理模板明细<BR> 删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTermendTemplateDetail(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 期末处理模板明细<BR> 批量删除
	 * @param  List<entityMap> 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTermendTemplateDetail(List<Map<String,Object>> entityMap)throws DataAccessException;
}
