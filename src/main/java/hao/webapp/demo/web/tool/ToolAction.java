package hao.webapp.demo.web.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hao.framework.core.SystemConfig;
import hao.framework.core.expression.HaoException;
import hao.framework.db.SqlScriptRunner;
import hao.framework.generation.Generation;

/***
 * 系统开发工具
 * @author chianghao
 */
@Controller
@RequestMapping("dev/tool")
public class ToolAction {

	/***
	 * 代码生成
	 * @throws HaoException 
	 */
	@RequestMapping("code_generation")
	@ResponseBody
	public void codeGeneration(
			) throws HaoException {
		
		Generation.generation(SystemConfig.getEntitys());
	}
	
	/***
	 * 初始化系统数据
	 * @throws HaoException 
	 */
	@RequestMapping("init_sys_db")
	@ResponseBody
	public void init_sys_db(
			) throws HaoException {
		(new SqlScriptRunner("dataSource")).run();
	}
	
	
}
