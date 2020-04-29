/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.mat.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 安全库存调整<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface MatSafeChangeService {

	//主查询
	public String queryMatSafeChangeList(Map<String, Object> map) throws DataAccessException;
	
	//加载单据
	public Map<String, Object> queryMatSafeChangeById(Map<String, Object> map) throws DataAccessException;
	public String queryMatSafeChangeDetailById(Map<String, Object> map) throws DataAccessException;
	
	//保存（新增和修改）
	public Map<String, Object> saveMatSafeChange(Map<String, Object> map) throws DataAccessException;
	
	//删除
	public Map<String, Object> deleteMatSafeChange(Map<String, Object> map) throws DataAccessException;
	
	//修改审核状态
	public Map<String, Object> updateMatSafeChangeState(Map<String, Object> map) throws DataAccessException;
	
	//确认
	public Map<String, Object> confirmMatSafeChange(Map<String, Object> map) throws DataAccessException;
	
	//材料列表查询
	public String queryMatInvBySafeChange(Map<String, Object> map) throws DataAccessException;
	
	//模板打印（包含主从表）
	public Map<String, Object> queryMatSafeChangeByPrintTemlate(Map<String,Object> map) throws DataAccessException;
}
