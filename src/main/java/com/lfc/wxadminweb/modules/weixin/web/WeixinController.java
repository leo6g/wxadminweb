package com.lfc.wxadminweb.modules.weixin.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.github.sd4324530.fastweixin.api.MessageAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.GetSendMessageResponse;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.ImageMsg;
import com.github.sd4324530.fastweixin.message.MpNewsMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.VideoMsg;
import com.github.sd4324530.fastweixin.message.VoiceMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.ImageReqMsg;
import com.github.sd4324530.fastweixin.message.req.LinkReqMsg;
import com.github.sd4324530.fastweixin.message.req.LocationReqMsg;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.QrCodeEvent;
import com.github.sd4324530.fastweixin.message.req.ScanCodeEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.message.req.VideoReqMsg;
import com.github.sd4324530.fastweixin.message.req.VoiceReqMsg;
import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.control.IControlService;
import com.lfc.wxadminweb.common.utils.CacheUtil;
import com.lfc.wxadminweb.common.utils.PaginationUtil;
import com.lfc.wxadminweb.common.utils.PropertiesUtil;
import com.lfc.wxadminweb.common.web.WeixinBaseController;
import com.lfc.wxadminweb.modules.biz.form.recommend.form.StaffRecomendForm;
import com.lfc.wxadminweb.modules.weixin.form.WXBatchSendForm;
import com.lfc.wxadminweb.modules.weixin.form.WXUserForm;
import com.lfc.wxadminweb.modules.weixin.form.WXUserGroupForm;

@Controller
@RequestMapping(value = "weixin")
public class WeixinController extends WeixinBaseController {


	@Value("#{system.appId}")
	private String appId;

	@Value("#{system.secret}")
	private String secret;

	@Value("#{system.picture_path}")
	private String path;

	@Value("#{system.viewImage}")
	private String viewImage;

	@Value("#{system.local_server}")
	private String localServer;
	
	@Value("#{system.siteDomain_name}")
	private String siteDomain_name;

	@Autowired
	private HttpServletRequest request;
	
	@Resource(name = "controlService")
	private IControlService controlService; // 前后工程调用服务
	
	private static final HashMap<String,String> cityMap = new HashMap<String,String>();
	static{
		cityMap.put("郑州", "41000122");
		cityMap.put("洛阳", "41000072");
		cityMap.put("南阳", "41000084");
		cityMap.put("焦作", "41000096");
		cityMap.put("商丘", "41000108");
		cityMap.put("信阳", "41000110");
		cityMap.put("漯河", "41022421");
		cityMap.put("新乡", "41022433");
		cityMap.put("周口", "41022460");
		cityMap.put("濮阳", "41022484");
		cityMap.put("许昌", "41022496");
		cityMap.put("鹤壁", "41022510");
		cityMap.put("开封", "41022522");
		cityMap.put("安阳", "41000021");
		cityMap.put("济源", "41000019");
		cityMap.put("三门峡", "41022508");
		cityMap.put("平顶山", "41022472");
		cityMap.put("驻马店", "41022458");
	}
	/**
	 * 处理接收到的文本消息
	 */
	@Override
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		String msgContent = msg.getContent().trim();
		// 根据发来的消息 匹配T_WX_AUTO_RESPONSE key_word
		
		// 微信用户openId
		String openId = msg.getFromUserName();
		String contextPath = request.getContextPath();
		//保存用户回复信息
		if(!"".equals(msgContent)){
			InputObject inputObject = new InputObject();
			Map<String, Object> map = new HashMap<String, Object>();
			//根据openId查询用户信息
			map.put("wxUserId", openId);
			map.put("msg", msgContent);
			getOutputObject(map, "userMessageService", "insertUserMessage");
			
		}
		if(msgContent.equals("操作手册") || msgContent.equals("使用帮助") 
				|| msgContent.equals("操作说明")){
			NewsMsg rtVal = new NewsMsg();
			Article articleIntro = new Article();
			
			articleIntro.setTitle("平台使用操作手册");
			articleIntro.setPicUrl(siteDomain_name+contextPath+"/assets/img/manuals.jpg");
			articleIntro.setUrl(siteDomain_name+"/weixinweb/biz/studyArticle/manuals?categoryId=e03ce5164f0c40e784baa87fad0756ad&openId="+openId);
			articleIntro.setDescription("您关心的问题都在这里...");
			rtVal.add(articleIntro);
			return rtVal;
		}
		
		
		//省分行劳动竞赛
		if(msgContent.equals("劳动竞赛")){
			String picURL = siteDomain_name+contextPath+"/assets/img/laoDongJingSai.png";
			String sfhLdjsURL = siteDomain_name+"/weixinweb/biz/rankinglist/getlist?openId="+openId;
			Article article = new Article("劳动竞赛", "劳动竞赛", picURL, sfhLdjsURL);
			List<Article> articles=new ArrayList<Article>();
			articles.add(article);
			NewsMsg newsMsg=new NewsMsg(articles);
			return newsMsg;
		}
		
		//各地市劳动竞赛
		if(msgContent.endsWith("劳动竞赛")){
			int endPos = msgContent.indexOf("劳动竞赛");
			String cityName = msgContent.substring(0, endPos);
			String cityCode = cityMap.get(cityName);
			if(cityCode != null){
				String picURL = siteDomain_name+contextPath+"/assets/img/laoDongJingSai.png";
				String zzsLdjsURL = siteDomain_name+"/weixinweb/biz/rankinglist/getzzlist?cityCode="+cityCode+"&openId="+openId;
				Article article = new Article(cityName+"市劳动竞赛", cityName+"市劳动竞赛", picURL, zzsLdjsURL);
				List<Article> articles=new ArrayList<Article>();
				articles.add(article);
				NewsMsg newsMsg=new NewsMsg(articles);
				return newsMsg;
			}
		}
		
