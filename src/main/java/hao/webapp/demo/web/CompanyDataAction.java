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
import hao.webapp.demo.model.CompanyDataItemModel;
import hao.webapp.demo.service.CompanyDataItemService;

@Controller
@RequestMapping("company/data")
public class CompanyDataAction {
	
	@Autowired
	CompanyDataItemService companyDataItemService;

	@RequestMapping("page_list.action")
	public JSONView page_list(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo
			) throws HaoException {
		Page page = new Page(pageNo,pageSize);
		List<CompanyDataItemModel> list = companyDataItemService.queryPageList(page);
		return RequestContext.getJSONView(list);
	}
	
	@RequestMapping("save.action")
	public JSONView save() throws HaoException {
		companyDataItemService.save();
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("load_company_data.action")
	public JSONView load_company_data(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		CompanyDataItemModel dataItem = this.companyDataItemService.queryById(id);
		return RequestContext.getJSONView(dataItem);
	}
	
	@RequestMapping("del.action")
	public JSONView del(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		companyDataItemService.del(id);
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("load_sun_dataitem.action")
	public ModelAndView load_sun_dataitem(
			@RequestParam(required=true,value="companyId") String companyId,
			@RequestParam(required=true,value="selfId") String selfId
			) throws HaoException {
		List<String> list = companyDataItemService.querySumDateItem(companyId,selfId);
		RequestContext.setModelData("list", list);
		return RequestContext.getView("company/data/load_sun_dataitem");
	}
	
}
