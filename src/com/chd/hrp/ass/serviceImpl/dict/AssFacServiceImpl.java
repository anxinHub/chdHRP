/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.ass.serviceImpl.dict;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssFacDictMapper;
import com.chd.hrp.ass.dao.dict.AssFacMapper;
import com.chd.hrp.ass.dao.dict.AssMatInvDictMapper;
import com.chd.hrp.ass.entity.dict.AssFac;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssFacService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("AssFacService")
public class AssFacServiceImpl implements AssFacService {

	private static Logger logger = Logger.getLogger(AssFacServiceImpl.class);
	@Resource(name = "assFacMapper")
	private final AssFacMapper facMapper = null;
	
	@Resource(name = "assFacDictMapper")
	private final AssFacDictMapper facDictMapper = null;
	@Resource(name ="sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	// 引入Service服务
	@Resource(name = "assMatInvDictMapper")
	private final AssMatInvDictMapper matInvDictMapper = null;
		
	/**
	 * @Description 添加Fac
	 * @param Fac entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addFac(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		List<AssFac> list = facMapper.queryFacById(entityMap);

		if (list .size() > 0) {
			for(AssFac fac : list){
				if(fac.getFac_code().equals(entityMap.get("fac_code"))){
					return "{\"error\":\"编码：" + fac.getFac_code().toString() + "已存在.\"}";
				}
				if(fac.getFac_name().equals(entityMap.get("fac_name"))){
					return "{\"error\":\"编码：" + fac.getFac_name().toString() + "已存在.\"}";
				}
			}
		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("fac_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("fac_name").toString()));
		
		// 添加编码规则判断
				entityMap.put("proj_code", "HOS_FAC");
				entityMap.put("mod_code", "00");

				String proj_code = entityMap.get("fac_code").toString();

				Map<Object, Object> rules = assBaseService.getHosRules(entityMap);
				if (null != entityMap.get("proj_code")) {
					Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
					Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
					int max_level = Integer.parseInt(rules.get("max_level").toString());
					if(max_level>0){
					String rules_v = rules.get("rules_view").toString();
					String s_view = Strings.removeFirst(rules_v);
					Object level = proj_code.length();
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
		
		Map<String,Object> utilMap=new HashMap<String,Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table","HOS_FAC");
		utilMap.put("field_key1", "");
		utilMap.put("field_value1", "");
		utilMap.put("field_key2", "");
		utilMap.put("field_value2", "");
		
		if(entityMap.get("fac_sort").equals("系统生成")){
			utilMap.remove("field_key2");
			utilMap.put("field_sort", "fac_sort");
			int count=sysFunUtilMapper.querySysMaxSort(utilMap);
			entityMap.put("fac_sort",count);
		}
		try {
			
			int result = facMapper.addFac(entityMap);
			if(result > 0){
				entityMap.put("fac_id", facMapper.queryCurrentSequence());
				entityMap.put("fac_no", facDictMapper.queryFacNoSeq());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", entityMap.get("is_stop"));
				entityMap.put("is_disable", entityMap.get("is_stop"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				
				facDictMapper.addFacDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {


			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());


		}

	}
	
	
	
	@Override
	public String addFacTz(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("fac_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("fac_name").toString()));
		
		List<AssFac> list = facMapper.queryFacById(entityMap);

		if (list .size() > 0) {
			for(AssFac fac : list){
				if(fac.getFac_name().equals(entityMap.get("fac_name"))){
					return "{\"error\":\"名称：" + fac.getFac_name().toString() + "已存在.\"}";
				}
			}
		}
		
		AssFac Map1 = facMapper.queryFacMaxTypeCode(entityMap);
		AssFac Maptypepy = facMapper.queryFacMaxTypePy(entityMap);
		String newfac_code;
		String typepy=Maptypepy.getFac_code().toUpperCase();
		if(Map1 ==null){
			newfac_code	=typepy+"0001";
		}else{
			String maxcode =Map1.getFac_code().toString();
			//String maxcode ="0099";
			DecimalFormat df=new DecimalFormat("0000");
			int code1=Integer.parseInt(maxcode.substring(maxcode.length()-4,maxcode.length()))+1;
			newfac_code=typepy+df.format(code1);	
		}	
		entityMap.put("fac_code", newfac_code);
		
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_FAC");
		entityMap.put("mod_code", "00");

		
		Map<String,Object> utilMap=new HashMap<String,Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table","HOS_FAC");
		utilMap.put("field_key1", "");
		utilMap.put("field_value1", "");
		utilMap.put("field_key2", "");
		utilMap.put("field_value2", "");
		
		if(entityMap.get("fac_sort").equals("系统生成")){
			utilMap.remove("field_key2");
			utilMap.put("field_sort", "fac_sort");
			int count=sysFunUtilMapper.querySysMaxSort(utilMap);
			entityMap.put("fac_sort",count);
		}
		
		entityMap.put("fac_no", facDictMapper.queryFacNoSeq());
		
		try {
			int result = facMapper.addFac(entityMap);
			if(result > 0){
				entityMap.put("fac_id", facMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", entityMap.get("is_stop"));
				
				entityMap.put("is_disable", entityMap.get("is_disable"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				
				facDictMapper.addFacDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
	
	public String addImportFac(Map<String,Object> entityMap)throws DataAccessException{
		try {
			
			int result = facMapper.addFac(entityMap);
			if(result > 0){
				entityMap.put("fac_no", facDictMapper.queryFacNoSeq());
				
				entityMap.put("fac_id", facMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", entityMap.get("is_stop"));
				
				entityMap.put("is_disable", entityMap.get("is_disable"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				
				facDictMapper.addFacDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * @Description 批量添加Fac
	 * @param  Fac entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchFac(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			facMapper.addBatchFac(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * @Description 查询Fac分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryFac(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<AssFac> list = facMapper.queryFac(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssFac> list = facMapper.queryFac(entityMap, rowBounds);
	        PageInfo page = new PageInfo(list); 
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 查询FacByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssFac queryFacByCode(Map<String,Object> entityMap)throws DataAccessException{
		return facMapper.queryFacByCode(entityMap);
	}
	
	/**
	 * @Description 批量删除Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchFac(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			String facIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "HOS_FAC");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					facIdStr+=mapVo.get("fac_id")+",";
					
						map.put("dict_id_str", facIdStr.substring(0, facIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						
						System.out.println("aaaaaaaaaa"+map);
						sysFunUtilMapper.querySysDictDelCheck(map);
						facIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的库房被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			
				facDictMapper.deleteBatchFacDict(entityList);
				int state = facMapper.deleteBatchFac(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 删除Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteFac(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			facDictMapper.deleteFacDict(entityMap);
			facMapper.deleteFac(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
    }
	
	/**
	 * @Description 更新Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateFac(Map<String,Object> entityMap)throws DataAccessException{
		/*int listInv= facMapper.queryFacInv(entityMap);
		if(listInv>0){
			return "{\"error\":\"该生产厂商已被物资材料使用，不能停用\"}";
		}*/
		try {
			if(entityMap.get("is_auto").equals("true")){
				entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("fac_name").toString()));
				entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("fac_name").toString()));	
			}
			
			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_FAC");
			entityMap.put("mod_code", "00");
