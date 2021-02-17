<div class="header">
  <div class="inner">
    <div class="title">
      <a href="/">我是徐蜀黍</a>
    </div>
    <div class="sub-title">杂食性程序猿：专注于 Android，web frontend，flutter，node.js 等大前端领域，也会玩玩 python 和 java
      等后端技术 ...
    </div>
  </div>
</div>
<div class="navigator">
  <ul>
      <#list menus as menu>
        <li <#if url?exists><#if url == menu.url>class="active"
            </#if><#if url == menu.url>class="active"</#if></#if>>
          <a href="${ menu.url }">${ menu.title }</a>
        </li>
      </#list>
  </ul>
</div>
