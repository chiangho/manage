package hao.webapp.demo.view;

import java.util.List;

import hao.webapp.demo.model.Salary;

/**
 * 员工工资数据
 * @author chianghao
 *
 */
public class EmployerSalaryData {

	//工资批次
	private String salaryId;
	//工资批次详细信息
	private Salary salary;
	//表头
	private List<String> headers;
	//表头对应的数据
	private List<Object[]> datas;
	
	
	public String getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(String salaryId) {
		this.salaryId = salaryId;
	}
	public Salary getSalary() {
		return salary;
	}
	public void setSalary(Salary salary) {
		this.salary = salary;
	}
	public List<String> getHeaders() {
		return headers;
	}
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}
	public List<Object[]> getDatas() {
		return datas;
	}
	public void setDatas(List<Object[]> datas) {
		this.datas = datas;
	}
	
}
