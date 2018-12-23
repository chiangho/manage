package hao.webapp.demo.model;


/**
 * 历史聘任信息
 */
import java.math.BigDecimal;
import java.util.Date;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.webapp.demo.CommonData;

/**
 * 雇佣记录
 * @author  chianghao
 * @time    2018-03-31
 * @version 0.1
 */
@Entity(title="雇佣记录")
public class HistoryEmploymentInfo {
	
	@Attribute(title="结算批次")
	@Column
	private long salaryBatchId;
	
	@Attribute(title="身份证")
	@Column
	private String idCard;//身份证
	
	@Attribute(title="公司")
	@Column
	private String companyId;//所在公司
	
	private Company company;
	
	@Attribute(title="雇佣公司")
	@Column
	private String employerId;//雇佣者
	private String employerName;
	
	@Attribute(title="合同形式",remark="1普通合同，2小时工，3零时工，4实习生")
	@Column
	private String employmentType;//工种
	private String employmentTypeName;
	
	@Attribute(title="入职日期")
	@Column
	private Date   beginTime;//入职日期
	
	@Attribute(title="离职日期")
	@Column(isNull=true)
	private Date   leaveDate;//离职日期
	
	
	@Attribute(title="离职类型",remark="1旷工自离，2非旷工自离，3辞职，4开除")
	@Column(isNull=true)
	private int    leaveType;//离职日期
	private String leaveTypeName;
	
	@Attribute(title="办理离职日期")
	@Column(isNull=true)
	private Date   realyLeaveDate;
	
	@Attribute(title="合同编号")
	@Column(isNull=true)
	private String contractNo;//合同编号
	
	@Attribute(title="银行")
	@Column(isNull=true)
	private String bankType;//银行种类
	private String bankTypeName;
	
	@Attribute(title="银行卡")
	@Column(isNull=true)
	private String bankCard;//银行卡
	
	@Attribute(title="工号")
	@Column
	private String workerNo;//工号
	
	@Attribute(title="状态",remark="状态-2离职 -1停职  0在职")
	@Column(defaultValue="0")
	private int    state;//
	
	@Attribute(title="基本工资")
	@Column
	private BigDecimal baseSalary;
	
	@Attribute(title="大项目")
	@Column
	private String   baseProjectName;
	
	
	@Attribute(title="项目")
	@Column
	private String   projectName;
	
	
	@Attribute(title="产线")
	@Column
	private String productionLineName;//产线
	
	
	@Attribute(title="部门")
	@Column
	private String   departmentName;
	
	
	@Attribute(title="职务")
	@Column
	private String   pointName;
	
	@Attribute(title="社保类型",remark="0不发，1当地最低标准 ，2 实际工资，3给定具体值")
	@Column
	private int  socialSecurityType;
	
	@Attribute(title="社保额度",remark="通过社保类型计算获取")
	@Column(isNull=true)
	private BigDecimal  socialSecurity;
	
	@Attribute(title="社保缴法",remark="0缴费到账号中，1暂扣离职时作为补偿经")
	@Column
	private int  socialSecurityAlgorithm;
	
	/**
	 * 员工信息
	 */
	private Employee employee;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public String getEmploymentTypeName() {
		return employmentTypeName;
	}
	public void setEmploymentTypeName(String employmentTypeName) {
		this.employmentTypeName = employmentTypeName;
	}
	public String getBankTypeName() {
		return bankTypeName;
	}
	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getEmployerId() {
		return employerId;
	}
	public void setEmployerId(String employerId) {
		this.employerId = employerId;
		this.setEmployerName(CommonData.getEmployerNameByCode(employerId));
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
		this.setEmploymentTypeName(CommonData.getEmploymentTypeNameByCode(employmentType));
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
		this.setBankTypeName(CommonData.getBankTypeNameByCode(bankType));
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getWorkerNo() {
		return workerNo;
	}
	public void setWorkerNo(String workerNo) {
		this.workerNo = workerNo;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(int leaveType) {
		this.leaveType = leaveType;
		this.leaveTypeName = CommonData.getleaveTypeNameByCode(leaveType+"");
	}
	public Date getRealyLeaveDate() {
		return realyLeaveDate;
	}
	public void setRealyLeaveDate(Date realyLeaveDate) {
		this.realyLeaveDate = realyLeaveDate;
	}
	public BigDecimal getBaseSalary() {
		return baseSalary;
	}
	public void setBaseSalary(BigDecimal baseSalary) {
		this.baseSalary = baseSalary;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	public int getSocialSecurityType() {
		return socialSecurityType;
	}
	public void setSocialSecurityType(int socialSecurityType) {
		this.socialSecurityType = socialSecurityType;
	}
	public BigDecimal getSocialSecurity() {
		return socialSecurity;
	}
	public void setSocialSecurity(BigDecimal socialSecurity) {
		this.socialSecurity = socialSecurity;
	}
	public int getSocialSecurityAlgorithm() {
		return socialSecurityAlgorithm;
	}
	public void setSocialSecurityAlgorithm(int socialSecurityAlgorithm) {
		this.socialSecurityAlgorithm = socialSecurityAlgorithm;
	}
	public long getSalaryBatchId() {
		return salaryBatchId;
	}
	public void setSalaryBatchId(long salaryBatchId) {
		this.salaryBatchId = salaryBatchId;
	}
	public String getBaseProjectName() {
		return baseProjectName;
	}
	public void setBaseProjectName(String baseProjectName) {
		this.baseProjectName = baseProjectName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProductionLineName() {
		return productionLineName;
	}
	public void setProductionLineName(String productionLineName) {
		this.productionLineName = productionLineName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
}
