package hao.webapp.demo.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/***
 * js脚本引擎,计算动态定义的工资栏目的公式
 * @author chianghao
 */
public class JSScript {
	
	static Logger log = LogManager.getLogger("javascript脚本计算");
	
	
	/**
	 * 工资脚本脚本计算
	 * @param script   脚本
	 * @param bindings 绑定元素
	 * @return
	 */
	public static Object run(String script,Map<String,Object> bindings) {
		StringBuffer sb = new StringBuffer();
		sb.append("  function toDate(str2){ ");
		sb.append("     var arr2 = str2.split(\"-\");  ");
		sb.append("     return (new Date(arr2[0],parseInt(arr2[1])-1,arr2[2])).getTime(); ");
		sb.append("  }   ");
		sb.append(" function calculation() {"+script+"}; calculation();   ");
		String calculationScript=sb.toString();
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		SimpleBindings simpleBindings= new SimpleBindings();
		simpleBindings.putAll(bindings);
		try {
			return engine.eval(calculationScript,simpleBindings);
		} catch (ScriptException e) {
			log.info("工资脚本计算错误|脚本："+script+"|绑定元素："+bindings.toString());
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) throws ScriptException, NoSuchMethodException {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("离职日期", (new Date()).getTime());
		System.out.println(run(
				"  if(离职日期<toDate(\"2018-07-10\")){ "
				+ "   return 1; "
				+ "}else{"
				+ "	  return 0;"
				+ "}"
				+ "",map));
	}
	
	
	
	
}
