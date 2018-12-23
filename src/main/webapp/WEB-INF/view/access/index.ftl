<@p.header title="账号管理"/>

<table id="list"></table>
<!-- 添加账号对话框 -->
<div id="addWindow" style="display: none;text-align: center;width: 100%;padding-top: 20px">
	<form id="addAccessForm" method="post" action="save_access.action">
		<div class="form-item">
			<label for="access" class="label-top">账号：</label>
			<input id="access" class="easyui-validatebox easyui-textbox" prompt="请输入账号" >
		</div>
		<div class="form-item">
			<label for="access" class="label-top">密码：</label>
			<input id="password" type="password" class="easyui-validatebox easyui-textbox" prompt="请输入密码" >
		</div>
		<div class="form-item">
			<label for="access" class="label-top">确认密码：</label>
			<input id="_password" type="password" class="easyui-validatebox easyui-textbox" prompt="请再次输入密码确认" >
		</div>
		<div class="form-item">
			<label for="mobilePhone" class="label-top">手机：</label>
			<input id="mobilePhone" class="easyui-validatebox easyui-textbox" prompt="请输入手机" >
		</div>
		<div class="form-item">
			<label for="email" class="label-top">邮箱：</label>
			<input id="email" class="easyui-validatebox easyui-textbox" prompt="请输入邮箱" >
		</div>
	</form>
	<div style="clear: both;"></div>
	<a href="javascript:addNewAccess()" class="easyui-linkbutton primary">保存</a>
	<a href="javascript:closeAddWindow()" class="easyui-linkbutton default">关闭</a>
</div>



<script type="text/javascript">

//打开添加页面
function openAddWindow(){
	$("#addWindow").window("open");
}
function closeAddWindow(){
	$("#addWindow").window("close");
}
function addNewAccess(){
	hao.submitForm({
		form:$("#addAccessForm"),
		success:function(rtn){
			alert(JSON.stringify(rtn));
			if(rtn.rtnCode=="000000"){
				
			}else{
				
			}
		}
	})
}


//页面初始化
$(document).ready(function(){
	
	$("#addWindow").window({
		title:"添加账号",
	 	width: 600,
        height: 400,
        modal: true,
        minimizable:false,
        closed:true
	});
	
	
	$('#list').datagrid({
		url: 'query_access_list.action',
		cache : false,
    	rownumbers : true,
    	pagination : true,
    	collapsible : false,
    	title:"账号列表",
		toolbar: [{
			text: '添加',
			iconCls: 'fa fa-plus',
			handler: function() {
				openAddWindow();
			}
		}],
		columns: [
			[{
				field: 'access',
				title: '账号',
				width: 100
			}, {
				field: 'mobilePhone',
				title: '手机',
				width: 100
			}, {
				field: 'email',
				title: '邮箱',
				width: 150
			},{
				field:"id",
				title:"操作",
				width:200,
				formatter(value,row,index){
					html = "<a href=\"javascript:resetPassword('"+value+"')\">修改密码</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:del('"+value+"')\">删除</a>&nbsp;|&nbsp;";
					html += "<a href=\"javascript:edit('"+value+"')\">编辑</a>";
					return html;
				}
			}]
		]
	});
})
</script>
<@p.footer/>