<@p.include/>
<style>
.panel.window.panel-htop{
	top:-10px !important;
}
</style>
<form id="saveSalaryAppSettingForm" method="post" action="${domain}salary/save_salary_app_setting.action">
	
	<div class="form-item">
		<label for="sbjx" class="label-top" style="width:120px">当地最低公积金基数：</label>
		<input  id="gjjjs" name="gjjjs" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请输入公积金基数，单位【元】，允许两位小数" >
		<br/>
		<span>请输入公积金基数，单位【元】，允许两位小数</span>
	</div>
	<div class="form-item">
		<label for="sbjx" class="label-top" style="width:120px">当地最低社保基数：</label>
		<input  id="sbjs" name="sbjs" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请输入社保基数，单位【元】，允许两位小数" >
		<br/>
		<span>请输入社保基数，单位【元】，允许两位小数</span>
	</div>
	<div class="form-item">
		<label for="sbbl" class="label-top" style="width:120px">社保养老缴费比例 ：</label>
		<input id="sbblYanglao" name="sbblYanglao" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="社保养老缴费比例，数值范围0-100，允许两位小数" >
		<br/>
		<span>社保养老缴费比例，数值范围0-100，允许两位小数</span>
	</div>
	<div class="form-item">
		<label for="sbbl" class="label-top" style="width:120px">社保医疗缴费比例 ：</label>
		<input  name="sbblYiliao" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="社保医疗缴费比例，数值范围0-100，允许两位小数" >
		<br/>
		<span>社保医疗缴费比例，数值范围0-100，允许两位小数</span>
	</div>
	<div class="form-item">
		<label for="sbbl" class="label-top" style="width:120px">社保失业缴费比例 ：</label>
		<input  id="sbblShiye" name="sbblShiye" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="社保失业缴费比例 ，数值范围0-100，允许两位小数" >
		<br/>
		<span>社保失业缴费比例 ，数值范围0-100，允许两位小数</span>
	</div>
	<div class="form-item">
		<label for="sbbl" class="label-top" style="width:120px">公积金缴费比例 ：</label>
		<input  id="sbblGongjijin" name="sbblGongjijin" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="公积金缴费比例，数值范围0-100，允许两位小数" >
		<br/>
		<span>公积金缴费比例，数值范围0-100，允许两位小数</span>
	</div>
	<div class="form-item">
		<label for="averageAnnualAttendanceDays" class="label-top" style="width:120px">全年平均出勤天数 ：</label>
		<input  id="averageAnnualAttendanceDays" name="averageAnnualAttendanceDays" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请填写全年平均应出勤天数" >
	</div>
	<div class="form-item">
		<label for="taxStartPoint" class="label-top" style="width:120px">个税起征点 ：</label>
		<input  id="taxStartPoint" name="taxStartPoint" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请填写个税起征点" >
	</div>
	
</form>
<div style="clear: both;"></div>
<a href="javascript:saveSalaryAppSettingForm()" class="easyui-linkbutton primary">保存</a>
<script>
/***保存系统参数***/
salaryId = "${RequestParameters['salaryId']}";
companyId ="${RequestParameters['companyId']}";
$(document).ready(function(){
	$("#saveSalaryAppSettingForm").form("load","${domain}salary/load_salary_app_setting.action?salaryId="+salaryId);
})
function saveSalaryAppSettingForm(){
	hao.submitForm({
		form:$("#saveSalaryAppSettingForm"),
		data:{salaryId:salaryId},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.alert('提示',"操作成功！");
				$("#saveSalaryAppSettingForm").form("load","${domain}salary/load_salary_app_setting.action?salaryId="+salaryId);
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}
</script>

