package hao.webapp.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.core.sequence.Seq;
import hao.framework.db.sql.SqlCommon.SqlSort;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWrite;
import hao.framework.db.sql.SqlWrite.SqlWriterAction;
import hao.framework.service.BaseService;
import hao.framework.web.RequestContext;
import hao.webapp.demo.model.ChangeSalaryLog;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.model.Employee;
import hao.webapp.demo.model.EmploymentInfo;


@Service
public class ChangeSalaryLogService extends BaseService{

	/**
	 * 保存
	 * 保存的逻辑
	 * 校验员工的调薪记录是否存在（条件：日期和员工编号），如果存在则做update操作
	 * 如果不存在则做insert操作
	 * @throws HaoException 
	 */
	public void save() throws HaoException {
		String date       = RequestContext.getParam("date");
		String employeeId = RequestContext.getParam("employeeId");
		String salary     = RequestContext.getParam("salary");
		
		SqlSearch searchEmployment = new SqlSearch(EmploymentInfo.class);
		searchEmployment.join(Employee.class, "idCard", "idCard");
		searchEmployment.addCondition(Employee.class, "id", employeeId);
		EmploymentInfo employee = this.jdbcDao.queryBean(searchEmployment);
		
		if(employee==null) {
			throw new HaoException("999999","员工的雇佣关系不存在");
		}
		
		SqlSearch search = new SqlSearch(ChangeSalaryLog.class);
		search.addCondition("employeeId",employeeId);
		search.addCondition("date", date);
		ChangeSalaryLog oldChangeSalaryLog  = this.jdbcDao.queryBean(search);
		
		Map<String,Object> map = RequestContext.getBeanMap(ChangeSalaryLog.class);
		map.put("companyId", employee.getCompanyId());
		map.put("employer", employee.getEmployerName());
		SqlWrite sqlWriter = null;
		if(oldChangeSalaryLog==null) {
			//插入
			//获取此员工的历史基本工资
			map.put("oldSalary", employee.getBaseSalary());
			map.put("id", Seq.getNextId());
			sqlWriter = new SqlWrite(ChangeSalaryLog.class,SqlWriterAction.insert);
			sqlWriter.setFields(map);
		}else {
			BigDecimal salaryDecimal    = new BigDecimal(salary);
			BigDecimal oldSalaryDecimal = oldChangeSalaryLog.getSalary();
			if(salaryDecimal.compareTo(oldSalaryDecimal)==0) {
				throw new HaoException("999999","薪资调整前后一直，无需调整！");
			}
			
			//更新
			sqlWriter = new SqlWrite(ChangeSalaryLog.class,SqlWriterAction.update);
			sqlWriter.setFields(map);
			sqlWriter.addCondition("employeeId",employeeId);
			sqlWriter.addCondition("date", date);
		}
		this.jdbcDao.execute(sqlWriter);
	}
	
	/**
	 * 查询员工的调薪日期
	 * @param employeeId
	 * @return
	 * @throws HaoException
	 */
	public List<ChangeSalaryLog> queryEmployeeChangeSalaryLog(String employeeId) throws HaoException{
		SqlSearch search = new SqlSearch(ChangeSalaryLog.class);
		search.addCondition("employeeId",employeeId);
		search.join(Employee.class, new String[] {"id","name"}, "employeeId", "id");
		search.join(Company.class, "companyId", "id");
		search.order("date", SqlSort.desc);
		return jdbcDao.queryList(search);
	}
	
	/**
	 * 删除
	 * @param id
	 * @throws HaoException
	 */
	public void del(String id) throws HaoException {
		this.jdbcDao.del(ChangeSalaryLog.class,"id", id);
	}
	
}
