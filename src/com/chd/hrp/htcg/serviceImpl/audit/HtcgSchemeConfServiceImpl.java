package com.chd.hrp.htcg.serviceImpl.audit;

import java.util.ArrayList;
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
import com.chd.hrp.htcg.dao.audit.HtcgSchemeConfMapper;
import com.chd.hrp.htcg.entity.audit.HtcgSchemeConf;
import com.chd.hrp.htcg.service.audit.HtcgSchemeConfService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcgSchemeConfService")
public class HtcgSchemeConfServiceImpl implements HtcgSchemeConfService {

	private static Logger logger = Logger.getLogger(HtcgSchemeConfServiceImpl.class);

	@Resource(name = "htcgSchemeConfMapper")
	private final HtcgSchemeConfMapper htcgSchemeConfMapper = null;

	@Override
	public String initHtcgSchemeConf(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			List<Map<String, Object>> addlist= new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> list=htcgSchemeConfMapper.queryHtcgSchemeConfPeriod(entityMap);
			if(list.size() > 0){
				 for (Map<String, Object> map : list) {
					 Map<String, Object> htcgSchemeConfMap = new HashMap<String, Object>();
					 htcgSchemeConfMap.put("group_id", map.get("group_id"));
					 htcgSchemeConfMap.put("hos_id", map.get("hos_id"));
					 htcgSchemeConfMap.put("copy_code", map.get("copy_code"));
					 htcgSchemeConfMap.put("scheme_seq_no", map.get("scheme_seq_no"));
					 htcgSchemeConfMap.put("scheme_code", map.get("scheme_code"));
					 htcgSchemeConfMap.put("period_type_code", map.get("period_type_code"));
					 htcgSchemeConfMap.put("acc_year", map.get("acc_year"));
					 htcgSchemeConfMap.put("acc_month", map.get("acc_month"));
					 addlist.add(htcgSchemeConfMap);
				}
				 htcgSchemeConfMapper.deleteHtcgSchemeConf(entityMap);
				 htcgSchemeConfMapper.addBatchHtcgSchemeConf(addlist);
			}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"生成失败\"}");
		}
	}

	@Override
	public String queryHtcgSchemeConf(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal() == -1) {
				
				List<HtcgSchemeConf> list = htcgSchemeConfMapper.queryHtcgSchemeConf(entityMap);
				
				return ChdJson.toJson(list);
			} else {
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
				
				List<HtcgSchemeConf> list = htcgSchemeConfMapper.queryHtcgSchemeConf(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());

			}
	}

	@Override
	public String deleteBatchHtcgSchemeConf(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			htcgSchemeConfMapper.deleteBatchHtcgSchemeConf(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	
}
