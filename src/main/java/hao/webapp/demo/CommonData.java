package hao.webapp.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import hao.webapp.demo.model.Employee;
import hao.webapp.demo.model.EmploymentInfo;

/**
 * 公共数据
 * @author chianghao
 *
 */
public class CommonData {
	
	/**
	 * 性别
	 * @author  chianghao
	 * @time    2018年3月31日
	 * @version 0.1
	 *
	 */
	public enum SEX{
		
		male("1","男"),
		female("0","女");
		
		private SEX(String code,String value) {
			this.code  =  code;
			this.value =  value;
		}
		private String code;
		private String value;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public static List<Map<String,String>> toList(){
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(SEX s:SEX.values()) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("code" ,s.getCode() );
				map.put("value",s.getValue() );
				list.add(map);
			}
			return list;
		}
		
		/**
		 * 根据code获取值
		 * @param code
		 * @return
		 */
		public static String getValueByCode(String code) {
			for(SEX s:SEX.values()) {
				if(s.getCode().equals(code)) {
					return s.getValue();
				}
			}
			return "";
		}
		
	}
	
	
	/**
	 * 学历数据
	 * @author  chianghao
	 * @time    2018年3月31日
	 * @version 0.1
	 */
	public enum EDUCATION{
		
		primary("1","小学"),
		junior("2","初中"),
		senior("3","高中"),
		secondary("4","中专"),
		college("5","大专"),
		undergraduate("5","本科"),
		master("7","硕士"),
		doctor("8","博士");
		
		private EDUCATION(String code,String name) {
			this.code = code;
			this.value = name;
		}
		private String code;
		private String value;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		/**
		 * 装换成list
		 * @return
		 */
		public static List<Map<String,String>> toList(){
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(EDUCATION s:EDUCATION.values()) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("code" ,s.getCode() );
				map.put("value",s.getValue() );
				list.add(map);
			}
			return list;
		}
		
		/**
		 * 根据code获取值
		 * @param code
		 * @return
		 */
		public static String getValueByCode(String code) {
			for(EDUCATION s:EDUCATION.values()) {
				if(s.getCode().equals(code)) {
					return s.getValue();
				}
			}
			return "";
		}
		
	}
	
	
	/**
	 * 雇主
	 */
	public static String[][] EMPLOYERS ={
		{"1","无锡阿波罗企业管理有限公司"},
		{"2","无锡优利优工业服务外包有限公司"}
	};
	
	/**
	 * 雇主装换成list
	 * @return
	 */
	public static List<Map<String,String>> employersToList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(String[] employer:EMPLOYERS) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("code" ,employer[0] );
			map.put("value",employer[1] );
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据code 获取名称
	 * @param code
	 * @return
	 */
	public static String getEmployerNameByCode(String code) {
		for(String[] employer:EMPLOYERS) {
			if(code.equals(employer[0])) {
				return employer[1];
			}
		}
		return "";
	}
	
	public static String getEmployerCodeByName(String name) {
		for(String[] employer:EMPLOYERS) {
			if(name.equals(employer[1])) {
				return employer[0];
			}
		}
		return "";
	}
	
	
	/**
	 *  雇佣类型
	 */
	public static String[][] EMPLOYMENT_TYPE= {
			{"1","普通合同"},
			{"2","小时工"},
			{"3","零时工"},
			{"4","实习生"},
			{"5","寒假工"}
	};
	
	/**
	 * 雇用类型装换成list
	 * @return
	 */
	public static List<Map<String,String>> employmentTypeToList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(String[] type:EMPLOYMENT_TYPE) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("code" ,type[0] );
			map.put("value",type[1] );
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据code 获取名称
	 * @param code
	 * @return
	 */
	public static String getEmploymentTypeNameByCode(String code) {
		for(String[] type:EMPLOYMENT_TYPE) {
			if(code.equals(type[0])) {
				return type[1];
			}
		}
		return "";
	}
	public static String getEmploymentTypeCodeByName(String name) {
		for(String[] type:EMPLOYMENT_TYPE) {
			if(name.equals(type[1])) {
				return type[0];
			}
		}
		return "";
	}
	
	/**
	 * 离职类型
	 */
	public static String[][] leaveType= {
			{"1","自离"},
			{"2","辞职"},
			{"3","开除"}
	};
	/**
	 * 离职类型
	 * @return
	 */
	public static List<Map<String,String>> leaveTypeToList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(String[] type:leaveType) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("code" ,type[0] );
			map.put("value",type[1] );
			list.add(map);
		}
		return list;
	}
	/**
	 * 获取名称获取离职类型名称
	 * @param code
	 * @return
	 */
	public static String getleaveTypeNameByCode(String code) {
		for(String[] type:leaveType) {
			if(code.equals(type[0])) {
				return type[1];
			}
		}
		return "";
	}
	public static String getleaveTypeCodeByName(String name) {
		for(String[] type:leaveType) {
			if(name.equals(type[1])) {
				return type[0];
			}
		}
		return "";
	}
	
	
	public static String[][] BANKTYPE= {
			{"1","中国银行"},
			{"2","农业银行"},
			{"3","建设银行"},
			{"4","工商银行"},
			{"5","交通银行"},
			{"6","江苏银行"},
			{"7","中国邮政储蓄银行"},
			{"8","农村商业银行"}
	};	
	/**
	 * 雇用类型装换成list
	 * @return
	 */
	public static List<Map<String,String>> bankTypeToList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(String[] type:BANKTYPE) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("code" ,type[0] );
			map.put("value",type[1] );
			list.add(map);
		}
		return list;
	}
	/**
	 * 根据code 获取名称
	 * @param code
	 * @return
	 */
	public static String getBankTypeNameByCode(String code) {
		for(String[] type:BANKTYPE) {
			if(code.equals(type[0])) {
				return type[1];
			}
		}
		return "";
	}
	public static String getBankTypeCodeByName(String name) {
		for(String[] type:BANKTYPE) {
			if(name.equals(type[1])) {
				return type[0];
			}
		}
		return "";
	}
	
	private static final String nationString = "[{\"id\":\"1\",\"name\":\"汉族\"},{\"id\":\"2\",\"name\":\"蒙古族\"},{\"id\":\"3\",\"name\":\"回族\"}," + 
			"{\"id\":\"4\",\"name\":\"藏族\"},{\"id\":\"5\",\"name\":\"维吾尔族\"},{\"id\":\"6\",\"name\":\"苗族\"}," + 
			"{\"id\":\"7\",\"name\":\"彝族\"},{\"id\":\"8\",\"name\":\"壮族\"},{\"id\":\"9\",\"name\":\"布依族\"}," + 
			"{\"id\":\"10\",\"name\":\"朝鲜族\"},{\"id\":\"11\",\"name\":\"满族\"},{\"id\":\"12\",\"name\":\"侗族\"}," + 
			"{\"id\":\"13\",\"name\":\"瑶族\"},{\"id\":\"14\",\"name\":\"白族\"},{\"id\":\"15\",\"name\":\"土家族\"}," + 
			"{\"id\":\"16\",\"name\":\"哈尼族\"},{\"id\":\"17\",\"name\":\"哈萨克族\"},{\"id\":\"18\",\"name\":\"傣族\"}," + 
			"{\"id\":\"19\",\"name\":\"黎族\"},{\"id\":\"20\",\"name\":\"傈僳族\"},{\"id\":\"21\",\"name\":\"佤族\"}," + 
			"{\"id\":\"22\",\"name\":\"畲族\"},{\"id\":\"23\",\"name\":\"高山族\"},{\"id\":\"24\",\"name\":\"拉祜族\"}," + 
			"{\"id\":\"25\",\"name\":\"水族\"},{\"id\":\"26\",\"name\":\"东乡族\"},{\"id\":\"27\",\"name\":\"纳西族\"}," + 
			"{\"id\":\"28\",\"name\":\"景颇族\"},{\"id\":\"29\",\"name\":\"柯尔克孜族\"},{\"id\":\"30\",\"name\":\"土族\"}," + 
			"{\"id\":\"31\",\"name\":\"达斡尔族\"},{\"id\":\"32\",\"name\":\"仫佬族\"},{\"id\":\"33\",\"name\":\"羌族\"}," + 
			"{\"id\":\"34\",\"name\":\"布朗族\"},{\"id\":\"35\",\"name\":\"撒拉族\"},{\"id\":\"36\",\"name\":\"毛难族\"}," + 
			"{\"id\":\"37\",\"name\":\"仡佬族\"},{\"id\":\"38\",\"name\":\"锡伯族\"},{\"id\":\"39\",\"name\":\"阿昌族\"}," + 
			"{\"id\":\"40\",\"name\":\"普米族\"},{\"id\":\"41\",\"name\":\"塔吉克族\"},{\"id\":\"42\",\"name\":\"怒族\"}," + 
			"{\"id\":\"43\",\"name\":\"乌孜别克族\"},{\"id\":\"44\",\"name\":\"俄罗斯族\"},{\"id\":\"45\",\"name\":\"鄂温克族\"}," + 
			"{\"id\":\"46\",\"name\":\"崩龙族\"},{\"id\":\"47\",\"name\":\"保安族\"},{\"id\":\"48\",\"name\":\"裕固族\"}," + 
			"{\"id\":\"49\",\"name\":\"京族\"},{\"id\":\"50\",\"name\":\"塔塔尔族\"},{\"id\":\"51\",\"name\":\"独龙族\"}," + 
			"{\"id\":\"52\",\"name\":\"鄂伦春族\"},{\"id\":\"53\",\"name\":\"赫哲族\"},{\"id\":\"54\",\"name\":\"门巴族\"}," + 
			"{\"id\":\"55\",\"name\":\"珞巴族\"},{\"id\":\"56\",\"name\":\"基诺族\"}]";
	public static JSONArray nationJSONArray = null;
	
	public static LinkedHashMap<String,String> nationMap = new LinkedHashMap<String,String>();
	public static List<Map<String,String>> nationList = new ArrayList<Map<String,String>>();
	
	public static String getNationById(String id) {
		return nationMap.get(id);
	}
	static {
		nationJSONArray = new JSONArray(nationString);
		for(int i=0;i<nationJSONArray.length();i++) {
			JSONObject nation = nationJSONArray.getJSONObject(i);
			nationMap.put(nation.getString("id"), nation.getString("name"));
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", nation.getString("id"));
			map.put("name", nation.getString("name"));
			nationList.add(map);
		}
	}
	
	public static String getNationIdByName(String name) {
		for(String key:nationMap.keySet()) {
			if(nationMap.get(key).equals(name)) {
				return key;
			}
		}
		return "";
	}
	
	
	public enum EmployeeItemInfo{
		idCard(Employee.class,"idCard","身份证号"),
		name(Employee.class,"name","姓名"),
		sex(Employee.class,"sex","性别"),
		phone1(Employee.class,"phone1","电话1"),
		phone2(Employee.class,"phone2","电话2"),
		email(Employee.class,"email","邮件"),
		nation(Employee.class,"nation","民族"),
		education(Employee.class,"education","学历"),
		placeOfOrigin(Employee.class,"placeOfOrigin","籍贯"),
		sourceProvince(Employee.class,"sourceProvince","省"),
		sourceCity(Employee.class,"sourceCity","地/市"),
		sourceArea(Employee.class,"sourceArea","区/县"),
		sourceAddress(Employee.class,"sourceAddress","地址"),
		livingPlace(Employee.class,"livingPlace","现居地"),
		birthday(Employee.class,"birthday","生日"),
		companyId(EmploymentInfo.class,"companyId","公司"),//所在公司
		employerId(EmploymentInfo.class,"employerId","雇佣公司"),//雇佣者
		employmentType(EmploymentInfo.class,"employmentType","雇佣类型"),//工种
		beginTime(EmploymentInfo.class,"beginTime","入职日期"),//入职日期
		leaveDate(EmploymentInfo.class,"leaveDate","离职日期"),//离职日期
		leaveType(EmploymentInfo.class,"leaveType","离职类型"),//离职日期
		realyLeaveDate(EmploymentInfo.class,"realyLeaveDate","办理离职日期"),
		contractNo(EmploymentInfo.class,"contractNo","合同编号"),//合同编号
		bankType(EmploymentInfo.class,"bankType","银行"),//银行种类
		bankCard(EmploymentInfo.class,"bankCard","银行卡号"),//银行卡
		workerNo(EmploymentInfo.class,"workerNo","工号"),//工号
		state(EmploymentInfo.class,"state","状态"),//
		productionLineId(EmploymentInfo.class,"productionLineId","产线"),//产线
		departmentId(EmploymentInfo.class,"departmentId","部门"),//部门
		pointId(EmploymentInfo.class,"pointId","职务"),//职务
		baseProjectId(EmploymentInfo.class,"baseProjectId","大项目"),//大项目
		projectId(EmploymentInfo.class,"projectId","项目"),//项目
		socialSecurityType(EmploymentInfo.class,"socialSecurityType","社保类型"),//社保类型
		socialSecurityAlgorithm(EmploymentInfo.class,"socialSecurityAlgorithm","社保缴法"),//社保类型
		baseSalary(EmploymentInfo.class,"baseSalary","基本工资");
			
		private Class<?> clazz;
		private String   field;
		private String   itemName;
		private EmployeeItemInfo(Class<?> clazz,String field,String itemName) {
			this.clazz = clazz;
			this.field = field;
			this.itemName = itemName;
		}
		public Class<?> getClazz() {
			return clazz;
		}
		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		
		public static EmployeeItemInfo getEmployyerItemInfo(String name) {
			for(EmployeeItemInfo e:EmployeeItemInfo.values()) {
				if(e.getItemName().equals(name)) {
					return e;
				}
			}
			return null;
		}
		
	}
	
	
	public static void main(String[] args) {
		for(String key:nationMap.keySet()) {
			System.out.println(key+":"+nationMap.get(key)+"\n");
		}
		System.out.println("*************************************************");
		for(Map<String,String> map:nationList) {
			System.out.println(map.get("id")+":"+map.get("name")+"");
		}
	}

	
	
	public static Set<String> getGlobleBasicComputingItem() {
		Set<String> set = new HashSet<String>();
		set.add("基本工资");
		set.add("离职日期");
		set.add("合同形式");
		set.add("入职日期");
		set.add("离职类型");
		set.add("办理离职日期");
		set.add("状态");
		set.add("大项目");
		set.add("项目");
		set.add("产线");
		set.add("部门");
		set.add("职务");
		set.add("社保类型");
		set.add("社保缴法");
		set.add("日工龄");
		set.add("年工龄");
		set.add("性别");
		set.add("雇佣公司");
		set.add("法定工作日");
		set.add("全年平均应出勤天数");
		set.add("社保应缴数");
		set.add("养老比例");
		set.add("失业比例");
		set.add("医疗比例");
		set.add("公积金应缴数");
		set.add("公积金比例");
		
		
		return set;
	}
	
}
