/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.vouch;

import java.util.ArrayList;
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
import com.chd.hrp.acc.entity.AccVouchDetail;
import com.chd.hrp.acc.service.vouch.AccVouchDetailService;

/**
 * @Title. @Description. 凭证明细表
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccVouchDetailController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AccVouchDetailController.class);

	@Resource(name = "accVouchDetailService")
	private final AccVouchDetailService accVouchDetailService = null;

	
	/**
	 * 凭证明细表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouchdetail/accVouchDetailMainPage", method = RequestMethod.GET)
	public String accVouchDetailMainPage(Model mode) throws Exception {

		return "hrp/acc/accvouchdetail/accVouchMakingMain";

	}

	/**
	 * 凭证明细表<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accvouchdetail/accVouchDetailAddPage", method = RequestMethod.GET)
	public String accVouchDetailAddPage(Model mode) throws Exception {

		return "hrp/acc/accvouchdetail/accVouchDetailAdd";

	}

	/**
	 * 凭证明细表<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accvouchdetail/addAccVouchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccVouchDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accVouchDetailJson = accVouchDetailService.addAccVouchDetail(mapVo);

		return JSONObject.parseObject(accVouchDetailJson);

	}


	/**
	 * 凭证明细表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouchdetail/queryAccVouchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accVouchDetail = accVouchDetailService.queryAccVouchDetail(getPage(mapVo));

		return JSONObject.parseObject(accVouchDetail);

	}

	

	/**
	 * 凭证明细表<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/accvouchdetail/deleteAccVouchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("temp", id);// 实际实体类变量
			listVo.add(mapVo);
		}
		String accVouchDetailJson = accVouchDetailService.deleteBatchAccVouchDetail(listVo);
		return JSONObject.parseObject(accVouchDetailJson);

	}

	/**
	 * 凭证明细表<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accvouchdetail/accVouchDetailUpdatePage", method = RequestMethod.GET)
	public String accVouchDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccVouchDetail accVouchDetail = new AccVouchDetail();
		accVouchDetail = accVouchDetailService.queryAccVouchDetailByCode(mapVo);
		mode.addAttribute("vouch_detail_id", accVouchDetail.getVouch_detail_id());
		mode.addAttribute("vouch_id", accVouchDetail.getVouch_id());
		mode.addAttribute("group_id", accVouchDetail.getGroup_id());
		mode.addAttribute("hos_id", accVouchDetail.getHos_id());
		mode.addAttribute("copy_code", accVouchDetail.getCopy_code());
		mode.addAttribute("acc_year", accVouchDetail.getAcc_year());
		mode.addAttribute("subj_id", accVouchDetail.getSubj_id());
		mode.addAttribute("vouch_row", accVouchDetail.getVouch_row());
		mode.addAttribute("summary", accVouchDetail.getSummary());
		mode.addAttribute("debit", accVouchDetail.getDebit());
		mode.addAttribute("credit", accVouchDetail.getCredit());
		mode.addAttribute("debit_w", accVouchDetail.getDebit_w());
		mode.addAttribute("credit_w", accVouchDetail.getCredit_w());
		return "hrp/acc/accvouchdetail/accVouchDetailUpdate";
	}

	/**
	 * 凭证明细表<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/accvouchdetail/updateAccVouchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouchDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accVouchDetailJson = accVouchDetailService.updateAccVouchDetail(mapVo);

		return JSONObject.parseObject(accVouchDetailJson);
	}

	/**
	 * 凭证明细表<BR>
	 * 导入
	 */

	@RequestMapping(value = "/hrp/acc/accvouchdetail/importAccVouchDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccVouchDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accVouchDetailJson = accVouchDetailService.importAccVouchDetail(mapVo);

		return JSONObject.parseObject(accVouchDetailJson);
	}

}
