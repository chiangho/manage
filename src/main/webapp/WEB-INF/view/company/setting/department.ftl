<table id="departmentList"></table>
<div id="departmentTb" style="height: auto">
	<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'" onclick="append()">添加</a>
	<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-minus'" onclick="removeit()">删除</a>
	<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-save'" onclick="accept()">保存</a>
</div>

<script type="text/javascript">
companyId = "${RequestParameters['companyId']}";
var editIndex = undefined;

function endEditing(){
    if (editIndex == undefined){return true}
    if ($('#departmentList').datagrid('validateRow', editIndex)){
        $('#departmentList').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

$("#departmentList").datagrid({
	toolbar:$("#departmentTb"),
	singleSelect:true,
    url: '${domain}company/setting/query_department.action?companyId='+companyId,
    columns:[[
    	{field:"name",title:"名称",editor:{type:"textbox",options:{required:true}},width:"100%"},
    	{field:"id",title:"主键",editor:"textbox",hidden:true}
    ]],
    onEndEdit: function(index, row){
    	var ed = $(this).datagrid('getEditor', {
            index: index,
            field: 'name'
        });
        tempName = $(ed.target).textbox('getText');
        tempId = row.id;
        if(tempName==null||tempName==""){
        	return;
        }
    	hao.ajax({
    		url:"${domain}company/setting/save_department.action",
    		data:{
    			companyId:companyId,
    			name:tempName,
    			id:row.id
    		},
    		success:function(rtn){
    			if(rtn.rtnCode=="000000"){
					row.id=rtn.item.id;
					row.name=rtn.item.name;
					uploadRowData(index,rtn.item.name,rtn.item.id);
    			}else{
    				alert(rtn.rtnMsg);
    			}
    		}
    	})
    },
    onClickCell:function(index,field){
    	 if (editIndex != index){
    		 if (endEditing()){
	    		  $('#departmentList').datagrid('selectRow', index).datagrid('beginEdit', index);
	    		  var ed = $('#departmentList').datagrid('getEditor', {index:index,field:field});
	    		  if (ed){
	                  ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
	              }
	              editIndex = index;
    		 }else{
    			 setTimeout(function(){
                     $('#departmentList').datagrid('selectRow', editIndex);
                 },0);
    		 }
    	 }
	}
})

function uploadRowData(index,name,id){
	$('#departmentList').datagrid('updateRow',{
		index:index,
		row:{
			id:id,
			name:name
		}
	});
}

function append(){
	if (endEditing()){
        $('#departmentList').datagrid('appendRow',{name:""});
        editIndex = $('#departmentList').datagrid('getRows').length-1;
        $('#departmentList').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
    }
	var rows = $('#departmentList').datagrid('getChanges');
}
function removeit(){
	if (editIndex == undefined){
		return;
	}
	tempRow = $("#departmentList").datagrid("getSelections");
	if(tempRow.length>0 && tempRow[0].id != undefined && tempRow[0].id !=""){
		hao.ajax({
			url:"${domain}company/setting/del_department.action",
			data:{
				id:tempRow[0].id
			},
			success:function(rtn){
				if(rtn.rtnCode=="000000"){
				    $('#departmentList').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
				    editIndex = undefined;
				}else{
					
				}
			}
		})
	}else{
		$('#departmentList').datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}
}
function accept(){
    if (endEditing()){
        $('#departmentList').datagrid('acceptChanges');
    }
}
</script>











