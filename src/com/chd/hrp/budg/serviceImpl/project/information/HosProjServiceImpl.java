/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.information;

import java.text.DateFormat;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccProjAttrMapper;
import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.budg.dao.project.information.HosProjDictMapper;
import com.chd.hrp.budg.dao.project.information.HosProjMapper;
import com.chd.hrp.budg.entity.HosProj;
import com.chd.hrp.budg.service.project.information.HosProjService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * HOS_PROJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hosProjService")
public class HosProjServiceImpl implements HosProjService {

	private static Logger logger = Logger.getLogger(HosProjServiceImpl.class);
	//引入DAO操作
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
	public String add(Map<String,Object> entityMap) throws DataAccessException{
		
		//获取对象tabledesc
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
		Map<String,Object> hosProjMaxProjId=hosProjMapper.queryMaxProjId(entityMap);
		entityMap.put("proj_id", Integer.parseInt(hosProjMaxProjId.get("proj_id").toString())+1);
		entityMap.put("proj_no", Integer.parseInt(hosProjMaxProjId.get("proj_id").toString())+1);
		entityMap.put("sort_code", Integer.parseInt(hosProjMaxProjId.get("sort_code").toString())+10);
		entityMap.put("user_code", SessionManager.getUserCode());
		entityMap.put("create_date", DateFormat.getDateInstance().format(new Date()));
		entityMap.put("dlog", "添加");
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("proj_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("proj_name").toString()));
			String p_code = entityMap.get("proj_code").toString();
			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_PROJ");
			entityMap.put("mod_code", "00");

			Map<Object, Object> rules = assBaseService.getHosRules(entityMap);
			entityMap.put("proj_code", p_code);
			if (null != entityMap.get("proj_code")) {
				Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
				
				int max_level = Integer.parseInt(rules.get("max_level").toString());
				int max_length =  Integer.parseInt(rules.get("max_length").toString());
				if(max_length!=0){
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
			}
			int inte=accProjAttrMapper.addAccProjAttrNew(entityMap);
			int state = hosProjMapper.add(entityMap);
			int inte1 =	hosProjDictMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {
				throw  e;


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
			
			hosProjMapper.addBatch(entityList);
			
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

			//获取对象tabledesc
			HosProj hosProj = hosProjMapper.queryProjByCodeNotThis(entityMap);
			if (hosProj != null) {

				return "{\"error\":\"当前输入的编码已存在，请修改！！！.\"}";

			}
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", DateFormat.getDateInstance().format(new Date()));
			entityMap.put("dlog", "修改");
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("proj_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("proj_name").toString()));
			String p_code = entityMap.get("proj_code").toString();
			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_PROJ");
			entityMap.put("mod_code", "00");

			Map<Object, Object> rules = assBaseService.getHosRules(entityMap);
			entityMap.put("proj_code", p_code);
			if (null != entityMap.get("proj_code")) {
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
					AccProjAttr map =accProjAttrMapper.queryAccProjAttrByCode(entityMap);
					  if(map==null){
							int inte=accProjAttrMapper.addAccProjAttrNew(entityMap);
					  }
					  accProjAttrMapper.updateAccProjAttrNew(entityMap);
					  hosProjMapper.update(entityMap);
		  			  hosProjDictMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			throw e;
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
			
		  hosProjMapper.updateBatch(entityList);
			
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
			
			int state = hosProjMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

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
			accProjAttrMapper.deleteBatchAccProjAttr(entityList);
			hosProjDictMapper.deleteBatch(entityList);
			hosProjMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			throw e ;

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
		
		List<HosProj> list = (List<HosProj>)hosProjMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = hosProjMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = hosProjMapper.add(entityMap);
			
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
			
			List<HosProj> list = (List<HosProj>)hosProjMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HosProj> list = (List<HosProj>)hosProjMapper.query(entityMap, rowBounds);
			
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
		return hosProjMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return HosProj
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return hosProjMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<HosProj>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return hosProjMapper.queryExists(entityMap);
	}
	/**
	 * 项目结题时更新状态
	 */
	@Override
	public String endHosProj(List<Map<String, Object>> listVo) {
		for(int i=0;i<listVo.size();i++){
			AccProjAttr accProjAttr=accProjAttrMapper.queryAccProjAttrByCode(listVo.get(i));
			if(accProjAttr == null){
				return "{\"error\":\"没有该项目属性信息，无法完成结题操作.\",\"state\":\"false\"}";
			}
			if(accProjAttr.getProj_state()!=null){
				if(!"01".equals(accProjAttr.getProj_state())){
					return "{\"error\":\"项目不是执行状态，无法完成结题操作.\",\"state\":\"false\"}";
				}
			}
			int s =accProjAttrMapper.endHosProj(listVo.get(i));
		}
		return "{\"msg\":\"完成.\",\"state\":\"true\"}";
	}
	@Override
	public String escEndProj(List<Map<String, Object>> listVo) {
		for(int i=0;i<listVo.size();i++){
			AccProjAttr accProjAttr=accProjAttrMapper.queryAccProjAttrByCode(listVo.get(i));
			if(accProjAttr == null){
				return "{\"error\":\"没有该项目属性信息，无法完成取消结题操作.\",\"state\":\"false\"}";
			}
			if(!"02".equals(accProjAttr.getProj_state())){
				return "{\"error\":\"项目不是结题状态，无法完成取消结题操作.\",\"state\":\"true\"}";
			}
			int s =accProjAttrMapper.escEndProj(listVo.get(i));
		}
		return "{\"msg\":\"完成.\",\"state\":\"true\"}";
	}
	@Override
	public String suspendHosProj(List<Map<String, Object>> listVo) {
		for(int i=0;i<listVo.size();i++){
			AccProjAttr accProjAttr=accProjAttrMapper.queryAccProjAttrByCode(listVo.get(i));
			if(accProjAttr == null){
				return "{\"error\":\"没有该项目属性信息，无法执行中止操作.\",\"state\":\"false\"}";
			}
			if(!"01".equals(accProjAttr.getProj_state())){
				return "{\"error\":\"项目不是执行状态，无法完成中止操作.\",\"state\":\"true\"}";
			}
			int s =accProjAttrMapper.suspendHosProj(listVo.get(i));
		}
		return "{\"msg\":\"完成.\",\"state\":\"true\"}";
	}
	@Override
	public String escSuspendHosProj(List<Map<String, Object>> listVo) {
		for(int i=0;i<listVo.size();i++){
			AccProjAttr accProjAttr=accProjAttrMapper.queryAccProjAttrByCode(listVo.get(i));
			if(accProjAttr == null){
				return "{\"error\":\"没有该项目属性信息，无法执行取消中止操作.\",\"state\":\"false\"}";
			}
			if(!"03".equals(accProjAttr.getProj_state())){
				return "{\"error\":\"项目不是中止状态，无法完成取消中止操作.\",\"state\":\"true\"}";
			}
			int s =accProjAttrMapper.escSuspendHosProj(listVo.get(i));
		}
		return "{\"msg\":\"完成.\",\"state\":\"true\"}";
	}
	
}
