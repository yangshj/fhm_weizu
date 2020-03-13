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
    <!-- jsp文件头和头部 -->
    <%@ include file="../../system/admin/top.jsp"%>
    <!-- 引入 echarts.js -->
    <script src="https://cdn.staticfile.org/echarts/4.3.0/echarts.min.js"></script>
</head>
<body>


<div class="container-fluid" id="main-container">


    <div id="page-content" class="clearfix">

        <div class="row-fluid">

            <div class="row-fluid">

                <!-- 检索  -->
                <form action="weizu/fundCharts/list.do" method="post" name="userForm" id="userForm">
                    <table border='0'>
                        <tr>
                            <td>
                                <select class="chosen-select" id="fundId" name="fundId" data-placeholder="请选择……">
                                    <option value=""></option>
                                    <c:forEach items="${fundList}" var="fund">
                                        <option value="${fund.id}"  <c:if test="${pd.fundId == fund.id}">selected</c:if> >${fund.name}</option>
                                    </c:forEach>
                                </select>
                            </td>

                            <td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
                            <td><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
                            <td style="vertical-align:top;">
                                <select class="chzn-select" name="STATUS" id="STATUS" data-placeholder="状态" style="vertical-align:top;width: 79px;">
                                    <option value=""></option>
                                    <option value="">全部</option>
                                    <option value="1" <c:if test="${pd.STATUS == '1' }">selected</c:if> >正常</option>
                                    <option value="0" <c:if test="${pd.STATUS == '0' }">selected</c:if> >冻结</option>
                                </select>
                            </td>
                            <td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
                            <c:if test="${QX.cha == 1 }">
                                <td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
                            </c:if>
                        </tr>
                    </table>
                    <!-- 检索  -->
                </form>

                <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                <div id="main" style="width: 800px;height:450px;"></div>
                <input id="fundCharts" type="hidden" value="${dataList}">
            </div>

            <!-- PAGE CONTENT ENDS HERE -->
        </div><!--/row-->

    </div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->

<!-- 返回顶部  -->
<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
    <i class="icon-double-angle-up icon-only"></i>
</a>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>

<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript">
    $(top.hangge());
    //检索
    function search(){
        top.jzts();
        $("#userForm").submit();
    }

    $(function() {

        // 主表的choose事件
        $(".chosen-select").chosen();
        $(".chosen-select").chosen({allow_single_deselect:true});

        //下拉框
        $(".chzn-select").chosen();
        $(".chzn-select-deselect").chosen({allow_single_deselect:true});

        //日期框
        $('.date-picker').datepicker();

        //复选框
        $('table th input:checkbox').on('click' , function(){
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                .each(function(){
                    this.checked = that.checked;
                    $(this).closest('tr').toggleClass('selected');
                });

        });

    });


</script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    data = JSON.parse($("#fundCharts").val());
    var dateList = data.map(function (item) {
        return item[0];
    });
    var valueList = data.map(function (item) {
        return item[1];
    });

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            type: 'category',
            boundaryGap: false,
            data: dateList
        },
        yAxis: {
            type: 'value'
        },
        ///用于添加框选缩放功能
        toolbox: {
            show: true,
            feature:{
                dataZoom:
                    {
                        realtime: false,
                        yAxisIndex: 'none',
                    },
                restore: {},
            }
        },
        dataZoom:[
                {type: 'inside'},   //用于添加滚轮缩放
                {type:'slider' },  //用于添加滑动条缩放，
        ],
        series: [{
            name:'本基金',
            data: valueList,
            type: 'line'
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
</body>
</html>

