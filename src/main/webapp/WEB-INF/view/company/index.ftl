<@p.header title="公司管理"/>
<div class="app-header">
公司管理>公司设置
</div>
<div class="app-content">
	<table id="list"></table>
</div>


<div id="addWindow" style="display: none;text-align: center;width: 100%;padding-top: 20px;">
	<form id="addForm" method="post" action="save.action">
		<input id="id"  name="id" type="hidden" >
		<div class="form-item">
			<label for="name" class="label-top">公司名称：</label>
			<input id="name"  name="name" class="easyui-validatebox easyui-textbox" style="width:250px" prompt="请输入公司名称" >
		</div>
	</form>
	<div style="clear: both;"></div>
	<a href="javascript:addNew()" class="easyui-linkbutton primary">保存</a>
	<a href="javascript:closeAddWindow()" class="easyui-linkbutton default">关闭</a>
</div>

<div id="settingWindow" style="display: none;text-align: center;width: 100%;">
	<div id="tabs" class="easyui-tabs" style="width:100%;height:100%;">
		<div title="考勤导入项" style="padding:20px;display:none;"></div>
	    <div title="大项目" style="padding:20px;display:none;"></div>
	    <div title="项目" style="padding:20px;display:none;"></div>
	    <div title="线别" style="padding:20px;display:none;"></div>
	    <div title="部门" style="padding:20px;display:none;"></div>
	    <div title="职务" style="padding:20px;display:none;"></div>
	 </div>
</div>

<div id="buttonArea">
	<a href="javascript:openAddWindow()" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'">添加公司</a>
</div>


<script type="text/javascript">
//打开添加页面
function openAddWindow(){
	$("#addWindow").window("open");
}
function closeAddWindow(){
	$("#addWindow").window("close");
}

var companyId = "";

function openSetting(id){
	companyId = id;
	$("#settingWindow").window("open");
	$("#tabs").tabs("select",0);
	var tab = $('#tabs').tabs('getSelected');
	tab.panel('refresh', "setting/attendance.action?companyId="+id);
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

function openEdit(id){
	openAddWindow();
	$("#addForm").form("load","load.action?id="+id);
}

function addNew(){
	
	hao.submitForm({
		form:$("#addForm"),
		success:function(rtn){
			if(rtn.rtnCode=="000000"){
				closeAddWindow();
				$('#list').datagrid("reload");
			}else{
				$.messager.alert('错误', rtn.rtnMsg );
			}
		}
	})
}


//页面初始化
$(document).ready(function(){
	$("#tabs").tabs({
		onSelect:function(title,index){
			$("#tabs").tabs("select",index);
			var tab = $('#tabs').tabs('getSelected');
			if(index==0){
				tab.panel('refresh', "setting/attendance.action?companyId="+companyId);
			}else if(index==1){
				tab.panel('refresh', "setting/base_project.action?companyId="+companyId);
			}else if(index==2){
				tab.panel('refresh', "setting/project.action?companyId="+companyId);
			}else if(index==3){
				tab.panel('refresh', "setting/production_line.action?companyId="+companyId);
			}else if(index==4){
				tab.panel('refresh', "setting/department.action?companyId="+companyId);
			}else if(index==5){
				tab.panel('refresh', "setting/point.action?companyId="+companyId);
			}
		}
	})
	
	
	$("#addWindow").window({
		title:"添加账号",
	 	width: 400,
        height: 200,
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
	
	$("#settingWindow").window({
		title:"设置公司信息",
	 	width: 600,
        height: 400,
        modal: true,
        minimizable:false,
        closed:true
	});
	
	$('#list').datagrid({
		url: 'page_list.action',
		cache : false,
    	rownumbers : true,
    	pagination : true,
		toolbar: $("#buttonArea"),
		columns: [
			[{
				field: 'name',
				title: '公司',
				width: 350
			}, {
				field:"id",
				title:"操作",
				width:200,
				formatter(value,row,index){
					html = "<a href=\"javascript:del('"+value+"')\">删除</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:openEdit('"+value+"')\">编辑</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:openSetting('"+value+"')\">基础设置</a>";
					return html;
				}
			}]
		]
	});
})
</script>
<@p.footer/>