		if(msgContent.equals("管理入口")){
			//openId.equals("ddd");
			Map<String, Object> map = new HashMap<String, Object>();
			//根据openId查询用户信息
			map.put("openId", openId);
			OutputObject outputObject = getOutputObject(map, "managerService", "getUserByOpenId");
			List<Map<String, Object>>  listMap = outputObject.getBeans();
			BaseMsg rtVal = null;
			if(listMap == null || listMap.size() == 0){ // 用户还没有认证
				rtVal = createMngEntranceMsg(false,openId);
			}
			else{
				rtVal = createMngEntranceMsg(true,openId);
			}
			return rtVal;
		}
		
		
		//获取个人二维码
		if("推荐二维码".equals(msgContent)|| "个人二维码".equals(msgContent)|| "二维码".equals(msgContent)){
			//根据openId查询用户信息
			logger.info("----weixinController --qrcode--openid is---"+openId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openId", openId);
			OutputObject outputObject = getOutputObject(map, "managerService", "getUserInfo");
			Map<String, Object>  outMap = (Map<String, Object>)outputObject.getObject();
			//标示该二维码是否存在
			boolean status = true ;
			String codeImg = "";
			String userName = "";
			if(outMap != null){
				codeImg = (String)outMap.get("codeImg");
				if(codeImg != null && !"".equals(codeImg.trim())){
					userName = (String)outMap.get("userName");
				}else{
					status = false;
				}
			}else{
				status = false;
			}
			BaseMsg rtVal = this.createMangerMsg(status,openId,codeImg,userName);
			return rtVal;
		}
		//从本地库查关键词回复设置
		Map<String, Object> mapSet = new HashMap<String, Object>();
		mapSet.put("new", msgContent);
		OutputObject outputObjectSet = getOutputObject(mapSet, "wXKeywordsResponseService", "getByNews");
		String type0="";
    	String text="";
    	String mediaId="";
		for(Map<String,Object> mapRespInfo: outputObjectSet.getBeans()){
			if(Integer.valueOf(mapRespInfo.get("isAllmatch").toString())==0){
				text=(String) mapRespInfo.get("txtContent");
				mediaId=(String) mapRespInfo.get("materialId");
				type0=(String) mapRespInfo.get("responseType");
				break;
			}else{
				if(msgContent.equals(mapRespInfo.get("keyWord"))){
					text=(String) mapRespInfo.get("txtContent");
					mediaId=(String) mapRespInfo.get("materialId");
					type0=(String) mapRespInfo.get("responseType");
					break;
				}
			}
		}
		//查询消息自动回复设置
		OutputObject outputObjectAuto=selectAutoResp ();
		//回复
		if(!"".equals(type0)&&(!"".equals(text) || !"".equals(mediaId))){
			return myMsg(type0,text,mediaId);
		}else if(outputObjectAuto.getObject()!=null){//未匹配到关键字则回复‘消息自动回复’中的设置
			return autoResp();
		}else{//暂时将以下代码放入else中
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyWord", msgContent.trim());
		OutputObject outputObject = getOutputObject(map, "wXAutoResponseService", "getAll");
		if (outputObject.getBeans().size() == 0) {
			return new TextMsg("抱歉,没有找到你输入的关键字！");
		}
		List<Map<String, Object>> list = outputObject.getBeans();
		Map<String, Object> mapRes = list.get(0);
		String type = (String) mapRes.get("msgType");
		// 获取模板ID
		String TempId = (String) mapRes.get("textTempId");
		// 判断type 是文本消息还是图文消息
		// 如果是文本消息text
		return createMsgByTemplateId(TempId, type);
		}
	}

	protected Logger logger = LoggerFactory.getLogger(getClass());

	
	/**
	 * 跳转到群发页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "toSendAll")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/massfunction");
		return mv;
	}
	/** 通过入参和service名称和方法名称构造inputObject调用Service获取outputObject **/
	public OutputObject getOutputObject(Map<String, Object> params, String service, String method) {
		OutputObject outputObject = null;
		if (StringUtils.isNotBlank(service) && StringUtils.isNotBlank(method)) {
			// 计算分页参数
			PaginationUtil.initPagination(params);
			InputObject inputObject = new InputObject();
			inputObject.setParams(params);
			inputObject.setService(service);
			inputObject.setMethod(method);
			outputObject = this.execute(inputObject);
		} else {
			logger.error("服务名和方法名不能为空!");

		}
		return outputObject;
	}

	/* 调用后台执行service方法 */
	private OutputObject execute(InputObject inputObject) {
		OutputObject outputObject = null;
		try {
			outputObject = controlService.execute(inputObject);

		} catch (Exception e) {
			logger.error("Invoke Service Error.", inputObject.getService() + "." + inputObject.getMethod(), e);
			e.printStackTrace();
		}
		return outputObject;
	}
	

