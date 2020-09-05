/**
 * 互动模块
 */
layui.define('fly', function(exports) {

    var $ = layui.jquery;
    var layer = layui.layer;
    var util = layui.util;
    var laytpl = layui.laytpl;
    var form = layui.form;
    var fly = layui.fly;

    var gather = {}, dom = {

    };

    gather.interact = {
        toFollow:function(div){
            layer.msg("关注");
        }

        ,sendMessage:function(div){
            layer.msg("发送消息");
        }
    };

    exports('interaction', null);
});