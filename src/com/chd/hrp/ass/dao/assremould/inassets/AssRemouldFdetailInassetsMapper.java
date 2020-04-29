/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.assremould.inassets;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldFdetailInassets;
/**
 * 
 * @Description:
 * 050805 资产改造竣工明细表(无形资产)
 * @Table:
 * ASS_REMOULD_F_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRemouldFdetailInassetsMapper extends SqlMapper{

	List<Map<String, Object>> queryByFcordNoMap(Map<String, Object> mapVo);

	Map<String, Object> queryByAssCardNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryByFcordNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryAssRemouldFDetailInassets(Map<String, Object> mapVo);

	int updatechangePriceByCardNo(Map<String, Object> mapVo);

	int deleteBatchByCardNo(List<Map<String, Object>> listVo);

	Map<String, Object> queryByAssCardNoMap(Map<String, Object> mapVo);

	List<Map<String, Object>> queryCardNoByFcordNo(Map<String, Object> mapVo);

	List<AssRemouldFdetailInassets> queryByDisANo(Map<String, Object> mapVo);

	void updateConfirmAssRemouldFInassets(List<Map<String, Object>> listVo);

	
}
