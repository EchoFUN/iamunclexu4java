<!DOCTYPE HTML>
<!--[if IE 9]><html class="ie9"><![endif]-->
<!--[if IE 8]><html class="ie8"><![endif]-->
<!--[if IE 7]><html class="ie7"><![endif]-->
<!--[if IE 6]><html class="ie6"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->

<head>
    <#include "component/head.ftl" >
</head>

<body>
<#include "component/banner.ftl" >
<div class="wrapper">
    <div class="wrapper-inner">
        <div class="main article-list">
            <#list posts as post>
            <div class="post" id="post-${ post.id }">
                <h2><a href="/post?id=${ post.id }">${ post.title }</a></h2>
                <div class="post-meta">
                    <span class="date">{{ post.formated }}</span>
                    <span class="author">{{ post.author }}</span>
                    <span class="comments"><a href="javascript:;" title="${ post.title }">0</a></span>
                </div>
                <div class="post-content">{% autoescape off %}{{ post.content }}{% endautoescape %}</div>
            </div>
            </#list>
        </div>
        <#include "component/sidebar.ftl" >
        <div style="clear: both;"></div>
        <div class="pagination clearfix">
            {% if has_next %}
            <a style="float: right;" href="/?p={{ current|add:1 }}">下一页</a>
            {% endif %}
            {% if has_prev %}
            <a href="/?p={{ current|add:-1 }}">上一页</a>
            {% endif %}
        </div>
    </div>
</div>
<#include "component/footer.ftl" >

<script src="/static/lib/jquery.js"></script>
<script src="/static/index/index.js"></script>
</body>

</html>