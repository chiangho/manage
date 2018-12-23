<@p.include/>
<style>
.panel.window.panel-htop{
	top:-10px !important;
}
</style>
<div style="text-align: left;margin-bottom: 10px">
	<a href="javascript:import_attendance()" class="easyui-linkbutton primary">从公司全局考勤导入项覆盖</a>
	<a href="javascript:add_new_attendance()" class="easyui-linkbutton primary">添加</a>
</div>
<table id="attendanceList"></table>
<div id="window_attendance" style="display: none;text-align: center;width: 100%;padding-top: 20px;">
	<form id="attendance_form" method="post" action="save_attendance.action">
		<input id="attendance_id"  name="id" type="hidden" >
		<input id="attendance_company_id"  name="companyId" type="hidden" >
		<input id="attendance_salary_id"  name="salaryId" type="hidden" >
		<div class="form-item">
			<label for="name" class="label-top" style="width:100px">考勤导入项：</label>
			<input id="attendance_name"  name="name" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="考勤导入项" >
		</div>
	</form>
	<div style="clear: both;"></div>
	<a href="javascript:submitAttendance()" class="easyui-linkbutton primary">保存</a>
	<a href="javascript:windowAttendanceCommand('close')" class="easyui-linkbutton default">关闭</a>
</div>
<script>
attendance_salaryId = "${RequestParameters['salaryId']}";
attendance_companyId ="${RequestParameters['companyId']}";
$("#window_attendance").window({
	title:"编辑考勤导入项",
 	width: 400,
    height: 200,
    modal: true,
    shadow:true,
    minimizable:false,
    closed:true,
    onClose:function(){
    	$("#attendance_form").form("reset");
    	$("#attendance_id").val("");
    	$("#attendance_company_id").val("");
    	$("#attendance_salary_id").val("");
    }
});
/**窗口命令**/
function windowAttendanceCommand(command){
	$("#window_attendance").window(command);
}
/**加载**/
function windowAttendanceFormLoad(id){
	$("#attendance_form").form("load","load_attendance.action?id="+id);
}

function import_attendance(){
	hao.ajax({
		url:"import_attendance.action",
		data:{
			salaryId:attendance_salaryId
		},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.show({
					title:'提示',
					msg:'操作成功',
					timeout:1000,
					showType:'show'
				});
				$("#attendanceList").datagrid("reload");
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}					
		}
	})
}

function submitAttendance(){
	hao.submitForm({
		form:$("#attendance_form"),
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.show({
					title:'提示',
					msg:'操作成功',
					timeout:1000,
					showType:'show'
				});
				$('#attendanceList').datagrid("reload");
				windowAttendanceCommand('close');
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}


function del_attendance(id){
	$.messager.confirm('确认','确认删除此导入项目吗?',function(r){
	    if(r){
			hao.ajax({
				url:"del_attendance.action",
				data:{
					id:id
				},
				success:function(rtn){
					if(rtn.rtnCode=="000000"){
						$.messager.show({
							title:'提示',
							msg:'操作成功',
							timeout:1000,
							showType:'show'
						});
						$("#attendanceList").datagrid("reload");
					}else{
						$.messager.alert('错误', rtn.rtnMsg );
					}					
				}
			})
	    }
	});
}
function edit_attendance(id){
	windowAttendanceCommand('open');
	windowAttendanceFormLoad(id);
}

function add_new_attendance(){
	$("#attendance_company_id").val(attendance_companyId);
	$("#attendance_salary_id").val(attendance_salaryId);
	windowAttendanceCommand('open');
}

$("#attendanceList").datagrid({
	url:"load_attendance_list.action",
	cache : false,
	rownumbers : true,
	pagination : true,
	queryParams:{
		salaryId:attendance_salaryId
	},
	columns: [[
		{field: 'name',title: '导入项',width:100}, 
		{field: 'companyName',title: '所属公司',width:200,formatter:function(value,row,index){
			return row.company.name;
		}}, 
		{	
			field:"id",
			title:"操作",
			width:100,
			formatter:function(value,row,index){
				var html = "";
				html += "<a href=\"javascript:del_attendance('"+value+"')\">删除</a>&nbsp;|&nbsp";
				html += "<a href=\"javascript:edit_attendance('"+value+"')\">修改</a>&nbsp;";
				return html;
			}
		}
	]]
})
</script>