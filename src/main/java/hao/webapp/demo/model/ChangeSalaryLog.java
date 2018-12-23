package hao.webapp.demo.model;

import java.math.BigDecimal;
import java.util.Date;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.webapp.demo.CommonData;

/**
 * 调薪记录
 * @author chianghao
 */
@Entity(title="调薪记录")
public class ChangeSalaryLog {

	@Attribute(title="主键",length=20)
	@Column(primary=true)
	private long id;
	
	@Attribute(title="员工")
	@Column
	private long employeeId;
	
	/**
	 * 员工信息
	 */
	public  Employee  employee;
	
	@Attribute(title="日期")
	@Column
	private Date date;
	
	/**
	 * 调薪前工资
	 */
	@Attribute(title="调薪前工资",length=20,precision=2)
	@Column
	private BigDecimal oldSalary;
	
	
	@Attribute(title="工资",length=20,precision=2)
	@Column
	private BigDecimal salary;
	
	@Attribute(title="外包公司")
	@Column
	private long companyId;
	
	private Company company;
	
	@Attribute(title="合同公司")
	@Column
	private String employer;
	
	@Attribute(title="工资结算方式",remark="1区分计算，2按照最新工资计算")
	@Column
	private String salaryType;
	
	
	private int year;
	
	private int month;
	
	/**
	 * 雇佣公司名称
	 */
	private String employerName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
		this.year  = date.getYear()+1;
		this.month = date.getMonth()+1;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
		this.setEmployerName(CommonData.getEmployerNameByCode(employer));
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getSalaryType() {
		return salaryType;
	}

	public void setSalaryType(String salaryType) {
		this.salaryType = salaryType;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	
	public static void main(String[] args) {
		String date = "2015-02-04";
		date = date.substring(0,10);
		int year = Integer.parseInt(date.split("-")[0]);
		int month = Integer.parseInt(date.split("-")[1]);
		System.out.println(year);
		System.out.println(month);
	}

	public BigDecimal getOldSalary() {
		return oldSalary;
	}

	public void setOldSalary(BigDecimal oldSalary) {
		this.oldSalary = oldSalary;
	}
	
	
}
