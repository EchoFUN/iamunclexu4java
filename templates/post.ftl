<!DOCTYPE HTML>
<!--[if IE 9]><html class="ie9"><![endif]-->
<!--[if IE 8]><html class="ie8"><![endif]-->
<!--[if IE 7]><html class="ie7"><![endif]-->
<!--[if IE 6]><html class="ie6"><![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->

<head>
    <#include "component/head.ftl">
    <link rel="stylesheet" href="/static/post/index.css">
</head>

<body>
    <#include "component/banner.ftl">
    <div class="wrapper">
        <div class="wrapper-inner clearfix">
            <div class="main">
                <h1>${ post_details.title }</h1>
                <div class="post-meta">
                    <span class="date">${ post_details.date }</span>
                    <span class="author">${ post_details.author }</span>
                    <span class="comments">
                        <a href="javascript:;" title="${ post_details.author }">0</a>
                    </span>
                </div>
                <div class="post-content">
                    ${ post_details.content }
                </div>
            </div>
            <#include "component/sidebar.ftl">
        </div>
    </div>
    <#include "component/footer.ftl">
</body>

</html>