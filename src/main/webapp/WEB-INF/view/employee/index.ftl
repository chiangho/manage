<@p.header title="人员管理"/>
<div class="app-header">
人员管理>人员信息
</div>
<div class="app-content">
	<table id="list"></table>
</div>


<div id="addWindow" style="display: none;text-align: center;width: 100%;padding-top: 20px;">
	<div class="form-item">
		<label for="name" class="label-top">导入文件：</label>
		<input id="file"  name="file" class="easyui-validatebox easyui-filebox" style="width:250px" prompt="请输入公司名称" >
	</div>
	<div style="clear: both;"></div>
	<a href="javascript:addNew()" class="easyui-linkbutton primary">提交</a>
	<a href="javascript:closeAddWindow()" class="easyui-linkbutton default">关闭</a>
</div>

<div id="changeSalaryWindow" style="display: none;text-align: center;width: 100%;padding-top: 20px;">
	<input type="hidden" id="employerId" value="" />
	<div class="form-item">
		<label for="salary" class="label-top" style="width:150px">工资：</label>
		<input id="salary"  name="salary" class="easyui-textbox" prompt="请输入工资" style="width:250px;">
	</div>
	<div class="form-item">
		<label for="changeData" class="label-top"  style="width:150px">调薪日期：</label>
		<input id="changeData"  name="changeData" class="easyui-datebox" style="width:250px">
	</div>
	<div class="form-item">
		<label for="salaryType" class="label-top"  style="width:150px">当月工资计算方式：</label>
		<input id="salaryType"  name="salaryType" />
	</div>
	<div style="clear: both;"></div>
	<a href="javascript:subChangeSalary()" class="easyui-linkbutton primary">提交</a>
	<a href="javascript:$('#changeSalaryWindow').window('close')" class="easyui-linkbutton default">关闭</a>
</div>


<div id="setQuitWindow" style="display: none;text-align: center;width: 100%;padding-top: 20px;">
	<input type="hidden" id="employerId" value="" />
	<div class="form-item">
		<label for="leaveDate" class="label-top"  style="width:150px">离职日期：</label>
		<input id="leaveDate"  name="leaveDate" class="easyui-datebox" style="width:250px">
	</div>
	<div class="form-item">
		<label for="realyLeaveDate" class="label-top"  style="width:150px">办理离职日期：</label>
		<input id="realyLeaveDate"  name="realyLeaveDate" class="easyui-datebox" style="width:250px">
	</div>
	<div style="clear: both;"></div>
	<a href="javascript:submitLeave()" class="easyui-linkbutton primary">提交</a>
	<a href="javascript:$('setQuitWindow').window('close')" class="easyui-linkbutton default">关闭</a>
</div>


<div id="buttonArea">
	<div style="margin: 5px 0px">
		姓名/工号/身份证：<input type="text" id="searchKey">
	</div>
	<a href="javascript:search()" class="easyui-linkbutton primary" >查询</a>
	<a href="javascript:openAddWindow()" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'">导入人员</a>
</div>


<script type="text/javascript">
//查询
function search(){
	$('#list').datagrid({
		queryParams:{
			url: 'page_list.action',
			searchKey:$("#searchKey").val()
		}
	});
}

function setQuit(id){
	alert(id);
	hao.ajax({
		url:"load_leave_info.action",
		data:{
			employerId:id
		},
		success:function(rtn){
			//
			$("#setQuitWindow").window("open");
		}
	})
}

function submitLeave(){
	
}

//打开添加页面
function openAddWindow(){
	$("#addWindow").window("open");
}
function closeAddWindow(){
	$("#addWindow").window("close");
}


