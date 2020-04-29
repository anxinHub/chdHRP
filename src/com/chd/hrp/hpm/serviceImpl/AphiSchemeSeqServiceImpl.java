package com.chd.hrp.hpm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiCompanyPercMapper;
import com.chd.hrp.hpm.dao.AphiCompanyPercSeqMapper;
import com.chd.hrp.hpm.dao.AphiCompanySchemeMapper;
import com.chd.hrp.hpm.dao.AphiCompanySchemeSeqMapper;
import com.chd.hrp.hpm.dao.AphiCostitemConfMapper;
import com.chd.hrp.hpm.dao.AphiCostitemConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiCostitemMapper;
import com.chd.hrp.hpm.dao.AphiCostitemPercMapper;
import com.chd.hrp.hpm.dao.AphiCostitemPercSeqMapper;
import com.chd.hrp.hpm.dao.AphiCostitemSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptBalancePercConfMapper;
import com.chd.hrp.hpm.dao.AphiDeptBalancePercConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptKindSchemeMapper;
import com.chd.hrp.hpm.dao.AphiDeptKindSchemeSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptKindTargetMapper;
import com.chd.hrp.hpm.dao.AphiDeptKindTargetSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptLimitConfMapper;
import com.chd.hrp.hpm.dao.AphiDeptLimitConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptSchemeMapper;
import com.chd.hrp.hpm.dao.AphiDeptSchemeSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptTargetMapper;
import com.chd.hrp.hpm.dao.AphiDeptTargetSeqMapper;
import com.chd.hrp.hpm.dao.AphiSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiIncomeItemMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemConfMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPercMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPercSeqMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPointMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemPointSeqMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemSeqMapper;
import com.chd.hrp.hpm.dao.AphiItemIncreasePercConfMapper;
import com.chd.hrp.hpm.dao.AphiItemIncreasePercConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.dao.AphiPointValueMapper;
import com.chd.hrp.hpm.dao.AphiPointValueSeqMapper;
import com.chd.hrp.hpm.dao.AphiSchemeSeqMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemConfMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemConfSeqMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiCompanyPerc;
import com.chd.hrp.hpm.entity.AphiCompanyScheme;
import com.chd.hrp.hpm.entity.AphiCostitem;
import com.chd.hrp.hpm.entity.AphiCostitemConf;
import com.chd.hrp.hpm.entity.AphiCostitemPerc;
import com.chd.hrp.hpm.entity.AphiDeptBalancePercConf;
import com.chd.hrp.hpm.entity.AphiDeptKindScheme;
import com.chd.hrp.hpm.entity.AphiDeptKindTarget;
import com.chd.hrp.hpm.entity.AphiDeptLimitConf;
import com.chd.hrp.hpm.entity.AphiDeptScheme;
import com.chd.hrp.hpm.entity.AphiDeptTarget;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.entity.AphiIncomeitemConf;
import com.chd.hrp.hpm.entity.AphiIncomeitemPerc;
import com.chd.hrp.hpm.entity.AphiIncomeitemPoint;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;
import com.chd.hrp.hpm.entity.AphiPointValue;
import com.chd.hrp.hpm.entity.AphiWorkitem;
import com.chd.hrp.hpm.entity.AphiWorkitemConf;
import com.chd.hrp.hpm.service.AphiSchemeSeqService;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.github.pagehelper.PageInfo;

@Service("aphiSchemeSeqService")
public class AphiSchemeSeqServiceImpl implements AphiSchemeSeqService {

	private static Logger logger = Logger.getLogger(AphiSchemeSeqServiceImpl.class);

	@Resource(name = "aphiDeptSchemeMapper")
	private final AphiDeptSchemeMapper aphiDeptSchemeMapper = null;
	
	@Resource(name = "aphiDeptSchemeSeqMapper")
	private final AphiDeptSchemeSeqMapper aphiDeptSchemeSeqMapper = null;

	@Resource(name = "aphiSchemeSeqMapper")
	private final AphiSchemeSeqMapper aphiSchemeSeqMapper = null;

	@Resource(name = "aphiIncomeItemMapper")
	private final AphiIncomeItemMapper aphiIncomeItemMapper = null;

	@Resource(name = "aphiIncomeitemSeqMapper")
	private final AphiIncomeitemSeqMapper aphiIncomeitemSeqMapper = null;

