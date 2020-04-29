package com.chd.hrp.pac.controller.basicset.common;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.basicset.common.PactSelectService;

/**
 * 付款合同下拉框
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/select")
public class PactSelectController extends BaseController {

	@Resource(name = "pactSelectService")
	private PactSelectService pactSelectService;

	/**
	 * 查询币种下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAccCurDictSelect", method = RequestMethod.POST)
	public String queryAccCurDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryAccCurSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询供应商下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryHosSupDictSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDictSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("is_stop", 0);
            mapVo.put("mode_code",SessionManager.getModCode());
            
            //System.out.println(mapVo);
            
			return pactSelectService.queryHosSupSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询职工下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryHosEmpDictSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("is_stop", 0);

			return pactSelectService.queryHosEmpSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询合同类型下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryPactTypeFKHTSelect")
	@ResponseBody
	public String queryPactTypeFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
            mapVo.put("mode_code", SessionManager.getModCode());
			return pactSelectService.queryPactTypeSelect(mapVo, "FKHT");
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询合同类型下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryPactTypeFKXYSelect")
	@ResponseBody
	public String queryPactTypeFKXYSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			 mapVo.put("mode_code", SessionManager.getModCode());
			return pactSelectService.queryPactTypeSelect(mapVo, "FKXY");
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询合同类型下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryPactTypeSKXYSelect")
	@ResponseBody
	public String queryPactTypeSKXYSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());

			return pactSelectService.queryPactTypeSelect(mapVo, "SKXY");
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询合同类型下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryPactTypeSKHTSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryPactTypeSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());

			return pactSelectService.queryPactTypeSelect(mapVo, "SKHT");
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询付款合同下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFKHTSelect", method = RequestMethod.POST)
	public String queryPactFKHTSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryPactMainFKHTSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 付款合同下拉框  有付款合同类型 权限  状态为 签订后  履行 12  状态   签订后合同变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFKHTSelectPerm", method = RequestMethod.POST)
	public String queryPactFKHTSelectPerm(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());

			return pactSelectService.queryPactFKHTSelectPerm(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 付款协议下拉框  有付款协议类型 权限  状态为 签订后  履行 12  状态   签订后协议变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFKXYSelectPerm", method = RequestMethod.POST)
	public String queryPactFKXYSelectPerm(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
           
			return pactSelectService.queryPactFKXYSelectPerm(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 收款协议下拉框  有收款协议类型 权限  状态为 签订后  履行 12  状态   签订后协议变动 页面用 修改时注意 权限和状态
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactSKXYSelectPerm", method = RequestMethod.POST)
	public String queryPactSKXYSelectPerm(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());

			return pactSelectService.queryPactSKXYSelectPerm(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询付款合同下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactSKHTSelect", method = RequestMethod.POST)
	public String queryPactSKHTSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryPactMainSKHTSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询付款协议下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactFKXYSelect", method = RequestMethod.POST)
	public String queryPactFKXYSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryPactFKXYSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询收款协议下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactSKXYSelect", method = RequestMethod.POST)
	public String queryPactSKXYSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryPactSKXYSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询项目下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryHosProjDictSelect")
	@ResponseBody
	public String queryHosProjDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_stop", 0);
			mapVo.put("is_disable", 0);
			return pactSelectService.queryHosProjDictSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 物资下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/querySubjectSelect")
	@ResponseBody
	public String queryPactMatInvDictSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			if (mapVo.get("without_id") != null) {
				String without_id = mapVo.get("without_id").toString();
				if (without_id.length() > 0) {
					without_id = without_id.substring(0, without_id.length() - 1);
					mapVo.put("without_id", without_id);
				}
			}

			String type = (String) mapVo.get("type");
			switch (type) {
			case "01":
				return pactSelectService.queryPactAssNoDictSelect(mapVo);
			case "02":
				return "{\"error\":\"暂不支持此类型\"}";
			case "03":
				return pactSelectService.queryPactMatInvDictSelect(mapVo);
			case "04":
				return pactSelectService.queryPactMedInvDictSelect(mapVo);
			case "05":
				return pactSelectService.queryPactElseSubDictSelect(mapVo);
			default:
				return "{\"error\":\"无效类型\"}";
			}
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询资金来源下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryHosSourceDictSelect")
	@ResponseBody
	public String queryHosSourceDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_stop", 0);

			return pactSelectService.queryHosSourceDictSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询支付方式
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPayTypeDictSelect", method = RequestMethod.POST)
	public String queryPayTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryPayTypeDict(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询银行信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactBankDictSelect", method = RequestMethod.POST)
	public String queryPactBankSelectDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryPactBankSelectDict(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPayTypeDictBySourceSelect", method = RequestMethod.POST)
	public String queryPayTypeDictBySource(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			List<Map<String, Object>> list = pactSelectService.queryPayTypeDictBySource(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAssTypeDictSelect", method = RequestMethod.POST)
	public String queryAssTypeSelectDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryAssTypeSelectDict(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询部门
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryDeptSelect")
	public String queryDept(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());

			return pactSelectService.queryDeptSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询付款合同性质
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryTypeFKHTNatureSelect")
	@ResponseBody
	public String queryFKHTNature(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactSelectService.querySelcetFKHTNature(mapVo);
			return query;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询文档类型下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryPactDocTypeSelect")
	@ResponseBody
	public String queryPactDocTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return pactSelectService.queryPactDocTypeSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询合同状态下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryPactStateSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryPactStateSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_stop", "0");

			return pactSelectService.queryPactStateSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询合同支付下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryPactPayCondSelect")
	@ResponseBody
	public String queryPactPayCond(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			return pactSelectService.queryPactPayCondSelect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询字典信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryDictSelect")
	@ResponseBody
	public String queryDictSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			String query = pactSelectService.queryDictSelect(mapVo);
			return query;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询收款合同性质
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryTypeSKHTNatureSelect")
	@ResponseBody
	public String queryTypeSKHTNatureSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactSelectService.queryTypeSKHTNatureSelect(mapVo);
			return query;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询客户列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryHosCusDictSelect")
	@ResponseBody
	public String queryHosCusDictSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> list = pactSelectService.queryHosCusDictSelect(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * 查询客户列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryAssTypeSelect")
	@ResponseBody
	public String queryAssTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactSelectService.queryAssTypeSelect(mapVo);
			return query;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 生产厂商 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryHosFacDict")
	@ResponseBody
	public String queryHosFacDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String query = pactSelectService.queryHosFacDict(mapVo);
			return query;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 定标信息 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryAssTendInfo")
	@ResponseBody
	public String queryAssTendInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactSelectService.queryAssTendInfo(mapVo);
			return query;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	/**
	 * 部门下拉 带变更id
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryDeptSelectDict")
	public String queryDeptSelectDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());

			return pactSelectService.queryDeptSelectDict(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 查询供应商下拉框 带变更id
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryHosSupSelectDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("is_stop", 0);
            mapVo.putIfAbsent("mode_code", SessionManager.getModCode());
			return pactSelectService.queryHosSupSelectDict(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 查询项目下拉框  带变更id
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryHosProjSelectDict")
	@ResponseBody
	public String queryHosProjSelectDict(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_stop", 0);
			mapVo.put("is_disable", 0);
			return pactSelectService.queryHosProjSelectDict(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	
	
	/**
	 * 合同模板配置方案 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping("/queryPactTemplate")
	@ResponseBody
	public String queryPactTemplate(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return pactSelectService.queryPactTemplate(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

/**
 * 按钮函数数据源下拉框
 * @author lh0225
 */
