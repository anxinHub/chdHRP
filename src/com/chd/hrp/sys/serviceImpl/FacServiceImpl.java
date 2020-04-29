/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

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
import com.chd.hrp.mat.dao.info.basic.MatInvDictMapper;
import com.chd.hrp.sys.dao.FacDictMapper;
import com.chd.hrp.sys.dao.FacMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Fac;
import com.chd.hrp.sys.service.FacService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;
 
/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("facService")
public class FacServiceImpl implements FacService {

	private static Logger logger = Logger.getLogger(FacServiceImpl.class);
	
	@Resource(name = "facMapper")
	private final FacMapper facMapper = null;
	
	@Resource(name = "facDictMapper")
	private final FacDictMapper facDictMapper = null;
    
	@Resource(name ="sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	// 引入Service服务
	@Resource(name = "matInvDictMapper")
	private final MatInvDictMapper matInvDictMapper = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
		
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
		List<Fac> list = facMapper.queryFacById(entityMap);

		if (list .size() > 0) {
			for(Fac fac : list){
				if(fac.getFac_code().equals(entityMap.get("fac_code"))){
					return "{\"error\":\"编码：" + fac.getFac_code().toString() + "已存在.\"}";
				}
				if(fac.getFac_name().equals(entityMap.get("fac_name"))){
					return "{\"error\":\"名称：" + fac.getFac_name().toString() + "已存在.\"}";
				}
			}
		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("fac_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("fac_name").toString()));
		
		// 添加编码规则判断
		Map<String, Object> rules = sysBaseService.getHosRulesList(entityMap).get("HOS_FAC");
		int max_length = Integer.parseInt(rules.get("max_length").toString());
		
		if(String.valueOf(entityMap.get("fac_code")).length() > max_length){
			return "{\"error\":\"编码不符合要求,请重新添加.超出长度范围：" + max_length + "\"}";
		}
				
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_FAC");
		entityMap.put("mod_code", "00");
		String proj_code = entityMap.get("fac_code").toString();
		entityMap.remove("copy_code");
		Map<Object, Object> rule = sysBaseService.getHosRules(entityMap);
		String rules_v = rule.get("rules_view").toString();
		String s_view = Strings.removeFirst(rules_v);
		if(entityMap.get("fac_code").toString().equals("自动生成"))
			if(facMapper.queryMaxFacCode(entityMap).length() < max_length){
				String fac_code =String.format("%0"+s_view+"d", Integer.parseInt(facMapper.queryMaxFacCode(entityMap)));
				entityMap.put("fac_code", fac_code);
			}
		else{
			if (null != entityMap.get("proj_code")) {
				Map<Object, Object> level_map = (Map<Object, Object>) rule.get("rules_type_level");
				Map<Object, Object> rules_level_length = (Map<Object, Object>) rule.get("rules_level_length");
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
			entityMap.put("copy_code", SessionManager.getCopyCode());
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
				entityMap.put("is_disable", 0);
				entityMap.put("is_stop", entityMap.get("is_stop")); 
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				entityMap.put("is_sup", entityMap.get("is_sup"));
				
				facDictMapper.addFacDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addFac\"}";

		}

	}
	
	@Override
	public String addFacType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("fac_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("fac_name").toString()));
		
		List<Fac> list = facMapper.queryFacById(entityMap);

		if (list .size() > 0) {
			for(Fac fac : list){
				if(fac.getFac_name().equals(entityMap.get("fac_name"))){
					return "{\"error\":\"名称：" + fac.getFac_name().toString() + "已存在.\"}";
				}
			}
		}
		
        Fac Map1 = facMapper.queryFacMaxTypeCode(entityMap);
		String newfac_code;
		if(Map1.getFac_code() ==null){
			newfac_code	=entityMap.get("type_code").toString()+"0001";
		}else{
			String maxcode =Map1.getFac_code().toString();
			newfac_code=entityMap.get("type_code").toString()+maxcode;
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
				entityMap.put("is_stop", 0);
				
				entityMap.put("is_disable", entityMap.get("is_stop"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				entityMap.put("is_sup", entityMap.get("is_sup"));
				
				facDictMapper.addFacDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addFac\"}";
		}
	}
	
	@Override
	public String addFacTz(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("fac_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("fac_name").toString()));
		if("自动生成".equals(entityMap.get("fac_code"))){
			entityMap.put("fac_code", "");
		}
		List<Fac> list = facMapper.queryFacById(entityMap);

		if (list .size() > 0) {
			for(Fac fac : list){
				if(fac.getFac_code().equals(entityMap.get("fac_code"))){
					return "{\"error\":\"编码：" + fac.getFac_code().toString() + "已存在.\"}";
				}
				
				if(fac.getFac_name().equals(entityMap.get("fac_name"))){
					return "{\"error\":\"名称：" + fac.getFac_name().toString() + "已存在.\"}";
				}
			}
		}
		
        Fac Map1 = facMapper.queryFacMaxTypeCode(entityMap);
        Fac Maptypepy = facMapper.queryFacMaxTypePy(entityMap);
		String newfac_code;
		String typepy=Maptypepy.getFac_code().toUpperCase();
		if(Map1 ==null ||  Map1.getFac_code() == null ){  
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
				entityMap.put("is_stop", 0);
				
				entityMap.put("is_disable", entityMap.get("is_stop"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				entityMap.put("is_sup", entityMap.get("is_sup"));
				
				facDictMapper.addFacDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addFac\"}";
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
				entityMap.put("is_stop", 0);
				
				entityMap.put("is_disable", entityMap.get("is_stop"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				entityMap.put("is_sup", entityMap.get("is_sup"));
				
				facDictMapper.addFacDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addFac\"}";
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
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchFac\"}";
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
			List<Fac> list = facMapper.queryFac(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Fac> list = facMapper.queryFac(entityMap, rowBounds);
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
	public Fac queryFacByCode(Map<String,Object> entityMap)throws DataAccessException{
		return facMapper.queryFacByCode(entityMap);
	}
	
	/**
	 * @Description 批量删除Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchFac(String param)throws DataAccessException{

		try {
			
			StringBuffer fac_ids = new StringBuffer();//存储厂商ID,用于判断是否被引用
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();//删除List,记录厂商信息
			
			for (String id : param.split(",")) {
				
				String[] ids = id.split("@");
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("fac_id", ids[2]);
				mapVo.put("fac_code",ids[3]);
				
				fac_ids.append(ids[2] + ",");
				listVo.add(mapVo);
			}
			
			String reStr="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("dict_code", "HOS_FAC");
			map.put("dict_id_str",fac_ids.substring(0, fac_ids.length()-1));
			map.put("acc_year", "");
			map.put("p_flag", "");
			
			//1.判断生产厂商是否被引用
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"warn\":\"删除失败,选择的生产厂商被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			//2.删除变更表
			facDictMapper.deleteBatchFacDict(listVo);
			
			//3.删除非变更表
			facMapper.deleteBatchFac(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

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
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteFac\"}";
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
			entityMap.put("fac_no", facDictMapper.queryFacNoSeq());
			
			String proj_code = entityMap.get("fac_code").toString();
			
			Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
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
			
			List<Fac> list = facMapper.queryFacById(entityMap);
			if (list .size() > 0) {
				for(Fac fac : list){
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
				entityMap.put("is_stop", entityMap.get("is_stop"));
				
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				entityMap.put("is_sup", entityMap.get("is_sup"));
				
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
						iMap.put("is_stop", 0);
						
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
					mapInv.put("is_stop", 1);
					mapInv.put("is_disable", 1);
					matInvDictMapper.updateIsStop(mapInv);
					//新增
					matInvDictMapper.addBatch(invNewList);
				}
			}else{
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				
				facDictMapper.updateFacDict_Fac(entityMap);
				//170906 生产商变更,不应该影响其他表
				//直接更新字典表
//				String inv_ids = matInvDictMapper.queryMatInvIdByFacIds(entityMap);
//				
//				if(inv_ids != null && !"".equals(inv_ids.toString())){
//					Map<String,Object> iMaps = new HashMap<String,Object>();
//					
//					iMaps.put("inv_ids", inv_ids);
//					iMaps.put("change_user", SessionManager.getUserId());
//					iMaps.put("change_date", new Date());
//					iMaps.put("change_note", "生产厂商变更");
//					iMaps.put("fac_id", entityMap.get("fac_id"));
//					iMaps.put("fac_no", entityMap.get("fac_no"));
//					iMaps.put("is_stop", "0");
//					//更新
//					matInvDictMapper.updateIsStop(iMaps);
//				}
				
				
			}

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFac\"}";
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
			
			List<Fac> list = facMapper.queryFacById(entityMap);

			if (list .size() > 0) {
				for(Fac fac : list){
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
				map.put("is_stop", 1);
				
				facDictMapper.updateFacDictState(map);
				
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				entityMap.put("is_disable", entityMap.get("is_stop"));
				entityMap.put("is_mat", entityMap.get("is_mat"));
				entityMap.put("is_ass", entityMap.get("is_ass"));
				entityMap.put("is_med", entityMap.get("is_med"));
				entityMap.put("is_sup", entityMap.get("is_sup"));
				
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
						iMap.put("is_stop", 0);
						
						iMap.put("change_user", SessionManager.getUserId());
						iMap.put("change_date", new Date());
						iMap.put("change_note", "生产厂商变更");
						invNewList.add(iMap);
					}
					Map<String,Object> mapInv = new HashMap<String,Object>();
					mapInv.put("group_id", entityMap.get("group_id"));
					mapInv.put("hos_id", entityMap.get("hos_id"));
					mapInv.put("inv_ids", inv_ids.substring(0, inv_ids.length()-1));
					mapInv.put("is_stop", 1);
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
					iMaps.put("is_stops", "0");
					//更新
					matInvDictMapper.updateIsStop(iMaps);
				}
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFac\"}";
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
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateFac\"}";
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
			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";
		}
	}



	@Override
	public String addGroupFac(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		int hos=facMapper.existsHosIdByFac(entityMap);
		if(hos==0){
			return "{\"error\":\"请先维护生成厂商分类.\",\"state\":\"false\"}";
		}
		int count = facMapper.existsGroupFacByAdd(entityMap);
		if(count>0){
			return "{\"error\":\"已经有数据了，无法继承.\",\"state\":\"false\"}";
		}
		int state=0;
		state=facMapper.addBatchGroupFacByCode(entityMap);
		int stateDict=facMapper.addBatchGroupFacDictByCode(entityMap);
		if(state>0){
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
		}else{
			return "{\"error\":\"没有数据，无法继承.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String deleteBatchFac(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			
				facDictMapper.deleteBatchFacDict(entityMap);
				int state = facMapper.deleteBatchFac(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}



	
}
