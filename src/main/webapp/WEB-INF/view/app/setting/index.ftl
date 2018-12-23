<@p.header title="系统参数设置"/>
<div class="app-header">
系统参数设置
</div>
<div class="app-content">
    <form id="addForm" method="post" action="${domain}app/setting/save.action">
		<div class="form-item">
			<label for="sbjx" class="label-top" style="width:120px">当地最低公积金基数：</label>
			<input id="gjjjs" name="gjjjs" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请输入公积金基数，单位【元】，允许两位小数" >
			<span>请输入公积金基数，单位【元】，允许两位小数</span>
		</div>
		<div class="form-item">
			<label for="sbjx" class="label-top" style="width:120px">当地最低社保基数：</label>
			<input id="sbjs" name="sbjs" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请输入社保基数，单位【元】，允许两位小数" >
			<span>请输入社保基数，单位【元】，允许两位小数</span>
		</div>
		<div class="form-item">
			<label for="sbbl" class="label-top" style="width:120px">社保养老缴费比例 ：</label>
			<input  id="sbblYanglao" name="sbblYanglao" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="社保养老缴费比例，数值范围0-100，允许两位小数" >
			<span>社保养老缴费比例，数值范围0-100，允许两位小数</span>
		</div>
		<div class="form-item">
			<label for="sbbl" class="label-top" style="width:120px">社保医疗缴费比例 ：</label>
			<input id="sbblYiliao" name="sbblYiliao" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="社保医疗缴费比例，数值范围0-100，允许两位小数" >
			<span>社保医疗缴费比例，数值范围0-100，允许两位小数</span>
		</div>
		<div class="form-item">
			<label for="sbbl" class="label-top" style="width:120px">社保失业缴费比例 ：</label>
			<input  id="sbblShiye" name="sbblShiye" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="社保失业缴费比例 ，数值范围0-100，允许两位小数" >
			<span>社保失业缴费比例 ，数值范围0-100，允许两位小数</span>
		</div>
		<div class="form-item">
			<label for="sbbl" class="label-top" style="width:120px">公积金缴费比例 ：</label>
			<input  id="sbblGongjijin" name="sbblGongjijin" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="公积金缴费比例，数值范围0-100，允许两位小数" >
			<span>公积金缴费比例，数值范围0-100，允许两位小数</span>
		</div>
		<div class="form-item">
			<label for="sbbl" class="label-top" style="width:120px">全年平均出勤天数 ：</label>
			<input id="averageAnnualAttendanceDays" name="averageAnnualAttendanceDays" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请填写全年平均应出勤天数" >
		</div>
		<div class="form-item">
			<label for="sbbl" class="label-top" style="width:120px">个税起征点 ：</label>
			<input  id="taxStartPoint" name="taxStartPoint" class="easyui-validatebox easyui-textbox" style="width:300px" prompt="请填写个税起征点" >
		</div>
	</form>
	<div style="clear: both;"></div>
	<a href="javascript:save()" class="easyui-linkbutton primary">保存</a>
</div>
<script>
/***保存系统参数***/
$(document).ready(function(){
	$("#addForm").form("load","${domain}app/setting/load.action");
})
function save(){

	hao.submitForm({
		form:$("#addForm"),
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.alert('提示',"操作成功！");
				$("#addForm").form("load","${domain}app/setting/load.action");
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}
</script>
<@p.footer/>