package com.chd.hrp.acc.controller.termend.monthend;

import java.util.HashMap;
import java.util.Map;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.service.commonbuilder.AccCurService;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;
import com.chd.hrp.acc.service.termend.monthend.AccAdjustCollectService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccAdjustCollectController extends BaseController {
	private static Logger logger = Logger.getLogger(AccAdjustCollectController.class);
	
	@Resource(name = "accAdjustCollectService")
	private final AccAdjustCollectService accAdjustCollectService = null;
	@Resource(name = "accCurService")
	private final AccCurService accCurService = null;
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	/**
	 * 期末调汇<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/adjustcollect/accAdjustCollectPage", method = RequestMethod.GET)
	public String accAdjustCollectMainPage(Model mode) throws Exception {
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		return "hrp/acc/termend/monthend/adjustcollect/accAdjustCollectMain";
	}
	
	/**
	 * 期末调汇<BR>
	 * 凭证查询
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/adjustcollect/queryAccAdjustCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccAdjustCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if (mapVo.get("template_type_code") == null) {
			mapVo.put("template_type_code", "Z002");
		}
		String accTemplateData = accTermendTemplateService.queryAccTermendTemplateVouch(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 期末调汇<BR>
	 * 凭证删除
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/adjustcollect/deleteAccAdjustCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccAdjustCollect(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String res="";
		int i=0;
		for (String id : paramVo.split(",")) {
			mapVo.put("vouch_id", id);// 实际实体类变量
			res=superVouchService.deleteSuperVouchByVouchId(mapVo);
			if(res!=null && !res.equals("ok")){
				if(i==0)res="{\"error\":\""+res+"\",\"state\":\"false\"}";
				else res="{\"msg\":\""+res+"\",\"state\":\"true\"}";
				break;
			}
			i++;
		}
		if(res!=null && res.equals("ok")){
			res="{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		//String accVouchJson = accVouchService.deleteBatchAccVouch(listVo);
		return JSONObject.parseObject(res);

	}
	
	/**
	 * 期末调汇<BR>
	 * 凭证生成
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/adjustcollect/addAccAdjustCollectVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccAdjustCollectVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String msgStr = accAdjustCollectService.addAccAdjustCollectVouch(getPage(mapVo));

		return JSONObject.parseObject(msgStr);
	}
	
	/**
	*币种<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "hrp/acc/termend/monthend/adjustcollect/accAdjustCollectCurPage", method = RequestMethod.GET)
	public String accAdjustCollectCurPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccCur accCur = new AccCur();
        if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
	        mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if(mapVo.get("cur_code") == null || "".equals(mapVo.get("cur_code"))){
			return "hrp/acc/termend/monthend/adjustcollect/accCurAdd";
		}
		
		accCur = accCurService.queryAccCurByCode(mapVo);
		mode.addAttribute("cur_code", accCur.getCur_code());
		mode.addAttribute("group_id", accCur.getGroup_id());
		mode.addAttribute("hos_id", accCur.getHos_id());
		mode.addAttribute("copy_code", accCur.getCopy_code());
		mode.addAttribute("acc_year", accCur.getAcc_year());
		mode.addAttribute("cur_name", accCur.getCur_name());
		mode.addAttribute("cur_num", accCur.getCur_num());
		mode.addAttribute("cur_rate", accCur.getCur_rate());
		mode.addAttribute("cur_plan", accCur.getCur_plan());
		mode.addAttribute("is_self", accCur.getIs_self());
		mode.addAttribute("spell_code", accCur.getSpell_code());
		mode.addAttribute("wbx_code", accCur.getWbx_code());
		mode.addAttribute("is_stop", accCur.getIs_stop());
		return "hrp/acc/termend/monthend/adjustcollect/accCurUpdate";
	}
	
	/**
	 * 期末调汇<BR>
	 *选择币种带出汇率信息
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/adjustcollect/queryAccAdjustCollectCurRateByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccAdjustCollectCurRateByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String strCurRate = null;
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
		if (mapVo.get("acc_month") == null) {
			return JSONObject.parseObject("{\"change\":\"false\"}");
		}
		if("01".equals(mapVo.get("acc_month"))){
			mapVo.put("acc_year_before",String.valueOf((Integer.parseInt(mapVo.get("acc_year").toString())-1)));
			mapVo.put("acc_month_before", "12");
		}else{
			mapVo.put("acc_year_before",mapVo.get("acc_year"));
			String acc_month_before = ("0"+String.valueOf((Integer.parseInt(mapVo.get("acc_month").toString())-1)));
			mapVo.put("acc_month_before",acc_month_before.substring(acc_month_before.length()-2, acc_month_before.length()));
		}
		if(mapVo.get("cur_code") == null || "".equals(mapVo.get("cur_code"))){
			strCurRate = "{\"change\":\"false\"}";
		}else{
			strCurRate = accAdjustCollectService.queryAccAdjustCollectCurRateByCode(getPage(mapVo));
		}
		return JSONObject.parseObject(strCurRate);
	}
	
	@RequestMapping(value = "/hrp/acc/termend/monthend/adjustcollect/updateAccCur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cur_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cur_name").toString()));
	   
		String accCurJson = accCurService.updateAccCur(mapVo);
		
		return JSONObject.parseObject(accCurJson);
	}
}
