<@p.header title="公司管理——数据定义"/>

<div class="app-header">
公司管理>数据定义
</div>
<div class="app-content">
	<table id="list"></table>
</div>


<div id="addCompanyItemWindow" style="display: none;text-align: center;width: 100%;padding:0px;">
    <div class="easyui-layout" data-options="fit:true" style="width: 100%;height:360px">
	    <div data-options="region:'south'" style="height:30px;overflow: hidden;text-align: center;">
	    	<a href="javascript:addNew()" class="easyui-linkbutton primary">保存</a>
			<a href="javascript:resetAddForm()" class="easyui-linkbutton default">重置</a>
	    </div>
	    <div data-options="region:'east'" style="width:70%;text-align: center;padding-top:10px;">
		    <form id="addForm" method="post" action="${domain}company/data/save.action">
				<input id="id"  name="id" type="hidden" >
				<div class="form-item">
					<label for="companyId" class="label-top">公司：</label>
					<input id="companyId"  name="companyId">
				</div>
				<div class="form-item">
					<label for="name" class="label-top">数据项：</label>
					<input id="name"  name="name" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入数据项" >
				</div>
				<div class="form-item">
					<label for="remark" class="label-top">备注：</label>
					<input id="remark"  name="remark" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入备注" >
				</div>
				<div class="form-item">
					<label for="script" class="label-top">脚本：</label>
					<input id="script"  name="script" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入脚本" >
				</div>
			</form>
	    </div>
	    <div id="viewBaseDataDiv" data-options="region:'west',title:'可用数据项',collapsible:false" style="width:30%;padding-top:10px;">
	    	
	    </div>
    </div>
</div>
<div id="buttonArea">
	<a href="javascript:openaddCompanyItemWindow()" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'">添加数据项目</a>
</div>

<script>
function openaddCompanyItemWindow(){
	$("#addCompanyItemWindow").window("open");
}
function closeCompanyItemWindow(){
	$("#addCompanyItemWindow").window("close");
}
function resetAddForm(){
	$("#addForm").form("reset")
}

function loadBaseData(companyId){
	if(companyId==null||companyId==undefined||companyId==""){
		return;	
	}
	hao.ajax({
		url:"${domain}company/data/load_sun_dataitem.action",
		dataType:"text",
		data:{
			companyId:companyId,
			selfId:$("#id").val()
		},
		success:function(rtn){
			$("#viewBaseDataDiv").html(rtn);
		}
	})
	
}

function del(id){
	$.messager.confirm("提供","是否删除此数据项目",function(r){
		if(r){
			hao.ajax({
				url:"${domain}company/data/del.action",
				data:{
					id:id
				},
				success:function(rtn){
					if(rtn.rtnCode=="000000"){
						$('#list').datagrid("reload");
						$.messager.alert('提示',"操作成功！");
					}else{
						$.messager.alert('错误', rtn.rtnMsg );
					}
				}
			})		
		}
	})
}

/**
 * 编辑
 */
function openEdit(id,companyId){
	openaddCompanyItemWindow();
	$("#addForm").form("load","${domain}company/data/load_company_data.action?id="+id);
}
/**
 * 保存
 */
function addNew(){
	hao.submitForm({
		form:$("#addForm"),
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$('#list').datagrid("reload");
				$.messager.confirm('提示', '操作成功！是否继续添加?', function(r){
					if (r){
						$("#id").val("");
						$("#addForm").form("reset")
					}else{
						closeCompanyItemWindow();
					}
				});
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}

function viewScript(id){
	hao.ajax({
		url:"${domain}company/data/load_company_data.action",
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


$(document).ready(function(){
	$("#addForm").form()
	$("#companyId").combobox({
	    url:'${domain}company/load_company.action',
	    valueField:'id',
	    textField:'name',
	    width:250,
	    prompt:"请选择公司！",
	    onSelect:function(row){
	    	loadBaseData(row.id);
	    }
	});
	
	$("#script").textbox({
		multiline:true,
		width:250,
		height:100,
		prompt:"请填写脚本！"
	})
	
	$("#addCompanyItemWindow").window({
		title:"数据项编辑",
	 	width: 600,
	    height: 400,
	    modal: true,
	    minimizable:false,
	    closed:true,
	    onBeforeClose:function(){
	    	$("#id").val("");
	    	$("#addForm").form("reset");
	    },
	    onBeforeLoad:function(){
	    	$("#id").val("");
	    	$("#addForm").form("reset");
	    }
	});


	$("#list").datagrid({
		url: '${domain}company/data/page_list.action',
		cache : false,
		rownumbers : true,
		pagination : true,
		toolbar: $("#buttonArea"),
		columns:[[
			{field:"company",title:"公司",width:200,formatter:function(value,row,index){return value.name}},
			{field:"name",title:"数据项",width:200},
			{field:"remark",title:"备注",width:200},
			{field:"viewScript",title:"脚本",width:100,formatter:function(value,row,index){
				return "<a href=\"javascript:viewScript('"+row.id+"')\">查阅脚本</a>";
			}},
			{field:"action",title:"操作",width:100,formatter:function(value,row,index){
				return "<a href=\"javascript:del('"+row.id+"')\">删除</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:openEdit('"+row.id+"','"+row.companyId+"')\">编辑</a>";
			}}
		]]
	})
	
})
</script>
<@p.footer/>