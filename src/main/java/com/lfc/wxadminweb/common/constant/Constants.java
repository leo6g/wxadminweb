package com.lfc.wxadminweb.common.constant;


public final class Constants {
	
	/**
	 * 登录不拦截的URL
	 */
	public static final String noFilterUrls[] = {
		"/upload/",
		"/assets/",
		"/weixin",
		"/login",
		"/loginCheck",
		"/servlet/validateCodeServlet",
		"/wx/article/outputArticle",
		"/wx/article/insertArticlePraise",
		"/wx/articleremark/remark",
		"/wx/articleremark/getAll",
		"/wx/user/getByOpenId",
		"/wx/articleremark/insertWXArticleRemark",
		"/biz/advertise",
		"/biz/commonservice",
		"/viewImage",
		"/wx/accesstoken/getToken",
		"/wx/autoresponse/init",
		"/MP_verify"
	};
	


	/**
	 * 系统配置
	 */
	public interface SYSTEM_PROP_CONFIG {
		String REDIS_ADDR_CFG = "REDIS_ADDR_CFG";// Redis IP地址配置，各ip之间以半角逗号","分隔
		String REDIS_CFG_SPLIT = ",";// 分隔符
		String APP_ARRAY_UNION_URL = "APP_ARRAY_UNION_URL";// ecpcore层地址配置		
		String REDIS_MAX_TOTAL = "REDIS_MAX_TOTAL"; // 最大连接数key值，默认8个
		String REDIS_MAX_IDLE = "REDIS_MAX_IDLE"; // 最大空闲连接数key值，默认8个
		String REDIS_MIN_IDLE = "REDIS_MIN_IDLE"; // 最小空闲连接数key值，默认8个
	
	}
	
	/**
	 * 用户session中
	 * @author helei
	 */
	public interface SYSTEM_LOGIN_STATE {
		String STATE_0 = "0"; // 登录成功
		String STATE_1 = "1"; // 验证码错误
		String STATE_N_1 = "-1"; //用户名或密码错误
		String STATE_2 = "2"; // 用户账号已经被冻结
	}
	
	
	/**
	 * session中存储的信息
	 * @author helei
	 */
	public interface USER_SESSION {
		String USER = "USER"; // 用户信息
		String ROLEIDS = "ROLEIDS"; // 角色ID集合
		String PRIVILEGES = "PRIVILEGES"; //权限集合
	}
	
	
	/**
	 * 微信系统配置参数
	 * @author helei
	 */
	public interface WEIXIN_SYSTEM_CONFIG {
		String TOKEN = "weixin"; // 接口配置信息,token
	}
			
}
