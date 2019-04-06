$(function () {
    var base_url = 'http://localhost:8080/easywu';
    var base_pic_url = 'http://localhost:8080/easywu/goods_pic/';
    var storage = window.localStorage;

    //根据是否有admin_id判断管理员是否已登录
    if (storage.getItem("admin_id") == null) {
        alert("请先登录！");
        window.location.href = base_url + "/pages/index.html";
    }

    //利用localStorage初始化管理员昵称
    $('#nick').html(storage.getItem("admin_nick"));

    //退出登录的点击事件
    $('.logout').click(function () {
        storage.clear();
        window.location.href = base_url + "/pages/index.html";
    });

    /*navbar选项的点击监听，修改图标及样式*/
    $('#goods-manage').click(function () {
        var $span = $('#goods-manage span');
        if ($(this).attr('aria-expanded') === 'false') {
            $span.addClass('glyphicon-menu-left');
            $span.removeClass('glyphicon-menu-down');
        } else {
            $span.addClass('glyphicon-menu-down');
            $span.removeClass('glyphicon-menu-left');
        }
    });
    $('.navbar-li').click(function () {
        $(this).addClass('active');
        $(this).siblings().removeClass('active');
        $('.navbar-li-li').removeClass('active');
    });
    $('.navbar-li-li').click(function () {
        $(this).addClass('active');
        $(this).siblings().removeClass('active');
        $('.navbar-li').removeClass('active');
    });

    /*侧边栏数据统计的点击事件*/
    $('#statistics').click(function () {
        var $main = $('.page-content .main');
        $main.empty();

    });

    /*侧边栏所有商品的点击事件*/
    $('#all-goods a').click(function () {
        var $main = $('.page-content .main');
        $main.empty();
        var $goodsList = $('<div class="goodsList"></div>');
        $goodsList.width(document.body.clientWidth - 250);
        var $page = $('<div class="container-page"><div class="page"><div class="record-div">共<span class="record-count"></span>条记录</div>' +
            '<a class="page-a-last" href="javascript:;">上一页</a>' +
            '<div class="page-container"></div><a class="page-a-next" href="javascript:;">下一页</a></div></div>');
        $main.empty();
        $main.append($goodsList);
        $main.append($page);
        getGoodsList(1);
        getGoodsPageCount();
        updatePageState(1, false);
    });

    /*侧边栏搜索商品的点击事件*/
    $('#search-goods').click(function () {
        var $main = $('.page-content .main');
        $main.empty();
        var $content = $('<div>\n' +
            '            <form class="col-md-4 search-form">\n' +
            '                <div class="search-id">\n' +
            '                    <span>商品ID：</span>\n' +
            '                    <input class="search-input" type="text" id="search-id-input">\n' +
            '                    <button class="search-btn" id="search-id-btn" type="button">搜索</button>\n' +
            '                </div>\n' +
            '                <div class="search-id-msg">请输入正确的 id </div>\n' +
            '            </form>\n' +
            '            <form class="col-md-4 search-form">\n' +
            '                <div class="search-name">\n' +
            '                    <span>商品名称：</span>\n' +
            '                    <input class="search-input" type="text" id="search-name-input">\n' +
            '                    <button class="search-btn" id="search-name-btn" type="button">搜索</button>\n' +
            '                </div>\n' +
            '                <div class="search-name-msg">请输入商品名称</div>\n' +
            '            </form>\n' +
            '            <form class="col-md-4 search-type-form">\n' +
            '                <div class="selection">\n' +
            '                    <select>\n' +
            '                        <option>===请选择分类===</option>\n' +
            '                    </select>\n' +
            '                </div>\n' +
            '                <button class="selection-btn" id="search-type-btn" type="button">按分类搜索</button>\n' +
            '                <div class="search-type-msg">请选择商品分类</div>\n' +
            '            </form>\n' +
            '        </div>\n' +
            '        <div class="goodsList search-goodsList"></div>');
        $main.append($content);

        //查询商品分类信息
        $.ajax({
            url:base_url+'/goods/allType',
            type:"get",
            //设置接收格式为JSON
            dataType:"json",
            //编码设置
            contentType:"application/json;charset=utf-8",
            success: function (msg) {
                $(".main").get(0).typeList = msg; //存储商品分类信息
                $.each(msg, function (key, value) {
                    // 根据内容创建商品类型节点
                    var $type = createTypeEle(value);
                    $type.get(0).obj = value; //存储商品分类信息
                    // 插入商品分类
                    $(".selection select").append($type);
                });
            },
            error: function (xhr) {
                console.log(xhr.status);
                alert("获取商品分类信息失败，请重试");
            }
        });
    });

    /*侧边栏发送通知的点击事件*/
    $('#notification-sidebar').click(function () {
        var $main = $('.page-content .main');
        $main.empty();
        var $content = $('<div class="notification-container">\n' +
            '            <div class="notification-page">\n' +
            '                <div class="notification-new">新通知发布</div>\n' +
            '                <div class="notification-title">\n' +
            '                    <span class="notification-span">标题：</span>\n' +
            '                    <input class="notification-title-input" type="text" maxlength="20">\n' +
            '                </div>\n' +
            '                <div class="notification-content">\n' +
            '                    <span class="notification-span">内容：</span>\n' +
            '                    <textarea class="notification-content-textarea" maxlength="255"></textarea>\n' +
            '                </div>\n' +
            '                <div class="notification-msg">&nbsp;</div>\n' +
            '                <button class="notification-send-btn" type="button">发布</button>\n' +
            '            </div>\n' +
            '        </div>');
        $main.append($content);
    });

    $('.goodsList').width(document.body.clientWidth - 250);
    // 窗口大小变化的监听
    $(window).resize(function () {
        $('.goodsList').width(document.body.clientWidth - 250);
    });

    initEvents();
    function initEvents() {
        var $main = $(".main");
        //监听页码的点击事件
        $main.delegate(".pageCode", "click", function () {
            var page = $(this).text();
            getGoodsList(page);
            $(this).addClass("cur");
            $(this).siblings().removeClass("cur");
            updatePageState(page, false);
        });
        //监听搜索商品的页码的点击事件
        $main.delegate(".pageCode-search", "click", function () {
            var page = $(this).text();
            var name = $main.get(0).searchName;
            var type = $main.get(0).searchType;
            if (name.length > 0) {
                getSearchGoodsListByName(page, name);
                $(this).addClass("cur");
                $(this).siblings().removeClass("cur");
                updatePageState(page, true);
            } else if (type.length > 0) {

                $(this).addClass("cur");
                $(this).siblings().removeClass("cur");
            }
        });
        //监听上一页按钮的点击事件
        $main.delegate(".page-a-last", "click", function () {
            var $pageCode = $('.page-container .cur');
            var page = $pageCode.text() - 1;
            if (page > 0) {
                getGoodsList(page);
                $pageCode.removeClass("cur");
                $pageCode.prev().addClass("cur");
                updatePageState(page, false);
            }
        });
        //监听商品搜索的上一页按钮的点击事件
        $main.delegate(".page-a-last-search", "click", function () {
            var $pageCode = $('.page-container .cur');
            var page = $pageCode.text() - 1;
            if (page > 0) {
                var searchName = $main.get(0).searchName;
                var searchType = $main.get(0).searchType;
                if (searchName.length > 0) {
                    getSearchGoodsListByName(page, searchName);
                } else if (searchType.length > 0) {

                }
                $pageCode.removeClass("cur");
                $pageCode.prev().addClass("cur");
                updatePageState(page, false);
            }
        });
        //监听下一页按钮的点击事件
        $main.delegate(".page-a-next", "click", function () {
            var $pageCode = $('.page-container .cur');
            var page = parseInt($pageCode.text()) + 1;
            if (page <= $main.get(0).pageCount) {
                getGoodsList(page);
                $pageCode.removeClass("cur");
                $pageCode.next().addClass("cur");
                updatePageState(page, false);
            }
        });
        //监听商品搜索的下一页按钮的点击事件
        $main.delegate(".page-a-next-search", "click", function () {
            var $pageCode = $('.page-container .cur');
            var page = parseInt($pageCode.text()) + 1;
            if (page <= $main.get(0).pageCount) {
                var searchName = $main.get(0).searchName;
                var searchType = $main.get(0).searchType;
                if (searchName.length > 0) {
                    getSearchGoodsListByName(page, searchName);
                } else if (searchType.length > 0) {

                }
                $pageCode.removeClass("cur");
                $pageCode.next().addClass("cur");
                updatePageState(page, false);
            }
        });
        //监听商品的下架标签点击事件
        $main.delegate(".delete_goods", "click", function () {
            var dialog = confirm("确认下架该商品？");
            if (dialog === true) {
                var goods = $(this).parents(".goods");
                var obj = goods.get(0).obj;
                var g_id = obj.g_id;
                $.ajax({
                    url:base_url+'/goods/deleteGoodsByAdmin',
                    data:{
                        g_id: g_id
                    },
                    type:"get",
                    //设置接收格式为JSON
                    dataType:"json",
                    //编码设置
                    contentType:"application/json;charset=utf-8",
                    success: function (msg) {
                        if (msg.code === 1) {
                            alert("商品下架成功");
                            obj.g_state = 3;
                            goods.children('form').children('.goods-state').children('#state').val("管理员下架");
                            goods.children('form').children('.bottom').children('.delete_goods').addClass("display-none");
                        } else {
                            alert("商品下架失败");
                        }
                    },
                    error: function (xhr) {
                        console.log(xhr.status);
                        alert("商品下架失败，请重试");
                    }
                });
            }
        });

        //监听商品id搜索按钮的点击事件
        $main.delegate("#search-id-btn", "click", function () {
            var id = $.trim($('#search-id-input').val());
            if (id.length != 16) {
                $('.search-id-msg').css('display', 'block');
            } else {
                $('.search-id-msg').css('display', 'none');
                $.ajax({
                    url:base_url+'/goods/searchGoodsById',
                    data:{
                        g_id: id
                    },
                    type:"get",
                    //设置接收格式为JSON
                    dataType:"json",
                    //编码设置
                    contentType:"application/json;charset=utf-8",
                    success: function (msg) {
                        $('.container-page').remove();
                        var $goodsList = $(".goodsList");
                        var $goods = createGoodsEle(msg); //根据内容创建节点
                        $goods.get(0).obj = msg; //存储商品信息
                        $goodsList.empty();
                        $goodsList.append($goods); //插入商品
                    },
                    error: function (xhr) {
                        console.log(xhr.status);
                        alert("商品查询失败，请重试");
                    }
                });
            }
        });

        //监听商品名称搜索按钮的点击事件
        $main.delegate("#search-name-btn", "click", function () {
            var name = $.trim($('#search-name-input').val());
            if (name.length === 0) {
                $('.search-name-msg').css('display', 'block');
            } else {
                $('.search-name-msg').css('display', 'none');
                var $page = $('<div class="container-page search-page"><div class="page"><div class="record-div">共<span class="record-count"></span>条记录</div>' +
                    '<a class="page-a-last-search" href="javascript:;">上一页</a>' +
                    '<div class="page-container"></div><a class="page-a-next-search" href="javascript:;">下一页</a></div></div>');
                $('.container-page').remove();
                $main.append($page);
                $.ajax({
                    url:base_url+'/goods/searchGoodsByName',
                    data:{
                        g_name: name,
                        pageCode: 1
                    },
                    type:"get",
                    //设置接收格式为JSON
                    dataType:"json",
                    //编码设置
                    contentType:"application/json;charset=utf-8",
                    success: function (msg) {
                        var $goodsList = $(".goodsList");
                        $goodsList.empty();
                        $.each(msg.goodsList, function (key, value) {
                            // 根据内容创建节点
                            var $goods = createGoodsEle(value);
                            $goods.get(0).obj = value; //存储商品信息
                            // 插入商品
                            $(".goodsList").append($goods);
                        });
                        var $main = $(".main");
                        $main.get(0).pageCount = msg.pageCount; //存储总页码
                        $main.get(0).searchName = name; //存储搜索的商品名称
                        $main.get(0).searchType = "";
                        $('.record-count').html(msg.count); //总记录数
                        var $page = $(".page-container");
                        $page.empty();
                        for(var i = 0; i < msg.pageCount; i++){
                            var $a = $("<a class='pageCode-search' href=\"javascript:;\">"+(i+1)+"</a>");
                            if(i === 0){
                                $a.addClass("cur");
                            }
                            $page.append($a);
                        }
                        updatePageState(1, true);
                    },
                    error: function (xhr) {
                        console.log(xhr.status);
                        alert("商品查询失败，请重试");
                    }
                });
            }
        });

        //监听商品分类搜索按钮的点击事件
        $main.delegate("#search-type-btn", "click", function () {
            var $option = $('.selection select option:selected');
            if ($option.val() === "===请选择分类===") {
                $('.search-type-msg').css('display', 'block');
            } else {
                $('.search-type-msg').css('display', 'none');
                var t_id = $option.get(0).obj.t_id;
                var $page = $('<div class="container-page search-page"><div class="page"><div class="record-div">共<span class="record-count"></span>条记录</div>' +
                    '<a class="page-a-last-search" href="javascript:;">上一页</a>' +
                    '<div class="page-container"></div><a class="page-a-next-search" href="javascript:;">下一页</a></div></div>');
                $('.container-page').remove();
                $main.append($page);
                $.ajax({
                    url:base_url+'/goods/searchGoodsByTypeId',
                    data:{
                        t_id: t_id,
                        pageCode: 1
                    },
                    type:"get",
                    //设置接收格式为JSON
                    dataType:"json",
                    //编码设置
                    contentType:"application/json;charset=utf-8",
                    success: function (msg) {
                        var $goodsList = $(".goodsList");
                        $goodsList.empty();
                        $.each(msg.goodsList, function (key, value) {
                            // 根据内容创建节点
                            var $goods = createGoodsEle(value);
                            $goods.get(0).obj = value; //存储商品信息
                            // 插入商品
                            $(".goodsList").append($goods);
                        });
                        var $main = $(".main");
                        $main.get(0).pageCount = msg.pageCount; //存储总页码
                        $main.get(0).searchName = "";
                        $main.get(0).searchType = t_id; //存储搜索的商品分类id
                        $('.record-count').html(msg.count); //总记录数
                        var $page = $(".page-container");
                        $page.empty();
                        for(var i = 0; i < msg.pageCount; i++){
                            var $a = $("<a class='pageCode-search' href=\"javascript:;\">"+(i+1)+"</a>");
                            if(i === 0){
                                $a.addClass("cur");
                            }
                            $page.append($a);
                        }
                        updatePageState(1, true);
                    },
                    error: function (xhr) {
                        console.log(xhr.status);
                        alert("商品查询失败，请重试");
                    }
                });
            }
        });

        //监听发送通知页面的发布按钮的点击事件
        $main.delegate(".notification-send-btn", "click", function () {
            var $title = $('.notification-title-input').val();
            var $content = $('.notification-content-textarea').val();
            if ($title.length === 0) {
                $('.notification-msg').text("标题不能为空！");
            } else if ($content.length === 0) {
                $('.notification-msg').text("内容不能为空！");
            } else {
                $('.notification-msg').text("");
                var admin_id = storage.getItem("admin_id");
                if (admin_id==null) { //需重新登录
                    alert("身份信息已失效，请重新登录！");
                    window.location.href = base_url + "/pages/index.html";
                } else {
                    //向后台发送的数据
                    var postData = {
                        admin_id: admin_id,
                        title: $title,
                        content: $content
                    };
                    $.ajax({
                        url:base_url+'/user/tzsc_sendNotification',
                        data:JSON.stringify(postData),
                        type:"post",
                        //设置接收格式为JSON
                        dataType:"json",
                        //编码设置
                        contentType:"application/json;charset=utf-8",
                        success: function (msg) {
                            if(msg != null){
                                if (msg.code === 1) {
                                    $('.notification-msg').text("通知发布成功！");
                                } else {
                                    $('.notification-msg').text("通知发布失败，请重试");
                                }
                            } else{
                                $('.notification-msg').text("未知错误");
                            }
                        },
                        error: function (xhr) {
                            console.log(xhr.status);
                            alert("通知发布失败，请重试");
                        }
                    });
                }
            }
        });
    }

    //更新上一页、下一页按钮的显示状态
    function updatePageState(page, isSearchGoods) {
        if (isSearchGoods === false) { //查询所有商品的更新
            if (page == 1) {
                $('.page-a-last').addClass("page-a-disable");
            } else {
                $('.page-a-last').removeClass("page-a-disable");
            }
            if (page == $('.main').get(0).pageCount) {
                $('.page-a-next').addClass("page-a-disable");
            } else {
                $('.page-a-next').removeClass("page-a-disable");
            }
        } else {
            if (page == 1) {
                $('.page-a-last-search').addClass("page-a-disable");
            } else {
                $('.page-a-last-search').removeClass("page-a-disable");
            }
            if (page == $('.main').get(0).pageCount) {
                $('.page-a-next-search').addClass("page-a-disable");
            } else {
                $('.page-a-next-search').removeClass("page-a-disable");
            }
        }
    }

    function getGoodsPageCount() {
        $.ajax({
            url:base_url+'/goods/allGoodsCount',
            type:"get",
            //设置接收格式为JSON
            dataType:"json",
            //编码设置
            contentType:"application/json;charset=utf-8",
            success: function (msg) {
                $(".main").get(0).pageCount = msg.pageCount; //存储总页码
                $('.record-count').html(msg.count); //总记录数
                var $page = $(".page-container");
                $page.empty();
                for(var i = 0; i < msg.pageCount; i++){
                    var $a = $("<a class='pageCode' href=\"javascript:;\">"+(i+1)+"</a>");
                    if(i === 0){
                        $a.addClass("cur");
                    }
                    $page.append($a);
                }
            },
            error: function (xhr) {
                console.log(xhr.status);
                alert("获取页码失败，请重试");
            }
        });
    }

    function getGoodsList(page) {
        $.ajax({
            url:base_url+'/goods/allGoods',
            data:{
                pageCode: page
            },
            type:"get",
            //设置接收格式为JSON
            dataType:"json",
            //编码设置
            contentType:"application/json;charset=utf-8",
            success: function (msg) {
                $(".goodsList").empty();
                $.each(msg, function (key, value) {
                    // 根据内容创建节点
                    var $goods = createGoodsEle(value);
                    $goods.get(0).obj = value; //存储商品信息
                    // 插入商品
                    $(".goodsList").append($goods);
                });
            },
            error: function (xhr) {
                console.log(xhr.status);
                alert("获取商品失败，请重试");
            }
        });
    }

    function getSearchGoodsListByName(page, name) {
        $.ajax({
            url:base_url+'/goods/searchGoodsByName',
            data:{
                g_name: name,
                pageCode: page
            },
            type:"get",
            //设置接收格式为JSON
            dataType:"json",
            //编码设置
            contentType:"application/json;charset=utf-8",
            success: function (msg) {
                var $goodsList = $(".goodsList");
                $goodsList.empty();
                $.each(msg.goodsList, function (key, value) {
                    // 根据内容创建节点
                    var $goods = createGoodsEle(value);
                    $goods.get(0).obj = value; //存储商品信息
                    // 插入商品
                    $(".goodsList").append($goods);
                });
            },
            error: function (xhr) {
                console.log(xhr.status);
                alert("获取商品失败，请重试");
            }
        });
    }

    // 创建商品分类节点方法
    function createTypeEle(obj) {
        var $type = $('<option>' + obj.t_name + '</option>');
        return $type;
    }

    // 创建商品节点方法
    function createGoodsEle(obj) {
        var state;
        if (obj.g_state===0) {
            state = "上架中";
        } else if (obj.g_state===1){
            state = "卖出下架";
        } else if (obj.g_state===3){
            state = "管理员下架";
        } else if (obj.g_state===5){
            state = "新订单生成下架";
        } else {
            state = "卖家下架";
        }
        var $goods;
        if (obj.g_state===0) {
            $goods = $("<div class=\"goods clearf\" style=\"float: left;\">\n" +
                "                <form>\n" +
                "                    <div class=\"top fl\" style=\"font-size: 10px; padding-top: 5px\">\n" +
                "                        <label style=\"width: 10%\">ID:</label><label id=\"goods-id\">"+obj.g_id+"</label>\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-photo fl\">\n" +
                "                        <img src=\""+base_pic_url+obj.g_pic1+"\" alt=\"\" id=photo width=\"80px\" height=\"80px\" style=\"margin-top: 25px;\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-name fl\">\n" +
                "                        <label for=\"name\">名称：</label>\n" +
                "                        <input id=\"name\" value=\""+obj.g_name+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-type fl\">\n" +
                "                        <label for=\"type\">分类：</label>\n" +
                "                        <input id=\"type\" value=\""+obj.g_t_name+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-price fl\">\n" +
                "                        <label for=\"price\">价格：</label>\n" +
                "                        <input id=\"price\" value=\"￥"+obj.g_price+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-state fl\">\n" +
                "                        <label for=\"state\">状态：</label>\n" +
                "                        <input id=\"state\" value=\""+state+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-uid fl\">\n" +
                "                        <label for=\"uid\" style=\"width: 25%\">用户id：</label>\n" +
                "                        <input id=\"uid\" value=\""+obj.g_u_id+"\" type=\"text\" disabled=\"disabled\" style=\"width: 75%\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-uname fl\">\n" +
                "                        <label for=\"uname\">昵称：</label>\n" +
                "                        <input id=\"uname\" value=\""+obj.g_u_nick+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-desc fl\">\n" +
                "                        <label for=\"desc\">描述：</label>\n" +
                "                        <textarea id=\"desc\" cols=\"20\" rows=\"2\"\n" +
                "                                                      disabled=\"disabled\"\n" +
                "                                                      maxlength=\"200\"\n" +
                "                                                      onchange=\"this.value=this.value.substring(0, 200)\"\n" +
                "                                                      onkeydown=\"this.value=this.value.substring(0, 200)\"\n" +
                "                                                      onkeyup=\"this.value=this.value.substring(0, 200)\">"+obj.g_desc+"</textarea>\n" +
                "                    </div>\n" +
                "                    <div class=\"bottom fl\">\n" +
                "                        <label for=\"updateTime\">最后更新于：</label>\n" +
                "                        <input id=\"updateTime\" value=\""+formartDate(obj.g_updateTime)+"\">\n" +
                "                        <a class=\"delete_goods\" href=\"javascript:;\">\n" +
                "                            <img src=\"img/delete.png\" alt=\"删除\" class=\"fr\">\n" +
                "                        </a>\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </div>");
        } else {
            $goods = $("<div class=\"goods clearf\" style=\"float: left;\">\n" +
                "                <form>\n" +
                "                    <div class=\"top fl\" style=\"font-size: 10px; padding-top: 5px\">\n" +
                "                        <label style=\"width: 10%\">ID:</label><label id=\"goods-id\">"+obj.g_id+"</label>\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-photo fl\">\n" +
                "                        <img src=\""+base_pic_url+obj.g_pic1+"\" alt=\"\" id=photo width=\"80px\" height=\"80px\" style=\"margin-top: 25px;\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-name fl\">\n" +
                "                        <label for=\"name\">名称：</label>\n" +
                "                        <input id=\"name\" value=\""+obj.g_name+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-type fl\">\n" +
                "                        <label for=\"type\">分类：</label>\n" +
                "                        <input id=\"type\" value=\""+obj.g_t_name+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-price fl\">\n" +
                "                        <label for=\"price\">价格：</label>\n" +
                "                        <input id=\"price\" value=\"￥"+obj.g_price+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-state fl\">\n" +
                "                        <label for=\"state\">状态：</label>\n" +
                "                        <input id=\"state\" value=\""+state+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-uid fl\">\n" +
                "                        <label for=\"uid\" style=\"width: 25%\">用户id：</label>\n" +
                "                        <input id=\"uid\" value=\""+obj.g_u_id+"\" type=\"text\" disabled=\"disabled\" style=\"width: 75%\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-uname fl\">\n" +
                "                        <label for=\"uname\">昵称：</label>\n" +
                "                        <input id=\"uname\" value=\""+obj.g_u_nick+"\" type=\"text\" disabled=\"disabled\">\n" +
                "                    </div>\n" +
                "                    <div class=\"goods-desc fl\">\n" +
                "                        <label for=\"desc\">描述：</label>\n" +
                "                        <textarea id=\"desc\" cols=\"20\" rows=\"2\"\n" +
                "                                                      disabled=\"disabled\"\n" +
                "                                                      maxlength=\"200\"\n" +
                "                                                      onchange=\"this.value=this.value.substring(0, 200)\"\n" +
                "                                                      onkeydown=\"this.value=this.value.substring(0, 200)\"\n" +
                "                                                      onkeyup=\"this.value=this.value.substring(0, 200)\">"+obj.g_desc+"</textarea>\n" +
                "                    </div>\n" +
                "                    <div class=\"bottom fl\">\n" +
                "                        <label for=\"updateTime\">最后更新于：</label>\n" +
                "                        <input id=\"updateTime\" value=\""+formartDate(obj.g_updateTime)+"\">\n" +
                "                        <a class=\"delete_goods display-none\" href=\"javascript:;\">\n" +
                "                            <img src=\"img/delete.png\" alt=\"删除\" class=\"fr\">\n" +
                "                        </a>\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </div>");
        }
        return $goods;
    }
    // 生成时间方法
    function formartDate(time) {
        var date = new Date(time);
        // 2018-4-3 21:30:23
        var arr = [date.getFullYear() + "-",
            date.getMonth() + 1 + "-",
            date.getDate() + " ",
            date.getHours() + ":",
            (date.getMinutes()>9?date.getMinutes():"0"+date.getMinutes()) + ":",
            date.getSeconds()];
        return arr.join("");
    }
});