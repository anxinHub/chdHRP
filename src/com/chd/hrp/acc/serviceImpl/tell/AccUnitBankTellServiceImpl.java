package com.chd.hrp.acc.serviceImpl.tell;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.tell.AccUnitBankTellMapper;
import com.chd.hrp.acc.entity.AccUnitBankTell;
import com.chd.hrp.acc.service.tell.AccUnitBankTellService;
import com.github.pagehelper.PageInfo;

@Service("accUnitBankTellService")
public class AccUnitBankTellServiceImpl implements AccUnitBankTellService { 
	 
	@Resource(name="accUnitBankTellMapper")
	private AccUnitBankTellMapper accUnitBankTellMapper = null;
	
	/**
	 * @Description 
	 * 单位银行账<BR> 查询AccUnitBankTell
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccUnitBankTell(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		String para = MyConfig.getSysPara("018").toString();
		
		System.out.println("aaa"+para);
		
		List<AccUnitBankTell> list = null;
		
		if (sysPage.getTotal()==-1){
			
			if("0".equals(para)){
				
				 list =  accUnitBankTellMapper.queryAccUnitBankTellByTell(entityMap);
				
			}else if ("1".equals(para)){
				
				 list =  accUnitBankTellMapper.queryAccUnitBankTell(entityMap);
				
			}
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			if("0".equals(para)){
				
				  list =  accUnitBankTellMapper.queryAccUnitBankTellByTell(entityMap,rowBounds);
				
			}else if ("1".equals(para)){
				
				 list =  accUnitBankTellMapper.queryAccUnitBankTell(entityMap,rowBounds);
				 
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}

	}
	
	/**
	 * @Description 
	 * 单位银行账<BR> 打印AccUnitBankTell
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> queryAccUnitBankTellPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		
			entityMap.put("acc_year", entityMap.get("begin_date").toString().substring(0, 4));
			
			entityMap.put("acc_month", entityMap.get("begin_date").toString().substring(5));
		
			List<Map<String, Object>> list =  accUnitBankTellMapper.queryAccUnitBankTellPrint(entityMap);
			
			return list;

	}
}
