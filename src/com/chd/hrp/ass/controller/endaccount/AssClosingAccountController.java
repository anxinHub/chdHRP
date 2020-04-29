package com.chd.hrp.ass.controller.endaccount;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.dict.AssStartModService;
import com.chd.hrp.ass.service.endaccount.AssClosingAccountService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

@Controller
public class AssClosingAccountController extends BaseController {

	@Resource(name = "assClosingAccountService")
	private final AssClosingAccountService assClosingAccountService = null;

	@Resource(name = "assStartModService")
	private final AssStartModService assStartModService = null;

	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;

	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assendaccount/assClosingAccountMainPage", method = RequestMethod.GET)
	public String assClosingAccountMainPage(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "05");
		Integer acc_year = 0;
		Integer acc_month = 0;
		// 获取系统启用年月
		String SysYearMonth = modStartService.queryModStartByModeCode(mapVo);
		
		if(SysYearMonth == null){
			mode.addAttribute("year", "0000");
			mode.addAttribute("month", "00");
			mode.addAttribute("last_year", "0000");
			mode.addAttribute("last_month", "00");
			return "hrp/ass/assendaccount/assClosingAccountMain";
		}

		Map<String, Object> costCur = assClosingAccountService.queryAssCurYearMonth(mapVo);
				
		if (costCur == null) {

			mode.addAttribute("year", "0000");

			mode.addAttribute("month", "00");
			// 获取上个物流期间
			acc_year = Integer.parseInt(SessionManager.getAcctYear());
			acc_month = 12;
		} else {

			//if (costCur.get("ass_flag").toString().equals("1")) {
				// 获取系统当前年月
				Integer mon = Integer.parseInt(costCur.get("acc_year")
						.toString() + costCur.get("acc_month").toString());

				Integer sessmon = Integer.parseInt(SysYearMonth);

				if (mon > sessmon) {

					mode.addAttribute("year", costCur.get("acc_year"));

					mode.addAttribute("month", costCur.get("acc_month"));
					// 获取上个物流期间
					acc_year = Integer.parseInt(costCur.get("acc_year")
							.toString());
					acc_month = Integer.parseInt(costCur.get("acc_month")
							.toString());

				} else {
					mapVo.put("SysYearMonth", SysYearMonth);
					// 查询系统启用日期是否结账
					Map<String, Object> costCurSysYearMonth = queryAssSysYearMonth(mapVo);
					String yearmonth = costCurSysYearMonth.get("SysYearMonth")
							.toString();
					// 对比日期
					/*
					 * if (yearmonth.subSequence(4, 6).toString().equals("12"))
					 * { mode.addAttribute("year", "0000");
					 * 
					 * mode.addAttribute("month","00"); // 获取上个物流期间 acc_year =
					 * Integer.parseInt(yearmonth.subSequence(0, 4).toString());
					 * acc_month = Integer.parseInt(yearmonth.subSequence(4,
					 * 6).toString()); }else{
					 */
					mode.addAttribute("year", yearmonth.subSequence(0, 4)
							.toString());

					mode.addAttribute("month", yearmonth.subSequence(4, 6)
							.toString());
					/*
					 * if ( yearmonth.equals(SysYearMonth)) { acc_year=0000;
					 * acc_month=00;
					 * 
					 * }else{
					 */
					// 获取上个物流期间
					acc_year = Integer.parseInt(yearmonth.subSequence(0, 4)
							.toString());
					acc_month = Integer.parseInt(yearmonth.subSequence(4, 6)
							.toString());
					/* } */

					/* } */
				}

			//}

		}
		Integer last_year = acc_year;
		Integer last_month = acc_month;

		if (acc_month == 1) {

			last_month = 12;

			last_year = acc_year - 1;

		} /*
			 * else if (acc_month == 12) { last_month = acc_month;
			 * 
			 * last_year = acc_year; }
			 */ 
		else {

			last_month = acc_month - 1;

			last_year = acc_year;
		}
		// 判断下一期间是否存在
		Map<String, Object> existsMap = new HashMap<String, Object>();

		existsMap.put("group_id", SessionManager.getGroupId());

		existsMap.put("hos_id", SessionManager.getHosId());

		existsMap.put("copy_code", SessionManager.getCopyCode());

		existsMap.put("year", last_year);

		existsMap.put(
				"month",
				(last_month.toString().length() == 1) ? ('0' + last_month
						.toString()) : last_month.toString());
		/*
		 * 
		 * 引用物流 判断日期是否在会计期间
		 */
		int flag = matCommonMapper.existsAccYearMonthCheck(existsMap);

		if (flag == 0) {
			mode.addAttribute("last_year", "");
			mode.addAttribute("last_month", "");
		} else {
			mode.addAttribute("last_year", last_year.toString());
			mode.addAttribute(
					"last_month",
					(last_month.toString().length() == 1) ? ('0' + last_month
							.toString()) : last_month.toString());
		}
		return "hrp/ass/assendaccount/assClosingAccountMain";
	}

	// 递归查询没有结账的月份
	public Map<String, Object> queryAssSysYearMonth(Map<String, Object> mapVo) {

		Map<String, Object> costCurSysYearMonth = assClosingAccountService
				.queryAssSysYearMonth(mapVo);

		if (costCurSysYearMonth != null
				&& costCurSysYearMonth.get("ass_flag").toString().equals("1")) {
			Integer SysYear = Integer.parseInt(mapVo.get("SysYearMonth")
					.toString().substring(0, 4));
			Integer SysMoth = Integer.parseInt(mapVo.get("SysYearMonth")
					.toString().substring(4, 6)) + 1;
			String SysMoths = (Integer.toString(SysMoth).length() == 1) ? ('0' + Integer
					.toString(SysMoth)) : Integer.toString(SysMoth);
			//月份大于12跳转下年
			if (SysMoth > 12) {
				String SysYearMonth = Integer.toString(SysYear + 1) + "01";
				mapVo.put("SysYearMonth", SysYearMonth);
				return queryAssSysYearMonth(mapVo);
			} else {
				String SysYearMonth = Integer.toString(SysYear) + SysMoths;
				mapVo.put("SysYearMonth", SysYearMonth);
				return queryAssSysYearMonth(mapVo);

			}

		}
		return mapVo;
	}

	@RequestMapping(value = "/hrp/ass/assendaccount/queryAssYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssYearMonth(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "05");
		String assAllotInSpecial = assClosingAccountService
				.queryAssYearMonth(getPage(mapVo));

		return JSONObject.parseObject(assAllotInSpecial);
	}

	@RequestMapping(value = "/hrp/ass/assendaccount/assColsingAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assColsingAccount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "05");
		String str = assClosingAccountService.collectDepreALL(mapVo);

		return JSONObject.parseObject(str);
	}

	@RequestMapping(value = "/hrp/ass/assendaccount/assDelColsingAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assDelColsingAccount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "05");
		
		String str = assClosingAccountService.collectDelDepreALL(mapVo);

		return JSONObject.parseObject(str);
	}
}
