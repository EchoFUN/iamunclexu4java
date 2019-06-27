<div class="header">
	<div class="inner">
		<div class="title">
			<a href="/">我是徐蜀黍</a>
		</div>
		<div class="sub-title">杂食性程序员：关注安卓，web frontend，flutter，node.js等大前端领域，偶尔也会玩玩 python和java后端 ... </div>
	</div>
</div>
<div class="navigator">
	<ul>
		<#list menus as menu>
		<li <#if '/' == menu.url>class="active"</#if>>
			<a href="${ menu.url }">${ menu.title }</a>
		</li>
		</#list>
	</ul>
</div>