/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.budgprojsetup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccProjAttrMapper;
import com.chd.hrp.acc.dao.payable.base.BudgNoManagerMapper;
import com.chd.hrp.acc.entity.payable.BudgNoManager;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.budg.dao.project.budgprojsetup.BudgProjSetUpMapper;
import com.chd.hrp.budg.dao.project.information.HosProjDictMapper;
import com.chd.hrp.budg.dao.project.information.HosProjMapper;
import com.chd.hrp.budg.entity.BudgProjSetUp;
import com.chd.hrp.budg.entity.HosProj;
import com.chd.hrp.budg.service.project.budgprojsetup.BudgProjSetUpService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_PROJ_SET_UP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgProjSetUpService")
public class BudgProjSetUpServiceImpl implements BudgProjSetUpService {

	private static Logger logger = Logger.getLogger(BudgProjSetUpServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgProjSetUpMapper")
	private final BudgProjSetUpMapper budgProjSetUpMapper = null;
	@Resource(name = "budgNoManagerMapper")
    private final BudgNoManagerMapper budgNoManagerMapper= null;
	@Resource(name = "hosProjMapper")
	private final HosProjMapper hosProjMapper = null;
	@Resource(name = "accProjAttrMapper")
	private final AccProjAttrMapper accProjAttrMapper = null;
	@Resource(name = "hosProjDictMapper")
	private final HosProjDictMapper hosProjDictMapper = null;
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象tabledesc
		BudgProjSetUp budgProjSetUp = queryByCode(entityMap);

		if (budgProjSetUp != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = budgProjSetUpMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjSetUpMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = budgProjSetUpMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgProjSetUpMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
    	BudgProjSetUp budgProjSetUp = queryByCode(entityMap);
    	if("01".equals(budgProjSetUp.getState())){
			int state = budgProjSetUpMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
    	}else{
    		return "{\"error\":\"删除失败，单据不是新建状态\"}";
    	}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgProjSetUpMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
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
		//判断是否存在对象tabledesc
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgProjSetUp> list = (List<BudgProjSetUp>)budgProjSetUpMapper.queryExists(mapVo);
		
		if (list.size()>0) {
			
			HosProj hosProj = hosProjMapper.queryByUniqueness(entityMap);
			if (hosProj != null) {			
				if(hosProj.getProj_code().equals(entityMap.get("proj_code"))){
					return "{\"error\":\"项目编码重复,请修改！！！.\"}";
				}
				if(hosProj.getProj_name().equals(entityMap.get("proj_name"))){
					return "{\"error\":\"项目名称重复,请修改！！！.\"}";
				}
				if(hosProj.getProj_simple().equals(entityMap.get("proj_simple"))){
					return "{\"error\":\"项目简称重复,请修改！！！.\"}";
				}
				return "{\"error\":\"数据重复,请重新添加.\"}";
			}

			int state = budgProjSetUpMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			HosProj hosProj = hosProjMapper.queryByUniqueness(entityMap);
			if (hosProj != null) {			
				if(hosProj.getProj_code().equals(entityMap.get("proj_code"))){
					return "{\"error\":\"项目编码重复,请修改！！！.\"}";
				}
				if(hosProj.getProj_name().equals(entityMap.get("proj_name"))){
					return "{\"error\":\"项目名称重复,请修改！！！.\"}";
				}
				if(hosProj.getProj_simple().equals(entityMap.get("proj_simple"))){
					return "{\"error\":\"项目简称重复,请修改！！！.\"}";
				}
				return "{\"error\":\"数据重复,请重新添加.\"}";
			}
			int state = budgProjSetUpMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgProjSetUp> list = (List<BudgProjSetUp>)budgProjSetUpMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgProjSetUp> list = (List<BudgProjSetUp>)budgProjSetUpMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjSetUpMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgProjSetUp
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjSetUpMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgProjSetUp>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgProjSetUpMapper.queryExists(entityMap);
	}
	/**
	 * 主页面跳转查询数据
	 */
	@Override
	public String queryBudgProjSetUp(Map<String, Object> entityMap) {
				SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<BudgProjSetUp> list = (List<BudgProjSetUp>)budgProjSetUpMapper.queryBudgProjSetUp(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<BudgProjSetUp> list = (List<BudgProjSetUp>)budgProjSetUpMapper.queryBudgProjSetUp(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public String addBudgProjSetUp(Map<String, Object> mapVo) {

		List<BudgProjSetUp> list = (List<BudgProjSetUp>)budgProjSetUpMapper.queryExists(mapVo);
		
		if (list.size()>0) {
			return "{\"error\":\"项目名称或编码重复,请重新添加.\"}";
		}
		
		HosProj hosProj = hosProjMapper.queryByUniqueness(mapVo);
		if (hosProj != null) {			
			if(hosProj.getProj_code().equals(mapVo.get("proj_code"))){
				return "{\"error\":\"项目编码重复,请修改！！！.\"}";
			}
			if(hosProj.getProj_name().equals(mapVo.get("proj_name"))){
				return "{\"error\":\"项目名称重复,请修改！！！.\"}";
			}
			if(hosProj.getProj_simple().equals(mapVo.get("proj_simple"))){
				return "{\"error\":\"项目简称重复,请修改！！！.\"}";
			}
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		
		try {
			mapVo.put("apply_code", generateApplyCode(mapVo));
			String p_code = mapVo.get("proj_code").toString();
			// 添加编码规则判断
			mapVo.put("proj_code", "HOS_PROJ");
			mapVo.put("mod_code", "00");

			Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
			mapVo.put("proj_code", p_code);
			if (null != mapVo.get("proj_code")) {
				Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
				
				int max_level = Integer.parseInt(rules.get("max_level").toString());
				
				if (max_level > 0) {
					String rules_v = rules.get("rules_view").toString();
					String s_view = Strings.removeFirst(rules_v);
					Object level = p_code.trim().length();
					if(rules_level_length!=null){
						//当第一级为0时 不验证规则
						if(!rules_level_length.get(1).toString().equals("0")){
							
							if (level != rules_level_length.get(1)) {
								return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
							}
							
						}
					}
					
					
				}
				
			}
			int state = budgProjSetUpMapper.addBudgProjSetUp(mapVo);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

	}
	
}
	/**
	 * 生成立项申请单号
	 * 
	 */
	@Override
	public String  generateApplyCode(Map<String,Object> entityMap){
		
	
		String ApplyCode="";
		String head="LXSQ";
		  int Year=Calendar.getInstance().get(Calendar.YEAR);
		  
			HashMap<String,Object>mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("hos_id", entityMap.get("hos_id"));
			mapVo.put("copy_code", entityMap.get("copy_code"));
			mapVo.put("table_code","BUDG_PROJ_SET_UP");
			mapVo.put("table_name","立项申请单表");
			mapVo.put("prefixe",head);
			mapVo.put("year", Year);
		try {
			BudgNoManager budgNoManage = budgNoManagerMapper.queryBudgProjSetUpApplyCode(mapVo);
			
			if(budgNoManage != null){
				
				//int lengeth=budgNoManage.getMax_no().toString().length();
				Integer maxno= budgNoManage.getMax_no()+1;
				String stringmaxno= maxno.toString();
				int length = stringmaxno.length() ;
					for(int i=length; i<budgNoManage.getSeq_no();i++){
						stringmaxno ="0"+stringmaxno;
					}
					
				ApplyCode=head+Year+stringmaxno;
				mapVo.put("max_no", stringmaxno);
				budgNoManagerMapper.updateBudgProjSetUpApplyCode(mapVo);
				return ApplyCode;
			}else{
				mapVo.put("max_no","1");
				mapVo.put("seq_no","4");
				budgNoManagerMapper.addBudgProjSetUpApplyCode(mapVo);
				ApplyCode=head+Year+"0001";
				return ApplyCode;
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			throw  e;
		}
				
		
		}
	@Override
	public String deleteBudgProjSetUp(List<Map<String, Object>> listVo) {
		 try {
					int state = budgProjSetUpMapper.deleteBudgProjSetUp(listVo);
					
					return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
				catch (Exception e) {

					logger.error(e.getMessage(), e);

					return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

				}	
	}
	@Override
	public Map<String, Object> queryBudgProjSetUpByCode(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return budgProjSetUpMapper.queryBudgProjSetUpByCode(mapVo);
	}
	
	
	@Override
	public String updateBudgProjSetUp(Map<String, Object> mapVo) {
		
		List<BudgProjSetUp> list = (List<BudgProjSetUp>)budgProjSetUpMapper.queryExists(mapVo);
		
		if (list.size()>1) {
			return "{\"error\":\"项目名称或编码重复,请重新添加.\"}";
		}
		
		HosProj hosProj = hosProjMapper.queryByUniqueness(mapVo);
		if (hosProj != null) {			
			if(hosProj.getProj_code().equals(mapVo.get("proj_code"))){
				return "{\"error\":\"项目编码重复,请修改！！！.\"}";
			}
			if(hosProj.getProj_name().equals(mapVo.get("proj_name"))){
				return "{\"error\":\"项目名称重复,请修改！！！.\"}";
			}
			if(hosProj.getProj_simple().equals(mapVo.get("proj_simple"))){
				return "{\"error\":\"项目简称重复,请修改！！！.\"}";
			}
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}
		
		String p_code = mapVo.get("proj_code").toString();
		// 添加编码规则判断
		mapVo.put("proj_code", "HOS_PROJ");
		mapVo.put("mod_code", "00");

		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		mapVo.put("proj_code", p_code);
		if (null != mapVo.get("proj_code")) {
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			
			int max_level = Integer.parseInt(rules.get("max_level").toString());
			
			if (max_level > 0) {
				String rules_v = rules.get("rules_view").toString();
				String s_view = Strings.removeFirst(rules_v);
				Object level = p_code.trim().length();
				if(rules_level_length!=null){
					//当第一级为0时 不验证规则
					if(!rules_level_length.get(1).toString().equals("0")){
						
						if (level != rules_level_length.get(1)) {
							return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
						}
						
					}
				}
				
				
			}
			
		}
		try {
			int s=budgProjSetUpMapper.updateBudgProjSetUp(mapVo);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}
	}
	@Override
	public String auditBudgProjSetUp(List<Map<String, Object>> listVo) {
		String projCode="";
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		

		for(int i=0;i<listVo.size();i++){
			BudgProjSetUp budgProjSetUp	=budgProjSetUpMapper.queryByCode(listVo.get(i));
			if(!"02".equals(budgProjSetUp.getState())){
				return "{\"error\":\"单据不是提交状态，无法审核！！！.\",\"state\":\"true\"}";
			}
//			HosProj hosProj = hosProjMapper.queryByUniqueness(listVo.get(i));
//			if (hosProj != null) {
//				projCode +="【"+hosProj.getProj_code()+"】";
//				continue;
//
//			}
			Map<String, Object> map=hosProjMapper.queryMaxProjId(listVo.get(i));
//			int s =budgProjSetUpMapper.auditBudgProjSetUp(listVo.get(i));
			Map<String, Object> entityMap =queryBudgProjSetUpByCode(listVo.get(i));
			entityMap.put("proj_state", "01");
			entityMap.put("proj_id",Integer.parseInt(map.get("proj_id").toString())+1);
			entityMap.put("proj_no",Integer.parseInt(map.get("proj_id").toString())+1);
			entityMap.put("sort_code", Integer.parseInt(map.get("sort_code").toString())+10);
			entityMap.put("group_id", listVo.get(i).get("group_id"));
			entityMap.put("hos_id", listVo.get(i).get("hos_id"));
			entityMap.put("copy_code", listVo.get(i).get("copy_code"));
			entityMap.put("copy_code", listVo.get(i).get("copy_code"));
			entityMap.put("copy_code", listVo.get(i).get("copy_code"));
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", DateFormat.getDateInstance().format(new Date()));
			entityMap.put("app_date", dateFormater.format(entityMap.get("app_date")));
			entityMap.put("set_up_date", dateFormater.format(entityMap.get("set_up_date")));
			entityMap.put("apply_code", listVo.get(i).get("apply_code"));
			entityMap.put("dlog", "添加");
			entityMap.put("is_stop", "0");
			int inte= accProjAttrMapper.synchronizationAddAccProjAttrNew(entityMap);
			
			int state = hosProjMapper.add(entityMap);
			int inte1 =	hosProjDictMapper.add(entityMap);
			
		}
		
		if("".equals(projCode)){
			return "{\"msg\":\"完成.\",\"state\":\"true\"}";
		}else{
			return "{\"msg\":\"部分单据已审核完成，其中编码为"+projCode+"的单据编码已存在，请修改后在审核！！！\",\"state\":\"true\"}";
		}
		
	}
	@Override
	public String cancelauditBudgProjSetUp(List<Map<String, Object>> listVo) {
		for(int i=0;i<listVo.size();i++){
			BudgProjSetUp budgProjSetUp	=budgProjSetUpMapper.queryByCode(listVo.get(i));
			if(!"03".equals(budgProjSetUp.getState())){
				return "{\"error\":\"单据不是审核状态，无法消审！！！.\",\"state\":\"true\"}";
			}
		int s =budgProjSetUpMapper.cancelauditBudgProjSetUp(listVo.get(i));
		int sa =accProjAttrMapper.cancelauditBudgProjSetUp(listVo.get(i));
		int inte1 =	hosProjDictMapper.cancelauditBudgProjSetUp(listVo.get(i));
		int state = hosProjMapper.cancelauditBudgProjSetUp(listVo.get(i));
		
		}
		return "{\"msg\":\"完成.\",\"state\":\"true\"}";
	}
	@Override
	public String submitBudgProjSetUp(List<Map<String, Object>> listVo) {
		for(int i=0;i<listVo.size();i++){
			BudgProjSetUp budgProjSetUp	=budgProjSetUpMapper.queryByCode(listVo.get(i));
			if(!"01".equals(budgProjSetUp.getState())){
				return "{\"error\":\"单据不是新建状态，无法提交！！！.\",\"state\":\"true\"}";
			}
			int s =budgProjSetUpMapper.submitBudgProjSetUp(listVo.get(i));
		}
		return "{\"msg\":\"完成.\",\"state\":\"true\"}";
	}
	@Override
	public String recallBudgProjSetUp(List<Map<String, Object>> listVo) {
		for(int i=0;i<listVo.size();i++){
			BudgProjSetUp budgProjSetUp	=budgProjSetUpMapper.queryByCode(listVo.get(i));
			if(!"02".equals(budgProjSetUp.getState())){
				return "{\"error\":\"单据不是提交状态，无法撤回提交！！！.\",\"state\":\"true\"}";
			}
			int s =budgProjSetUpMapper.recallBudgProjSetUp(listVo.get(i));
		}
		return "{\"msg\":\"完成.\",\"state\":\"true\"}";
	}
	
	
}