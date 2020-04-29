package com.chd.hrp.ass.serviceImpl.repair.assmassrepairsche;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.assmassrepairsche.AssMassRepairScheMapper;
import com.chd.hrp.ass.service.repair.assmassrepairsche.AssMassRepairScheService;

@Service("assMassRepairScheService")
public class AssMassRepairScheServiceImpl implements AssMassRepairScheService{
	
	@Resource(name="assMassRepairScheMapper")
	private  final AssMassRepairScheMapper assMassRepairScheMapper = null ;
	
	@Override
	public String queryAssrRepTeamTree(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assMassRepairScheMapper.queryAssrRepTeamTree(mapVo);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> queryAssRepairUserByCode(Map<String, Object> mapVo) throws DataAccessException {
		Map<String,Object> map=assMassRepairScheMapper.queryAssRepairUserByCode(mapVo);
		return map;
	}

	@Override
	public String updateAssRepairUser(Map<String, Object> mapVo) throws DataAccessException {
		
		try {
			assMassRepairScheMapper.updateAssRepairUser(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
	}

	@Override
	public String queryAssRepUser(Map<String, Object> mapVo) throws DataAccessException {
		try {
			List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
			
			list=assMassRepairScheMapper.queryAssRepUser(mapVo);
			if(list.size()==0){
				assMassRepairScheMapper.initRepUser(mapVo);
				
				list=assMassRepairScheMapper.queryAssRepUser(mapVo);
			}
			return ChdJson.toJsonLower(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
	}

	@Override
	public String updateWorkDa(Map<String, Object> mapVo) throws DataAccessException {
		try {
			assMassRepairScheMapper.updateWorkDay(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
	}

	@Override
	public String addRepUser(Map<String, Object> mapVo) throws DataAccessException {
		try {
			Map<String,Object>map=assMassRepairScheMapper.queryAssRepairScheByCode(mapVo);
			if(map!=null){
				return "{\"error\":\"职工已存在.\",\"state\":\"false\"}";
			}
			mapVo.put("sort_code", assMassRepairScheMapper.queryMaxSortCode(mapVo));
			assMassRepairScheMapper.addRepUser(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
	}

	@Override
	public String deleteRepUser(List<Map> listVo) throws DataAccessException  {
		try {
			
			assMassRepairScheMapper.deleteRepUser(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
	}

	@Override
	public String updateWorkDay(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		assMassRepairScheMapper.updateWorkDay(mapVo);
		return "{\"state\":\"true\"}";
	}

	@Override
	public String updateUserSort(List<Map> listVo) {
		
		assMassRepairScheMapper.updateUserSort(listVo);
		return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
	}

}
