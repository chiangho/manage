<#macro include>  
	<script src="${domain}common/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${domain}common/easyui/jquery.easyui.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="${domain}common/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript" charset="utf-8"></script>
	<script src="${domain}common/easyui/themes/super/super.js" type="text/javascript" charset="utf-8"></script>
	<script src="${domain}common/js/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="${domain}common/date_picker/WdatePicker.js" type="text/javascript" charset="utf-8"></script>
	<script src="${domain}common/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
	<link  href="${domain}common/easyui/themes/super/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link  href="${domain}common/easyui/themes/super/superBlue.css" rel="stylesheet" type="text/css"  id="themeCss"/>
	<link  href="${domain}common/style.css" rel="stylesheet" type="text/css" />
</#macro>


<#macro header title="后台系统">  
<!DOCTYPE html>  
<html>  
    <head>  
        <meta charset="UTF-8">  
        <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0" />  
        <meta name="format-detection" content="telephone=no" />  
        <title>${title}</title>  
        <script src="${domain}common/js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${domain}common/easyui/jquery.easyui.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${domain}common/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript" charset="utf-8"></script>
		<script src="${domain}common/easyui/themes/super/super.js" type="text/javascript" charset="utf-8"></script>
		<script src="${domain}common/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="${domain}common/date_picker/WdatePicker.js" type="text/javascript" charset="utf-8"></script>
		<script src="${domain}common/js/jquery.form.js" type="text/javascript" charset="utf-8"></script>
		<link  href="${domain}common/easyui/themes/super/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
		<link  href="${domain}common/easyui/themes/super/superBlue.css" rel="stylesheet" type="text/css"  id="themeCss"/>
		<link  href="${domain}common/style.css" rel="stylesheet" type="text/css" />
        <#nested>  
    </head>  
    <body id="${appid}" class="easyui-layout">  
    	<#include "/frame/header.ftl"/>
    	<#include "/frame/left.ftl"/>
    	<div data-options="region:'center'" style="padding:0px 0px 0xp 0px;">
</#macro>  
  
<#--公共底部-->  
<#macro footer>  
		</div>
		<div data-options="region:'south'" class="super-south">
			<!--页脚-->
			<div class="super-footer-info">
				<span><i class="fa fa-info-circle"></i>作者：蒋昊</span>
				<span><i class="fa fa-copyright"></i> CopyRight 2018 版权所有</span>
			</div>
		</div>
    </body>  
</html>  
</#macro>  