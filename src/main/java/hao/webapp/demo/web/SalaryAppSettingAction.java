package hao.webapp.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hao.framework.core.expression.HaoException;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.SalaryAppSetting;
import hao.webapp.demo.service.SalaryAppSettingService;

@Controller
@RequestMapping("salary_app_setting")
public class SalaryAppSettingAction {
	
	private SalaryAppSettingService salaryAppSettingService;

	public JSONView loadSalaryAppSetting(
			@RequestParam(value="salaryId") String salaryId
			) throws HaoException {
		SalaryAppSetting appSetting = salaryAppSettingService.querySalaryAppSetting(salaryId);
		return RequestContext.getJSONView(appSetting);
	}
	
}