	@Resource(name = "aphiIncomeitemConfMapper")
	private final AphiIncomeitemConfMapper aphiIncomeitemConfMapper = null;

	@Resource(name = "aphiIncomeitemConfSeqMapper")
	private final AphiIncomeitemConfSeqMapper aphiIncomeitemConfSeqMapper = null;

	@Resource(name = "aphiIncomeitemPercMapper")
	private final AphiIncomeitemPercMapper aphiIncomeitemPercMapper = null;

	@Resource(name = "aphiIncomeitemPercSeqMapper")
	private final AphiIncomeitemPercSeqMapper aphiIncomeitemPercSeqMapper = null;
	
	@Resource(name = "aphiIncomeitemPointMapper")
	private final AphiIncomeitemPointMapper aphiIncomeitemPointMapper = null;

	@Resource(name = "aphiIncomeitemPointSeqMapper")
	private final AphiIncomeitemPointSeqMapper aphiIncomeitemPointSeqMapper = null;

	@Resource(name = "aphiPointValueMapper")
	private final AphiPointValueMapper aphiPointValueMapper = null;

	@Resource(name = "aphiPointValueSeqMapper")
	private final AphiPointValueSeqMapper aphiPointValueSeqMapper = null;

	@Resource(name = "aphiItemIncreasePercConfMapper")
	private final AphiItemIncreasePercConfMapper aphiItemIncreasePercConfMapper = null;

	@Resource(name = "aphiItemIncreasePercConfSeqMapper")
	private final AphiItemIncreasePercConfSeqMapper aphiItemIncreasePercConfSeqMapper = null;

	@Resource(name = "aphiCostitemMapper")
	private final AphiCostitemMapper aphiCostitemMapper = null;

	@Resource(name = "aphiCostitemSeqMapper")
	private final AphiCostitemSeqMapper aphiCostitemSeqMapper = null;

	@Resource(name = "aphiCostitemConfMapper")
	private final AphiCostitemConfMapper aphiCostitemConfMapper = null;

	@Resource(name = "aphiCostitemConfSeqMapper")
	private final AphiCostitemConfSeqMapper aphiCostitemConfSeqMapper = null;

	@Resource(name = "aphiDeptBalancePercConfMapper")
	private final AphiDeptBalancePercConfMapper aphiDeptBalancePercConfMapper = null;

	@Resource(name = "aphiDeptBalancePercConfSeqMapper")
	private final AphiDeptBalancePercConfSeqMapper aphiDeptBalancePercConfSeqMapper = null;

	@Resource(name = "aphiDeptLimitConfMapper")
	private final AphiDeptLimitConfMapper aphiDeptLimitConfMapper = null;

	@Resource(name = "aphiDeptLimitConfSeqMapper")
	private final AphiDeptLimitConfSeqMapper aphiDeptLimitConfSeqMapper = null;

	@Resource(name = "aphiWorkitemMapper")
	private final AphiWorkitemMapper aphiWorkitemMapper = null;

	@Resource(name = "aphiWorkitemSeqMapper")
	private final AphiWorkitemSeqMapper aphiWorkitemSeqMapper = null;

	@Resource(name = "aphiWorkitemConfMapper")
	private final AphiWorkitemConfMapper aphiWorkitemConfMapper = null;

	@Resource(name = "aphiWorkitemConfSeqMapper")
	private final AphiWorkitemConfSeqMapper aphiWorkitemConfSeqMapper = null;

	@Resource(name = "aphiCostitemPercMapper")
	private final AphiCostitemPercMapper aphiCostitemPercMapper = null;

	@Resource(name = "aphiCostitemPercSeqMapper")
	private final AphiCostitemPercSeqMapper aphiCostitemPercSeqMapper = null;

	@Resource(name = "aphiCompanyPercMapper")
	private final AphiCompanyPercMapper aphiCompanyPercMapper = null;

	@Resource(name = "aphiCompanyPercSeqMapper")
	private final AphiCompanyPercSeqMapper aphiCompanyPercSeqMapper = null;

	@Resource(name = "aphiDeptKindTargetMapper")
	private final AphiDeptKindTargetMapper aphiDeptKindTargetMapper = null;

	@Resource(name = "aphiDeptKindTargetSeqMapper")
	private final AphiDeptKindTargetSeqMapper aphiDeptKindTargetSeqMapper = null;

	@Resource(name = "aphiDeptTargetMapper")
	private final AphiDeptTargetMapper aphiDeptTargetMapper = null;

