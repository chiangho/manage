<html>
<head>
</head>
<html>
	<table>
		<th>
			<#if data.headers??>
				<#list data.headers as column> 
				   <td>${column}</td>
				</#list>
			</#if>
		</th>
		<#if data.datas??>
			<#list data.datas as row> 
				  <tr>
					  	 <#list row as column> 
					  	 		<td>${column}</td>
					  	 </#list>
				  </tr>
			</#list>
		</#if>
	</table>
</html>
</html>