package hao.webapp.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hao.framework.core.expression.HaoException;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.CompanyAttendanceItemModel;
import hao.webapp.demo.service.CompanyAttendanceService;

@Controller
@RequestMapping("company/attendance")
public class CompanyAttendanceAction {

	@Autowired
	private  CompanyAttendanceService companyAttendanceService;
	
	@RequestMapping("load_attendance_item")
	public JSONView load_attendance_item(
			@RequestParam(value="companyId") String companyId
			) throws HaoException {
		List<CompanyAttendanceItemModel> items = companyAttendanceService.queryItemByCompanyId(companyId);
		return RequestContext.getJSONView(items);
	}
	
	@RequestMapping("save_attendance_item")
	public JSONView save_attendance_item() throws HaoException {
		CompanyAttendanceItemModel item = companyAttendanceService.saveAttendanceItem();
		RequestContext.setModelData("item", item);
		return RequestContext.getJSONView();
	}
	
	/***
	 * 删除
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("del_attendance_item")
	public JSONView del_attendance_item(
			@RequestParam(value="id") String id
			) throws HaoException {
		companyAttendanceService.del(id);
		return RequestContext.getJSONView();
	}
}
