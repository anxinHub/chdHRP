package com.chd.hrp.med.serviceImpl.info.fim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.info.fim.MedFimTypeMapper;
import com.chd.hrp.med.entity.MedFimType;
import com.chd.hrp.med.service.info.fim.MedFimTypeService;


@Service("medFimTypeService")
public class MedFimTypeServiceImpl implements MedFimTypeService {
	
	@Resource(name = "medFimTypeMapper")
	private final MedFimTypeMapper medFimTypeMapper = null;
	private static Logger logger = Logger.getLogger(MedFimTypeServiceImpl.class);
	@Override
	public String queryMedBillMain(Map<String, Object> entityMap) {
		List<Map<String,Object>> list= medFimTypeMapper.queryMedFimTypeMain(entityMap);
		
		return  ChdJson.toJson(list);
	}
	@Override
	public String add(Map<String, Object> mapVo) {
		//根据gup_id,hos_id,copy_code,med_fim_code,查询是否存在重复数据
		MedFimType medfimtype = queryByCode(mapVo);
		if (medfimtype != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			int state = medFimTypeMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 MedFimTypeMapper add\"}";

		}
	}
	/**
	 * 获取药品材料数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medFimTypeMapper.queryByCode(entityMap);
	}
	@Override
	public MedFimType updatepage(Map<String, Object> mapVo) {
		return medFimTypeMapper.queryByCode(mapVo);
	}
	@Override
	public String update(Map<String, Object> mapVo) {

		try {
			
		  int state = medFimTypeMapper.update(mapVo);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedfimType\"}";

		}	
		 
	}
	@Override
	public String deleteBatch(List<Map<String, Object>> listVo) {
		try {
			//  查看要删除的药品财务分类在药品分类中是否存在
			for(Map<String,Object> item : listVo){
				
				int state = medFimTypeMapper.queryMedFimTypeIsExists(item);
				if(state >0 ){
					return "{\"error\":\"删除失败，选择的药品财务类别正在使用！\",\"state\":\"false\"}";
				}
			}
			medFimTypeMapper.deleteBatch(listVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	

}
