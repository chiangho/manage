<@p.header title="工资发放"/>

<style>
.panel-tool{
	top:18px !important;
}
</style>


<div class="app-header">
工资发放
</div>
<div class="app-content">
	<div id="buttonArea">
		<a href="javascript:editSalary()" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'">创建工资</a>
	</div>
	<table id="list"></table>
</div>

<div id="editSalaryWindow" style="display: none;text-align: center;width: 100%;padding-top: 20px;">
	<form id="addForm" method="post" action="save.action">
		<input id="id"  name="id" type="hidden" >
		<div class="form-item">
			<label for="companyId" class="label-top" style="width:100px">公司名称：</label>
			<input id="companyId"  name="companyId" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请选择公司" >
		</div>
		<div class="form-item">
			<label for="year" class="label-top" style="width:100px">年：</label>
			<input id="year"  name="year" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入结算年" >
		</div>
		<div class="form-item">
			<label for="month" class="label-top" style="width:100px">月：</label>
			<input id="month"  name="month" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入结算月" >
		</div>
		<div class="form-item">
			<label for="beginDate" class="label-top" style="width:100px">工资开始日期：</label>
			<input id="beginDate"  name="beginDate" class="easyui-validatebox easyui-datebox" style="width:250px" prompt="结算开始日期" >
		</div>
		<div class="form-item">
			<label for="endDate" class="label-top" style="width:100px">工资结束日期：</label>
			<input id="endDate"   name="endDate" class="easyui-validatebox easyui-datebox" style="width:250px" prompt="结算结束日期" >
		</div>
	</form>
	<div style="clear: both;"></div>
	<a href="javascript:submitSalary()" class="easyui-linkbutton primary">保存</a>
	<a href="javascript:openOrCloseSalayEdit('close')" class="easyui-linkbutton default">关闭</a>
</div>
<div style="clear: both;"></div>
<div id="attribute_window"  style="display: none;text-align: center;width: 100%;"></div>
<div id="importAttendanceWindow" style="text-align:center">
	<p style="width:100%;text-align: left">导入说明：</p>
	<p style="width:100%;text-align: left">1、表格第一行为列头，第一列固定为“工号”</p>
	<p style="width:100%;text-align: left">2、表格其余列的位置可变动，但列头的名称要和设置的考勤导入项名称一致，否则导入不进去</p>
	<input type="file" name="importAttendanceFile" id="importAttendanceFile"/>
	<a href="javascript:importAttendanceFunction()" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'">导入</a>
</div>

<div id="importAllowanceWindow" style="text-align:center">
	<p style="width:100%;text-align: left">导入说明：</p>
	<p style="width:100%;text-align: left">1、一个工作簿是一个津贴，工作簿的名称和系统定义的津贴名称一致。</p>
	<p style="width:100%;text-align: left">2、每个津贴的第一列为工号。名称固定（“工号”），位置固定（第一列），第二列为津贴的金额</p>
	<input type="file" name="importAllowanceFile" id="importAllowanceFile"/>
	<a href="javascript:importAllowanceFunction()" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'">导入</a>
</div>
<script>

//管理个税系统
function manageSb(id,companyId){
	removeAndCreateNewIframe();
	$("#attribute_window").window({title:"编辑社保比例",closed:false,width:600});
	$("#attribute_window").find("iframe").attr("src","${domain}salary/app_setting.action?salaryId="+id+"&companyId="+companyId);//.window("refresh",);
}

function manageGs(id,companyId){
	removeAndCreateNewIframe();
	$("#attribute_window").window({title:"编辑个税比例",closed:false,width:600});
	$("#attribute_window").find("iframe").attr("src","${domain}salary/income_tax.action?salaryId="+id+"&companyId="+companyId);//.window("refresh",);
}

/**代开或者关闭窗口**/
function openOrCloseSalayEdit(action){
	$("#editSalaryWindow").window(action);
}
/**清空表单数据**/
function cleanSalaryEdit(){
	$("#addForm").form("reset");
	$("#id").val("");
}


/**创建编辑工资**/
function editSalary(id){
	cleanSalaryEdit();
	openOrCloseSalayEdit("open");
	if(id!=null&&id!=""){
		//加载数据
		$("#addForm").form("load","load_salary.action?id="+id);
	}
}

