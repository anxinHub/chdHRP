/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.prm.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.prm.serviceImpl.HrpPrmSelectServiceImpl;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpPrmSelectController extends BaseController {

	private static Logger logger = Logger.getLogger(HrpPrmSelectController.class);

	@Resource(name = "hrpPrmSelectService")
	private final HrpPrmSelectServiceImpl hrpPrmSelectService = null;

	// -------------------以下方法
	// 用于公用页面跳转--------------------------------------------------------------------------------------
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmKpiLibPage", method = RequestMethod.GET)
	public String prmKpiLibPage(Model mode) throws Exception {

		return "/hrp/prm/prmkpilib/prmKpiLib";

	}

	// ------------------------以上方法
	// 用于公用页面跳转---------------------------------------------------------------------------------

	// 科室类别
	@RequestMapping(value = "/hrp/prm/queryPrmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptKind = hrpPrmSelectService.queryPrmDeptKind(mapVo);
		return deptKind;
	}
	
	// 同步平台分类
	@RequestMapping(value = "/hrp/prm/quneryPlatformKind", method = RequestMethod.POST)
	@ResponseBody
	public String quneryPlatformKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptKind = hrpPrmSelectService.quneryPlatformKind(mapVo);
		return deptKind;
	}
	

	// 科室性质
	@RequestMapping(value = "/hrp/prm/queryPrmDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptNature = hrpPrmSelectService.queryPrmDeptNature(mapVo);
		return deptNature;
	}

	// 科室字典
	@RequestMapping(value = "/hrp/prm/queryPrmDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String dept = hrpPrmSelectService.queryPrmDept(mapVo);
		return dept;
	}

	// 科室变更字典
	@RequestMapping(value = "/hrp/prm/queryPrmDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptdict = hrpPrmSelectService.queryPrmDeptDict(mapVo);
		return deptdict;
	}

	// KPI指标维度

	@RequestMapping(value = "/hrp/prm/queryPrmKpiDim", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmKpiDim(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptNature = hrpPrmSelectService.queryPrmKpiDim(mapVo);
		return deptNature;
	}

	/* KIP指标库 */
	@RequestMapping(value = "/hrp/prm/queryPrmKpiLibDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmKpiLibDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptNature = hrpPrmSelectService.queryPrmKpiLibDict(mapVo);

		return deptNature;
	}

	/* KPI指标性质字典表 */
	@RequestMapping(value = "/hrp/prm/queryPrmKpiNatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmKpiNatureDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptNature = hrpPrmSelectService.queryPrmKpiNatureDict(mapVo);

		return deptNature;
	}

	/* 科室平台对应性质 */
	@RequestMapping(value = "/hrp/prm/queryPrmDeptRefDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmDeptRefDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptRefDict = hrpPrmSelectService.queryPrmDeptRefDict(mapVo);

		return deptRefDict;
	}

	/* 取值方法 */
	@RequestMapping(value = "/hrp/prm/queryPrmTargetMethodPara", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmTargetMethodPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmTargetMethodPara = hrpPrmSelectService.queryPrmTargetMethodPara(mapVo);

		return prmTargetMethodPara;
	}

	/* 指标性质 */
	@RequestMapping(value = "/hrp/prm/queryPrmTargetNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmTargetNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmTargetNature = hrpPrmSelectService.queryPrmTargetNature(mapVo);

		return prmTargetNature;
	}

	/* 绩效指标字典 */
	@RequestMapping(value = "/hrp/prm/quertPrmTargetDict", method = RequestMethod.POST)
	@ResponseBody
	public String quertPrmTargetDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptRefDict = hrpPrmSelectService.quertPrmTargetDict(mapVo);

		return deptRefDict;
	}

	/* 绩效指标性质 */
	@RequestMapping(value = "/hrp/prm/quertPrmTargetNatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String quertPrmTargetNatureDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptRefDict = hrpPrmSelectService.quertPrmTargetNatureDict(mapVo);

		return deptRefDict;
	}

	/**
	 * 指示灯编码
	 */
	@RequestMapping(value = "/hrp/prm/quertPrmLedDict", method = RequestMethod.POST)
	@ResponseBody
	public String quertPrmLedDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptRefDict = hrpPrmSelectService.quertPrmLedDict(mapVo);

		return deptRefDict;
	}

	/**
	 * 函数分类
	 */
	@RequestMapping(value = "/hrp/prm/queryPrmFunType", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmFunType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String funType = hrpPrmSelectService.queryPrmFunType(mapVo);

		return funType;
	}

	/**
	 * 系统医院
	 */
	@RequestMapping(value = "/hrp/prm/quertSysHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String quertSysHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String sysHosInfo = hrpPrmSelectService.quertSysHosInfoDict(mapVo);

		return sysHosInfo;
	}

	/**
	 * 目标管理
	 */
	@RequestMapping(value = "/hrp/prm/quertPrmGoalDict", method = RequestMethod.POST)
	@ResponseBody
	public String quertPrmGoalDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		mapVo.put("mod_code", SessionManager.getModCode());
		
		//2017-04-08 增加数据权限
		mapVo.put("prem_data", 1);
		String prmGoalDict = hrpPrmSelectService.quertPrmGoalDict(mapVo);

		return prmGoalDict;
	}

	/**
	 * 计算公式
	 */
	@RequestMapping(value = "/hrp/prm/queryPrmFormula", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmFormula = hrpPrmSelectService.queryPrmFormula(mapVo);

		return prmFormula;
	}

	/**
	 * 绩效函数参数取值
	 */
	@RequestMapping(value = "/hrp/prm/queryPrmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmFunParaMethod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmFunParaMethod = hrpPrmSelectService.queryPrmFunParaMethod(mapVo);

		return prmFunParaMethod;
	}

	/**
	 * 计量单位
	 */
	@RequestMapping(value = "/hrp/prm/quertHosUnitDict", method = RequestMethod.POST)
	@ResponseBody
	public String quertHosUnitDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmFunParaMethod = hrpPrmSelectService.quertHosUnitDict(mapVo);

		return prmFunParaMethod;
	}

	/**
	 * 人员变更表
	 */
	@RequestMapping(value = "/hrp/prm/quertPrmEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String quertPrmEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmFunParaMethod = hrpPrmSelectService.quertPrmEmpDict(mapVo);

		return prmFunParaMethod;
	}

	/**
	 * 表名:PRM_GRADE_PARA 解释:0204 指标评分方法参数表 
	 */
	@RequestMapping(value = "/hrp/prm/queryPrmGradePara", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmGradePara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String prmGradePara = hrpPrmSelectService.queryPrmGradePara(mapVo);

		return prmGradePara;
	}

			
			/**
			 * 职务字典
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmEmpDuty", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmEmpDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmpDuty = hrpPrmSelectService.queryPrmEmpDuty(mapVo);

				return prmEmpDuty;
			}
			
			
			/**
			 * 职工字典
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmEmp", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmEmp(mapVo);

				return prmEmp;
			}
			
			/**
			 * 职工变动表
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmEmpDict", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
				if (mapVo.get("group_id") == null) {
					
					mapVo.put("group_id", SessionManager.getGroupId());
					
				}
				if (mapVo.get("hos_id") == null) {
					
					mapVo.put("hos_id", SessionManager.getHosId());
					
				}
				if (mapVo.get("copy_code") == null) {
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
					
				}
				
				mapVo.put("is_stop", "0");
				
				String prmEmpDict = hrpPrmSelectService.queryPrmEmpDict(mapVo);

				return prmEmpDict;
			}
			
			@RequestMapping(value = "/hrp/prm/queryPrmDeptHip1", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmDeptHip1(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmDeptHip = hrpPrmSelectService.queryPrmDeptHip1(mapVo);

				return prmDeptHip;
			}

			@RequestMapping(value = "/hrp/prm/queryPrmDeptHipName", method = RequestMethod.POST)
			@ResponseBody
			public String queryAphiDeptHip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String aphiDeptHip = hrpPrmSelectService.queryPrmDeptHipName(mapVo);

				return aphiDeptHip;
			}
			
			/**
			 * 院级KPI指标编码
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmHosKpi", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmHosKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmHosKpi(mapVo);

				return prmEmp;
			}
			
			
			/**
			 * 科室KPI指标编码
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmDeptKpi", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmDeptKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmDeptKpi(mapVo);

				return prmEmp;
			}
			
			
			/**
			 * 职工KPI指标编码
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmEmpKpi", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmEmpKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmEmpKpi(mapVo);

				return prmEmp;
			}
	
			/**
			 * 医院KPI指标上级编码
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmHosKpiSuperKpiCode", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmHosKpiSuperKpiCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmHosKpiSuperKpiCode(mapVo);

				return prmEmp;
			}
			
			@RequestMapping(value = "/hrp/prm/queryHosEmpDict", method = RequestMethod.POST)
			@ResponseBody
			public String queryHosEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				String prmEmp = hrpPrmSelectService.queryHosEmpDict(mapVo);

				return prmEmp;
			}
			
			/**
			 * 科室KPI指标上级编码
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmDeptKpiSuperKpiCode", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmDeptKpiSuperKpiCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmDeptKpiSuperKpiCode(mapVo);

				return prmEmp;
			}
			
			/**
			 * 医院KPI指标上级编码
			 */
			@RequestMapping(value = "/hrp/prm/queryPrmEmpKpiSuperKpiCode", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmEmpKpiSuperKpiCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmEmpKpiSuperKpiCode(mapVo);

				return prmEmp;
			}
			
			@RequestMapping(value = "/hrp/prm/quertPrmComType", method = RequestMethod.POST)
			@ResponseBody
			public String quertPrmComType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.quertPrmComType(mapVo);

				return prmEmp;
			}
			
			@RequestMapping(value = "/hrp/prm/queryPrmOraclePkg", method = RequestMethod.POST)
			@ResponseBody
			public String queryPrmOraclePkg(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				String prmEmp = hrpPrmSelectService.queryPrmOraclePkg(mapVo);

				return prmEmp;
			}
			
			
}


