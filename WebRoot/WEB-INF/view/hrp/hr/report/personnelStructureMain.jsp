<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,grid,pageOffice" name="plugins" />
    </jsp:include>
    <style>
        #query,
        #print {
            margin-left: 30px;
        }
        .msg-container {
            border-top: 1px solid #bed5f3;
        }
        .msg-title {
            text-align: center;
            font-size: 14px;
        }
        .msg-grid {
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            padding: 0 10px;
        }
        .msg-unit {
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
        }
        .msg-date {
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
            text-align: right;
        }
    </style>
    <script>
        var grid;
        var initFrom = function () {
            hostime = $("#hostime").etDatepicker({
                range: true,
                defaultDate: ['yyyy-mm-fd', 'yyyy-mm-ed'],
                onChange: function (date) {
                    var preDate = date[0] || '';
                    var endDae = date[1] || '';
                    $(".msg-date").text('日期：' + preDate + ' 至 '+ endDae);
                }
            });

            $("#query").click(query)
            $("#print").click(print)
        };
        var query = function () {
            var params = [
                { name: 'hostime', value: hostime.getValue()[0] || ''  },
                { name: 'endtime', value: hostime.getValue()[1] || ''  }
            ];
            grid.loadData(params,'queryHrPersonnelStructure.do?isCheck=false');
        };
        var print = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
            		]  */}; 
        	var printPara={
                title: " 人员结构表打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.report.HosPersonnelStructureService",
                method_name: "queryPersonnelStructureByPrint",
                bean_name: "hosPersonnelStructureService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        	
        	
        };
        var initGrid = function () {
            var columns = [
                { display: '单位：人', name: 'kind_name', width: 300,  },
                { display: '人数', name: 'kind_num', width: 300,  },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
              /*   dataModel: {
                    url: ''
                }, */
                columns: columns
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initGrid();
            
            $(".msg-unit").text('编制单位：' + parent.sessionJson.hospital);
        })
    </script>
</head>

<body>
    <table class="table-layout">
        <tr>
            <td class="label">统计日期：</td>
            <td class="ipt">
                <input id="hostime" type="text" />
            </td>

            <td class="label"></td>
            <td class="ipt" style="width:60%;text-align:right;">
                <button id="query">查询</button>
                <button id="print">打印</button>
            </td>
        </tr>
    </table>
    <div class="msg-container">
        <h2 class="msg-title">人员结构</h2>
        <div class="msg-grid">
            <span class="msg-unit"></span>
            <span class="msg-date"></span>
        </div>
    </div>
    <div id="mainGrid"></div>
</body>

</html>