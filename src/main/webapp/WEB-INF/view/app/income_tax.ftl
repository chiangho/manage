<@p.header title="个人所得税设置"/>
<div class="app-header">
个税比例设置
</div>
<div class="app-content">
	<table id="departmentList"></table>
	<div id="departmentTb" style="height: auto">
		<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-plus'" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-minus'" onclick="removeit()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton primary" data-options="iconCls:'fa fa-save'" onclick="accept()">保存</a>
	</div>
</div>
<script type="text/javascript">
var currentIndex =-1;
var isEdit       = false;
$("#departmentList").datagrid({
	toolbar:$("#departmentTb"),
	singleSelect:true,
    url: '${domain}app/income_tax/load.action',
    columns:[[
    	{field:"lowNum",title:"全月应纳税所得额下线【单位元】",editor:{type:"numberbox",options:{precision:2,validType:'minLength[1]'}},width:200},
    	{field:"upNum",title:"全月应纳税所得额上线【单位元】",editor:{type:"numberbox",options:{precision:2,validType:'minLength[1]'}},width:200},
    	{field:"proportion",title:"税率【数值在0-100之间】",editor:{type:"numberbox",options:{required:true,precision:2}},width:200},
    	{field:"sskcs",title:"速算扣除数【数值在0-100之间】",editor:{type:"numberbox",options:{required:true,precision:2}},width:200},
    	{field:"id",title:"主键",editor:"textbox",hidden:true}
    ]],
    onAfterEdit: function(index, row){
    	hao.ajax({
    		url:"${domain}app/income_tax/save.action",
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
			url:"${domain}app/income_tax/del.action",
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
<@p.footer/>