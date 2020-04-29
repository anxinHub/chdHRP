package com.chd.hrp.htcg.serviceImpl.audit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.htcg.dao.audit.HtcgSchemeDrgsCheckMapper;
import com.chd.hrp.htcg.entity.audit.HtcgSchemeDrgsCheck;
import com.chd.hrp.htcg.service.audit.HtcgSchemeDrgsCheckService;
import com.github.pagehelper.PageInfo;

@Service("htcgSchemeDrgsCheckService")
public class HtcgSchemeDrgsCheckServiceImpl implements HtcgSchemeDrgsCheckService {

	private static Logger logger = Logger.getLogger(HtcgSchemeDrgsCheckServiceImpl.class);
	@Resource(name = "htcgSchemeDrgsCheckMapper")
	private final HtcgSchemeDrgsCheckMapper htcgSchemeDrgsCheckMapper = null;

	@Override
	public String queryHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeDrgsCheck> list = htcgSchemeDrgsCheckMapper.queryHtcgSchemeDrgsCheck(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeDrgsCheck> list = htcgSchemeDrgsCheckMapper.queryHtcgSchemeDrgsCheck(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String auditHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// TODO Auto-generated method stub
			Integer v_scheme_seq_no = 0;
			
			Integer flag = Integer.parseInt(entityMap.get("seq_max_flag").toString());
			
			Integer scheme_seq_no = htcgSchemeDrgsCheckMapper.queryHtcgSeqTableMaxSeqNo(entityMap);
			
			if(flag == 1){
				v_scheme_seq_no = scheme_seq_no;
			}else if(flag == 0){
				v_scheme_seq_no = scheme_seq_no + 1;
				Map<String, Object> htcgSeqTableMap = new HashMap<String, Object>();
				htcgSeqTableMap.put("group_id", entityMap.get("group_id"));
				htcgSeqTableMap.put("hos_id", entityMap.get("hos_id"));
				htcgSeqTableMap.put("copy_code", entityMap.get("copy_code"));
				htcgSeqTableMap.put("scheme_code", entityMap.get("scheme_code"));
				htcgSeqTableMap.put("scheme_seq_no", v_scheme_seq_no);
				htcgSeqTableMap.put("scheme_note", "");
				htcgSchemeDrgsCheckMapper.addHtcgSeqTable(htcgSeqTableMap);
			}
			
			Map<String, Object> schemeConfirmSeqMap = new HashMap<String, Object>();
			schemeConfirmSeqMap.put("group_id", entityMap.get("group_id"));
			schemeConfirmSeqMap.put("hos_id", entityMap.get("hos_id"));
			schemeConfirmSeqMap.put("copy_code", entityMap.get("copy_code"));
			schemeConfirmSeqMap.put("scheme_seq_no", v_scheme_seq_no);
			schemeConfirmSeqMap.put("scheme_code", entityMap.get("scheme_code"));
			htcgSchemeDrgsCheckMapper.addbatchHtcgSchemeConfirmSeq(schemeConfirmSeqMap);
			
			/*
			 *判断方案审核是否存在(存在修改,不存在添加)
			 * */
			Map<String, Object> auditMap = new HashMap<String, Object>();
			auditMap.put("group_id", entityMap.get("group_id"));
			auditMap.put("hos_id", entityMap.get("hos_id"));
			auditMap.put("copy_code", entityMap.get("copy_code"));
			auditMap.put("scheme_code", entityMap.get("scheme_code"));
			auditMap.put("is_confirm", "1");
			
			Map<String, Object> htcgSchemeDrgsCheck = htcgSchemeDrgsCheckMapper.queryHtcgSchemeConfirmByCode(auditMap);
			
			if(null == htcgSchemeDrgsCheck){
				
				htcgSchemeDrgsCheckMapper.addHtcgSchemeConfirm(auditMap);
				
			}else{
				
				htcgSchemeDrgsCheckMapper.updateHtcgSchemeDrgsCheck(auditMap);
			}
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"审核失败\"}");
		}
	}
	
	@Override
	public String reAuditHtcgSchemeDrgsCheck(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// TODO Auto-generated method stub
			Map<String, Object> reAuditMap = new HashMap<String, Object>();
			reAuditMap.put("group_id", entityMap.get("group_id"));
			reAuditMap.put("hos_id", entityMap.get("hos_id"));
			reAuditMap.put("copy_code", entityMap.get("copy_code"));
			reAuditMap.put("scheme_code", entityMap.get("scheme_code"));
			reAuditMap.put("is_confirm", "0");
			Map<String, Object> htcgSchemeDrgsCheck = htcgSchemeDrgsCheckMapper.queryHtcgSchemeConfirmByCode(reAuditMap);
			if(null == htcgSchemeDrgsCheck){
				return "{\"error\":\"方案未审核.\",\"state\":\"false\"}";
			}
			
			htcgSchemeDrgsCheckMapper.updateHtcgSchemeDrgsCheck(reAuditMap);
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"销审失败\"}");
		}
	}



	@Override
	public String flagHtcgSchemeDrgsCheckDrgsCheck(Map<String, Object> entityMap)
			throws DataAccessException {
		return JsonListMapUtil.listToJson(htcgSchemeDrgsCheckMapper.flagHtcgSchemeDrgsCheckDrgsCheck(entityMap));
	}

	@Override
	public String querySchemeDrgsCheckReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgSchemeDrgsCheck> list = htcgSchemeDrgsCheckMapper.querySchemeDrgsCheckReport(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgSchemeDrgsCheck> list = htcgSchemeDrgsCheckMapper.querySchemeDrgsCheckReport(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
