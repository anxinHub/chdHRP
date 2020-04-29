<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="hr,dialog,datepicker,select,validate,grid,dateSlider,characterList,tree,tab,from,pageOffice" name="plugins" />
</jsp:include>

<!-- 日历   --> 
<link rel="stylesheet" href="<%=path%>/lib/calendar/calendar.css">
<script src="<%=path%>/lib/calendar/index.js"></script>

<style >
	*{
		margin:0;
		padding:0;
	}
    html,body {
        height: 100%;
        min-width: 900px
	}

    .grid_header .float_left {
        float: left;
        padding: 5px 10px;
    }

    .grid_header .float_right {
        float: right;
        padding: 5px 10px;
    }

    .main_title {
        background-color: #ededed;
        padding: 2px 10px;
        position: fixed;
        z-index: 99;
        width: 100%;
    }

    .container {
    	width:100%;
    	height:100%;
        padding-top: 0px;
    }
    
    .tab_content {
        text-align: center;
        position: absolute;
        left: 198px;
    	top: 30px;
        /* overflow: hidden */
    }
    
    .tab_content h3 {
    	font-size: 20px;
    }
    
    #dateSlider,#person_dateSlider {
    	 font: bold 4em/273px "微软雅黑", Verdana, Arial, Helvetica, sans-serif;
    }

    /* tab切换铺满 */

    .ettab-container {
        width: 200px;
        height: 100%;
        display: -webkit-flex;
        display: -ms-flex;
        display: flex;
        box-sizing:border-box;
    }

    .ettab-nav {
        padding: 0;
        margin: 0;
    }

    .ettab-nav .ettab-tab {
        padding: 0;
        margin: 0;
        width: 50%;
        box-sizing: border-box;
        height: 100%;
        text-align: center
    }
    .ettab-content {
        height: 95%;
        height: -moz-calc(100% - 28px);
        height: -webkit-calc(100% - 28px);
        height: calc(100% - 28px);
    }
    .ettab-content .ettab-panel {
        padding: 0;
    }

   /* tab样式覆盖 */

	.ettab-content .ettab-panel.ettab-active {
		display: flex;
        flex-direction: column;
	} 


	#leftGrid{
		width: 100%;
	}
	#divLeft {
		width: 25%;
    	float: left;
   }
	#calendar {
	    width: 75%;
	    height: 100%;
	    float: left;
	    padding: 10px;
	}