function del(id){
	$.messager.confirm('确认','确认删除本批次的工资吗?',function(r){
	    if(r){
			hao.ajax({
				url:"del.action",
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
						$("#list").datagrid("reload");
					}else{
						$.messager.alert('错误', rtn.rtnMsg );
					}					
				}
			})
	    }
	});
}


function removeAndCreateNewIframe(){
	$("#attribute_window").find("iframe").remove();
	$("#attribute_window").append("<iframe src=\"\" style=\"width:100%;height: 98%;overflow: hidden;border:0px;\" scrolling=\"no\"></iframe>")
}

function editAttendance(id,companyId){
	removeAndCreateNewIframe();
	$("#attribute_window").window({title:"编辑考勤导入项",closed:false,width:450});
	$("#attribute_window").find("iframe").attr("src","${domain}salary/attendance.action?salaryId="+id+"&companyId="+companyId);//.window("refresh",);
}

function editSalaryItem(id,companyId){
	removeAndCreateNewIframe();
	$("#attribute_window").window({title:"编辑工资项",closed:false,width:800});
	$("#attribute_window").find("iframe").attr("src","${domain}salary/salary_item.action?salaryId="+id+"&companyId="+companyId);
	//$("#attribute_window").window("refresh","${domain}salary/salary_item.action?salaryId="+id+"&companyId="+companyId);
}

function editAllowance(id,companyId){
	removeAndCreateNewIframe();
	$("#attribute_window").window({title:"编辑津贴项",closed:false,width:800});
	$("#attribute_window").find("iframe").attr("src","${domain}salary/allowance_item.action?salaryId="+id+"&companyId="+companyId);
}


function submitSalary(){
	hao.submitForm({
		form:$("#addForm"),
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$('#list').datagrid("reload");
				$.messager.confirm('提示', '是否前去设置工资项', function(r){
					if (r){
						alert("1");
					}else{
						openOrCloseSalayEdit("close");
					}
					$("#list").datagrid("reload");
				});
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}

/**
 * 将系统社保参数覆盖至当前工资
 */
function coverSb(salaryId){
	hao.ajax({
		url:"${domain}salary/cover_app_setting.action",
		data:{salaryId:salaryId},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.alert('提示',"操作成功！");
			}else{
				$.messager.alert('错误',rtn.rtnMsg);
			}
		}
	})
}
/***
 * 将系统个税比例覆盖至当前工资
 */
function coverGs(salaryId){
	hao.ajax({
		url:"${domain}salary/cover_app_income_tax.action",
		data:{salaryId:salaryId},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.alert('提示',"操作成功！");
			}else{
				$.messager.alert('错误',rtn.rtnMsg);
			}
		}
	})
}


var importSalaryId = null;
var importCompanyId = null;

$("#importAttendanceWindow").window({
   title:"导入考勤数据",
   height:200,
   width:400,
   closed:true,
   modal: true,
   minimizable:false,
   maximizable:true
})

$("#importAllowanceWindow").window({
   title:"导入津贴数据",
   height:200,
   width:400,
   closed:true,
   modal: true,
   minimizable:false,
   maximizable:true
})

/***
 * 导入津贴
 */
function importAllowance(id,companyId){
	importSalaryId = id;
	importCompanyId = companyId;
	$("#importAllowanceWindow").window("open");
}


function importAttendance(id,companyId){
	importSalaryId = id;
	importCompanyId = companyId;
	$("#importAttendanceWindow").window("open");
}

