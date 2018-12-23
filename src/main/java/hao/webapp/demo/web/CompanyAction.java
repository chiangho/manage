package hao.webapp.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.service.CompanyService;

@Controller
@RequestMapping("company")
public class CompanyAction {

	@Autowired
	CompanyService companyService;
	
	@RequestMapping("page_list")
	public JSONView queryCompanyList(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo
			) throws HaoException {
		
		Page page = new Page(pageNo,pageSize);
		List<Company> list= companyService.queryPageList(page);
		return RequestContext.getJSONView(page.getPageData(list));
	}
	
	
	@RequestMapping("load")
	public JSONView load(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		Company company= companyService.queryBean(id);
		return RequestContext.getJSONView(company);
	}
	
	@RequestMapping("save")
	public JSONView save() throws HaoException {
		companyService.saveCompany();
		return RequestContext.getJSONView();
	}
	
	/**
	 * 销毁
	 * @param id
	 * @return
	 */
	@RequestMapping("destroy")
	public JSONView destroy(
			@RequestParam("id") String id
			) {
		companyService.destroy(id);
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("del")
	public JSONView del(
			@RequestParam("id") String id
			) throws HaoException {
		companyService.del(id);
		return RequestContext.getJSONView();
	}
	
	
	@RequestMapping("load_company")
	public JSONView load_company() throws HaoException {
		List<Company> companys = companyService.queryAll();
		return RequestContext.getJSONView(companys);
	}
}
