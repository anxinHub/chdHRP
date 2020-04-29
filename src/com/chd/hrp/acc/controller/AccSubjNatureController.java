/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.util.*;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.serviceImpl.AccSubjNatureServiceImpl;

/**
* @Title. @Description.
* 科目性质
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccSubjNatureController extends BaseController{
	private static Logger logger = Logger.getLogger(AccSubjNatureController.class);
	
	
	@Resource(name = "accSubjNatureService")
	private final AccSubjNatureServiceImpl accSubjNatureService = null;
   
    
	/**
	*科目性质<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubjnature/accSubjNatureMainPage", method = RequestMethod.GET)
	public String accSubjNatureMainPage(Model mode) throws Exception {

		return "hrp/acc/accsubjnature/accSubjNatureMain";

	}
	/**
	*科目性质<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accsubjnature/accSubjNatureAddPage", method = RequestMethod.GET)
	public String accSubjNatureAddPage(Model mode) throws Exception {

		return "hrp/acc/accsubjnature/accSubjNatureAdd";

	}
	/**
	*科目性质<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accsubjnature/addAccSubjNature", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccSubjNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("根据名称生成拼音码").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("根据名称生成五笔码").toString()));
		String accSubjNatureJson = accSubjNatureService.addAccSubjNature(mapVo);

		return JSONObject.parseObject(accSubjNatureJson);
		
	}
	/**
	*科目性质<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsubjnature/queryAccSubjNature", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccSubjNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accSubjNature = accSubjNatureService.queryAccSubjNature(getPage(mapVo));

		return JSONObject.parseObject(accSubjNature);
		
	}
	/**
	*科目性质<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accsubjnature/deleteAccSubjNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccSubjNature(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accSubjNatureJson = accSubjNatureService.deleteBatchAccSubjNature(listVo);
	   return JSONObject.parseObject(accSubjNatureJson);
			
	}
	
	/**
	*科目性质<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsubjnature/accSubjNatureUpdatePage", method = RequestMethod.GET)
	
	public String accSubjNatureUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccSubjNature accSubjNature = new AccSubjNature();
		accSubjNature = accSubjNatureService.queryAccSubjNatureByCode(mapVo);
		mode.addAttribute("subj_nature_code", accSubjNature.getSubj_nature_code());
		mode.addAttribute("group_id", accSubjNature.getGroup_id());
		mode.addAttribute("hos_id", accSubjNature.getHos_id());
		mode.addAttribute("copy_code", accSubjNature.getCopy_code());
		mode.addAttribute("subj_nature_name", accSubjNature.getSubj_nature_name());
		/*mode.addAttribute("spell_code", accSubjNature.getSpell_code());
		mode.addAttribute("wbx_code", accSubjNature.getWbx_code());*/
		return "hrp/acc/accsubjnature/accSubjNatureUpdate";
	}
	/**
	*科目性质<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accsubjnature/updateAccSubjNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccSubjNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
	   
	   
	   
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("根据名称生成拼音码").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("根据名称生成五笔码").toString()));
		String accSubjNatureJson = accSubjNatureService.updateAccSubjNature(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}
	/**
	*科目性质<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accsubjnature/importAccSubjNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccSubjNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accSubjNatureJson = accSubjNatureService.importAccSubjNature(mapVo);
		
		return JSONObject.parseObject(accSubjNatureJson);
	}

}

