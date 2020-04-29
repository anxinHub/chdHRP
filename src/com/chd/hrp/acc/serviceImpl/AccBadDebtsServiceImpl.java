package com.chd.hrp.acc.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.search.parser.MCriteria.Simple;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.acc.dao.AccBadDebtsMapper;
import com.chd.hrp.acc.service.AccBadDebtsService;
import com.github.pagehelper.PageInfo;

@Service("accBadDebtsService")
public class AccBadDebtsServiceImpl implements AccBadDebtsService {

	private static Logger logger = Logger.getLogger(AccBadDebtsServiceImpl.class);
	
	@Resource(name = "accBadDebtsMapper")
	private final AccBadDebtsMapper accBadDebtsMapper = null;

	@Override
	public String queryBadDebts(Map<String, Object> mapVo) {
			List<Map<String, Object>> list = accBadDebtsMapper.queryBadDebts(mapVo);
			return ChdJson.toJson(list);
	}

	@Override
	public String addBadDebts(Map<String, Object> mapVo) {
		try {
			
			//全量更新删除全部数据
			accBadDebtsMapper.deleteAddBadDebts();
			
			List<Map> list = JSONArray.parseArray(mapVo.get("parmarr").toString(),Map.class);
			
			int addCount = accBadDebtsMapper.addBadDebts(list);
			if (addCount == 0) {
				throw new SysException("保存失败,请刷新尝试!");
			}
			return "{\"msg\":\"保存成功!\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException();
		}
	}

	@Override
	public List<Map<String, Object>> queryAccSubjSelect(Map<String, Object> mapVo) {
		return accBadDebtsMapper.queryAccSubjSelect(mapVo);
	}

	@Override
	public String importBadDebts(Map<String, Object> mapVo) {
		int addCount = 0;
		try {

			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
			
			List<Map> addList = new ArrayList<Map>();
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if(list != null && list.size() > 0){
				for(Map<String, List<String>> map : list){
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("DEBT_COMP", map.get("DEBT_COMP").get(1));
					saveMap.put("ACC_SUBJ_CODE", map.get("ACC_SUBJ_CODE").get(1));
					saveMap.put("RESP_DEPT", map.get("RESP_DEPT").get(1));
					saveMap.put("DEBT_MONEY", map.get("DEBT_MONEY").get(1));
					saveMap.put("AFF_DATE", map.get("AFF_DATE").get(1));
					saveMap.put("AFF_MONEY", map.get("AFF_MONEY").get(1));
					saveMap.put("REGET_MONEY", map.get("REGET_MONEY").get(1));
					saveMap.put("NOGET_MONEY", map.get("NOGET_MONEY").get(1));
					saveMap.put("AFF_NO", map.get("AFF_NO").get(1));
					saveMap.put("NOTE", map.get("NOTE").get(1));
					
				          Date vouchDate = new Date();
				          BigDecimal bigDecimalA = new BigDecimal(map.get("OCC_DATE").get(1).toString().replace("OADate","").replace("/","").replace("\\","").replace("(","").replace(")",""));
				          bigDecimalA=bigDecimalA.multiply(new BigDecimal(24)).multiply(new BigDecimal(60)).multiply(new BigDecimal(60)).multiply(new BigDecimal(1000));
				          bigDecimalA=bigDecimalA.add(new BigDecimal(sim.parse("1899-12-30").getTime()));
				          vouchDate.setTime(bigDecimalA.longValue());
				          String str = DateUtil.dateToString(vouchDate,"yyyy-MM-dd");
				          saveMap.put("OCC_DATE", str);
				          
				          bigDecimalA = new BigDecimal(map.get("AFF_DATE").get(1).toString().replace("OADate","").replace("/","").replace("\\","").replace("(","").replace(")",""));
				          bigDecimalA=bigDecimalA.multiply(new BigDecimal(24)).multiply(new BigDecimal(60)).multiply(new BigDecimal(60)).multiply(new BigDecimal(1000));
				          bigDecimalA=bigDecimalA.add(new BigDecimal(sim.parse("1899-12-30").getTime()));
				          vouchDate.setTime(bigDecimalA.longValue());
				          String strs = DateUtil.dateToString(vouchDate,"yyyy-MM-dd");
				          saveMap.put("AFF_DATE", strs);
					addList.add(saveMap);
					
				}
				addCount = accBadDebtsMapper.addBadDebtsTo(addList);
				if (addCount == 0) {
					throw new SysException("导入失败,请重试!");
				}
				
			}
			return "{\"meg\":\"共导入 "+addCount+" 条数据!\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "{\"meg\":\"共导入 "+addCount+" 条数据!\"}";
	}

	@Override
	public List<Map<String, Object>> queryBadDebtsPrint(
			Map<String, Object> mapVo) {
		return accBadDebtsMapper.queryBadDebts(mapVo);
	}
	
}
