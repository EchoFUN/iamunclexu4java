<div class="sidebar">

	<div class="widget widget-avatarbox">
		<div class="avatar">
			<img width="80" height="80" src="http://ww3.sinaimg.cn/mw1024/6bf9eebbjw1ed7nmcrwx9j206l06m3yi.jpg">
		</div>
		<div class="description">
			奔三路上的
			<strong>80</strong>后
			<br />热爱
			<strong>Code</strong>和
			<strong>音乐</strong>
			<br />
			<strong>今日头条</strong>研发工程师
			<br />Github粉，
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
	<div class="widget">
		<h3>找个地方瞎 BB</h3>
		<ul>
			{% for blog in microblogs %}
			<li>
				{{ blog.text }}
			</li>
			{% endfor %}
		</ul>
	</div>

	<!-- Recent posts . -->
	<div class="widget">
		<h3>近期文章</h3>
		<ul>
			{% for post in recent_post %}
			<li>
				<a href="/post?id={{ post.id }}">{{ post.title|truncatechars:"22" }}</a>
			</li>
			{% endfor %}
		</ul>
	</div>

	<!-- Archive -->
	<div class="widget">
		<h3>文章归档</h3>
		<ul>
			{% for archive in archived %}
			<li>
				<a href="javascript:;">{{ archive }}</a>
			</li>
			{% endfor %}
		</ul>
	</div>

	<!-- Friendly link .  -->
	<div class="widget">
		<h3>友情链接</h3>
		<ul>
			{% for li in link %}
			<li>
				<a href="javascript:;">{{ li.title }}</a>
			</li>
			{% endfor %}
		</ul>
	</div>
</div>