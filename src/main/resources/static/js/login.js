layui.use(['form','layer','jquery'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
        $ = layui.jquery;

    // $(".loginBody .seraph").click(function(){
    //     layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
    //         time:5000
    //     });
    // })

    //登录按钮
    // form.on("submit(login)",function(data){
    //
    //     $(this).text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
    //
    //     var data={};
    //     data["username"]=$("#username").val();
    //     data["password"]=$("#password").val();
    //     data["plumCKRM"]=$("#code").val();
    //     console.log(JSON.stringify(data));
    //     $.ajax({
    //         type: "POST",
    //         url: "login",
    //         contentType: "application/json; charset=utf-8",
    //         data: JSON.stringify(data),
    //         dataType: "json",
    //         success: function (message) {
    //             if (message > 0) {
    //                 alert("请求已提交！我们会尽快与您取得联系");
    //             }
    //         },
    //         error: function (message) {
    //             $("#request-process-patent").html("提交数据失败！");
    //         }
    //
    //     })
    //
    //     setTimeout(function(){
    //         window.location.href = "/login";
    //     },1000);
    //     return false;
    // })

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
})
