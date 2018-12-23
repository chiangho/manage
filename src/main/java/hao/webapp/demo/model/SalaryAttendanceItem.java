package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 工资结算需要的考勤项目
 * @author chianghao
 *
 */
@Entity(title="工资的考勤导入项")
public class SalaryAttendanceItem {

	@Attribute(title="主键",length=20)
	@Column(primary=true)
	private long id;
	
	@Attribute(title="公司主键")
	@Column
	private long companyId;
	
	@Attribute(title="名称")
	@Column
	private String name;
	
	@Attribute(title="工资批次")
	@Column
	private long salaryId;
	
	//所在公司
	private Company company;
	
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(long salaryId) {
		this.salaryId = salaryId;
	}
	
	
	
}
