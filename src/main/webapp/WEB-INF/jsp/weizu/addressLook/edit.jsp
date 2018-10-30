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
		if($("#userName").val()==""){
			$("#userName").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#userName").focus();
			return false;
		}
		
		/* 
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		
		*/
		
		
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
		var userName = $("#userName").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>weizu/addressLook/hasU.do',
	    	data: {userName:userName,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				 }else{
					$("#userName").css("background-color","#D16E6C");
					setTimeout("$('#userName').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
</script>
	</head>
	<body>
		<form action="weizu/addressLook/${path}.do" name="userForm" id="userForm" method="post">
			<input type="hidden" name="id" id="id" value="${bean.id}"/>
			<div id="zhongxin">
				<table>
					<tr>
						<td><input type="text" name="userName" id="userName" value="${bean.userName}" placeholder="用户名" title="用户名"/></td>
					</tr>
					<tr>
						<td><input type="text" name="mobilePhone" id="mobilePhone"  value="${bean.mobilePhone}" placeholder="手机号" title="手机号" /></td>
					</tr>
					<tr>
						<td>
							<select name="sex" title="性别">
								<option value="1" <c:if test="${bean.sex == '1' }">selected</c:if> >男</option>
								<option value="2" <c:if test="${bean.sex == '2' }">selected</c:if> >女</option>
							</select>
						</td>
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