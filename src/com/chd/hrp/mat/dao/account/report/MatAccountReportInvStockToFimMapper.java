/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.account.report;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 材料库存汇总表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAccountReportInvStockToFimMapper extends SqlMapper{

	public List<Map<String, Object>>  queryMatAccountReportInvStockToFim(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryMatAccountReportInvStockToFimColumns(Map<String, Object> mapVo);

	public void collectMatStoreInvStock(Map<String, Object> mapVo);
	/**
	 * 在begin_year@begin_month 和 end_year@end_month之间结账的月份数
	 * @param mapVo
	 * @return
	 */
	public int queryIsEntireAccByYearMonth(Map<String, Object> mapVo);
	/**
	 * 查询某仓库 在begin_year@begin_month 和 end_year@end_month之间结账的月份数
	 * @param mapVo
	 * @return
	 */
	public int queryIsAccByStore(Map<String, Object> mapVo);
	/**
	 * 查询虚仓对应的所有实仓  在begin_year@begin_month 和 end_year@end_month之间结账的月份数
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryIsAccBySet(Map<String, Object> mapVo);
	
}
