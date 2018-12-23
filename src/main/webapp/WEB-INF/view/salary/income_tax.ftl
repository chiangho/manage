<@p.include/>
<style>
.panel.window.panel-htop{
	top:-10px !important;
}
</style>
<table id="departmentList"></table>
<div id="departmentTb" style="height: auto">
	<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'" onclick="append()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-minus'" onclick="removeit()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-save'" onclick="accept()">保存</a>
</div>
<script type="text/javascript">
salaryId = "${RequestParameters['salaryId']}";
companyId ="${RequestParameters['companyId']}";

var currentIndex =-1;
var isEdit       = false;
$("#departmentList").datagrid({
	toolbar:$("#departmentTb"),
	singleSelect:true,
    url: '${domain}salary/load_salary_app_income_tax.action?salaryId='+salaryId,
    columns:[[
    	{field:"lowNum",title:"全月应纳税所得额下线【单位元】",editor:{type:"numberbox",options:{precision:2,validType:'minLength[1]'}},width:200},
    	{field:"upNum",title:"全月应纳税所得额上线【单位元】",editor:{type:"numberbox",options:{precision:2,validType:'minLength[1]'}},width:200},
    	{field:"proportion",title:"比例【数值在0-100之间】",editor:{type:"numberbox",options:{required:true,precision:2}},width:200},
    	{field:"sskcs",title:"速算扣除数【数值在0-100之间】",editor:{type:"numberbox",options:{required:true,precision:2}},width:200},
    	{field:"id",title:"主键",editor:"textbox",hidden:true}
    ]],
    onAfterEdit: function(index, row){
    	row["salaryId"]=salaryId;
    	hao.ajax({
    		url:"${domain}salary/save_app_income_tax.action",
    		data:row,
    		success:function(rtn){
    			if(rtn.rtnCode=="000000"){
    				$('#departmentList').datagrid('updateRow',{
    					index:index,
    					row:rtn.data
    				});
    			}else{
    			    $.messager.alert('错误', rtn.rtnMsg );
    			}
    		}
    	})
    },
    onSelect:function(index,row){
    	//取消所有的的选中
    	$('#departmentList').datagrid('unselectAll');
    	//结束其他编辑行
    	if(currentIndex!=-1){
    		$('#departmentList').datagrid('endEdit',currentIndex);
    	}
    	//设置当前行为编辑
    	currentIndex = index;
    	isEdit = true;
   	 	$('#departmentList').datagrid('beginEdit',index);
    },
})

function append(){
	if(!isEdit){
		$('#departmentList').datagrid('appendRow',{
			lowNum:0,
			upNum:0,
			proportion:0,
			sskcs:0,
			id:""
		});
		editIndex = $('#departmentList').datagrid('getRows').length-1;
		currentIndex = editIndex;
        $('#departmentList').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
        isEdit = true;
	}else{
		if(currentIndex!=-1){
    		$('#departmentList').datagrid('endEdit',currentIndex);
    		isEdit = false;
    		currentIndex==-1;
    	}
    	append();
	}
}


function endEditing(){
    if (currentIndex == undefined||currentIndex==-1){
    	return true
    }
    if ($('#departmentList').datagrid('validateRow', currentIndex)){
        $('#departmentList').datagrid('endEdit', currentIndex);
        currentIndex = -1;
        isEdit = false;
        return true;
    } else {
        return false;
    }
}

function accept(){
	if(endEditing()){
        $('#departmentList').datagrid('acceptChanges');
    }
}

function removeit(){
	if (currentIndex == undefined||currentIndex==-1){
		return;
	}
	idEdit =  $("#departmentList").datagrid("getEditor",{index:currentIndex,field:"id"});
	id="";
	if(idEdit!=undefined&&idEdit!=null){
		id=$(idEdit.target).textbox('getValue');
	}
	if(id != undefined && id !=""){
		hao.ajax({
			url:"${domain}salary/del_app_income_tax.action",
			data:{
				id:id
			},
			success:function(rtn){
				if(rtn.rtnCode=="000000"){
				    $('#departmentList').datagrid('cancelEdit', currentIndex).datagrid('deleteRow', currentIndex);
				    currentIndex = -1;
				}else{
					
				}
			}
		})
	}else{
		$('#departmentList').datagrid('cancelEdit', currentIndex).datagrid('deleteRow', currentIndex);
		currentIndex = -1;
	}
	isEdit = false;
}
</script>
