package com.chd.base.startup;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
    "*anno", "com.chd.nutz",
    "*tx", // 事务拦截 aop
    "*async"}) // 异步执行aop
@Modules(scanPackage=true)
public class MainModule {
	
}