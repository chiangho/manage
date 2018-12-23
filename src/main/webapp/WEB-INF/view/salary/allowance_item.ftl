<@p.include/>
<style>
.panel.window.panel-htop{
	top:10px !important;
}
</style>

<div style="text-align: left;margin-bottom: 10px">
	<a href="javascript:import_allowance_item()" class="easyui-linkbutton primary">从津贴库中选择</a>
	<a href="javascript:edit_new_allowance_item()" class="easyui-linkbutton primary">添加津贴</a>
</div>
<table id="allowanceItemList"></table>
<div id="addAllowanceItemWindow" style="display: none;text-align: center;width: 100%;padding:0px;">
    <div class="easyui-layout" data-options="fit:true" style="width: 100%;height:360px">
	    <div data-options="region:'south'" style="height:30px;overflow: hidden;text-align: center;">
	    	<a href="javascript:submit_allowance_item()" class="easyui-linkbutton primary">保存</a>
			<a href="javascript:$('#addAllowanceItemWindow').window('close')" class="easyui-linkbutton default">关闭</a>
	    </div>
	    <div data-options="region:'east'" style="width:70%;text-align: center;padding-top:10px;">
		    <form id="allowanceItemForm" method="post" action="${domain}salary/save_allowance_item.action">
				<input id="allowance_item_id"         name="id"        type="hidden" >
				<div class="form-item">
					<label for="name" class="label-top">津贴：</label>
					<input id="name"  name="name" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入数据项" >
				</div>
				
				<div class="form-item">
					<label for="script" class="label-top">脚本：</label>
					<input id="summayScript"  name="summayScript" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入脚本" >
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
salaryId = "${RequestParameters['salaryId']}";
companyId ="${RequestParameters['companyId']}";


$("#summayScript").textbox({
	multiline:true,
	width:250,
	height:100,
	prompt:"请填写脚本！"
})

function cleanAllowanceItemForm(){
	$("#allowanceItemForm").form("reset");
	$("#allowance_item_id").val("");
}

function submit_allowance_item(){
	hao.submitForm({
		form:$("#allowanceItemForm"),
		data:{
			"salaryId":salaryId,
			"companyId":companyId
		},
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				$('#allowanceItemList').datagrid("reload");
				$("#addAllowanceItemWindow").window("close");
				$.messager.alert('提示',"操作成功");
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}

function edit_new_allowance_item(){
	edit_new_allowance_item("");
}

function edit_new_allowance_item(id){
	cleanAllowanceItemForm();
	$("#viewBaseDataDiv").html("");
	$("#addAllowanceItemWindow").window("open");
	//加载计算因子
	hao.ajax({
		url:"query_salary_computing_item.action",
		data:{
			salaryId:salaryId,
			companyId:companyId
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
		$("#allowanceItemForm").form("load","load_allowance_item.action?id="+id);
	}
}


/***查询脚本***/
function viewScript(id){
	hao.ajax({
		url:"load_allowance_item.action",
		data:{
			id:id
		},
		success:function(rtn){
			if(rtn){
				$.messager.alert('脚本', "<textarea style=\"width:270px;height:200px\">"+rtn.summayScript+"</textarea>");
			}
		}
	})
}


function del_allowance_item(id){
	$.messager.confirm('确认','确认删除此津贴吗?',function(r){
	    if(r){
			hao.ajax({
				url:"del_allowance_item.action",
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
						$("#allowanceItemList").datagrid("reload");
					}else{
						$.messager.alert('错误', rtn.rtnMsg );
					}					
				}
			})
	    }
	});
}

$("#allowanceItemForm").form();

$("#addAllowanceItemWindow").window({
	 title:"编辑津贴",
 	 width: 600,
    height: 400,
    modal: true,
    minimizable:false,
    closed:true
})

$("#allowanceItemList").datagrid({
	url:"load_allowance_item_page.action",
	cache : false,
	rownumbers : true,
	pagination : true,
	queryParams:{
		salaryId:salaryId,
		companyId:companyId
	},
	columns: [[
		{field:"name",title:"津贴",width:100},
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
				html += "<a href=\"javascript:del_allowance_item('"+value+"')\">删除</a>&nbsp;|&nbsp";
				html += "<a href=\"javascript:edit_new_allowance_item('"+value+"')\">修改</a>&nbsp;";
				return html;
			}
		}
	]]
})
</script>