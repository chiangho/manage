<@p.include/>

<style>
.panel.window.panel-htop{
	top:-10px !important;
}
</style>

<div style="text-align: left;margin-bottom: 10px">
	<a href="javascript:import_salary_item()" class="easyui-linkbutton primary">从工资库中选择</a>
	<a href="javascript:edit_new_salary_item()" class="easyui-linkbutton primary">添加新工资项</a>
</div>
<table id="salaryItemList"></table>
<div id="addSalaryItemWindow" style="display: none;text-align: center;width: 100%;padding:0px;">
    <div class="easyui-layout" data-options="fit:true" style="width: 100%;height:360px">
	    <div data-options="region:'south'" style="height:30px;overflow: hidden;text-align: center;">
	    	<a href="javascript:submit_salary_item()" class="easyui-linkbutton primary">保存</a>
			<a href="javascript:$('#addSalaryItemWindow').window('close')" class="easyui-linkbutton default">关闭</a>
	    </div>
	    <div data-options="region:'east'" style="width:70%;text-align: center;padding-top:10px;">
		    <form id="salaryItemForm" method="post" action="${domain}salary/save_salary_item.action">
				<input id="salary_item_id"         name="id"        type="hidden" >
				<div class="form-item">
					<label for="name" class="label-top">工资项：</label>
					<input id="name"  name="name" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入数据项" >
				</div>
				<div class="form-item">
					<label for="operation" class="label-top">性质：</label>
					<input id="operation"  name="operation" style="width:250px" />
				</div>
				<div class="form-item">
					<label for="yfSalary" class="label-top">是否应发：</label>
					<input id="yfSalary"  name="yfSalary" style="width:250px" />
				</div>
				<div class="form-item">
					<label for="changeSalaryDiffCalculation" class="label-top">调薪前后区别计算：</label>
					<input id="changeSalaryDiffCalculation"  name="changeSalaryDiffCalculation" style="width:250px" />
				</div>
				<div class="form-item">
					<label for="script" class="label-top">脚本：</label>
					<input id="script"  name="script" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入脚本" >
				</div>
				<div class="form-item">
					<label for="remark" class="label-top">备注：</label>
					<input id="remark"  name="remark" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入备注" >
				</div>
			</form>
	    </div>
	    <div id="viewBaseDataDiv" data-options="region:'west',title:'可用数据项',collapsible:false" style="width:30%;padding-top:10px;">
	    	
	    </div>
    </div>
</div>

<script>
salaryitem_salaryId = "${RequestParameters['salaryId']}";
salaryitem_companyId ="${RequestParameters['companyId']}";

$('#operation').combobox({
	valueField: 'value',
	textField: 'label',
	prompt:"请选择性质",
	data: [{
		label: '加款',
		value: '1'
	},{
		label: '扣款',
		value: '0'
	}]
});


$('#yfSalary').combobox({
	valueField: 'value',
	textField: 'label',
	prompt:"请选择",
	data: [{
		label: '是',
		value: '1'
	},{
		label: '否',
		value: '0'
	}]
});




$("#script").textbox({
	multiline:true,
	width:250,
	height:100,
	prompt:"请填写脚本！"
})

$('#changeSalaryDiffCalculation').combobox({
	valueField:'value',
    textField:'label',
    data: [{
		label: '是',
		value: '1'
	},{
		label: '否',
		value: '0'
	}]
});

function cleanSalaryItemForm(){
	$("#salaryItemForm").form("reset");
	$("#salary_item_id").val("");
}

function submit_salary_item(){
	hao.submitForm({
		form:$("#salaryItemForm"),
		data:{
			"salaryId":salaryitem_salaryId,
			"companyId":salaryitem_companyId
		},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$('#salaryItemList').datagrid("reload");
				$("#addSalaryItemWindow").window("close");
				$.messager.alert('提示',"操作成功");
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}


/***查询脚本***/
function viewScript(id){
	hao.ajax({
		url:"load_salary_item.action",
		data:{
			id:id
		},
		success:function(rtn){
			if(rtn){
				$.messager.alert('脚本', "<textarea style=\"width:270px;height:200px\">"+rtn.script+"</textarea>");
			}
		}
	})
}


function edit_new_salary_item(id){
	cleanSalaryItemForm();
	$("#viewBaseDataDiv").html("");
	$("#addSalaryItemWindow").window("open");
	//加载计算因子
	hao.ajax({
		url:"query_salary_computing_item.action",
		data:{
			salaryId:salaryitem_salaryId,
			companyId:salaryitem_companyId
		},
		success:function(rtn){
			html="";
			for(var i=0;i<rtn.length;i++){
				html+="<div>"+rtn[i]+"</div>";
			}
			$("#viewBaseDataDiv").html(html);
		}
	});
	if(id!=null&&id!=""){
		$("#salaryItemForm").form("load","load_salary_item.action?id="+id);
	}
}

function del_salary_item(id){
	$.messager.confirm('确认','确认删除此工资项码?',function(r){
	    if(r){
			hao.ajax({
				url:"del_salary_item.action",
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
						$("#salaryItemList").datagrid("reload");
					}else{
						$.messager.alert('错误', rtn.rtnMsg );
					}					
				}
			})
	    }
	});
}

$("#salaryItemForm").form();

$("#addSalaryItemWindow").window({
	title:"数据项编辑",
 	width: 600,
    height: 400,
    modal: true,
    minimizable:false,
    closed:true
})

$("#salaryItemList").datagrid({
	url:"load_salary_item_page.action",
	cache : false,
	rownumbers : true,
	pagination : true,
	queryParams:{
		salaryId:salaryitem_salaryId,
		companyId:salaryitem_companyId
	},
	columns: [[
		{field:"name",title:"工资项",width:100},
		{field:"operation",title:"性质",width:50,formatter:function(value,row,index){
			if(value=="0"){
				return "扣款";
			}else{
				return "加款";
			}
		}},
		{field:"remark",title:"备注",width:200},
		{field:"viewScript",title:"脚本",width:80,formatter:function(value,row,index){
			return "<a href=\"javascript:viewScript('"+row.id+"')\">查阅脚本</a>";
		}},
		{	
			field:"id",
			title:"操作",
			width:100,
			formatter:function(value,row,index){
				var html = "";
				html += "<a href=\"javascript:del_salary_item('"+value+"')\">删除</a>&nbsp;|&nbsp";
				html += "<a href=\"javascript:edit_new_salary_item('"+value+"')\">修改</a>&nbsp;";
				return html;
			}
		}
	]]
})
</script>