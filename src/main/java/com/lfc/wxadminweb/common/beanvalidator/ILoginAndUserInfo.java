package com.lfc.wxadminweb.common.beanvalidator;

import javax.validation.GroupSequence;

/**
 * 登录，添加用户信息参数校验的组验证标识
 * @author yaohaohao
 *
 */
@GroupSequence( { ILoginCheck.class,IUserInfoCheck.class})
public interface ILoginAndUserInfo {

}
