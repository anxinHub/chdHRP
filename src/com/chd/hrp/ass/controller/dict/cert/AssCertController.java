package com.chd.hrp.ass.controller.dict.cert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.dict.cert.AssCertMain;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
import com.chd.hrp.ass.service.dict.cert.AssCertDetailService;
import com.chd.hrp.ass.service.dict.cert.AssCertMainService;

@Controller
public class AssCertController extends BaseController{
	private static Logger logger = Logger.getLogger(AssCertController.class);

	// 引入Service服务
	@Resource(name = "assCertMainService")
	private final AssCertMainService assCertMainService = null;

	@Resource(name = "assCertDetailService")
	private final AssCertDetailService assCertDetailService = null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscertmain/assCertMainPage", method = RequestMethod.GET)
	public String assCertMainPage(Model mode) throws Exception {
		return "hrp/ass/asscertmain/main";
	}
	
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscertmain/assCertMainAddPage", method = RequestMethod.GET)
	public String assCertMainAddPage(Model mode) throws Exception {
		return "hrp/ass/asscertmain/add";
	}
	
	
	
	@RequestMapping(value = "/hrp/ass/asscertmain/addAssCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCert(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_user", SessionManager.getUserId());
		

		String assInMainSpecialJson = assCertMainService.add(mapVo);

		return JSONObject.parseObject(assInMainSpecialJson);
	}
	
	@RequestMapping(value = "/hrp/ass/asscertmain/updateAssCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCert(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCertMain assInMainSpecial = assCertMainService.queryByCode(mapVo);
		
		if(assInMainSpecial != null){
			
			if(assInMainSpecial.getState() > 0){
				
				return JSONObject.parseObject("{\"warn\":\"已审核的数据不能保存! \"}");
			}
		}

		String assInMainSpecialJson = assCertMainService.update(mapVo);

		return JSONObject.parseObject(assInMainSpecialJson);
	}
	
	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscertmain/updateToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInsMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("cert_code", ids[3]);

			AssCertMain assInsMain = assCertMainService.queryByCode(mapVo);

			if (assInsMain.getState() != 0) {
				continue;
			}else{
				listVo.add(mapVo);
			}

		}

		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复审核! \"}");
		}
		try {

			assInsMainJson = assCertMainService.updateToExamine(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assInsMainJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscertmain/updateNotToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInsMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("cert_code", ids[3]);

			AssCertMain assInsMain = assCertMainService.queryByCode(mapVo);

			if (assInsMain.getState() != 1) {
				continue;
			}else{
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不是审核状态! \"}");
		}
		try {
			assInsMainJson = assCertMainService.updateNotToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assInsMainJson);

	}

	
	@RequestMapping(value = "/hrp/ass/asscertmain/assCertUpdatePage", method = RequestMethod.GET)
	public String assCertUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssCertMain assInMainSpecial = new AssCertMain();

		assInMainSpecial = assCertMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainSpecial.getGroup_id());
		mode.addAttribute("hos_id", assInMainSpecial.getHos_id());
		mode.addAttribute("copy_code", assInMainSpecial.getCopy_code());
		mode.addAttribute("cert_code",assInMainSpecial.getCert_code());
		mode.addAttribute("cert_inv_name",assInMainSpecial.getCert_inv_name());
		mode.addAttribute("fac_id",assInMainSpecial.getFac_id());
		mode.addAttribute("fac_no",assInMainSpecial.getFac_no());
		mode.addAttribute("fac_name",assInMainSpecial.getFac_name());
		mode.addAttribute("cert_date",DateUtil.dateToString(assInMainSpecial.getCert_date(),"yyyy-MM-dd"));
		mode.addAttribute("issuing_authority",assInMainSpecial.getIssuing_authority());
		mode.addAttribute("start_date", DateUtil.dateToString(assInMainSpecial.getStart_date(),"yyyy-MM-dd"));
		mode.addAttribute("end_date",DateUtil.dateToString(assInMainSpecial.getEnd_date(),"yyyy-MM-dd"));
		mode.addAttribute("link_person",assInMainSpecial.getLink_person());
		mode.addAttribute("link_tel",assInMainSpecial.getLink_tel());
		mode.addAttribute("link_mobile",assInMainSpecial.getLink_mobile());
		mode.addAttribute("cert_memo",assInMainSpecial.getCert_memo());
		mode.addAttribute("file_path",assInMainSpecial.getFile_path());
		mode.addAttribute("file_address",assInMainSpecial.getFile_address());
		mode.addAttribute("cert_state",assInMainSpecial.getCert_state());
		mode.addAttribute("cert_state_name",assInMainSpecial.getCert_state_name());
		mode.addAttribute("create_user",assInMainSpecial.getCreate_user());
		mode.addAttribute("create_user_name",assInMainSpecial.getCreate_user_name());
		mode.addAttribute("state", assInMainSpecial.getState());
		mode.addAttribute("state_name", assInMainSpecial.getState_name());
		return "hrp/ass/asscertmain/update";
	}
	
	
	
	@RequestMapping(value = "/hrp/ass/asscertmain/deleteAssCertMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCertMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assCertMainJson;

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("cert_code", ids[3]);

			AssCertMain assCertMain = assCertMainService.queryByCode(mapVo);

			if (assCertMain.getState() != 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}

		try {
			assCertMainJson = assCertMainService.deleteBatch(listVo);
			return JSONObject.parseObject(assCertMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}
	}
	
	@RequestMapping(value = "/hrp/ass/asscertmain/deleteAssCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCertDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("cert_code", ids[3]);
			mapVo.put("ass_id", ids[4]);
			
			AssCertMain assCertMain = assCertMainService.queryByCode(mapVo);
			
			if(assCertMain.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}
		
		String assInDetailSpecialJson = assCertDetailService.deleteBatch(listVo);
		return JSONObject.parseObject(assInDetailSpecialJson);
	}

	
	@RequestMapping(value = "/hrp/ass/asscertmain/queryAssCertMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCertMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
	
		String assInsMain = assCertMainService.query(getPage(mapVo));

		return JSONObject.parseObject(assInsMain);

	}
	
	@RequestMapping(value = "/hrp/ass/asscertmain/queryAssCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
	
		String assInsMain = assCertDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assInsMain);

	}
}
