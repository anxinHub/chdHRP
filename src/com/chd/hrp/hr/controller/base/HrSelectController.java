package com.chd.hrp.hr.controller.base;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.service.base.HrSelectService;

/**
 * 
 * @ClassName: HrSelectController
 * @Description: 下拉框
 * @author zn
 * @date 2017年10月13日 下午4:37:16
 * 
 *
 */
@Controller
public class HrSelectController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrSelectController.class);

	@Resource(name = "hrSelectService")
	private HrSelectService hrSelectService = null;

	/**
	 * 性别下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDicSexSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryDicSexSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryDicSexSelect(mapVo);
		return json;

	}

	/**
	 * 民族下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDicNationSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryDicNationSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryDicNationSelect(mapVo);
		return json;

	}

	/**
	 * 政治面貌下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDicPoliticalSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryDicPoliticalSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryDicPoliticalSelect(mapVo);
		return json;

	}

	/**
	 * 婚姻下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDicMarriageSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryDicMarriageSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryDicMarriageSelect(mapVo);
		return json;

	}

	/**
	 * 户口性质下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDicResidenceSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryDicResidenceSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryDicResidenceSelect(mapVo);
		return json;

	}

	/**
	 * 部门（树型结构）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHosDeptDictTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosDeptDictTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String json = hrSelectService.queryHosDeptDictTree(mapVo);
		return json;

	}

	/**
	 * 人员档案库分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrStoreType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrStoreTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrStoreTypeSelect(mapVo);
		return json;
	}
	
	/**
	 * 职工分类下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrEmpKindSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEmpKindSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String json = hrSelectService.queryHrEmpKindSelect(mapVo);
		return json;
	}

	/**
	 * 人员档案库数据集表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrStoreTabStruc", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrStoreTabStrucSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryHrStoreTabStrucSelect(mapVo);
		return json;

	}

	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrTabType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTabTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrTabTypeSelect(mapVo);
		return json;

	}
	
	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrTableType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTableTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrTableTypeSelect(mapVo);
		return json;

	}

	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrTabStruc", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrTabStrucSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryHrTabStrucSelect(mapVo);
		return json;

	}

	@RequestMapping(value = "/hrp/hr/queryMods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryMods(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String json = hrSelectService.queryMods();
		return json;

	}
	/**
	 * 数据构建 系统结构列 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrColStrucSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("tab_code").toString()==null || mapVo.get("tab_code").toString().equals("")){
			return "{\"error\":\"数据表名称不能为空\"}";
		}
		String json = hrSelectService.queryHrColStrucSelect(mapVo);
		return json;

	}

	/**
	 * 数据构建 系统结构列 表格
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrColStrucGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrColStrucGrid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrColStrucGrid(mapVo);
		return json;

	}

	/**
	 * 数据构建 系统结构列 数据类型 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrColDataType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrColDataTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrColDataTypeSelect(mapVo);
		return json;

	}

	/**
	 * 组件类型HR_COM_TYP 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrComType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrComTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrComTypeSelect(mapVo);
		return json;

	}

	/**
	 * 条件符号HR_CON_SIGN 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrConSignSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrConSignSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrConSignSelect(mapVo);
		return json;

	}

	/**
	 * 连接符号HR_JOIN_SIGN 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrJoinSignSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrJoinSignSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrJoinSignSelect(mapVo);
		return json;

	}

	/**
	 * 代码表HR_FIIED_TAB_STRUC
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrFiiedTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrFiiedTabStrucDic(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrFiiedTabStrucDic(mapVo);
		return json;

	}

	/**
	 * 代码项数据表格HR_FIIED_DATA
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrFiiedDataDicByTabCol", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrFiiedDataDicByTabCol(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrFiiedDataDicByTabCol(mapVo);
		return json;

	}

	/**
	 * 代码项数据下拉框HR_FIIED_DATA
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrFiiedDataSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrFiiedDataSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrFiiedDataSelect(mapVo);
		return json;

	}

	/**
	 * 代码分类HR_FIIED_TAB_TYPE
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryTypeFiledTypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryTypeFiledTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryTypeFiledTypeSelect(mapVo);
		return json;

	}

	/**
	 * 人员信息 包括电话 照片
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	/*	if (mapVo.get("dept_code") != null && mapVo.get("dept_code") !="") {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_code", dept_id_no.split("@")[1]);
		}
*/
		String json = hrSelectService.queryEmp(getPage(mapVo));
		return JSONObject.parseObject(json);

	}

	/**
	 * 查询人员下拉框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hrp/hr/queryPerson", method = RequestMethod.POST)
	@ResponseBody
	public String queryPerson(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrSelectService.queryPerson(mapVo);
	}
	/**
	 * 查询人员详细信息
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryPersonnel", method = RequestMethod.POST)
	@ResponseBody
	public String queryPersonnel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

	String	hrStationTransfer = hrSelectService.queryPersonnel(mapVo);
		return hrStationTransfer;
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		throw new SysException(e.getMessage());
		}
	}
	/**
	 * 查询科室下拉框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hrp/hr/queryHosDeptSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		return hrSelectService.queryHosDeptSelect(mapVo);
	}
	
	/**
	 * 查询科室下拉框不带权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hrp/hr/queryHosAftDeptSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosAftDeptSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		return hrSelectService.queryHosAftDeptSelect(mapVo);
	}
	/**
	 * 查询人员按部门下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryEmpSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("dept_code") !=null && mapVo.get("dept_code") !="" ) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_id", dept_id_no.split("@")[1]);
			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}

		String json = hrSelectService.queryEmpSelect(mapVo);
		return json;

	}
	
	/**
	 * 查询人员按部门下拉框(只查考勤是的人)
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryEmpSelectAttend", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpSelectAttend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("dept_code") !=null && mapVo.get("dept_code") !="" ) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_id", dept_id_no.split("@")[1]);
			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}

		String json = hrSelectService.queryEmpSelectAttend(mapVo);
		return json;

	}
	//
	/**
	 * 人员
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/hr/queryEmpSelectInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpSelectInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair = hrSelectService.queryEmpSelectInfo(mapVo);
		return JSONObject.parseObject(assMyRepair);
	}
	/**
	 * 查询用戶下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryUserSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	
		String json = hrSelectService.queryUserSelect(mapVo);
		return json;

	}
	/**
	 * 部门架构快速查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDeptTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryDeptTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		

		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String subjType = hrSelectService.queryDeptTree(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		/*
		 * String str = "{\"id\":\"-1\",\"name\":\"全部折叠\"}"; JSONObject jsonObj
		 * = JSONArray.parseObject(str); json.add(0, jsonObj);
		 */
		return json.toString();
	}
	

	/**
	 * 查询职务
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDuty", method = RequestMethod.POST)
	@ResponseBody
	public String queryDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryDuty(mapVo);
		return json;

	}
	/**
	 * 查询职工类别
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryEmpType", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryEmpType(mapVo);
		return json;

	}
	/**
	 * 查询职称
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrTitle", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryHrTitle(mapVo);
		return json;

	}
	/**
	 * 查询岗位
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryStation", method = RequestMethod.POST)
	@ResponseBody
	public String queryStation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryStation(mapVo);
		return json;

	}
	/**
	 * 查询护理等级
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDicLevel", method = RequestMethod.POST)
	@ResponseBody
	public String queryDicLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryDicLevel(mapVo);
		return json;

	}

	/***
	 * 查询员工姓名 职称 职务 护理等级 科室
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryEmpDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		// mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryEmpDuty(getPage(mapVo));
		return JSONObject.parseObject(json);

	}

	/**
	 * 查询人员下拉表格
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrEmpData", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrEmpData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryHrEmpData(mapVo);
		return json;

	}

	@RequestMapping(value = "/hrp/hr/queryHosEmpGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosEmpGrid(@RequestParam Map<String, Object> mapVo) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return hrSelectService.queryHosEmpGrid(mapVo);
	}

	/**
	 * 存储过程包名
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHpmOraclePkg", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmOraclePkg(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hpmOraclePkg = hrSelectService.queryHpmOraclePkg(mapVo);

		return hpmOraclePkg;
	}

	// 分类名称
	@RequestMapping(value = "/hrp/hr/queryHpmFunType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		return hrSelectService.queryHpmFunType(mapVo);

	}

	/**
	 * 绩效函数参数取值
	 */
	@RequestMapping(value = "/hrp/hr/queryHrFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmFunParaMethod = hrSelectService.queryHrFunParaMethod(mapVo);

		return prmFunParaMethod;
	}
	/**
	 * 查询药品权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hrp/hr/queryPermTypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryPermTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		return hrSelectService.queryPermTypeSelect(mapVo);
	}
	/**
	 * 查询药品
	 */
	@RequestMapping(value = "/hrp/hr/queryMedTypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return hrSelectService.queryMedTypeSelect(mapVo);
	}
	/**
	 * 查询问题定性
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryProbNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryProbNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryProbNature(mapVo);
		return json;

	}
	/**
	 * 查询问题归类
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryProbType", method = RequestMethod.POST)
	@ResponseBody
	public String queryProbType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryProbType(mapVo);
		return json;

	}
	/**
	 * 是否下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryIsOrNot", method = RequestMethod.POST)
	@ResponseBody
	public String queryIsOrNot(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryIsOrNot(mapVo);
		return json;

	}
	
	/**
	 * 颜色下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryColorSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryColorSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryColorSelect(mapVo);
		return json;

	}
	/**
	 * 休假类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAttendTypesSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryAttendTypesSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryAttendTypesSelect(mapVo);
		return json;

	}

	/**
	 * 加班类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryOvertimeKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryOvertimeKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrSelectService.queryOvertimeKind(mapVo);
		return json;

	}
	/**
	 * 单位下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHosInfoSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfoSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
	//	mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryHosInfoSelect(mapVo);
		return json;

	}
	/**
	 * 班次下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryCalssCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryCalssCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if(mapVo.get("attend_areacode")==null || mapVo.get("attend_areacode").toString().equals("")){
			return "{}";
		}
		String json = hrSelectService.queryCalssCode(mapVo);
		return json;

	}
	/**
	 * 出勤科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryCalssDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryCalssDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String json = hrSelectService.queryCalssDept(mapVo);
		return json;

	}
	
	/**
	 * 考勤项目增加数据集表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHrStoreTabStrucSelect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrStoreTabStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryHrStoreTabStruc(mapVo);
		return json;

	}
	
	/**
	 *  职务等级
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDutyLevel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryDutyLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryDutyLevel(mapVo);
		return json;

	}
	/**
	 *  职务类别
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDutyKind", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryDutyKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryDutyKind(mapVo);
		return json;

	}

	/**
	 *  班次分类
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAttendCalssType", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAttendCalssType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());

		String json = hrSelectService.queryAttendCalssType(mapVo);
		return json;

	}
	
	/**
	 *  职务名称
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryDutyCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryDutyCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryDutyCode(mapVo);
		return json;

	}
	/**
	 *  岗位等级
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryStationLevel", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStationLevel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryStationLevel(mapVo);
		return json;

	}
	/**
	 * 考勤分类下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAttendCodeCla", method = RequestMethod.POST)
	@ResponseBody
	public String queryAttendCodeCla(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryAttendCodeCla(mapVo);
		return json;

	}
	//
	/**
	 * 考勤项目分组下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAttendFZ", method = RequestMethod.POST)
	@ResponseBody
	public String queryAttendFZ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryAttendFZ(mapVo);
		return json;

	}
	/**
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHosDeptByEmpSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptByEmpSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryHosDeptByEmpSelect(mapVo);
		return json;

	}
	
	/**
	 *  代码项下拉加载
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAttendFieldOption", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAttendFieldOption(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryAttendFieldOption(mapVo);
		return json;

	}
	
	
	/**
	 * 查询区域、排班、倒班规则、医护属性
	 */
	@RequestMapping(value = "/hrp/hr/queryDicAreaAttr", method = RequestMethod.POST)
	@ResponseBody
	public String queryDicAreaAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrSelectService.queryDicAreaAttr(mapVo);
		return json;

	}
	
	/**
	 * 查询考勤项目树
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAttendItemTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAttendItemTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryAttendItemTree(mapVo);
		return json;
	}
	
	/**
	 * 查考勤分类
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAttendTypes", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAttendTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryAttendTypes(mapVo);
		return json;
	}
	/**
	 * 查考勤分类
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAllAttendCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAllAttendCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		//mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryAllAttendCode(mapVo);
		return json;
	}
	
	/**
	 * 查询人员休假申请下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryAppEmpSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryAppEmpSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if (mapVo.get("dept_code") !=null && mapVo.get("dept_code") !="" ) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_id", dept_id_no.split("@")[1]);
			mapVo.put("dept_no", dept_id_no.split("@")[0]);
		}

		String json = hrSelectService.queryAppEmpSelect(mapVo);
		return json;

	}
	/**
	 * 查培训类别
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHosTrainTypeSelect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosTrainTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		//mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryHosTrainTypeSelect(mapVo);
		return json;
	}
	/**
	 * 查培训方式
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryHosTrainWaySelect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosTrainWaySelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		//mapVo.put("user_id", SessionManager.getUserId());
		String json = hrSelectService.queryHosTrainWaySelect(mapVo);
		return json;
	}
	
	/**
	 * 查考核方式
	 */
	@RequestMapping(value = "/hrp/hr/queryHrExamWaySelect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrExamWaySelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String json = hrSelectService.queryHrExamWaySelect(mapVo);
		return json;
	}
	/**
	 * 查培训地点
	 */
	@RequestMapping(value = "/hrp/hr/queryHrTrainSiteSelect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrTrainSiteSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String json = hrSelectService.queryHrTrainSiteSelect(mapVo);
		return json;
	}
	/**
	 * 新方法下拉框查询(根据tab_code查询related_sql)
	 */
	@RequestMapping(value = "/hrp/hr/baseSelect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String baseSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String json = hrSelectService.baseSelect(mapVo);
		return json;
	}
	/**
	 * 管理岗位架构快速查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/queryStManTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStManTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		

		String subjType = hrSelectService.queryStManTree(mapVo);
		JSONArray json = JSONArray.parseArray(subjType);
		/*
		 * String str = "{\"id\":\"-1\",\"name\":\"全部折叠\"}"; JSONObject jsonObj
		 * = JSONArray.parseObject(str); json.add(0, jsonObj);
		 */
		return json.toString();
	}
	/**
	 * 新方法查询定员信息(根据tab_code查询related_sql)
	 */
	@RequestMapping(value = "/hrp/hr/queryManNumData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryManNumData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String json = hrSelectService.queryManNumData(mapVo);
		return json;
	}
}
