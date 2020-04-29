package com.chd.hrp.ass.serviceImpl.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.check.AssCheckPlanMapMapper;
import com.chd.hrp.ass.dao.check.general.AssCheckDGeneralMapper;
import com.chd.hrp.ass.dao.check.general.AssCheckDdetailGeneralMapper;
import com.chd.hrp.ass.dao.check.general.AssCheckPlanGeneralMapper;
import com.chd.hrp.ass.dao.check.general.AssCheckSGeneralMapper;
import com.chd.hrp.ass.dao.check.general.AssCheckSdetailGeneralMapper;
import com.chd.hrp.ass.dao.check.other.AssCheckDOtherMapper;
import com.chd.hrp.ass.dao.check.other.AssCheckDdetailOtherMapper;
import com.chd.hrp.ass.dao.check.other.AssCheckPlanOtherMapper;
import com.chd.hrp.ass.dao.check.other.AssCheckSOtherMapper;
import com.chd.hrp.ass.dao.check.other.AssCheckSdetailOtherMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckDdetailSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckDpDetailSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckDspecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckPlanSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckSdetailSpecialMapper;
import com.chd.hrp.ass.dao.check.special.AssCheckSspecialMapper;
import com.chd.hrp.ass.entity.check.AssCheckPlan;
import com.chd.hrp.ass.entity.check.general.AssCheckDGeneral;
import com.chd.hrp.ass.entity.check.general.AssCheckSGeneral;
import com.chd.hrp.ass.entity.check.other.AssCheckDOther;
import com.chd.hrp.ass.entity.check.other.AssCheckSOther;
import com.chd.hrp.ass.entity.check.special.AssCheckDdetailSpecial;
import com.chd.hrp.ass.entity.check.special.AssCheckDspecial;
import com.chd.hrp.ass.entity.check.special.AssCheckSdetailSpecial;
import com.chd.hrp.ass.entity.check.special.AssCheckSspecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.AssCheckPlanService;
import com.github.pagehelper.PageInfo;
 
@Service("assCheckPlanService")
public class AssCheckPlanServiceImpl implements AssCheckPlanService {
	
	private static Logger logger = Logger.getLogger(AssCheckPlanServiceImpl.class);
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assCheckPlanMapMapper")
	private final AssCheckPlanMapMapper assCheckPlanMapMapper = null;
	
	@Resource(name = "assCheckPlanSpecialMapper")
	private final AssCheckPlanSpecialMapper assCheckPlanSpecialMapper = null;
	
	@Resource(name = "assCheckPlanOtherMapper")
	private final AssCheckPlanOtherMapper assCheckPlanOtherMapper = null;
	
	@Resource(name = "assCheckPlanGeneralMapper")
	private final AssCheckPlanGeneralMapper assCheckPlanGeneralMapper = null;
	
	@Resource(name = "assCheckSdetailSpecialMapper")
	private final AssCheckSdetailSpecialMapper assCheckSdetailSpecialMapper = null;

	// 引入DAO操作
	@Resource(name = "assCheckSspecialMapper")
	private final AssCheckSspecialMapper assCheckSspecialMapper = null;

	@Resource(name = "assCheckDdetailSpecialMapper")
	private final AssCheckDdetailSpecialMapper assCheckDdetailSpecialMapper = null;

	// 引入DAO操作
	@Resource(name = "assCheckDspecialMapper")
	private final AssCheckDspecialMapper assCheckDspecialMapper = null;

	@Resource(name = "assCheckDpDetailSpecialMapper")
	private final AssCheckDpDetailSpecialMapper assCheckDpDetailSpecialMapper = null;
	
	@Resource(name = "assCheckSdetailOtherMapper")
	private final AssCheckSdetailOtherMapper assCheckSdetailOtherMapper = null;

	// 引入DAO操作
	@Resource(name = "assCheckSOtherMapper")
	private final AssCheckSOtherMapper assCheckSOtherMapper = null;
	
	@Resource(name = "assCheckDdetailOtherMapper")
	private final AssCheckDdetailOtherMapper assCheckDdetailOtherMapper = null;
	
	// 引入DAO操作
	@Resource(name = "assCheckDOtherMapper")
	private final AssCheckDOtherMapper assCheckDOtherMapper = null;
	
