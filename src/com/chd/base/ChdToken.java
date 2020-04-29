package com.chd.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Title:
 * @Description:
 * 一个用户 相同url 同时提交 相同数据 验证 
 * 
 * @Copyright: Copyright (c) 2017年7月13日 下午4:44:21
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChdToken {
}