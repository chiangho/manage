package hao.webapp.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hao.framework.core.expression.HaoException;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.AppSetting;
import hao.webapp.demo.service.AppSettingService;

@Controller
@RequestMapping("app")
public class AppSettingAction {
	
	@Autowired
	AppSettingService appSettingService;
	
	
	/***
	 * 保存系统参数
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("setting/save")
	public JSONView save() throws HaoException {
		appSettingService.settingSave();
		return RequestContext.getJSONView();
	}
	
	/**
	 * 获取系统参数
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("setting/load")
	public JSONView load() throws HaoException {
		AppSetting setting = appSettingService.settingLoad();
		return RequestContext.getJSONView(setting);
	}
}
