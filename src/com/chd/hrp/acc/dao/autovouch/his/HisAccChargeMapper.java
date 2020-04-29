/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch.his;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;

public interface HisAccChargeMapper extends SqlMapper {

	//查询凭证模板
	public List<HrpAccSelect> queryAutoBusiTemplate(Map<String, Object> map)throws DataAccessException;
	
	/**
	 * 查询门诊收费类别-表头
	 * */
	public List<Map<String, Object>> queryHisChargeHeadO(Map<String, Object> map) throws DataAccessException;

	/**
	 * 查询门诊收费数据
	 * */
	public List<Map<String, Object>> queryHisChargeDataO(Map<String, Object> map) throws DataAccessException;
	

	//查询门诊结算数据 
	public List<Map<String, Object>> queryHisBalO(Map<String, Object> map) throws DataAccessException;
	
	//判断是否生成凭证，自动凭证通用
	public List<String> queryAutoVouchIsCreate(Map<String, Object> map) throws DataAccessException;
	
	
	//查询门诊结算凭证数据  meta=0101
	public List<Map<String, Object>> queryHisBalVouchO_0101(Map<String, Object> map) throws DataAccessException;

	//查询门诊结算凭证数据  meta=0109
	public List<Map<String, Object>> queryHisBalVouchO_0109(Map<String, Object> map) throws DataAccessException;

	
	
	//查询门诊收费凭证数据 meta=0104
	public List<Map<String, Object>> queryHisChargeVouchO_0104(Map<String, Object> map) throws DataAccessException;
	
	//查询门诊收费凭证科室辅助核算数据 meta=0104
	public List<Map<String, Object>> queryHisChargeVouchCheckO_0104(Map<String, Object> map) throws DataAccessException;
	
	//现金/银行科目 现金流量标注默认标101 meta=0101
	public Map<String, Object> queryHisChargeVouchCashO_0101(Map<String, Object> map) throws DataAccessException;	

	// 查询门诊预交金凭证数据 meta=0102 
	public List<Map<String, Object>> queryHisPreVouchO_0102(Map<String, Object> map) throws DataAccessException;
	
	//查询住院收费类别-表头
	public List<Map<String, Object>> queryHisChargeHeadI(Map<String, Object> map) throws DataAccessException;
	
	
	//查询住院收费数据
	public List<Map<String, Object>> queryHisChargeDataI(Map<String, Object> map) throws DataAccessException;
	
	
	//生成凭证取最小、最大收费日期，用来存日志表
	public Map<String, String> queryHisChargeDataIMinMaxDate(Map<String, Object> map) throws DataAccessException;
	
	//查询住院结算数据
	public List<Map<String, Object>> queryHisBalI(Map<String, Object> map) throws DataAccessException;
	
	
	//查询住院收费凭证数据 应收在院病人医疗款 meta=0107
	public List<Map<String, Object>> queryHisChargeI_0107(Map<String, Object> map) throws DataAccessException;
	
	//查询住院收费凭证数据 应收在院病人医疗款 meta=0108
	public List<Map<String, Object>> queryHisChargeI_0108(Map<String, Object> map) throws DataAccessException;
	
	//查询住院收费凭证-科室辅助核算数据 meta=0108
	public List<Map<String, Object>> queryHisChargeVouchCheckI_0108(Map<String, Object> map) throws DataAccessException;
	
	//查询住院结算凭证 meta=0101
	public List<Map<String, Object>> queryHisBalVouchI_0101(Map<String, Object> map) throws DataAccessException;
	
	//查询住院结算凭证 meta=0109
	public List<Map<String, Object>> queryHisBalVouchI_0109(Map<String, Object> map) throws DataAccessException;
	
	
	//现金/银行科目 现金流量标注默认标101 meta=0101
	public List<Map<String, Object>> queryHisChargeVouchCashI_0101(Map<String, Object> map) throws DataAccessException;
	
	
	//查询住院预交金凭证数据 meta=0105
	public List<Map<String, Object>> queryHisPreVouchI_0105(Map<String, Object> map) throws DataAccessException;
	
	//判断住院收费是否生成凭证
	public List<String> queryAutoVouchIsCreateByChargeI(Map<String, Object> map) throws DataAccessException;
	
	//收入凭证，查询自动核销的票据类型
	public List<String> queryAccPaperTypeByAuto(Map<String, Object> map)throws DataAccessException;
	
	
	// 门诊结算凭证，按票据号生成辅助核算 
	public List<Map<String, Object>> queryHisChargeVouchPaperCheckO(Map<String, Object> map)throws DataAccessException;
	
	// 住院结算凭证，按票据号生成辅助核算 
	public List<Map<String, Object>> queryHisChargeVouchPaperCheckI(Map<String, Object> map)throws DataAccessException;

	public int saveAccAutoCheckI_0108(Map<String, Object> detailMap);

	public int saveAutoVouchLogTemp(Map<String, Object> map);

	public int saveAccAutoCash(Map<String, Object> detailMap);

	public int saveAutoVouchLogOutpatient(Map<String, Object> map);

	public List<Map<String, Object>> queryVouchNo(Map<String, Object> mapVo);

	public int saveHisChargeVouchCashO_0101(Map<String, Object> detailMap);

	public int saveHisChargeVouchPaperCheckO(Map<String, Object> detailMap);

	public int saveHisChargeVouchPaperCashO(Map<String, Object> detailMap);

	public int saveHisChargeVouchCheckO_0104(Map<String, Object> map);

	public int saveHisChargeVouchPaperCheckI(Map<String, Object> detailMap);

	public int saveHisChargeVouchPaperCashI(Map<String, Object> detailMap);

	public int saveHisChargeVouchCheckI_0108(Map<String, Object> detailMap);
}
