﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.business.budgeworkadjust;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.budg.dao.business.budgeworkadjust.BudgWorkAdjMapper;
import com.chd.hrp.budg.dao.common.BudgEditStateMapper;
import com.chd.hrp.budg.entity.BudgWorkAdj;
import com.chd.hrp.budg.service.business.budgeworkadjust.BudgWorkAdjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 业务预算调整
 * @Table:
 * BUDG_WORK_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgWorkAdjService")
public class BudgWorkAdjServiceImpl implements BudgWorkAdjService {

	private static Logger logger = Logger.getLogger(BudgWorkAdjServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgWorkAdjMapper")
	private final BudgWorkAdjMapper budgWorkAdjMapper = null;
	@Resource(name = "budgNoManagerMapper")
	private final BudgNoManagerMapper budgNoManagerMapper = null;
	@Resource(name = "budgEditStateMapper")
	private final BudgEditStateMapper budgEditStateMapper = null;
	/**
	 * @Description 
	 * 添加
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		
		try {
			
			int state = budgWorkAdjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 批量添加
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgWorkAdjMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 更新
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			String state = budgWorkAdjMapper.queryAdjState(entityMap);
			if(!"01".equals(state)){
				return "{\"error\":\"数据不是新建状态,无法执行修改操作.\",\"state\":\"false\"}";
			}
			
		    budgWorkAdjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			String str = "";
			
			for (Map<String, Object> map : entityList) {
				String state = budgWorkAdjMapper.queryAdjState(map);
				if(!"01".equals(state)){
					str += map.get("adj_code").toString() + " ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\"调整单号:"+str+"的数据不是新建状态,无法执行修改操作.\",\"state\":\"false\"}";
			}
			
			
			budgWorkAdjMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
	    	String state = budgWorkAdjMapper.queryAdjState(entityMap);
			if(!"01".equals(state)){
				return "{\"error\":\"数据不是新建状态,无法执行删除操作.\",\"state\":\"false\"}";
			}
    	
			budgWorkAdjMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteData(Map<String,Object> mapVo,Map<String, Object> fileMap)throws DataAccessException{
		
		try {
			String str = "";
			String state = budgWorkAdjMapper.queryAdjState(mapVo);
			String adj_code = budgWorkAdjMapper.queryMaxCodeFromAdj(mapVo);
			if(!"01".equals(state)){
				return "{\"error\":\"数据不是新建状态,无法执行删除操作.\",\"state\":\"false\"}";
			}
			
			if(!adj_code.equals(mapVo.get("adj_code"))){
				return "{\"error\":\"数据单号不是最大单号,无法执行删除操作.\",\"state\":\"false\"}";
			}
			budgWorkAdjMapper.delete(mapVo);
			//删除数据后   还原最大单号
			mapVo.put("table_code", "BUDG_WORK_ADJ");
			budgWorkAdjMapper.updateMaxNo(mapVo);
			
			//如果存在文件名  说明存在文件  删除文件
			if(!"".equals(mapVo.get("adj_file"))){
				String file_name = fileMap.get("adj_file").toString();
				String path = fileMap.get("file_url").toString();
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加系统字典表
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象系统字典表状态（STATE），“01新建、02已审核”
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgWorkAdj> list = (List<BudgWorkAdj>)budgWorkAdjMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = budgWorkAdjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = budgWorkAdjMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集系统字典表
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkAdjMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkAdjMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象系统字典表
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkAdjMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取系统字典表
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgWorkAdj
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkAdjMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取系统字典表
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgWorkAdj>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgWorkAdjMapper.queryExists(entityMap);
	}
	/**
	 * 
	 * 审核
	 */
	@Override
	public String auditBudgWorkAdj(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try{
			String str = "";
			String budg_year = "";
			for (Map<String, Object> map : listVo) {
				
				String state = budgWorkAdjMapper.queryAdjState(map);
				if(!"01".equals(state)){
					str += map.get("adj_code").toString() + " ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\"调整单号:"+str+"的数据不是新建状态,无法执行审核操作.\",\"state\":\"false\"}";
			}
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			for (Map<String, Object> map : listVo) {
				
				mapVo.putAll(map);
				break;
			}
			
			mapVo.put("work_flag", "1");
			
			budgEditStateMapper.updateWorkFlag(mapVo);
			
			budgWorkAdjMapper.auditOrUnAudit(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch( Exception e ){
			logger.error(e.getMessage(), e);
		    throw new SysException("{\"error\":\"操作失败.\"}");
		}
	}
		
	/**
	 * 销审
	 * 
	 */
	@Override
	public String unAuditBudgWorkAdj(List<Map<String, Object>> listVo) throws DataAccessException {
		try{
		
			String str = "";
			String errorStr = "";
			
			for (Map<String, Object> map : listVo) {
				String state = budgWorkAdjMapper.queryAdjState(map);
				if(!"02".equals(state)){
					str += map.get("adj_code").toString() + " ";
				}
				String checkCode = budgWorkAdjMapper.queryMaxCodeFromCheck(map);
				if(!checkCode.equals(map.get("last_check_code"))){
					errorStr += map.get("adj_code").toString() + " ";
				}
			}
			
			if(str != ""){
				return "{\"error\":\"调整单号:"+str+"的数据不是审核状态,无法执行销审操作.\",\"state\":\"false\"}";
			}
			if(errorStr != ""){
				return "{\"error\":\"调整单号:"+errorStr+"的数据已做审批调整,不允许销审!\",\"state\":\"false\"}";
			}
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			for (Map<String, Object> map : listVo) {
				
				mapVo.putAll(map);
				break;
			}
			
			mapVo.put("work_flag", "0");
			
			budgEditStateMapper.updateWorkFlag(mapVo);
		
			
		    //消除审核,修改状态为新建
		    budgWorkAdjMapper.auditOrUnAudit(listVo);
		    return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){			
	       logger.error(e.getMessage(),e);
		   throw new SysException("{\"error:\"操作失败.\"}");
		}
		
	}
	
	@Override
	public String queryBudgWorkAdjHosMonth(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkAdjMapper.queryBudgWorkAdjHosMonth(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkAdjMapper.queryBudgWorkAdjHosMonth(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	@Override
	public String queryBudgWorkAdjDeptMonth(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkAdjMapper.queryBudgWorkAdjDeptMonth(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgWorkAdjMapper.queryBudgWorkAdjDeptMonth(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	/**
	 * 查询调整数据是否均已下达
	 */
	@Override
	public int queryBcState(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkAdjMapper.queryBcState(mapVo);
	}
	
	/**
	 * 查询 调整表中最大单号  年度  下达日期  添加页面使用
	 */
	@Override
	public Map<String, Object> queryMaxCheckData(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkAdjMapper.queryMaxCheckData(mapVo);
	}
	
	/**
	 * 页面跳转前查询审核表中是否存在当前预算年度数据
	 */
	@Override
	public int queryCheckDataExists(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkAdjMapper.queryCheckDataExists(mapVo);
	}
	/**
	 * 查询 调整表中最大单号  年度  下达日期  修改页面使用
	 */
	@Override
	public Map<String, Object> queryMaxCheckDataForUpdate(Map<String, Object> mapVo) throws DataAccessException {
		
		return budgWorkAdjMapper.queryMaxCheckDataUpdate(mapVo);
	}
	
	/**
	 * 上传文件
	 */
	@Override
	public String importFile(Map<String, Object> entityMap, MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath) throws Exception {
		try {
			
			if(entityMap.get("old_adj_file") != null && !entityMap.get("old_adj_file").equals("")){
				String file_name = entityMap.get("old_adj_file").toString();
				String path = filePath;
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			
			if (uploadFile != null ) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath,request,response)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"上传成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除文件
	 */
	@Override
	public String deleteFile(List<Map<String,Object>> entityMap) {
		
		try {
			for(Map<String,Object> map : entityMap){
				String file_name = map.get("adj_file").toString();
				String path = map.get("file_url").toString();
				if(!FtpUtil.deleteFile(path, file_name)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"删除成功.\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 下载文件
	 */
	@Override
	public String downloadFile(HttpServletResponse response, Map<String, Object> entityMap) {
		try {
			String file_name = entityMap.get("adj_file").toString();
			String path = entityMap.get("file_url").toString();
			if(!FtpUtil.downloadFile(response, file_name, path)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "{\"msg\":\"下载成功.\",\"state\":\"true\"}";
		} catch (NoTransactionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}