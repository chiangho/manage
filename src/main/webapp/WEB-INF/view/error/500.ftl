<@p.header title="错误页面"/>
不好意思，这个是一个异常页面！
<#if RequestParameters['back_url']??>
	<a href="${RequestParameters['back_url']}">返回</a>
</#if>
<@p.footer/>