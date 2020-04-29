/**
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Copyright: Copyright (c) 2015-2-14
 */
package com.chd.hrp.acc.controller;

import java.util.Calendar;
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
import com.chd.hrp.acc.service.AccYearMonthService;
import com.chd.hrp.acc.service.HrpAccSelectService;
import com.chd.hrp.acc.service.commonbuilder.AccCheckTypeService;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpAccSelectController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HrpAccSelectController.class);

	@Resource(name = "hrpAccSelectService")
	private final HrpAccSelectService hrpAccSelectService = null;

	@Resource(name = "accYearMonthService")
	private final AccYearMonthService accYearMonthService = null;

	@Resource(name = "accCheckTypeService")
	private final AccCheckTypeService accCheckTypeService = null;

	@RequestMapping(value = "/hrp/acc/querySubjDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjDict(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.querySubjDict(mapVo);
		return subjType;
	}

	/**
	 * 项目预算中查询收入项目,以410102或4201为前缀
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/querySubjToProj", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjToProj(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", Calendar.getInstance().get(Calendar.YEAR));
		String subjType = hrpAccSelectService.querySubjToProj(mapVo);
		return subjType;
	}

	@RequestMapping(value = "/hrp/acc/queryBudgSysDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSysDict(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.queryBudgSysDict(mapVo);
		return subjType;
	}

	@RequestMapping(value = "/hrp/acc/queryBudgPaymentItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItemDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAccSelectService.queryBudgPaymentItemDict(mapVo);
		return subjType;
	}
	
	@RequestMapping(value = "/hrp/acc/queryBudgPaymentItemDictDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItemDictDept(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String subjType = hrpAccSelectService.queryBudgPaymentItemDictDept(mapVo);
		return subjType;
	}

	@RequestMapping(value = "/hrp/acc/queryBudgPaymentItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItem(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.queryBudgPaymentItem(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		return json.toJSONString();
	}

	@RequestMapping(value = "/hrp/acc/queryBusiType", method = RequestMethod.POST)
	@ResponseBody
	public String queryBusiType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.queryBusiType(mapVo);
		return subjType;
	}

	// 科目类别
	@RequestMapping(value = "/hrp/acc/querySubjType", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.querySubjType(mapVo);
		return subjType;
	}

	// 科目类别
	@RequestMapping(value = "/hrp/acc/querySubjTypeKind", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjTypeKind(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.querySubjTypeKind(mapVo);
		return subjType;
	}

	// 基本分析指标类别
	@RequestMapping(value = "/hrp/acc/queryAccBasType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBasType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.queryAccBasType(mapVo);
		return subjType;
	}
	
	// 五性分析指标类别
		@RequestMapping(value = "/hrp/acc/queryAccWxType", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccWxType(@RequestParam Map<String, Object> mapVo,
				Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String subjType = hrpAccSelectService.queryAccWxType(mapVo);
			return subjType;
		}

	@RequestMapping(value = "/hrp/acc/queryCheckTypeBySubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckTypeBySubj(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String yearMonth = accCheckTypeService.queryAccCheckTypeBySelect(mapVo);
		return yearMonth;
	}

	@RequestMapping(value = "/hrp/acc/queryYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public String queryYearMonth(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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
		String yearMonth = accYearMonthService.queryYearMonthBySelect(mapVo);
		return yearMonth;
	}

	@RequestMapping(value = "/hrp/acc/queryMonth", method = RequestMethod.POST)
	@ResponseBody
	public String queryMonth(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
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
		String yearMonth = accYearMonthService.queryMonthBySelect(mapVo);
		return yearMonth;
	}

	@RequestMapping(value = "/hrp/acc/queryCheckItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckItem(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String yearMonth = hrpAccSelectService.queryCheckItem(mapVo);
		return yearMonth;
	}

	@RequestMapping(value = "/hrp/acc/queryCheckItemById", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckItemById(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String yearMonth = hrpAccSelectService.queryCheckItemById(mapVo);
		return yearMonth;
	}

	// 科目
	@RequestMapping(value = "/hrp/acc/querySubj", method = RequestMethod.POST)
	@ResponseBody
	public String querySubj(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
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

		String subj = hrpAccSelectService.querySubj(mapVo);
		return subj;
	}

	// 科目 账簿中 科目选择器
	@RequestMapping(value = "/hrp/acc/querySubjByAccount", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjByAccount(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		String subj = hrpAccSelectService.querySubjByAccount(mapVo);

		return subj;
	}

	// 科目 不区别级次
	@RequestMapping(value = "/hrp/acc/querySubjBylevel", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjBylevel(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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

		String subj = hrpAccSelectService.querySubjBylevel(mapVo);
		return subj;
	}

	// 根据核算项目查找会计科目
	@RequestMapping(value = "/hrp/acc/querySubjByProjCheck", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjByProjCheck(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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

		String subj = hrpAccSelectService.querySubjByProjCheck(mapVo);
		return subj;
	}

	// 科目全称
	@RequestMapping(value = "/hrp/acc/querySubjAll", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjAll(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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

		String subj = hrpAccSelectService.querySubjAll(mapVo);
		return subj;
	}

	// 科目性质
	@RequestMapping(value = "/hrp/acc/querySubjNature", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjNature(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjNature = hrpAccSelectService.querySubjNature(mapVo);
		return subjNature;
	}

	// 凭证类型
	@RequestMapping(value = "/hrp/acc/queryVouchType", method = RequestMethod.POST)
	@ResponseBody
	public String queryVouchType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String vouchType = hrpAccSelectService.queryVouchType(mapVo);
		return vouchType;
	}

	// 币种
	@RequestMapping(value = "/hrp/acc/queryCur", method = RequestMethod.POST)
	@ResponseBody
	public String queryCur(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
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
		String cur = hrpAccSelectService.queryCur(mapVo);
		return cur;
	}

	// 核算类型
	@RequestMapping(value = "/hrp/acc/queryCheckType", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String checkType = hrpAccSelectService.queryCheckType(mapVo);
		return checkType;
	}

	// 支出功能分类
	@RequestMapping(value = "/hrp/acc/queryFunType", method = RequestMethod.POST)
	@ResponseBody
	public String queryFunType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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
		String fun = hrpAccSelectService.queryFunType(mapVo);
		JSONArray json = JSONArray.parseArray(fun);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		logger.warn("json:" + json.toString());
		return json.toString();
	}

	// 支出经济分类
	@RequestMapping(value = "/hrp/acc/queryEcoType", method = RequestMethod.POST)
	@ResponseBody
	public String queryEcoType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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
		String eco = hrpAccSelectService.queryEcoType(mapVo);
		JSONArray json = JSONArray.parseArray(eco);
		String str = "{\"id\":\"0\",\"text\":\"TOP\"}";
		JSONObject jsonObj = JSONArray.parseObject(str);
		json.add(0, jsonObj);
		logger.warn("json:" + json.toString());
		return json.toString();
	}

	// 科目级次
	@RequestMapping(value = "/hrp/acc/querySubjLevel", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjLevel(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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
		String eco = hrpAccSelectService.querySubjLevel(mapVo);

		return eco;
	}

	@RequestMapping(value = "/hrp/acc/queryFunLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryFunLevel(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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
		String eco = hrpAccSelectService.queryFunLevel(mapVo);

		return eco;
	}

	@RequestMapping(value = "/hrp/acc/queryEcoLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryEcoLevel(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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
		String eco = hrpAccSelectService.queryEcoLevel(mapVo);

		return eco;
	}

	@RequestMapping(value = "/hrp/acc/queryCashType", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String checkType = hrpAccSelectService.queryCashType(mapVo);
		return checkType;
	}

	@RequestMapping(value = "/hrp/acc/queryDeptType", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		String checkType = hrpAccSelectService.queryDeptType(mapVo);
		return checkType;
	}

	@RequestMapping(value = "/hrp/acc/queryDeptNatur", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptNatur(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		String checkType = hrpAccSelectService.queryDeptNatur(mapVo);
		return checkType;
	}

	@RequestMapping(value = "/hrp/acc/queryDeptOut", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptOut(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		String checkType = hrpAccSelectService.queryDeptOut(mapVo);
		return checkType;
	}

	@RequestMapping(value = "/hrp/acc/queryEmpPay", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpPay(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String checkType = hrpAccSelectService.queryEmpPay(mapVo);
		return checkType;
	}

	@RequestMapping(value = "/hrp/acc/queryAccVouchState", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccVouchState(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String checkType = hrpAccSelectService.queryAccVouchState(mapVo);
		return checkType;
	}

	@RequestMapping(value = "/hrp/acc/queryPayType", method = RequestMethod.POST)
	@ResponseBody
	public String queryPayType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String payType = hrpAccSelectService.queryPayType(mapVo);
		return payType;
	}

	@RequestMapping(value = "/hrp/acc/queryAccFinaContent", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccFinaContent(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		String accFinaContent = hrpAccSelectService.queryAccFinaContent(mapVo);
		return accFinaContent;
	}

	@RequestMapping(value = "/hrp/acc/queryAccWageScheme", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWageScheme(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accFinaContent = hrpAccSelectService.queryAccWageScheme(mapVo);

		return accFinaContent;
	}

	@RequestMapping(value = "/hrp/acc/queryAccWageItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWageItem(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acc_year") == null || "".equals(mapVo.get("acc_year"))
				|| "本年度".equals(mapVo.get("acc_year"))
				|| "上一年".equals(mapVo.get("acc_year"))) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		String accFinaContent = hrpAccSelectService.queryAccWageItem(mapVo);

		return accFinaContent;
	}

	@RequestMapping(value = "/hrp/acc/queryCheckTable", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckTable(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String checkTable = accCheckTypeService.queryCheckTable(mapVo);
		return checkTable;
	}

	@RequestMapping(value = "/hrp/acc/queryCheckTypeBySubjId", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckTypeBySubjId(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String checkTable = accCheckTypeService.queryCheckTypeBySubjId(mapVo);
		return checkTable;
	}

	@RequestMapping(value = "/hrp/acc/queryAccWage", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.containsKey("wage_type")) {

			if ("meals".equals(mapVo.get("wage_type").toString())) {

				mapVo.put("wage_where", " and wage_name like '%餐%'");

			} else if ("salary".equals(mapVo.get("wage_type").toString())) {

				mapVo.put("wage_where", " and wage_name not like '%餐%'");
			}
		}

		mapVo.put("user_id", SessionManager.getUserId());

		String accFinaContent = hrpAccSelectService.queryAccWage(mapVo);

		return accFinaContent;
	}

	@RequestMapping(value = "/hrp/acc/queryDeptDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDictNo(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accFinaContent = hrpAccSelectService.queryDeptDictNo(mapVo);

		return accFinaContent;
	}

	// 根据部门加载职工
	@RequestMapping(value = "/hrp/acc/queryEmpDictById", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDictById(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accFinaContent = hrpAccSelectService.queryEmpDictById(mapVo);

		return accFinaContent;
	}

	// 账户类别
	@RequestMapping(value = "/hrp/acc/queryWageType", method = RequestMethod.POST)
	@ResponseBody
	public String queryWageType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accFinaContent = hrpAccSelectService.queryWageType(mapVo);

		return accFinaContent;
	}

	// 账户类别
	@RequestMapping(value = "/hrp/acc/queryEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDict(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accFinaContent = hrpAccSelectService.queryEmpDict(mapVo);

		return accFinaContent;
	}

	// 员工信息
	@RequestMapping(value = "/hrp/acc/queryAllEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAllEmpDict(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accFinaContent = hrpAccSelectService.queryAllEmpDict(mapVo);

		return accFinaContent;
	}

	// 银行信息
	@RequestMapping(value = "/hrp/acc/queryBankSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryBankSelect(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String accFinaContent = hrpAccSelectService.queryBankSelect(mapVo);

		return accFinaContent;
	}

	// 现金流量项目
	@RequestMapping(value = "/hrp/acc/queryCashItemSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryCashItemSelect(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accFinaContent = hrpAccSelectService.queryCashItemSelect(mapVo);

		return accFinaContent;
	}

	// 患者类别
	@RequestMapping(value = "/hrp/acc/queryAccPatientType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPatientType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String accFinaContent = hrpAccSelectService.queryAccPatientType(mapVo);

		return accFinaContent;
	}

	// 收费类别
	@RequestMapping(value = "/hrp/acc/queryAccChargeType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccChargeType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String accFinaContent = hrpAccSelectService.queryAccChargeType(mapVo);

		return accFinaContent;
	}

	// 现金流量项目
	@RequestMapping(value = "/hrp/acc/queryDeptOutSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptOutSelect(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		String accDeptOut = hrpAccSelectService.queryDeptOutSelect(mapVo);

		return accDeptOut;
	}

	// 查询报表字典
	@RequestMapping(value = "/hrp/acc/querySuperReportDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperReportDict(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String rejson = hrpAccSelectService.querySuperReportDict(mapVo);
		return rejson;
	}

	// 查询报表字典
	@RequestMapping(value = "/hrp/acc/queryCreateUser", method = RequestMethod.POST)
	@ResponseBody
	public String queryCreateUser(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String rejson = hrpAccSelectService.queryCreateUser(mapVo);

		return rejson;
	}

	// 查询报表字典
	@RequestMapping(value = "/hrp/acc/queryAccPayAttr", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPayAttr(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String rejson = hrpAccSelectService.queryAccPayAttr(mapVo);

		return rejson;
	}

	// 核算分类下拉框
	@RequestMapping(value = "/hrp/acc/queryCheckItemType", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckItemType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String rejson = hrpAccSelectService.queryCheckItemType(mapVo);

		return rejson;
	}

	// 科室字典(根据参数决定是否按权限)
	@RequestMapping(value = "/hrp/acc/queryHosDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDept(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("is_perm") == null) {
			mapVo.put("is_perm", "0");
		}

		String rejson = hrpAccSelectService.queryHosDept(mapVo);

		return rejson;
	}
	
	//科室分类查询
	@RequestMapping(value = "/hrp/acc/queryHosDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptKind(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		String rejson = hrpAccSelectService.queryHosDeptKind(mapVo);

		return rejson;
	}

	// 工资项目性质字典
	@RequestMapping(value = "/hrp/acc/queryAccWageItemNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWageItemNature(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String rejson = hrpAccSelectService.queryAccWageItemNature(mapVo);

		return rejson;
	}

	// 工资项目类型字典
	@RequestMapping(value = "/hrp/acc/queryAccWageItemType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWageItemType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return hrpAccSelectService.queryAccWageItemType(mapVo);
	}

	// 会计年度
	@RequestMapping(value = "/hrp/acc/queryAccYear", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccYear(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return hrpAccSelectService.queryAccYear(mapVo);
	}

	// 会计年度
	@RequestMapping(value = "/hrp/acc/queryAccTarget", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccTarget(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return hrpAccSelectService.queryAccTarget(mapVo);
	}

	// 票据类型
	@RequestMapping(value = "/hrp/acc/queryAccPaperType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return hrpAccSelectService.queryAccPaperType(mapVo);
	}

	// 会计年度
	@RequestMapping(value = "/hrp/acc/queryAccWageItemColumn", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWageItemColumn(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return hrpAccSelectService.queryAccWageItemColumn(mapVo);
	}

	// 会计年度
	@RequestMapping(value = "/hrp/acc/queryAccWageColumn", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccWageColumn(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

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

		return hrpAccSelectService.queryAccWageColumn(mapVo);
	}

	// 查询自定义辅助核算字典对应表
	@RequestMapping(value = "/hrp/acc/queryAccBusiZCheck", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBusiZCheck(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrpAccSelectService.queryAccBusiZCheck(mapVo);
	}

	// 多栏方案
	@RequestMapping(value = "/hrp/acc/queryAccMultiPlan", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccMultiPlan(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return hrpAccSelectService.queryAccMultiPlan(mapVo);
	}

	@RequestMapping(value = "/hrp/acc/queryAccYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccYearMonth(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acc_year") == null
				|| "本年度".equals(mapVo.get("acc_year"))
				|| "上一年".equals(mapVo.get("acc_year"))) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		return hrpAccSelectService.queryAccYearMonth(mapVo);
	}

	@RequestMapping(value = "/hrp/acc/queryGroupDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryGroupDict(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		return hrpAccSelectService.queryGroupDict(mapVo);
	}

	@RequestMapping(value = "/hrp/acc/queryHosInfo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfo(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		return hrpAccSelectService.queryHosInfo(mapVo);
	}

	@RequestMapping(value = "/hrp/acc/queryHosCopy", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosCopy(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		return hrpAccSelectService.queryHosCopy(mapVo);
	}

	@RequestMapping(value = "/hrp/acc/queryAccTellType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccTellType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return hrpAccSelectService.queryAccTellType(mapVo);
	}

	// pj查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name
	@RequestMapping(value = "/hrp/acc/querySubjCode", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjCode(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
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
		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.querySubjCode(mapVo);
	}

	// pj查询科目下拉框，根据条件匹配通用，不分页，value=id，text=code name
	@RequestMapping(value = "/hrp/acc/querySubjId", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjId(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

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
		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.querySubjId(mapVo);
	}

	// pj根据科目编码，查询核算类型下拉框,根据条件匹配通用，不分页
	@RequestMapping(value = "/hrp/acc/queryCheckTypeBySubjCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckTypeBySubjCode(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.queryCheckTypeBySubjCode(mapVo);
	}

	// pj根据核算类型，查询核算项下拉框,根据条件匹配通用，不分页
	@RequestMapping(value = "/hrp/acc/queryCheckItemByType", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckItemByType(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("check_type_id") == null
				|| mapVo.get("check_type_id").equals("")) {
			return "{}";
		}

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
		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.queryCheckItemByType(mapVo);
	}

	// 根据核算类型，查询核算项下拉框,根据条件匹配通用
	@RequestMapping(value = "/hrp/acc/queryCheckItemByTypeByCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckItemByTypeByCode(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("check_type_id") == null
				|| mapVo.get("check_type_id").equals("")) {
			return "{}";
		}

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
		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.queryCheckItemByTypeByCode(mapVo);
	}

	// 根据核算类型，查询核算项下拉框,根据条件匹配通用，取前100条
	@RequestMapping(value = "/hrp/acc/queryCheckItemByTypeFy", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckItemByTypeFy(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("check_type_id") == null
				|| mapVo.get("check_type_id").equals("")) {
			return "{}";
		}

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
		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.queryCheckItemByTypeFy(mapVo);
	}

	// 出纳账管理 摘要字典 下拉框
	@RequestMapping(value = "/hrp/acc/queryAccTellSummaryById", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccTellSummaryById(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());

		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.queryAccTellSummaryById(mapVo);
	}

	@RequestMapping(value = "/hrp/acc/queryAccHisLog", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccHisLog(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.queryAccHisLog(mapVo);
		return subjType;
	}

	@RequestMapping(value = "/hrp/acc/queryProjEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjEmp(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.queryProjEmp(mapVo);
		return subjType;
	}

	@RequestMapping(value = "/hrp/acc/queryBusiTypeByVouch", method = RequestMethod.POST)
	@ResponseBody
	public String queryBusiTypeByVouch(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = hrpAccSelectService.queryBusiTypeByVouch(mapVo);
		return subjType;
	}

	/**
	 * 集团化 会计科目 根据医院 账套进行级联
	 */
	@RequestMapping(value = "/hrp/acc/querySubjByHosCopyRela", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjByHosCopyRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("acc_year", SessionManager.getAcctYear());
		String subjType = hrpAccSelectService.querySubjByHosCopyRela(mapVo);
		return subjType;
	}

	/**
	 * 集团化 查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name search=拼音码 五笔码检索 用于
	 * 自定义辅助核算余额表
	 */
	@RequestMapping(value = "/hrp/acc/querySubjCodeByRela", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjCodeByRela(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("key") != null && !mapVo.get("key").equals("")) {
			String key = mapVo.get("key").toString();
			mapVo.put("key", key.toUpperCase());
		}

		return hrpAccSelectService.querySubjCodeByRela(mapVo);
	}

	// 科目 账簿中 科目选择器
	@RequestMapping(value = "/hrp/acc/querySubjByAccountRela", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjByAccountRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		String subj = hrpAccSelectService.querySubjByAccountRela(mapVo);

		return subj;
	}

	// 账簿方案列表
	@RequestMapping(value = "/hrp/acc/queryAccBookSch", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBookSch(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());

		String str = hrpAccSelectService.queryAccBookSch(mapVo);

		return str;
	}

	// 账簿方案--单位列表
	@RequestMapping(value = "/hrp/acc/querySysHosAll", method = RequestMethod.POST)
	@ResponseBody
	public String querySysHosAll(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("user_id", SessionManager.getUserId());

		mapVo.put("this_hos_id", SessionManager.getHosId());
		mapVo.put("this_hos_name", SessionManager.getHosName());

		String str = hrpAccSelectService.querySysHosAll(mapVo);

		return str;
	}

	// 账簿方案--账套列表(与单位下拉列表联动)
	@RequestMapping(value = "/hrp/acc/querySysHosCopyAll", method = RequestMethod.POST)
	@ResponseBody
	public String querySysHosCopyAll(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("user_id", SessionManager.getUserId());
		if (mapVo.get("hos_id") == null
				|| "".equals(mapVo.get("hos_id").toString())) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("this_copy_code", SessionManager.getCopyCode());
		mapVo.put("this_copy_name", SessionManager.getCopyName());

		String str = hrpAccSelectService.querySysHosCopyAll(mapVo);

		return str;
	}

	// 四院查询自定义领用人
	@RequestMapping(value = "/hrp/acc/queryAccPaperCheques", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPaperCheques(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());

		String str = hrpAccSelectService.queryAccPaperCheques(mapVo);

		return str;
	}
	//自定义属性
	@RequestMapping(value = "/hrp/acc/queryEmpSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String checkType = hrpAccSelectService.queryEmpSet(mapVo);
		return checkType;
	}
	
	/* ==============================东阳分支下拉框=============================== */
	
	// 科目
		@RequestMapping(value = "/hrp/acc/querySubjByCode", method = RequestMethod.POST)
		@ResponseBody
		public String querySubjByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String subj = hrpAccSelectService.querySubjByCode(mapVo);
			return subj;
		}

		//
		@RequestMapping(value = "/hrp/acc/queryDrugsPrepaType", method = RequestMethod.POST)
		@ResponseBody
		public String queryDrugsPrepaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("acc_year", SessionManager.getAcctYear());

			String subj = hrpAccSelectService.queryDrugsPrepaType(mapVo);

			return subj;
		}

		@RequestMapping(value = "/hrp/acc/queryAccDrugsPrepaType", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccDrugsPrepaType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

			String rejson = hrpAccSelectService.queryAccDrugsPrepaType(mapVo);

			return rejson;
		}

		@RequestMapping(value = "/hrp/acc/queryHosSupDictUniversalMethod", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupDictUniversalMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = hrpAccSelectService.queryHosSupDictUniversalMethod(mapVo);
			return hrpMatSelect;
		}

		/**
		 * 付款方式(结算方式)
		 * 
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/acc/queryAccPayType", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpAccSelectService.queryAccPayType(mapVo);
			return hrpMatSelect;
		}

		/**
		 * 仓库(虚仓)
		 * 
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/acc/queryAccVirStore", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccVirStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hrpMatSelect = hrpAccSelectService.queryAccVirStore(mapVo);
			return hrpMatSelect;
		}

		// 通过参数控制 仓库权限
		@RequestMapping(value = "/hrp/acc/queryAccStoreDictDate", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccStoreDictDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String hrpMatSelect = hrpAccSelectService.queryAccStoreDictDate(mapVo);
			return hrpMatSelect;
		}

		/**
		 * 查询制单人
		 * 
		 * @param mapVo
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/acc/querySysUser", method = RequestMethod.POST)
		@ResponseBody
		public String querySysUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}

			String hrpMatSelect = hrpAccSelectService.querySysUser(mapVo);
			return hrpMatSelect;
		}

		// 供应商变更不显示停用
		@RequestMapping(value = "/hrp/acc/queryHosSupDictDisable", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupDictDisable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hrpMatSelect = hrpAccSelectService.queryHosSupDictDisable(mapVo);
			return hrpMatSelect;
		}

		/**
		 * 采购发票 付款条件下拉框
		 * 
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/acc/queryAccPayTerm", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccPayTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			String hrpMatSelect = hrpAccSelectService.queryAccPayTerm(mapVo);
			return hrpMatSelect;
		}

		// 通过参数控制 编制科室权限
		@RequestMapping(value = "/hrp/acc/queryAccDeptDictDate", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccDeptDictDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String hrpMatSelect = hrpAccSelectService.queryAccDeptDictDate(mapVo);
			return hrpMatSelect;
		}

		/**
		 * 资产性质下拉框检索
		 * 
		 * @param entityMap
		 * @param rowBounds
		 * @return
		 * @throws DataAccessException
		 */
		@RequestMapping(value = "/hrp/acc/queryAccNaturs", method = RequestMethod.POST)
		@ResponseBody
		public String queryAssNaturs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("user_id", SessionManager.getUserId());
			String subjType = hrpAccSelectService.queryAccNaturs(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			if (mapVo.get("query_key") == null) {
				return json.toString();
			} else {
				String str = "{\"id\":\"\",\"text\":\"全部\"}";
				JSONObject jsonObj = JSONArray.parseObject(str);
				json.add(0, jsonObj);
				return json.toString();
			}
		}

		/**
		 * 采购入库供应商下拉框检索
		 * 
		 * @param entityMap
		 * @param rowBounds
		 * @return
		 * @throws DataAccessException
		 */
		@RequestMapping(value = "/hrp/acc/queryHosSupDictNo", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String subjType = hrpAccSelectService.queryHosSupDictNo(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			if (mapVo.get("query_key") == null) {
				return json.toString();
			} else {
				String str = "{\"id\":\"\",\"text\":\"全部\"}";
				JSONObject jsonObj = JSONArray.parseObject(str);
				json.add(0, jsonObj);
				return json.toString();
			}
		}

		/**
		 * 合同下拉框检索
		 * 
		 * @param entityMap
		 * @param rowBounds
		 * @return
		 * @throws DataAccessException
		 */
		@RequestMapping(value = "/hrp/acc/queryContractMain", method = RequestMethod.POST)
		@ResponseBody
		public String queryContractMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("user_id", SessionManager.getUserId());
			String subjType = hrpAccSelectService.queryContractMain(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			if (mapVo.get("query_key") == null) {
				return json.toString();
			} else {
				String str = "{\"id\":\"\",\"text\":\"全部\"}";
				JSONObject jsonObj = JSONArray.parseObject(str);
				json.add(0, jsonObj);
				return json.toString();
			}
		}

		/**
		 * 供应商下拉框检索
		 * 
		 * @param entityMap
		 * @param rowBounds
		 * @return
		 * @throws DataAccessException
		 */
		@RequestMapping(value = "/hrp/acc/queryHosSupDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("user_id", SessionManager.getUserId());
			String subjType = hrpAccSelectService.queryHosSupDict(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			if (mapVo.get("query_key") == null) {
				return json.toString();
			} else {
				String str = "{\"id\":\"\",\"text\":\"全部\"}";
				JSONObject jsonObj = JSONArray.parseObject(str);
				json.add(0, jsonObj);
				return json.toString();
			}
		}

		/**
		 * 仓库下拉框检索
		 * 
		 * @param entityMap
		 * @param rowBounds
		 * @return
		 * @throws DataAccessException
		 */
		@RequestMapping(value = "/hrp/acc/queryHosStoreDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}

			mapVo.put("user_id", SessionManager.getUserId());
			String subjType = hrpAccSelectService.queryHosStoreDict(mapVo);
			JSONArray json = JSONArray.parseArray(subjType);
			if (mapVo.get("query_key") == null) {
				return json.toString();
			} else {
				String str = "{\"id\":\"\",\"text\":\"全部\"}";
				JSONObject jsonObj = JSONArray.parseObject(str);
				json.add(0, jsonObj);
				return json.toString();
			}
		}	
		
		@RequestMapping(value = "/hrp/acc/queryGroupDictDataType", method = RequestMethod.POST)
		@ResponseBody
		public String queryGroupDictDataType(@RequestParam Map<String, Object> mapVo,
				Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());

			}

			return hrpAccSelectService.queryGroupDictDataType(mapVo);
		}

		@RequestMapping(value = "/hrp/acc/queryHosInfoDataType", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosInfoDataType(@RequestParam Map<String, Object> mapVo,
				Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());

			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());

			}

			return hrpAccSelectService.queryHosInfoDataType(mapVo);
		}

		@RequestMapping(value = "/hrp/acc/queryHosCopyDataType", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosCopyDataType(@RequestParam Map<String, Object> mapVo,
				Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());

			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());

			}

			return hrpAccSelectService.queryHosCopyDataType(mapVo);
		}
		
		@RequestMapping(value = "/hrp/acc/queryAccMedTypeHis", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccMedTypeHis(@RequestParam Map<String, Object> mapVo,
				Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());

			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());

			}

			return hrpAccSelectService.queryAccMedTypeHis(mapVo);
		}
		
		/**
		 * 查询业务字典表下拉框
		 * 
		 * @param mapVo
		 * @param mode
		 * @return
		 */
		@RequestMapping(value = "/hrp/acc/queryHosDictType", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosDictType(@RequestParam Map<String, Object> mapVo, Model mode) {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String hosNature = hrpAccSelectService.queryHosDictTypeDict(mapVo);
			return hosNature;
		}
		/**
		 * 查询业务字典表下拉框
		 * 
		 * @param mapVo
		 * @param mode
		 * @return
		 */
		@RequestMapping(value = "/hrp/acc/queryInitAccDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryInitAccDict(@RequestParam Map<String, Object> mapVo, Model mode) {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String hosNature = hrpAccSelectService.queryInitAccDict(mapVo);
			return hosNature;
		}
		
		//业务类型
		@RequestMapping(value = "/hrp/acc/queryAccYewuType", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccYewuType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("mod_code") == null) {
				mapVo.put("mod_code", SessionManager.getModCode());
			}

			return hrpAccSelectService.queryAccYewuType(mapVo);
		}
		
		//业务字典
		@RequestMapping(value = "/hrp/acc/queryAccYewuDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccYewuDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("mod_code") == null) {
				mapVo.put("mod_code", SessionManager.getModCode());
			}

			return hrpAccSelectService.queryAccYewuDict(mapVo);
		}
		@RequestMapping(value = "/hrp/acc/queryAccBudgRange", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccBudgRange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("mod_code") == null) {
				mapVo.put("mod_code", SessionManager.getModCode());
			}
			
			return hrpAccSelectService.queryAccBudgRange(mapVo);
		}
		
}
