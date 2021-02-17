<!DOCTYPE HTML>
<!--[if IE 9]>
<html class="ie9"><![endif]-->
<!--[if IE 8]>
<html class="ie8"><![endif]-->
<!--[if IE 7]>
<html class="ie7"><![endif]-->
<!--[if IE 6]>
<html class="ie6"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->

<head>
    <#include "component/head.ftl">
  <link rel="stylesheet" href="/static/post/index.css">
  <script src="/static/lib/jquery.js" type="application/javascript"></script>
  <script src="/static/post/index.js" type="application/javascript"></script>
</head>

<body>
<#include "component/banner.ftl">
<div class="wrapper">
  <div class="wrapper-inner clearfix">
    <div class="main">
        <#if post_details?exists>
          <h1>${ post_details.title }</h1>
          <div class="post-meta">
            <span class="date">${ post_details.date }</span>
            <span class="author">${ post_details.author }</span>
            <span class="comments">
                            <a href="javascript:;"
                               title="${ post_details.author }">${ post_details.counter }</a>
                        </span>
          </div>
          <div class="post-content">${ post_details.content }</div>

          <!-- 评论内容 -->
          <div class="comments-area" id="comments-area">
              <#if comments?size gt 0 >
                <h3 id="comments-title">关于“${ post_details.title }”的${ comments?size }个想法</h3>
                <div class="comment-list" id="comment-list">
                    <#list comments as comment>
                      <div class="comment-item ${ ((comment_index % 2) == 1) ?string('even','odd') }">
                        <div class="reply-comment right">
                          <a href="javascript:;">回复</a>
                        </div>
                        <div class="avatar">
                          <img height="38" width="38"
                               src="http://cdn.v2ex.com/static/img/avatar_normal.png"/>
                        </div>
                        <div class="comment-info">
                          <div class="name"><a href="">${ comment.name }</a>&nbsp;说：</div>
                          <div class="date">${ comment.formatted }</div>
                        </div>
                        <div class="clear"></div>
                        <div class="comment-content">${ comment.content }</div>
                      </div>
                    </#list>
                </div>
              </#if>

            <div id="respond" class="respond">
              <h3 id="reply-title">回复</h3>
              <div class="respond-content">
                <form id="respond-form" action="/" data-pid="${ post_details.id }">
                  <div class="item clearfix">
                    <label for="author">姓名：</label>
                    <div class="input">
                      <input placeholder="昵称" type="text" name="author" class="author"/><span
                              class="required">*</span>
                    </div>
                  </div>
                  <div class="item clearfix">
                    <label for="mail">邮箱：</label>
                    <div class="input">
                      <input placeholder="您的邮箱地址" type="text" name="mail" class="mail"/><span
                              class="required">*</span>
                    </div>
                  </div>
                  <div class="item clearfix">
                    <label for="webside">网站：</label>
                    <div class="input">
                      <input placeholder="您的网站地址" type="text" name="webside" class="webside"/>
                    </div>
                  </div>
                  <div class="item clearfix">
                    <label for="comment">评论：</label>
                    <div class="input">
                      <textarea cols="63" rows="8" name="comment"></textarea>
                    </div>
                    <span class="required fl">*</span>
                  </div>
                  <div class="item clearfix">
                    <label></label><input class="submit borderradius-3" name="submit" value="提交评论"
                                          type="submit"/>
                  </div>
                  <form>
              </div>
            </div>
          </div>
        </#if>
    </div>
      <#include "component/sidebar.ftl">
  </div>
</div>
<#include "component/footer.ftl">
</body>
</html>
