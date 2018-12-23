package hao.webapp.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hao.framework.core.expression.HaoException;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.webapp.demo.model.AppIncomeTax;
import hao.webapp.demo.service.AppIncomeTaxService;

@Controller
@RequestMapping("app")
public class AppIncomeTaxAction {
	
	@Autowired
	AppIncomeTaxService appIncomeTaxService;
	
	/***
	 * 保存
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("income_tax/save")
	public JSONView save() throws HaoException {
		AppIncomeTax tax = appIncomeTaxService.save();
		RequestContext.setModelData("data", tax);
		return RequestContext.getJSONView();
	}
	
	/**
	 * 加载
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("income_tax/load")
	public JSONView load() throws HaoException {
		List<AppIncomeTax> list = appIncomeTaxService.load_list();
		return RequestContext.getJSONView(list);
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("income_tax/del")
	public JSONView del(
			@RequestParam(defaultValue="",required=true,value="id") String id
			) throws HaoException {
		
		appIncomeTaxService.del(id);
		return RequestContext.getJSONView();
	}
	
}
