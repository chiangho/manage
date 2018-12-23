package hao.webapp.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import hao.framework.core.expression.HaoException;
import hao.framework.core.sequence.Seq;
import hao.framework.db.dao.jdbc.BatchUpdate;
import hao.framework.db.page.Page;
import hao.framework.db.sql.SqlCommon.SqlOperator;
import hao.framework.db.sql.SqlCommon.SqlSort;
import hao.framework.db.sql.SqlSearch;
import hao.framework.db.sql.SqlWrite;
import hao.framework.db.sql.SqlWrite.SqlWriterAction;
import hao.framework.service.BaseService;
import hao.framework.utils.BeanUtil;
import hao.framework.utils.ClassUtils;
import hao.framework.utils.ConverterUtils;
import hao.framework.web.RequestContext;
import hao.framework.web.form.Form;
import hao.framework.xls.ExcelTool;
import hao.framework.xls.XLSData;
import hao.framework.xls.XLSHeader;
import hao.webapp.demo.CommonData;
import hao.webapp.demo.model.ChangeSalaryLog;
import hao.webapp.demo.model.Company;
import hao.webapp.demo.model.CompanyAttendanceItemModel;
import hao.webapp.demo.model.CompanySalaryItemModel;
import hao.webapp.demo.model.EmploymentInfo;
import hao.webapp.demo.model.Salary;
import hao.webapp.demo.model.SalaryAllowance;
import hao.webapp.demo.model.SalaryAppIncomeTax;
import hao.webapp.demo.model.SalaryAppSetting;
import hao.webapp.demo.model.SalaryAttendanceItem;
import hao.webapp.demo.model.SalaryEmployee;
import hao.webapp.demo.model.SalaryEmployeeAllowanceValue;
import hao.webapp.demo.model.SalaryEmployeeAttendance;
import hao.webapp.demo.model.SalaryEmployeeItemValue;
import hao.webapp.demo.model.SalaryItem;
import hao.webapp.demo.utils.DateUtil;
import hao.webapp.demo.utils.JSScript;
import hao.webapp.demo.utils.SalaryAttendanceItemUtils;
import hao.webapp.demo.utils.ScriptAnalysis;
import hao.webapp.demo.view.EmployerSalaryData;


@Service
public class SalaryService extends BaseService{
	
	private SalaryAppSettingService salaryAppSettingService;
	
	private SalaryAppIncomeTaxService salaryAppIncomeTaxService;
	
	public List<Salary> loadList(Page page) throws HaoException {
		SqlSearch search = new SqlSearch(Salary.class);
		search.join(Company.class, "companyId", "id");
		search.order("companyId", SqlSort.asc);
		search.order("year", SqlSort.desc);
		search.order("month", SqlSort.desc);
		List<Salary> list = this.jdbcDao.queryList(page, search);
		return list;
	}

