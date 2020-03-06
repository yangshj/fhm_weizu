<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">

    <meta charset="utf-8" />
    <title></title>

    <meta name="description" content="overview & stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="static/css/bootstrap.min.css" rel="stylesheet" />
    <link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="static/css/font-awesome.min.css" />
    <!-- 下拉框 -->
    <link rel="stylesheet" href="static/css/chosen.css" />

    <link rel="stylesheet" href="static/css/ace.min.css" />
    <link rel="stylesheet" href="static/css/ace-responsive.min.css" />
    <link rel="stylesheet" href="static/css/ace-skins.min.css" />

    <link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>

    <script type="text/javascript">

        $(top.hangge());

        //保存
        function save(){
            if($("#code").val()==""){
                $("#code").tips({
                    side:3,
                    msg:'输入编码',
                    bg:'#AE81FF',
                    time:3
                });
                $("#code").focus();
                return false;
            }

            if($("#id").val()==""){
                hasU();
            }else{
                $("#userForm").submit();
                $("#zhongxin").hide();
                $("#zhongxin2").show();
            }
        }

        //判断用户名是否存在
        function hasU(){
            var code = $("#code").val();
            $.ajax({
                type: "POST",
                url: '<%=basePath%>weizu/fund/hasU.do',
                data: {code:code,tm:new Date().getTime()},
                dataType:'json',
                cache: false,
                success: function(data){
                    if("success" == data.result){
                        $("#userForm").submit();
                        $("#zhongxin").hide();
                        $("#zhongxin2").show();
                    }else{
                        $("#code").css("background-color","#D16E6C");
                        setTimeout("$('#code').val('此编码已存在!')",500);
                    }
                }
            });
        }

    </script>
</head>
<body>
<form action="weizu/fund/${path}.do" name="userForm" id="userForm" method="post">
    <input type="hidden" name="id" id="id" value="${bean.id}"/>
    <div id="zhongxin">
        <table>
            <tr>
                <td><input type="text" name="code" id="code" value="${bean.code}" placeholder="编码" title="编码"/></td>
            </tr>
            <tr>
                <td><input type="text" name="name" id="name"  value="${bean.name}" placeholder="名称" title="名称" /></td>
            </tr>
            <tr>
                <td><input type="text" name="type" id="type"  value="${bean.type}" placeholder="类型" title="类型" /></td>
            </tr>
            <tr>
                <td><input type="text" name="manager" id="manager"  value="${bean.manager}" placeholder="基金经理" title="基金经理" /></td>
            </tr>
            <tr>
                <td><input type="text" name="fundScale" id="fundScale" value="${bean.fundScale}" placeholder="基金规模" title="基金规模"/></td>
            </tr>
            <tr>
                <td><input type="text" name="lastMonthGrowth" id="lastMonthGrowth"  value="${bean.lastMonthGrowth}" placeholder="最近一月" title="最近一月" /></td>
            </tr>
            <tr>
                <td><input type="text" name="lastThreeMonthGrowth" id="lastThreeMonthGrowth"  value="${bean.lastThreeMonthGrowth}" placeholder="最近三月" title="最近三月" /></td>
            </tr>
            <tr>
                <td><input type="text" name="lastSixMonthGrowth" id="lastSixMonthGrowth"  value="${bean.lastSixMonthGrowth}" placeholder="最近六月" title="最近六月" /></td>
            </tr>
            <tr>
                <td><input type="text" name="lastYearGrowth" id="lastYearGrowth"  value="${bean.lastYearGrowth}" placeholder="最近一年" title="最近一年" /></td>
            </tr>
            <tr>
                <td><input type="text" name="buyMin" id="buyMin"  value="${bean.buyMin}" placeholder="最少买入" title="最少买入" /></td>
            </tr>
            <tr>
                <td><input type="text" name="buyRate" id="buyRate"  value="${bean.buyRate}" placeholder="买入费率" title="买入费率" /></td>
            </tr>
            <tr>
                <td class="center" colspan="2">
                    <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                    <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
</form>


<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript">
    $(function() {
        //单选框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});
        //日期框
        $('.date-picker').datepicker();

    });
</script>

</body>
</html>