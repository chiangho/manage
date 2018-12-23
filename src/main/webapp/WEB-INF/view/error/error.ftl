<@p.header title="错误页面"/>
你好，你的操作发生了错误
<br/>
错误编号：${rtnCode}
<br/>
错误信息：${rtnMsg }
<br/>
<#if RequestParameters['back_url']??>
	<a href="${RequestParameters['back_url']}">返回</a>
</#if>
<@p.footer/>