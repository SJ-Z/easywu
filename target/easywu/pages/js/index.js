$(function () {
    var base_url = 'http://localhost:8080/easywu';
    var storage = window.localStorage;

    //跳蚤市场登录按钮的点击事件
    $('#tzsc-login-btn').click(function () {
        var $username = $('#tzsc-username').val();
        var $pwd = $('#tzsc-pwd').val();
        if ($username.length === 0) {
            $('.tzsc-login-msg').text("请输入用户名");
        } else if ($pwd.length === 0) {
            $('.tzsc-login-msg').text("请输入密码");
        } else {
            $('.tzsc-login-msg').text("");
            //向后台发送的数据
            var postData = {
                username: $username,
                pwd: $pwd
            };
            $.ajax({
                url:base_url+'/user/tzsc_login',
                data:JSON.stringify(postData),
                type:"post",
                //设置接收格式为JSON
                dataType:"json",
                //编码设置
                contentType:"application/json;charset=utf-8",
                success: function (msg) {
                    if(msg != null){
                        if (msg.code === 1) {//登录成功
                            storage.setItem("admin_id", msg.id);
                            storage.setItem("admin_nick", msg.nick);
                            window.location.href = base_url + "/pages/tzsc-index.html";
                        } else {
                            $('.tzsc-login-msg').text("用户名或密码错误，登录失败！");
                        }
                    } else{
                        $('.tzsc-login-msg').text("未知错误");
                    }
                },
                error: function (xhr) {
                    console.log(xhr.status);
                    alert("跳蚤市场管理员登录失败，请重试");
                }
            });
        }
    });
});