package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 公司考情汇总项目
 * @author chianghao
 *
 */
@Entity(title="公司考勤导入项")
public class CompanyAttendanceItemModel {

	@Attribute(title="主键")
	@Column(primary=true)
	private long id;
	
	@Attribute(title="公司主键")
	@Column
	private long companyId;
	
	@Attribute(title="名称")
	@Column
	private String name;
	
	/**
	 * 公司名称
	 */
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
	
}
