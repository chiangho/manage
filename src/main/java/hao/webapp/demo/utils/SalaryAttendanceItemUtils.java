package hao.webapp.demo.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hao.webapp.demo.model.SalaryAttendanceItem;

public class SalaryAttendanceItemUtils {

	
	/**
	 * 转换成计算需要的列头
	 * @param list
	 * @return
	 */
	public static Set<String> toSet(List<SalaryAttendanceItem> list){
		Set<String> sets = new HashSet<String>();
		for(SalaryAttendanceItem i:list) {
			sets.add(i.getName());
		}
		return sets;
	}
	
}
