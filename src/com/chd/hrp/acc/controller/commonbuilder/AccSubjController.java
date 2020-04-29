/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.commonbuilder;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.entity.AccVouchType;
import com.chd.hrp.acc.service.AccDeptAttrService;
import com.chd.hrp.acc.service.AccLederService;
import com.chd.hrp.acc.service.AccSubjNatureService;
import com.chd.hrp.acc.service.commonbuilder.AccCheckTypeService;
import com.chd.hrp.acc.service.commonbuilder.AccCurService;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.acc.service.commonbuilder.AccSubjTypeService;
import com.chd.hrp.acc.service.commonbuilder.AccVouchTypeService;
import com.chd.hrp.acc.service.vouch.AccVouchCheckService;
import com.chd.hrp.acc.service.vouch.AccVouchDetailService;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.service.RulesService;
import com.chd.hrp.sys.service.base.SysBaseService;

/** 
* @Title. @Description.
* 会计科目
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccSubjController extends BaseController{
	private static Logger logger = Logger.getLogger(AccSubjController.class);
	
	
	@Resource(name = "accSubjService")
	private final AccSubjService accSubjService = null;
	
	
	@Resource(name = "rulesService")
	private final RulesService rulesService = null;
	
	@Resource(name = "accVouchDetailService")
	private final AccVouchDetailService accVouchDetailService = null;
	
	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckService accVouchCheckService = null;
	
	@Resource(name = "accLederService")
	private final AccLederService accLederService = null;
	
	@Resource(name = "accCurService")
	private final AccCurService accCurService = null;
	
	@Resource(name = "accSubjTypeService")
	private final AccSubjTypeService accSubjTypeService = null;
	
	@Resource(name = "accSubjNatureService")
	private final AccSubjNatureService accSubjNatureService = null;
    
	@Resource(name = "accVouchTypeService")
	private final AccVouchTypeService accVouchTypeService = null;
	
	@Resource(name = "accCheckTypeService")
	private final AccCheckTypeService accCheckTypeService = null;
	
	@Resource(name = "accDeptAttrService")
	private final AccDeptAttrService accDeptAttrService = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	/**
	*会计科目<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubj/accSubjMainPage", method = RequestMethod.GET)
	public String accSubjMainPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Map<String,Object>> map = sysBaseService.queryGroupParaList(mapVo);
		if(map.containsKey("98001")){
			mode.addAttribute("98001", map.get("98001").get("para_value"));
		}else{
			mode.addAttribute("98001", "");
		}
		
		return "hrp/acc/accsubj/accSubjMain";

	}
	/**
	*会计科目<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accsubj/accSubjAddPage", method = RequestMethod.GET)
	public String accSubjAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("subj_type_code", mapVo.get("subj_type_code"));
		mode.addAttribute("subj_type_name", mapVo.get("subj_type_name"));
		return "hrp/acc/accsubj/accSubjAdd";

	}
	/**
	*会计科目<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accsubj/addAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        String resultJson = "";
        
        String rules = (String)mapVo.get("rules");//编码规则4-2-2....
        
        String subj_code = (String)mapVo.get("subj_code");//科目编码
        
        String [] ruless  = rules.split("-");
               
        
        Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
        
        Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
        
        int super_num = 0;
        
        for(int i = 0; i < ruless.length; i++){
        	int num = Integer.parseInt(ruless[i].replace(" ", ""));
        	super_num += num;
        	maxNumMap.put(super_num, i + 1);
        	position.put(i + 1, super_num);
        }
        
        if(maxNumMap.containsKey(subj_code.length())){//编码匹配
        	 int subj_level = maxNumMap.get(subj_code.length());
        	 mapVo.put("subj_level", subj_level);
        	 if(subj_level == 1){
        		 mapVo.put("super_code", "top");
        	 }else{
        		 int super_level =  subj_level - 1;//上级级次
            	 int subPosition = position.get(super_level);//上级级次位置
            	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
        		 mapVo.put("super_code", super_code);
        	 }
        	 
        }else{
        	resultJson =  "{\"error\":\"添加失败，不符合编码规则，请重新输入！\"}";
        	return JSONObject.parseObject(resultJson);
        }
        
        mapVo.put("is_last", "1");
        
        try {
			
        	resultJson = accSubjService.addAccSubj(mapVo);
        	
		} catch (Exception e) {
			
			resultJson = e.getMessage();
		}

		return JSONObject.parseObject(resultJson);
		
	}
	/**
	*会计科目<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubj/queryAccSubj", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String accSubj = accSubjService.queryAccSubj(getPage(mapVo));

		return JSONObject.parseObject(accSubj);
		
	}
	/**
	*会计科目<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accsubj/deleteAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccSubj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("subj_id", id.split("@")[0]);
            
            mapVo.put("group_id", id.split("@")[1]);
            
            mapVo.put("hos_id", id.split("@")[2]);
            
            mapVo.put("copy_code", id.split("@")[3]);
            
            mapVo.put("acc_year", id.split("@")[4]);
            
            mapVo.put("subj_code", id.split("@")[5]);
            
            mapVo.put("super_code", id.split("@")[6]);
            
            listVo.add(mapVo);
            
           /* if(id.split("@")[5].toString().startsWith("5001")){
            	
            	Map<String, Object> map = new HashMap<String, Object>();
                
            	map.put("group_id", id.split("@")[1]);

            	map.put("hos_id", id.split("@")[2]);
                
            	map.put("copy_code", id.split("@")[3]);
                
            	map.put("acc_year", id.split("@")[4]);
                
            	map.put("subj_code", id.split("@")[5].toString().replace("5001", "5301"));
                
                AccSubj accSubj=accSubjService.querySubjByCode(map);
                
                map.put("subj_id", accSubj.getSubj_id());
               
                map.put("super_code", id.split("@")[6].toString().replace("5001", "5301"));
                
                listVo.add(map);
            
            }else if(id.split("@")[5].toString().startsWith("5301")){
            	
            	Map<String, Object> map = new HashMap<String, Object>();
                
            	map.put("group_id", id.split("@")[1]);
                
            	map.put("hos_id", id.split("@")[2]);
                
            	map.put("copy_code", id.split("@")[3]);
                
            	map.put("acc_year", id.split("@")[4]);
                
            	map.put("subj_code", id.split("@")[5].toString().replace("5301", "5001"));
                
                AccSubj accSubj=accSubjService.querySubjByCode(map);
               
                map.put("subj_id", accSubj.getSubj_id());
                
                map.put("super_code", id.split("@")[6].toString().replace("5301", "5001"));
               
                listVo.add(map);
            }*/
        }
		
		String accSubjJson =null;
		
		if(null!=listVo && listVo.size()>0){
		
			accSubjJson=accSubjService.deleteBatchAccSubj(listVo);
		
		}else{
			
			accSubjJson="{\"msg\":\"没有要删除的数据。\"}";
		
		}
	   
		return JSONObject.parseObject(accSubjJson);
			
	}
	
	/**
	*会计科目<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsubj/accSubjUpdatePage", method = RequestMethod.GET)
	
	public String accSubjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		AccCheckType accCheckType = new AccCheckType();
		
		AccSubj accSubj = new AccSubj();
		
		accSubj = accSubjService.queryAccSubjByCode(mapVo);
		
		mode.addAttribute("subj_id", accSubj.getSubj_id());
		
		mode.addAttribute("group_id", accSubj.getGroup_id());
		
		mode.addAttribute("hos_id", accSubj.getHos_id());
		
		mode.addAttribute("copy_code", accSubj.getCopy_code());
		
		mode.addAttribute("acc_year", accSubj.getAcc_year());
		
		mode.addAttribute("subj_code", accSubj.getSubj_code());
		
		mode.addAttribute("cur_code", accSubj.getCur_code());
		
		mode.addAttribute("subj_type_code", accSubj.getSubj_type_code());
		
		mode.addAttribute("kind_code", accSubj.getKind_code());
		
		mode.addAttribute("subj_nature_code", accSubj.getSubj_nature_code());
		
		mode.addAttribute("vouch_type_code", accSubj.getVouch_type_code());
		
		mode.addAttribute("cur_name", accSubj.getCur_name());
		
		mode.addAttribute("subj_type_name", accSubj.getSubj_type_name());
		
		mode.addAttribute("subj_nature_name", accSubj.getSubj_nature_name());
		
		mode.addAttribute("vouch_type_name", accSubj.getVouch_type_name());
		
		mode.addAttribute("super_code", accSubj.getSuper_code());
		
		mode.addAttribute("subj_name", accSubj.getSubj_name());
		
		mode.addAttribute("subj_name_all", accSubj.getSubj_name_all());
		
		mode.addAttribute("subj_name_en", accSubj.getSubj_name_en());
		
		mode.addAttribute("is_cash", accSubj.getIs_cash());
		
		mode.addAttribute("is_bill", accSubj.getIs_bill());
		
		mode.addAttribute("is_jrsz", accSubj.getIs_jrsz());
		
		mode.addAttribute("is_wlhx", accSubj.getIs_wlhx());
		
		mode.addAttribute("subj_dire", accSubj.getSubj_dire());
		
		mode.addAttribute("subj_level", accSubj.getSubj_level());
		
		mode.addAttribute("is_last", accSubj.getIs_last());
		
		mode.addAttribute("spell_code", accSubj.getSpell_code());
		
		mode.addAttribute("wbx_code", accSubj.getWbx_code());
		
		mode.addAttribute("is_stop", accSubj.getIs_stop());
		
		mode.addAttribute("is_remit", accSubj.getIs_remit());
		
		mode.addAttribute("is_check", accSubj.getIs_check());
		
		mode.addAttribute("check1", accSubj.getCheck1());
		
		mode.addAttribute("check2", accSubj.getCheck2());
		
		mode.addAttribute("check3", accSubj.getCheck3());
		
		mode.addAttribute("check4", accSubj.getCheck4());
		
		mode.addAttribute("check1_name", accSubj.getCheck1_name());
		
		mode.addAttribute("check2_name", accSubj.getCheck2_name());
		
		mode.addAttribute("check3_name", accSubj.getCheck3_name());
		
		mode.addAttribute("check4_name", accSubj.getCheck4_name());
		
		mapVo.put("group_id", accSubj.getGroup_id());
		
		mapVo.put("hos_id", accSubj.getHos_id());
		
		mapVo.put("copy_code", accSubj.getCopy_code());
		
		mapVo.put("acc_year", accSubj.getAcc_year());
		
		mapVo.put("subj_id", accSubj.getSubj_id());
		
		//验证科目是否使用
		mapVo.put("dict_code", "ACC_SUBJ");
		
		mapVo.put("dict_id_str", accSubj.getSubj_id());
		
		mode.addAttribute("isSubjNote", accSubjService.existsSubjCheck(mapVo));
		
		//验证辅助核算1是否使用
		int isCheck1Use=0;
		
		int isCheck2Use=0;
		
		int isCheck3Use=0;
		
		int isCheck4Use=0;
		
		if(accSubj.getColumn1_check()!=null && !accSubj.getColumn1_check().equals("")){
			
			String checkFiled=accSubj.getColumn1_check();
			
			if(checkFiled.toLowerCase().startsWith("check")){
				
				checkFiled=checkFiled+"_ID";
			
			}
			
			mapVo.put("check_filed", checkFiled);
			
			isCheck1Use=accSubjService.existsVouchLederBySubjCheck(mapVo);
		
		}
		//验证辅助核算2是否使用
		if(accSubj.getColumn2_check()!=null && !accSubj.getColumn2_check().equals("")){
			
			String checkFiled=accSubj.getColumn2_check();
			
			if(checkFiled.toLowerCase().startsWith("check")){
			
				checkFiled=checkFiled+"_ID";
			
			}
			
			mapVo.put("check_filed", checkFiled);
			
			isCheck2Use=accSubjService.existsVouchLederBySubjCheck(mapVo);
		}
		//验证辅助核算3是否使用
		if(accSubj.getColumn3_check()!=null && !accSubj.getColumn3_check().equals("")){
			
			String checkFiled=accSubj.getColumn3_check();
			
			if(checkFiled.toLowerCase().startsWith("check")){
			
				checkFiled=checkFiled+"_ID";
			
			}
			
			mapVo.put("check_filed", checkFiled);
			
			isCheck3Use=accSubjService.existsVouchLederBySubjCheck(mapVo);
		}
		//验证辅助核算4是否使用
		if(accSubj.getColumn4_check()!=null && !accSubj.getColumn4_check().equals("")){
			
			String checkFiled=accSubj.getColumn4_check();
			
			if(checkFiled.toLowerCase().startsWith("check")){
			
				checkFiled=checkFiled+"_ID";
			
			}
			
			mapVo.put("check_filed", checkFiled);
			
			isCheck4Use=accSubjService.existsVouchLederBySubjCheck(mapVo);
		}
		
		mode.addAttribute("isCheck1Use", isCheck1Use);
		
		mode.addAttribute("isCheck2Use", isCheck2Use);
		
		mode.addAttribute("isCheck3Use", isCheck3Use);
		
		mode.addAttribute("isCheck4Use", isCheck4Use);
		
		mode.addAttribute("check_type_code1", accSubj.getCheck_type_code1());
		
		mode.addAttribute("check_type_code2", accSubj.getCheck_type_code2());
		
		mode.addAttribute("check_type_code3", accSubj.getCheck_type_code3());
		
		mode.addAttribute("check_type_code4", accSubj.getCheck_type_code4());
		
		mode.addAttribute("out_code", accSubj.getOut_code());
		
		mode.addAttribute("out_name", accSubj.getOut_name());
		
		if(accSubj.getCheck1() != null  && null != accSubj.getCheck_type_code1()){
			
			mapVo.put("check_type_id", accSubj.getCheck1());
			
			accCheckType = accCheckTypeService.queryCheckType(mapVo);
			
			//判断 辅助核算是否为 单位（核算数据表 HOS_INFO）(单位没有核算分类)
			if(!"V_HOS_DICT".equals(accCheckType.getCheck_table())){
				//根据 查询出  核算数据表  组装 查询对应的核算分类 的查询字段
				mapVo.putAll(checkTypePara(accCheckType));
				
				mapVo.put("check_type_code", accSubj.getCheck_type_code1().replaceAll(";", ","));
				
				accCheckType = accCheckTypeService.queryCheckColumn(mapVo);
				mode.addAttribute("check_type_name1", accCheckType.getColumn_name().replaceAll(",", ";"));
				
			}else{
				mode.addAttribute("check_type_name1", "");
			}
			
		}
		
		if(accSubj.getCheck2() != null && null!=accSubj.getCheck_type_code2()){
			
			mapVo.put("check_type_id", accSubj.getCheck2());
			
			accCheckType = accCheckTypeService.queryCheckType(mapVo);

			//判断 辅助核算是否为 单位（核算数据表 HOS_INFO）(单位没有核算分类 )
			if(!"V_HOS_DICT".equals(accCheckType.getCheck_table())){
				
				mapVo.putAll(checkTypePara(accCheckType));
				
				mapVo.put("check_type_code", accSubj.getCheck_type_code2().replaceAll(";", ","));
				
				accCheckType = accCheckTypeService.queryCheckColumn(mapVo);
				
				mode.addAttribute("check_type_name2", accCheckType.getColumn_name().replaceAll(",", ";"));
			}else{
				mode.addAttribute("check_type_name2", "");
			}
			
		}

		if(accSubj.getCheck3() != null && null!=accSubj.getCheck_type_code3()){
			
			mapVo.put("check_type_id", accSubj.getCheck3());
			
			accCheckType = accCheckTypeService.queryCheckType(mapVo);
			
			//判断 辅助核算是否为 单位（核算数据表 HOS_INFO）(单位没有核算分类)
			if(!"V_HOS_DICT".equals(accCheckType.getCheck_table())){
				
				//根据 查询出  核算数据表  组装 查询对应的核算分类 的查询字段
				mapVo.putAll(checkTypePara(accCheckType));
				
				mapVo.put("check_type_code", accSubj.getCheck_type_code3().replaceAll(";", ","));
				
				accCheckType = accCheckTypeService.queryCheckColumn(mapVo);
				
				mode.addAttribute("check_type_name3", accCheckType.getColumn_name().replaceAll(",", ";"));
			}else{
				mode.addAttribute("check_type_name3", "");
			}
		}

		if(accSubj.getCheck4() != null && null!=accSubj.getCheck_type_code4()){
			
			mapVo.put("check_type_id", accSubj.getCheck4());
			
			accCheckType = accCheckTypeService.queryCheckType(mapVo);
			
			//判断 辅助核算是否为 单位（核算数据表 HOS_INFO）(单位没有核算分类)
			if(!"V_HOS_DICT".equals(accCheckType.getCheck_table()) ){
				
				//根据 查询出  核算数据表  组装 查询对应的核算分类 的查询字段
				mapVo.putAll(checkTypePara(accCheckType));
				
				mapVo.put("check_type_code", accSubj.getCheck_type_code4().replaceAll(";", ","));
				
				accCheckType = accCheckTypeService.queryCheckColumn(mapVo);
				
				mode.addAttribute("check_type_name4", accCheckType.getColumn_name().replaceAll(",", ";"));
			}else{
				mode.addAttribute("check_type_name4", "");
			}
			
		}
		
		List<Map<String, Object>> list=accSubjService.queryAccSubjByVouch(mapVo);
		
		if(Double.parseDouble(list.get(0).get("SUBJCOUNT").toString())!=0){
			
			mode.addAttribute("is_disable", true);
			
		}else{
			mode.addAttribute("is_disable", false);
			
		}
		
		Map<String,Map<String,Object>> map = sysBaseService.queryGroupParaList(mapVo);
		if(map.containsKey("98001")){
			mode.addAttribute("98001", map.get("98001").get("para_value"));
		}else{
			mode.addAttribute("98001", "");
		}
		
		return "hrp/acc/accsubj/accSubjUpdate";
	}
	// 根据 查询出  核算数据表  组装 查询对应的核算分类 的查询字段
	public Map<String,Object> checkTypePara(AccCheckType accCheckType ){
		Map<String,Object> mapVo = new HashMap<String,Object>();
		if("HOS_DEPT_DICT".equals(accCheckType.getCheck_table()) ){
			mapVo.put("check_table", "ACC_DEPT_TYPE");
			
			mapVo.put("column_name", "type_name");
			
			mapVo.put("column_id", "type_code");
			
			
		}else if("HOS_EMP_DICT".equals(accCheckType.getCheck_table())){
			mapVo.put("check_table", "HOS_EMP_KIND");
			
			mapVo.put("column_name", "kind_name");
			
			mapVo.put("column_id", "kind_code");
		}else if("HOS_PROJ_DICT".equals(accCheckType.getCheck_table()) ){
			mapVo.put("check_table", "HOS_PROJ_TYPE");
			
			mapVo.put("column_name", "type_name");
			
			mapVo.put("column_id", "type_code");
		}else if("HOS_STORE_DICT".equals(accCheckType.getCheck_table())){
			mapVo.put("check_table", "HOS_STORE_TYPE");
			
			mapVo.put("column_name", "type_name");
			
			mapVo.put("column_id", "type_code");
			
		}else if("HOS_CUS_DICT".equals(accCheckType.getCheck_table())){
			mapVo.put("check_table", "HOS_CUS_TYPE");
			
			mapVo.put("column_name", "type_name");
			
			mapVo.put("column_id", "type_code");
			
		}else if("HOS_SUP_DICT".equals(accCheckType.getCheck_table())){
			
			mapVo.put("check_table", "HOS_SUP_TYPE");
			
			mapVo.put("column_name", "type_name");
			
			mapVo.put("column_id", "type_code");
			
		}else if("HOS_SOURCE".equals(accCheckType.getCheck_table())){
			
			mapVo.put("check_table", "HOS_SOURCE_NATURE");
			
			mapVo.put("column_name", "nature_name");
			
			mapVo.put("column_id", "nature_code");
			
		}else if("ACC_CHECK_ITEM".equals(accCheckType.getCheck_table())){
			
			mapVo.put("check_table", "ACC_CHECK_ITEM_TYPE");
			
			mapVo.put("column_name", "type_name");
			
			mapVo.put("column_id", "type_code");
		}
		return mapVo;
	}
	
	/**
	*会计科目<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accsubj/updateAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
        
        String rules = (String)mapVo.get("rules");//编码规则4-2-2....
        
        String subj_code = (String)mapVo.get("subj_code");//科目编码
        
        String [] ruless  = rules.split("-");
        String resultJson="";      
        
        Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
        
        Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
        
        int super_num = 0;
        
        for(int i = 0; i < ruless.length; i++){
        	int num = Integer.parseInt(ruless[i].replace(" ", ""));
        	super_num += num;
        	maxNumMap.put(super_num, i + 1);
        	position.put(i + 1, super_num);
        }
        
        if(maxNumMap.containsKey(subj_code.length())){//编码匹配
        	 int subj_level = maxNumMap.get(subj_code.length());
        	 mapVo.put("subj_level", subj_level);
        	 if(subj_level == 1){
        		 mapVo.put("super_code_new", "top");
        	 }else{
        		 int super_level =  subj_level - 1;//上级级次
            	 int subPosition = position.get(super_level);//上级级次位置
            	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
        		 mapVo.put("super_code_new", super_code);
        	 }
        	 
        }else{
        	resultJson =  "{\"error\":\"修改失败，不符合编码规则，请重新输入！\"}";
        	return JSONObject.parseObject(resultJson);
        }
        
        resultJson = accSubjService.updateAccSubj(mapVo);
		
		return JSONObject.parseObject(resultJson);
	}
	/**
	*会计科目<BR>
	*导入
	*/
	
	/*@RequestMapping(value = "/hrp/acc/accsubj/importAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accSubjJson = accSubjService.importAccSubj(mapVo);
		
		return JSONObject.parseObject(accSubjJson);
	}*/
	
	@RequestMapping(value = "/hrp/acc/accsubj/accSubjExtendPage", method = RequestMethod.GET)
	public String accSubjExtendPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/accsubj/accSubjExtend";
	}
	
	
	//继承会计科目
	@RequestMapping(value = "/hrp/acc/accsubj/addBatchAccSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	        mapVo.put("acc_year", SessionManager.getAcctYear());
		
        String accSubjJson = accSubjService.addBatchAccSubjExtend(mapVo);
        
		return JSONObject.parseObject(accSubjJson);
		
	}
	
	//集团同步会计科目
	@RequestMapping(value = "/hrp/acc/accsubj/addSynGroupSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSynGroupSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("acc_year", SessionManager.getAcctYear());
		
        String accSubjJson = accSubjService.addSynGroupSubj(mapVo);
        
		return JSONObject.parseObject(accSubjJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accsubj/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo) throws Exception {
        
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	    mapVo.put("proj_code", "ACC_SUBJ");
	    
	    mapVo.put("mod_code", "01");
	        
	    Rules rules = rulesService.queryRulesByCode(mapVo);
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 1; i <= 10; i++){
	    	Method m = (Method) rules.getClass().getMethod(
					"get" + ("Level" + i));
	    	Object obj = m.invoke(rules,new Object[] {});
//	    	if(obj.toString().equals("0")){
//	    		break;
//	    	}
	    	if(i == 10){
	    		sb.append(obj.toString());
	    	}else{
	    		sb.append(obj.toString()+"-");
	    	}
			
	    }
		return sb.toString();
		
	}
	
	//下载导入模版
	@RequestMapping(value="hrp/acc/accsubj/downTemplateAccSubj", method = RequestMethod.GET)  
	public String downTemplateAccSubj(Plupload plupload,HttpServletRequest request,
		    		HttpServletResponse response,Model mode) throws IOException { 
		    printTemplate(request,response,"acc\\基础设置","会计科目.xls");
		    return null; 
	}
	
	/**
	*会计科目<BR>
	*导入
	*导入有两种方式:
	*1、动态创建临时表，将导入数据插入临时表，然后按照科目编码升序查询导入数据，
	*在进行条件验证加入到会计科目表中
	*2、利用list排序，利用冒泡排序，将所有科目按照升序导入会计科目表
	*/ 
	@RequestMapping(value="/hrp/acc/accsubj/readAccSubjFiles",method = RequestMethod.POST)  
	public String readAccSubjFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,String rules,Model mode) throws IOException { 
			
		List<AccSubj> list_err = new ArrayList<AccSubj>();
		
		List<AccSubj> subjList= new ArrayList<AccSubj>();
		
		List<Map<String, Object>> Maplist = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			
			//list =mySort(list);
			
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AccSubj costInAcctVouch = new AccSubj();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (mapVo.get("group_id") == null) {
						
						mapVo.put("group_id", SessionManager.getGroupId());
						
					}
					
					if (mapVo.get("hos_id") == null) {
						
						mapVo.put("hos_id", SessionManager.getHosId());
						
					}
					
					if (mapVo.get("copy_code") == null) {
						
						mapVo.put("copy_code", SessionManager.getCopyCode());
					}
					
					if (mapVo.get("acc_year") == null) {
						
						mapVo.put("acc_year", SessionManager.getAcctYear());
						
					}
					
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costInAcctVouch.setSubj_code(temp[0]);
						
						mapVo.put("subj_code", temp[0]);
						
						AccSubj data_exc_extis = accSubjService.queryAccSubjCode(mapVo);
						
						if (data_exc_extis != null) {
							
							err_sb.append("会计科目编码已经存在！ ");
						
						}
						
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costInAcctVouch.setSubj_name(temp[1]);
						
						mapVo.put("subj_name", temp[1]);
						
					} else {
						
						err_sb.append("科目名称为空  ");
						
					}
					
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costInAcctVouch.setSubj_name_en(temp[2]);
						
						mapVo.put("subj_name_en", temp[2]);
						
					} else {
						
						mapVo.put("subj_name_en", null);
						
					}
					
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costInAcctVouch.setCur_name(temp[3]);
						
						mapVo.put("cur_name", temp[3]);
						
						AccCur acccur = accCurService.queryAccCurByCode(mapVo);
						
						if(acccur== null){
							
							err_sb.append("币种不存在  ");
							
						}else{
							
							mapVo.put("cur_code", acccur.getCur_code());
							
						}
						
					} else {
						
						mapVo.put("cur_code", "RMB");
						
					}
					
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costInAcctVouch.setSubj_type_name(temp[4]);
						
						mapVo.put("subj_type_name", temp[4]);
						
						AccSubjType accSubjType =accSubjTypeService.queryAccSubjTypeByCode(mapVo);
						
						if(accSubjType == null){
							
							err_sb.append("科目类别不存在  ");
							
						}else{
							
							mapVo.put("subj_type_code", accSubjType.getSubj_type_code());
							
						}
						
						
					} else {
						
						err_sb.append("科目类别为空  ");
						
					}
					
					if (StringUtils.isNotEmpty(temp[5])) {
						
						costInAcctVouch.setSubj_nature_name(temp[5]);
						
						mapVo.put("subj_nature_name", temp[5]);
						
						AccSubjNature accSubjNature =accSubjNatureService.queryAccSubjNatureByCode(mapVo);
						
						if(accSubjNature == null){
							
							err_sb.append("科目性质不存在  ");
							
						}else{
							
							mapVo.put("subj_nature_code", accSubjNature.getSubj_nature_code());
							
						}
						
					} else {
						
						mapVo.put("subj_nature_code", "");
						
					}
					
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costInAcctVouch.setVouch_type_name(temp[6]);
						
						mapVo.put("vouch_type_name", temp[6]);
						
						AccVouchType accVouchType =accVouchTypeService.queryAccVouchTypeByCode(mapVo);
						
						if(accVouchType == null){
							
							err_sb.append("凭证类型不存在  ");
							
						}else{
							
							mapVo.put("vouch_type_code", accVouchType.getVouch_type_code());
							
						}
						
					} else {
						
						mapVo.put("vouch_type_code", "");
						
					}
					
					if (StringUtils.isNotEmpty(temp[7])) {
						
						costInAcctVouch.setOut_name(temp[7]);
						
						mapVo.put("out_name", temp[7]);
						
						AccDeptAttr accDeptAttr =accDeptAttrService.queryAccOutDeptByName(mapVo);
						
						if(accDeptAttr == null){
							
							err_sb.append("支出性质不存在  ");
							
						}else{
							
							mapVo.put("out_code", accDeptAttr.getOut_code());
							
						}
						
					} else {
						
						mapVo.put("out_code", "");
						
					}
					
					if (StringUtils.isNotEmpty(temp[8])) {
						
						if("借".equals(temp[8])){
							
							costInAcctVouch.setSubj_dire_name(temp[8]);
							
							mapVo.put("subj_dire", 0);
							
						}else if("贷".equals(temp[8])){
							
							costInAcctVouch.setSubj_dire_name(temp[8]);
							
							mapVo.put("subj_dire", 1);
							
						}else{
							
							costInAcctVouch.setSubj_dire_name(temp[8]);
							
							err_sb.append("余额方向填写不符合规则  ");
							
						}
						
					} else {
						
						err_sb.append("余额方向为空  ");
						
					}
					
					if (StringUtils.isNotEmpty(temp[9])) {
						
						if("是".equals(temp[9])){
							
							costInAcctVouch.setIs_remit_name(temp[9]);
							
							mapVo.put("is_remit", 1);
							
						}else if("否".equals(temp[9])){
							
							costInAcctVouch.setIs_remit_name(temp[9]);
							
							mapVo.put("is_remit", 0);
							
						}else{
							
							costInAcctVouch.setIs_remit_name(temp[9]);
							
							err_sb.append("期末调汇填写不合规则  ");
							
						}
						
					} else {
						
						err_sb.append("期末调汇为空  ");
						
					}
					
					if (StringUtils.isNotEmpty(temp[10])) {
						
						if("是".equals(temp[10])){
							
							costInAcctVouch.setIs_cash_name(temp[10]);
							
							mapVo.put("is_cash", 1);
							
						}else if("否".equals(temp[10])){
							
							costInAcctVouch.setIs_cash_name(temp[10]);
							
							mapVo.put("is_cash", 0);
							
						}else{
							
							costInAcctVouch.setIs_cash_name(temp[10]);
							
							err_sb.append("核算现金流填写不符合规则  ");
						}
						
					} else {
						
						err_sb.append("核算现金流为空  ");
						
					}
					
					if (StringUtils.isNotEmpty(temp[11])) {
						
						if("是".equals(temp[11])){
							
							costInAcctVouch.setIs_bill_name(temp[11]);
							
							mapVo.put("is_bill", 1);
							
						}else if("否".equals(temp[11])){
							
							costInAcctVouch.setIs_bill_name(temp[11]);
							
							mapVo.put("is_bill", 0);
							
						}else{
							
							costInAcctVouch.setIs_cash_name(temp[11]);
							
							err_sb.append("是否票据管理填写不符合规则  ");
						}
						
					} else {
						
						err_sb.append("是否票据管理为空  ");
						
					}
					
					mapVo.put("is_stop", 0);
					
					mapVo.put("is_last", 1);
					
					mapVo.put("is_check", 0);
					
					//判断科目是否挂辅助核算，如果辅助核算列不为空，则设置is_check=1,否则为0
					if (temp.length>12&&temp[12]!=null&&!"".equals(temp[12].trim())) {
						
						mapVo.put("is_check", 1);
						
						mapVo.put("check_type_name", temp[12]);
						
						AccCheckType accCheckType=accCheckTypeService.queryAccCheckTypeByName(mapVo);
						
						if(accCheckType==null){
							
							costInAcctVouch.setCheck1_name(temp[12]);
							
							err_sb.append("辅助核算1不存在  ");
							
						}else{
							
							costInAcctVouch.setCheck1_name(temp[12]);
							
							mapVo.put("check1", accCheckType.getCheck_type_id());
							
						}
						
					}else{
						
						mapVo.put("check1", null);
						
					}
					
					if (temp.length>13&&temp[13]!=null&&!"".equals(temp[13].trim())) {
						
						mapVo.put("is_check", 1);
						
						mapVo.put("check_type_name", temp[13]);
						
						AccCheckType accCheckType=accCheckTypeService.queryAccCheckTypeByName(mapVo);
						
						if(accCheckType==null){
							
							costInAcctVouch.setCheck2_name(temp[13]);
							
							err_sb.append("辅助核算2不存在  ");
							
						}else{
							
							costInAcctVouch.setCheck2_name(temp[13]);
							
							mapVo.put("check2", accCheckType.getCheck_type_id());
							
						}
						
					}else{
						
						mapVo.put("check2", null);
						
					}
					
					if (temp.length>14&&temp[14]!=null&&!"".equals(temp[14].trim())) {
						
						mapVo.put("is_check", 1);
						
						mapVo.put("check_type_name", temp[14]);
						
						AccCheckType accCheckType=accCheckTypeService.queryAccCheckTypeByName(mapVo);
						
						if(accCheckType==null){
							
							costInAcctVouch.setCheck3_name(temp[14]);
							
							err_sb.append("辅助核算3不存在  ");
							
						}else{
							
							costInAcctVouch.setCheck3_name(temp[14]);
							
							mapVo.put("check3", accCheckType.getCheck_type_id());
							
						}
						
					}else{
						
						mapVo.put("check3", null);
						
					}
					
					if (temp.length>15&&temp[15]!=null&&!"".equals(temp[15].trim())) {
						
						mapVo.put("is_check", 1);
						
						mapVo.put("check_type_name", temp[15]);
						
						AccCheckType accCheckType=accCheckTypeService.queryAccCheckTypeByName(mapVo);
						
						if(accCheckType==null){
							
							costInAcctVouch.setCheck4_name(temp[15]);
							
							err_sb.append("辅助核算4不存在  ");
							
						}else{
							
							costInAcctVouch.setCheck4_name(temp[15]);
							
							mapVo.put("check4", accCheckType.getCheck_type_id());
							
						}
						
					}else{
						
						mapVo.put("check4",null);
						
					}
					
				if (err_sb.toString().length() > 0) {
					
					costInAcctVouch.setError_type(err_sb.toString());
					
					list_err.add(costInAcctVouch);
					
				} else {
					
			        String subj_code = (String)mapVo.get("subj_code");//科目编码
			        	
					try {
						
						rules = getRules(mapVo);
						
					} catch (Exception e) {
			
						e.printStackTrace();
						
						err_sb.append("会计科目编码规则不合法！ ");
					}
			        
			        String [] ruless  = rules.split("-");
			        
			        Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
			        
			        Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
			        
			        int super_num = 0;
			        
			        for(int j = 0; j < ruless.length; j++){
			        	
			        	int num = Integer.parseInt(ruless[j].replace(" ", ""));
			        	
			        	super_num += num;
			        	
			        	maxNumMap.put(super_num, j + 1);
			        	
			        	position.put(j + 1, super_num);
			        
			        }
			        
			        if(maxNumMap.containsKey(subj_code.length())){//编码匹配
			        	 
			        	int subj_level = maxNumMap.get(subj_code.length());
			        	 
			        	mapVo.put("subj_level", subj_level);
			        	 
			        	if(subj_level == 1){
			        		
			        		mapVo.put("super_code", "top");
			        		
			        	}else{
			        		
			        		int super_level =  subj_level - 1;//上级级次
			            	
			        		int subPosition = position.get(super_level);//上级级次位置
			            	
			        		String super_code = subj_code.substring(0,subPosition);//截取上级编码
			        		
			        		mapVo.put("super_code", super_code);
			        		
			        	}
			        	
			        	mapVo.put("subj_name_all", "");
		        		
//		        		mapVo.put("spell_code", "");
//    			        
//    			        mapVo.put("wbx_code", "");  
			        	mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
				        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
			        	 
			        }else{
			        	
			        	//AccSubj data_exc = new AccSubj();
						
			        	costInAcctVouch.setError_type("科目编码不符合编码规则 ");
						
			        	list_err.add(costInAcctVouch);
			        }
			        
				    Maplist.add(mapVo);  
					//String dataJson = accSubjService.addBatchAccSubj(mapVo);
				}
				
				if (err_sb.toString().length() > 0) {
					
					costInAcctVouch.setError_type(err_sb.toString());
					
					list_err.add(costInAcctVouch);
					
				} 
					
			}
			//此处添加批量添加功能，没有错误信息（list_err的size为0）才批量添加，否则全部返回
			if(list_err.size()<=0 ){
				
				 subjList=accSubjService.accSubjImport(Maplist);
				
			}
			
		} catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			
			AccSubj data_exc = new AccSubj();
			
			data_exc.setError_type("导入系统出错  ");
			
			list_err.add(data_exc);
			
		} 
		
		if(list_err.size()>0){
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
		}else if(subjList.size()>0 && list_err.size()==0){
			
			response.getWriter().print(ChdJson.toJson(subjList, subjList.size()));
			
		}
		
		return null;
    }  
	/**
	*会计科目<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/acc/accsubj/addBatchAccSubjs", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAccSubj(@RequestParam(value = "ParamVo") String paramVo,String rules, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<AccSubj> list_err = new ArrayList<AccSubj>();
		JSONArray json = JSONArray.parseArray(paramVo);
		Map<String, Object> mapVo = new HashMap<String, Object>();
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("acc_year") == null) {
				mapVo.put("acc_year", SessionManager.getAcctYear());
			}
		String s = null;
		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {
			StringBuffer err_sb = new StringBuffer();
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			mapVo.put("subj_code", jsonObj.get("subj_code"));
			mapVo.put("subj_name", jsonObj.get("subj_name"));
			mapVo.put("subj_name_all", jsonObj.get("subj_name_all"));
			mapVo.put("subj_name_en", jsonObj.get("subj_name_en"));
			mapVo.put("cur_code", jsonObj.get("cur_code"));
			mapVo.put("subj_type_code", jsonObj.get("subj_type_code"));
			mapVo.put("subj_nature_code", jsonObj.get("subj_nature_code"));
			mapVo.put("vouch_type_code", jsonObj.get("vouch_type_code"));
			mapVo.put("out_code", jsonObj.get("out_code"));
			mapVo.put("subj_dire", jsonObj.get("subj_dire"));
			mapVo.put("is_remit", jsonObj.get("is_remit"));
			mapVo.put("is_cash", jsonObj.get("is_cash"));
			mapVo.put("is_last", jsonObj.get("is_last"));
			mapVo.put("is_stop", jsonObj.get("is_stop"));
			mapVo.put("is_check", jsonObj.get("is_check"));
			mapVo.put("check1", jsonObj.get("check1"));
			mapVo.put("check2", jsonObj.get("check2"));
			mapVo.put("check3", jsonObj.get("check3"));
			mapVo.put("check4", jsonObj.get("check4"));
			mapVo.put("check5", "");
			mapVo.put("check6", "");
			mapVo.put("check7", "");
			mapVo.put("check8", "");
			mapVo.put("check9", "");
			mapVo.put("check10", "");
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
	        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
	        String resultJson = "";
	        
	        String subj_code = (String)mapVo.get("subj_code");//科目编码
	        
	        String [] ruless  = rules.split("-");
	        
	        Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
	        
	        Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
	        
	        int super_num = 0;
	        
	        for(int j = 0; j < ruless.length; j++){
	        	int num = Integer.parseInt(ruless[j].replace(" ", ""));
	        	super_num += num;
	        	maxNumMap.put(super_num, j + 1);
	        	position.put(j + 1, super_num);
	        }
	        
	        if(maxNumMap.containsKey(subj_code.length())){//编码匹配
	        	 int subj_level = maxNumMap.get(subj_code.length());
	        	 mapVo.put("subj_level", subj_level);
	        	 if(subj_level == 1){
	        		 mapVo.put("super_code", "top");
	        	 }else{
	        		 int super_level =  subj_level - 1;//上级级次
	            	 int subPosition = position.get(super_level);//上级级次位置
	            	 String super_code = subj_code.substring(0,subPosition);//截取上级编码
	        		 mapVo.put("super_code", super_code);
	        	 }
	        	 
	        }else{
	        	AccSubj data_exc = new AccSubj();
				data_exc.setError_type("编码不符合编码规则 ");
				list_err.add(data_exc);
	        }
	        AccSubj subj = accSubjService.queryAccSubj_id(mapVo);
			if(subj != null){
				mapVo.put("subj_id", subj.getSubj_id());
			}else{
				mapVo.put("subj_id", "");
			}
			AccSubj data_exc_extis = accSubjService.queryAccSubjByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				AccSubj costInAcctVouch = new AccSubj();
				if (err_sb.toString().length() > 0) {
					costInAcctVouch.setSubj_code(mapVo.get("subj_code").toString());
					costInAcctVouch.setSubj_name(mapVo.get("subj_name").toString());
					costInAcctVouch.setSubj_name_all(mapVo.get("subj_name_all").toString());
					costInAcctVouch.setSubj_name_en(mapVo.get("subj_name_en").toString());
					costInAcctVouch.setCur_code(mapVo.get("cur_code").toString());
					costInAcctVouch.setSubj_type_code(mapVo.get("subj_type_code").toString());
					costInAcctVouch.setSubj_nature_code(mapVo.get("subj_nature_code").toString());
					costInAcctVouch.setVouch_type_code(mapVo.get("vouch_type_code").toString());
					costInAcctVouch.setVouch_type_code(mapVo.get("out_code").toString());
					costInAcctVouch.setSubj_dire(Integer.parseInt(mapVo.get("subj_dire").toString()));
					costInAcctVouch.setIs_remit(Integer.parseInt(mapVo.get("is_remit").toString()));
					costInAcctVouch.setIs_cash(Integer.parseInt(mapVo.get("is_cash").toString()));
					costInAcctVouch.setIs_last(Integer.parseInt(mapVo.get("is_last").toString()));
					costInAcctVouch.setIs_stop(Integer.parseInt(mapVo.get("is_stop").toString()));
					costInAcctVouch.setIs_check(Integer.parseInt(mapVo.get("is_check").toString()));
					costInAcctVouch.setCheck1(Long.parseLong(mapVo.get("check1").toString()));
					costInAcctVouch.setCheck2(Long.parseLong(mapVo.get("check2").toString()));
					costInAcctVouch.setCheck3(Long.parseLong(mapVo.get("check3").toString()));
					costInAcctVouch.setCheck4(Long.parseLong(mapVo.get("check4").toString()));
					costInAcctVouch.setError_type(err_sb.toString());
					list_err.add(costInAcctVouch);
				} else {
					accSubjService.addAccSubj(mapVo);
				}
			}
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
    }
	
	@RequestMapping(value = "/hrp/acc/accsubj/queryAccSubjByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
        mapVo.put("hos_id", SessionManager.getHosId());
        
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String group = accSubjService.queryAccSubjByMenu(mapVo);

		return JSONObject.parseObject(group);
		
	}
	
	/**
	 * 导入数据根据科目编码排序，避免出现下级导入父级导入失败
	 * （下级科目直接导入成功，父级科目导入失败，修改页面不做修改关闭窗口）
	 * */
	private List<String[]> mySort(List<String[]> arr) { // 交换排序->冒泡排序
		
		String [] array=null;
        
        boolean exchange = false;
        
        for (int i = 0; i < arr.size(); i++) {
        	
            exchange = false;
            
            for (int j = arr.size() - 2; j >= i; j--) {
            	
                if(j>0){
                	
                	if (Integer.parseInt(arr.get(j)[0].trim())-Integer.parseInt(arr.get(j+ 1)[0].trim()) >= 0) {
                    	
                    	array = (String []) arr.get(j + 1);
                        
                        arr.set(j + 1, (String []) arr.get(j));
                        
                        arr.set(j, array);
                        
                        exchange = true;
                        
                    }
                }
                
            }
            
            if (!exchange)
            	
                break;
            
        }
        return arr;
    }
	
	@RequestMapping(value = "/hrp/acc/accsubj/querySubjIsCheck", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjIsCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
        mapVo.put("hos_id", SessionManager.getHosId());
        
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        return accSubjService.querySubjIsCheck(mapVo);

	}
	
	/**
	 * 会计科目导入页跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsubj/accSubjImportPage", method = RequestMethod.GET)
	public String accSubjImport(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("proj_code", "ACC_SUBJ");
	    mapVo.put("mod_code", "01");

	    Rules rules = rulesService.queryRulesByCode(mapVo);
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 1; i <= 10; i++){
	    	Method m = (Method) rules.getClass().getMethod("get" + ("Level" + i));
	    	Object obj = m.invoke(rules,new Object[] {});
	    	if(i == 10){
	    		sb.append(obj.toString());
	    	}else{
	    		sb.append(obj.toString()+"-");
	    	}
	    }
	    
	    mode.addAttribute("rules", sb.toString());
	    
		return "hrp/acc/accsubj/accSubjImport";
	}
	
	/**
	 * 会计科目导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsubj/importAccSubjData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccSubjData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Map<String, Object> retMap = null;
        try{
        	retMap = accSubjService.importAccSubjData(mapVo);
        }catch(Exception e){
        	retMap = new HashMap<String, Object>();
        	retMap.put("state", "false");
        	retMap.put("error", "操作失败");
        }
        
        return retMap;
	}
}