	/**
	 * 保存数据
	 * @throws Exception 
	 */
	public void save() throws Exception {
		String companyId = RequestContext.getParam("companyId");
		if(companyId==null||companyId.equals("")) {
			throw new HaoException("999999","请选择公司");
		}
		String year      = RequestContext.getParam("year");
		if(year==null||companyId.equals("")) {
			throw new HaoException("999999","请选择年份");
		}
		String month     = RequestContext.getParam("month");
		if(month==null||month.equals("")) {
			throw new HaoException("999999","请选择月份");
		}
		String beginDate     = RequestContext.getParam("beginDate");
		if(beginDate==null||beginDate.equals("")) {
			throw new HaoException("999999","工资开始日期");
		}
		String endDate     = RequestContext.getParam("endDate");
		if(endDate==null||endDate.equals("")) {
			throw new HaoException("999999","工资结束日期");
		}
		SqlSearch search = new SqlSearch(Salary.class);
		search.addCondition("companyId", companyId);
		search.addCondition("year", year);
		search.addCondition("month", month);
		int count = this.jdbcDao.queryCount(search);
		SqlWrite write = null;
		if(count==0) {
			//新曾
			Map<String,Object> map =RequestContext.getBeanMap(Salary.class);
			long id = Seq.getNextId();
			map.put("id",id);
			write = new SqlWrite(Salary.class,SqlWriterAction.insert);
			write.setFields(map);
			//如新建则初始考勤导入项
			initAttendanceItem(companyId,id);
			//初始化社保缴费比例
			coverSalaryAppSetting(id);
			//初始化个税比例
			coverSalaryAppIncomeTax(id);
		}else {
			//更新
			write = new SqlWrite(Salary.class,SqlWriterAction.update);
			write.setFields(RequestContext.getBeanMap(Salary.class));
			write.addCondition("companyId", companyId);
			write.addCondition("year", year);
			write.addCondition("month", month);
		}
		this.jdbcDao.execute(write);
	}

	
	public void initAttendanceItem(String companyId,long salaryId) throws HaoException {
		List<CompanyAttendanceItemModel> companyAttendanceItems = 
				this.jdbcDao.queryList(CompanyAttendanceItemModel.class, "companyId", companyId);
		List<SalaryAttendanceItem> salyarAttendanceItems = new ArrayList<SalaryAttendanceItem>(); 
		BatchUpdate batch = BatchUpdate.createBatchUpdate();
		for(CompanyAttendanceItemModel m:companyAttendanceItems) {
			SalaryAttendanceItem item = new SalaryAttendanceItem();
			try {
				BeanUtil.copyBean2Bean(item,m);
				item.setId(Seq.getNextId());
				item.setSalaryId(salaryId);
				salyarAttendanceItems.add(item);
				SqlWrite sqlWrite = new SqlWrite(SalaryAttendanceItem.class,SqlWriterAction.insert);
				sqlWrite.setFields(BeanUtil.copyBean2Map(item));
				batch.addSqlWrite(sqlWrite);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.jdbcDao.executeBatch(batch);
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 * @throws HaoException 
	 */
	public Salary loadSalary(String id) throws HaoException {
		Salary salary = this.jdbcDao.queryBean(Salary.class, "id", id);
		return salary;
	}

	/**
	 * 删除工资批次
	 * @param id
	 * @throws HaoException 
	 */
	public void del(String id) throws HaoException {
		BatchUpdate batch =BatchUpdate.createBatchUpdate();
		if(id==null||id.equals("")) {
			throw new HaoException("999999","缺少参数id！");
		}
		//删除工资批次基础数据
		SqlWrite delSalary = new SqlWrite(Salary.class, SqlWriterAction.delete);
		delSalary.addCondition("id",id);
		batch.addSqlWrite(delSalary);
		//删除考勤导入项目
		SqlWrite delSalaryAttendanceItem = new SqlWrite(SalaryAttendanceItem.class,SqlWriterAction.delete);
		delSalaryAttendanceItem.addCondition("salaryId", id);
		batch.addSqlWrite(delSalaryAttendanceItem);
		//删除工资项目
		SqlWrite delSalaryItem = new SqlWrite(SalaryItem.class,SqlWriterAction.delete);
		delSalaryItem.addCondition("salaryId", id);
		batch.addSqlWrite(delSalaryItem);
		//删除工资对应社保比例
		delSalaryAppSetting(id);
		//删除此批次工资的
		this.jdbcDao.executeBatch(batch);
		
	}

	public List<SalaryAttendanceItem> querySalaryAttendanceItemList(Page page,String salaryId) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(SalaryAttendanceItem.class);
		sqlSearch.addCondition("salaryId", salaryId);
		sqlSearch.join(Company.class, "companyId", "id");
		sqlSearch.order("id", SqlSort.desc);
		return this.jdbcDao.queryList(page, sqlSearch);
	}

	/**
	 * 删除考勤导入项目
	 * @param id
	 * @throws HaoException
	 */
	public void delAttendance(String id) throws HaoException {
		this.jdbcDao.del(SalaryAttendanceItem.class,"id",id);
	}

	/**
	 * 获取考勤导入项内容
	 * @param id
	 * @return
	 * @throws HaoException 
	 */
	public SalaryAttendanceItem loadAttendance(String id) throws HaoException {
		SalaryAttendanceItem item = this.jdbcDao.queryBean(SalaryAttendanceItem.class, "id", id);
		return item;
	}

	/**
	 *  保存
	 * @throws HaoException
	 */
	public void saveAttendance() throws HaoException {
		String id         =  RequestContext.getParam("id");
		String name       =  RequestContext.getParam("name");
		String companyId  =  RequestContext.getParam("companyId");
		String salaryId   =  RequestContext.getParam("salaryId");
		if(name==null||name.equals("")) {
			throw new HaoException("999999", "名字不能为空");
		}
		if(companyId==null||companyId.equals("")) {
			throw new HaoException("999999", "公司不能为空");
		}
		if(salaryId==null||salaryId.equals("")) {
			throw new HaoException("999999", "所属工资批次不能为空");
		}
		SalaryAttendanceItem item = this.jdbcDao.queryBean(SalaryAttendanceItem.class, "id", id);
		SqlWrite sqlWrite = null;
		Form form  = new Form(SalaryAttendanceItem.class,RequestContext.getHttpRequest());
		if(id!=null&&!id.equals("")&&item!=null) {
			//update
			sqlWrite= new SqlWrite(SalaryAttendanceItem.class,SqlWriterAction.update);
			sqlWrite.setFields(form.getBeanMap());
			sqlWrite.addCondition("id", id);
		}else {
			//insert
			Map<String,Object> map = form.getBeanMap();
			map.put("id",Seq.getNextId());
			sqlWrite= new SqlWrite(SalaryAttendanceItem.class,SqlWriterAction.insert);
			sqlWrite.setFields(map);
		}
		this.jdbcDao.execute(sqlWrite);
	}

	/**
	 * 删除原来的考勤导入项，覆盖公司标准的导入项目
	 * @param salaryId
	 * @throws HaoException 
	 */
	public void importAttendance(long salaryId) throws HaoException {
		Salary salary = this.jdbcDao.queryBean(Salary.class,"id",salaryId);
		long companyId = salary.getCompanyId();
		BatchUpdate batchUpdate = BatchUpdate.createBatchUpdate();
		
		SqlWrite delSalaryAttendance = new SqlWrite(SalaryAttendanceItem.class,SqlWriterAction.delete);
		delSalaryAttendance.addCondition("salaryId", salaryId);
		batchUpdate.addSqlWrite(delSalaryAttendance);
		
		List<CompanyAttendanceItemModel> companyAttendanceItems = 
				this.jdbcDao.queryList(CompanyAttendanceItemModel.class, "companyId", companyId);
		List<SalaryAttendanceItem> salyarAttendanceItems = new ArrayList<SalaryAttendanceItem>(); 
		for(CompanyAttendanceItemModel m:companyAttendanceItems) {
			SalaryAttendanceItem item = new SalaryAttendanceItem();
			try {
				BeanUtil.copyBean2Bean(item,m);
				item.setId(Seq.getNextId());
				item.setSalaryId(salaryId);
				salyarAttendanceItems.add(item);
				SqlWrite sqlWrite = new SqlWrite(SalaryAttendanceItem.class,SqlWriterAction.insert);
				sqlWrite.setFields(BeanUtil.copyBean2Map(item));
				batchUpdate.addSqlWrite(sqlWrite);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.jdbcDao.executeBatch(batchUpdate);
	}

	/**
	 * 保存
	 * @param salaryId
	 * @param companyId
	 * @param companySalaryIds
	 * @throws HaoException 
	 */
	public void importSalaryItem(long salaryId, long companyId, String[] companySalaryIds) throws HaoException {
		BatchUpdate batchUpdate = BatchUpdate.createBatchUpdate();
		//删除
//		SqlWrite del = new SqlWrite(SalaryItem.class,SqlWriterAction.delete);
//		del.addCondition("salaryId",salaryId);
//		batchUpdate.addSqlWrite(del);
		
		//查询获取数组的
		SqlSearch sqlSearch  =  new SqlSearch(CompanySalaryItemModel.class);
		sqlSearch.addCondition("id",SqlOperator.in, companySalaryIds);
		List<CompanySalaryItemModel> list = this.jdbcDao.queryList(sqlSearch);
		
		for(CompanySalaryItemModel temp:list) {
			try {
				SalaryItem item = new SalaryItem();
				BeanUtil.copyBean2Bean(item,temp);
				item.setId(Seq.getNextId());
				item.setCompanyId(companyId);
				item.setSalaryId(salaryId);
				SqlWrite sqlWrite = new SqlWrite(SalaryItem.class,SqlWriterAction.insert);
				sqlWrite.setFields(BeanUtil.copyBean2Map(item));
				batchUpdate.addSqlWrite(sqlWrite);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.jdbcDao.executeBatch(batchUpdate);
	}

	/**
	 * 查询列表
	 * @param page
	 * @param salaryId
	 * @return
	 * @throws HaoException
	 */
	public List<SalaryItem> querySalaryItemPage(Page page,String salaryId) throws HaoException{
		SqlSearch search = new SqlSearch(SalaryItem.class);
		search.addCondition("salaryId", salaryId);
		search.order("id",SqlSort.desc);
		return this.jdbcDao.queryList(page,search);
	}
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 * @throws HaoException
	 */
	public SalaryItem loadSalaryItemById(String id) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(SalaryItem.class);
		sqlSearch.join(Company.class, "companyId", "id");
		return this.jdbcDao.queryBean(sqlSearch);
	}
	
	/**
	 * 删除工资项目
	 * @param id
	 * @throws HaoException
	 */
	public void delSalaryItem(String id) throws HaoException {
		this.jdbcDao.del(SalaryItem.class, "id",id);
	}
	/**
	 * 保存新增和编辑的工资项
	 * @throws HaoException 
	 */
	public void saveSalaryItem() throws HaoException {
		String companyId = RequestContext.getParam("companyId");
		String salaryId  = RequestContext.getParam("salaryId");
		if(companyId==null||companyId.equals("")) {
			throw new HaoException("999999","所属公司不能为空");
		}
		if(salaryId==null||salaryId.equals("")) {
			throw new HaoException("999999","工资批次不能为空");
		}
		//创建工资项需要考勤导入项的支持，故先校验考勤导入项是否已经被创建
		SqlSearch sqlSearchAttendanceCount = new SqlSearch(SalaryAttendanceItem.class);
		sqlSearchAttendanceCount.addCondition("salaryId", salaryId);
		List<SalaryAttendanceItem> attendanceList = this.jdbcDao.queryList(sqlSearchAttendanceCount);
		if(attendanceList==null||attendanceList.size()==0) {
			throw new HaoException("999999","请选设置考勤导入项！");
		}
		
		String id        = RequestContext.getParam("id");
		String name      = RequestContext.getParam("name");
		String operation = RequestContext.getParam("operation");
		String script    = RequestContext.getParam("script");
		String remark    = RequestContext.getParam("remark");
		String changeSalaryDiffCalculationStr = RequestContext.getParam("changeSalaryDiffCalculation");
		
		
		//校验非空
		if(name==null||name.equals("")) {
			throw new HaoException("999999","名称不能为空");
		}
		if(operation==null||operation.equals("")) {
			throw new HaoException("999999","操作符号不能为空");
		}
		if(script==null||script.equals("")) {
			throw new HaoException("999999","脚本不能为空");
		}
		if(changeSalaryDiffCalculationStr==null||changeSalaryDiffCalculationStr.equals("")) {
			throw new HaoException("999999","请选择此工资项是否调薪后按照不同数字计算");
		}
		
		//校验脚本是否正确
		ScriptAnalysis analysis = new ScriptAnalysis();
		analysis.run(script);
		Set<String> basicComputingItem  = analysis.getBasicComputingItem();
		if(basicComputingItem!=null&&basicComputingItem.size()>0) {
			Set<String> sets = SalaryAttendanceItemUtils.toSet(attendanceList);
			sets.addAll(CommonData.getGlobleBasicComputingItem());
			for(String s:basicComputingItem) {
				String temp = s.substring(1, s.lastIndexOf("#"));
				if(!sets.contains(temp)) {
					throw new HaoException("999999","基础计算项"+s+"不存在,请查阅后再矫正后再提交");
				}
			}
		}
		
		SalaryItem  item = jdbcDao.queryBean(SalaryItem.class,"id",id);
		SqlWrite sqlwrite = null;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name",name);
		map.put("operation", operation);
		map.put("script", script);
		map.put("remark", remark);
		map.put("id", id);
		map.put("salaryId", salaryId);
		map.put("companyId", companyId);
		map.put("changeSalaryDiffCalculation", changeSalaryDiffCalculationStr);
		if(item==null) {
			//插入
			map.put("id",Seq.getNextId());
			sqlwrite = new SqlWrite(SalaryItem.class,SqlWriterAction.insert);
			sqlwrite.setFields(map);
		}else {
			//更新
			sqlwrite = new SqlWrite(SalaryItem.class,SqlWriterAction.update);
			sqlwrite.setFields(map);
			sqlwrite.addCondition("id",id);
		}
		this.jdbcDao.execute(sqlwrite);
	}

	/**
	 * 获取计算因子
	 * @param salaryId
	 * @return
	 * @throws HaoException 
	 */
	public Set<String> querySalaryComputingItem(String salaryId,String companyId) throws HaoException {
		//常量
		Set<String> computinItem = CommonData.getGlobleBasicComputingItem();
		SqlSearch sqlSearch =  new SqlSearch(SalaryAttendanceItem.class);
		sqlSearch.addCondition("salaryId",salaryId);
		sqlSearch.addCondition("companyId",companyId);
		List<SalaryAttendanceItem> list = this.queryList(sqlSearch);
		//工资计算项目
		Set<String> sets = SalaryAttendanceItemUtils.toSet(list);
		sets.addAll(computinItem);
		return sets;
	}
	
	/**
	 * 删除工资的社保比例
	 * @param id
	 * @throws HaoException
	 */
	public void delSalaryAppSetting(String salaryId) throws HaoException {
		this.jdbcDao.del(SalaryAppSetting.class,"salaryId",salaryId);
	}

	/**
	 * 删除个税比例
	 * @param salaryId
	 * @throws HaoException
	 */
	public void delSalaryAppIncomeTax(String salaryId) throws HaoException {
		this.jdbcDao.del(SalaryAppIncomeTax.class,"salaryId",salaryId);
	}
	
	/**
	 * 覆盖社保比例
	 * @param salaryId
	 * @throws Exception 
	 */
	public void coverSalaryAppSetting(long salaryId) throws Exception {
		salaryAppSettingService.coverSalaryAppSetting(salaryId+"");
	}
	
	public void coverSalaryAppIncomeTax(long salaryId) throws HaoException {
		salaryAppIncomeTaxService.coverSalaryAppIncomeTax(salaryId);
	}

	/**
	 * 查询公司的津贴列表
	 * @param page
	 * @param salaryId
	 * @param companyId
	 * @return
	 * @throws HaoException 
	 */
	public List<SalaryAllowance> queryAllowanceItemPage(Page page, String salaryId,String companyId) throws HaoException {
		SqlSearch sqlSearch = new SqlSearch(SalaryAllowance.class);
		sqlSearch.addCondition("salaryId",salaryId);
		sqlSearch.addCondition("companyId", companyId);
		List<SalaryAllowance> list = this.jdbcDao.queryList(page,sqlSearch);
		return list;
	}

	
	/***
	 * 获取工资批次的津贴
	 * @param id
	 * @return
	 * @throws HaoException 
	 */
	public SalaryAllowance loadAllowanceItemById(String id) throws HaoException {
		SalaryAllowance salaryAllowance = this.jdbcDao.queryBean(SalaryAllowance.class, "id", id);
		return salaryAllowance;
	}
	
	/**
	 * 保存新增和编辑的工资项
	 * @throws HaoException 
	 */
	public void saveAllowanceItem() throws HaoException {
		String companyId = RequestContext.getParam("companyId");
		String salaryId  = RequestContext.getParam("salaryId");
		
		if(companyId==null||companyId.equals("")) {
			throw new HaoException("999999","所属公司不能为空");
		}
		if(salaryId==null||salaryId.equals("")) {
			throw new HaoException("999999","工资批次不能为空");
		}
		//创建工资项需要考勤导入项的支持，故先校验考勤导入项是否已经被创建
		SqlSearch sqlSearchAttendanceCount = new SqlSearch(SalaryAttendanceItem.class);
		sqlSearchAttendanceCount.addCondition("salaryId", salaryId);
		sqlSearchAttendanceCount.addCondition("companyId", companyId);
		List<SalaryAttendanceItem> attendanceList = this.jdbcDao.queryList(sqlSearchAttendanceCount);
		if(attendanceList==null||attendanceList.size()==0) {
			throw new HaoException("999999","请选设置考勤导入项！");
		}
		
		String id        =  RequestContext.getParam("id");
		String name      =  RequestContext.getParam("name");
		String script    =  RequestContext.getParam("summayScript");
		String remark    =  RequestContext.getParam("remark");
		
		//校验非空
		if(name==null||name.equals("")) {
			throw new HaoException("999999","名称不能为空");
		}
		if(script==null||script.equals("")) {
			throw new HaoException("999999","脚本不能为空");
		}
		//校验脚本是否正确
		ScriptAnalysis analysis = new ScriptAnalysis();
		analysis.run(script);
		Set<String> basicComputingItem  = analysis.getBasicComputingItem();
		if(basicComputingItem!=null&&basicComputingItem.size()>0) {
			Set<String> sets = SalaryAttendanceItemUtils.toSet(attendanceList);
			sets.add(name);//加入自己
			sets.addAll(CommonData.getGlobleBasicComputingItem());
			for(String s:basicComputingItem) {
				String temp = s.substring(1, s.lastIndexOf("#"));
				if(!sets.contains(temp)) {
					throw new HaoException("999999","基础计算项"+s+"不存在,请查阅后再矫正后再提交");
				}
			}
		}
		
		SalaryAllowance  item = jdbcDao.queryBean(SalaryAllowance.class,"id",id);
		SqlWrite sqlwrite = null;
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name",name);
		map.put("summayScript", script);
		map.put("remark", remark);
		map.put("id", id);
		map.put("salaryId", salaryId);
		map.put("companyId", companyId);
		
		if(item==null) {
			//插入
			map.put("id",Seq.getNextId());
			sqlwrite = new SqlWrite(SalaryAllowance.class,SqlWriterAction.insert);
			sqlwrite.setFields(map);
		}else {
			//更新
			sqlwrite = new SqlWrite(SalaryAllowance.class,SqlWriterAction.update);
			sqlwrite.setFields(map);
			sqlwrite.addCondition("id",id);
		}
		this.jdbcDao.execute(sqlwrite);
	}

	
	/***
	 * 删除津贴
	 * @param id
	 * @throws HaoException 
	 */
	public void delSalaryAllowanceItem(String id) throws HaoException {
		this.jdbcDao.del(SalaryAllowance.class,"id",id);
	}

	
	/**
	 * 导入员工共的工资
	 * @param xlsData
	 * @param salaryId
	 * @param companyId
	 * @throws HaoException 
	 */
	public List<String> importAttendance(XLSData xlsData, String salaryId, String companyId) throws HaoException {
		//查询公司的所有员工共.并装换成map格式
		List<EmploymentInfo> employeeList = this.jdbcDao.queryList(EmploymentInfo.class,"companyId","companyId");
		Map<String,EmploymentInfo> employeeMap = ClassUtils.listToMap(employeeList, "workerNo");//根据工号转换
		
		//查询工资导入项目是否都存在
		SqlSearch searchAttendanceItemSearch = new SqlSearch(SalaryAttendanceItem.class);
		searchAttendanceItemSearch.addCondition("salaryId", salaryId);
		searchAttendanceItemSearch.addCondition("companyId", companyId);
		List<SalaryAttendanceItem> salaryAttendanceItemList = this.jdbcDao.queryList(searchAttendanceItemSearch);
		
		//比对导入项是否符合要求
		XLSHeader xlsHeader  = xlsData.getXlsHeader();
		Set<String> headers = new HashSet<String>();
		for(String header:xlsHeader.getCellNames()) {
			headers.add(header);
		}
		for(SalaryAttendanceItem item:salaryAttendanceItemList) {
			if(!headers.contains(item.getName())){
				throw new HaoException("999999","所填报的数据项名称错误，导入项"+item+"未找到或者您提供的导入项不对！");
			}
		}
		
		List<String> error = new ArrayList<String>();
		
		//校验是否含有重复数据
		Map<String,Integer> workerNum = new HashMap<String,Integer>();
		int i=0;
		for(Map<String,String> worker:xlsData.getData()) {
			i++;
			String workerNo = worker.get("工号");
			if(workerNo==null||workerNo.equals("")) {
				error.add("第"+i+"行,工号为空，请填写工号！");
				continue;
			}
			Integer num = workerNum.get(workerNo); 
			if(num==null) {
				num = 0;
			}
			num++;
			workerNum.put(workerNo, num);
		}
		
		for(String key:workerNum.keySet()) {
			if(workerNum.get(key)>1) {
				throw new HaoException("999999","工号"+key+"有多条数据，请校验后再导入！");
			}
		}
		
		
		//查询本次的工资
		Salary salary = this.jdbcDao.queryBean(Salary.class, "id", "salaryId");
		
		List<SqlWrite> sqlWrites = new ArrayList<SqlWrite>();
		
		for(Map<String,String> worker:xlsData.getData()) {
			String workerNo = worker.get("工号");
			if(workerNo==null||workerNo.equals("")) {
				continue;
			}
			EmploymentInfo e = employeeMap.get(workerNo);
			if(e==null) {
				error.add("工号"+worker.get("工号")+"不存在，请检查工号是否正确。");
				continue;
			}
			//判断员工的状态是否满足
			//@Attribute(title="状态",remark="状态-2离职 -1停职  0在职")
			int state = e.getState();
			//获取员工的离职日期
			Date leaveDate = e.getLeaveDate();
			//更具日期和状态判断是否结算此员工的工资
		    if(state!=0) {
		    	if(leaveDate.getTime()<salary.getEndDate().getTime()) {
		    		error.add("工号"+worker.get("工号")+"已经离职，这里不做处理。");
		    		continue;
		    	}
		    }
		    //生成考勤数据
		    Map<String,String> attendanceItemValue = new HashMap<String,String>();
		    for(SalaryAttendanceItem item:salaryAttendanceItemList) {
				String value = worker.get(item.getName());
				attendanceItemValue.put(item.getId()+"",value);
			}
		    
		    SqlWrite delSalaryAttendance = new SqlWrite(SalaryEmployeeAttendance.class,SqlWriterAction.delete);
		    delSalaryAttendance.addCondition("salaryId", salaryId);
		    sqlWrites.add(delSalaryAttendance);
		    
		    for(String key:attendanceItemValue.keySet()) {
		    	SalaryEmployeeAttendance _a = new SalaryEmployeeAttendance();
		    	_a.setCompanyId(Long.parseLong(companyId));
		    	_a.setIdCard(e.getIdCard());
		    	_a.setEmployerId(e.getEmployerId());
		    	_a.setWorkerNo(e.getWorkerNo());
		    	_a.setSalaryId(Long.parseLong(salaryId));
		    	_a.setValue(attendanceItemValue.get(key));
		    	_a.setAttendanceItemId(key);
		    	_a.setId(Seq.getNextId());
		    	SqlWrite insertA = new SqlWrite(SalaryEmployeeAttendance.class,SqlWriterAction.insert);
		    	insertA.setFields(BeanUtil.copyBean2Map(_a));
		    	sqlWrites.add(insertA);
		    }
		    
		    //删除原人员
		    SqlWrite delSalaryEmployee = new SqlWrite(SalaryEmployee.class,SqlWriterAction.delete); 
		    delSalaryEmployee.addCondition("salaryId", salaryId);
		    delSalaryEmployee.addCondition("idCard",e.getIdCard());
		    sqlWrites.add(delSalaryEmployee);
		    
		    //添加人员
		    SalaryEmployee salaryEmployee = new SalaryEmployee();
		    try {
				BeanUtil.copyBean2Bean(salaryEmployee, e);
				salaryEmployee.setId(Seq.getNextId());
				salaryEmployee.setSalaryId(Long.parseLong(salaryId));
				SqlWrite insertSalaryEmployee = new SqlWrite(SalaryEmployee.class,SqlWriterAction.insert);
				insertSalaryEmployee.setFields(BeanUtil.copyBean2Map(salaryEmployee));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		this.jdbcDao.executeBatch(sqlWrites);
		return error;
	}
	
	
	private String getEmployeeAttendanceValueKey(String salaryId,String idcard,String value) {
		return salaryId+"_"+idcard+"_"+value;
	}

	
	private List<ChangeSalaryLog> getWorkerChangeSalaryLog(List<ChangeSalaryLog> list,long employeeId){
		List<ChangeSalaryLog> tagList = new ArrayList<ChangeSalaryLog>();
		for(ChangeSalaryLog log:list) {
			if(log.getEmployeeId()==employeeId) {
				tagList.add(log);
			}
		}
		//按照日期排序
		Collections.sort(tagList,new Comparator<ChangeSalaryLog>() {

			@Override
			public int compare(ChangeSalaryLog arg0, ChangeSalaryLog arg1) {
				if(arg0.getDate().getTime()<arg1.getDate().getTime()) {
					return 1;
				}else if(arg0.getDate().getTime()>arg1.getDate().getTime()) {
					return -1;
				}else {
					return 0;
				}
			}
		});
		return tagList;
	}
	
	/**
	 * 计算员工的津贴工资
	 * @param list
	 * @return
	 */
	private Map<String,BigDecimal> sumEmployeeAllowen(List<SalaryEmployeeAllowanceValue> list){
		Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
		for(SalaryEmployeeAllowanceValue a:list) {
			BigDecimal value = BigDecimal.ZERO;
			if(map.containsKey(a.getWorkerId())) {
				value = map.get(a.getWorkerId());
			}
			value = value.add(a.getVlaue());
			map.put(a.getWorkerId(), value);
		}
		return map;
	}
	
	/***
	 * 计算工人工资
	 * @return
	 * @throws HaoException 
	 */
	public List<String> computeSalary(String salaryId) throws HaoException{
		List<String> error = new ArrayList<String>();
		Salary salary = this.jdbcDao.queryBean(Salary.class,"id",salaryId);
		if(salary==null) {
			error.add("工资批次不存在！");
			return error;
		}
		
		//获取员工的结算信息
		List<SalaryEmployee> employeeList = this.jdbcDao.queryList(SalaryEmployee.class,"salaryId",salaryId);
		if(employeeList==null||employeeList.size()==0) {
			error.add("员工数据不存在，请先设置员工信息或者导入考勤数据！");
			return error;
		}
		//查询当前批次的社保比例
		SalaryAppSetting salaryAppSetting = this.jdbcDao.queryBean(SalaryAppSetting.class,"salaryId",salaryId);
		//查询当前批次的个税比例
		List<SalaryAppIncomeTax> salaryAppIncomeTaxList = this.jdbcDao.queryList(SalaryAppIncomeTax.class,"salaryId",salaryId);
		//查询本批次的工资项目
		List<SalaryAttendanceItem> attendanceItmeList = this.jdbcDao.queryList(SalaryAttendanceItem.class,"salaryId",salaryId); 
		//查询本批次的工资项数据。
		List<SalaryEmployeeAttendance> employeeAttendanceValueList = this.jdbcDao.queryList(SalaryEmployeeAttendance.class,"salaryId",salaryId);
		Map<String,String> employeeAttendaceValueMap = new HashMap<String,String>();
		for(SalaryEmployeeAttendance salaryEmployeeAttendance:employeeAttendanceValueList) {
			String key = getEmployeeAttendanceValueKey(salaryEmployeeAttendance.getSalaryId()+"",salaryEmployeeAttendance.getIdCard(),salaryEmployeeAttendance.getAttendanceItemId());
			employeeAttendaceValueMap.put(key, salaryEmployeeAttendance.getValue());
		}
		
		//获取工资计算项目
		List<SalaryItem> salaryItemList = this.jdbcDao.queryList(SalaryItem.class,"salaryId",salaryId);
		//获取本次工资的所有工资数据
		List<SalaryEmployeeAllowanceValue> salaryEmployeeAllowanceList = this.jdbcDao.queryList(SalaryEmployeeAllowanceValue.class, "salaryId", salaryId);
		 Map<String,BigDecimal> employeeAllowen =sumEmployeeAllowen(salaryEmployeeAllowanceList);  
		
		
		//获取当前工资计算周期内所有的调薪记录
		SqlSearch searchSalrayLog = new SqlSearch(ChangeSalaryLog.class);
		searchSalrayLog.addCondition("date", SqlOperator.greaterEqual,salary.getBeginDate());
		searchSalrayLog.addCondition("date", SqlOperator.lessEqual,salary.getEndDate());
		searchSalrayLog.addCondition("companyId", salary.getCompanyId());
		
		List<ChangeSalaryLog> changeSalaryLogList = jdbcDao.queryList(searchSalrayLog);
		//计算从开始到结束时间的法定工作日，在2009年之前不精确
		int  dayNum = DateUtil.getWorkDayNum(salary.getBeginDate(),salary.getEndDate());
		
		//做覆盖操作，先此次工资的所有工资项目，在插入
		SqlWrite delEmployeeSalary = new SqlWrite(SalaryEmployeeItemValue.class,SqlWriterAction.delete);
		delEmployeeSalary.addCondition("salaryId", salaryId);
		delEmployeeSalary.addCondition("companyId", salary.getCompanyId());
		this.jdbcDao.execute(delEmployeeSalary);
		
		
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		List<Object[]> updateShuiArgs = new ArrayList<Object[]>();
		
		for(SalaryEmployee e:employeeList) {
			
			//计算员工共的工龄日
			int glDay = DateUtil.calcDayOffset(e.getBeginTime(), e.getLeaveDate());
			//计算员工共的功能年
			int glYear = DateUtil.getYearDiff(e.getLeaveDate(),e.getBeginTime());
			
			
			//设置计算常量
			HashMap<String,Object> _itemV = new HashMap<String,Object>();
			for(SalaryAttendanceItem attendanceItem:attendanceItmeList) {
				String key =getEmployeeAttendanceValueKey(e.getSalaryId()+"",e.getIdCard(),attendanceItem.getId()+"");
				String value = employeeAttendaceValueMap.get(key);
				_itemV.put(attendanceItem.getName(), value);
			}
			
			
			_itemV.put("离职日期",e.getLeaveDate().getTime());
			_itemV.put("合同形式",e.getEmploymentTypeName());
			_itemV.put("入职日期",e.getBeginTime().getTime());
			_itemV.put("离职类型",e.getLeaveTypeName());
			_itemV.put("办理离职日期",e.getRealyLeaveDate().getTime());
			_itemV.put("状态",e.getState());
			_itemV.put("大项目",e.getBaseProject().getName());
			_itemV.put("项目",e.getProject().getName());
			_itemV.put("产线",e.getLine().getName());
			_itemV.put("部门",e.getDepartment().getName());
			_itemV.put("职务",e.getPoint().getName());
			_itemV.put("社保类型",e.getSocialSecurityType());
			_itemV.put("社保缴法",e.getSocialSecurityAlgorithm());
			_itemV.put("日工龄",glDay);
			_itemV.put("年工龄",glYear);
			_itemV.put("性别",e.getSexName());
			_itemV.put("雇佣公司",e.getEmployerName());
			_itemV.put("法定工作日",dayNum);
			_itemV.put("全年平均应出勤天数", salaryAppSetting.getAverageAnnualAttendanceDays());
			_itemV.put("基本工资",e.getBaseSalary());
			_itemV.put("基本工资",e.getBaseSalary());
			_itemV.put("养老比例",salaryAppSetting.getSbblYanglao().divide(new BigDecimal(100),4, BigDecimal.ROUND_DOWN));
			_itemV.put("失业比例",salaryAppSetting.getSbblShiye().divide(new BigDecimal(100),4, BigDecimal.ROUND_DOWN));
			_itemV.put("医疗比例",salaryAppSetting.getSbblYiliao().divide(new BigDecimal(100),4, BigDecimal.ROUND_DOWN));
			_itemV.put("公积金比例",salaryAppSetting.getSbblGongjijin().divide(new BigDecimal(100),4, BigDecimal.ROUND_DOWN));
			
			List<ChangeSalaryLog> userChangeSalaryLogList = getWorkerChangeSalaryLog(changeSalaryLogList,e.getEmployeeId());
			//判断区分计算还是非区分计算（区分计算即调整前按照调整的计算，调整后按照调整后的计算）
			boolean isQfjs = false;
			Date qfjsDate = null;
			BigDecimal forntSalary = BigDecimal.ZERO;
			BigDecimal afterSalary = BigDecimal.ZERO;
			if(userChangeSalaryLogList!=null&&userChangeSalaryLogList.size()>0) {
				ChangeSalaryLog lastChangeSalaryLog = userChangeSalaryLogList.get(0);
				if(lastChangeSalaryLog.getSalaryType()!=null&&lastChangeSalaryLog.getSalaryType().equals("2")) {
					isQfjs =true;
					qfjsDate =  lastChangeSalaryLog.getDate();
					forntSalary = lastChangeSalaryLog.getOldSalary();
					afterSalary = lastChangeSalaryLog.getSalary();
				}
			}
			
			List<BigDecimal> yfs = new ArrayList<BigDecimal>();
			
			
			//计算应发
			if(isQfjs==false) {
				for(SalaryItem item:salaryItemList) {
					if(item.getYfSalary()==0) {
						continue;
					}
					Object value = JSScript.run(item.getScript(), _itemV);
					BigDecimal tempValue =ConverterUtils.objectToDecimal(value);
					if(item.getOperation().equals("0")) {
						tempValue =  tempValue.multiply(new BigDecimal(-1));
					}
					
					//插入数据
					Object[] params = new Object[6];
					params[0]=salaryId;
					params[1]=salary.getCompanyId();
					params[2]=item.getId();
					params[3]=item.getName();
					params[4]=e.getIdCard();
					params[5]=tempValue;
					batchArgs.add(params);
					yfs.add(tempValue);
				}
			}else {
				Calendar c = Calendar.getInstance();
				c.setTime(qfjsDate);
				c.add(Calendar.DAY_OF_YEAR, 1);
				int  frontDayNum = DateUtil.getWorkDayNum(salary.getBeginDate(),c.getTime());
				int  afterDayNum = DateUtil.getWorkDayNum(c.getTime(),salary.getEndDate());
				
				BigDecimal forntP = (new BigDecimal(frontDayNum)).divide(new BigDecimal(dayNum),2,BigDecimal.ROUND_DOWN);
				BigDecimal afterP = (new BigDecimal(afterDayNum)).divide(new BigDecimal(dayNum),2,BigDecimal.ROUND_DOWN);
				
				for(SalaryItem item:salaryItemList) {
					if(item.getYfSalary()==0) {
						continue;
					}
					BigDecimal tempValue = BigDecimal.ZERO;
					if(item.getChangeSalaryDiffCalculation().equals("1")) {
						//需要拆分计算
						//先按照调薪前计算
						HashMap<String,Object> forntItemV = (HashMap<String, Object>) _itemV.clone();
						forntItemV.put("基本工资",forntSalary);
						Object  forentValue = JSScript.run(item.getScript(), forntItemV);
						
						//再按照调薪后计算
						HashMap<String,Object> afterItemV = (HashMap<String, Object>) _itemV.clone();
						afterItemV.put("基本工资",afterSalary);
						Object  afterValue = JSScript.run(item.getScript(), afterItemV);
						
						//按照比例取值
						tempValue = forntP.multiply(ConverterUtils.objectToDecimal(forentValue)).add(afterP.multiply(ConverterUtils.objectToDecimal(afterValue)));
					}else {
						Object value = JSScript.run(item.getScript(), _itemV);
						tempValue=ConverterUtils.objectToDecimal(value);
					}
					//去负数
					if(item.getOperation().equals("0")) {
						tempValue =  tempValue.multiply(new BigDecimal(-1));
					}
					Object[] params = new Object[6];
					params[0]=salaryId;
					params[1]=salary.getCompanyId();
					params[2]=item.getId();
					params[3]=item.getName();
					params[4]=e.getIdCard();
					params[5]=tempValue;
					batchArgs.add(params);
					yfs.add(tempValue);
				}
			}
			
			
			//计算应发工资
			BigDecimal yfgz = BigDecimal.ZERO;//基本工资 
			for(BigDecimal i:yfs) {
				yfgz = yfgz.add(i);
			}
			yfgz.add(employeeAllowen.get(e.getWorkerNo())==null?BigDecimal.ZERO:employeeAllowen.get(e.getWorkerNo()));//应付工资加入津贴
			
			
			//计算社保扣发等数据
			if(e.getSocialSecurityType()==0) {
				_itemV.put("社保应缴数",0);
			}else if(e.getSocialSecurityType()==1) {
				_itemV.put("社保应缴数",salaryAppSetting.getSbjs());
			}else if(e.getSocialSecurityType()==2) {
				_itemV.put("社保应缴数",yfgz);
			}else {
				_itemV.put("社保应缴数",0);
			}
			if(e.getGjjType()==0) {
				_itemV.put("公积金应缴数",0);
			}else if(e.getGjjType()==1) {
				_itemV.put("公积金应缴数",salaryAppSetting.getGjjjs());
			}else if(e.getGjjType()==2) {
				_itemV.put("公积金应缴数",yfgz);
			}else {
				_itemV.put("公积金应缴数",0);
			}
			
			List<BigDecimal> kflist = new ArrayList<BigDecimal>();
			
			for(SalaryItem item:salaryItemList) {
				if(item.getYfSalary()==0) {
					Object value = JSScript.run(item.getScript(), _itemV);
					BigDecimal tempValue =ConverterUtils.objectToDecimal(value);
					tempValue =  tempValue.multiply(new BigDecimal(-1));
					Object[] params = new Object[6];
					params[0]=salaryId;
					params[1]=salary.getCompanyId();
					params[2]=item.getId();
					params[3]=item.getName();
					params[4]=e.getIdCard();
					params[5]=tempValue;
					batchArgs.add(params);
					kflist.add(tempValue);
				}
			}
			
			BigDecimal kf = BigDecimal.ZERO;
			for(BigDecimal i:kflist) {
				kf = kf.add(i);
			}
			
			//计算税前工资
			BigDecimal sqgz = yfgz.add(kf);//税前工资
			BigDecimal taxStartPoint = salaryAppSetting.getTaxStartPoint();//起征点
			BigDecimal shui = BigDecimal.ZERO;
			if(sqgz.compareTo(taxStartPoint)==1) {
				//如果大于起征点则计算个税
				BigDecimal yuenae = sqgz.subtract(taxStartPoint);
				//按照起征点排序
				Collections.sort(salaryAppIncomeTaxList,new Comparator<SalaryAppIncomeTax>() {
					@Override
					public int compare(SalaryAppIncomeTax arg0, SalaryAppIncomeTax arg1) {
						if(arg0.getLowNum().compareTo(arg1.getLowNum())==0) {
							return 0;
						}
						if(arg0.getLowNum().compareTo(arg1.getLowNum())==-1) {
							return 1;
						}
						if(arg0.getLowNum().compareTo(arg1.getLowNum())==1) {
							return -1;
						}
						return 0;
					}
					
				});
				for(SalaryAppIncomeTax tax:salaryAppIncomeTaxList) {
					if(yuenae.compareTo(tax.getLowNum())==1) {
						shui =  yuenae.multiply(tax.getProportion().divide(new BigDecimal(100))).subtract(tax.getSskcs());
						break;
					}
				}
			}
			
			//更新税务
			Object[] shuiItem = new Object[5];
			shuiItem[0]=yfgz;
			shuiItem[1]=shui;
			shuiItem[2]=sqgz;
			shuiItem[3]=sqgz.subtract(shui) ;
			shuiItem[4]=e.getId();
			updateShuiArgs.add(shuiItem);
		}
		
		this.jdbcDao.batchInsert(
				SalaryEmployeeItemValue.class, 
				new String[] {"salaryId","companyId","itemId","itemName","idCard","vlaue"}, 
				batchArgs);
		
		this.jdbcDao.batchUpdate(SalaryEmployee.class, "id", new String[] {"yfgz","shui","sqgz","sfgz"}, updateShuiArgs);
		return error;
	}
	
	/**
	 * 导入员工津贴业务
	 * @param xlsData
	 * @param salaryId
	 * @param companyId
	 * @throws HaoException 
	 */
	public List<String> importEmployeeAllowance(ExcelTool xlsTool, String salaryId, String companyId) throws HaoException {
		List<String> error = new ArrayList<String>();
		//查询此次工资的信息
		Salary salary = this.jdbcDao.queryBean(Salary.class,"id",salaryId);
		if(salary==null) {
			error.add("工资批次不存在！");
			return error;
		}
		//获取员工的结算信息
		List<SalaryEmployee> employeeList = this.jdbcDao.queryList(SalaryEmployee.class,"salaryId",salaryId);
		if(employeeList==null||employeeList.size()==0) {
			error.add("员工数据不存在，请先设置员工信息或者导入考勤数据！");
			return error;
		}
		Map<String,SalaryEmployee> employeeMap = ClassUtils.listToMap(employeeList,"workerNo");
		
		
		SqlSearch searchSalaryAllowance = new SqlSearch(SalaryAllowance.class);
		searchSalaryAllowance.addCondition("salaryId", salaryId);
		List<SalaryAllowance> allowances = this.jdbcDao.queryList(searchSalaryAllowance);
		
		int  dayNum = DateUtil.getWorkDayNum(salary.getBeginDate(),salary.getEndDate());
		
		//数据设置
		SalaryAppSetting salaryAppSetting = this.jdbcDao.queryBean(SalaryAppSetting.class,"salaryId",salaryId);
		
		//查询本批次的工资项目
		List<SalaryAttendanceItem> attendanceItmeList = this.jdbcDao.queryList(SalaryAttendanceItem.class,"salaryId",salaryId); 
		//查询本批次的工资项数据。
		List<SalaryEmployeeAttendance> employeeAttendanceValueList = this.jdbcDao.queryList(SalaryEmployeeAttendance.class,"salaryId",salaryId);
		Map<String,String> employeeAttendaceValueMap = new HashMap<String,String>();
		for(SalaryEmployeeAttendance salaryEmployeeAttendance:employeeAttendanceValueList) {
			String key = getEmployeeAttendanceValueKey(salaryEmployeeAttendance.getSalaryId()+"",salaryEmployeeAttendance.getIdCard(),salaryEmployeeAttendance.getAttendanceItemId());
			employeeAttendaceValueMap.put(key, salaryEmployeeAttendance.getValue());
		}
		
		for(SalaryAllowance allowance:allowances) {
			XLSData xlsData=xlsTool.read(allowance.getName());
			if(xlsData==null) {
				error.add("没有找到津贴“"+allowance.getName()+"”");
				continue;
			}
			//导入津贴的逻辑是覆盖导入。先删除原先的数据，再导入新的数据
			SqlWrite delEmployeeAllowance = new SqlWrite(SalaryEmployeeAllowanceValue.class,SqlWriterAction.delete);
			delEmployeeAllowance.addCondition("salaryId", salaryId);
			this.jdbcDao.execute(delEmployeeAllowance);
			
			int index = 0;
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			for(String[] items:xlsData.getDataArray()) {
				//先判断员工编号有无
				String workerNo = items[0];
				if(workerNo==null||workerNo.equals("")) {
					index++;
					error.add("第"+(index+1)+"行，第一列员工编号为空，跳过次行");
					continue;
				}
				SalaryEmployee e = employeeMap.get(workerNo);
				if(e==null) {
					index++;
					error.add("第"+(index+1)+"行，提供的工号不存在，或者未导入考勤数据。如果未导入考勤请先导入考勤");
					continue;
				}
				
				//计算员工共的工龄日
				int glDay = DateUtil.calcDayOffset(e.getBeginTime(), e.getLeaveDate());
				//计算员工共的功能年
				int glYear = DateUtil.getYearDiff(e.getLeaveDate(),e.getBeginTime());
				
				HashMap<String,Object> _itemV = new HashMap<String,Object>();
				for(SalaryAttendanceItem attendanceItem:attendanceItmeList) {
					String key =getEmployeeAttendanceValueKey(e.getSalaryId()+"",e.getIdCard(),attendanceItem.getId()+"");
					String value = employeeAttendaceValueMap.get(key);
					_itemV.put(attendanceItem.getName(), value);
				}
				
				_itemV.put("离职日期",e.getLeaveDate().getTime());
				_itemV.put("合同形式",e.getEmploymentTypeName());
				_itemV.put("入职日期",e.getBeginTime().getTime());
				_itemV.put("离职类型",e.getLeaveTypeName());
				_itemV.put("办理离职日期",e.getRealyLeaveDate().getTime());
				_itemV.put("状态",e.getState());
				_itemV.put("大项目",e.getBaseProject().getName());
				_itemV.put("项目",e.getProject().getName());
				_itemV.put("产线",e.getLine().getName());
				_itemV.put("部门",e.getDepartment().getName());
				_itemV.put("职务",e.getPoint().getName());
				_itemV.put("社保类型",e.getSocialSecurityType());
				_itemV.put("社保缴法",e.getSocialSecurityAlgorithm());
				_itemV.put("日工龄",glDay);
				_itemV.put("年工龄",glYear);
				_itemV.put("性别",e.getSexName());
				_itemV.put("雇佣公司",e.getEmployerName());
				_itemV.put("法定工作日",dayNum);
				_itemV.put("全年平均应出勤天数", salaryAppSetting.getAverageAnnualAttendanceDays());
				_itemV.put("基本工资",e.getBaseSalary());
				_itemV.put("基本工资",e.getBaseSalary());
				_itemV.put("养老比例",salaryAppSetting.getSbblYanglao());
				_itemV.put("失业比例",salaryAppSetting.getSbblShiye());
				_itemV.put("医疗比例",salaryAppSetting.getSbblYiliao());
				_itemV.put("公积金比例",salaryAppSetting.getSbblGongjijin());
				
				String value=items[1];
				if(value==null||value.equals("")) {
					index++;
					error.add("第"+(index+1)+"行，第二列津贴值为空，跳过次行");
					continue;
				}
				_itemV.put(allowance.getName(), value);
				Object tagValue =  JSScript.run(allowance.getSummayScript(), _itemV);
				Object[] args = new Object[7];
				args[0]=salaryId;
				args[1]=companyId;
				args[2]=allowance.getId();
				args[3]=tagValue;
				args[4]=e.getId();
				args[5]=workerNo;
				batchArgs.add(args);
				index++;
			}
			jdbcDao.batchInsert(SalaryEmployeeAllowanceValue.class,
					new String[] {"salaryId","companyId","allowanceId","vlaue","idCard","employeeId","workerId"},
					batchArgs);	
		}
		return error;
	}

	
	public EmployerSalaryData getEmployyerSalaryData(String salaryId) throws HaoException{
		EmployerSalaryData salarydata = new EmployerSalaryData();
		Salary salary = this.jdbcDao.queryBean(Salary.class, "id",salaryId);
		salarydata.setSalaryId(salaryId);
		salarydata.setSalary(salary);
		//获取员工的结算信息
		List<SalaryEmployee> employeeList = this.jdbcDao.queryList(SalaryEmployee.class,"salaryId",salaryId);
		
		List<String> header = new ArrayList<String>();
		header.add("工号");
		header.add("姓名");
		header.add("身份证");
		header.add("应发工资");
		header.add("税前工资");
		header.add("个税");
		header.add("实发");
 		//工资项目
		List<SalaryItem> salaryItemList = this.jdbcDao.queryList(SalaryItem.class,"salaryId",salaryId);
		//津贴数据
		SqlSearch searchSalaryAllowance = new SqlSearch(SalaryAllowance.class);
		searchSalaryAllowance.addCondition("salaryId", salaryId);
		List<SalaryAllowance> allowances = this.jdbcDao.queryList(searchSalaryAllowance);
		for(SalaryItem i:salaryItemList) {
			header.add(i.getName());
		}
		for(SalaryAllowance i:allowances) {
			header.add(i.getName());
		}
		salarydata.setHeaders(header);
		
		List<SalaryEmployeeAllowanceValue> salaryEmployeeAllowanceList = this.jdbcDao.queryList(SalaryEmployeeAllowanceValue.class, "salaryId", salaryId);
		Map<String,BigDecimal> allowanceValueMap = new HashMap<String,BigDecimal>();
		for(SalaryEmployeeAllowanceValue i:salaryEmployeeAllowanceList) {
			allowanceValueMap.put(i.getWorkerId()+"_"+i.getAllowanceId(),i.getVlaue());
		}
		
		List<SalaryEmployeeItemValue> itemValueList = this.jdbcDao.queryList(SalaryEmployeeItemValue.class, "salaryId", salaryId);
		Map<String,BigDecimal> itemValueMap = new HashMap<String,BigDecimal>();
		for(SalaryEmployeeItemValue i:itemValueList) {
			itemValueMap.put(i.getWorkerId()+"_"+i.getItemId(),i.getVlaue());
		}
		
		List<Object[]> datas = new ArrayList<Object[]>();
		for(SalaryEmployee e:employeeList) {
			Object[] items = new Object[header.size()];
			int i=0;
			for(String s:header) {
				if(i==0) {
					items[0] = e.getWorkerNo();
				}
				if(i==1) {
					items[1] = e.getName();
				}
				if(i==2) {
					items[2] = e.getIdCard();
				}
				if(i==3) {
					items[3] = e.getYfgz();
				}
				if(i==4) {
					items[4] = e.getSqgz();
				}
				if(i==5) {
					items[5] = e.getShui();
				}
				if(i==6) {
					items[6] = e.getYfgz();
				}
				if((i+1+7)<=salaryItemList.size()) {
					items[i] = allowanceValueMap.get(e.getWorkerNo()+"_"+s);
				}else {
					items[i] = itemValueMap.get(e.getWorkerNo()+"_"+s);
				}
				i++;
			}
			datas.add(items);
		}
		salarydata.setDatas(datas);
		return salarydata;
	}
	
	
}
