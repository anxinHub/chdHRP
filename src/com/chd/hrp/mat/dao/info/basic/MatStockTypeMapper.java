
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.dao.info.basic;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatStockType;
/**
 * 
 * @Description:
 * 04118 采购类型
 * @Table:
 * MAT_STOCK_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatStockTypeMapper extends SqlMapper{

	List<MatStockType> queryMatStockTypeDataAll(Map<String, Object> stockTypeMap);
	
	
}