function del(id){
	hao.ajax({
		url:"destroy.action",
		data:{
			id:id
		},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$('#list').datagrid("reload");
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}

function addNew(){
	hao.ajaxUpload({
		fileId:"filebox_file_id_4",
		url:"${domain}employee/import_employee.action",
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				if(rtn.error){
					$('#file').filebox("clear");
					$.messager.alert('异常数据', rtn.error);
				}else{
					$.messager.alert('提示',"操作成功");
					closeAddWindow();
					$('#list').datagrid("reload");
				}
			}else{
				$('#file').filebox("clear");
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	});
}

////提交调薪请求
function subChangeSalary(){
	hao.ajax({
		url:"${domain}employee/sub_change_salary.action",
		data:{
			employeeId:$("#employerId").val(),
			date:$("#changeData").textbox("getValue"),
			salary:$("#salary").textbox("getValue"),
			salaryType:$("#salaryType").combobox("getValue"),
		},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$('#list').datagrid("reload");
				$('#changeSalaryWindow').window('close');
				$.messager.alert('提示', "操作成功！" );
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}

//调整工资
function changeSalary(id){
	$("#employerId").val(id);
	var curr_time = new Date();
    var strDate = curr_time.getFullYear()+"-";
    strDate += curr_time.getMonth()+1+"-";
    strDate += curr_time.getDate()+"-";
    strDate += curr_time.getHours()+":";
    strDate += curr_time.getMinutes()+":";
    strDate += curr_time.getSeconds();
	$("#changeData").datebox("setValue",strDate);
	$("#salaryType").combobox("clear");
	$("#salary").textbox("setValue","");
	$("#changeSalaryWindow").window("open");
}


//页面初始化
$(document).ready(function(){
	
	$('#file').filebox({
	    buttonText: '选择文件',
	})
	$("#addWindow").window({
		title:"导入人员信息",
	 	width: 400,
        height: 200,
        modal: true,
        minimizable:false,
        closed:true,
        onBeforeClose:function(){
        	$("#addForm").form("reset");
        },
        onBeforeLoad:function(){
        	$("#addForm").form("reset");
        }
	});
	
	$("#setQuitWindow").window({
		title:"办理离职",
	 	width: 400,
        height: 200,
        modal: true,
        minimizable:false,
        closed:true
	});
	
	$("#salaryType").combobox({
	    valueField:'id',
	    textField:'text',
	    width:250,
	    data:[
	    	{id:"1",text:"按最新工资计算"},
	    	{id:"2",text:"调整前和调整后各自计算"}
	    ]
	});

	$("#changeData").datebox({
		 required:true
	});

	$("#changeSalaryWindow").window({
		title:"变更员工工资",
	 	width: 600,
	    height: 400,
	    modal: true,
	    minimizable:false,
	    closed:true
	})
	
	$('#list').datagrid({
		url: 'page_list.action',
		cache : false,
    	rownumbers : true,
    	pagination : true,
		toolbar: $("#buttonArea"),
		queryParams:{
			searchKey:$("#searchKey").val()
		},
		columns: [[
				{field: 'idCard',title:'身份证',width:160}, 
				{field: 'employerName',title:'雇佣公司',width:100}, 
				{field: 'sex',title:'性别',width:40,formatter:function(value,row,index){
					return row.employee.sexName;
				}},	
				{field: 'phone1',title:'电话1',width:90,formatter:function(value,row,index){
					return row.employee.phone1;
				}}, 
				{field: 'phone2',title:'电话2',width:90,formatter:function(value,row,index){
					return row.employee.phone2;
				}}, 
				{field: 'contractNo',title:"合同编号",width:100}, 
				{field: 'baseSalary',title:'基本工资',width:100}, 
				{field: "employmentTypeName",title:"合同形式"},
				{field: 'state',title:'状态',width:40,formatter:function(value,row,index){
					if(value==0){
						return "在职";
					}else if(value==-1){
						return "停职";
					}else if(value==-2){
						return "离职";
					}else{
						return "--";						
					}
				}},
				{field:"socialSecurityType",title:"社保类型",formatter:function(value,row,index){
					if(value==0){
						return "不发";
					}else if(value==1){
						return "当地最低标准";
					}else if(value==2){
						return "实际工资";
					}
				}},
				{field:"socialSecurityType",title:"公积金类型",formatter:function(value,row,index){
					if(value==0){
						return "不发";
					}else if(value==1){
						return "当地最低标准";
					}else if(value==2){
						return "实际工资";
					}
				}},
				{field:"socialSecurityAlgorithm",title:"社保缴发",formatter:function(value,row,index){
					if(value==0){
						return "缴费到社保";
					}else if(value==1){
						return "暂扣，离职时补发";
					}
				}},
				{field:"baseProject",title:"大项目",formatter:function(value,row,index){
					return row.baseProject.name
				}},
				{field:"project",title:"项目",formatter:function(value,row,index){
					return row.project.name
				}},
				{field:"line",title:"产线",formatter:function(value,row,index){
					return row.line.name
				}},
				{field:"department",title:"部门",formatter:function(value,row,index){
					return row.department.name
				}},
				{field:"point",title:"职务",formatter:function(value,row,index){
					return row.point.name
				}},
				{field:'bankCard',title:'银行卡',width:100}, 
				{field:'beginTime',title:'入职日期',width:100,formatter:function(value,row,index){
					return stampToDate(value)
				}}, 
				{field:'leaveDate',title:'离职日期',width:100,formatter:function(value,row,index){
					return stampToDate(value)
				}}, 
				{field:'realyLeaveDate',title:'办理离职日期',width:100,formatter:function(value,row,index){
					return stampToDate(value)
				}}, 
				{field:'realyLeaveDate',title:'离职类型',width:100,formatter:function(value,row,index){
					if(value==0){
						return "自离";
					}
					if(value==1){
						return "辞职";
					}
					if(value==2){
						return "开除";
					}
				}},
		]],
		frozenColumns:[[
			{field: 'name',title:'姓名',width:100,formatter:function(value,row,index){
				return row.employee.name;
			}}, 
			{field: 'gs',title:'外派公司',width:100,formatter:function(value,row,index){
				if(row.company){
					return row.company.name;
				}
			}}, 
			{field: 'workerNo',title:'工号',width:100}, 
			{
				field:"id",
				title:"操作",
				width:200,
				formatter(value,row,index){
					html = "<a href=\"javascript:detail('"+row.employee.id+"')\">详情</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:changeSalary('"+row.employee.id+"')\">调薪</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:setQuit('"+row.employee.id+"')\">办理离职</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:del('"+row.employee.id+"')\">删除</a>&nbsp;";
					return html;
				}
			}
		]]
	});
})
</script>
<@p.footer/>