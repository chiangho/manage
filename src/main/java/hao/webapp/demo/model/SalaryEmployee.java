package hao.webapp.demo.model;

import java.math.BigDecimal;
import java.util.Date;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;

/**
 * 参与结算工资的员工
 * @author chianghao
 *
 */
@Entity
public class SalaryEmployee {

	@Attribute(title="主键",length=20,precision=2)
	@Column(primary=true)
	private long id;
	
	@Attribute(title="工资信息",length=20,precision=2)
	@Column
	private long salaryId;
	
	@Attribute(title="员工信息主键",length=20,precision=2)
	@Column
	private long employeeId;
	
	@Attribute(title="身份证")
	@Column
	private String    idCard;
	
	@Attribute(title="姓名")
	@Column
	private String    name;
	
	@Attribute(title="性别")
	@Column(isNull=true)
	private String    sexName;
	
	@Attribute(title="电话1")
	@Column(isNull=true)
	private String      phone1;
	
	@Attribute(title="电话2")
	@Column(isNull=true)
	private String      phone2;

	@Attribute(title="邮箱")
	@Column(isNull=true)
	private String    email;
	
	@Attribute(title="照片")
	@Column(isNull=true)
	private String    icon;
	
	@Attribute(title="民族")
	@Column(isNull=true)
	private String    nationName;
	
	@Attribute(title="学历")
	@Column(isNull=true)
	private String    educationName;
	
	@Attribute(title="籍贯")
	@Column(isNull=true)
	private String    placeOfOrigin;
	
	@Attribute(title="来源省")
	@Column(isNull=true)
	private String    sourceProvince;
	
	@Attribute(title="来源城市")
	@Column(isNull=true)
	private String    sourceCity;
	
	@Attribute(title="来源区县")
	@Column(isNull=true)
	private String    sourceArea;
	
	@Attribute(title="来源地址")
	@Column(isNull=true)
	private String    sourceAddress;
	
	@Attribute(title="现居地")
	@Column(isNull=true)
	private String    livingPlace;
	
	@Attribute(title="生日")
	@Column(isNull=true)
	private String    birthday;
	
	
	
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
	
	@Attribute(title="基本工资",length=20,precision=2)
	@Column
	private BigDecimal baseSalary;
	
	@Attribute(title="大项目")
	@Column(isNull=true)
	private String   baseProjectId;
	
	private CompanyBaseProject baseProject;
	
	@Attribute(title="项目")
	@Column(isNull=true)
	private String   projectId;
	
	private CompanyProject project;
	
	@Attribute(title="产线")
	@Column(isNull=true)
	private String productionLineId;//产线
	
	private CompanyProductionLine line;
	
	@Attribute(title="部门")
	@Column(isNull=true)
	private String   departmentId;
	
	private CompanyDepartment department;
	
	@Attribute(title="职务")
	@Column(isNull=true)
	private String   pointId;
	
	private CompanyPoint point;
	
	@Attribute(title="社保类型",remark="0不发，1当地最低标准 ，2 实际工资，3给定具体值")
	@Column
	private int  socialSecurityType;
	
	@Attribute(title="社保额度",remark="通过社保类型计算获取")
	@Column(isNull=true)
	private BigDecimal  socialSecurity;
	
	@Attribute(title="社保缴法",remark="0缴费到账号中，1暂扣离职时作为补偿经")
	@Column
	private int  socialSecurityAlgorithm;

	@Attribute(title="公积金类型",remark="0不发，1当地最低标准 ，2 应发工资")
	@Column
	private int gjjType;
	
	@Attribute(title="应发工资",length=20,precision=2)
	@Column
	private BigDecimal yfgz;
	
	@Attribute(title="扣税",length=20,precision=2)
	@Column
	private BigDecimal shui;
	
	@Attribute(title="税前工资",length=20,precision=2)
	@Column
	private BigDecimal sqgz;
	
	@Attribute(title="实发工资",length=20,precision=2)
	@Column
	private BigDecimal sfgz;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(long salaryId) {
		this.salaryId = salaryId;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	public String getPlaceOfOrigin() {
		return placeOfOrigin;
	}

	public void setPlaceOfOrigin(String placeOfOrigin) {
		this.placeOfOrigin = placeOfOrigin;
	}

	public String getSourceProvince() {
		return sourceProvince;
	}

	public void setSourceProvince(String sourceProvince) {
		this.sourceProvince = sourceProvince;
	}

	public String getSourceCity() {
		return sourceCity;
	}

	public void setSourceCity(String sourceCity) {
		this.sourceCity = sourceCity;
	}

	public String getSourceArea() {
		return sourceArea;
	}

	public void setSourceArea(String sourceArea) {
		this.sourceArea = sourceArea;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getLivingPlace() {
		return livingPlace;
	}

	public void setLivingPlace(String livingPlace) {
		this.livingPlace = livingPlace;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getEmploymentTypeName() {
		return employmentTypeName;
	}

	public void setEmploymentTypeName(String employmentTypeName) {
		this.employmentTypeName = employmentTypeName;
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

	public int getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(int leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public Date getRealyLeaveDate() {
		return realyLeaveDate;
	}

	public void setRealyLeaveDate(Date realyLeaveDate) {
		this.realyLeaveDate = realyLeaveDate;
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
	}

	public String getBankTypeName() {
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
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

	public BigDecimal getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(BigDecimal baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getBaseProjectId() {
		return baseProjectId;
	}

	public void setBaseProjectId(String baseProjectId) {
		this.baseProjectId = baseProjectId;
	}

	public CompanyBaseProject getBaseProject() {
		return baseProject;
	}

	public void setBaseProject(CompanyBaseProject baseProject) {
		this.baseProject = baseProject;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public CompanyProject getProject() {
		return project;
	}

	public void setProject(CompanyProject project) {
		this.project = project;
	}

	public String getProductionLineId() {
		return productionLineId;
	}

	public void setProductionLineId(String productionLineId) {
		this.productionLineId = productionLineId;
	}

	public CompanyProductionLine getLine() {
		return line;
	}

	public void setLine(CompanyProductionLine line) {
		this.line = line;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public CompanyDepartment getDepartment() {
		return department;
	}

	public void setDepartment(CompanyDepartment department) {
		this.department = department;
	}

	public String getPointId() {
		return pointId;
	}

	public void setPointId(String pointId) {
		this.pointId = pointId;
	}

	public CompanyPoint getPoint() {
		return point;
	}

	public void setPoint(CompanyPoint point) {
		this.point = point;
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

	public int getGjjType() {
		return gjjType;
	}

	public void setGjjType(int gjjType) {
		this.gjjType = gjjType;
	}

	public BigDecimal getYfgz() {
		return yfgz;
	}

	public void setYfgz(BigDecimal yfgz) {
		this.yfgz = yfgz;
	}

	public BigDecimal getShui() {
		return shui;
	}

	public void setShui(BigDecimal shui) {
		this.shui = shui;
	}

	public BigDecimal getSqgz() {
		return sqgz;
	}

	public void setSqgz(BigDecimal sqgz) {
		this.sqgz = sqgz;
	}

	public BigDecimal getSfgz() {
		return sfgz;
	}

	public void setSfgz(BigDecimal sfgz) {
		this.sfgz = sfgz;
	}
	
	
	
}
