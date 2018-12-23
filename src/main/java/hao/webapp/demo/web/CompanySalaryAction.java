package hao.webapp.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.CompanySalaryItemModel;
import hao.webapp.demo.service.CompanySalaryService;
import hao.webapp.demo.service.CompanyService;

/**
 * 工资项
 * @author chianghao
 * @time   2018年5月22日
 */
@Controller
@RequestMapping("company/salary_item")
public class CompanySalaryAction {
	
	@Autowired
	private CompanySalaryService companySalaryService;
	
	@Autowired
	private CompanyService companyService;

	
	@RequestMapping("page_list.action")
	public JSONView page_list(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo
			) throws HaoException {
		Page page = new Page(pageNo,pageSize);
		List<CompanySalaryItemModel> list = companySalaryService.queryPageList(page);
		return RequestContext.getJSONView(list);
	}
	
	@RequestMapping("load_company_salary_item_list.action")
	public JSONView loadCompanySalaryItemList(
			@RequestParam(required=true,value="companyId") String companyId
			) throws HaoException {
		List<CompanySalaryItemModel> list = companySalaryService.queryByCompanyId(companyId);
		RequestContext.setModelData("list", list);
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("save.action")
	public JSONView save() throws HaoException {
		companySalaryService.save();
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("load_company_data.action")
	public JSONView load_company_data(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		CompanySalaryItemModel dataItem = this.companySalaryService.queryById(id);
		return RequestContext.getJSONView(dataItem);
	}
	
	@RequestMapping("del.action")
	public JSONView del(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		companySalaryService.del(id);
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("load_sun_dataitem.action")
	public ModelAndView load_sun_dataitem(
			@RequestParam(required=true,value="companyId") String companyId,
			@RequestParam(required=true,value="selfId") String selfId
			) throws HaoException {
		List<String> list = companyService.queryCompanyDateItem(companyId);
		RequestContext.setModelData("list", list);
		return RequestContext.getView("company/data/load_sun_dataitem");
	}
	
}
