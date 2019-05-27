<div class="header">
	<div class="inner">
		<div class="title">
			<a href="/">我是徐蜀黍</a>
		</div>
		<div class="sub-title">杂食性程序员：关注前端，python，node.js，偶尔也会玩玩 android ... </div>
	</div>
</div>
<div class="navigator">
	<ul>
		<#list menus as menu>
		<li <#if '/' == menu.url>class="active" ></#if>
			<a href="${ menu.url }">${ menu.title }</a>
		</li>
		</#list>
	</ul>
</div>