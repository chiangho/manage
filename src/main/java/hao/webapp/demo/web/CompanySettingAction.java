package hao.webapp.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hao.framework.core.expression.HaoException;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.CompanyBaseProject;
import hao.webapp.demo.model.CompanyDepartment;
import hao.webapp.demo.model.CompanyPoint;
import hao.webapp.demo.model.CompanyProductionLine;
import hao.webapp.demo.model.CompanyProject;
import hao.webapp.demo.service.CompanySettingService;

/**
 * 公司基本信息设置
 * 
 * @author chianghao
 */
@Controller
@RequestMapping("company/setting")
public class CompanySettingAction {

	@Autowired
	CompanySettingService settingService;

	//////////////////////////////// 取大项目数据//////////////////////////////////////
	@RequestMapping("query_base_project")
	public JSONView queryCompanyBaseProject(@RequestParam(required = true, value = "companyId") String companyId)
			throws HaoException {
		List<CompanyBaseProject> list = settingService.queryCompanyBaseProjectList(companyId);
		return RequestContext.getJSONView(list);
	}
	@RequestMapping("save_base_project")
	public JSONView saveBaseProject() throws HaoException {
		settingService.saveBaseProject();
		return RequestContext.getJSONView();
	}
	@RequestMapping("del_base_project")
	public JSONView delBaseProject(@RequestParam(required = true, value = "id") String id) throws HaoException {
		settingService.delBaseProject(id);
		return RequestContext.getJSONView();
	}

	////////////////////////////// 项目/////////////////////////////////////////////
	@RequestMapping("query_project")
	public JSONView queryCompanyProject(@RequestParam(required = true, value = "companyId") String companyId)
			throws HaoException {
		List<CompanyProject> list = settingService.queryCompanyProjectList(companyId);
		return RequestContext.getJSONView(list);
	}
	@RequestMapping("save_project")
	public JSONView saveProject() throws HaoException {
		settingService.saveProject();
		return RequestContext.getJSONView();
	}
	@RequestMapping("del_project")
	public JSONView delProject(@RequestParam(required = true, value = "id") String id) throws HaoException {
		settingService.delProject(id);
		return RequestContext.getJSONView();
	}

	////////////////////////////// 部门/////////////////////////////////////////////
	@RequestMapping("query_department")
	public JSONView queryCompanyDepartment(@RequestParam(required = true, value = "companyId") String companyId)
			throws HaoException {
		List<CompanyDepartment> list = settingService.queryCompanyDepartment(companyId);
		return RequestContext.getJSONView(list);
	}
	@RequestMapping("save_department")
	public JSONView saveCompanyDepartment() throws HaoException {
		settingService.saveDepartment();
		return RequestContext.getJSONView();
	}
	@RequestMapping("del_department")
	public JSONView delCompanyDepartment(@RequestParam(required = true, value = "id") String id) throws HaoException {
		settingService.delDepartment(id);
		return RequestContext.getJSONView();
	}

	////////////////////////////// 职务/////////////////////////////////////////////
	@RequestMapping("query_point")
	public JSONView queryCompanyPoint(@RequestParam(required = true, value = "companyId") String companyId)
			throws HaoException {
		List<CompanyPoint> list = settingService.queryCompanyPoint(companyId);
		return RequestContext.getJSONView(list);
	}
	@RequestMapping("save_point")
	public JSONView saveCompanyPoint() throws HaoException {
		settingService.savePoint();
		return RequestContext.getJSONView();
	}
	@RequestMapping("del_point")
	public JSONView delCompanyPoint(@RequestParam(required = true, value = "id") String id) throws HaoException {
		settingService.delPoint(id);
		return RequestContext.getJSONView();
	}

	////////////////////////////// 产线/////////////////////////////////////////////
	@RequestMapping("query_production_line")
	public JSONView queryProductionLine(@RequestParam(required = true, value = "companyId") String companyId)
			throws HaoException {
		List<CompanyProductionLine> list = settingService.queryProductionLine(companyId);
		return RequestContext.getJSONView(list);
	}
	@RequestMapping("save_production_line")
	public JSONView saveCompanyProductionLine() throws HaoException {
		settingService.saveProductionLine();
		return RequestContext.getJSONView();
	}
	@RequestMapping("del_production_line")
	public JSONView delCompanyProductionLine(@RequestParam(required = true, value = "id") String id) throws HaoException {
		settingService.delProductionLine(id);
		return RequestContext.getJSONView();
	}

}