	@Resource(name = "assCheckSdetailGeneralMapper")
	private final AssCheckSdetailGeneralMapper assCheckSdetailGeneralMapper = null;

	// 引入DAO操作
	@Resource(name = "assCheckSGeneralMapper")
	private final AssCheckSGeneralMapper assCheckSGeneralMapper = null;
	
	@Resource(name = "assCheckDdetailGeneralMapper")
	private final AssCheckDdetailGeneralMapper assCheckDdetailGeneralMapper = null;
	
	// 引入DAO操作
	@Resource(name = "assCheckDGeneralMapper")
	private final AssCheckDGeneralMapper assCheckDGeneralMapper = null;
	
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> mv_s = new HashMap<String, Object>();
		mv_s.put("group_id", entityMap.get("group_id"));
		mv_s.put("hos_id", entityMap.get("hos_id"));
		mv_s.put("copy_code", entityMap.get("copy_code"));
		mv_s.put("user_id", SessionManager.getUserId());
		mv_s.put("naturs_code", "02");
		// 盘点是否存在未执行完的盘点计划单
		List<Map<String, Object>> planlist_s = (List<Map<String, Object>>) assBaseService.queryCheckPlanFin(mv_s);
		if (planlist_s.size() > 0) {
			return "{\"error\":\"专用设备存在未执行完的计划单，不允许添加.\"}";
		}
		
		Map<String, Object> mv_g = new HashMap<String, Object>();
		mv_g.put("group_id", entityMap.get("group_id"));
		mv_g.put("hos_id", entityMap.get("hos_id"));
		mv_g.put("copy_code", entityMap.get("copy_code"));
		mv_g.put("user_id", SessionManager.getUserId());
		mv_g.put("naturs_code", "03");
		// 盘点是否存在未执行完的盘点计划单
		List<Map<String, Object>> planlist_g = (List<Map<String, Object>>) assBaseService.queryCheckPlanFin(mv_g);
		if (planlist_g.size() > 0) {
			return "{\"error\":\"一般设备存在未执行完的计划单，不允许添加.\"}";
		}
		
		Map<String, Object> mv_o = new HashMap<String, Object>();
		mv_o.put("group_id", entityMap.get("group_id"));
		mv_o.put("hos_id", entityMap.get("hos_id"));
		mv_o.put("copy_code", entityMap.get("copy_code"));
		mv_o.put("user_id", SessionManager.getUserId());
		mv_o.put("naturs_code", "04");
		// 盘点是否存在未执行完的盘点计划单
		List<Map<String, Object>> planlist_o = (List<Map<String, Object>>) assBaseService.queryCheckPlanFin(mv_o);
		if (planlist_o.size() > 0) {
			return "{\"error\":\"其他固定资产存在未执行完的计划单，不允许添加.\"}";
		}

