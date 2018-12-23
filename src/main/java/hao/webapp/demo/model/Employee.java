package hao.webapp.demo.model;

import hao.framework.annotation.Attribute;
import hao.framework.annotation.Column;
import hao.framework.annotation.Entity;
import hao.framework.model.BaseModel;
import hao.webapp.demo.CommonData;
import hao.webapp.demo.CommonData.EDUCATION;
import hao.webapp.demo.CommonData.SEX;

/**
 * 雇员信息
 * @author   chianghao
 * @time     2018年3月31日
 * @version  0.1
 */
@Entity
public class Employee extends BaseModel {

	/**
	 * 身份证
	 */
	@Attribute(title="身份证")
	@Column
	private String    idCard;
	/**
	 * 姓名
	 */
	@Attribute(title="姓名")
	@Column
	private String    name;
	/**
	 * 性别  1男 0女
	 */
	@Attribute(title="性别")
	@Column(isNull=true)
	private int       sex;
	private String    sexName;
	/**
	 * 电话1
	 */
	@Attribute(title="电话1")
	@Column(isNull=true)
	private String      phone1;
	/**
	 * 电话2
	 */
	@Attribute(title="电话2")
	@Column(isNull=true)
	private String      phone2;
	/**
	 * 邮箱
	 */
	@Attribute(title="邮箱")
	@Column(isNull=true)
	private String    email;
	/**
	 * 照片
	 */
	@Attribute(title="照片")
	@Column(isNull=true)
	private String    icon;
	/**
	 * 民族
	 */
	@Attribute(title="民族")
	@Column(isNull=true)
	private String    nation;
	private String    nationName;
	/**
	 * 学历
	 */
	@Attribute(title="学历")
	@Column(isNull=true)
	private String    education;
	private String    educationName;
	/**
	 * 籍贯
	 */
	@Attribute(title="籍贯")
	@Column(isNull=true)
	private String    placeOfOrigin;
	/**
	 * 来源省
	 */
	@Attribute(title="来源省")
	@Column(isNull=true)
	private String    sourceProvince;
	/**
	 * 来源城市
	 */
	@Attribute(title="来源城市")
	@Column(isNull=true)
	private String    sourceCity;
	/**
	 * 来源区县
	 */
	@Attribute(title="来源区县")
	@Column(isNull=true)
	private String    sourceArea;
	/**
	 * 来源地址
	 */
	@Attribute(title="来源地址")
	@Column(isNull=true)
	private String    sourceAddress;
	/**
	 * 现居地
	 */
	@Attribute(title="现居地")
	@Column(isNull=true)
	private String    livingPlace;
	/**
	 * 生日
	 */
	@Attribute(title="生日")
	@Column(isNull=true)
	private String    birthday;
	
	/**
	 * 雇佣信息
	 */
	private EmploymentInfo employmentInfo;
	
	
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	public String getSexName() {
		return sexName;
	}
	public void setSexName(String sexName) {
		this.sexName = sexName;
	}
	public String getEducationName() {
		return educationName;
	}
	public void setEducationName(String educationName) {
		this.educationName = educationName;
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
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
		this.setSexName(SEX.getValueByCode(sex+""));
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
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
		this.setNationName(CommonData.getNationById(nation));
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
		this.setEducationName(EDUCATION.getValueByCode(education));
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
	public EmploymentInfo getEmploymentInfo() {
		return employmentInfo;
	}
	public void setEmploymentInfo(EmploymentInfo employmentInfo) {
		this.employmentInfo = employmentInfo;
	}
	
}