	@Resource(name = "aphiDeptTargetSeqMapper")
	private final AphiDeptTargetSeqMapper aphiDeptTargetSeqMapper = null;

	@Resource(name = "aphiCompanySchemeMapper")
	private final AphiCompanySchemeMapper aphiCompanySchemeMapper = null;

	@Resource(name = "aphiCompanySchemeSeqMapper")
	private final AphiCompanySchemeSeqMapper aphiCompanySchemeSeqMapper = null;

	@Resource(name = "aphiDeptKindSchemeMapper")
	private final AphiDeptKindSchemeMapper aphiDeptKindSchemeMapper = null;

	@Resource(name = "aphiDeptKindSchemeSeqMapper")
	private final AphiDeptKindSchemeSeqMapper aphiDeptKindSchemeSeqMapper = null;

	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	@Resource(name = "aphiSchemeConfMapper")
	private AphiSchemeConfMapper aphiSchemeConfMapper = null;

	@Override
	public String queryHpmSchemeSeq(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		StringBuffer sql = new StringBuffer();

		List checkList = aphiItemMapper.qeuryItemData(entityMap);
		
		for (int i = 0; i < checkList.size(); i++) {
			
			AphiItem item = (AphiItem) checkList.get(i);
			
			sql.append("max(nvl((case when ai.item_code = '" + item.getItem_code() + "' then to_char(formula_method_chs) end),'')) as  formula_method_chs" + i + ",");
			
			sql.append("max(nvl((case when ai.item_code = '" + item.getItem_code() + "' then ads.formula_code end),'')) as  formula_code" + i + ",");
		
		}
		
		entityMap.put("sql", sql);

		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = aphiDeptSchemeMapper.queryDeptSchemeBySchemeSeq(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = aphiDeptSchemeMapper.queryDeptSchemeBySchemeSeq(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}

	@Override
	public String auditHpmSchemeSeq(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 返回最大ID 用作方案序号
		int max_scheme_seq_no = aphiSchemeSeqMapper.queryMaxSchemeSeqNo(entityMap) + 1;

		try {

			// 1.save APHI_SCHEME_SEQ
			entityMap.put("scheme_seq_no", max_scheme_seq_no);

			entityMap.put("scheme_note", "scheme_note");

			aphiSchemeSeqMapper.addSchemeSeq(entityMap);

			// 2.save Aphi_IncomeItem_seq
			List<AphiIncomeItem> listIncomeItem = aphiIncomeItemMapper.queryIncomeItem(entityMap);

			for (int i = 0; i < listIncomeItem.size(); i++) {

				AphiIncomeItem ii = listIncomeItem.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", ii.getGroup_id());

				map.put("hos_id", ii.getHos_id());

				map.put("copy_code", ii.getCopy_code());

				map.put("scheme_seq_no", max_scheme_seq_no);

				map.put("income_item_code", ii.getIncome_item_code());

				if(ii.getIncome_item_name() !=null && !"".equals(ii.getIncome_item_name())){
					
					map.put("income_item_name", ii.getIncome_item_name());
					
				}else{
					
					map.put("income_item_name", "");
					
				}
				
				if(ii.getSpell_code() !=null && !"".equals(ii.getSpell_code())){

					map.put("spell_code", ii.getSpell_code());

				}else{
					
					map.put("spell_code", "");
				}
				
				if(ii.getWbx_code() !=null && !"".equals(ii.getWbx_code())){
					
					map.put("wbx_code", ii.getWbx_code());
					
				}else{
					
					map.put("wbx_code", "");
				}
				

				if(ii.getData_source() !=null && !"".equals(ii.getData_source())){
					
					map.put("data_source", ii.getData_source());
					
				}else{
					
					map.put("data_source", "");
				}
				
				if(ii.getIs_stop() !=null && !"".equals(ii.getIs_stop())){
					
					map.put("is_stop", ii.getIs_stop());
				}else{
					
					map.put("is_stop", "0");
				}

				aphiIncomeitemSeqMapper.addIncomeitemSeq(map);
			}

			// 3.Aphi_IncomeItem_Conf_Seq

			List<AphiIncomeitemConf> listIncomeItemConf = aphiIncomeitemConfMapper.queryIncomeitemConf(entityMap);

			for (int i = 0; i < listIncomeItemConf.size(); i++) {

				AphiIncomeitemConf iic = listIncomeItemConf.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", iic.getGroup_id());

				map.put("hos_id", iic.getHos_id());

				map.put("copy_code", iic.getCopy_code());

				map.put("dept_id", iic.getDept_id());
				
				map.put("dept_no", iic.getDept_no());

				map.put("income_item_code", iic.getIncome_item_code());

				map.put("is_acc", iic.getIs_acc());

				map.put("order_perc", iic.getOrder_perc());

				map.put("perform_perc", iic.getPerform_perc());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiIncomeitemConfSeqMapper.addIncomeitemConfSeq(map);

			}
			// 4.Aphi_CostItem_Seq

			List<AphiCostitem> listCostitem = aphiCostitemMapper.queryCostitem(entityMap);

			for (int i = 0; i < listCostitem.size(); i++) {

				AphiCostitem ci = listCostitem.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", ci.getGroup_id());

				map.put("hos_id", ci.getHos_id());

				map.put("copy_code", ci.getCopy_code());

				map.put("cost_item_code", ci.getCost_item_code());

				map.put("cost_type_code", ci.getCost_type_code());

				if(ci.getCost_iitem_name() !=null && !"".equals(ci.getCost_iitem_name())){
					
					map.put("cost_iitem_name", ci.getCost_iitem_name());
					
				}else{
					
					map.put("cost_iitem_name", "");
				}
				
				if (ci.getSpell_code() != null && !"".equals(ci.getSpell_code())) {

					map.put("spell_code", ci.getSpell_code());
					
				} else {
					
					map.put("spell_code", "");
					
				}

				if (ci.getWbx_code() != null && !"".equals(ci.getWbx_code())) {
					
					map.put("wbx_code", ci.getWbx_code());
					
				} else {

					map.put("wbx_code", "");
					
				}

				if (ci.getData_source() != null && !"".equals(ci.getData_source())) {

					map.put("data_source", ci.getData_source());
					
				} else {
					
					map.put("data_source", "");
					
				}

				if (ci.getIs_stop() != null && !"".equals(ci.getIs_stop())) {
					
					map.put("is_stop", ci.getIs_stop());
					
				} else {
					
					map.put("is_stop", "");
					
				}

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiCostitemSeqMapper.addCostitemSeq(map);

			}
			// 5.Aphi_CostItem_Conf_Seq

			List<AphiCostitemConf> listCostitemConf = aphiCostitemConfMapper.queryCostitemConf(entityMap);

			for (int i = 0; i < listCostitemConf.size(); i++) {

				AphiCostitemConf cif = listCostitemConf.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", cif.getGroup_id());

				map.put("hos_id", cif.getHos_id());

				map.put("copy_code", cif.getCopy_code());

				map.put("dept_id", cif.getDept_id());
				
				map.put("dept_no", cif.getDept_no());

				map.put("cost_item_code", cif.getCost_item_code());

				map.put("is_acc", cif.getIs_acc());

				map.put("is_prim_cost", cif.getIs_prim_cost());

				map.put("is_calc_cost", cif.getIs_calc_cost());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiCostitemConfSeqMapper.addCostitemConfSeq(map);

			}
			// 6.aphi_dept_balance_perc_conf_Seq

			List<AphiDeptBalancePercConf> listDeptBalancePercConf = aphiDeptBalancePercConfMapper.queryDeptBalancePercConf(entityMap);

			for (int i = 0; i < listDeptBalancePercConf.size(); i++) {

				AphiDeptBalancePercConf dbpc = listDeptBalancePercConf.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", dbpc.getGroup_id());

				map.put("hos_id", dbpc.getHos_id());

				map.put("copy_code", dbpc.getCopy_code());

				map.put("dept_id", dbpc.getDept_id());
				
				map.put("dept_no", dbpc.getDept_no());

				map.put("dept_percent", dbpc.getDept_percent());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiDeptBalancePercConfSeqMapper.addDeptBalancePercConfSeq(map);

			}

			// 7.aphi_dept_limit_conf_Seq

			List<AphiDeptLimitConf> listDeptLimitConf = aphiDeptLimitConfMapper.queryDeptLimitConf(entityMap);

			for (int i = 0; i < listDeptLimitConf.size(); i++) {

				AphiDeptLimitConf dlc = listDeptLimitConf.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", dlc.getGroup_id());

				map.put("hos_id", dlc.getHos_id());

				map.put("copy_code", dlc.getCopy_code());

				map.put("dept_id", dlc.getDept_id());
				
				map.put("dept_no", dlc.getDept_no());

				map.put("is_limit", dlc.getIs_limit());

				map.put("lower_money", dlc.getLower_money());

				map.put("upper_money", dlc.getUpper_money());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiDeptLimitConfSeqMapper.addDeptLimitConfSeq(map);

			}

			// 8.Aphi_WorkItem_Seq

			List<AphiWorkitem> listWorkitem = aphiWorkitemMapper.queryWorkitem(entityMap);

			for (int i = 0; i < listWorkitem.size(); i++) {

				AphiWorkitem wi = listWorkitem.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", wi.getGroup_id());

				map.put("hos_id", wi.getHos_id());

				map.put("copy_code", wi.getCopy_code());

				map.put("work_item_code", wi.getWork_item_code());
				
				if (wi.getWork_item_name() != null && !"".equals(wi.getWork_item_name())) {
					
					map.put("work_item_name", wi.getWork_item_name());
					
				}else{
					map.put("work_item_name", "");
					
				}

				if (wi.getSpell_code() != null && !"".equals(wi.getSpell_code())) {
					
					map.put("spell_code", wi.getSpell_code());
					
				}else{
					map.put("spell_code", "");
					
				}
				
				if (wi.getWbx_code() != null && !"".equals(wi.getWbx_code())) {
					
					map.put("wbx_code", wi.getWbx_code());
					
				}else{
					map.put("wbx_code", "");
					
				}
				
				if (wi.getData_source() != null && !"".equals(wi.getData_source())) {
					
					map.put("data_source", wi.getData_source());
					
				}else{
					map.put("data_source", "");
					
				}

				if (wi.getIs_stop() != null && !"".equals(wi.getIs_stop())) {
					
					map.put("is_stop", wi.getIs_stop());
					
				}else{
					map.put("is_stop", "0");
					
				}

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiWorkitemSeqMapper.addWorkitemSeq(map);

			}

			// 9.Aphi_WorkItem_Conf_Seq

			List<AphiWorkitemConf> listWorkitemConf = aphiWorkitemConfMapper.queryWorkitemConf(entityMap);

			for (int i = 0; i < listWorkitemConf.size(); i++) {

				AphiWorkitemConf wic = listWorkitemConf.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", wic.getGroup_id());

				map.put("hos_id", wic.getHos_id());

				map.put("copy_code", wic.getCopy_code());

				map.put("dept_id", wic.getDept_id());
				
				map.put("dept_no", wic.getDept_no());

				map.put("work_item_code", wic.getWork_item_code());

				map.put("is_acc", wic.getIs_acc());

				map.put("work_standard", wic.getWork_standard());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiWorkitemConfSeqMapper.addWorkitemConfSeq(map);

			}

			// 10.aphi_item_increase_perc_conf_seq

			List<AphiItemIncreasePercConf> listItemIncreasePercConf = aphiItemIncreasePercConfMapper.queryItemIncreasePercConf(entityMap);

			for (int i = 0; i < listItemIncreasePercConf.size(); i++) {

				AphiItemIncreasePercConf iipc = listItemIncreasePercConf.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", iipc.getGroup_id());

				map.put("hos_id", iipc.getHos_id());

				map.put("copy_code", iipc.getCopy_code());

				map.put("item_code", iipc.getItem_code());

				map.put("increase_percent", iipc.getIncrease_percent());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiItemIncreasePercConfSeqMapper.addItemIncreasePercConfSeq(map);

			}

			// 11.Aphi_IncomeItem_Perc_Seq

			List<AphiIncomeitemPerc> listIncomeitemPerc = aphiIncomeitemPercMapper.queryIncomeitemPerc(entityMap);

			for (int i = 0; i < listIncomeitemPerc.size(); i++) {

				AphiIncomeitemPerc ip = listIncomeitemPerc.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", ip.getGroup_id());

				map.put("hos_id", ip.getHos_id());

				map.put("copy_code", ip.getCopy_code());

				map.put("income_item_code", ip.getIncome_item_code());

				map.put("is_acc", ip.getIs_acc());

				map.put("income_percent", ip.getIncome_percent());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiIncomeitemPercSeqMapper.addIncomeitemPercSeq(map);

			}

			// 12.Aphi_CostItem_Perc_Seq

			List<AphiCostitemPerc> listCostitemPerc = aphiCostitemPercMapper.queryCostitemPerc(entityMap);

			for (int i = 0; i < listCostitemPerc.size(); i++) {

				AphiCostitemPerc cp = listCostitemPerc.get(i);

				Map<String, Object> map = new HashMap();

				map.put("group_id", cp.getGroup_id());

				map.put("hos_id", cp.getHos_id());

				map.put("copy_code", cp.getCopy_code());

				map.put("cost_item_code", cp.getCost_item_code());

				map.put("is_acc", cp.getIs_acc());

				map.put("cost_percent", cp.getCost_percent());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiCostitemPercSeqMapper.addCostitemPercSeq(map);

			}

			// 13.Aphi_Company_Perc_Seq

			List<AphiCompanyPerc> listCompanyPerc = aphiCompanyPercMapper.queryCompanyPerc(entityMap);

			for (int i = 0; i < listCompanyPerc.size(); i++) {

				AphiCompanyPerc cp = listCompanyPerc.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", cp.getGroup_id());

				map.put("hos_id", cp.getHos_id());

				map.put("copy_code", cp.getCopy_code());

				map.put("comp_percent", cp.getComp_percent());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiCompanyPercSeqMapper.addCompanyPercSeq(map);

			}

			// 14.aphi_dept_kind_target_seq

			List<AphiDeptKindTarget> listDeptKindTarget = aphiDeptKindTargetMapper.queryDeptKindTarget(entityMap);

			for (int i = 0; i < listDeptKindTarget.size(); i++) {

				AphiDeptKindTarget dkt = listDeptKindTarget.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", dkt.getGroup_id());

				map.put("hos_id", dkt.getHos_id());

				map.put("copy_code", dkt.getCopy_code());

				map.put("target_code", dkt.getTarget_code());

				map.put("is_acc", dkt.getIs_acc());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiDeptKindTargetSeqMapper.addDeptKindTargetSeq(map);

			}

			// 15.aphi_dept_target_seq

			List<AphiDeptTarget> listDeptTarget = aphiDeptTargetMapper.queryDeptTarget(entityMap);

			for (int i = 0; i < listDeptTarget.size(); i++) {

				AphiDeptTarget dt = listDeptTarget.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", dt.getGroup_id());

				map.put("hos_id", dt.getHos_id());

				map.put("copy_code", dt.getCopy_code());

				map.put("target_code", dt.getTarget_code());

				map.put("dept_kind_code", dt.getDept_kind_code());

				map.put("is_acc", dt.getIs_acc());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiDeptTargetSeqMapper.addDeptTargetSeq(map);

			}

			// 16.aphi_company_scheme_Seq

			List<AphiCompanyScheme> listCompanyScheme = aphiCompanySchemeMapper.queryCompanyScheme(entityMap);

			for (int i = 0; i < listCompanyScheme.size(); i++) {

				AphiCompanyScheme cs = listCompanyScheme.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", cs.getGroup_id());

				map.put("hos_id", cs.getHos_id());

				map.put("copy_code", cs.getCopy_code());

				map.put("item_code", cs.getItem_code());

				map.put("method_code", cs.getMethod_code());
				
				if(cs.getFormula_code() != null && !"".equals(cs.getFormula_code())) {
					
					map.put("formula_code", cs.getFormula_code());
					
				}else{
					
					map.put("formula_code", "");
				}
				
				if(cs.getFun_code() != null && !"".equals(cs.getFun_code())) {
					
					map.put("fun_code", cs.getFun_code());
					
				}else{
					
					map.put("fun_code", "");
				}	

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiCompanySchemeSeqMapper.addCompanySchemeSeq(map);

			}

			// 17.aphi_dept_kind_scheme_seq

			List<AphiDeptKindScheme> listDeptKindScheme = aphiDeptKindSchemeMapper.queryDeptKindScheme(entityMap);

			for (int i = 0; i < listDeptKindScheme.size(); i++) {

				AphiDeptKindScheme dks = listDeptKindScheme.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", dks.getGroup_id());

				map.put("hos_id", dks.getHos_id());

				map.put("copy_code", dks.getCopy_code());

				map.put("dept_kind_code", dks.getDept_kind_code());

				map.put("item_code", dks.getItem_code());

				map.put("method_code", dks.getMethod_code());
				
				if(dks.getFormula_code() != null && !"".equals(dks.getFormula_code())) {
					
					map.put("formula_code", dks.getFormula_code());
					
				}else{
					
					map.put("formula_code", "");
				}	
				
				if(dks.getFun_code() != null && !"".equals(dks.getFun_code())) {
					
					map.put("fun_code", dks.getFun_code());
					
				}else{
					
					map.put("fun_code", "");
				}	

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiDeptKindSchemeSeqMapper.addDeptKindSchemeSeq(map);

			}

			// 18.Aphi_IncomeItem_Point_Seq

			List<AphiIncomeitemPoint> listIncomeitemPoint = aphiIncomeitemPointMapper.queryIncomeitemPoint(entityMap);

			for (int i = 0; i < listIncomeitemPoint.size(); i++) {

				AphiIncomeitemPoint ip = listIncomeitemPoint.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", ip.getGroup_id());

				map.put("hos_id", ip.getHos_id());

				map.put("copy_code", ip.getCopy_code());

				map.put("income_item_code", ip.getIncome_item_code());

				map.put("is_acc", ip.getIs_acc());

				map.put("imcome_point", ip.getImcome_point());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiIncomeitemPointSeqMapper.addIncomeitemPointSeq(map);

			}

			// 19.Aphi_Point_Value_Seq

			List<AphiPointValue> listPointValue = aphiPointValueMapper.queryPointValue(entityMap);

			for (int i = 0; i < listPointValue.size(); i++) {

				AphiPointValue pv = listPointValue.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", pv.getGroup_id());

				map.put("hos_id", pv.getHos_id());

				map.put("copy_code", pv.getCopy_code());

				map.put("point_value", pv.getPoint_value());

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiPointValueSeqMapper.addPointValueSeq(map);

			}

			// 20.aphi_dept_scheme_Seq

			List<AphiDeptScheme> listDeptScheme = aphiDeptSchemeMapper.queryDeptScheme(entityMap);

			for (int i = 0; i < listDeptScheme.size(); i++) {

				AphiDeptScheme ds = listDeptScheme.get(i);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", ds.getGroup_id());

				map.put("hos_id", ds.getHos_id());

				map.put("copy_code", ds.getCopy_code());

				map.put("item_code", ds.getItem_code());

				map.put("dept_id", ds.getDept_id());
				
				map.put("dept_no", ds.getDept_no());

				map.put("method_code", ds.getMethod_code());
				
				if(ds.getFormula_code() != null && !"".equals(ds.getFormula_code())){
					
					map.put("formula_code", ds.getFormula_code());
					
				}else{
					
					map.put("formula_code", "");
				}
				
				if(ds.getFun_code() != null && !"".equals(ds.getFun_code())){
					
					map.put("fun_code", ds.getFun_code());
					
				}else{
					
					map.put("fun_code", "");
				}
				

				map.put("scheme_seq_no", max_scheme_seq_no);

				aphiDeptSchemeSeqMapper.addDeptSchemeSeq(map);

			}

			//如果选择了 审核应用月份 直接设置应用的月份
			if (entityMap.get("year_month") != null && !"".equals(entityMap.get("year_month"))) {

				String year_month = (String) entityMap.get("year_month");

				String acct_year = year_month.substring(0, 4);

				String acct_month = year_month.substring(4, 6);

				entityMap.put("acct_year", acct_year);

				entityMap.put("acct_month", acct_month);

				aphiSchemeConfMapper.deleteSchemeConf(entityMap);

				aphiSchemeConfMapper.addSchemeConf(entityMap);

			}

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {// 如果审核失败 删除已经添加的记录

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"审核失败 数据库异常 请联系管理员! 错误编码  auditHpmSchemeSeq\"}");

		}

	}

	public String querySchemeSeqGrid(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuilder json = new StringBuilder();
		
		List<AphiItem> queryItemDict = aphiItemMapper.qeuryItemData(entityMap);
		
		json.append("[");
		
		for (int i = 0; i < queryItemDict.size(); i++) {
			
			AphiItem item = queryItemDict.get(i);
			
			json.append("{");
			
			json.append("\"id\":\"" + item.getItem_code() + "\"," + "\"text\":" + "\"" 
									+ item.getItem_name() + "\"," + "\"Sort\":" + "\"" 
									+ i + "\"");
			json.append("},");
			
		}
		
		json.setCharAt(json.length() - 1, ']');

		return json.toString();
	}
}