		try {
			entityMap.put("bill_table", "ASS_CHECK_PLAN_SPECIAL");
			String check_plan_no_s = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("check_plan_no", check_plan_no_s);
			assCheckPlanSpecialMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
			entityMap.put("bill_table", "ASS_CHECK_PLAN_GENERAL");
			String check_plan_no_g = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("check_plan_no", check_plan_no_g);
			assCheckPlanGeneralMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
			entityMap.put("bill_table", "ASS_CHECK_PLAN_OTHER");
			String check_plan_no_o = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("check_plan_no", check_plan_no_o);
			assCheckPlanOtherMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
			entityMap.put("bill_table", "ASS_CHECK_PLAN_MAP");
			String check_plan_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("check_plan_no", check_plan_no);
			entityMap.put("check_plan_no_s", check_plan_no_s);
			entityMap.put("check_plan_no_g", check_plan_no_g);
			entityMap.put("check_plan_no_o", check_plan_no_o);
			assCheckPlanMapMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			assCheckPlanMapMapper.addBatch(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {

			int state = assCheckPlanMapMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			assCheckPlanMapMapper.updateBatch(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			throw new SysException(e.getMessage());

		}
	}
	
	private String vo_split(String str) {
		StringBuilder vo_no = new StringBuilder();

		for (String vo : str.split(",")) {
			vo_no.append("'");
			vo_no.append(vo);
			vo_no.append("',");
		}

		String sp = vo_no.toString().substring(0, vo_no.length() - 1);

		return "(" + sp + ")";
	}

	@Override
	public String delete(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			// 拼接不符合审核的单据
			StringBuilder sb = new StringBuilder();
			mapVo.put("check_plan_no", "");
			String vr = vo_split(mapVo.get("check_plan_no_all").toString());
			mapVo.put("check_plan_no_all", vr);

			List<AssCheckPlan> list = (List<AssCheckPlan>) queryExists(mapVo);

			for (AssCheckPlan cs : list) {

				Map<String, Object> map = new HashMap<String, Object>();

				// 表的主键
				map.put("group_id", cs.getGroup_id());
				map.put("hos_id", cs.getHos_id());
				map.put("copy_code", cs.getCopy_code());
				map.put("check_plan_no_s", cs.getCheck_plan_no_s());
				map.put("check_plan_no_g", cs.getCheck_plan_no_g());
				map.put("check_plan_no_o", cs.getCheck_plan_no_o());
				map.put("check_plan_no", cs.getCheck_plan_no());

				if (cs.getState() == 1) {
					sb.append(cs.getCheck_plan_no()).append(" ");
				} else {
					listVo.add(map);
				}
			}

			// 循环删除 盘点单（仓库 科室 主从表 ） 任务单
			for (Map<String, Object> m : listVo) {
				assCheckPlanMapMapper.delete(m);
			}

			if (null != sb && !"".equals(sb.toString().trim())) {
				return "{\"msg\":\"" + sb.toString() + "单据为确认状态不能删除.\",\"state\":\"true\"}";
			} else {
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityList,List<Map<String, Object>> entityListS,List<Map<String, Object>> entityListG,List<Map<String, Object>> entityListO) throws DataAccessException {
		try {
			// 删除仓库盘点明细
			assCheckSdetailSpecialMapper.deleteBatch(entityListS);
			// 删除科室盘点明细
			assCheckDdetailSpecialMapper.deleteBatch(entityListS);
			// 删除科室盘点单
			assCheckDspecialMapper.deleteBatch(entityListS);
			// 删除仓库盘点单
			assCheckSspecialMapper.deleteBatch(entityListS);
			// 删除盘点计划
			assCheckPlanSpecialMapper.deleteBatch(entityListS);
			
			// 删除仓库盘点明细
			assCheckSdetailGeneralMapper.deleteBatch(entityListG);
			// 删除科室盘点明细
			assCheckDdetailGeneralMapper.deleteBatch(entityListG);
			// 删除科室盘点单
			assCheckDGeneralMapper.deleteBatch(entityListG);
			// 删除仓库盘点单
			assCheckSGeneralMapper.deleteBatch(entityListG);
			// 删除盘点计划
			assCheckPlanGeneralMapper.deleteBatch(entityListG);
			
			// 删除仓库盘点明细
			assCheckSdetailOtherMapper.deleteBatch(entityListO);
			// 删除科室盘点明细
			assCheckDdetailOtherMapper.deleteBatch(entityListO);
			// 删除科室盘点单
			assCheckDOtherMapper.deleteBatch(entityListO);
			// 删除仓库盘点单
			assCheckSOtherMapper.deleteBatch(entityListO);
			// 删除盘点计划
			assCheckPlanOtherMapper.deleteBatch(entityListO);
			
			
			assCheckPlanMapMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssCheckPlan> list = (List<AssCheckPlan>) assCheckPlanMapMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCheckPlan> list = (List<AssCheckPlan>) assCheckPlanMapMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanMapMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanMapMapper.queryByUniqueness(entityMap);
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanMapMapper.queryExists(entityMap);
	}

	@Override
	public String audit(@RequestParam Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlan> list = (List<AssCheckPlan>) queryExists(mapVo);

		for (AssCheckPlan cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no_s", cs.getCheck_plan_no_s());
			map.put("check_plan_no_g", cs.getCheck_plan_no_g());
			map.put("check_plan_no_o", cs.getCheck_plan_no_o());
			map.put("check_plan_no", cs.getCheck_plan_no());

			map.put("audit_emp", SessionManager.getUserId());

			map.put("audit_date", DateUtil.getCurrenDate());
			Integer checeks_s = assCheckPlanMapMapper.queryCheckS_s(map);
			if (checeks_s == 0) {
				return "{\"error\":\"专用设备-编码：【" + cs.getCheck_plan_no_s() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			Integer checekd_s = assCheckPlanMapMapper.queryCheckD_s(map);
			if (checekd_s == 0) {
				return "{\"error\":\"专用设备-编码：【" + cs.getCheck_plan_no_s() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			
			
			Integer checeks_g = assCheckPlanMapMapper.queryCheckS_g(map);
			if (checeks_g == 0) {
				return "{\"error\":\"一般设备-编码：【" + cs.getCheck_plan_no_g() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			Integer checekd_g = assCheckPlanMapMapper.queryCheckD_g(map);
			if (checekd_g == 0) {
				return "{\"error\":\"一般设备-编码：【" + cs.getCheck_plan_no_g() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			
			
			Integer checeks_o = assCheckPlanMapMapper.queryCheckS_o(map);
			if (checeks_o == 0) {
				return "{\"error\":\"其他固定资产-编码：【" + cs.getCheck_plan_no_o() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			Integer checekd_o = assCheckPlanMapMapper.queryCheckD_o(map);
			if (checekd_o == 0) {
				return "{\"error\":\"其他固定资产-编码：【" + cs.getCheck_plan_no_o() + "】未生产盘点单,不能确认！\",\"state\":\"false\"}";
			}
			
			
			map.put("state", "1");

			if (cs.getState() == 1) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		assCheckPlanSpecialMapper.updateBatch(listVo);
		assCheckPlanGeneralMapper.updateBatch(listVo);
		assCheckPlanOtherMapper.updateBatch(listVo);
		assCheckPlanMapMapper.updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据已确认不能重复确认.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		}
	}

	@Override
	public String unAudit(@RequestParam Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlan> list = (List<AssCheckPlan>) queryExists(mapVo);

		for (AssCheckPlan cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());
			map.put("check_plan_no_s", cs.getCheck_plan_no_s());
			map.put("check_plan_no_g", cs.getCheck_plan_no_g());
			map.put("check_plan_no_o", cs.getCheck_plan_no_o());
			map.put("audit_emp", "");

			map.put("audit_date", "");

			map.put("state", "0");

			if (cs.getState() == 0) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		assCheckPlanSpecialMapper.updateBatch(listVo);
		assCheckPlanGeneralMapper.updateBatch(listVo);
		assCheckPlanOtherMapper.updateBatch(listVo);
		assCheckPlanMapMapper.updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为新建状态不能取消确认.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
		}
	}

	@Override
	public String finish(@RequestParam Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlan> list = (List<AssCheckPlan>) queryExists(mapVo);

		for (AssCheckPlan cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());
			map.put("check_plan_no_s", cs.getCheck_plan_no_s());
			map.put("check_plan_no_g", cs.getCheck_plan_no_g());
			map.put("check_plan_no_o", cs.getCheck_plan_no_o());
			map.put("fin_date", DateUtil.getCurrenDate());

			map.put("state", "2");

			if (cs.getState() == 2 || cs.getState() == 0) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		assCheckPlanSpecialMapper.updateBatch(listVo);
		assCheckPlanGeneralMapper.updateBatch(listVo);
		assCheckPlanOtherMapper.updateBatch(listVo);
		assCheckPlanMapMapper.updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为新建或者完成状态不能盘点完成.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"盘点完成.\",\"state\":\"true\"}";
		}
	}

	@Override
	public String unFinish(@RequestParam Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		// 拼接不符合审核的单据
		StringBuilder sb = new StringBuilder();
		mapVo.put("check_plan_no", "");
		String vr = vo_split(mapVo.get("check_plan_no_all").toString());
		mapVo.put("check_plan_no_all", vr);
		List<AssCheckPlan> list = (List<AssCheckPlan>) queryExists(mapVo);

		for (AssCheckPlan cs : list) {

			Map<String, Object> map = new HashMap<String, Object>();

			// 表的主键
			map.put("group_id", cs.getGroup_id());
			map.put("hos_id", cs.getHos_id());
			map.put("copy_code", cs.getCopy_code());
			map.put("check_plan_no", cs.getCheck_plan_no());
			map.put("check_plan_no_s", cs.getCheck_plan_no_s());
			map.put("check_plan_no_g", cs.getCheck_plan_no_g());
			map.put("check_plan_no_o", cs.getCheck_plan_no_o());
			map.put("fin_date", "");

			map.put("state", "1");

			if (cs.getState() == 0 || cs.getState() == 1) {
				sb.append(cs.getCheck_plan_no()).append(" ");
			} else {
				listVo.add(map);
			}
		}
		// 更新数据
		assCheckPlanSpecialMapper.updateBatch(listVo);
		assCheckPlanGeneralMapper.updateBatch(listVo);
		assCheckPlanOtherMapper.updateBatch(listVo);
		assCheckPlanMapMapper.updateBatch(listVo);

		if (null != sb && !"".equals(sb.toString().trim())) {
			return "{\"msg\":\"" + sb.toString() + "单据为新建和确认状态不能取消完成.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"取消完成.\",\"state\":\"true\"}";
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public String generate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			AssCheckPlan s = assCheckPlanMapMapper.queryByCode(entityMap);
			if (s == null || s.getState() == 1) {
				return "{\"error\":\"盘点单已确认，不能生成盘点单.\",\"state\":\"true\"}";
			} else {

				addGenerateStore(entityMap);
				addGenerateDept(entityMap);

				// 添加科室盘点单 主从表
				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			}
		}
		catch (Exception e) {
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public String generateDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("is_dept").toString().equals("0")) {
				addGenerateStoreDetail(entityMap);
			} else if (entityMap.get("is_dept").toString().equals("1")) {
				addGenerateDeptDetail(entityMap);
			} else {
				// 当前台传递的is_dept不为 0 或者 1 时 不进行操作
				return "{\"msg\":\"无效的盘点单 is_dept 取值错误.\",\"state\":\"true\"}";
			}

			// 添加科室盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public String addGenerateStore(Map<String, Object> entityMap) throws DataAccessException {
		try {

			// 查询仓库信息 按照仓库生成盘点单
			List<Map<String, Object>> store = (List<Map<String, Object>>) assCheckPlanMapMapper.queryStores(entityMap);

			for (Map<String, Object> mapS : store) {
				Map<String, Object> s = new HashMap<String, Object>();
				s.putAll(entityMap);
				s.put("store_id", mapS.get("store_id"));
				s.put("store_no", mapS.get("store_no"));
				
				// 添加仓库盘点单 主表
				mapS.put("bill_table", "ASS_CHECK_S_SPECIAL");
				String check_no_s = assBaseService.getBillNOSeqNo(mapS);
				mapS.put("bill_table", "ASS_CHECK_S_GENERAL");
				String check_no_g = assBaseService.getBillNOSeqNo(mapS);
				mapS.put("bill_table", "ASS_CHECK_S_OTHER");
				String check_no_o = assBaseService.getBillNOSeqNo(mapS);

				s.put("check_no_s", check_no_s);
				s.put("check_no_g", check_no_g);
				s.put("check_no_o", check_no_o);
				s.put("check_emp", SessionManager.getUserId());
				s.put("check_date", DateUtil.getCurrenDate());
				s.put("state", "0");
				s.put("summary", "");
				s.put("note", "");

				assCheckPlanMapMapper.addGenerateStore(s);
				// 更新最大单号
				mapS.put("bill_table", "ASS_CHECK_S_SPECIAL");
				assBaseService.updateAssBillNoMaxNo(mapS);
				mapS.put("bill_table", "ASS_CHECK_S_GENERAL");
				assBaseService.updateAssBillNoMaxNo(mapS);
				mapS.put("bill_table", "ASS_CHECK_S_OTHER");
				assBaseService.updateAssBillNoMaxNo(mapS);
			}
			// 添加科室盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public String addGenerateDept(Map<String, Object> entityMap) throws DataAccessException {
		try {

			// 查询科室信息 按照科室生成盘点单
			List<Map<String, Object>> depts = (List<Map<String, Object>>) assCheckPlanSpecialMapper.queryDepts(entityMap);
			for (Map<String, Object> mapD : depts) {

				Map<String, Object> s = new HashMap<String, Object>();
				s.putAll(entityMap);
				s.put("dept_id", mapD.get("dept_id"));
				s.put("dept_no", mapD.get("dept_no"));
				
				
				
				mapD.put("bill_table", "ASS_CHECK_D_SPECIAL");
				String check_no_s = assBaseService.getBillNOSeqNo(mapD);
				mapD.put("bill_table", "ASS_CHECK_D_GENERAL");
				String check_no_g = assBaseService.getBillNOSeqNo(mapD);
				mapD.put("bill_table", "ASS_CHECK_D_OTHER");
				String check_no_o = assBaseService.getBillNOSeqNo(mapD);

				s.put("check_no_s", check_no_s);
				s.put("check_no_g", check_no_g);
				s.put("check_no_o", check_no_o);
				s.put("check_emp", SessionManager.getUserId());
				s.put("check_date", DateUtil.getCurrenDate());
				s.put("state", "0");
				s.put("summary", "");
				s.put("note", "");
				assCheckPlanMapMapper.addGenerateDept(s);
				// 更新最大单号
				mapD.put("bill_table", "ASS_CHECK_D_SPECIAL");
				assBaseService.updateAssBillNoMaxNo(mapD);
				mapD.put("bill_table", "ASS_CHECK_D_GENERAL");
				assBaseService.updateAssBillNoMaxNo(mapD);
				mapD.put("bill_table", "ASS_CHECK_D_OTHER");
				assBaseService.updateAssBillNoMaxNo(mapD);
			}
			// 添加科室盘点单 主从表
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}
	
	@Override
	public String addGenerateStoreDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assCheckPlanMapMapper.addGenerateStoreDetail(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}
	
	@Override
	public String addGenerateDeptDetail(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assCheckPlanMapMapper.addGenerateDeptDetail(entityMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException {
		return assCheckPlanMapMapper.queryByTree(entityMap);
	}

	@Override
	public String auditCheck(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("is_dept").toString().equals("0")) {
				AssCheckSspecial s = assCheckSspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 0) {
					return "{\"msg\":\"专用设备-不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckSspecialMapper.update(entityMap);
				}
				
				AssCheckSGeneral g = assCheckSGeneralMapper.queryByCode(entityMap);
				if (g == null || g.getState() != 0) {
					return "{\"msg\":\"一般设备-不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckSGeneralMapper.update(entityMap);
				}
				
				
				AssCheckSOther o = assCheckSOtherMapper.queryByCode(entityMap);
				if (o == null || o.getState() != 0) {
					return "{\"msg\":\"其他固定资产-不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckSOtherMapper.update(entityMap);
				}

			} else if (entityMap.get("is_dept").toString().equals("1")) {
				AssCheckDspecial s = assCheckDspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 0) {
					return "{\"msg\":\"专用设备-不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckDspecialMapper.update(entityMap);
				}
				
				AssCheckDGeneral g = assCheckDGeneralMapper.queryByCode(entityMap);
				if (g == null || g.getState() != 0) {
					return "{\"msg\":\"一般设备-不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckDGeneralMapper.update(entityMap);
				}
				
				AssCheckDOther o = assCheckDOtherMapper.queryByCode(entityMap);
				if (o == null || o.getState() != 0) {
					return "{\"msg\":\"其他固定资产-不是新建状态不能提交.\",\"state\":\"false\"}";
				} else {
					assCheckDOtherMapper.update(entityMap);
				}
				
			} else {
				// 当前台传递的is_dept不为 0 或者 1 时 不进行操作
				return "{\"msg\":\"无效的盘点单 is_dept 取值错误.\",\"state\":\"false\"}";
			}
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"提交失败\"}");
		}
	}

	@Override
	public String unAuditCheck(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("is_dept").toString().equals("0")) {
				AssCheckSspecial s = assCheckSspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 1) {
					return "{\"msg\":\"专用设备-不是提交状态不能取消提交.\",\"state\":\"false\"}";
				} else {
					assCheckSspecialMapper.update(entityMap);
				}
				
				AssCheckSGeneral g = assCheckSGeneralMapper.queryByCode(entityMap);
				if (g == null || g.getState() != 1) {
					return "{\"msg\":\"一般设备-不是提交状态不能取消提交.\",\"state\":\"false\"}";
				} else {
					assCheckSGeneralMapper.update(entityMap);
				}
				
				
				AssCheckSOther o = assCheckSOtherMapper.queryByCode(entityMap);
				if (o == null || o.getState() != 1) {
					return "{\"msg\":\"其他固定资产-不是提交状态不能取消提交.\",\"state\":\"false\"}";
				} else {
					assCheckSOtherMapper.update(entityMap);
				}

			} else if (entityMap.get("is_dept").toString().equals("1")) {
				AssCheckDspecial s = assCheckDspecialMapper.queryByCode(entityMap);
				if (s == null || s.getState() != 1) {
					return "{\"msg\":\"专用设备-不是提交状态不能取消提交.\",\"state\":\"false\"}";
				} else {
					assCheckDspecialMapper.update(entityMap);
				}
				
				AssCheckDGeneral g = assCheckDGeneralMapper.queryByCode(entityMap);
				if (g == null || g.getState() != 1) {
					return "{\"msg\":\"一般设备-不是提交状态不能取消提交.\",\"state\":\"false\"}";
				} else {
					assCheckDGeneralMapper.update(entityMap);
				}
				
				AssCheckDOther o = assCheckDOtherMapper.queryByCode(entityMap);
				if (o == null || o.getState() != 1) {
					return "{\"msg\":\"其他固定资产-不是提交状态不能取消提交.\",\"state\":\"false\"}";
				} else {
					assCheckDOtherMapper.update(entityMap);
				}
			} else {
				// 当前台传递的is_dept不为 0 或者 1 时 不进行操作
				return "{\"msg\":\"无效的盘点单 is_dept 取值错误.\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"取消提交成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			// 抛出异常 防止事务不回滚
			throw new SysException("{\"error\":\"取消提交失败\"}");
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAssCheckS(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<AssCheckSspecial> list = (List<AssCheckSspecial>) assCheckSspecialMapper.queryAll(entityMap);
			
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCheckSspecial> list = (List<AssCheckSspecial>) assCheckSspecialMapper.queryAll(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryAssCheckD(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<AssCheckDspecial> list = (List<AssCheckDspecial>) assCheckDspecialMapper.queryAll(entityMap);
			
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssCheckDspecial> list = (List<AssCheckDspecial>) assCheckDspecialMapper.queryAll(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryAssCheckSdetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCheckSdetailSpecial> list = (List<AssCheckSdetailSpecial>)assCheckSdetailSpecialMapper.queryAll(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCheckSdetailSpecial> list = (List<AssCheckSdetailSpecial>)assCheckSdetailSpecialMapper.queryAll(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String queryAssCheckDdetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCheckDdetailSpecial> list = (List<AssCheckDdetailSpecial>)assCheckDdetailSpecialMapper.queryAll(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCheckDdetailSpecial> list = (List<AssCheckDdetailSpecial>)assCheckDdetailSpecialMapper.queryAll(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String queryCheckPlanStore(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub 
		StringBuilder reJson=new StringBuilder();
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		sysPage.setPage(1);
		sysPage.setPagesize(10);
		reJson.append("{\"Rows\":");
		try{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list=assCheckPlanMapMapper.queryCheckPlanStore(map,rowBounds);
			if(list!=null && list.size()>0){
				reJson.append("[");
				for(Map<String,Object> m:list){
					reJson.append("{");
					reJson.append("\"store_name\":\""+m.get("STORE_NAME")+"\",");
					reJson.append("\"amount\":\""+m.get("AMOUNT")+"\"");
					reJson.append("},");
				}
				reJson.setCharAt(reJson.length()-1, ']');
				reJson.append("}");
			}else{
				reJson.append("[]}");
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		// TODO Auto-generated method stub
		return reJson.toString();
	}

	@Override
	public String queryCheckPlanDept(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuilder reJson=new StringBuilder();
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		sysPage.setPage(1);
		sysPage.setPagesize(10);
		reJson.append("{\"Rows\":");
		try{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list=assCheckPlanMapMapper.queryCheckPlanDept(map,rowBounds);
			if(list!=null && list.size()>0){
				reJson.append("[");
				for(Map<String,Object> m:list){
					reJson.append("{");
					reJson.append("\"dept_name\":\""+m.get("DEPT_NAME")+"\",");
					reJson.append("\"amount\":\""+m.get("AMOUNT")+"\"");
					reJson.append("},");
				}
				reJson.setCharAt(reJson.length()-1, ']');
				reJson.append("}");
			}else{
				reJson.append("[]}");
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
		// TODO Auto-generated method stub
		return reJson.toString();
	}

}
