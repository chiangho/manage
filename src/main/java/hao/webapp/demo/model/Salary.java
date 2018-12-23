package hao.webapp.demo.model;

import java.util.Date;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/**
 * 工资
 * @author chianghao
 *
 */
@Entity(title="工资批次")
public class Salary extends BaseModel{

	@Attribute(title="年",length=4)
	@Column
	private int year;
	
	@Attribute(title="月",length=2)
	@Column
	private int month;
	
	@Attribute(title="工资开始日期",timeFormat="yyyy-MM-dd")
	@Column
	private Date beginDate;
	

	@Attribute(title="工资结束日期",timeFormat="yyyy-MM-dd")
	@Column
	private Date endDate;
	
	@Attribute(title="结算公司")
	@Column
	private long companyId;
	
	private Company company;
	
	
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
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	
}
