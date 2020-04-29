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
import com.chd.hrp.mat.entity.MatLocationDict;
/**
 * 
 * @Description:
 * 货位性质 LOCATION_NATURE
1、固定货位
2、自由货位

货位控制方式：
1、不控制
2、提示控制
3、强制控制（入库不可以修改货位）
 * @Table:
 * MAT_LOCATION_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatLocationDictMapper extends SqlMapper{
	
	
}
