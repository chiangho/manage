package hao.webapp.demo.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hao.framework.core.expression.HaoException;
import hao.framework.db.page.Page;
import hao.framework.utils.StringUtils;
import hao.framework.web.RequestContext;
import hao.framework.web.view.JSONView;
import hao.framework.xls.ExcelTool;
import hao.framework.xls.XLSData;
import hao.webapp.demo.model.Salary;
import hao.webapp.demo.model.SalaryAllowance;
import hao.webapp.demo.model.SalaryAppIncomeTax;
import hao.webapp.demo.model.SalaryAppSetting;
import hao.webapp.demo.model.SalaryAttendanceItem;
import hao.webapp.demo.model.SalaryItem;
import hao.webapp.demo.service.SalaryAppIncomeTaxService;
import hao.webapp.demo.service.SalaryAppSettingService;
import hao.webapp.demo.service.SalaryService;
import hao.webapp.demo.view.EmployerSalaryData;

/**
 * 工资发放
 * @author chianghao
 *
 */
@Controller
@RequestMapping("salary")
public class SalaryAction {

	
	@Autowired
	SalaryService salaryService;
	
	
	@Autowired
	SalaryAppSettingService salaryAppSettingService;
	
	@Autowired
	SalaryAppIncomeTaxService salaryAppIncomeTaxService;
	
	
	@RequestMapping("load_list")
	public JSONView loadList(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo
			) throws HaoException {
		Page page = new Page(pageNo,pageSize);
		List<Salary> list = salaryService.loadList(page);
		return RequestContext.getJSONView(page.getPageData(list));
	}
	
