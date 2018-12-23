<div data-options="region:'west',border:false,title:'系统菜单'" class="super-west">
	<!--左侧导航 data-options="iconCls:'fa fa-desktop'"   -->
	<div id="menu_accordion" class="easyui-accordion" data-options="border:false,fit:true,animate:false,selected:false">
		<#list menuList as menu>
			<div title="${menu.name}" index="${menu_index}" >
				<ul>
					<#if menu.subMenuList??>
						<#list menu.subMenuList as subMenu>
							<li id="MENU_${subMenu.id}" onclick="_frameGoUrl('${domain}${subMenu.url}','${subMenu.id}')">${subMenu.name}</li>
						</#list>
					</#if>
				</ul>
			</div>
		</#list>
	</div>
</div>
<script>
function _frameGoUrl(url,id){
	window.location.href=url+"#"+id;
}
$(document).ready(function(){
	showMenuId = location.hash.substring(1);
	indexNum = $("#MENU_"+showMenuId).parent().parent().attr("index");
	title = $("#MENU_"+showMenuId).parent().parent().attr("title");
	$("#menu_accordion").accordion("select",parseInt(indexNum));
	$("#MENU_"+showMenuId).addClass("select");
});
</script>