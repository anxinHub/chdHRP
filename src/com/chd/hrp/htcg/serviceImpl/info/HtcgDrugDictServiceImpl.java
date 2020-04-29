/*
 *
 */package com.chd.hrp.htcg.serviceImpl.info;

import java.util.ArrayList;
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
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.htcg.dao.info.HtcgDrugDictMapper;
import com.chd.hrp.htcg.dao.info.HtcgDrugTypeDictMapper;
import com.chd.hrp.htcg.entity.info.HtcgDrugDict;
import com.chd.hrp.htcg.entity.info.HtcgDrugTypeDict;
import com.chd.hrp.htcg.service.info.HtcgDrugDictService;
import com.chd.hrp.sys.dao.FacDictMapper;
import com.chd.hrp.sys.entity.FacDict;
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

@Service("htcgDrugDictService")
public class HtcgDrugDictServiceImpl implements HtcgDrugDictService {

	private static Logger logger = Logger
			.getLogger(HtcgDrugDictServiceImpl.class);

	@Resource(name = "htcgDrugDictMapper")
	private final HtcgDrugDictMapper htcgDrugDictMapper = null;
	
	@Resource(name = "htcgDrugTypeDictMapper")
	private final HtcgDrugTypeDictMapper htcgDrugTypeDictMapper = null;
	
	@Resource(name = "facDictMapper")
	private final FacDictMapper facDictMapper = null;

	@Override
	public String addHtcgDrugDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			HtcgDrugDict htcgDrugDict = htcgDrugDictMapper.queryHtcgDrugDictByCode(entityMap);
			
			if(null != htcgDrugDict){
				
				return "{\"error\":\"药品编码重复!.\",\"state\":\"false\"}";
			}
			
			htcgDrugDictMapper.addHtcgDrugDict(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgDrugDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgDrugDictMapper.addBatchHtcgDrugDict(list);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgDrugDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgDrugDict> list = htcgDrugDictMapper
					.queryHtcgDrugDict(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcgDrugDict> list = htcgDrugDictMapper.queryHtcgDrugDict(
					entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgDrugDict queryHtcgDrugDictByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgDrugDictMapper.queryHtcgDrugDictByCode(entityMap);
	}

	@Override
	public String deleteHtcgDrugDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgDrugDictMapper.deleteHtcgDrugDict(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgDrugDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgDrugDictMapper.deleteBatchHtcgDrugDict(list);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgDrugDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgDrugDictMapper.updateHtcgDrugDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String importHtcgDrugDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			 List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
	       	  
        	 List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
        	 
        	 if (list.size() == 0 || list == null) {
  				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
  			 }
        	 
        	 for (Map<String, List<String>> map : list) {
        		 
                 Map<String, Object> mapVo = new HashMap<String, Object>();
         		 
         		 mapVo.put("group_id", SessionManager.getGroupId());
  				
  				 mapVo.put("hos_id", SessionManager.getHosId());
  				
  				 mapVo.put("copy_code", SessionManager.getCopyCode());
  				 
  				 mapVo.put("drug_code", map.get("drug_code").get(1));
  				 
  				 mapVo.put("drug_name", map.get("drug_name").get(1));
  				 
  				 mapVo.put("drug_type_code", map.get("drug_type_code").get(1));
  				 
  				 mapVo.put("drug_type_name", map.get("drug_type_name").get(1));
  				
  				 mapVo.put("mode_code", map.get("mode_code").get(1));
  				
  				 mapVo.put("unit_code", map.get("unit_code").get(1));
  				
  				 mapVo.put("price", map.get("price").get(1));
  				 
  				 mapVo.put("fac_code", map.get("fac_code").get(1));
   				
  				 mapVo.put("fac_name", map.get("fac_name").get(1));
  				 
  				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drug_name").toString()));
  				
  				 mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drug_name").toString()));
  				 
  				 mapVo.put("is_stop", 0);
  				 
  				 /**
  				  * 判断药品类别是否存在
  				  */
  				 HtcgDrugTypeDict htcgDrugTypeDict = htcgDrugTypeDictMapper.queryHtcgDrugTypeDictByCode(mapVo);
  				 
  				 if(null == htcgDrugTypeDict){
  					 
  					return "{\"error\":\""+ map.get("drug_type_code").get(0)+"\t\t药品编码\t"+map.get("drug_type_code").get(1)+"\t不存在请重新输入!\",\"state\":\"false\"}";
  				 }
  				 
  				 /**
  				  * 查询生产厂商是否存在,如果存在取生产厂商ID,NO
  				  */
  				Map<String, Object> facMapVo = new HashMap<String, Object>();
  				facMapVo.put("group_id", mapVo.get("group_id"));
  				facMapVo.put("hos_id", mapVo.get("hos_id"));
  				facMapVo.put("copy_code", mapVo.get("copy_code"));
  				facMapVo.put("fac_code", mapVo.get("fac_code"));
  				facMapVo.put("is_stop","0");
  				FacDict facDict = facDictMapper.queryFacDictByCode(facMapVo);
  				
  				if(null == facDict){
 					 
  					return "{\"error\":\""+ map.get("fac_code").get(0)+"\t\t生产厂商编码\t"+map.get("fac_code").get(1)+"\t不存在请重新输入!\",\"state\":\"false\"}";
  				}else {
  					
  					mapVo.put("fac_id", facDict.getFac_id());
  					mapVo.put("fac_no", facDict.getFac_no());
  				}
  				
  				
  				
  				HtcgDrugDict htcgDrugDict = htcgDrugDictMapper.queryHtcgDrugDictByCode(mapVo);
  				
  				if(null != htcgDrugDict){
  					
  					return "{\"error\":\""+ map.get("drug_code").get(0)+"\t\t药品编码:"+map.get("drug_code").get(1)+" 重复.\",\"state\":\"false\"}";
  				}
  				
  				resultSet.add(mapVo);
        	 }

        	 if(resultSet.size() > 0 ){
        		 htcgDrugDictMapper.addBatchHtcgDrugDict(resultSet);
        	 }
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"导入失败\"}");
		}
	}

}