	// @Override
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		//String fromUserName = event.getFromUserName();
		String eventKey = event.getEventKey();
		//根据菜单参数，查询菜单类型，如果是点击事件返回消息素材
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("menuKey", eventKey);
		OutputObject menuOutObject = getOutputObject(params,"wXMenusService","getWXMenuByKey");
		Map<String, Object> result = (Map<String, Object>) menuOutObject.getObject();
		if(result.get("type")!=null && result.get("type").toString().equals("click")){
			if(result.get("mediaId")!=null && result.get("mediaType")!=null){
				String mediaId = result.get("mediaId").toString();
				String mediaType = result.get("mediaType").toString();
				//素材分类(text:文本|news:图文|voice:语音|video:视频|image:图片)
				if(mediaType.equals("text")){
					//文本类型暂时没有
					
				}else if(mediaType.equals("news")){
					logger.info("---------点击菜单--推送--图文素材------->"); 
					Map<String, Object> articleParms = new HashMap<String, Object>();
					articleParms.put("mediaId", mediaId);
					OutputObject outputObject = getOutputObject(articleParms, "wXArticleMaterialService", "getArticlesByWxMediaId");
					NewsMsg rtVal = new NewsMsg();
					if(CollectionUtils.isNotEmpty(outputObject.getBeans())){
						for (Map<String, Object> map : outputObject.getBeans()) {
							Article articleIntro = new Article();
							articleIntro.setTitle(map.get("title").toString());
							articleIntro.setPicUrl(map.get("imgUrl")==null?"":map.get("imgUrl").toString());
							articleIntro.setUrl(map.get("url").toString());
							articleIntro.setDescription(map.get("digest").toString());
							rtVal.add(articleIntro);
						}
					}
					return rtVal;
				}else if(mediaType.equals("voice")){
					logger.info("---------点击菜单--推送--音频素材------->"); 
					VoiceMsg voiceMsg = new VoiceMsg(mediaId);
					return voiceMsg;
				}else if(mediaType.equals("video")){
					Map<String, Object> videoParms = new HashMap<String, Object>();
					videoParms.put("mediaId", mediaId);
					OutputObject outputObject = getOutputObject(videoParms, "wXMaterialService", "getByMediaId");
					Map<String, Object> videoResult = (Map<String, Object>) outputObject.getObject();
					String title = videoResult.get("name")==null?"":videoResult.get("name").toString();
					String description = videoResult.get("videoInstru")==null?"":videoResult.get("videoInstru").toString();
					VideoMsg videoMsg = new VideoMsg(mediaId, title, description);
					return videoMsg;
				}else if(mediaType.equals("image")){
					ImageMsg imageMsg = new ImageMsg(mediaId);
					return imageMsg;
				}
			}
		}
		return null;
	}
	

	/**
	 * 处理语音消息，有需要时子类重写
	 *
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	@Override
	protected BaseMsg handleVoiceMsg(VoiceReqMsg msg) {
		OutputObject outputObjectAuto=selectAutoResp ();
		if(outputObjectAuto.getObject()!=null){//未匹配到关键字则回复‘消息自动回复’中的设置
			return autoResp();
		}
		return new TextMsg("您说的是：" + msg.getRecognition());
	}

	/**
	 * 处理图片消息，有需要时子类重写
	 *
	 * @param msg
	 *            请求消息对象
	 * @return 响应消息对象
	 */
	@Override
	protected BaseMsg handleImageMsg(ImageReqMsg imageMessage) {
		OutputObject outputObjectAuto=selectAutoResp ();
		if(outputObjectAuto.getObject()!=null){//‘消息自动回复’中的设置
			return autoResp();
		}
		return new TextMsg("这是个图片消息");
	}
    /**
     * 处理视频消息，有需要时子类重写
     *
     * @param msg 请求消息对象
     * @return 响应消息对象
     */
    protected BaseMsg handleVideoMsg(VideoReqMsg msg) {
    	OutputObject outputObjectAuto=selectAutoResp ();
    	if(outputObjectAuto.getObject()!=null){//未匹配到关键字则回复‘消息自动回复’中的设置
			return autoResp();
		}
        return handleDefaultMsg(msg);
    }
    /**
     * 处理小视频消息，有需要时子类重写
     *
     * @param msg 请求消息对象
     * @return 响应消息对象
     */
    protected BaseMsg hadnleShortVideoMsg(VideoReqMsg msg) {
    	OutputObject outputObjectAuto=selectAutoResp ();
    	if(outputObjectAuto.getObject()!=null){//未匹配到关键字则回复‘消息自动回复’中的设置
			return autoResp();
		}
        return handleDefaultMsg(msg);
    }

    /**
     * 处理地理位置消息，有需要时子类重写
     *
     * @param msg 请求消息对象
     * @return 响应消息对象
     */
    protected BaseMsg handleLocationMsg(LocationReqMsg msg) {
    	OutputObject outputObjectAuto=selectAutoResp ();
    	if(outputObjectAuto.getObject()!=null){//‘消息自动回复’中的设置
			return autoResp();
		}
        return handleDefaultMsg(msg);
    }

    /**
     * 处理链接消息，有需要时子类重写
     *
     * @param msg 请求消息对象
     * @return 响应消息对象
     */
    protected BaseMsg handleLinkMsg(LinkReqMsg msg) {
    	OutputObject outputObjectAuto=selectAutoResp ();
    	if(outputObjectAuto.getObject()!=null){//未匹配到关键字则回复‘消息自动回复’中的设置
			return autoResp();
		}
        return handleDefaultMsg(msg);
    }

	/**
	 * 处理添加关注事件
	 * 
	 * @param event
	 *            添加关注事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleSubscribe(BaseEvent event,String eventKey) {
		logger.info("----------------关注事件----------------");
		// 添加关注用户到数据库
		String openId = event.getFromUserName();
		/*---yaohh调此方法时有报错，暂时注掉
		 * */
		/* --------JZP此方法已修改可正常使用--内含--吸粉统计逻辑-------*/
		insertWXUser(openId,eventKey);
		/*--------end-----------*/
		//saveUser(opendId);--缺少吸粉统计逻辑--暂注掉
		//查本地库中设置的回复
		Map<String, Object> map0=new HashMap<>();
		map0.put("type", 0);
		OutputObject outputObject0 = getOutputObject(map0, "wXSubscribeRespService", "getReply");
		Map<String,String> info=(Map<String,String>)outputObject0.getObject();
		if(info!=null){
			return subscribeResp(info);
		}else{
			//关注回复的图文消息，mediaId暂时写死，mediaId=vX2Vns96shnvgg6WU6dUb6Hr7vvpluEQC29cZVMsgWY，这是公众号关注后的返回图文消息mediaId，勿删
			String mediaId = "vX2Vns96shnvgg6WU6dUb6Hr7vvpluEQC29cZVMsgWY";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mediaId", mediaId);
			OutputObject outputObject = getOutputObject(params, "wXArticleMaterialService", "getArticlesByWxMediaId");
			NewsMsg rtVal = new NewsMsg();
			if(CollectionUtils.isNotEmpty(outputObject.getBeans())){
				for (Map<String, Object> map : outputObject.getBeans()) {
					Article articleIntro = new Article();
					articleIntro.setTitle(map.get("title").toString());
					articleIntro.setPicUrl(map.get("imgUrl")==null?"":map.get("imgUrl").toString());
					articleIntro.setUrl(map.get("url").toString());
					articleIntro.setDescription(map.get("digest").toString());
					rtVal.add(articleIntro);
				}
			}
			return rtVal;
		}
		//暂时屏蔽此段代码，目前写死回复图文消息，无意屏蔽笔者的劳动成果，有问题询问孟老师=====================================
