/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.accept;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.accept.AssAcceptMain;
import com.chd.hrp.ass.entity.dict.AssAcceptItem;
import com.chd.hrp.ass.entity.ins.AssInsMain;
/**
 * 
 * @Description:
 * 050601 资产验收主表
 * @Table:
 * ASS_ACCEPT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssAcceptMainService {

	/**
	 * @Description 
	 * 添加050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssAcceptMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssAcceptMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public Long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssAcceptMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssAcceptMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssAcceptMain(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssAcceptMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssAcceptMain(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050601 资产验收主表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssAcceptMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050601 资产验收主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssAcceptMain queryAssAcceptMainByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050601 资产验收主表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssAcceptMain
	 * @throws DataAccessException
	*/
	public AssAcceptMain queryAssAcceptMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssAcceptMain> queryAssAcceptMainExists(Map<String,Object> entityMap)throws DataAccessException;

	public String updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
	public String updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String initContract(Map<String, Object> entityMap)throws DataAccessException;

	public AssAcceptMain queryAssAcceptMainUniqueness(Map<String, Object> entityMap)throws DataAccessException;
	
	//生成验收项目
	public String buildAssAcceptItem(Map<String, Object> entityMap)throws DataAccessException;
	
	//查询验收项目
	public String queryAssAcceptItem(Map<String, Object> entityMap)throws DataAccessException;
	
	//保存验收项目
	public String saveAssAcceptItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//删除验收项目
	public String deleteAssAcceptItem(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<AssAcceptItem> queryByAcceptId(Map<String, Object> entityMap)throws DataAccessException;
	//删除验收项目如果修改过之前资产不存在
	public String deleteAssAcceptItemByAssAcceptDetail(AssAcceptDetail entityMap)throws DataAccessException;
	//资产验收打印
	Map<String, Object> queryAssAcceptDY(Map<String, Object> map)
			throws DataAccessException;
	//打印状态
	public List<String> queryAcceptMainState(Map<String, Object> mapVo)throws DataAccessException;
	
	public String updateAcceptMainInState(Map<String, Object> mapVo)throws DataAccessException;
}
