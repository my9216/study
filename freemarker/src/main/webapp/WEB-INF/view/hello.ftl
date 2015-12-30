<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"   
 "http://www.w3.org/TR/html4/loose.dtd">  
<html>  
    <head>  
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <title>${title}</title>  
    </head>  
    <body>  
        ${content}  <br/>
        ${map.abc} <br/>
        <#if content == 'young'>
        	i'm young
        <#else>
		  Pythons are not cheaper than elephants today.
		</#if>
		<#list listtest as animal>
		    ${animal}
		</#list>
		<p>Fruits: <#list listtest as fruit>${fruit}<#sep>, </#list>
		<br/>
		<#list listtest2.abc>
		     aaaa
			<#items as fruit>
		      ${fruit} 
		    </#items>
		</#list>
		
		<#macro greet>
		  <font size="+2">Hello Joe!</font>
		</#macro>
		
		<@greet/>
    </body>  
</html>  