package com.chd.base.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    /**
    * 根据DatasourceHolder中DataType的值获取具体的数据源
    */
    @Override
    protected Object determineCurrentLookupKey() {
        return DatasourceHolder.getDataSource();
    }
}