/*			entityMap.put("fac_no", facDictMapper.queryFacNoSeq());*/
			
			String proj_code = entityMap.get("fac_code").toString();
			
			Map<Object, Object> rules = assBaseService.getHosRules(entityMap);
			if (null != entityMap.get("proj_code")) {
				Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
				Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

				String rules_v = rules.get("rules_view").toString();
				String s_view = Strings.removeFirst(rules_v);
				Object level = proj_code.length();
				if(rules_level_length!=null){
					//当第一级为0时 不验证规则
					if(!rules_level_length.get(1).toString().equals("0")){
						if (level != rules_level_length.get(1)) {
							return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
						}
					}
				}
			}
			
			List<AssFac> list = facMapper.queryFacById(entityMap);
			if (list .size() > 0) {
				for(AssFac fac : list){
					if(fac.getFac_code().equals(entityMap.get("fac_code"))){
						return "{\"error\":\"编码：" + fac.getFac_code().toString() + "已存在.\"}";
					}
					if(fac.getFac_name().equals(entityMap.get("fac_name"))){
						return "{\"error\":\"编码：" + fac.getFac_name().toString() + "已存在.\"}";
					}
				}
			}
			
			facMapper.updateFac(entityMap);
			
			if(entityMap.get("history").equals("true")){
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("fac_id", entityMap.get("fac_id"));
				map.put("fac_code", entityMap.get("fac_code"));
				map.put("fac_name", entityMap.get("fac_name"));
				map.put("type_code", entityMap.get("type_code"));
				map.put("is_stop", entityMap.get("is_stop"));
				map.put("spell_code", entityMap.get("spell_code"));
				map.put("wbx_code", entityMap.get("wbx_code"));
				facDictMapper.updateFacDictState(map);
				
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				entityMap.put("is_disable", entityMap.get("is_disable"));
				entityMap.put("is_stop", entityMap.get("is_stop"));
				
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				
				
				//材料字典表变更
				List<Map<String,Object>> invNewList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> invList = matInvDictMapper.queryMatInvIdByFacId(entityMap);
				
				String inv_ids = "";
				if(invList.size() > 0){
					for(Map<String,Object> invMap : invList){
						Map<String,Object> iMap = new HashMap<String,Object>();
						inv_ids=inv_ids+invMap.get("inv_id").toString()+",";
						
						iMap.putAll(invMap);
						iMap.put("fac_id", entityMap.get("fac_id"));
						iMap.put("fac_no", entityMap.get("fac_no"));
						iMap.put("is_stop",  entityMap.get("is_stop"));
						
						iMap.put("change_user", SessionManager.getUserId());
						iMap.put("change_date", new Date());
						iMap.put("change_note", "生产厂商变更");
						invNewList.add(iMap);
					}
					
					//更新
					Map<String,Object> mapInv = new HashMap<String,Object>();
					mapInv.put("group_id", entityMap.get("group_id"));
					mapInv.put("hos_id", entityMap.get("hos_id"));
					mapInv.put("inv_ids", inv_ids.substring(0, inv_ids.length()-1));
					mapInv.put("is_stop", entityMap.get("is_stop"));
					matInvDictMapper.updateIsStop(mapInv);
					//新增
					matInvDictMapper.addBatch(invNewList);
				}
			}else{
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				
				facDictMapper.updateFacDict_Fac(entityMap);
				//直接更新字典表
				String inv_ids = matInvDictMapper.queryMatInvIdByFacIds(entityMap);
				
				if(inv_ids != null && !"".equals(inv_ids.toString())){
					Map<String,Object> iMaps = new HashMap<String,Object>();
					
					iMaps.put("inv_ids", inv_ids);
					iMaps.put("change_user", SessionManager.getUserId());
					iMaps.put("change_date", new Date());
					iMaps.put("change_note", "生产厂商变更");
					iMaps.put("fac_id", entityMap.get("fac_id"));
					iMaps.put("fac_no", entityMap.get("fac_no"));
					iMaps.put("is_stops", entityMap.get("is_stop"));
					//更新
					matInvDictMapper.updateIsStop(iMaps);
				}
				
				
			}

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
	@Override
	public String updateFacTz(Map<String,Object> entityMap)throws DataAccessException{
		/*int listInv= facMapper.queryFacInv(entityMap);
		if(listInv>0){
			return "{\"error\":\"该生产厂商已被物资材料使用，不能停用\"}";
		}*/
		try {
			
			if(entityMap.get("is_auto").equals("true")){
				entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("fac_name").toString()));
				entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("fac_name").toString()));	
			}
			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_FAC");
			entityMap.put("mod_code", "00");
			entityMap.put("fac_no", facDictMapper.queryFacNoSeq());
			
			List<AssFac> list = facMapper.queryFacById(entityMap);

			if (list .size() > 0) {
				for(AssFac fac : list){
					if(fac.getFac_code().equals(entityMap.get("fac_code"))){
						return "{\"error\":\"编码：" + fac.getFac_code().toString() + "已存在.\"}";
					}
					if(fac.getFac_name().equals(entityMap.get("fac_name"))){
						return "{\"error\":\"编码：" + fac.getFac_name().toString() + "已存在.\"}";
					}
				}
			}
			
			facMapper.updateFac(entityMap);
			
			if(entityMap.get("history").equals("true")){
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("fac_id", entityMap.get("fac_id"));
				map.put("fac_code", entityMap.get("fac_code"));
				map.put("is_stop", entityMap.get("is_stop"));
				
				facDictMapper.updateFacDictState(map);
				
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				entityMap.put("is_disable", entityMap.get("is_stop"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				
				
				facDictMapper.addFacDict(entityMap);
				
				//材料字典表变更
				List<Map<String,Object>> invNewList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> invList = matInvDictMapper.queryMatInvIdByFacId(entityMap);
				String inv_ids = "";
				if(invList.size() > 0){
					for(Map<String,Object> invMap : invList){
						Map<String,Object> iMap = new HashMap<String,Object>();
						inv_ids=inv_ids+invMap.get("inv_id").toString()+",";
						
						iMap.putAll(invMap);
						iMap.put("fac_id", entityMap.get("fac_id"));
						iMap.put("fac_no", entityMap.get("fac_no"));
						iMap.put("is_stop", entityMap.get("is_stop"));
						
						iMap.put("change_user", SessionManager.getUserId());
						iMap.put("change_date", new Date());
						iMap.put("change_note", "生产厂商变更");
						invNewList.add(iMap);
					}
					Map<String,Object> mapInv = new HashMap<String,Object>();
					mapInv.put("group_id", entityMap.get("group_id"));
					mapInv.put("hos_id", entityMap.get("hos_id"));
					mapInv.put("inv_ids", inv_ids.substring(0, inv_ids.length()-1));
					mapInv.put("is_stop", entityMap.get("is_stop"));
					//更新
					matInvDictMapper.updateIsStop(mapInv);
					//新增
					matInvDictMapper.addBatch(invNewList);
				}
				
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}else{
				
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				
				facDictMapper.updateFacDict_Fac(entityMap);
				
				//直接更新字典表
				String inv_ids = matInvDictMapper.queryMatInvIdByFacIds(entityMap);
				if(inv_ids != null && !"".equals(inv_ids.toString())){
					Map<String,Object> iMaps = new HashMap<String,Object>();
					
					iMaps.put("inv_ids", inv_ids);
					iMaps.put("change_user", SessionManager.getUserId());
					iMaps.put("change_date", new Date());
					iMaps.put("change_note", "生产厂商变更");
					iMaps.put("fac_id", entityMap.get("fac_id"));
					iMaps.put("fac_no", entityMap.get("fac_no"));
					iMaps.put("is_stops", entityMap.get("is_stop"));
					//更新
					matInvDictMapper.updateIsStop(iMaps);
				}
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * @Description 批量更新Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchFac(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			facMapper.updateBatchFac(entityList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	/**
	 * @Description 导入Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importFac(Map<String,Object> entityMap)throws DataAccessException{

		try {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
}
