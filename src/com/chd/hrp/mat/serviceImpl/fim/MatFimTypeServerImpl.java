package com.chd.hrp.mat.serviceImpl.fim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.fim.MatFimTypeMapper;
import com.chd.hrp.mat.entity.MatFimType;
import com.chd.hrp.mat.entity.MatLocationType;
import com.chd.hrp.mat.service.fim.MatFimTypeServer;
import com.chd.hrp.mat.serviceImpl.info.basic.MatLocationTypeServiceImpl;

@Service("matfimtypeserver")
public class MatFimTypeServerImpl implements MatFimTypeServer {
	
	@Resource(name = "matFimTypeMapper")
	private final MatFimTypeMapper matFimTypeMapper = null;
	private static Logger logger = Logger.getLogger(MatLocationTypeServiceImpl.class);
	@Override
	public String queryMatBillMain(Map<String, Object> entityMap) {
		List<Map<String,Object>> list= matFimTypeMapper.queryMatFimTypelMain(entityMap);
		
		return  ChdJson.toJson(list);
	}
	@Override
	public String add(Map<String, Object> mapVo) {
		//根据gup_id,hos_id,copy_code,mat_fim_code,查询是否存在重复数据
		MatFimType matfimtype = queryByCode(mapVo);
		if (matfimtype != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			int state = matFimTypeMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 MatFimTypeMapper add\"}";

		}
	}
	/**
	 * 获取物资材料数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matFimTypeMapper.queryByCode(entityMap);
	}
	@Override
	public MatFimType updatepage(Map<String, Object> mapVo) {
		return matFimTypeMapper.queryByCode(mapVo);
	}
	@Override
	public String update(Map<String, Object> mapVo) {

		try {
			
		  int state = matFimTypeMapper.update(mapVo);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatfimType\"}";

		}	
		 
	}
	@Override
	public String deleteBatch(List<Map<String, Object>> listVo) {
		try {
			//  查看要删除的物资财务分类在物资分类中是否存在
			for(Map<String,Object> item : listVo){
				
				int state = matFimTypeMapper.queryMatFimTypeIsExists(item);
				if(state >0 ){
					return "{\"error\":\"删除失败，选择的物资财务类别正在使用！\",\"state\":\"false\"}";
				}
			}
			matFimTypeMapper.deleteBatch(listVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatLocationType\"}";

		}	
	}
	

}
