/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.dao.SupMapper;
import com.chd.hrp.sys.entity.Sup;
import com.chd.hrp.sys.entity.SupType;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupTypeServiceImpl;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SupController extends BaseController {
	
	private static Logger logger = Logger.getLogger(SupController.class);
	
	@Resource(name = "supService")
	private final SupServiceImpl supService = null;
	
	@Resource(name = "supMapper")
	private final SupMapper supMapper = null;
	
	@Resource(name = "supDictService")
	private final SupDictServiceImpl supDictService = null;
	
	@Resource(name = "supTypeService")
	private final SupTypeServiceImpl supTypeService = null;
	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/sup/supMainPage", method = RequestMethod.GET)
	public String supMainPage(Model mode) throws Exception {
		
		return "hrp/sys/sup/supMain";
		
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/sys/sup/supAddPage", method = RequestMethod.GET)
	public String supAddPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mode.addAttribute("is_auto", sysBaseService.getHosRulesList(mapVo).get("HOS_SUP").get("is_auto"));
		
		return "hrp/sys/sup/supAdd";
		
	}
	
	// 保存
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrp/sys/sup/addSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String supJson = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		/*if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}*/
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		//获取编码规则
		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(mapVo);
		
		//供应商编码自动生成是否使用供应商类别做为前缀
		String p98008 = MyConfig.getSysPara(SessionManager.getGroupId(), "0", "0", "98008");
		
		if (null != mv) {
			if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
				if (p98008.equals("1")) {
					supJson = supService.addSupType(mapVo);
				} else {
					// 台州单独自动生成编码规则
					supJson = supService.addSupNotType(mapVo);
					// supJson = supService.addSupTz(mapVo);
				}
				
			} else {
				supJson = supService.addSup(mapVo);
			}
		} else {
			supJson = supService.addSup(mapVo);
		}
		
		return JSONObject.parseObject(supJson);
		
	}
	
	// 查询系统平台
	@RequestMapping(value = "/hrp/sys/sup/querySup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		// 不同系统过滤筛选出相应系统的供应商
		String modCode = SessionManager.getModCode();// 获取系统编码
		// 物流管理系统modCode=04
		// 耐用品管理modCode=0413
		if (modCode != null && modCode.startsWith("04")) {
			mapVo.put("is_mat", "1");
		}
		// 固定资产管理系统modCode=05
		if (modCode != null && modCode.startsWith("05")) {
			mapVo.put("is_ass", "1");
		}
		// 药品管理系统modCode=08
		if (modCode != null && modCode.startsWith("08")) {
			mapVo.put("is_med", "1");
		}
		
		String sup = supService.querySup(getPage(mapVo));
		return JSONObject.parseObject(sup);
	}
	
	// 查询固定资产
	@RequestMapping(value = "/hrp/sys/sup/querySupAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupAss(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("is_ass", "1");
		
		String sup = supService.querySup(getPage(mapVo));
		
		return JSONObject.parseObject(sup);
		
	}
	
	// 查询物流
	@RequestMapping(value = "/hrp/sys/sup/querySupMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("is_mat", "1");
		
		String sup = supService.querySup(getPage(mapVo));
		
		return JSONObject.parseObject(sup);
		
	}
	
	// 查询药品
	@RequestMapping(value = "/hrp/sys/sup/querySupMed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupMed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("is_med", "1");
		
		String sup = supService.querySup(getPage(mapVo));
		
		return JSONObject.parseObject(sup);
		
	}
	
	// 删除
	@RequestMapping(value = "/hrp/sys/sup/deleteSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSup(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		String str = "";
		boolean falg = true;
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("sup_id", id.split("@")[2]);
			mapVo.put("sup_code", id.split("@")[3]);
			
			str = str + sysBaseService.isExistsDataByTable("HOS_SUP", id.split("@")[2]) == null ? "" : sysBaseService.isExistsDataByTable("HOS_SUP",
					id.split("@")[2]);
			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			
			listVo.add(mapVo);
		}
		if (!falg) {
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的供应商被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}");
		}
		String supJson = supService.deleteBatchSup(listVo);
		return JSONObject.parseObject(supJson);
		
	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/sup/supUpdatePage", method = RequestMethod.GET)
	public String supUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Sup sup = new Sup();
		sup = supService.querySupByCode(mapVo);
		mode.addAttribute("group_id", sup.getGroup_id());
		mode.addAttribute("hos_id", sup.getHos_id());
		mode.addAttribute("sup_id", sup.getSup_id());
		mode.addAttribute("sup_code", sup.getSup_code());
		mode.addAttribute("hos_hos_id", sup.getHos_hos_id());
		mode.addAttribute("type_code", sup.getType_code());
		mode.addAttribute("type_name", sup.getType_name());
		mode.addAttribute("sup_name", sup.getSup_name());
		mode.addAttribute("sort_code", sup.getSort_code());
		mode.addAttribute("spell_code", sup.getSpell_code());
		mode.addAttribute("wbx_code", sup.getWbx_code());
		mode.addAttribute("is_stop", sup.getIs_stop());
		mode.addAttribute("is_disable", sup.getIs_disable());
		mode.addAttribute("note", sup.getNote());
		mode.addAttribute("is_mat", sup.getIs_mat());
		mode.addAttribute("is_ass", sup.getIs_ass());
		mode.addAttribute("is_med", sup.getIs_med());
		mode.addAttribute("is_sup", sup.getIs_sup());
		mode.addAttribute("is_delivery", sup.getIs_delivery());
		
		return "hrp/sys/sup/supUpdate";
	}
	
	// 修改保存
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrp/sys/sup/updateSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(mapVo);
		
		String supJson = "";
		if (null != mv) {
			if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				supJson = supService.updateSupTz(mapVo);
			} else {
				supJson = supService.updateSup(mapVo);
			}
		} else {
			supJson = supService.updateSup(mapVo);
		}
		
		return JSONObject.parseObject(supJson);
	}
	
	@RequestMapping(value = "/hrp/sys/sup/supImportPage", method = RequestMethod.GET)
	public String supImportPage(Model mode) throws Exception {
		
		return "hrp/sys/sup/supImport";
		
	}
	
	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;
	
	// 导入
	@RequestMapping(value = "/hrp/sys/sup/supImport", method = RequestMethod.POST)
	public String supImport(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		
		// 根据系统参数判断 供应商编码是否个性化定义 1表示个性化 0表示 按默认规则生成
		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(map);
		
		List<Sup> list_err = new ArrayList<Sup>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Sup costDeptParaDict = new Sup();
				
				String temp[] = list.get(i);// 行
				
				if (mapVo.get("group_id") == null) {
					
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				
				if (StringUtils.isNotEmpty(temp[0])) {
					
					costDeptParaDict.setType_code(temp[0]);
					
					costDeptParaDict.setType_name(temp[1]);
					
					mapVo.put("type_code", temp[0]);
					
				} else {
					
					err_sb.append("供应商类别为空  ");
					
				}
				
				// if (ExcelReader.validExclColunm(temp, 2)) {
				//
				// Map<String, Object> supMapVo = new HashMap<String, Object>();
				//
				// supMapVo.put("group_id", SessionManager.getGroupId());
				//
				// supMapVo.put("hos_id", SessionManager.getHosId());
				//
				// supMapVo.put("copy_code", SessionManager.getCopyCode());
				//
				// supMapVo.put("sup_name", temp[2]);
				//
				// List<Sup> list1 = supMapper.querySupById(supMapVo);
				//
				// if (list1.size() > 0) {
				//
				// for (Sup sup : list1) {
				//
				// if (sup.getSup_name().equals(temp[2])) {
				//
				// err_sb.append("供应商名称重复!");
				// }
				//
				// }
				//
				// }
				//
				// costDeptParaDict.setSup_name(temp[2]);
				//
				// mapVo.put("sup_name", temp[2]);
				//
				// } else {
				//
				// err_sb.append("供应商名称为空  ");
				//
				// }
				if (StringUtils.isNotEmpty(temp[2])) {
					
					costDeptParaDict.setSup_code(temp[2]);
					
					mapVo.put("sup_code", temp[2]);
					
				} else {
					
					err_sb.append("供应商编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {
					
					costDeptParaDict.setSup_name(temp[3]);
					
					mapVo.put("sup_name", temp[3]);
					
				} else {
					err_sb.append("供应商名称为空  ");
				}
				
				mapVo.put("sort_code", "系统生成");
				
				if (ExcelReader.validExclColunm(temp, 4)) {
					
					costDeptParaDict.setIs_mat(Integer.parseInt(temp[4]));
					
					mapVo.put("is_mat", temp[4]);
					
				} else {
					
					err_sb.append("物流管理为空  ");
					
				}
				if (ExcelReader.validExclColunm(temp, 5)) {
					
					costDeptParaDict.setIs_ass(Integer.parseInt(temp[5]));
					
					mapVo.put("is_ass", temp[5]);
					
				} else {
					
					err_sb.append("固定资产为空  ");
					
				}
				if (ExcelReader.validExclColunm(temp, 6)) {
					
					costDeptParaDict.setIs_med(Integer.parseInt(temp[6]));
					
					mapVo.put("is_med", temp[6]);
					
				} else {
					
					err_sb.append("药品管理为空  ");
					
				}
				if (ExcelReader.validExclColunm(temp, 7)) {
					
					costDeptParaDict.setIs_sup(Integer.parseInt(temp[7]));
					
					mapVo.put("is_sup", temp[7]);
					
				} else {
					
					err_sb.append("供应商平台为空  ");
					
				}
				
				if (ExcelReader.validExclColunm(temp, 8)) {
					
					costDeptParaDict.setIs_stop(Integer.parseInt(temp[8]));
					
					mapVo.put("is_stop", temp[8]);
					
				} else {
					
					err_sb.append("是否停用为空  ");
					
				}
				
				if (ExcelReader.validExclColunm(temp, 9)) {
					
					costDeptParaDict.setNote(temp[9]);
					
					mapVo.put("note", temp[9]);
					
				} else {
					
					costDeptParaDict.setNote("");
					
					mapVo.put("note", "");
				}
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("type_code", mapVo.get("type_code"));
				
				SupType suptype = supTypeService.querySupTypeByCode(byCodeMap);
				
				if (suptype == null) {
					
					err_sb.append("类别编码不存在  ");
					
				}
				
				if (err_sb.toString().length() > 0) {
					
					costDeptParaDict.setError_type(err_sb.toString());
					
					list_err.add(costDeptParaDict);
					
				} else {
					
					String p98008 = MyConfig.getSysPara(SessionManager.getGroupId(), "0", "0", "98008");
					
					if (null != mv) {
						if ("1".equals(mv.get("HOS_SUP").get("is_auto").toString())) {
							if (p98008.equals("1")) {
								 supService.addSupType(mapVo);
							} else {
								// 台州单独自动生成编码规则
								 supService.addSupNotType(mapVo);
								// supJson = supService.addSupTz(mapVo);
							}
						} else {
							supService.addSup(mapVo);
						}
					} else {
						supService.addSup(mapVo);
					}
					
				}
			}
		} catch (DataAccessException e) {
			
			Sup data_exc = new Sup();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if (list_err.size() <= 0) {
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
	}
	
	@RequestMapping(value = "/hrp/sys/sup/supChangePage", method = RequestMethod.GET)
	public String supChangePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Sup sup = new Sup();
		sup = supService.querySupByCode(mapVo);
		mode.addAttribute("group_id", sup.getGroup_id());
		mode.addAttribute("hos_id", sup.getHos_id());
		mode.addAttribute("sup_id", sup.getSup_id());
		mode.addAttribute("sup_code", sup.getSup_code());
		mode.addAttribute("hos_hos_id", sup.getHos_hos_id());
		mode.addAttribute("type_code", sup.getType_code());
		mode.addAttribute("type_name", sup.getType_name());
		mode.addAttribute("sup_name", sup.getSup_name());
		mode.addAttribute("sort_code", sup.getSort_code());
		mode.addAttribute("spell_code", sup.getSpell_code());
		mode.addAttribute("wbx_code", sup.getWbx_code());
		mode.addAttribute("is_stop", sup.getIs_stop());
		mode.addAttribute("note", sup.getNote());
		mode.addAttribute("is_delivery", sup.getIs_delivery());
		
		return "hrp/sys/sup/supChange";
	}
	
	@RequestMapping(value = "/hrp/sys/sup/addSupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projJson = supDictService.addSupDict(mapVo);
		
		return JSONObject.parseObject(projJson);
		
	}
	
	@RequestMapping(value = "/hrp/sys/sup/addImportSupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportSupDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		
		// 根据系统参数判断 供应商编码是否个性化定义 1表示个性化 0表示 按默认规则生成
		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(map);
		
		List<Sup> list_err = new ArrayList<Sup>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				StringBuffer err_sb = new StringBuffer();
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				if (mapVo.get("group_id") == null) {
					
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				
				mapVo.put("sup_name", jsonObj.get("sup_name"));
				
				mapVo.put("type_code", jsonObj.get("type_code"));
				
				mapVo.put("type_name", jsonObj.get("type_name"));
				
				mapVo.put("is_stop", jsonObj.get("is_stop"));
				
				mapVo.put("note", jsonObj.get("note"));
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("type_code", mapVo.get("type_code"));
				
				SupType suptype = supTypeService.querySupTypeByCode(byCodeMap);
				
				if (suptype == null) {
					
					err_sb.append("供应商类別编码不存在  ");
					
				}
				
				Map<String, Object> supMapVo = new HashMap<String, Object>();
				
				supMapVo.put("group_id", SessionManager.getGroupId());
				
				supMapVo.put("hos_id", SessionManager.getHosId());
				
				supMapVo.put("copy_code", SessionManager.getCopyCode());
				
				supMapVo.put("sup_name", mapVo.get("sup_name").toString());
				
				List<Sup> list1 = supMapper.querySupById(supMapVo);
				
				if (list1.size() > 0) {
					
					for (Sup sup : list1) {
						
						if (sup.getSup_name().equals(supMapVo.get("sup_name").toString())) {
							
							err_sb.append("供应商名称重复!");
						}
						
					}
					
				}
				Sup costDeptParaDict = new Sup();
				
				if (err_sb.toString().length() > 0) {
					
					costDeptParaDict.setSup_name(mapVo.get("sup_name").toString());
					
					costDeptParaDict.setType_code(mapVo.get("type_code").toString());
					
					costDeptParaDict.setType_name(mapVo.get("type_name").toString());
					
					costDeptParaDict.setIs_stop(Integer.parseInt(mapVo.get("is_stop").toString()));
					
					costDeptParaDict.setNote(mapVo.get("note").toString());
					
					costDeptParaDict.setError_type(err_sb.toString());
					
					list_err.add(costDeptParaDict);
					
				} else {
					
					mapVo.put("sort_code", "系统生成");
					
					String p98008 = MyConfig.getSysPara(SessionManager.getGroupId(), "0", "0", "98008");
					
					if (null != mv) {
						if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
							if (p98008.equals("1")) {
								supService.addSupType(mapVo);
							} else {
								// 台州单独自动生成编码规则
							   supService.addSupNotType(mapVo);
								// supJson = supService.addSupTz(mapVo);
							}
						} else {
							supService.addSup(mapVo);
						}
					} else {
						supService.addSup(mapVo);
					}
				}
			}
			
		} catch (DataAccessException e) {
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));
			
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
	}
	
	@RequestMapping(value = "hrp/sys/sup/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		
		printTemplate(request, response, "sys\\医院信息", "供应商维护.xls");
		
		return null;
	}
	
	// 集团继承供应商
	@RequestMapping(value = "/hrp/sys/sup/addGroupSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSynGroupSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		String hos_id = SessionManager.getHosId();
		if (hos_id.equals("0")) {
			mapVo.put("hos_id", "0");
		} else {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String accSubjJson = supService.addGroupSup(mapVo);
		
		return JSONObject.parseObject(accSubjJson);
		
	}
	
}
