var ioc = {
		conf : {
			type : "org.nutz.ioc.impl.PropertiesProxy",
			fields : {
				paths : ["system.properties"]
			}
		},
	    dataSource : {
	        type : "com.alibaba.druid.pool.DruidDataSource",
	        events : {
	        	create : "init",
	            depose : 'close'
	        },
	        fields : {
	            url : {java:"$conf.get('url')"},
	            username : {java:"$conf.get('username')"},
	            password : {java:"$conf.get('password')"},
	            testWhileIdle : true,
	            validationQuery : {java:"$conf.get('validationQuery')"},
	            maxActive : 100,
	            filters : "mergeStat",
	            connectionProperties : "druid.stat.slowSqlMillis=2000",
	            defaultAutoCommit : false
	        }
	    },
		
        /*定义NutDao*/
        dao : {
            type : "org.nutz.dao.impl.NutDao",
            args : [{refer:"dataSource"}]
        },
        dataSourceDYSMS : {
	        type : "com.alibaba.druid.pool.DruidDataSource",
	        events : {
	        	create : "init",
	            depose : 'close'
	        },
	        fields : {
	            url : {java:"$conf.get('dysms.url')"},
	            username : {java:"$conf.get('dysms.username')"},
	            password : {java:"$conf.get('dysms.password')"},
	            testWhileIdle : true,
	            validationQuery : {java:"$conf.get('dysms.validationQuery')"},
	            maxActive : 100,
	            filters : "mergeStat",
	            connectionProperties : "druid.stat.slowSqlMillis=2000",
	            defaultAutoCommit : false
	        }
	    },
		
        /*定义NutDao*/
	    daoDYSMS : {
            type : "org.nutz.dao.impl.NutDao",
            args : [{refer:"dataSourceDYSMS"}]
        },
        dataSourceH2 : {
	        type : "com.alibaba.druid.pool.DruidDataSource",
	        events : {
	        	create : "init",
	            depose : 'close'
	        },
	        fields : {
	            url : {java:"com.chd.base.util.StringTool.getJdbcUrlToH2('db/h2/h2_db')"},
	            username : "sa",
	            password : "sa",
	            testWhileIdle : true,
	            validationQuery : "select 1 ",
	            maxActive : 100,
	            filters : "mergeStat",
	            connectionProperties : "druid.stat.slowSqlMillis=2000",
	            defaultAutoCommit : false
	        }
	    },
		daoH2 : {
			type : "org.nutz.dao.impl.NutDao",
			args : [{refer:"dataSourceH2"}]
		},
};