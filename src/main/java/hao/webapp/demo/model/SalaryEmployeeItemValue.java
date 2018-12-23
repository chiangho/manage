package hao.webapp.demo.model;

import java.math.BigDecimal;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/***
 * 员工各工资项的值
 * @author chianghao
 *
 */
@Entity
public class SalaryEmployeeItemValue {

	/**
	 * 工资
	 */
	@Attribute(title="工资")
	@Column
	private long        salaryId;
	/**
	 * 外包公司
	 */
	@Attribute(title="外包公司")
	@Column
	private long        companyId;
	/**
	 * 项目主键
	 */
	@Attribute(title="所属工资项目")
	@Column
	private long        itemId;
	
	
	@Attribute(title="所属项目名称")
	@Column
	private String      itemName;
	/**
	 * 值
	 */
	@Attribute(title="值")
	@Column
	private BigDecimal  vlaue;
	/**
	 * 身份证
	 */
	@Attribute(title="身份证")
	@Column
	private String      idCard;
	/**
	 * 员工主键
	 */
	@Attribute(title="员工主键")
	@Column
	private long        employeeId;
	/**
	 * 工号
	 */
	@Attribute(title="工号")
	@Column
	private String      workerId;
	
	
	public long getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(long salaryId) {
		this.salaryId = salaryId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public BigDecimal getVlaue() {
		return vlaue;
	}
	public void setVlaue(BigDecimal vlaue) {
		this.vlaue = vlaue;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	
	
	
}
