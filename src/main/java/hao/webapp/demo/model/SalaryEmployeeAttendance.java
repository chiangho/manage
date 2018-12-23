package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;

/**
 * 员工的考勤数据
 * @author chianghao
 *
 */
@Entity
public class SalaryEmployeeAttendance extends BaseModel{

	/***
	 * 所属工资嘻嘻你
	 */
	@Attribute(title="工资属性")
	@Column
	private long   salaryId;
	/**
	 * 省份证信息
	 */
	@Attribute(title="身份证")
	@Column
	private String idCard;
	/**
	 * 工号
	 */
	@Attribute(title="员工工号")
	@Column
	private String workerNo;
	/**
	 * 员工信息
	 */
	@Attribute(title="员工主键")
	@Column
	private long   employeeId;
	/***
	 * 考勤值
	 */
	@Attribute(title="考勤项的值")
	@Column
	private String value;
	/**
	 * 考情项目编号
	 */
	@Attribute(title="考勤项目")
	@Column
	private String attendanceItemId;
	
	@Attribute(title="外包公司信息")
	@Column
	private long   companyId;
	
	
	@Attribute(title="合同公司信息")
	@Column
	private String employerId;
	
	
	public long getSalaryId() {
		return salaryId;
	}
	public void setSalaryId(long salaryId) {
		this.salaryId = salaryId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getWorkerNo() {
		return workerNo;
	}
	public void setWorkerNo(String workerNo) {
		this.workerNo = workerNo;
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAttendanceItemId() {
		return attendanceItemId;
	}
	public void setAttendanceItemId(String attendanceItemId) {
		this.attendanceItemId = attendanceItemId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getEmployerId() {
		return employerId;
	}
	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}
	
}