	@RequestMapping("save")
	public JSONView save() throws Exception {
		salaryService.save();
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("load_salary")
	public JSONView load_salary(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		Salary salary = salaryService.loadSalary(id);
		return RequestContext.getJSONView(salary,"yyyy-MM-dd");
	}
	
	@RequestMapping("del")
	public JSONView del(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		this.salaryService.del(id);
		return RequestContext.getJSONView();
	}
	
	/**
	 * 获取考勤导入项的信息
	 * @param pageSize
	 * @param pageNo
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("load_attendance_list")
	public JSONView load_attendance_list(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo,
			@RequestParam(required=true,value="salaryId") String salaryId
			) throws HaoException {
		Page page = new Page(pageNo,pageSize);
		List<SalaryAttendanceItem> list = salaryService.querySalaryAttendanceItemList(page,salaryId);
		return RequestContext.getJSONView(page.getPageData(list));
	}
	
	
	@RequestMapping("del_attendance")
	public JSONView del_attendance(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		this.salaryService.delAttendance(id);
		return RequestContext.getJSONView();
	}
	/**
	 * 加载考勤导入项
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("load_attendance")
	public JSONView load_attendance(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		SalaryAttendanceItem item =  this.salaryService.loadAttendance(id);
		return RequestContext.getJSONView(item);
	}
	/**
	 * 保存考勤导入想
	 * @return
	 * @throws HaoException 
	 */
	@RequestMapping("save_attendance")
	public JSONView save_attendance() throws HaoException {
		salaryService.saveAttendance();
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("import_attendance")
	public JSONView importAttendance(
			@RequestParam(required=true,value="salaryId") long salaryId
			) throws HaoException {
		salaryService.importAttendance(salaryId);
		return RequestContext.getJSONView();
	}
	
	/**
	 * 从公司数据中选中复制到工资项目。用于初始化工资项目
	 * @param companySalaryIds
	 * @param salaryId
	 * @param companyId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("import_salary_item")
	public JSONView importSalaryItem(
			@RequestParam(required=true,value="companySalaryIds") String[] companySalaryIds,
			@RequestParam(required=true,value="salaryId") long salaryId,
			@RequestParam(required=true,value="companyId") long companyId
			) throws HaoException {
		salaryService.importSalaryItem(salaryId,companyId,companySalaryIds);
		return RequestContext.getJSONView();
	}
	
	/**
	 * 保存或者修改工资项目
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("save_salary_item")
	public JSONView saveSalaryItem() throws HaoException {
		salaryService.saveSalaryItem();
		return RequestContext.getJSONView();
	}
	
	/**
	 * 删除工资项
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("del_salary_item")
	public JSONView delSalaryItem(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		this.salaryService.delSalaryItem(id);
		return RequestContext.getJSONView();
	}
	/**
	 * 根据主键获取单个工资项
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("load_salary_item")
	public JSONView loadSalaryItem(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		SalaryItem item = this.salaryService.loadSalaryItemById(id);
		return RequestContext.getJSONView(item);
	}
	
	/**
	 * 加载分页的工资项目
	 * @param pageSize
	 * @param pageNo
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("load_salary_item_page")
	public JSONView load_salary_item_page(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo,
			@RequestParam(required=true,value="salaryId") String salaryId
			) throws HaoException {
		Page page = new Page(pageNo,pageSize);
		List<SalaryItem> list = salaryService.querySalaryItemPage(page,salaryId);
		return RequestContext.getJSONView(page.getPageData(list));
	}
	/**
	 * 加载工资的
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("query_salary_computing_item")
	public JSONView querySalaryComputingItem(
			@RequestParam(required=true,value="salaryId") String salaryId,
			@RequestParam(required=true,value="companyId") String compileId
			) throws HaoException {
		Set<String> items = this.salaryService.querySalaryComputingItem(salaryId,compileId);
		List<String> list = new ArrayList<String>();
		for(String item:items) {
			list.add(item);
		}
		return RequestContext.getJSONView(list);
	}
	
	
	/**
	 * 加载某次工资的社保比例
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("load_salary_app_setting")
	public JSONView loadSalaryAppSetting(
			@RequestParam(required=true,value="salaryId") long salaryId
			) throws HaoException {
		SalaryAppSetting salaryAppSetting  = this.salaryAppSettingService.querySalaryAppSetting(salaryId+"");
		return RequestContext.getJSONView(salaryAppSetting);
	}
	
	/**
	 * 覆盖
	 * @param salaryId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cover_app_setting")
	public JSONView coverAppSetting(
			@RequestParam(required=true,value="salaryId") long salaryId
			) throws Exception {
		this.salaryAppSettingService.coverSalaryAppSetting(salaryId+"");
		return  RequestContext.getJSONView();
	}
	
	/**
	 * 保存社工比例
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("save_salary_app_setting")
	public JSONView saveSalaryCoverAppSetting(
			@RequestParam(required=true,value="salaryId") long salaryId
			) throws HaoException {
		this.salaryAppSettingService.settingSave(salaryId+"");
		return  RequestContext.getJSONView();
	}
	
	@RequestMapping("load_salary_app_income_tax")
	public JSONView loadSalaryAppIncomeTax(
			@RequestParam(required=true,value="salaryId") long salaryId
			) throws HaoException {
		List<SalaryAppIncomeTax> list = this.salaryAppIncomeTaxService.load_list(salaryId+"");
		return  RequestContext.getJSONView(list);
	}
	
	/**
	 * 覆盖
	 * @param salaryId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("cover_app_income_tax")
	public JSONView coverAppIncomeTax(
			@RequestParam(required=true,value="salaryId") long salaryId
			) throws Exception {
		this.salaryAppIncomeTaxService.coverSalaryAppIncomeTax(salaryId);
		return  RequestContext.getJSONView();
	}
	
	/**
	 * 保存个税比例
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("save_app_income_tax")
	public JSONView saveSalaryincomeAppSetting(
			@RequestParam(required=true,value="salaryId") long salaryId
			) throws HaoException {
		this.salaryAppIncomeTaxService.save();
		return  RequestContext.getJSONView();
	}
	/**
	 * 保存个税比例
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("del_app_income_tax")
	public JSONView delSalaryincomeAppSetting(
			@RequestParam(required=true,value="id") long id
			) throws HaoException {
		this.salaryAppIncomeTaxService.del(id+"");
		return  RequestContext.getJSONView();
	}
	
	
	@RequestMapping("load_allowance_item_page")
	public JSONView load_allowance_item_page(
			@RequestParam(required=true,value=Page.PAGE_SIZE,defaultValue="10") int pageSize,
			@RequestParam(required=true,value=Page.PAGE_NO,defaultValue="1") int pageNo,
			@RequestParam(required=true,value="salaryId") String salaryId,
			@RequestParam(required=true,value="companyId") String companyId
			) throws HaoException {
		Page page = new Page(pageNo,pageSize);
		List<SalaryAllowance> list = salaryService.queryAllowanceItemPage(page,salaryId,companyId);
		return RequestContext.getJSONView(page.getPageData(list));
	}
	
	/**
	 * 根据主键获取单个工资项
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("load_allowance_item")
	public JSONView load_allowance_item(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		SalaryAllowance item = this.salaryService.loadAllowanceItemById(id);
		return RequestContext.getJSONView(item);
	}
	
	/**
	 * 保存或者修改工资项目
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("save_allowance_item")
	public JSONView saveAllowanceItem() throws HaoException {
		salaryService.saveAllowanceItem();
		return RequestContext.getJSONView();
	}
	
	/**
	 * 删除工资项
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("del_allowance_item")
	public JSONView delAllowanceItem(
			@RequestParam(required=true,value="id") String id
			) throws HaoException {
		this.salaryService.delSalaryAllowanceItem(id);
		return RequestContext.getJSONView();
	}
	
	/***
	 * 导入员工的考勤
	 * @return
	 * @throws HaoException 
	 */
	@RequestMapping("import_employee_attendance")
	public JSONView import_employee_attendance(
			@RequestParam("importAttendanceFile") MultipartFile file,
			@RequestParam("salaryId") String salaryId,
			@RequestParam("companyId") String companyId
			) throws HaoException {
		try {
			ExcelTool excelTool = new ExcelTool(file.getInputStream(),file.getOriginalFilename());
			XLSData xlsData = excelTool.readToList(0);
			this.salaryService.importAttendance(xlsData,salaryId,companyId);
		}catch(Exception e) {
			throw new HaoException("999999",e.getMessage());
		}
		return RequestContext.getJSONView();
	}
	
	/**
	 * 导出员工的津贴
	 * @param file
	 * @param salaryId
	 * @param companyId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("import_employee_allowance")
	public JSONView import_employee_allowance(
			@RequestParam("importAllowanceFile") MultipartFile file,
			@RequestParam("salaryId") String salaryId,
			@RequestParam("companyId") String companyId
			) throws HaoException {
		try {
			ExcelTool excelTool = new ExcelTool(file.getInputStream(),file.getOriginalFilename());
			List<String> error = this.salaryService.importEmployeeAllowance(excelTool,salaryId,companyId);
			if(error!=null&&error.size()>0) {
				throw new HaoException("999999",StringUtils.listToString(error, '。'));
			}
		}catch(Exception e) {
			throw new HaoException("999999",e.getMessage());
		}
		return RequestContext.getJSONView();
	}
	
	
	/**
	 * 计算工资，获取应发和扣发等数据
	 * @param salaryId
	 * @param companyId
	 * @return
	 * @throws HaoException
	 */
	@RequestMapping("calculation_salary")
	public JSONView calculation_salary(
			@RequestParam("salaryId") String salaryId,
			@RequestParam("companyId") String companyId
			) throws HaoException {
		List<String> error = this.salaryService.computeSalary(salaryId);
		if(error!=null&&error.size()>0) {
			throw new HaoException("999999",StringUtils.listToString(error,'。'));
		}
		return RequestContext.getJSONView();
	}
	
	@RequestMapping("view_salary_data")
	public ModelAndView viewSalaryData(
			@RequestParam("salaryId") String salaryId
			) throws HaoException {
		EmployerSalaryData data = salaryService.getEmployyerSalaryData(salaryId);
		RequestContext.setModelData("data", data);
		return RequestContext.getView("salary/viewSalaryData");
	}
	
}
