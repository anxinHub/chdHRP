package com.chd.hrp.acc.controller.verification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckTypeMapper;
import com.chd.hrp.acc.serviceImpl.AccLederCheckServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccLederServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCheckTypeServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccSubjServiceImpl;
import com.chd.hrp.acc.serviceImpl.verification.AccVerificationServiceImpl;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchCheckServiceImpl;

@Controller
public class AccVerificationController extends BaseController{
	private static Logger logger = Logger.getLogger(AccVerificationController.class);
	
	@Resource(name = "accSubjService")
	private final AccSubjServiceImpl accSubjService = null;
	
	@Resource(name = "accLederService")
	private final AccLederServiceImpl accLederService = null;
	
	@Resource(name = "accLederCheckService")
	private final AccLederCheckServiceImpl accLederCheckService = null;
	
	@Resource(name = "accCheckTypeService")
	private final AccCheckTypeServiceImpl accCheckTypeService = null;
	
	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckServiceImpl accVouchCheckService = null;
	
	@Resource(name = "accVerificationService")
	private final AccVerificationServiceImpl accVerificationService = null;
	
	@Resource(name = "accCheckTypeMapper")
	private final AccCheckTypeMapper accCheckTypeMapper = null;
	
	@Resource(name = "accParaService")
	private final AccParaServiceImpl accParaService = null;
	/*
	 * 往来核销页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accverification/accVerificationMainPage", method = RequestMethod.GET)
	public String accVerificationMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String rs = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		mode.addAttribute("acc_month", MyConfig.getAccYearMonth().getCurYearMonthAcc());
		return "hrp/acc/accverification/accVerificationMain";
	}
	/*
	 * 往来核销左侧查询
	 */
	@RequestMapping(value = "/hrp/acc/accverification/queryAccVerificationL", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccVerificationL(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String result = accVerificationService.queryAccVerificationL(getPage(mapVo));
		return JSONObject.parseObject(result);
	}
	
	/*
	 * 往来核销右侧查询
	 */
	@RequestMapping(value = "/hrp/acc/accverification/queryAccVerificationR", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccVerificationR(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String result = accVerificationService.queryAccVerificationR(getPage(mapVo));
		return JSONObject.parseObject(result);
	}
	
	/**
	 * 往来核销--勾选进行核销
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accverification/addAccVerification", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccVerification(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        mapVo.put("user_id", SessionManager.getUserId());
        
        String accLederJson;
		try {
			accLederJson = accVerificationService.addAccVerification(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
	}
	
	/**
	 * 勾选取消核销
	 * deleteCancleAccVericaIsCheck
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accverification/deleteCancleAccVericaIsCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCancleAccVericaIsCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        mapVo.put("user_id", SessionManager.getUserId());
        
        String accLederJson;
		try {
			accLederJson = accVerificationService.deleteAccVeriIsChecked(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
	}
	
	/**
	 * 批量核销跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accverification/accVerificationBatchPage", method = RequestMethod.GET)
	public String accVerificationBatchPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_name", SessionManager.getUserName());
		mode.addAttribute("is_check", mapVo.get("is_check"));
		mode.addAttribute("check_type", mapVo.get("check_type"));
		mode.addAttribute("check_type_id", mapVo.get("check_type_id"));
		mode.addAttribute("proj1", mapVo.get("proj1"));
		mode.addAttribute("proj2", mapVo.get("proj2"));
		
		return "hrp/acc/accverification/accVerificationBatch";
	}
	
	/**
	 * 批量 核销
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accverification/addBatchAccVerica", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccVerica(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("user_id",SessionManager.getUserId());
		mapVo.put("user_name",SessionManager.getUserName());
		
		String accLederJson;
		try {
			accLederJson = accVerificationService.addBatchAccVerica(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	/**
	 * 取消批量核销跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accverification/accVerificationBatchCancle", method = RequestMethod.GET)
	public String accVerificationBatchCancle(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_name", SessionManager.getUserName());
		return "hrp/acc/accverification/accVerificationBatchCancle";
	}
	
	/**
	 * 批量取消 核销
	 * deleteBatchCancleAccVerica
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accverification/deleteBatchCancleAccVerica", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchCancleAccVerica(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        mapVo.put("user_id", SessionManager.getUserId());
        String accLederJson;
		try {
			accLederJson = accVerificationService.deleteBatchAccVeri(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	
	//往来核销--核销明细账
	@RequestMapping(value = "/hrp/acc/accverification/accVerificationDetail", method = RequestMethod.GET)
	public String accVerificationDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        mode.addAttribute("veri_check_id", mapVo.get("veri_check_id"));
		mode.addAttribute("check_id", mapVo.get("check_id"));
		mode.addAttribute("direct", mapVo.get("direct"));
		mode.addAttribute("check_type", mapVo.get("check_type"));
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("check_type_id", mapVo.get("check_type_id"));
		String check_code=mapVo.get("check_code").toString();
		mode.addAttribute("check_code",check_code);
		
		return "hrp/acc/accverification/accVerificationDetail";
	}
	/*
	 *  往来核销 核销明细数据查询
	 */
	@RequestMapping(value = "/hrp/acc/accverification/queryAccVerificationDetail", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccVerificationDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
       
        String result = "";
		result = accVerificationService.queryAccVerDetail(getPage(mapVo));
		
		return JSONObject.parseObject(result);
		
	}
	/**
	 * 查询会计科目  带方向
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accverification/querySubjDirect", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjDirect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String subj = accVerificationService.querySubjDirect(mapVo);
		return subj;
		
	}
	//单位
	@RequestMapping(value = "/hrp/acc/accverification/queryVHosDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryVHosDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String subj = accLederService.queryHosInfo(mapVo);
		return subj;
		
	}
}
