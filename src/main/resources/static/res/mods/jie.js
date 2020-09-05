/**

 @Name: 求解板块

 */
 
layui.define('fly', function(exports){

  var $ = layui.jquery;
  var layer = layui.layer;
  var util = layui.util;
  var laytpl = layui.laytpl;
  var form = layui.form;
  var fly = layui.fly;
  
  var gather = {}, dom = {
    jieda: $('#jieda')
    ,content: $('#L_content')
    ,jiedaCount: $('#jiedaCount')
  };

  //监听专栏选择
  form.on('select(column)', function(obj){
    var value = obj.value
    ,elemQuiz = $('#LAY_quiz')
    ,tips = {
      tips: 1
      ,maxWidth: 250
      ,time: 10000
    };
    elemQuiz.addClass('layui-hide');
    if(value === '0'){
      layer.tips('下面的信息将便于您获得更好的答案', obj.othis, tips);
      elemQuiz.removeClass('layui-hide');
    } else if(value === '99'){
      layer.tips('系统会对【分享】类型的帖子予以飞吻奖励，但我们需要审核，通过后方可展示', obj.othis, tips);
    }
  });

  //提交回答
  fly.form['/jie/reply/'] = function(data, required){
    var tpl = '<li>\
      <div class="detail-about detail-about-reply">\
        <a class="fly-avatar" href="/u/{{ layui.cache.user.uid }}" target="_blank">\
          <img src="{{= d.user.avatar}}" alt="{{= d.user.username}}">\
        </a>\
        <div class="fly-detail-user">\
          <a href="/u/{{ layui.cache.user.uid }}" target="_blank" class="fly-link">\
            <cite>{{d.user.username}}</cite>\
          </a>\
        </div>\
        <div class="detail-hits">\
          <span>刚刚</span>\
        </div>\
      </div>\
      <div class="detail-body jieda-body photos">\
        {{ d.content}}\
      </div>\
    </li>'
    data.content = fly.content(data.content);
    laytpl(tpl).render($.extend(data, {
      user: layui.cache.user
    }), function(html){
      required[0].value = '';
      dom.jieda.find('.fly-none').remove();
      dom.jieda.append(html);

      var count = dom.jiedaCount.text()|0;
      dom.jiedaCount.html(++count);
    });
  };

  //求解管理
  gather.jieAdmin = {
    //删求解
    del: function(div){
      layer.confirm('删除后无法恢复，确认删除？', function(index){
        layer.close(index);

        // fly.json('/post/delete/', {
        //   id: div.data('id')
        // }, function(res){
        //   if(res.status === 0){
        //     location.href = '/';
        //   } else {
        //     layer.msg(res.msg);
        //   }
        // });

        var map={};
        map["id"]=div.data('id');
        var dataJson=JSON.stringify(map);
        $.ajax({
          url:'/post/delete/',
          data:dataJson,
          method:"POST",
          contentType : "application/json;charset=UTF-8",//必须
          dataType:"json",
          success:function(data){
            if(data.status == 0){
              location.href = '/';
            } else {
              layer.msg(data.msg);
            }
          },
          error:function(data){
            layer.msg("服务器繁忙，请稍后再试");
          }
        });
      });
    }

    ,zan: function(li){ //赞
      var othis = $(this), ok = othis.hasClass('zanok');

      // fly.json('/post/like/', {
      //   ok: ok
      //   ,id: li.data('id')
      // }, function(res){
      //   if(res.status === '0'){
      //     var zans = othis.find('em').html()|0;
      //     othis[ok ? 'removeClass' : 'addClass']('zanok');
      //     othis.find('em').html(ok ? (--zans) : (++zans));
      //   } else {
      //     layer.msg(res.msg);
      //   }
      // });

      var map={};
      map["id"]=li.data('id');
      map["ok"]=ok;
      var dataJson=JSON.stringify(map);
      $.ajax({
        url:'/post/like/',
        data:dataJson,
        method:"post",
        contentType : "application/json;charset=UTF-8",//必须
        dataType:"json",
        success:function(data){
          if(data.status == '0'){
            var zans = othis.find('em').html()|0;
            othis[ok ? 'removeClass' : 'addClass']('zanok');
            othis.find('em').html(ok ? (--zans) : (++zans));
          } else {
            layer.msg(data.msg);
          }
        },
        error:function(data){
          layer.msg("服务器繁忙，请稍后再试");
        }
      });
    }

    //设置置顶、状态
    ,set: function(div){
      var othis = $(this);
      fly.json('/api/jie-set/', {
        id: div.data('id')
        ,rank: othis.attr('rank')
        ,field: othis.attr('field')
      }, function(res){
        if(res.status === 0){
          location.reload();
        }
      });
    }

    //收藏
    ,collect: function(div){
      var othis = $(this), type = othis.data('type');

      // fly.json('/treasure/'+ type +'/', {
      //   id: div.data('id')
      // }, function(res){
      //   console.log(type);
      //   if(type === 'add'){
      //     if (res.status === "0") {
      //       layui.msg("收藏成功");
      //       othis.data('type', 'undo').html('取消收藏').addClass('layui-btn-danger');
      //     }else if (res.status === "1") {
      //       layui.msg(res.msg);
      //     }
      //   } else if(type === 'undo'){
      //     if (res.status === "0") {
      //       layui.msg("取消收藏成功");
      //       othis.data('type', 'add').html('收藏').removeClass('layui-btn-danger');
      //     }else if (res.status === "1") {
      //       layui.msg(res.msg);
      //     }
      //   }
      // });

      var map={};
      map["id"]=div.data('id');
      var dataJson=JSON.stringify(map);
      $.ajax({
        url:'/treasure/'+ type +'/',
        data:dataJson,
        method:"post",
        contentType : "application/json;charset=UTF-8",//必须
        dataType:"json",
        success:function(data){
          if(type == 'add'){
            if (data.status == "0") {
              layer.msg("收藏成功");
              othis.data('type', 'undo').html('取消收藏').addClass('layui-btn-danger');
            }else if (data.status == "1") {
              layer.msg(data.msg);
            }
          } else if(type == 'undo'){
            if (data.status == "0") {
              layer.msg("取消收藏成功");
              othis.data('type', 'add').html('收藏').removeClass('layui-btn-danger');
            }else if (data.status == "1") {
              layer.msg(data.msg);
            }
          }
        },
        error:function(data){
          layer.msg("服务器繁忙，请稍后再试");
        }
      });
    }
  };

  $('body').on('click', '.jie-admin', function(){
    var othis = $(this), type = othis.attr('type');
    gather.jieAdmin[type] && gather.jieAdmin[type].call(this, othis.parent());
  });

  //异步渲染
  var asyncRender = function(){
    var div = $('.fly-admin-box'), jieAdmin = $('#LAY_jieAdmin');
    //查询帖子是否收藏
    if(jieAdmin[0] && layui.cache.user.uid != -1){
      fly.json('/collection/find/', {
        cid: div.data('id')
      }, function(res){
        jieAdmin.append('<span class="layui-btn layui-btn-xs jie-admin '+ (res.data.collection ? 'layui-btn-danger' : '') +'" type="collect" data-type="'+ (res.data.collection ? 'remove' : 'add') +'">'+ (res.data.collection ? '取消收藏' : '收藏') +'</span>');
      });
    }
  }();

  //解答操作
  gather.jiedaActive = {
    // zan: function(li){ //赞
    //   var othis = $(this), ok = othis.hasClass('zanok');
    //   fly.json('/api/jieda-zan/', {
    //     ok: ok
    //     ,id: li.data('id')
    //   }, function(res){
    //     if(res.status === 0){
    //       var zans = othis.find('em').html()|0;
    //       othis[ok ? 'removeClass' : 'addClass']('zanok');
    //       othis.find('em').html(ok ? (--zans) : (++zans));
    //     } else {
    //       layer.msg(res.msg);
    //     }
    //   });
    // }
    reply: function(li){ //回复
      var val = dom.content.val();
      var aite = '@'+ li.find('.fly-detail-user cite').text().replace(/\s/g, '');
      dom.content.focus()
      if(val.indexOf(aite) !== -1) return;
      dom.content.val(aite +' ' + val);
    }
    ,accept: function(li){ //采纳
      var othis = $(this);
      layer.confirm('是否采纳该回答为最佳答案？', function(index){
        layer.close(index);
        fly.json('/api/jieda-accept/', {
          id: li.data('id')
        }, function(res){
          if(res.status === 0){
            $('.jieda-accept').remove();
            li.addClass('jieda-daan');
            li.find('.detail-about').append('<i class="iconfont icon-caina" title="最佳答案"></i>');
          } else {
            layer.msg(res.msg);
          }
        });
      });
    }
    ,edit: function(li){ //编辑
      fly.json('/jie/getDa/', {
        id: li.data('id')
      }, function(res){
        var data = res.rows;
        layer.prompt({
          formType: 2
          ,value: data.content
          ,maxlength: 100000
          ,title: '编辑回帖'
          ,area: ['728px', '300px']
          ,success: function(layero){
            fly.layEditor({
              elem: layero.find('textarea')
            });
          }
        }, function(value, index){
          fly.json('/jie/updateDa/', {
            id: li.data('id')
            ,content: value
          }, function(res){
            layer.close(index);
            li.find('.detail-body').html(fly.content(value));
          });
        });
      });
    }
    ,del: function(li){ //删除
      layer.confirm('确认删除该回答么？', function(index){
        layer.close(index);

        // fly.json('/answer/delete/', {
        //   id: li.data('id')
        // }, function(res){
        //   if(res.status == 0){
        //     var count = dom.jiedaCount.text()|0;
        //     dom.jiedaCount.html(--count);
        //     li.remove();
        //     //如果删除了最佳答案
        //     if(li.hasClass('jieda-daan')){
        //       $('.jie-status').removeClass('jie-status-ok').text('求解中');
        //     }
        //   } else {
        //     layer.msg(res.msg);
        //   }
        // });

        var map={};
        map["id"]=li.data('id');
        var dataJson=JSON.stringify(map);
        $.ajax({
          url:'/answer/delete/',
          data:dataJson,
          method:"post",
          contentType : "application/json;charset=UTF-8",//必须
          dataType:"json",
          success:function(data){
            if(data.status == 0){
              var count = parseInt($("#answer_count").text());
              $("#answer_count").text(--count);
              li.remove();
            } else {
              layer.msg(data.msg);
            }
          },
          error:function(data){
            layer.msg("服务器繁忙，请稍后再试");
          }
        });
      });    
    }
  };

  $('.jieda-reply span').on('click', function(){
    var othis = $(this), type = othis.attr('type');
    gather.jiedaActive[type].call(this, othis.parents('li'));
  });


  //定位分页
  if(/\/page\//.test(location.href) && !location.hash){
    var replyTop = $('#flyReply').offset().top - 80;
    $('html,body').scrollTop(replyTop);
  }

  exports('jie', null);
});