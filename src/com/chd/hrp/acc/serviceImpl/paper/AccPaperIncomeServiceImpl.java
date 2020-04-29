package com.chd.hrp.acc.serviceImpl.paper;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.paper.AccPaperIncomeMapper;
import com.chd.hrp.acc.service.paper.AccPaperIncomeService;
import com.github.pagehelper.PageInfo;

@Service("accPaperIncomeService")
public class AccPaperIncomeServiceImpl implements AccPaperIncomeService {

	private static Logger logger = Logger.getLogger(AccPaperIncomeServiceImpl.class);
	
	@Resource(name = "accPaperIncomeMapper")
	private final AccPaperIncomeMapper accPaperIncomeMapper =null;
	
	@Override
	public List<Map<String,Object>> queryAccPaperIncomeType_code(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeType_code(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryAccPaperIncomeMoney(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeMoney(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryAccPaperIncomeRatename(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeRatename(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryAccPaperIncomeCheckItemNo(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeCheckItemNo(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryAccPaperIncomeCheckTypeId(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeCheckTypeId(mapVo);
	}

	@Override
	public String addPaperIncome(Map<String, Object> mapVo) {
		
		try {
			//查询票据编号是否重复
			int incomeCounts = accPaperIncomeMapper.queryPaperIncomeCounts(mapVo);
			if (incomeCounts != 0) {
				return "{\"error\":\"票据信息重复！.\",\"state\":\"false\"}";
			}
			
			//票据新增
			int incomeCount = accPaperIncomeMapper.addPaperIncome(mapVo);
			if (incomeCount == 0) {
				new SysException();
			}
			
			//判断前台是否传输背书数据
			List<Map> alllistVo = JSONArray.parseArray(String.valueOf(mapVo.get("gridarr")),
					Map.class);
			if (alllistVo == null || alllistVo.size() == 0) {
				return "{\"parsemsg\":\"添加成功！.\",\"state\":\"true\"}";
			}
			
			//新增背书信息(批量)
			int beishuCounts = accPaperIncomeMapper.addBeishu(alllistVo,mapVo);
			if (beishuCounts == 0) {
				new SysException();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"parsemsg\":\"添加成功！.\",\"state\":\"true\"}";
	}

	@Override
	public String queryAccPaperIncome(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accPaperIncomeMapper.queryAccPaperIncome(mapVo);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accPaperIncomeMapper.queryAccPaperIncome(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public Map<String, Object> accPaperIncomeUpdateQuery(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.accPaperIncomeUpdateQuery(mapVo);
	}

	@Override
	public String updatePaperIncome(Map<String, Object> mapVo) {
		
		//验证本条数据是为新建状态
		mapVo.put("state", 1);
		int updateCode = accPaperIncomeMapper.accPaperIncomeUpdateQueryCount(mapVo);
		
		if (updateCode != 1) {
			return "{\"error\":\"修改失败,数据无法修改！.\",\"state\":\"false\"}";
		}
		
		try {
			
			//删除全部背书数据-然后添加修改数据
			int aaa = accPaperIncomeMapper.accPaperIncomeUpdateBeiShuDelete(mapVo);
			
			//修改应收
			int updateCount = accPaperIncomeMapper.updatePaperIncome(mapVo);
			if (updateCode == 0) {
				new SysException();
			}
			
			//判断前台是否传输背书数据
			List<Map> alllistVo = JSONArray.parseArray(String.valueOf(mapVo.get("gridarr")),
					Map.class);
			if (alllistVo == null || alllistVo.size() == 0) {
				return "{\"msg\":\"修改成功！.\",\"state\":\"true\"}";
			}
			
			//新增背书信息(批量)
			int beishuCounts = accPaperIncomeMapper.addBeishu(alllistVo,mapVo);
			if (beishuCounts == 0) {
				new SysException();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
		return "{\"msg\":\"修改成功！.\",\"state\":\"true\"}";
		
	}

	@Override
	public String queryAccPaperIncomeBook(Map<String, Object> mapVo) {
		return ChdJson.toJson(accPaperIncomeMapper.queryAccPaperIncomeBook(mapVo));
	}

	@Override
	public String deleteAccPaperIncome(List<Map> list, Map<String, Object> mapVo) {
		
		try {
			
			//批量验证是否有不可删除的数据,有侧剔除
			mapVo.put("state", 1);
			List<Map> list2 = accPaperIncomeMapper.queryPaperIncomeCountState(list,mapVo);
			
			if (list2.size() > 0) {
				
				//删除背书信息
				accPaperIncomeMapper.deleteAccPaperIncomeBeiShu(list2,mapVo);
				
				accPaperIncomeMapper.deleteAccPaperIncome(list2, mapVo);
				
			}else {
				return "{\"error\":\"删除失败！数据无法删除.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"msg\":\"删除成功！.\",\"state\":\"true\"}";
	}

	@Override
	public String updateAuditAccPaperIncome(List<Map> list, Map<String, Object> mapVo) {
		
		try {
			
			//批量验证是否有不可审核的数据,有侧剔除
			mapVo.put("state", 1);
			list = accPaperIncomeMapper.queryPaperIncomeCountState(list,mapVo);
			
			if (list.size() > 0) {
				int in =  accPaperIncomeMapper.updateAuditAccPaperIncome(list, mapVo);
				if(in == 0){
					new SysException();
				}
			}else {
				return "{\"error\":\"审核失败！数据不能审核.\",\"state\":\"false\"}";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"msg\":\"审核成功！.\",\"state\":\"true\"}";
	}

	@Override
	public String updateCancelAccPaperIncome(List<Map> list, Map<String, Object> mapVo) {
		
		try {
			
			//批量验证是否有不可取消审核的数据,有侧剔除
			mapVo.put("state", 2);
			list = accPaperIncomeMapper.queryPaperIncomeCountState(list,mapVo);
			
			if (list.size() > 0) {
				int in = accPaperIncomeMapper.updateCancelAccPaperIncome(list, mapVo);
				if(in == 0){
					new SysException();
				}
			}else {
				return "{\"error\":\"取消审核失败！数据不能取消审核.\",\"state\":\"false\"}";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"msg\":\"取消审核成功！.\",\"state\":\"true\"}";
	}

	@Override
	public String updateZuofeiAccPaperIncome(List<Map> list, Map<String, Object> mapVo) {
		
		try {
			
			//票据作废修改项
			int zuofeiCount = accPaperIncomeMapper.updateZuofeiAccPaperIncome(list, mapVo);
			if(zuofeiCount == 0){
				new SysException();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"msg\":\"作废成功！.\",\"state\":\"true\"}";
	}

	@Override
	public String updateRefundAccPaperIncome(Map<String, Object> mapVo) {
		
		try {
			
			mapVo.put("state", 2);
			int refundCount = accPaperIncomeMapper.accPaperIncomeUpdateQueryCount(mapVo);
			if (refundCount == 0) {
				return "{\"error\":\"退票失败,此票据已经不能退票了！.\",\"state\":\"false\"}";
			}
			//票据退票
			int in = accPaperIncomeMapper.updateRefundAccPaperIncome(mapVo);
			if(in == 0){
				new SysException();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"msg\":\"退票成功！.\",\"state\":\"true\"}";
	}

	@Override
	public String updatePutAccPaperIncome(Map<String, Object> mapVo) {
		
		try {
			
			mapVo.put("state", 2);
			int refundCount = accPaperIncomeMapper.accPaperIncomeUpdateQueryCount(mapVo);
			if (refundCount == 0) {
				return "{\"error\":\"收款失败,此票不能收款了！.\",\"state\":\"false\"}";
			}
			//票据退票
			int in = accPaperIncomeMapper.updatePutAccPaperIncome(mapVo);
			if(in == 0){
				new SysException();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"msg\":\"收款成功！.\",\"state\":\"true\"}";
	}

	@Override
	public List<Map<String,Object>> queryAccPaperIncomeOpCreateuser(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeOpCreateuser(mapVo);
	}

	@Override
	public List<Map<String,Object>> queryAccPaperIncomeOpAudituser(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeOpAudituser(mapVo);
	}

	@Override
	public String deletePaperIncomeBeishu(Map<String,Object> mapVo) {
		
		try {
			
			List<Map> list = JSONArray.parseArray(String.valueOf(mapVo.get("arrid")),
					Map.class);
			
			int in = accPaperIncomeMapper.deletePaperIncomeBeishu(list,mapVo);
			if(in == 0){
				new SysException();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		return "{\"msg\":\"删除成功！.\",\"state\":\"true\"}";
	}

	@Override
	public List<Map<String, Object>> queryAccPaperIncomeCheckNo(Map<String, Object> mapVo) {
		return accPaperIncomeMapper.queryAccPaperIncomeCheckNo(mapVo);
	}

}
