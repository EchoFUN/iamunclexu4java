$(function () {
    var jQrespond = $('#respond-form');

    var submitHandler = function (evt) {
        evt.preventDefault();

        var author = $.trim(this.author.value),
            mail = $.trim(this.mail.value),
            webside = $.trim(this.webside.value),
            comment = $.trim(this.comment.value);

        if (author.length == 0 || mail.length == 0 || comment.length == 0) {
            alert('请完善资料！')
        } else {
            var self = this;
            $.get('/comment', jQrespond.serialize() + '&pid=' + jQrespond.data('pid'), null, 'json').then(function (ret) {
                if (ret.code == 0) {
                    alert('评论成功！需要管理员审核才能显示！');

                    self.mail.value = '';
                    self.author.value = '';
                    self.webside.value = '';
                    self.comment.value = '';
                } else {
                    alert(ret.msg);
                }
            }).fail(function(ret) {

                debugger;

            });
        }
    };


    var bindEvt = function () {
        jQrespond.submit(submitHandler);
    };
    bindEvt();
});