//		Map<String, Object> map = new HashMap<String, Object>();
//		OutputObject outputObject = getOutputObject(map, "wXSubcribeMsgService", "getAll");
//		List<Map<String, Object>> msgs = outputObject.getBeans();
//		// 模板ID
//		String templateId = (String) msgs.get(0).get("templateId");
//		// 类型
//		String type = (String) msgs.get(0).get("msgType");
//		map.put("textTempId", templateId);// 文本消息模板ID
//		map.put("itemId", templateId); // 图文消息模板ID
//
//		return createMsgByTemplateId(templateId, type);
		// 文本消息回复语
		//屏蔽结束========================================================================
	}
	
	/**
	 * 处理二维码扫码事件
	 */
	@Override
	protected BaseMsg handleScanCodeEvent(ScanCodeEvent event) {
		/*
		logger.info("----------start----------");
		String eventKey = event.getEventKey();
		String openId = event.getFromUserName();
		//获得扫描用户信息
		WXUserForm wxUserFrom = getUserMsg(openId);
		String[] arr = eventKey.split("_");
		logger.info("-------------》》"+arr.length);
		if(arr.length>1){
			// 员工工号
			String staffCode = arr[1];
			Map<String, Object> map = new HashMap<String, Object>();
			//设置查询条件
			map.put("openId", openId);
			map.put("staffCode", staffCode);
			OutputObject outputObject = getOutputObject(map,"staffRecomendService","getAll");//查询
			map.put("nickName", wxUserFrom.getNickName());
			map.put("headImg", wxUserFrom.getHeaderImage());
			if(outputObject.getBeans() == null || outputObject.getBeans().size() == 0){
				map.put("curState", "T");
				getOutputObject(map, "staffRecomendService", "insertStaffRecomend");
			}else{
				map.put("curState", "R");
				getOutputObject(map, "staffRecomendService", "updateStaffRecomend");
			}
		}
		logger.info("-------handleScanCodeEvent-------->"+eventKey);
		*/
        return handleDefaultEvent(event);
    }

	/**
	 * 把关注的用户信息插入到数据库
	 * 
	 * @param wxUserFrom
	 */
	public void insertWXUser(String openId,String eventKey) {
		
		// 获取用户信息
		ApiConfig config = new ApiConfig(PropertiesUtil.getString("appId"),	PropertiesUtil.getString("secret"));
		UserAPI userAPI = new UserAPI(config);
		GetUserInfoResponse userInfo = userAPI.getUserInfo(openId);
		Map<String, Object> mapUser = BeanUtil.convertBean2Map(userInfo);
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		String[] arr = eventKey.split("_");
		logger.info("------保存个人信息-------》》"+arr.length);
		if(arr.length>1){//二维码携带参数
			userMap.put("openId", openId);
			//性别 1 为男 2为女
			userMap.put("gender", mapUser.get("sex"));
			OutputObject outputObject = getOutputObject(userMap,"staffRecomendService","getAll");
			// 员工工号
			String staffCode = arr[1];
			if(outputObject.getBeans() == null || 
					outputObject.getBeans().size() == 0){//不在吸粉表中
				Map<String, Object> userInfoMap = new HashMap<String, Object>();
				userInfoMap.put("openId", openId);
				OutputObject outputObject1 = getOutputObject(userInfoMap, 
						"wXUserService", "getByOpenId");
				Map<String,Object> userInfo0=(Map<String,Object>)(outputObject1.getObject());
				if (userInfo0 == null) {// 库中无该用户信息添加
					logger.info("-------吸粉保存--------");
					mapUser.put("headImg", mapUser.get("headimgurl"));
					mapUser.put("nickName", mapUser.get("nickname"));
					mapUser.put("staffCode", staffCode);
					mapUser.put("openId", openId);
					mapUser.put("curState","T");
					getOutputObject(mapUser, "staffRecomendService", "insertStaffRecomend");
				} else {// 库中存在该用户信息--更新
					mapUser.put("wxUserId", userInfo0.get("wxUserId"));
					getOutputObject(mapUser, "wXUserService", "updateWXUser");
				}
			}else{
				logger.info("-------吸粉重复关注--------");
				Map<String, Object> staffMap = new HashMap<String, Object>();
				staffMap.put("curState", "R");
				staffMap.put("id", outputObject.getBeans().get(0).get("id"));
				getOutputObject(staffMap, "staffRecomendService", "updateStaffRecomend");
			}
		}else{//二维码无参数
			Map<String, Object> userInfoMap = new HashMap<String, Object>();
			userInfoMap.put("openId", openId);
			OutputObject outputObject1 = getOutputObject(userInfoMap, "wXUserService",
					"getByOpenId");
			Map<String,Object> userInfo0=(Map<String,Object>)(outputObject1.getObject());
			
			if (userInfo0 == null) {// 库中无该用户信息添加
				logger.info("-------保存用户信息--------");
				getOutputObject(mapUser, "wXUserService", "insertWXUser");
				
				//同步增加关注邮豆积分
				logger.info("-------关注邮豆积分--------");
				userMap.clear();
				userMap.put("openId", openId);
				userMap.put("type", "subscribe");
				OutputObject output=getOutputObject(userMap, "ficMoneyService", "getAll");
				if(output.getBeans().size()==0){//积分表中无关注积分
					userMap.put("amount", 5);
					getOutputObject(userMap, "ficMoneyService", "insertFicMoney");
				}
			} else {// 库中存在该用户信息--更新
				mapUser.put("wxUserId", userInfo0.get("wxUserId"));
				getOutputObject(mapUser, "wXUserService", "updateWXUser");
			}
			
		}
	}

	/**
	 * 获取微信 accesstoken
	 * 
	 * @return
	 */
	public String getAccessToken() {
		String accessToken = "";
		OutputObject outputObject = null;
		Map< String, Object> map = new HashMap< String, Object>();
		map.put("id", "1");
		outputObject = getOutputObject(map,"accessTokenService","getById");
		Map< String, Object> outputMap = (Map< String, Object>)outputObject.getObject();
		if(!outputMap.isEmpty()){
			accessToken = (String)outputMap.get("accessToken");
		}
		return accessToken;
	}

	/**
	 * 根据openId 组建一个WXUserForm
	 * 
	 * @param openId
	 *            微信ID
	 * @return WXUserForm
	 */
	public WXUserForm getUserMsg(String openId) {
		WXUserForm userFrom = new WXUserForm();
		String access_token = getAccessToken();
		logger.info("------token--------"+access_token);
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid=" + openId + "&lang=zh_CN";
		URL urlGet;
		try {
			
			userFrom.setOpenId(openId);
			
			urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();

			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒

			http.connect();

			InputStream is = http.getInputStream();
			int size = is.available();

			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			logger.info("------message--------"+message);
			JSONObject demoJson = new JSONObject(message);
			String nickName = demoJson.getString("nickname");
			String headimgurl = demoJson.getString("headimgurl");
			//1男 2女
			int sex = demoJson.getInt("sex");
			if(sex==1){
				userFrom.setGender("男");
			}else{
				userFrom.setGender("女");
			}
			userFrom.setNickName(nickName);
			
			userFrom.setSubscribeTime(new Date());
			userFrom.setHeaderImage(headimgurl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userFrom;
	}

	/**
	 * 处理取消关注事件，有需要时子类重写
	 * 
	 * @param event
	 *            取消关注事件对象
	 * @return 响应消息对象
	 */
	protected BaseMsg handleUnsubscribe(BaseEvent event) {
		logger.info("-----------------取消关注---------------");
		Map<String, Object> userMap = new HashMap<String, Object>();
		// openId
		String openId = event.getFromUserName();
		userMap.put("openId", openId);
		OutputObject outputObject = getOutputObject(userMap,"staffRecomendService","getAll");//查询
		if(outputObject.getBeans() != null && outputObject.getBeans().size() > 0){
			userMap.put("curState", "F");
			userMap.put("id", outputObject.getBeans().get(0).get("id"));
			getOutputObject(userMap, "staffRecomendService", "updateStaffRecomend");
		}
		//更新T_WX_USER（Yaohh）
		updateUser(openId);
		/*else{
			getOutputObject(userMap, "wXUserService", "deleteWXUser");
		}*/
		//根据open删除对应的邮豆纪录表  zhaoyan
		//getOutputObject(userMap, "ficMoneyService", "deleteFicMoneyByParams");
		
		return null;
	}

	/**
	 * 处理重复关注事件，有需要时
	 * 
	 * @param event
	 *            重复关注事件对象
	 * @return 响应消息对象
	 */
	@Override
	 protected BaseMsg handleQrCodeEvent(QrCodeEvent event) {
	     return handleDefaultEvent(event);
	}
	/**
	 * 关注语回复
	 * 
	 * @param templateId
	 *            模板ID
	 * @param type
	 *            类型 text为文本 news为图文
	 * @return BaseMsg
	 */
	protected BaseMsg createMsgByTemplateId(String templateId, String type) {
		BaseMsg rtValue = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if (type.equals("text")) {
			map.put("textTempId", templateId);
			OutputObject textoutputObject = getOutputObject(map, "wXTextTemplatesService", "getById");
			Map<String, Object> textMap = (Map<String, Object>) textoutputObject.getObject();
			rtValue = new TextMsg((String) textMap.get("content"));

		} else if (type.equals("news")) {// 如果是图文消息news

			map.put("newsTempId", templateId);
			OutputObject newsoutputObject = getOutputObject(map, "wXNewsTemplatesService", "getById");

			Map<String, Object> newsMap = (Map<String, Object>) newsoutputObject.getObject();

			if (newsMap != null) {
				map.put("orderByClause", "sort");
				OutputObject outputObjectItm = getOutputObject(map, "wXNewsItemsService", "getAll");
				List<Map<String, Object>> newsItem = outputObjectItm.getBeans();

				List<Article> articleList = new ArrayList<Article>();
				NewsMsg newsMessage = new NewsMsg();
				String contextPath = request.getContextPath();
				for (int i = 0; i < newsItem.size(); i++) {
					Article article = new Article();
					
					article.setTitle((String) newsItem.get(i).get("title"));
					article.setDescription((String) newsItem.get(i).get("content"));
					article.setPicUrl(siteDomain_name + contextPath + viewImage + (String) newsItem.get(i).get("imagePath"));
					
					String url = (String) newsItem.get(i).get("url");
					String openIDParameter = "openId=";
					if(url != null && !url.trim().equals("")){
						if(url.contains("?")){
							openIDParameter = "&"+openIDParameter;
						}else{
							openIDParameter = "?"+openIDParameter;
						}
						article.setUrl(url+openIDParameter);
					}else{
						String articleId= (String) newsItem.get(i).get("itemId");
						article.setUrl(siteDomain_name+contextPath+"/wx/article/outputArticle?articleId="+articleId+"&"+openIDParameter);
					}
					logger.info(article.getUrl());
					articleList.add(article);
					
				}

				newsMessage.setArticles(articleList);
				rtValue = newsMessage;

			} else {
				rtValue = new TextMsg("抱歉,没有找到相关信息！");
			}

		} else {// 其他消息
			rtValue = new TextMsg("抱歉,没有找到你输入的关键字！");
		}

		return rtValue;
	}


	/**
	 * 同步微信分组 微信服务器到本地服务器
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "synWXUserGroup")
	public OutputObject synWXUserGroup() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", appId);
		map.put("secret", secret);
		OutputObject outputObject = getOutputObject(map, "weixinService", "getGroups");
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("同步成功");
		}

		return outputObject;
	}

	/**
	 * 添加微信用户组
	 * 
	 * @param WXUserGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXUserGroup")
	public OutputObject insertWXUserGroup(@ModelAttribute("WXUserGroupForm") @Valid WXUserGroupForm wXUserGroupForm, BindingResult result, Model model, ModelMap mm) {

		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupName", wXUserGroupForm.getName());
		map.put("appid", appId);
		map.put("secret", secret);
		outputObject = getOutputObject(map, "weixinService", "createUserGroup");
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("微信用户组添加成功!");
		}
		return outputObject;
	}
	
	/**
	 * 删除微信用户组
	 * 
	 * @param WXUserGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXUserGroup")
	public OutputObject deleteWXUserGroup(@ModelAttribute("WXUserGroupForm") @Valid WXUserGroupForm wXUserGroupForm, BindingResult result, Model model, ModelMap mm) {

		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupName", wXUserGroupForm.getName());
		map.put("appid", appId);
		map.put("secret", secret);
		map.put("groupId", wXUserGroupForm.getGroupId());
		
		outputObject = getOutputObject(map, "weixinService", "deleteUserGroup");
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("微信用户组添加成功!");
		}
		return outputObject;
	}

	@ResponseBody
	@RequestMapping(value = "moveUser")
	public OutputObject moveUser(@ModelAttribute("wXUserGroupForm") WXUserGroupForm wXUserGroupForm, BindingResult result, Model model, ModelMap mm, String userID) {
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", appId);
		map.put("secret", secret);
		// 组ID
		map.put("groupId", wXUserGroupForm.getGroupId());

		// 微信用户ID集合
		String[] userIds = userID.split(",");
		map.put("openIds", userID);
		outputObject = getOutputObject(map, "weixinService", "moveGroupUser");

		for (int i = 0; i < userIds.length; i++) {
			map.put("openId", userIds[i]);
			outputObject = getOutputObject(map, "wXUserService", "updateWXUserGroup");
		}
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("移动成功!");
		}
		return outputObject;
	}
	
	/**
	 * 处理 管理入口 的消息
	 * @param authentication
	 * @return BaseMsg
	 */
	private BaseMsg createMngEntranceMsg(boolean authentication,String openId){
		NewsMsg rtVal = new NewsMsg();
		
		List<Article> articleList = new ArrayList<Article>();
		String contextPath = request.getContextPath();
		Article articleIntro = new Article(); // 管理入口介绍

		articleIntro.setTitle("登录管理入口操作说明");
		articleIntro.setPicUrl(localServer+contextPath+"/assets/img/guanlirk.jpg");
		articleIntro.setUrl("http://mp.weixin.qq.com/s/Ha3smQcJQilaBqAFEArXdQ");
		articleIntro.setDescription("亲爱的客户经理们，这个登录入口可以通过登录微信公众平台查询和管理业务申请客户信息，方便您随时随地掌握业务动态哦！");
		logger.info("pic---path--->"+localServer+contextPath+"/assets/img/guanlirk.jpg");
		if(authentication){
			Article articleAuth  = new Article(); // 管理认证入口
			articleAuth.setTitle("登录管理入口");
			articleAuth.setPicUrl(siteDomain_name+contextPath+"/assets/img/enter.jpg");
			articleAuth.setUrl(localServer +"/weixinweb/biz/manageenter/handle?openId="+openId);
			articleList.add(articleIntro);
			articleList.add(articleAuth);
		}else{
			Article articleAuth  = new Article(); // 管理认证入口
			articleAuth.setTitle("用户认证");
			articleAuth.setPicUrl(localServer+contextPath+"/assets/img/enter.jpg");
			articleAuth.setUrl(localServer +"/weixinweb/biz/manageenter/enter?openId="+openId);
			articleList.add(articleIntro);
			articleList.add(articleAuth);
			
		}
		rtVal.setArticles(articleList);
		return rtVal;
	}
	
	
	/**
	 * 处理 商户入口 的消息
	 * @param authentication
	 * @return BaseMsg
	 */
	private BaseMsg createMerchantEntranceMsg(boolean authentication,String openId){
		NewsMsg rtVal = new NewsMsg();
		
		List<Article> articleList = new ArrayList<Article>();
		String contextPath = request.getContextPath();
		
		Article articleIntro = new Article(); // 管理入口介绍
		
		articleIntro.setTitle("商户入口功能介绍");
		articleIntro.setPicUrl(siteDomain_name + contextPath + viewImage + "intro.jpg");
		articleIntro.setDescription("为您提供新的邮政储蓄银行特惠商户折扣信息,您还可以查询邮政信用卡特惠商户,了解特惠商户的电话、地址、优惠折扣信息,享受特惠商户的折扣价,做个信用卡达人。");
		articleIntro.setUrl(siteDomain_name +"/weixinweb/biz/merchant/introduce?openId="+openId);
		
		if(authentication){//处理已认证
			//添加商户入口功能介绍
			articleList.add(articleIntro);
		}else{//处理未认证
			Article articleAuth  = new Article(); // 认证入口
			
			articleAuth.setTitle("用户认证");
			articleAuth.setPicUrl(siteDomain_name + contextPath + viewImage + "intro.jpg");
			articleAuth.setUrl(siteDomain_name +"/weixinweb/biz/merchant/approve?openId="+openId);
			
			articleList.add(articleIntro);
			articleList.add(articleAuth);
		}
		rtVal.setArticles(articleList);
		return rtVal;
	}
	/**
	 * 处理 二维码回复 消息
	 * @param authentication
	 * @return BaseMsg
	 */
	private BaseMsg createMangerMsg(boolean authentication,String openId,String codeImg,String userName){
		NewsMsg rtVal = new NewsMsg();
		
		List<Article> articleList = new ArrayList<Article>();
		String contextPath = request.getContextPath();
		
		if(authentication){//处理二维码已存在
			//添加商户入口功能介绍
			Article articleIntro = new Article();
			articleIntro.setTitle("查看个人二维码");
			articleIntro.setPicUrl(siteDomain_name +contextPath+"/assets/img/erweimtj.jpg");
			articleIntro.setUrl(siteDomain_name +"/weixinweb/biz/manageenter/qrCode?codeImg="+codeImg+"&userName="+userName);
			articleList.add(articleIntro);
		}else{//处理未认证
			Article articleAuth  = new Article(); //处理二维码不存在
			
			articleAuth.setTitle("生成个人二维码");
			articleAuth.setDescription("快来生成您的个人专属二维码吧！");
			articleAuth.setPicUrl(siteDomain_name +contextPath+"/assets/img/erweimtj.jpg");
			articleAuth.setUrl(siteDomain_name +"/weixinweb/biz/manageenter/createQRCode?openId="+openId);
			//logger.info("------生成个人二维码-------->"+"http://localhost:8090/weixinweb/biz/manageenter/createQRCode?openId="+openId);
			articleList.add(articleAuth);
		}
		rtVal.setArticles(articleList);
		return rtVal;
	}
	
	@RequestMapping(value = "forward")
	public void forward(HttpServletRequest request ,HttpServletResponse response){
		
		logger.debug(request.getParameter("code"));
	}

	/**
	 * 通过关键字被动回复消息
	 * 
	 * @param msg
	 * @return
	 */
	protected BaseMsg replyMessage(TextReqMsg msg) {
		String reqContent = msg.getContent();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyWord", reqContent);
		List<Article> articles = new ArrayList<Article>();
		OutputObject type = getOutputObject(map, "weixinService", "getReType");
		OutputObject outputObject = null;
		if(type.getBean()!=null){
			String responseType = (String) type.getBean().get("responseType");
			String txtContent = (String) type.getBean().get("txtContent");
			if ("text".equals(responseType)) {
				return new TextMsg(txtContent);
			} else if ("article".equals(responseType)) {
				outputObject = getOutputObject(map, "weixinService", "getMaterial");
				List<Map<String, Object>> materials = outputObject.getBeans();
				for (int i = 0; i < materials.size(); i++) {
					Map<String, Object> material = materials.get(i);
					String title = material.get("title").toString();
					String digest = material.get("digest").toString();
					String picUrl = material.get("remoteUrl").toString();
					String url = material.get("url").toString();
					Article article = new Article(title, digest, picUrl, url);
					articles.add(article);
				}
				return new NewsMsg(articles);
			}else if("image".equals(responseType)){
				return new ImageMsg(type.getBean().get("materialId").toString());
			}else if("voice".equals(responseType)){
				return new VoiceMsg(type.getBean().get("materialId").toString());
			}else{
				return new VideoMsg(type.getBean().get("materialId").toString());
			}
		}else{
			return new TextMsg("暂不支持");
		}
	}
	/**
	 * 微信接入入口  GET调用用于微信介入校验  POST用于接收微信消息
	 * @param response
	 */
	@RequestMapping(value = "init")
	@ResponseBody
	public String init(HttpServletResponse response){
		logger.info("微信接入入口!");
		HttpServletRequest request=getRequest();
		String method = getRequest().getMethod();
		if(method.equals("GET")){
			logger.info("GET请求，微信服务器握手!");
			String result = bind(request);
			logger.info("绑定微信服务器结果:"+result);
			return result;
		}else{
			try {
				logger.info("POST请求，微信消息交互处理!");
				process(request, response);
			} catch (ServletException e) {
				logger.info("消息请求异常："+ e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.info("消息请求 IO异常"+ e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 群发消息
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "batchSend")
	public GetSendMessageResponse batchSend(@ModelAttribute("WXBatchSendForm") WXBatchSendForm wXBatchSendForm){
		MessageAPI messageAPI=new MessageAPI(new ApiConfig(appId, secret));
		if("text".equals(wXBatchSendForm.getType())){//群发文本
			return	messageAPI.sendMessageToUser(new TextMsg(wXBatchSendForm.getContent()), wXBatchSendForm.getIsToAll()==0?true:false, wXBatchSendForm.getTagId());
		}else if("news".equals(wXBatchSendForm.getType())){//群发图文
			return messageAPI.sendMessageToUser(new MpNewsMsg(wXBatchSendForm.getMaterialId()), wXBatchSendForm.getIsToAll()==0?true:false, wXBatchSendForm.getTagId());
		}else if("image".equals(wXBatchSendForm.getType())){//群发图片
			return messageAPI.sendMessageToUser(new ImageMsg(wXBatchSendForm.getMaterialId()), wXBatchSendForm.getIsToAll()==0?true:false, wXBatchSendForm.getTagId());
		}else if("voice".equals(wXBatchSendForm.getType())){//群发语音
			return messageAPI.sendMessageToUser(new VoiceMsg(wXBatchSendForm.getMaterialId()), wXBatchSendForm.getIsToAll()==0?true:false, wXBatchSendForm.getTagId());
		}else{//群发视频
			return messageAPI.sendMessageToUser(new VideoMsg(wXBatchSendForm.getMaterialId()), wXBatchSendForm.getIsToAll()==0?true:false, wXBatchSendForm.getTagId());
		}
		
	}
	//取消关注时，更新T_WX_USER及其属性表
	public void updateUser(String fromUserName) {
    	Map<String, Object> map = new HashMap<>();
    	map.put("openId", fromUserName);
    	map.put("unsubscribeTime", new Date());
		getOutputObject(map, "wXUserService", "updateByOpenId");
    }
    //向T_WX_USER及其属性表保存关注用户
	public void saveUser(String fromUserName) {
		Map<String, Object> map0 = new HashMap<>();
		// 判断用户是否存在
		map0.put("openId", fromUserName);
		OutputObject outputObject0 = getOutputObject(map0, "wXUserService",
				"getByOpenId");
		Map<String,Object> user=(Map<String,Object>)(outputObject0.getObject());
		// 获取用户信息
		ApiConfig config = new ApiConfig(PropertiesUtil.getString("appId"),
				PropertiesUtil.getString("secret"));
		UserAPI userAPI = new UserAPI(config);
		GetUserInfoResponse userInfo = userAPI.getUserInfo(fromUserName);
		Map<String, Object> mapUser = BeanUtil.convertBean2Map(userInfo);
		if (user == null) {// 库中无该用户信息则添加
			getOutputObject(mapUser, "wXUserService", "insertWXUser");
		} else {// 更新
			mapUser.put("wxUserId", user.get("wxUserId"));
			getOutputObject(mapUser, "wXUserService", "updateWXUser");
		}
	}
    //关注时回复语
	protected BaseMsg subscribeResp(Map<String,String> info) {
		//发送消息
		String defaultMsg="河南邮储微信平台感谢您的关注!";
    	String type=info.get("msgType");
    	String text=info.get("txtMsg");
    	String mediaId=info.get("mediaId");
    	//返回设置信息
		if(type!=null&&!"".equals(type)){
			return myMsg(type,text,mediaId);
    	}else{
    		return new TextMsg(defaultMsg);
    	}
    }
	public BaseMsg myMsg(String type, String text, String mediaId){
		if("text".equals(type)){
			return new TextMsg(text);
		}else if("image".equals(type)){
			return new ImageMsg(mediaId);
		}else if("voice".equals(type)){
			return new VoiceMsg(mediaId);
		}else if("video".equals(type)){
			return new VideoMsg(mediaId);
		}else if("article".equals(type)){
			Map<String, Object> mapArticle=new HashMap<>();
			mapArticle.put("mediaId", mediaId);
			OutputObject outputObjectArtilce = getOutputObject(mapArticle,"wXArticleMaterialService","getArticlesByWxMediaId");
			List<Article> articles=new ArrayList<Article>();
			for(Map<String, Object> article0:outputObjectArtilce.getBeans()){
				Article article = new Article((String)article0.get("title"),(String) article0.get("digest"), (String)article0.get("imgUrl"), (String)article0.get("url"));
				articles.add(article);
			}
			NewsMsg newsMsg=new NewsMsg(articles);
			return newsMsg;
		}
		return null;
	}
	public OutputObject selectAutoResp (){
		Map<String,Object> mapAuto=new HashMap<String,Object>();
		mapAuto.put("type", 1);
		OutputObject outputObject = getOutputObject(mapAuto, "wXSubscribeRespService", "getReply");
		return outputObject;
	}
	public BaseMsg autoResp(){
		OutputObject outputObject=selectAutoResp ();
		Map<String,String> autoResp=(Map<String,String>)outputObject.getObject();
		return myMsg(autoResp.get("msgType"),autoResp.get("txtMsg"),autoResp.get("mediaId"));
	}
	
}
