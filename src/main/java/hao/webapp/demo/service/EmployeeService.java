package hao.webapp.demo.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.core.sequence.Seq;
import hao.framework.db.dao.jdbc.BatchUpdate;
import hao.framework.db.page.Page;
import hao.framework.db.sql.SqlCommon.SqlLinkOperator;
import hao.framework.db.sql.SqlCommon.SqlOperator;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWrite;
import hao.framework.db.sql.SqlWrite.SqlWriterAction;
import hao.framework.service.BaseService;
import hao.framework.utils.ClassUtils;
import hao.framework.utils.StringUtils;
import hao.framework.xls.XLSData;
import hao.webapp.demo.CommonData;
import hao.webapp.demo.CommonData.EmployeeItemInfo;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.model.CompanyBaseProject;
import hao.webapp.demo.model.CompanyDepartment;
import hao.webapp.demo.model.CompanyPoint;
import hao.webapp.demo.model.CompanyProductionLine;
import hao.webapp.demo.model.CompanyProject;
import hao.webapp.demo.model.Employee;
import hao.webapp.demo.model.EmploymentInfo;

@Service
public class EmployeeService extends BaseService{

	Logger log = LogManager.getLogger("====Service日志===="+EmployeeService.class.getName()+"====》");
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 * @throws HaoException
	 */
	public List<EmploymentInfo> queryPageList(Page page,String searchKey) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(EmploymentInfo.class);
		sqlSearch.join(Employee.class,"idCard", "idCard");
		sqlSearch.join(Company.class, "companyId", "id");
		sqlSearch.join(CompanyDepartment.class,"departmentId", "id");
		sqlSearch.join(CompanyProductionLine.class,"productionLineId", "id");
		sqlSearch.join(CompanyProject.class,"projectId", "id");
		sqlSearch.join(CompanyBaseProject.class,"baseProjectId", "id");
		sqlSearch.join(CompanyPoint.class,"pointId", "id");
		
		if(searchKey!=null&&!searchKey.equals("")) {
			sqlSearch.addCondition("idCard", SqlOperator.like, searchKey, SqlLinkOperator.or);
			sqlSearch.addCondition(Employee.class,"name", SqlOperator.like, searchKey, SqlLinkOperator.or);
			sqlSearch.addCondition("workerNo", SqlOperator.like, searchKey);
		}
		