</style>
<script>
	
	var hos_name,dateSilder,person_dateSlider,characterList;
	var currentDateList = []; // 当前列头所展示的所有日期列表

	$(function () {
        leftGrid();
    	 initForm();
 
         initTree();
         // 初始化标签
         tab = $("#tab").etTab();
         $('.tab_content').width($(window).width() - $('.ettab-container').width());
         $('#divLeft').height($(window).height()-90);
    });
        
	function initForm() {
		
		/*  hos_name = $("#hos_name").etSelect({
			url: '../../queryHosInfoSelect.do?isCheck=false',
            defaultValue: '${sessionScope.hos_id}',
         	onInit : function(value) {
				changeLeftGridColumns()
			},
		});  
     	
		/* 出勤科室日期滚动条 */
 		dateSilder = $("#dateSlider").etDateSlider({
			view : 'months',
			dateFormat : 'YYYY-MM',
 			onChanged : function() {
 				changeLeftGridColumns()
 			}
 		});
 		
		/* 人员视角日期滚动条 */
 		person_dateSlider = $("#person_dateSlider").etDateSlider({
 			view : 'months',
			dateFormat : 'YYYY-MM',
 			onChanged : function(value) {
 				var e = $('#calendar');
 				var options = {
 					date : new Date(value)
 				};
 				createTable(options,e),
 				staffSchedule();
 			}
 		});
 		
		/* 排班人员列 */
 		characterList = $('#character_list').etCharacterList({
			url : '../../queryEmp.do?isCheck=false',
			param : {},
			pageModel : {
				type : true,
			},
			onInit:function(res){
				var firstItem = res.Rows[0];
				characterList.setSelectedItem(firstItem.user_code);
				staffSchedule();
			},
			onClick : function() {
				staffSchedule();
			},
		});
 		changeLeftGridColumns();
	};
        
	/* 左表 基础列头 */
   	var leftcolumns = [{
		display : '班次名称',
   		name : 'BETIME',
   		width : 80,
   	}];
        
   	/* 生成月份的列 */
   	var genegrateMonthsColumns = function(date) {
		var columns = [];
   		var daysInMonth = moment(date, 'YYYY-MM').daysInMonth();
   		currentDateList = [];
   		for (var i = 0; i < daysInMonth; i++) {
   			var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD');
   			currentDateList.push(theDate);
   			
   			var sWeek = new Date(theDate).getDay();
   			switch (sWeek){ 
    			case  0: sWeek='周日';  
                	break;  
                case  1: sWeek='周一';  
                    break;  
                case  2: sWeek='周二';  
                    break;  
                case  3: sWeek='周三';  
                    break;  
                case  4: sWeek='周四';  
                    break;  
                case  5: sWeek='周五';  
                    break;  
                case  6: sWeek='周六';  
                    break;  
               
                default:  
                    break;  
            }; 
   			columns.push({
				display : theDate +'<br/>'+ sWeek ,
				name : theDate,
   				width : 80,
			})
   		};
   		return columns;
   	};
    	
   	var changeLeftGridColumns = function() {
   		var options = dateSilder.getOptions();
   		var backColumns = [];
   		backColumns = genegrateMonthsColumns(options.activeDate);
   		var newColumns = leftcolumns.concat(backColumns);
   		leftGrid.option('columns', newColumns);
   		leftGrid.refreshView();
   	};
   	
    function initTree() {
		// 加载部门树
        var dept_url = '../../queryHosDeptDictTree.do?isCheck=false';
        dept_tree = $("#dept_tree").etTree({
            async: {
                enable: true,
                url: dept_url,
            },
            callback: {
                onClick: function (e, id, node) {
                    reloadleftGrid(node.id);
                },
                onAsyncSuccess:function(e, id, node){
                	var firstChildrenNode = dept_tree.getNodes()[0];
                	dept_tree.selectNode(firstChildrenNode);
                	reloadleftGrid(firstChildrenNode.id);
                }
            },
        });
       
        //人员视角部门树
        person_tree = $("#person_tree").etTree({
            async: {
                enable: true,
                url: '../../queryHosDeptDictTree.do?isCheck=false',
            },
            callback: {
                onClick: function (e, id, node) {
					if (node) {
						characterList.param.dept_code = node.id;
					}
					characterList.reload()
                }
            }
        });
    };
    
	/* 查询考勤科室排班表 */
	var reloadleftGrid = function (treeId) {
     	
		var date=dateSilder.getValue();
     	var d = moment(date, 'YYYY-MM'); 
     	var firstDate = d.startOf("month").format("YYYY-MM-DD"); 
     	var lastDate = d.endOf("month").format("YYYY-MM-DD");
		var params = [];

        params = [
			{ name: 'dept_id', value: treeId },     
         	{ name: 'attend_pbdatebeg', value: firstDate },
         	{ name: 'attend_pbdateend', value: lastDate },
       	];
        leftGrid.loadData(params,'querySchedulingInquire.do?isCheck=false'); //右侧人员排班查询
        
	};
        
	var leftGrid = function() {
		var paramObj = {
			height : '100%',
			width :  'auto',
			freezeCols : 1, //冻结一列
			inWindowHeight: '100%',
			editable : false,
			wrap: true,
			columns : leftcolumns,
			dataModel:{
  				getData:function(res){
 			 		$.each(res.Rows,function(i,k){
 			 			k.BETIME = k.BETIME.replace(/\/-/g, "");
 			 			k.BETIME = k.BETIME.replace(/\//g, "<br>");
 			 		});
 			 		return res;
 			 	}
  			},
		};
		leftGrid = $("#maGrid").etGrid(paramObj);
	};
	
	/* 查询人员排班表日历 */
	function staffSchedule(){
		
		var date=person_dateSlider.getValue();
     	var d = moment(date, 'YYYY-MM'); 
     	var firstDate = d.startOf("month").format("YYYY-MM-DD"); 
     	var lastDate = d.endOf("month").format("YYYY-MM-DD");
        
     	var params = {
			user_code : characterList.selectedItem.user_code,
			attend_pbdatebeg : firstDate,
			attend_pbdateend :lastDate
        }
        ajaxPostData({
			url: 'queryEmpPb.do?isCheck=false',
    	  	data: params,
    	  	success: function (res) {
    	  		DateTable.prototype.lunarDay(res.Rows)
    	  	},
    	})
	};

</script>
</head>

<body>

	<!-- div class="main_title">
		<label for="">单位信息：</label>
		 <select id="hos_name" style="width: 180px;" disabled></select>
	</div-->
	
	<div class="container">
		<div id="tab">
              	<div title="出勤科室视角" tabid="0">
				<div id="dept_tree" class="flex-item-1"></div>
                   <div class="tab_content">
            		<h3>考勤科室排班表(月)</h3>
            		<div id="dateSlider"></div>
             		<div id="maGrid" ></div>
            	</div>
              	</div>
			<div title="人员视角" tabid="1">
				<div id="person_tree" class="flex-item-1"></div>
				<div class="tab_content">
					<h3>人员排班表(月)</h3>
					<div id="person_dateSlider"></div>
					<div id="divLeft" class="left border-right"><ul id="character_list"></ul></div> 
					<div id="calendar"></div>
				</div>
            </div>
		</div>
	</div>
	
</body>
</html>

<script>
var calendar = {
	/**
	* 公历每个月份的天数普通表
	*/
	solarMonth: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
	/**
	* 星期称呼速查表
	*/
	Week: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
};

	/* 保存今天的日期 */
	var ToDay = null;
	/* 保存点击的日期 */
	var ClickDays = 0;
	/* 休假和上班自定义配置信息 */
	var configDay = {};
	/* 本月信息 */
	var configDayM = {};
    
    /*************************主程序******************************/
    $.fn.calendar = function (options) {
    	var e = $('#calendar');
        var defaults = {
            date: new Date(),
            week: false,
            configDay: {},
        };
        var object = $.extend(true, {}, defaults, options);
        ToDay = object.date;
        configDay = object.configDay;
        
    	var Y = ToDay.getFullYear();
    	ClickDays = options.date.getDate();
        
        createTable(options,e);
    };

    /*************************主程序******************************/

    function createTable(options,e) {
        var Y = options.date.getFullYear();
        var M = options.date.getMonth() + 1;
        
        
        /* 拷贝configDay对应本月的设置信息 */
        setconfigDayM(Y, M);
        ClickDays = options.date.getDate();
        /* 创建时间表格 */
        var datetable = new DateTable(Y, M, e);
        datetable.create(M);
        /* 设置样式 */
        setLayer(options, datetable.count);
    }

    /* 拷贝configDay对应本月的设置信息 */
    function setconfigDayM(Y, M) {
        if (configDay["Y" + Y] && configDay["Y" + Y]["M" + M]) configDayM = $.extend(true, {}, configDay["Y" + Y]["M" + M]);
        else configDayM = {};
    }

    /* 定义创建时间表格函数 */
    var DateTable = function (Y, M, e) {
        var temp = null;
        this.Y = Y;
        this.M = M;
        this.e = e;
        temp = new Date(Y + "/" + M + "/1").getDay();
        this.W = (temp == 0 ? 7 : temp);
        this.days = this.monthDays();
        temp = this.days + this.W - 1;
        this.count = temp % 7 == 0 ? parseInt(temp / 7) : parseInt(temp / 7) + 1;
        this.html = "";
    };

    /* 创建表格 */
    DateTable.prototype.create = function (M) {
        this.html = "<div class=\"calendar\">";
        this.html += "<div class=\"leftArea\">";
        this.leftWeek();
        this.leftDay();
        this.html += "</div>";
        this.html += "</div>";
        this.e.html(this.html);
        //this.lunarDay();
    };

    /* 获取本月多少天 */
    DateTable.prototype.monthDays = function () {
        if ((this.M == 2) && (this.Y % 400 == 0 || (this.Y % 100 != 0 && this.Y % 4 == 0))) return 29;
        else return calendar.solarMonth[this.M - 1];
    }

    /* 获取左边区域的星期名称 */
    DateTable.prototype.leftWeek = function () {
        for (i = 0; i < 7; i++) this.html += "<div class=\"week\">" + calendar.Week[i] + "</div>";
    };

    /* 获取本月天数 */
    DateTable.prototype.leftDay = function () {
        var day = 0;
        var W = this.W;
        var color = "";
        for (i = 0; i < this.count * 7; i += 1) {
            if (i < this.W - 1 || i >= this.days + this.W - 1) this.html += "<div class=\"days1\"></div>";
            else {
                day = i + 2 - this.W;
                if (W == 6 || W == 7) color = "red";
                else color = "#006db7";
                this.html += "<div class=\"days\" id=\"days" + day + "\"><div id=\"num" + day +
                    "\" class=\"num\" style=\"color:" + color + ";height: 0;padding-top: 6%;\">" + day + "</div><div class=\"schedul schedul" +
                    (day > 9 ? day : ("0" + day)) + "\"></div><div class=\"lunar lunar" +(day > 9 ? day : ("0" + day)) + "\"></div></div>";
                W += 1;
                if (W == 8) W = 1;
            }
        }
    };
    
    /* 获取对应的排班科室 */
    DateTable.prototype.lunarDay = function (arr) {
    	var value = person_dateSlider.getValue();
     	var daysMonth = moment(value, 'YYYY-MM').daysInMonth();
     	$(".calendar .schedul").html('');
		$(".calendar .lunar").html('');
		
		if(arr.length > 0){
			$.each(arr,function(index,j){
				for (var i = 1; i < daysMonth; i ++) {
					var i = i > 9 ? i : ("0" + i);
	           		if(value +'-'+ i == j.attend_pbdate){
           				$(".calendar .schedul" + i).html(j.attend_classsname);
               			$(".calendar .lunar" + i).html(j.dept_name);
					}
 				};
			});	
        }
    };

    function sTerm(y, n) {
        var offDate = new Date((31556925974.7 * (y - 1900) + calendar.sTermInfo[n] * 60000) + Date.UTC(1900, 0, 6, 2, 5));
        return (offDate.getUTCDate());
    }
    
    // 设置表格样式
    function setLayer(options, count) {
        var width = options.width;
        var height = options.height;
        $(".calendar").css("margin","0");
        $(".week").css({width:"14.28%",height:"40px",lineHeight: '40px',background:'#c9e1fb',fontWeight: '600',margin: '0',padding: '0',boxSizing:' border-box'});
        $(".days").css({width: "14.28%",height:"60px",position:'relative',boxSizing:' border-box',cursor: "default"});
        $(".days1").css({width:"14.28%",height:"60px",boxSizing:' border-box'});
        $(".schedul").css({paddingTop:"16%"});
        $(".lunar").css({margin:"0",fontSize:'12px',color:'#000'});
    }

</script>
