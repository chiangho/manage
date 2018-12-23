package hao.webapp.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hao.framework.core.expression.HaoException;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.service.CompanyAttendanceService;
import hao.webapp.demo.service.TestService;

@Controller
@RequestMapping("test")
public class Test {

	
	@Autowired
	TestService testService;
	
	@Autowired
	CompanyAttendanceService companyAttendanceService;
	
	@RequestMapping("add")
	public JSONView insert() throws HaoException {
		testService.insert();
		return RequestContext.getJSONView();
	}
	
	
	@RequestMapping("query_bean")
	public JSONView query_bean() throws HaoException {
		Object access = companyAttendanceService.queryList();
		return RequestContext.getJSONView(access);
	}
	
}
