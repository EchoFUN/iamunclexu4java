<div class="sidebar">

  <div class="widget widget-avatarbox">
    <div class="avatar">
      <img width="80" height="80" src="/static/image/avatar.jpg">
        <#--			<img width="80" height="80" src="http://s.xnimg.cn/a.gif">-->
    </div>
    <div class="description">
      奔三路上的
      <strong>80</strong>后
      <br/>热爱
      <strong>Code</strong>和
      <strong>音乐</strong>
      <br/>
      <strong>字节跳动</strong>研发工程师
      <br/>Github粉，
      <strong>
        <a href="http://github.com/echofun" target="_blank">Github地址</a>
      </strong>
    </div>
    <div class="clear"></div>
    <div class="mod">
      <span class="tips"></span>
      <div class="mod-content"></div>
    </div>
  </div>

  <!-- Micro BLOG . -->
  <#--
  <div class="widget">
    <h3>找个地方瞎 BB</h3>
    <ul>
        <#list microblogs as blog>
          <li>
              ${ blog.text }<br/>@ ${ blog.date }
          </li>
        </#list>
    </ul>
  </div>
  -->

    <!-- Recent posts . -->
  <div class="widget">
    <h3>近期文章</h3>
    <ul>
        <#list recent_post as post>
          <li>
            <a href="/post?id=${ post.id }">${ post.title }</a>
          </li>
        </#list>
    </ul>
  </div>

  <!-- Archive -->
  <div class="widget">
    <h3>文章归档</h3>
    <ul>
        <#list archived as archive >
          <li>
            <a href="javascript:;">${ archive }</a>
          </li>
        </#list>
    </ul>
  </div>

  <!-- Friendly link .  -->
  <div class="widget">
    <h3>友情链接</h3>
    <ul>
        <#list links as link>
          <li>
            <a href="${ link.url }">${ link.title }</a>
          </li>
        </#list>
    </ul>
  </div>
</div>
