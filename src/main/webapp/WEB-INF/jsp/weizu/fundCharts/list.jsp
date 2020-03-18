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
<style type="text/css">
    .myTable-container {
        width: calc(100vw - 400px);
        height: 530px;
    }
</style>
<body>


<div class="container-fluid" id="main-container">


    <div id="page-content" class="clearfix">

            <div class="row-fluid">
                <!-- 检索  -->
                <form action="weizu/fundCharts/list.do" method="post" name="userForm" id="userForm">
                    <table border='0' class="table table-striped table-bordered table-hover">
                        <tr style="width:100%">
                            <td>
                                <select class="chosen-select" style="width:95%" id="fundId" name="fundId" data-placeholder="请选择……">
                                    <option value=""></option>
                                    <c:forEach items="${fundList}" var="fund">
                                        <option value="${fund.id}"  <c:if test="${pd.fundId == fund.id}">selected</c:if> >${fund.name}</option>
                                    </c:forEach>
                                </select>
                                <button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button>
                            </td>

                        </tr>
                    </table>
                    <!-- 检索  -->
                </form>
            </div>
                <div class="pull-left" style="padding-top: 5%; ">
                    <table id="table_report" class="table table-striped table-bordered table-hover">
                        <tbody>
                            <tr>
                                <td>基金名称</td>
                                <td>${fund.name }</td>
                            </tr>
                            <tr>
                                <td>编码</td>
                                <td>${fund.code}</td>
                            </tr>
                            <tr>
                                <td>类型</td>
                                <td>${fund.type}</td>
                            </tr>
                            <tr>
                                <td>基金经理</td>
                                <td>${fund.manager }</td>
                            </tr>
                            <tr>
                                <td>基金规模</td>
                                <td>${fund.fundScale }</td>
                            </tr>
                            <tr>
                                <td>最近一月</td>
                                <td>${fund.lastMonthGrowth }</td>
                            </tr>
                            <tr>
                                <td>最近三月</td>
                                <td>${fund.lastThreeMonthGrowth }</td>
                            </tr>
                            <tr>
                                <td>最近六月</td>
                                <td>${fund.lastSixMonthGrowth }</td>
                            </tr>
                            <tr>
                                <td>最近一年</td>
                                <td>${fund.lastYearGrowth }</td>
                            </tr>
                            <tr>
                                <td>最少买入</td>
                                <td>${fund.buyMin }</td>
                            </tr>
                            <tr>
                                <td>买入费率</td>
                                <td>${fund.buyRate }</td>
                            </tr>
                            <tr>
                                <td>期望日期</td>
                                <td>${lastNetWorth.expectWorthDate }</td>
                            </tr>
                            <tr>
                                <td>期望净值</td>
                                <td>${lastNetWorth.expectWorth }</td>
                            </tr>
                            <tr>
                                <td>期望增长率</td>
                                <td>${lastNetWorth.expectGrowth }</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="pull-right" style="padding-top: 5%;">
                    <ul id="myTab" class="nav nav-tabs">
                        <li class="active"><a href="#tab1" data-toggle="tab" >近一个月</a></li>
                        <li><a href="#tab2" data-toggle="tab">近三个月</a></li>
                        <li><a href="#tab3" data-toggle="tab">近六个月</a></li>
                        <li><a href="#tab4" data-toggle="tab">近一年</a></li>
                        <li ><a href="#tab5" data-toggle="tab">近三年</a></li>
                    </ul>

                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane active" id="tab1">
                            <div id="tab1-container" class="myTable-container"></div>
                        </div>
                        <div class="tab-pane " id="tab2">
                            <div id="tab2-container" class="myTable-container"></div>
                        </div>
                        <div class="tab-pane " id="tab3">
                            <div id="tab3-container" class="myTable-container"></div>
                        </div>
                        <div class="tab-pane " id="tab4">
                            <div id="tab4-container" class="myTable-container"></div>
                        </div>
                        <div class="tab-pane  " id="tab5">
                            <div id="tab5-container" class="myTable-container"></div>
                        </div>
                    </div>
                </div>

                <input id="lastMonthList" type="hidden" value="${lastMonthList}">
                <input id="lastThreeList" type="hidden" value="${lastThreeList}">
                <input id="lastSixList" type="hidden" value="${lastSixList}">
                <input id="lastYearList" type="hidden" value="${lastYearList}">
                <input id="lastThreeYearList" type="hidden" value="${lastThreeYearList}">

            </div>

            <!-- PAGE CONTENT ENDS HERE -->
    </div><!--/row-->

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

    chartInit("tab1-container","lastMonthList");
    chartInit("tab2-container","lastThreeList");
    chartInit("tab3-container","lastSixList");
    chartInit("tab4-container","lastYearList");
    chartInit("tab5-container","lastThreeYearList");

    // 自动调整宽度
    window.addEventListener("resize",function (){
        var width = window.innerWidth;
        $("#tab1-container").css('width',(width-400))
        $("#tab2-container").css('width',(width-400))
        $("#tab3-container").css('width',(width-400))
        $("#tab4-container").css('width',(width-400))
        $("#tab5-container").css('width', (width-400))
    });

    function chartInit(divId,  dataListId) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById(divId));
        var data = JSON.parse($("#"+dataListId).val());
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
                scale: true, // y轴数据,根据数据的最大最小之进行计算
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
    }
</script>
</body>
</html>

