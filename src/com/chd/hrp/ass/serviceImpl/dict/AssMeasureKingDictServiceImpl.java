package com.chd.hrp.ass.serviceImpl.dict;

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
import com.chd.hrp.ass.dao.dict.AssMeasureKingDictMapper;
import com.chd.hrp.ass.entity.dict.AssMeasureKingDict;
import com.chd.hrp.ass.entity.dict.AssPropDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.service.dict.AssMeasureKingDictService;
import com.github.pagehelper.PageInfo;
@Service("assMeasureKingDictService")
public class AssMeasureKingDictServiceImpl implements AssMeasureKingDictService {
	private static Logger logger = Logger.getLogger(AssMeasureKingDictServiceImpl.class);
	
	//引入DAO操作
		@Resource(name = "assMeasureKingDictMapper")
		private final AssMeasureKingDictMapper assMeasureKingDictMapper = null;

	@Override
	public String addAssMeasureKingDict(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = assMeasureKingDictMapper.addAssMeasureKingDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addBatchAssMeasureKingDict(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			assMeasureKingDictMapper.addBatchAssMeasureKingDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateAssMeasureKingDict(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = assMeasureKingDictMapper.updateAssMeasureKingDict(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}

	@Override
	public String updateBatchAssMeasureKingDict(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			assMeasureKingDictMapper.updateBatchAssMeasureKingDict(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}

	@Override
	public String deleteAssMeasureKingDict(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = assMeasureKingDictMapper.deleteAssMeasureKingDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}

	@Override
	public String deleteBatchAssMeasureKingDict(List<Map<String, Object>> entityMap) throws DataAccessException {
try {
			
	assMeasureKingDictMapper.deleteBatchAssMeasureKingDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryAssMeasureKingDict(Map<String, Object> entityMap) throws DataAccessException {
SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMeasureKingDict> list = assMeasureKingDictMapper.queryAssMeasureKingDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMeasureKingDict> list = assMeasureKingDictMapper.queryAssMeasureKingDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public AssMeasureKingDict queryAssMeasureKingDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assMeasureKingDictMapper.queryAssMeasureKingDictByCode(entityMap);
	}

	@Override
	public String readAssMeasureKingDictFiles(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String assMeasureKingDictImport(Map<String, Object> mapVo) {
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if (list.size()==0 || list==null) {
				return	"{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map : list) {
				if(map.get("measure_king_code").get(1)==null || "".equals(map.get("measure_king_code").get(1))){
				return "{\"msg\":\""+map.get("measure_king_code").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("measure_king_code").get(0)+"\"}";

				}
				if (map.get("measure_king_name").get(1)==null || map.get("measure_king_name").get(1).equals("")){
					return "{\"msg\":\""+map.get("measure_king_name").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("measure_king_name").get(0)+"\"}";
				}
				if (map.get("is_stop").get(1)==null || map.get("is_stop").get(1).equals("")){
					return "{\"msg\":\""+map.get("is_stop").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("is_stop").get(0)+"\"}";
				}
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("measure_king_code", map.get("measure_king_code").get(1));
				mapVo1.put("measure_king_name", map.get("measure_king_name").get(1));
				mapVo1.put("is_stop", map.get("is_stop").get(1));
				
				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("measure_king_code", mapVo1.get("measure_king_code"));

				List<Map<String,Object>> data_exc_extis_code = assMeasureKingDictMapper.queryExistsCode(map_code);

				if (data_exc_extis_code.size() > 0) {
					return "{\"warn\":\"" + map.get("prop_code").get(0) + ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+  map.get("prop_code").get(0) + "\"}";

				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("measure_king_name", mapVo1.get("measure_king_name"));

				List<Map<String,Object>> data_exc_extis_name = assMeasureKingDictMapper.queryExistsName(map_name);

				if (data_exc_extis_name.size() > 0) {

					return "{\"warn\":\"" +  map.get("measure_king_name").get(0) + ",名称已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+ map.get("measure_king_name").get(0) + "\"}";

				}
				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("prop_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("prop_name").toString()));
				
				assMeasureKingDictMapper.addAssMeasureKingDict(mapVo1);
			}
			return"{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			throw new SysException(e);
		}
	}

	@Override
	public AssMeasureKingDict queryByName(Map<String, Object> entityMap) throws DataAccessException {
		return assMeasureKingDictMapper.queryByName(entityMap);
	}

}
