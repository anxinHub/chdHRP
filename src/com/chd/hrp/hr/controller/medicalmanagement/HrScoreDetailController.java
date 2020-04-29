/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.chd.hrp.hr.entity.medicalmanagement.HrScoreDetail;
import com.chd.hrp.hr.service.medicalmanagement.HrScoreDetailService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ScoreDetail 投诉登记表
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/medicalsafety")
public class HrScoreDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrScoreDetailController.class);
	
	//引入Service服务
	@Resource(name = "hrScoreDetailService")
	private final HrScoreDetailService hrScoreDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrScoreDetailMainPage", method = RequestMethod.GET)
	public String hrScoreDetailMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/scoredetail/scoreDetailMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addScoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrScoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		try {

			String hrScoreDetailJson = hrScoreDetailService.addScoreDetail(mapVo);

			return JSONObject.parseObject(hrScoreDetailJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteScoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrScoreDetail(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
			List<HrScoreDetail> listVo = JSONArray.parseArray(paramVo, HrScoreDetail.class);
		
		try {
		/*	for (HrScoreDetail hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
				if (hrDrugPerm.getIs_commit()!=0) {

					return ("{\"error\":\"删除失败!请选择新建状态的投诉单\",\"state\":\"false\"}");
				}
			}
			*/
			
			  return hrScoreDetailService.deleteScoreDetail(listVo);
			
					
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryScoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrScoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String hrScoreDetail = hrScoreDetailService.queryScoreDetail(getPage(mapVo));

		return JSONObject.parseObject(hrScoreDetail);
		
	}

	/**
	 * @Description 
	 * 查询下拉框数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryHrResearch", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrResearch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrScoreDetailService.queryHrResearch(mapVo);
		return json;

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmScoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmScoreDetail(@RequestParam String paramVo, Model mode)
			throws Exception {

		String msg="";
		try{
			msg = hrScoreDetailService.confirmScoreDetail(paramVo);
		}catch(Exception e){
			msg = "{\"error\":\"" + e.getMessage() + "\",\"state\":\"false\"}";
		}
		
		return JSONObject.parseObject(msg);
	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmScoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmScoreDetail(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrScoreDetail> listVo = JSONArray.parseArray(paramVo, HrScoreDetail.class);
			for (HrScoreDetail hrScoreDetail : listVo) {
				if (hrScoreDetail.getIs_commit() != 0) {
					hrScoreDetail.setIs_commit(0);
					hrScoreDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrScoreDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrScoreDetailService.reConfirmScoreDetailBatch(listVo);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
}