@RequestMapping("/queryBtenDictSelect")
@ResponseBody
public String queryBtenDictSelect(@RequestParam Map<String ,Object> mapVo, Model mode){
	try{
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		return pactSelectService.queryBtenDictSelect(mapVo);
	}catch (Exception e){
		return "{\"error\":\"" + e.getMessage() + "\"}";
		
	}
	
}
/**
 * wjt0316
 * 物流仓库(全部)
 * @param mapVo
 * @param mode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/queryMatStoreAll", method = RequestMethod.POST)
@ResponseBody
public String queryMatStoreAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
	}
	if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
	}
	if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
	}
	
	String hrpMatSelect = pactSelectService.queryMatStoreAll(mapVo);
	return hrpMatSelect;
}

/**
 * wjt0316
 * 物资类别下拉查询
 * @param mapVo
 * @param mode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/queryMatType", method = RequestMethod.POST)
@ResponseBody
public String queryMatType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
	}
	if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
	}
	if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
	}
	String hrpMatSelect = pactSelectService.queryMatType(mapVo);
	return hrpMatSelect;
}

/**
 * 计量单位 下拉框
 * @param mapVo
 * @param mode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/queryHosUnitSelect", method = RequestMethod.POST)
@ResponseBody
public String queryHosUnitSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
	}
	if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
	}
	if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
	}
	String hrpMatSelect = pactSelectService.queryHosUnit(mapVo);
	return hrpMatSelect;
}

/**
 * 计量单位 下拉框
 * @param mapVo
 * @param mode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/queryDeptNameAndId", method = RequestMethod.POST)
@ResponseBody
public String queryDeptNameAndId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
	}
	if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
	}
	if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
	}
	String hrpMatSelect = pactSelectService.queryDeptNameAndId(mapVo);
	return hrpMatSelect;
}

}