function importAttendanceFunction(){
	hao.ajaxUpload({
		fileId:"importAttendanceFile",	
		url:"${domain}salary/import_employee_attendance.action",
		data:{
			salaryId:importSalaryId,
			companyId:importCompanyId
		},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.alert('提示',"操作成功" );
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}

function importAllowanceFunction(){
	hao.ajaxUpload({
		fileId:"importAllowanceFile",	
		url:"${domain}salary/import_employee_allowance.action",
		data:{
			salaryId:importSalaryId,
			companyId:importCompanyId
		},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.alert('提示',"操作成功" );
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}

/*****
 * 计算工资
 */
function calculationSalary(salary,companyId){
	hao.ajax({
		url:"${domain}salary/calculation_salary.action",
		data:{salaryId:salaryId,companyId:companyId},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$.messager.alert('提示',"操作成功！");
			}else{
				$.messager.alert('错误',rtn.rtnMsg);
			}
		}
	})
}

$(document).ready(function(){
	var thisYear=new Date().getUTCFullYear();//今年
	var startYear = 2006;
	var yearArray=new Array();
	for(var i=0;i<=(thisYear-startYear);i++){
		var yearObject = new Object();
		yearObject["id"]=(thisYear-i)+"";
		yearObject["text"]=(thisYear-i)+"年";
		yearArray[i] = yearObject;
	}
	
	$("#year").combobox({
	 	valueField: 'id',
        textField: 'text',
        data:yearArray
	});
	$("#month").combobox({
		valueField: 'id',
        textField: 'text',
        data:[
        	{"id":1,"text":"一月"},
        	{"id":2,"text":"二月"},
        	{"id":3,"text":"三月"},
        	{"id":4,"text":"四月"},
        	{"id":5,"text":"五月"},
        	{"id":6,"text":"六月"},
        	{"id":7,"text":"七月"},
        	{"id":8,"text":"八月"},
        	{"id":9,"text":"九月"},
        	{"id":10,"text":"十月"},
        	{"id":11,"text":"十一月"},
        	{"id":12,"text":"十二月"},
        ]
	});

	$('#beginDate').datebox({
	    currentText:"今天",
	    closeText:"关闭",
	    okText:"确认"
	});
	$('#endDate').datebox({
	    currentText:"今天",
	    closeText:"关闭",
	    okText:"确认"
	});

	//加载公司数据
	$("#companyId").combobox({
	    url:'${domain}company/load_company.action',
	    valueField:'id',
	    textField:'name',
	    width:250,
	});

	$("#editSalaryWindow").window({
		title:"编辑工资",
	 	width: 400,
        height: 400,
        modal: true,
        shadow:true,
        minimizable:false,
        closed:true
	});

	$("#attribute_window").window({
	 	width:450,
        height:500,
        modal: true,
        minimizable:false,
        maximizable:true,
        shadow:true,
        closed:true,
        top:100,
        onClose:function(){
			$("#attribute_window").html("");
		}
	})
	
	
	
	$("#list").datagrid({
		url: 'load_list.action',
		cache : false,
    	rownumbers : true,
    	pagination : true,
		toolbar: $("#buttonArea"),
		columns: [[
			{field: 'companyName',title: '公司',width:100,formatter:function(value,row,index){
				return row.company.name;
			}}, 
			{field: 'year',title: '年',width:80}, 
			{field: 'month',title: '月',width:80}, 
			{field: 'beginDate',title: '工资开始日期',width:100,formatter:function(value,row,index){
				return stampToDate(value);
			}}, 
			{field: 'endDate',title: '工资开始日期',width:100,formatter:function(value,row,index){
				return stampToDate(value);
			}}, 
			{field:"sb",title:"基本参数",width:200,formatter:function(value,row,index){
				return "<a href=\"javascript:manageSb('"+row.id+"','"+row.companyId+"')\">基本参数<a>&nbsp;|&nbsp;<a href=\"javascript:coverSb('"+row.id+"')\">以系统基本参数为准</a>";
			}},
			{field:"gs",title:"个人比例",width:200,formatter:function(value,row,index){
				return "<a href=\"javascript:manageGs('"+row.id+"','"+row.companyId+"')\">个税管理<a>&nbsp;|&nbsp;<a href=\"javascript:coverGs('"+row.id+"')\">以系统个税比例为准</a>";
			}},
			{field: 'manageSalary',title: '工资数据预览',width:100,formatter:function(value,row,index){
				return "<a href=\"view_salary_data.action?salaryId='"+row.id+"'\" target='_blank'>查询工资数据<a>";
			}}, 
			{	
				field:"id",
				title:"操作",
				width:500,
			   formatter:function(value,row,index){
					var html = "";
					html += "<a href=\"javascript:editAttendance('"+value+"','"+row.companyId+"')\">考勤导入项</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:editSalaryItem('"+value+"','"+row.companyId+"')\">设置工资项</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:editAllowance('"+value+"','"+row.companyId+"')\">设置津贴</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:importAttendance('"+value+"','"+row.companyId+"')\">导入考勤</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:importAllowance('"+value+"','"+row.companyId+"')\">导入津贴</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:calculationSalary('"+value+"','"+row.companyId+"')\">计算工资</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:del('"+value+"')\">删除</a>&nbsp;|&nbsp";
					html += "<a href=\"javascript:editSalary('"+value+"')\">修改</a>&nbsp;";
					return html;
				}
			}
		]]
	})
})
</script>
<@p.footer/>