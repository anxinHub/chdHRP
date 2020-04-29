package com.chd.hrp.acc.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccNewOldJoinMapper;
import com.chd.hrp.acc.dao.AccSubjNatureMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccCheckTypeMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjTypeMapper;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.entity.TmpAccLedger;
import com.chd.hrp.acc.entity.TmpAccSubj;
import com.chd.hrp.acc.service.AccNewOldJoinService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 新旧制度衔接
 * 
 * @author yang
 *
 */
@Service("accNewOldJoinService")
public class AccNewOldJoinServiceImpl implements AccNewOldJoinService {

	private static Logger logger = Logger.getLogger(AccNewOldJoinServiceImpl.class);

	@Resource(name = "accNewOldJoinMapper")
	private final AccNewOldJoinMapper accNewOldJoinMapper = null;
	
	@Resource(name = "accSubjTypeMapper")
	private final AccSubjTypeMapper accSubjTypeMapper = null;
	
	@Resource(name = "accSubjNatureMapper")
	private final AccSubjNatureMapper accSubjNatureMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "accCheckTypeMapper")
	private final AccCheckTypeMapper accCheckTypeMapper = null;

	// 查旧科目
	@Override
	public String queryAccSubjOld(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<TmpAccSubj> list = accNewOldJoinMapper.queryAccSubjOld(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<TmpAccSubj> list = accNewOldJoinMapper.queryAccSubjOld(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	// 查旧余额
	@Override
	public String queryAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accNewOldJoinMapper.queryAccLedgerOld(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accNewOldJoinMapper.queryAccLedgerOld(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	// 查新科目
	@Override
	public String queryAccSubjNew(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<TmpAccSubj> list = accNewOldJoinMapper.queryAccSubjNew(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<TmpAccSubj> list = accNewOldJoinMapper.queryAccSubjNew(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	// 查新余额
	@Override
	public String queryAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accNewOldJoinMapper.queryAccLedgerNew(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accNewOldJoinMapper.queryAccLedgerNew(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	// 查新旧科目对应 关系表
	@Override
	public String queryAccSubjMap(Map<String, Object> paramMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accNewOldJoinMapper.queryAccSubjMap(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accNewOldJoinMapper.queryAccSubjMap(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	// 删除新科目
	@Override
	public String deleteAccSubjNew(Map<String, Object> paramMap) throws DataAccessException {
		try {
			List<TmpAccSubj> listVo = JSONArray
				.parseArray(String.valueOf(paramMap.get("paramVo")), TmpAccSubj.class);
			if(listVo.size() > 0){
				String msg = checkSubjListIsLast(listVo);
				if(msg != null){
					return msg;
				}
				
				accNewOldJoinMapper.deleteAccSubjNewByCodeBatch(JsonListMapUtil.beanToListMap(listVo));
				
				// 取上级科目编号
				Set<String> superSubjCodes = new HashSet<String>();
				for(TmpAccSubj subj : listVo){
					if(subj.getSubj_code().length() > 4){
						superSubjCodes.add(subj.getSubj_code().substring(0, subj.getSubj_code().length() - 2));
					}
				}
				
				// 更新上级科目是否末级
				if(superSubjCodes.size() > 0){
					List<Map<String, Object>> subjList = accNewOldJoinMapper.queryExistsLastForNew(superSubjCodes);
					if(subjList.size() > 0){
						List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
						for(Map<String, Object> map : subjList){
							Map<String, Object> update = new HashMap<String, Object>();
							update.put("table_name", "tmp_acc_subj_new");
							update.put("subj_code", map.get("SUBJ_CODE"));
							if(Integer.valueOf(map.get("COUNT").toString()) == 0){
								update.put("is_last", "是");
							}else{
								update.put("is_last", "否");
							}
							updateList.add(update);
						}
						accNewOldJoinMapper.updateSubjIsLastBatch(updateList);
					}
				}
			}else{
				return "{\"warn\":\"请选择行！.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("删除失败.");
		}
	}
	
	// 删除旧科目
	@Override
	public String deleteAccSubjOld(Map<String, Object> paramMap) throws DataAccessException {
		try {
			List<TmpAccSubj> listVo = JSONArray
					.parseArray(String.valueOf(paramMap.get("paramVo")), TmpAccSubj.class);
			if(listVo.size() > 0){
				// 检查是否为末级科目
				String msg = checkSubjListIsLast(listVo);
				if(msg != null){
					return msg;
				}
				
				// 删除所选科目
				accNewOldJoinMapper.deleteAccSubjOldByCodeBatch(JsonListMapUtil.beanToListMap(listVo));
				
				// 取上级科目编号
				Set<String> superSubjCodes = new HashSet<String>();
				for(TmpAccSubj subj : listVo){
					if(subj.getSubj_code().length() > 4){
						superSubjCodes.add(subj.getSubj_code().substring(0, subj.getSubj_code().length() - 2));
					}
				}
				
				// 更新上级科目是否末级
				if(superSubjCodes.size() > 0){
					List<Map<String, Object>> subjList = accNewOldJoinMapper.queryExistsLastForOld(superSubjCodes);
					if(subjList.size() > 0){
						List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
						for(Map<String, Object> map : subjList){
							Map<String, Object> update = new HashMap<String, Object>();
							update.put("table_name", "tmp_acc_subj_old");
							update.put("subj_code", map.get("SUBJ_CODE"));
							if(Integer.valueOf(map.get("COUNT").toString()) == 0){
								update.put("is_last", "是");
							}else{
								update.put("is_last", "否");
							}
							updateList.add(update);
						}
						accNewOldJoinMapper.updateSubjIsLastBatch(updateList);
					}
				}
			}else{
				return "{\"warn\":\"请选择行！.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("删除失败.");
		}
	}
	
	/**
	 * 删除前检查科目是不是末级
	 */
	private String checkSubjListIsLast(List<TmpAccSubj> list){
		if(list.size() > 0){
			StringBuilder sb = new StringBuilder("以下科目不是末级：");
			boolean flag = false;
			for(TmpAccSubj subj : list){
				if("0".equals(subj.getIs_last())){
					flag = true;
					sb.append("<br/>").append(subj.getSubj_name());
				}
			}
			if(flag){
				return "{\"warn\":\"" + sb.toString() + "\",\"state\":\"true\"}";
			}
		}
		return null;
	}
	
	// 删除旧余额
	@Override
	public String deleteAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException {
		try {
			List<TmpAccLedger> listVo = JSONArray
					.parseArray(String.valueOf(paramMap.get("paramVo")), TmpAccLedger.class);
			if(listVo.size() > 0){
				accNewOldJoinMapper.deleteAccLedgerOldByCodeBatch(JsonListMapUtil.beanToListMap(listVo));
			}else{
				return "{\"warn\":\"请选择行！.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("删除失败.");
		}
	}

	// 删除新余额
	@Override
	public String deleteAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException {
		try {
			List<TmpAccLedger> listVo = JSONArray
					.parseArray(String.valueOf(paramMap.get("paramVo")), TmpAccLedger.class);
			if(listVo.size() > 0){
				accNewOldJoinMapper.deleteAccLedgerNewByCodeBatch(JsonListMapUtil.beanToListMap(listVo));
			}else{
				return "{\"warn\":\"请选择行！.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"删除成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("删除失败.");
		}
	}
	
	// 检查编码规则、并取上级次编码
	private String checkCodeRule(Map<String, Object> paramMap){
		String subjCode = paramMap.get("subj_code").toString();
		String[] ruless = paramMap.get("rules").toString().split("-");
		Map<Integer, Integer> maxNumMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> position = new HashMap<Integer, Integer>();
		int super_num = 0;
		for (int i = 0; i < ruless.length; i++) {
			int num = Integer.parseInt(ruless[i].replace(" ", ""));
			super_num += num;
			maxNumMap.put(super_num, i + 1);
			position.put(i + 1, super_num);
		}
		if (maxNumMap.containsKey(subjCode.length())) {// 编码匹配
			int subj_level = maxNumMap.get(subjCode.length());
			paramMap.put("subj_level", subj_level);
			if (subj_level != 1) {
				int super_level = subj_level - 1;// 上级级次
				int subPosition = position.get(super_level);// 上级级次位置
				String super_code = subjCode.substring(0, subPosition);// 截取上级编码
				paramMap.put("super_code", super_code);
			}
		} else {
			return "{\"error\":\"添加失败，不符合编码规则，请重新输入！\",\"state\":\"false\"}";
		}
		return null;
	}
	
	// 添加科目（旧）
	@Override
	public String addTmpAccSubjOld(Map<String, Object> paramMap) throws DataAccessException {
		try {
			String subjCode = paramMap.get("subj_code").toString();
			
			// 检查编码规则并取上级次编码
			String str = checkCodeRule(paramMap);
			if(str != null){
				return str;
			}
			
			// 检查科目编码重复
			paramMap.put("table_name", "tmp_acc_subj_old");
			TmpAccSubj subj = accNewOldJoinMapper.getTmpAccSubjByPK(paramMap);
			if(subj != null){
				return "{\"error\":\"科目编码：[" + subjCode + "]重复.\",\"state\":\"false\"}";
			}
			
			String subj_name_all = paramMap.get("subj_name").toString();
			// 检查上级编码
			if(!paramMap.get("subj_level").toString().equals("1")){
				paramMap.put("subj_code", paramMap.get("super_code"));
				TmpAccSubj superSubj = accNewOldJoinMapper.getTmpAccSubjByPK(paramMap);
				paramMap.put("subj_code", subjCode);
				if(superSubj == null){
					return "{\"error\":\"上级编码：[" + paramMap.get("super_code").toString() + "]不存在.\",\"state\":\"false\"}";
				}
				subj_name_all = superSubj.getSubj_name_all() + "-" + subj_name_all;
				// 更新上级是否末级
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("is_last", "0");
				map.put("subj_code", superSubj.getSubj_code());
				accNewOldJoinMapper.updateAccSubjOldByPK(map);
			}
			
			// 设置科目全称
			paramMap.put("subj_name_all", subj_name_all);
			
			paramMap.put("is_last", "1");
			accNewOldJoinMapper.addAccSubjOld(paramMap);
			
			accNewOldJoinMapper.addInitAccLedgerOld(paramMap);
			return "{\"msg\":\"添加成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("添加失败.");
		}
	}
	
	@Override
	public String addTmpAccSubjNew(Map<String, Object> paramMap) throws DataAccessException {
		try {
			String subjCode = paramMap.get("subj_code").toString();
			
			// 检查编码规则并取上级次编码
			String str = checkCodeRule(paramMap);
			if(str != null){
				return str;
			}
			
			// 检查科目编码重复
			paramMap.put("table_name", "tmp_acc_subj_new");
			TmpAccSubj subj = accNewOldJoinMapper.getTmpAccSubjByPK(paramMap);
			if(subj != null){
				return "{\"error\":\"科目编码：[" + subjCode + "]重复.\",\"state\":\"false\"}";
			}
			
			String subj_name_all = paramMap.get("subj_name").toString();
			// 检查上级编码
			if(!paramMap.get("subj_level").toString().equals("1")){
				paramMap.put("subj_code", paramMap.get("super_code"));
				TmpAccSubj superSubj = accNewOldJoinMapper.getTmpAccSubjByPK(paramMap);
				paramMap.put("subj_code", subjCode);
				if(superSubj == null){
					return "{\"error\":\"上级编码：[" + paramMap.get("super_code").toString() + "]不存在.\",\"state\":\"false\"}";
				}
				subj_name_all = superSubj.getSubj_name_all() + "-" + subj_name_all;
				// 更新上级是否末级
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("is_last", "0");
				map.put("subj_code", superSubj.getSubj_code());
				accNewOldJoinMapper.updateAccSubjNewByPK(map);
			}
			
			// 设置科目全称
			paramMap.put("subj_name_all", subj_name_all);
			
			// 设置拼音码、五笔码
			paramMap.put("spell_code", StringTool.toPinyinShouZiMu(paramMap.get("subj_name").toString()));
			paramMap.put("wbx_code", StringTool.toWuBi(paramMap.get("subj_name").toString()));
			
			// 保存本条数据
			paramMap.put("is_last", "1");
			accNewOldJoinMapper.addAccSubjNew(paramMap);
			
			accNewOldJoinMapper.addInitAccLedgerNew(paramMap);
			return "{\"msg\":\"添加成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("添加失败.");
		}
	}
	
	// 更新会计科目（旧）
	@Override
	public String updateTmpAccSubjOld(Map<String, Object> paramMap) throws DataAccessException {
		try{
			String subjCode = paramMap.get("subj_code").toString();
			
			// 设置科目全称
			paramMap.put("subj_name_all", paramMap.get("subj_name"));
			Map<String, Object> map = new HashMap<String, Object>();
			if(subjCode.length() > 4){
				map.put("subj_code", subjCode.substring(0, subjCode.length() - 2));
				map.put("table_name", "tmp_acc_subj_old");
				TmpAccSubj superSubj = accNewOldJoinMapper.getTmpAccSubjByPK(map);
				paramMap.put("subj_name_all", superSubj.getSubj_name_all() + "-" + paramMap.get("subj_name").toString());
			}
			paramMap.put("table_name", "tmp_acc_subj_old");
			int count = accNewOldJoinMapper.queryTmpSubjIsLast(paramMap);
			if(count > 0){
				paramMap.put("is_last", "否");
			}else{
				paramMap.put("is_last", "是");
			}
			// 更新
			accNewOldJoinMapper.updateAccSubjOldByPK(paramMap);
			
			// 设置子级科目全称
			String subjNameAll = paramMap.get("subj_name_all").toString();
			paramMap.put("table_name", "tmp_acc_subj_old");
			List<TmpAccSubj> subjList = accNewOldJoinMapper.getAllSubSubj(paramMap);
			if(subjList.size() > 0){
				List<Map<String, Object>> subjMapList = JsonListMapUtil.beanToListMap(subjList);
				for(Map<String, Object> subj : subjMapList){
					subj.put("subj_name_all", subjNameAll + "-" + subj.get("subj_name").toString());
					subj.put("table_name", "tmp_acc_subj_old");
				}
				accNewOldJoinMapper.updateSubjNameAllBatch(subjMapList);
			}
			return "{\"msg\":\"更新成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("更新失败.");
		}
	}
	
	// 更新会计科目（新）
	@Override
	public String updateTmpAccSubjNew(Map<String, Object> paramMap) throws DataAccessException {
		try{
			String subjCode = paramMap.get("subj_code").toString();
			
			// 设置科目全称
			paramMap.put("subj_name_all", paramMap.get("subj_name"));
			if(subjCode.length() > 4){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("subj_code", subjCode.substring(0, subjCode.length() - 2));
				map.put("table_name", "tmp_acc_subj_new");
				TmpAccSubj superSubj = accNewOldJoinMapper.getTmpAccSubjByPK(map);
				paramMap.put("subj_name_all", superSubj.getSubj_name_all() + "-" + paramMap.get("subj_name").toString());
			}
			paramMap.put("table_name", "tmp_acc_subj_new");
			int count = accNewOldJoinMapper.queryTmpSubjIsLast(paramMap);
			if(count > 0){
				paramMap.put("is_last", "否");
			}else{
				paramMap.put("is_last", "是");
			}
			// 更新
			accNewOldJoinMapper.updateAccSubjNewByPK(paramMap);
			
			// 设置子级科目全称
			String subjNameAll = paramMap.get("subj_name_all").toString();
			paramMap.put("table_name", "tmp_acc_subj_new");
			List<TmpAccSubj> subjList = accNewOldJoinMapper.getAllSubSubj(paramMap);
			if(subjList.size() > 0){
				List<Map<String, Object>> subjMapList = JsonListMapUtil.beanToListMap(subjList);
				for(Map<String, Object> subj : subjMapList){
					subj.put("subj_name_all", subjNameAll + "-" + subj.get("subj_name").toString());
					subj.put("table_name", "tmp_acc_subj_new");
				}
				accNewOldJoinMapper.updateSubjNameAllBatch(subjMapList);
			}
			return "{\"msg\":\"更新成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("更新失败.");
		}
	}
	
	// 从excel导入旧科目
	@Override
	public String importAccSubjOld(Map<String, Object> paramMap) throws DataAccessException {
		List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
		Map<String, Object> isDicMap = new HashMap<String, Object>(){
			{
				put("是", "是");
				put("否", "否");
				put("1", "是");
				put("0", "否");
			}
		};
		Map<String, Object> direMap = new HashMap<String, Object>(){
			{
				put("贷", "贷");
				put("借", "借");
				put("1", "贷");
				put("0", "借");
			}
		};
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		try {
			List<String> strList = new ArrayList<String>();
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paramMap);
			if(list != null && list.size() > 0){
				for(Map<String, List<String>> map : list){
					if(map.get("subj_code").get(1) == null || map.get("subj_code").get(1) == ""){
						strList.add(map.get("subj_code").get(0));
					}
					if(map.get("subj_name").get(1) == null || map.get("subj_name").get(1) == ""){
						strList.add(map.get("subj_name").get(0));
					}
					
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("subj_code", map.get("subj_code").get(1));
					saveMap.put("subj_name", map.get("subj_name").get(1));
					saveMap.put("subj_name_all", map.get("subj_name_all").get(1));
					saveMap.put("subj_type_code", map.get("subj_type_name").get(1));
					saveMap.put("subj_nature_code", map.get("subj_nature_name").get(1));
					
					saveMap.put("subj_dire", 
						direMap.get(StringUtils.isEmpty(map.get("subj_dire_name").get(1)) ? null : map.get("subj_dire_name").get(1)));
					saveMap.put("subj_level", map.get("subj_level").get(1));
					saveMap.put("is_cash", 
						isDicMap.get(StringUtils.isEmpty(map.get("is_cash_name").get(1)) ? null : map.get("is_cash_name").get(1)));
					saveMap.put("is_last", 
						isDicMap.get(StringUtils.isEmpty(map.get("is_last_name").get(1)) ? null : map.get("is_last_name").get(1)));
					
					saveMap.put("note", map.get("note").get(1));
					saveMap.put("is_check", "否");
					saveMap.put("check1", map.get("check1").get(1));
					saveMap.put("check2", map.get("check2").get(1));
					saveMap.put("check3", map.get("check3").get(1));
					saveMap.put("check4", map.get("check4").get(1));
					if(map.get("check1").get(1) != null && map.get("check1").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					if(map.get("check2").get(1) != null && map.get("check2").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					if(map.get("check3").get(1) != null && map.get("check3").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					if(map.get("check4").get(1) != null && map.get("check4").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					
					allList.add(saveMap);
				}
				
				// 必填格子检查
				String msg = mustFillCheck(strList);
				if(msg != null){
					return msg;
				}
				
				if(allList.size() > 0){
					// 检验科目编码重复
					msg = subjCodeRepeatCheck(allList);
					if(msg != null){
						return msg;
					}
					
					List<AccSubjType> typeList = accSubjTypeMapper.queryAccSubjType(paramMap);
					List<AccSubjNature> natureList = accSubjNatureMapper.queryAccSubjNature(paramMap);
					// 检验并重新填充科目类型
					msg = subjTypeCheckAndRewrite(typeList, allList);
					if(msg != null){
						return msg;
					}
					
					// 检验并重新填充科目性质
					msg = subjNatureCheckAndRewrite(natureList, allList);
					if(msg != null){
						return msg;
					}
					
					// 执行保存
					accNewOldJoinMapper.deleteAccSubjOldByCodeBatch(allList);
					accNewOldJoinMapper.addAccSubjOldBatch(allList);
					return "{\"msg\":\"已成功导入" + allList.size() + "条\",\"state\":\"true\"}";
				}
			}

			return "{\"msg\":\"没有可导入数据\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("导入失败.");
		}
	}

	/**
	 * 从excel导入新科目
	 */
	@Override
	public String importAccSubjNew(Map<String, Object> paramMap) throws DataAccessException {
		List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
		Map<String, Object> isDicMap = new HashMap<String, Object>(){
			{
				put("是", "是");
				put("否", "否");
				put("1", "是");
				put("0", "否");
			}
		};
		Map<String, Object> direMap = new HashMap<String, Object>(){
			{
				put("贷", "贷");
				put("借", "借");
				put("1", "贷");
				put("0", "借");
			}
		};
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		try {
			List<String> strList = new ArrayList<String>();
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paramMap);
			if(list != null && list.size() > 0){
				for(Map<String, List<String>> map : list){
					if(map.get("subj_code").get(1) == null || map.get("subj_code").get(1) == ""){
						strList.add(map.get("subj_code").get(0));
					}
					if(map.get("subj_name").get(1) == null || map.get("subj_name").get(1) == ""){
						strList.add(map.get("subj_name").get(0));
					}
					
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("subj_code", map.get("subj_code").get(1));
					saveMap.put("subj_name", map.get("subj_name").get(1));
					saveMap.put("subj_name_all", map.get("subj_name_all").get(1));
					saveMap.put("subj_type_code", map.get("subj_type_name").get(1));
					saveMap.put("subj_nature_code", map.get("subj_nature_name").get(1));
					
					saveMap.put("subj_dire", 
						direMap.get(StringUtils.isEmpty(map.get("subj_dire_name").get(1)) ? null : map.get("subj_dire_name").get(1)));
					saveMap.put("subj_level", map.get("subj_level").get(1));
					saveMap.put("is_cash", 
						isDicMap.get(StringUtils.isEmpty(map.get("is_cash_name").get(1)) ? null : map.get("is_cash_name").get(1)));
					saveMap.put("is_last", 
						isDicMap.get(StringUtils.isEmpty(map.get("is_last_name").get(1)) ? null : map.get("is_last_name").get(1)));
					
					saveMap.put("note", map.get("note").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(saveMap.get("subj_name").toString()));
					saveMap.put("wbx_code", StringTool.toWuBi(saveMap.get("subj_name").toString()));
					
					saveMap.put("is_check", "否");
					saveMap.put("check1", map.get("check1").get(1));
					saveMap.put("check2", map.get("check2").get(1));
					saveMap.put("check3", map.get("check3").get(1));
					saveMap.put("check4", map.get("check4").get(1));
					if(map.get("check1").get(1) != null && map.get("check1").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					if(map.get("check2").get(1) != null && map.get("check2").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					if(map.get("check3").get(1) != null && map.get("check3").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					if(map.get("check4").get(1) != null && map.get("check4").get(1) != ""){
						saveMap.put("is_check", "是");
					}
					
					allList.add(saveMap);
				}
				
				// 必填格子检查
				String msg = mustFillCheck(strList);
				if(msg != null){
					return msg;
				}
				
				if(allList.size() > 0){
					// 检验科目编码重复
					msg = subjCodeRepeatCheck(allList);
					if(msg != null){
						return msg;
					}
					
					List<AccSubjType> typeList = accSubjTypeMapper.queryAccSubjType(paramMap);
					List<AccSubjNature> natureList = accSubjNatureMapper.queryAccSubjNature(paramMap);
					// 检验并重新填充科目类型
					msg = subjTypeCheckAndRewrite(typeList, allList);
					if(msg != null){
						return msg;
					}
					
					// 检验并重新填充科目性质
					msg = subjNatureCheckAndRewrite(natureList, allList);
					if(msg != null){
						return msg;
					}
					
					// 执行保存
					accNewOldJoinMapper.deleteAccSubjNewByCodeBatch(allList);
					accNewOldJoinMapper.addAccSubjNewBatch(allList);
					return "{\"msg\":\"已成功导入" + allList.size() + "条\",\"state\":\"true\"}";
				}
			}

			return "{\"msg\":\"没有可导入数据\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("导入失败.");
		}
	}

	// 从excel导入旧余额
	@Override
	public String importAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException {

			List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();

			try {
				List<String> strList = new ArrayList<String>();
				List<Map<String, List<String>>> list = ReadFiles.readFiles(paramMap);
				if(list != null && list.size() > 0){
					for(Map<String, List<String>> map : list){
						
						Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("subj_code", map.get("subj_code").get(1));
						saveMap.put("subj_name", map.get("subj_name").get(1));
						saveMap.put("bal_od", map.get("bal_od").get(1));
						saveMap.put("bal_oc", map.get("bal_oc").get(1));
						saveMap.put("this_od", 0);
						saveMap.put("this_oc", 0);
						saveMap.put("sum_od", 0);
						saveMap.put("sum_oc", 0);
						saveMap.put("end_od", map.get("end_od").get(1));
						saveMap.put("end_oc", map.get("end_oc").get(1));
						
						allList.add(saveMap);
					}
					
					if(allList.size() > 0){
						// 执行保存
						accNewOldJoinMapper.deleteAccSubjAccLedgerOld(allList);
						int addcount = accNewOldJoinMapper.addAccSubjAccLedgerOld(allList);
						return "{\"msg\":\"已成功导入" + addcount + "条\",\"state\":\"true\"}";
					}
				}

				return "{\"msg\":\"没有可导入数据\",\"state\":\"true\"}";
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
				throw new SysException("导入失败.");
			}
	}

	/**
	 * 从excel导入新余额
	 */
	@Override
	public String importAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException {
		List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paramMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("subj_code", map.get("subj_code").get(1));
					saveMap.put("subj_name", map.get("subj_name").get(1));
					saveMap.put("bal_od", map.get("bal_od").get(1));
					saveMap.put("bal_oc", map.get("bal_oc").get(1));
					saveMap.put("this_od", 0);
					saveMap.put("this_oc", 0);
					saveMap.put("sum_od", 0);
					saveMap.put("sum_oc", 0);
					saveMap.put("end_od", map.get("end_od").get(1));
					saveMap.put("end_oc", map.get("end_oc").get(1));

					allList.add(saveMap);
				}

				if (allList.size() > 0) {
					// 执行保存
					accNewOldJoinMapper.deleteTmpAccLedgerNewBatch(allList);
					int addcount = accNewOldJoinMapper.addTmpAccLedgerNewBatch(allList);
					return "{\"msg\":\"已成功导入" + addcount + "条\",\"state\":\"true\"}";
				}
			}
			return "{\"msg\":\"没有可导入数据\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SysException("导入失败.");
		}
	}
	
	/**
	 * 检验科目编码重复，返回提示
	 */
	private String subjCodeRepeatCheck(List<Map<String, Object>> list){
		Map<String, String> scMap = new HashMap<String, String>();
		for(int i = 0; i < list.size(); i++){
			for(int j = i + 1; j < list.size(); j++){
				if(list.get(i).get("subj_code").toString()
					.equals(list.get(j).get("subj_code").toString())){
					scMap.put(list.get(i).get("subj_code").toString(), list.get(i).get("subj_code").toString());
				}
			}
		}
		// 不为空，则有重复的科目编码
		if(!scMap.isEmpty()){
			StringBuilder msg = new StringBuilder("以下科目编码重复：");
			Set<String> keySet = scMap.keySet();
			for(String code : keySet){
				msg.append("</br>").append("[" + code + "]");
			}
			return "{\"warn\":\"" + msg.toString() + "\",\"state\":\"false\"}";
		}
		return null;
	}
	
	/**
	 * 科目性质编码检验并重新写入，返回提示
	 */
	private String subjNatureCheckAndRewrite(List<AccSubjNature> natureList, List<Map<String, Object>> list) {
		Map<String, String> natureMap = new HashMap<String, String>();
		for(AccSubjNature nature : natureList){
			natureMap.put(nature.getSubj_nature_code(), nature.getSubj_nature_name());
			natureMap.put(nature.getSubj_nature_name(), nature.getSubj_nature_name());
		}
		StringBuilder msg = new StringBuilder("没有找到以下科目性质：");
		boolean flag = false;
		for(Map<String, Object> map : list){
			if(natureMap.get(map.get("subj_nature_code").toString()) == null
				&& msg.indexOf("[" + map.get("subj_nature_code").toString() + "]") < 0){
				flag = true;
				msg.append("</br>").append("[" + map.get("subj_nature_code").toString() + "]");
			}else{
				map.put("subj_nature_code", natureMap.get(map.get("subj_nature_code").toString()));
			}
		}
		if(flag){
			return "{\"warn\":\"" + msg.toString() + "\",\"state\":\"false\"}";
		}
		return null;
	}
	
	/**
	 * 科目类型检验并重新写入，返回提示
	 */
	private String subjTypeCheckAndRewrite(List<AccSubjType> typeList, List<Map<String, Object>> list) {
		Map<String, String> typeMap = new HashMap<String, String>();
		for(AccSubjType type : typeList){
			typeMap.put(type.getSubj_type_code(), type.getSubj_type_name());
			typeMap.put(type.getSubj_type_name(), type.getSubj_type_name());
		}
		StringBuilder msg = new StringBuilder("没有找到以下科目类型：");
		boolean flag = false;
		for(Map<String, Object> map : list){
			if(typeMap.get(map.get("subj_type_code").toString()) == null 
				&& msg.indexOf("[" + map.get("subj_type_code").toString() + "]") < 0){
				flag = true;
				msg.append("</br>").append("[" + map.get("subj_type_code").toString() + "]");
			}else{
				map.put("subj_type_code", typeMap.get(map.get("subj_type_code").toString()));
			}
		}
		if(flag){
			return "{\"warn\":\"" + msg.toString() + "\",\"state\":\"false\"}";
		}
		return null;
	}
	
	/**
	 * 必填检查
	 */
	private String mustFillCheck(List<String> list) {
		if(list.size() > 0){
			StringBuilder msg = new StringBuilder("以下必填格子不能为空：");
			for(String str : list){
				String[] ar = str.split(":");
				msg.append("</br>").append("第" + ar[0] + "行 " + ar[1] + "列");
			}
			return msg.toString();
		}
		return null;
	}
	
	//导入系统旧科目
	@Override
	public String importSystemAccSubjOldOldSubj(Map<String, Object> paramMap) {
		
		try {
			paramMap.put("table", "TMP_ACC_SUBJ_OLD");
			accNewOldJoinMapper.deleteimportSystemAccSubjNewSubj(paramMap);
			
			int addCount = accNewOldJoinMapper.importSystemAccSubjOldOldSubj(paramMap);
			if(addCount == 0){
				return "{\"msg\":\"原系统数据已全部导入!\"}";
			}
			return "{\"msg\":\"导入成功,共 "+addCount+" 条!\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	/**
	 * 导入系统新科目
	 * @deprecated
	 */
	@Override
	public String importSystemAccSubjNewSubj(Map<String, Object> paramMap) {
		
		try {
			
			List<TmpAccSubj> tmpList = accNewOldJoinMapper.queryimportSystemAccSubjNewSubj(paramMap);
			if (tmpList == null || tmpList.size() == 0) {
				return "{\"error\":\"没有数据可以导入!\"}";
			}
			for (TmpAccSubj tmpAccSubjNew : tmpList) {
				tmpAccSubjNew.setSpell_code(StringTool.toPinyinShouZiMu(tmpAccSubjNew.getSubj_name()));
				tmpAccSubjNew.setWbx_code(StringTool.toWuBi(tmpAccSubjNew.getSubj_name()));
			}
			paramMap.put("table", "TMP_ACC_SUBJ_NEW");
			accNewOldJoinMapper.deleteimportSystemAccSubjNewSubj(paramMap);
			
			int addCount = accNewOldJoinMapper.importSystemAccSubjNewSubj(tmpList);
			if(addCount == 0){
				return "{\"msg\":\"原系统数据已全部导入!\"}";
			}
			return "{\"msg\":\"导入成功,共 "+addCount+" 条!\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public TmpAccSubj getTmpAccSubjOld(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("table_name", "tmp_acc_subj_old");
		return accNewOldJoinMapper.getTmpAccSubjByPK(paramMap);
	}

	@Override
	public TmpAccSubj getTmpAccSubjNew(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("table_name", "tmp_acc_subj_new");
		return accNewOldJoinMapper.getTmpAccSubjByPK(paramMap);
	}

	/**
	 * 导入系统旧余额
	 */
	@Override
	public String importSystemAccSubjOldLedger(Map<String, Object> paramMap) {
		try {
			//String st = null;st.equals("2");
			paramMap.put("table", "TMP_ACC_LEDGER_OLD");
			accNewOldJoinMapper.deleteimportSystemAccSubjOld(paramMap);
			
			int addCount = accNewOldJoinMapper.importSystemAccSubjOld(paramMap);
			if(addCount == 0){
				return "{\"msg\":\"没有可导入数据\"}";
			}
			
			accNewOldJoinMapper.importLedgerCheckFromSys(paramMap);
			return "{\"msg\":\"导入成功,共 "+addCount+" 条!\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException("导入失败");
		}
	}

	@Override
	public String regenerateAccSubjOld() {
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			accNewOldJoinMapper.regenerateAccSubjOld(map);
			
			if (map.get("code").equals(0)) {
				return "{\"msg\":\"旧科目成功转为新科目！\"}";
			}else{
				return "{\"error\":\""+map.get("name").toString()+"\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}

	}

	@Override
	public TmpAccLedger getTmpAccLedgerOld(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("table_name", "tmp_acc_ledger_old");
		return accNewOldJoinMapper.getTmpAccLedgerByPK(paramMap);
	}

	@Override
	public TmpAccLedger getTmpAccLedgerNew(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("table_name", "tmp_acc_ledger_new");
		return accNewOldJoinMapper.getTmpAccLedgerByPK(paramMap);
	}

	@Override
	public String getSubjCheckTitle(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		List<Map<String, Object>> list = accNewOldJoinMapper.querySubjCheck(paramMap);
		return JSON.toJSONString(JsonListMapUtil.toListMapLower(list));
	}

	@Override
	public String queryLedgerCheckList(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> map = accNewOldJoinMapper.queryCheckItemTable(paramMap);
		
		if (map != null) {

			map.put("COLUMN_CODE1", String.valueOf(map.get("COLUMN_CODE1")).toLowerCase());
			map.put("COLUMN_CODE2", String.valueOf(map.get("COLUMN_CODE2")).toLowerCase());
			map.put("COLUMN_CODE3", String.valueOf(map.get("COLUMN_CODE3")).toLowerCase());
			map.put("COLUMN_CODE4", String.valueOf(map.get("COLUMN_CODE4")).toLowerCase());

			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE1")))) {
				if (map.get("TABLE1") != null) {
					if (StringUtil.isBlank(String.valueOf(map.get("COLUMN_CHECK1")))) {
						map.put("COLUMN_CHECK1", "");
					} else {

						map.put("COLUMN_CHECK1", map.get("COLUMN_CHECK1") + "_ID");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE1")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE1")))) {
						map.put("COLUMN_NO1", "");
					} else {
						map.put("COLUMN_CHECK1_NO", map.get("COLUMN_CHECK1").toString().substring(0, 6) + "_NO");

					}
				}

			} else {
				map.put("COLUMN_NO1", "");
			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE2")))) {
				if (map.get("TABLE2") != null) {
					if (map.get("COLUMN_CHECK2") == null) {
						map.put("COLUMN_CHECK2", "");
					} else {
						map.put("COLUMN_CHECK2", map.get("COLUMN_CHECK2") + "_ID");

					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE2")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE2")))) {
						map.put("COLUMN_NO2", "");
					} else {
						map.put("COLUMN_CHECK2_NO", map.get("COLUMN_CHECK2").toString().substring(0, 6) + "_NO");
					}
				}

			} else {

				map.put("COLUMN_NO2", "");
			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE3")))) {
				if (map.get("TABLE3") != null) {
					if (map.get("COLUMN_CHECK3") == null) {
						map.put("COLUMN_CHECK3", "");
					} else {
						map.put("COLUMN_CHECK3", map.get("COLUMN_CHECK3") + "_ID");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE3")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE3")))) {
						map.put("COLUMN_NO3", "");
					} else {
						map.put("COLUMN_CHECK3_NO", map.get("COLUMN_CHECK3").toString().substring(0, 6) + "_NO");
					}
				}

			} else {

				map.put("COLUMN_NO3", "");
			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE4")))) {
				if (map.get("TABLE4") != null) {
					if (map.get("COLUMN_CHECK4") == null) {
						map.put("COLUMN_CHECK4", "");
					} else {
						map.put("COLUMN_CHECK4", map.get("COLUMN_CHECK4") + "_ID");

					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE4")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE4")))) {
						map.put("COLUMN_NO4", "");
					} else {
						map.put("COLUMN_CHECK4_NO", map.get("COLUMN_CHECK4").toString().substring(0, 6) + "_NO");
					}
				}

			} else {

				map.put("COLUMN_NO4", "");
			}
			paramMap.putAll(map);
		}
		
		List<Map<String, Object>> list = accNewOldJoinMapper.queryLedgerCheckList(paramMap);
		return ChdJson.toJson(JsonListMapUtil.toListMapLower(list));
	}

	@Override
	public String saveTmpAccLedger(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		
		try{
			// 保存余额
			accNewOldJoinMapper.deleteTmpAccLedger(paramMap);
			accNewOldJoinMapper.saveTmpAccLedger(paramMap);
			
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			// 准备保存辅助核算的参数
			if("1".equals(paramMap.get("is_check").toString())){
				String[] datas = paramMap.get("check_data").toString().split(",");
				String[] checkTypeNames = paramMap.get("checkTypeName").toString().split(";");
				for(String str : datas){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("subj_code", paramMap.get("subj_code"));
					map.put("check_type1", "");
					map.put("check_type2", "");
					map.put("check_type3", "");
					map.put("check_type4", "");
					map.put("check_code1", "");
					map.put("check_code2", "");
					map.put("check_code3", "");
					map.put("check_code4", "");
					map.put("check_name1", "");
					map.put("check_name2", "");
					map.put("check_name3", "");
					map.put("check_name4", "");
					for(int i = 0; i < checkTypeNames.length; i++){
						int k = i + 1;
						map.put("check_type" + k, checkTypeNames[i]);
					}
					String[] data = str.split("@");
					for(int j = 0; j < checkTypeNames.length; j++){
						int n = j + 1;
						String[] v = data[j].split(" ");
						if(v.length > 0){
							map.put("check_code" + n, v[0]);
							map.put("check_name" + n, v[1]);
						}
					}
					
					// 4 5 6 7
					map.put("bal_od", data[4]);
					map.put("bal_oc", data[5]);
					map.put("end_od", data[6]);
					map.put("end_oc", data[7]);
					list.add(map);
				}
				
				// 保存辅助核算
				accNewOldJoinMapper.deleteTmpAccLedgerCheckBySubjCode(paramMap);
				accNewOldJoinMapper.saveTmpAccLedgerCheckBatch(paramMap, list);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException("操作失败.");
		}
	}

	// 通过excel导入辅助核算
	@Override
	public String impTmpAccLedgerCheckByExcel(Map<String, Object> paramMap) throws DataAccessException {
		Map<String, Object> tmpMap = new HashMap<String, Object>();
		tmpMap.put("group_id", SessionManager.getGroupId());
		tmpMap.put("hos_id", SessionManager.getHosId());
		tmpMap.put("copy_code", SessionManager.getCopyCode());
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("group_id", SessionManager.getGroupId());
		sqlMap.put("hos_id", SessionManager.getHosId());
		sqlMap.put("copy_code", SessionManager.getCopyCode());
		Map<String, Object> table = (Map<String, Object>) paramMap.get("table");
		StringBuilder msg = new StringBuilder("没有找到以下项：");
		boolean flag = false;
		try{
			// 拿到excel头部
			Map<String,Object> para = (Map<String, Object>) JSONArray.parse(paramMap.get("para").toString());
			String subjCode = para.get("subj_code").toString();// 科目编号
			// excel头部
			List<Map<String, Object>> excelHead = (List<Map<String, Object>>) para.get("column");
			
			// excel行数据
			List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paramMap);
			if(list.size() > 0){
				for(Map<String, List<String>> map : list){
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("bal_od", map.get("bal_od").get(1) == null ? 0 : map.get("bal_od").get(1));
					saveMap.put("bal_oc", map.get("bal_oc").get(1) == null ? 0 : map.get("bal_oc").get(1));
					saveMap.put("end_od", map.get("bal_od").get(1) == null ? 0 : map.get("bal_od").get(1));
					saveMap.put("end_oc", map.get("bal_oc").get(1) == null ? 0 : map.get("bal_oc").get(1));
					saveMap.put("check_type1", "");
					saveMap.put("check_type2", "");
					saveMap.put("check_type3", "");
					saveMap.put("check_type4", "");
					saveMap.put("check_code1", "");
					saveMap.put("check_code2", "");
					saveMap.put("check_code3", "");
					saveMap.put("check_code4", "");
					saveMap.put("check_name1", "");
					saveMap.put("check_name2", "");
					saveMap.put("check_name3", "");
					saveMap.put("check_name4", "");
					for(int i = 0; i < excelHead.size() - 2; i++){
						int k = i + 1;
						saveMap.put("check_type" + k, excelHead.get(i).get("display").toString());
						//saveMap.put("check_name" + k, map.get(excelHead.get(i).get("name")).get(1));
						
						tmpMap.put("check_type_id", excelHead.get(i).get("check_type_id").toString());
						AccCheckType checkType = accCheckTypeMapper.queryAccCheckTypeById(tmpMap);
						sqlMap.put("column_code", checkType.getColumn_code());
						sqlMap.put("column_name", checkType.getColumn_name());
						sqlMap.put("check_table", checkType.getCheck_table());
						sqlMap.put("check_type_id", checkType.getCheck_type_id());
						sqlMap.put("is_sys", checkType.getIs_sys());
						sqlMap.put("is_change", checkType.getIs_change());
						sqlMap.put("check_name", map.get(excelHead.get(i).get("name").toString()).get(1));
						Map<String, Object> item = accNewOldJoinMapper.queryLedgerCheckCodeAndName(sqlMap);
						item = JsonListMapUtil.toMapLower(item);
						if(item.get("column_code") == null){
							flag = true;
							msg.append("<br/>").append(map.get(excelHead.get(i).get("name").toString()).get(1));
						}else{
							saveMap.put("check_code" + k, item.get("column_code").toString());
							saveMap.put("check_name" + k, item.get("column_name").toString());
						}
					}
					saveMap.put("subj_code", subjCode);
					allList.add(saveMap);
				}
				
				if(flag){
					return "{\"warn\":\"导入失败,"+ msg.toString() +".\",\"state\":\"false\"}";
				}else if(allList.size() > 0){
					accNewOldJoinMapper.deleteTmpAccLedgerCheckBatch(table, allList);
					accNewOldJoinMapper.saveTmpAccLedgerCheckBatch(table, allList);
					Map<String, Object> t = new HashMap<String, Object>();
					t.put("subj_code", subjCode);
					t.put("table_name_check", table.get("table_name_check"));
					Map<String, Object> ledgerSum = accNewOldJoinMapper.queryLedgerSumBySubjCode(t);
					ledgerSum = JsonListMapUtil.toMapLower(ledgerSum);
					ledgerSum.put("end_od", ledgerSum.get("bal_od"));
					ledgerSum.put("end_oc", ledgerSum.get("bal_oc"));
					ledgerSum.put("table_name_ledger", table.get("table_name_ledger").toString());
					accNewOldJoinMapper.updateTmpAccLedger(ledgerSum);
					
					return "{\"msg\":\"成功导入" + allList.size() + "条.\",\"state\":\"true\"}";
				}
			}
			return "{\"warn\":\"无数据导入.\",\"state\":\"false\"}";
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException("导入失败.");
		}
	}
	
	@Override
	public String regenerateAccSubjnewLedger() {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			accNewOldJoinMapper.regenerateAccSubjnewLedger(map);
			
			if (map.get("code").equals(0)) {
				accNewOldJoinMapper.regenerateAccSubjnewLedgerFuzhu(map);
				if(map.get("code").equals(0)){
					return "{\"msg\":\"旧余额成功转为新余额！\"}";
				}
				return "{\"error\":\""+map.get("name").toString()+"\"}";
			}else{
				return "{\"error\":\""+map.get("name").toString()+"\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String regenerateAccSubjnewOldMap() {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			accNewOldJoinMapper.regenerateAccSubjnewOldMap(map);
			
			if (map.get("code").equals(0)) {
				return "{\"msg\":\"生成成功！\"}";
			}else{
				return "{\"error\":\""+map.get("name").toString()+"\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String addOfficiallyNewSubj(Map<String, Object> paramMap) {
		try {
			//查询新年度是否存在数据
			int newCount =  accNewOldJoinMapper.queryaddOfficiallyAccSubj(paramMap);
			if (newCount != 0) {
				return "{\"warn\":\"新年度存在数据无法载入!\",\"state\":\"false\"}";
			}
			
			//查询辅助核算中文编码是否合法-新科目是否存在数据
			List<Map<String, Object>> list = accNewOldJoinMapper.queryaddOfficiallyNewSubj(paramMap);
			if(list.size() > 1){
				StringBuilder strb = new StringBuilder();
				for (Map<String, Object> map : list) {
					strb.append(map.get("SUBJ_NAME").toString()+":("+map.get("SUBJ_CODE").toString()+")<br>");
				}
				return "{\"error\":\"载入失败,辅助类名称不合法!<br>"+strb.toString()+"\"}";
			}else if(list.get(list.size() - 1).get("SUBJ_CODE").equals(0)){
				return "{\"error\":\"载入失败,新会计制度的数据为空!\"}";
			}
			
			List<Map<String, Object>> newlist = accNewOldJoinMapper.queryNewList(paramMap);
			for (Map<String, Object> map : newlist) {
				map.put("SPELL_CODE",StringTool.toPinyinShouZiMu(map.get("SUBJ_NAME").toString()));
				map.put("WBX_CODE",StringTool.toWuBi(map.get("SUBJ_NAME").toString()));
			}
			//新科目数据正式载入系统
			int addCount = accNewOldJoinMapper.addOfficiallyNewSubj(newlist);
			if (addCount == 0) {
				throw new SysException("载入失败,请重试!");
			}
			return "{\"msg\":\"载入成功!共 "+addCount+"条\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public List<Map<String, Object>> querySubjCodeOldSelect() {
		return accNewOldJoinMapper.querySubjCodeOldSelect();
	}

	@Override
	public List<Map<String, Object>> querySubjcodenewSelect(
			Map<String, Object> paramMap) {
		return accNewOldJoinMapper.querySubjcodenewSelect(paramMap);
	}
	
	@Override
	public String ledgerAndCheckIntoSys(Map<String, Object> paramMap) throws DataAccessException {
		try{
			accNewOldJoinMapper.ledgerAndCheckIntoSys(paramMap);
			return "{\"msg\":\"载入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException("载入失败.");
		}
	}

	@Override
	public String updateTranMap(Map<String, Object> paramMap) {
		try {
			
			accNewOldJoinMapper.deleteUpdateTranMap(paramMap);
			
			int updateCount = accNewOldJoinMapper.updateTranMap(paramMap);
			if (updateCount == 0) {
				throw new SysException("修改失败,请刷新页面后重试!");
			}
			return "{\"msg\":\"修改成功！\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String deleteAccNewOldMap(Map<String, Object> paramMap) {
		try {

			List<Map> list = JSONArray.parseArray(paramMap.get("paramVo").toString(), Map.class);
			for (Map map : list) {
				if (map.get("SUBJ_CODE_NEW_B") == null) {
					map.put("SUBJ_CODE_NEW_B", 0);
				}
			}
			
			int deleteCount = accNewOldJoinMapper.deleteTranMapList(list);
			if (deleteCount == 0) {
				new SysException("删除失败,请尝试或者刷新页面");
			}
			return "{\"msg\":\"删除成功！\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String queryNewLedgerExists(Map<String, Object> paramMap) throws DataAccessException {
		try {
			int count = accNewOldJoinMapper.queryNewLedgerExists(paramMap);
			if(count > 0){
				return "{\"state\":\"true\",\"exists\":\"exists\"}";
			}
			return "{\"state\":\"true\",\"exists\":\"not exists\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "{\"error\":\"查询失败！\",\"state\":\"false\"}";
		}
	}

	@Override
	public String importAccTranMap(Map<String, Object> paramMap) throws DataAccessException {
		List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
		List<String> strList = new ArrayList<String>();
		try{
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paramMap);
			if(list != null && list.size() > 0){
				for(Map<String, List<String>> map : list){
					if(map.get("subj_code_old").get(1) == null || map.get("subj_code_old").get(1) == ""){
						strList.add(map.get("subj_code_old").get(0));
					}
					if(map.get("subj_code_new").get(1) == null || map.get("subj_code_new").get(1) == ""){
						strList.add(map.get("subj_code_new").get(0));
					}
					
					Map<String, Object> saveMap = new HashMap<String, Object>();
					int codeLen1 = Integer.valueOf(map.get("subj_code_old").get(1).toString());
					int codeLen2 = Integer.valueOf(map.get("subj_code_new").get(1).toString());
					if(codeLen1 < 4 || codeLen1%2 != 0){
						String[] ar = map.get("subj_code_old").get(0).split(":");
						return "{\"warn\":\"第" + ar[0] + "行 " + ar[1] + "列编码格式不正确\",\"state\":\"false\"}";
					}
					if(codeLen2 < 4 || codeLen2%2 != 0){
						String[] ar = map.get("subj_code_new").get(0).split(":");
						return "{\"warn\":\"第" + ar[0] + "行 " + ar[1] + "列编码格式不正确\",\"state\":\"false\"}";
					}
					if(map.get("subj_code_new_b").get(1) != null && map.get("subj_code_new_b").get(1).toString() != ""){
						int codeLen3 = Integer.valueOf(map.get("subj_code_new_b").get(1).toString());
						if(codeLen3 < 4 || codeLen3%2 != 0){
							String[] ar = map.get("subj_code_new_b").get(0).split(":");
							return "{\"warn\":\"第" + ar[0] + "行 " + ar[1] + "列编码格式不正确\",\"state\":\"false\"}";
						}
					}
					
					saveMap.put("subj_code_old", map.get("subj_code_old").get(1));
					saveMap.put("subj_name_old", map.get("subj_name_old").get(1) == null ? "" : map.get("subj_name_old").get(1));
					saveMap.put("subj_name_all_old", map.get("subj_name_all_old").get(1) == null ? "" : map.get("subj_name_all_old").get(1));
					saveMap.put("subj_code_new", map.get("subj_code_new").get(1));
					saveMap.put("subj_name_new", map.get("subj_name_new").get(1) == null ? "" : map.get("subj_name_new").get(1));
					saveMap.put("subj_name_all_new", map.get("subj_name_all_new").get(1) == null ? "" : map.get("subj_name_all_new").get(1));
					saveMap.put("subj_code_new_b", map.get("subj_code_new_b").get(1) == null ? "" : map.get("subj_code_new_b").get(1));
					saveMap.put("subj_name_new_b", map.get("subj_name_new_b").get(1) == null ? "" : map.get("subj_name_new_b").get(1));
					saveMap.put("subj_name_all_new_b", map.get("subj_name_all_new_b").get(1) == null ? "" : map.get("subj_name_all_new_b").get(1));
					
					allList.add(saveMap);
				}
				String msg = null;
				// 必填格子检查
				msg = mustFillCheck(strList);
				if(msg != null){
					return msg;
				}
				
				// 检查主键重复
				msg = tranMapPKRepeatCheck(allList);
				if(msg != null){
					return msg;
				}
				
				if(allList.size() > 0){
					accNewOldJoinMapper.deleteTmpAccTranMapBatch(allList);
					int count = accNewOldJoinMapper.addTmpAccTranMapBatch(allList);
					return "{\"msg\":\"成功导入" + count + "条\",\"state\":\"false\"}";
				}
			}
			return "{\"warn\":\"没有数据！\",\"state\":\"false\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("导入失败.");
		}
	}
	
	/**
	 * 科目映射关系，检查联合主键是否重复
	 */
	private String tranMapPKRepeatCheck(List<Map<String, Object>> list){
		Map<String, String> scMap = new HashMap<String, String>();
		for(int i = 0; i < list.size(); i++){
			for(int j = i + 1; j < list.size(); j++){
				if(list.get(i).get("subj_code_old").toString().equals(list.get(j).get("subj_code_old").toString()) 
					&& list.get(i).get("subj_code_new").toString().equals(list.get(j).get("subj_code_new").toString()) ){
					
					scMap.put(list.get(i).get("subj_code_old").toString() + "," + list.get(i).get("subj_code_new").toString(),
							list.get(i).get("subj_code_old").toString() + "," + list.get(i).get("subj_code_new").toString());
				}
			}
		}
		// 不为空，则有重复的科目编码
		if(!scMap.isEmpty()){
			StringBuilder msg = new StringBuilder("以下科目对照重复：");
			Set<String> keySet = scMap.keySet();
			for(String code : keySet){
				msg.append("</br>").append("[" + code + "]");
			}
			return "{\"warn\":\"" + msg.toString() + "\",\"state\":\"false\"}";
		}
		return null;
	}

}
