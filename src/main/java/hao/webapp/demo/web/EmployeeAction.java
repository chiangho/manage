package hao.webapp.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.utils.StringUtils;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.framework.xls.ExcelTool;
import hao.framework.xls.XLSData;
import hao.webapp.demo.model.EmploymentInfo;
import hao.webapp.demo.service.ChangeSalaryLogService;
import hao.webapp.demo.service.EmployeeService;

/**
 * 雇员信息处理
 * @author  chianghao
 * @time    2018年4月1日
 * @version 0.1
 */
@Controller
@RequestMapping("employee")
public class EmployeeAction {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ChangeSalaryLogService changeSalaryLogService;
	
	@RequestMapping("page_list")
	public JSONView page_list(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo,
			@RequestParam(required=false,value="searchKey",defaultValue="") String searchKey
			) throws HaoException {
		Page page = new Page(pageNo,pageSize);
		List<EmploymentInfo> list = employeeService.queryPageList(page,searchKey);
		return RequestContext.getJSONView(page.getPageData(list));
	}

	/**
	 * 导入员工
	 * @return
	 * @throws HaoException 
	 */
	@RequestMapping("import_employee")
	public JSONView importEmployee(@RequestParam("file") MultipartFile file) throws HaoException {
		String originalFilename = file.getOriginalFilename();//文件名称
		if(!ExcelTool.isExcel(originalFilename)) {
			throw new HaoException("999999","请上传EXCEL文档");
		}
		XLSData data = null;
		try {
			ExcelTool xls = new ExcelTool(file.getInputStream(),originalFilename);
			data = xls.readToList(0);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		List<String> error = new ArrayList<String>();
		employeeService.importEmployee(data,error);
		RequestContext.setModelData("error", StringUtils.arrayToStr(",", error));
		return RequestContext.getJSONView();
	}
	
	
	//员工调薪请求
	@RequestMapping("sub_change_salary")
	public JSONView subChangeSalary() throws HaoException {
		changeSalaryLogService.save();
		return RequestContext.getJSONView();
	}
	
	
	@RequestMapping("load_leave_info")
	public JSONView loadLeaveInfo(
			@RequestParam(required=false,value="employerId",defaultValue="") String employerId
			) throws HaoException {
		EmploymentInfo info = employeeService.queryEmploymentInfo(employerId);
		RequestContext.setModelData("data", info);
		return RequestContext.getJSONView();
	}
	
	
	public JSONView saveLeaveInfo() {
		
		return RequestContext.getJSONView();
	}
	
}