		List<EmploymentInfo> list = jdbcDao.queryList(page, sqlSearch);//this.queryPageList(page, sqlSearch);
		return list;
	}

	private boolean checkEmployeeHasIdCard(Map<String,Employee> empolyees,String idCard) {
		if(empolyees.containsKey(idCard)) {
			return true;
		}
		return false;
	}
	private boolean checkEmployeeinfoHasIdCard(Map<String,EmploymentInfo>  employmentInfos,String idCard) {
		if(employmentInfos.containsKey(idCard)) {
			return true;
		}
		return false;
	}
	
	private Company checkCompanyInMap(Map<String,Company> map,String companyId) {
		if(map.get(companyId)!=null) {
			return map.get(companyId);
		}
		return null;
	}
	
	private CompanyBaseProject getCompanyBaseProject(List<CompanyBaseProject> list,String companyId,String name) {
		for(CompanyBaseProject c:list) {
			if(c.getCompanyId().equals(companyId)&&c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	private CompanyProject getCompanyProject(List<CompanyProject> list,String companyId,String name) {
		for(CompanyProject c:list) {
			if(c.getCompanyId().equals(companyId)&&c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	private CompanyPoint getCompanyPoint(List<CompanyPoint> list,String companyId,String name) {
		for(CompanyPoint c:list) {
			if(c.getCompanyId().equals(companyId)&&c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	private CompanyProductionLine getCompanyProductionLine(List<CompanyProductionLine> list,String companyId,String name) {
		for(CompanyProductionLine c:list) {
			if(c.getCompanyId().equals(companyId)&&c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	private CompanyDepartment getCompanyDepartment(List<CompanyDepartment> list,String companyId,String name) {
		for(CompanyDepartment c:list) {
			if(c.getCompanyId().equals(companyId)&&c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	
	/**
	 * 导入员工的人事信息
	 * @param data
	 * @throws HaoException 
	 */
	public void importEmployee(XLSData data,List<String> error) throws HaoException {
		log.info("+++++++++++===========================开始导入==================================");
		
		//校验data是否含有重复数据
		data.checkRepeat(EmployeeItemInfo.idCard.getItemName());
		List<Employee>              empolyeeList        = this.jdbcDao.queryList(Employee.class);//  this.queryAll(Employee.class);
		List<EmploymentInfo>        employmentInfoList  = this.jdbcDao.queryList(EmploymentInfo.class);
		List<Company>               companyList         = this.jdbcDao.queryList(Company.class);
		List<CompanyBaseProject>    baseProjectList     = this.jdbcDao.queryList(CompanyBaseProject.class);
		List<CompanyProject>        projectList         = this.jdbcDao.queryList(CompanyProject.class);
		List<CompanyProductionLine> lineList            = this.jdbcDao.queryList(CompanyProductionLine.class);
		List<CompanyDepartment>     departmentList      = this.jdbcDao.queryList(CompanyDepartment.class);
		List<CompanyPoint>          pointList           = this.jdbcDao.queryList(CompanyPoint.class);
		
		
		Map<String,Employee>       employeeMap       = ClassUtils.listToMap(empolyeeList, "idCard");
		Map<String,EmploymentInfo> employmentInfoMap = ClassUtils.listToMap(employmentInfoList, "idCard");
		Map<String,Company>        companyMap        = ClassUtils.listToMap(companyList, "name");
		
		
		// TODO Auto-generated method stub
		if(data.getData()!=null&&data.getData().size()>0) {
			String[] headers = data.getXlsHeader().getCellNames();
			//过滤到不存在的项目
			Set<String> tempHeaders = new HashSet<String>();
			for(String header:headers) {
				if(EmployeeItemInfo.getEmployyerItemInfo(header)!=null) {
					tempHeaders.add(header);
				}else {
					error.add(header+"列头名称错误，请校验是否符合模版的要求");
				}
			}
			//校验必填字段身份证号是否存在
			if(!StringUtils.checkArrayHasValue(tempHeaders.toArray(new String[tempHeaders.size()]), EmployeeItemInfo.idCard.getItemName())) {
				error.add("必填数据身份证号码为提供，程序不予以处理导入操作。");
				return;
			}
			BatchUpdate batchUpdate = BatchUpdate.createBatchUpdate();
			for(int i=0;i<data.getData().size();i++) {
				Map<String,String> row = data.getData().get(i);
				String idCard = row.get(EmployeeItemInfo.idCard.getItemName());
				if(idCard==null||idCard.equals("")) {
					error.add("第"+(i+1)+""+EmployeeItemInfo.idCard.getItemName()+"不能为空！");
					continue;
				}
				idCard = idCard.toUpperCase();
				Map<String,Object> updateEmployeeField = new HashMap<String,Object>();
				Map<String,Object> updateEmploymentInfoField = new HashMap<String,Object>();
				for(String header:tempHeaders) {
					String field    = EmployeeItemInfo.getEmployyerItemInfo(header).getField();
					String itemName = EmployeeItemInfo.getEmployyerItemInfo(header).getItemName();
					Class<?> clazz  = EmployeeItemInfo.getEmployyerItemInfo(header).getClazz();
					String value    = row.get(itemName).trim();
					if(value==null||value.equals("")) {
						continue;
					}
					String _value = value;
					//校验下面的值是否有效，如有效则将值转换成对应的主键
					if(field.equals("sex")) {
						if(value.equals("男")) {
							_value = "1";
						}else if(value.equals("女")) {
							_value = "0";
						}
					}
					//离职类型
					if(field.equals("leaveType")) {
						if(CommonData.getleaveTypeCodeByName(value).equals("")) {
							throw new HaoException("999999","离职类型填写错误，位置在第"+(i+2)+"行");
						}
						_value = CommonData.getleaveTypeCodeByName(value);
					}
					//民族
					if(field.equals("nation")) {
						if(value!=null&&!value.equals("")&&CommonData.getNationIdByName(value).equals("")) {
							throw new HaoException("999999","民族名称填写错误，位置在第"+(i+2)+"行");
						}
						_value = CommonData.getNationIdByName(value);
					}
					//外派公司
					if(field.equals("companyId")) {
						Company company = this.checkCompanyInMap(companyMap, value);
						if(company==null) {
							throw new HaoException("999999","外派公司名称填写错误，位置在第"+(i+2)+"行");
						}
						_value = company.getId()+"";
					}
					//雇佣公司
					if(field.equals("employerId")) {
						if(CommonData.getEmployerCodeByName(value).equals("")) {
							throw new HaoException("999999","合同公司名称填写错误，位置在第"+(i+2)+"行");
						}
						_value = CommonData.getEmployerCodeByName(value);
					}
					//合同类型
					if(field.equals("employmentType")) {
						if(value!=null&&!value.equals("")&&CommonData.getEmploymentTypeCodeByName(value).equals("")) {
							throw new HaoException("999999","合同形式填写错误，位置在第"+(i+2)+"行");
						}
						_value = CommonData.getEmploymentTypeCodeByName(value);
					}
					//银行
					if(field.equals("bankType")) {
						if(value!=null&&!value.equals("")&&CommonData.getBankTypeCodeByName(value).equals("")) {
							throw new HaoException("999999","银行填写错误，位置在第"+(i+2)+"行");
						}
						_value = CommonData.getBankTypeCodeByName(value);
					}
					//社保类型
					if(field.equals("socialSecurityType")) {
						if(!value.equals("0")&&!value.equals("1")&&!value.equals("2")&&!value.equals("3")) {
							throw new HaoException("999999","社保类型填写错误，位置在第"+(i+2)+"行");
						}
					}
					//社保缴法
					if(field.equals("socialSecurityAlgorithm")) {
						if(!value.equals("0")&&!value.equals("1")) {
							throw new HaoException("999999","社保缴法填写错误，位置在第"+(i+2)+"行");
						}
					}
					if(clazz.isAssignableFrom(Employee.class)) {
						updateEmployeeField.put(field, _value);
					}else {
						updateEmploymentInfoField.put(field, _value);
					}
				}
				
				//替换产线  部门  职务 大项目 项目的值
				String companyId = updateEmploymentInfoField.get(EmployeeItemInfo.companyId.getField())==null?
						"":(String)updateEmploymentInfoField.get(EmployeeItemInfo.companyId.getField());
				if(companyId!=null&&!companyId.equals("")) {
					if(updateEmploymentInfoField.get(EmployeeItemInfo.productionLineId.getField())!=null) {
						String name = (String) updateEmploymentInfoField.get(EmployeeItemInfo.productionLineId.getField());
						CompanyProductionLine productionLine = this.getCompanyProductionLine(lineList, companyId, name);
						if(productionLine==null) {
							throw new HaoException("999999","产线填写错误，位置在第"+(i+1)+"行");
						}
						updateEmploymentInfoField.put(EmployeeItemInfo.productionLineId.getField(), productionLine.getId());
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.departmentId.getField())!=null) {
						String name = (String) updateEmploymentInfoField.get(EmployeeItemInfo.departmentId.getField());
						CompanyDepartment department = this.getCompanyDepartment(departmentList, companyId, name);
						if(department==null) {
							throw new HaoException("999999","部门填写错误，位置在第"+(i+1)+"行");
						}
						updateEmploymentInfoField.put(EmployeeItemInfo.departmentId.getField(), department.getId());
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.pointId.getField())!=null) {
						String name = (String) updateEmploymentInfoField.get(EmployeeItemInfo.pointId.getField());
						CompanyPoint point = this.getCompanyPoint(pointList, companyId, name);
						if(point==null) {
							throw new HaoException("999999","职位填写错误，位置在第"+(i+1)+"行");
						}
						updateEmploymentInfoField.put(EmployeeItemInfo.pointId.getField(), point.getId());
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.baseProjectId.getField())!=null) {
						String name = (String) updateEmploymentInfoField.get(EmployeeItemInfo.baseProjectId.getField());
						CompanyBaseProject baseProject = this.getCompanyBaseProject(baseProjectList, companyId, name);
						if(baseProject==null) {
							throw new HaoException("999999","大项目名称填写错误，位置在第"+(i+1)+"行");
						}
						updateEmploymentInfoField.put(EmployeeItemInfo.baseProjectId.getField(), baseProject.getId());
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.projectId.getField())!=null) {
						String name = (String) updateEmploymentInfoField.get(EmployeeItemInfo.projectId.getField());
						CompanyProject project = this.getCompanyProject(projectList, companyId, name);
						if(project==null) {
							throw new HaoException("999999","项目名称填写错误，位置在第"+(i+1)+"行");
						}
						updateEmploymentInfoField.put(EmployeeItemInfo.projectId.getField(), project.getId());
					}
				}else {
					if(updateEmploymentInfoField.get(EmployeeItemInfo.productionLineId.getField())!=null) {
						throw new HaoException("999999","填写外派公司，产线才有效，位置在第"+(i+1)+"行");
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.departmentId.getField())!=null) {
						throw new HaoException("999999","填写外派公司，部门才有效，位置在第"+(i+1)+"行");
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.pointId.getField())!=null) {
						throw new HaoException("999999","填写外派公司，职位才有效，位置在第"+(i+1)+"行");
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.baseProjectId.getField())!=null) {
						throw new HaoException("999999","填写外派公司，大项目才有效，位置在第"+(i+1)+"行");
					}
					if(updateEmploymentInfoField.get(EmployeeItemInfo.projectId.getField())!=null) {
						throw new HaoException("999999","填写外派公司，项目才有效，位置在第"+(i+1)+"行");
					}
				}
				//将进入数据库身份证设置成大写
				updateEmploymentInfoField.put(EmployeeItemInfo.idCard.getField(), idCard);
				updateEmployeeField.put(EmployeeItemInfo.idCard.getField(), idCard);
				//校验数据库中是否已经存在此人的信息
				if(updateEmployeeField.size()>1) {
					if(checkEmployeeHasIdCard(employeeMap,idCard)) {
						//update
						SqlWrite sqlWrite = new SqlWrite(Employee.class,SqlWriterAction.update);
						sqlWrite.addCondition("idCard", idCard);
						sqlWrite.setFields(updateEmployeeField);
						batchUpdate.addSqlWrite(sqlWrite);
					}else {
						//insert
						SqlWrite sqlWrite = new SqlWrite(Employee.class,SqlWriterAction.insert);
						sqlWrite.setFields(updateEmployeeField);
						updateEmployeeField.put("id",Seq.getNextId());
						batchUpdate.addSqlWrite(sqlWrite);
					}
				}
				
				if(updateEmploymentInfoField.size()>1) {
					if(checkEmployeeinfoHasIdCard(employmentInfoMap,idCard)) {
						SqlWrite sqlWrite = new SqlWrite(EmploymentInfo.class,SqlWriterAction.update);
						sqlWrite.addCondition("idCard", idCard);
						sqlWrite.setFields(updateEmploymentInfoField);
						batchUpdate.addSqlWrite(sqlWrite);
					}else {
						//校验必填字段有无填写
						//外派公司
						if(updateEmploymentInfoField.get(EmployeeItemInfo.companyId.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写外派公司信息，位置在第"+(i+1)+"行");
						}
						//合同公司
						if(updateEmploymentInfoField.get(EmployeeItemInfo.employerId.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写合同公司信息，位置在第"+(i+1)+"行");
						}
						//合同形式
						if(updateEmploymentInfoField.get(EmployeeItemInfo.employmentType.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写合同形式，位置在第"+(i+1)+"行");
						}
						//入职日期
						if(updateEmploymentInfoField.get(EmployeeItemInfo.beginTime.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写入职日期，位置在第"+(i+1)+"行");
						}
						//工号
						if(updateEmploymentInfoField.get(EmployeeItemInfo.workerNo.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写工号，位置在第"+(i+1)+"行");
						}
						//基本工资
						if(updateEmploymentInfoField.get(EmployeeItemInfo.baseSalary.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写员基本工资，位置在第"+(i+1)+"行");
						}
						//大项目
//						if(updateEmploymentInfoField.get(EmployeeItemInfo.baseProjectId.getField())==null) {
//							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写大项目，位置在第"+(i+1)+"行");
//						}
						//项目
						if(updateEmploymentInfoField.get(EmployeeItemInfo.projectId.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写项目，位置在第"+(i+1)+"行");
						}
						//产线
//						if(updateEmploymentInfoField.get(EmployeeItemInfo.productionLineId.getField())==null) {
//							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写产线，位置在第"+(i+1)+"行");
//						}
						//职位
						if(updateEmploymentInfoField.get(EmployeeItemInfo.pointId.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写职位，位置在第"+(i+1)+"行");
						}
						//部门
						if(updateEmploymentInfoField.get(EmployeeItemInfo.departmentId.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写部门，位置在第"+(i+1)+"行");
						}
						//社保类型
						if(updateEmploymentInfoField.get(EmployeeItemInfo.socialSecurityType.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写社保类型，位置在第"+(i+1)+"行");
						}
						//社保缴法
						if(updateEmploymentInfoField.get(EmployeeItemInfo.socialSecurityAlgorithm.getField())==null) {
							throw new HaoException("999999","为了避免后续计算工资发生错误，新增数据时请填写社保缴法，位置在第"+(i+1)+"行");
						}
						SqlWrite sqlWrite = new SqlWrite(EmploymentInfo.class,SqlWriterAction.insert);
						sqlWrite.setFields(updateEmploymentInfoField);
						batchUpdate.addSqlWrite(sqlWrite);
					}
				}
			}
			this.jdbcDao.executeBatch(batchUpdate);
			log.info("++++++++++++++++++++++++++++++导入完成++++++++++++++++++++++++++++++++++++++++");
		}
	}

	public EmploymentInfo queryEmploymentInfo(String employerId) throws HaoException {
		SqlSearch search = new SqlSearch(EmploymentInfo.class);
		search.join(Employee.class, "idCard", "idCard");
		search.addCondition(Employee.class,"id",employerId);
		return this.jdbcDao.queryBean(search);
	}

	public void saveLeaveData(
			String idCard,
			String leaveDate,
			String realyLeaveDate
			) {
		
		
		
	}
	
}
