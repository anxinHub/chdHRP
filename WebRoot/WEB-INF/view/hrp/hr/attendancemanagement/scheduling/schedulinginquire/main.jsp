<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="hr,dialog,datepicker,select,validate,grid,checkbox,dateSlider,characterList,tree,tab,from,pageOffice" name="plugins" />
</jsp:include>

</head>
<style >
*{
	margin:0;
	padding:0;
}
html,body {
	height: 100%;
   /*  min-width: 900px */
}

.grid_header .float_left {
    float: left;
    padding: 5px 10px;
}

.grid_header .float_right {
    float: right;
    padding: 5px 10px;
}

.container {
	width:100%;
	height:100%;
    padding-top: 0px;
}
    
.tab_content h3{
    text-align: center;
	font-size: 20px;
	margin: 5px 0;
}
    
#dateSlider,#person_dateSlider {
	font: bold 4em/273px "微软雅黑", Verdana, Arial, Helvetica, sans-serif;
	float: left;
}

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
	
.pbrule{
    margin: 10px;
}

.dateTable{
	height: 38px;
	clear: both;
    display: block;
    overflow: hidden;
}

#jump,#print,#query{
    padding: 4px 8px;
    cursor: pointer;
    width: 50px;
    float: left;
    border: 1px solid #aecaf0;
    background: #a1cef4;
    outline: none;
    border-radius: 2px;
    margin-left: 5px;
    text-align: center;
}

#pbsq{
    padding: 4px 8px;
    border: 1px solid red;
    cursor: pointer;
    width: 38px;
    float: left;
    margin-left: 5px;
}

.popup{
   	position: absolute;
    top: 40px;
    left: 945px;
    min-width: 200px;
    min-height: 40px;
    background: #FFFCC7;
    border: 1px solid #FFC340;
    padding: 4px 12px;
    width: 100px;
    font-size: 12px;
    z-index: 100;
}

.popup ul{
    margin: 0;
    padding: 0;
}

.popup ul li{
	list-style:none;
    padding: 2px 0;
}
.popup:before{
    position: absolute;
    content: "";
    right: 100%;
    top: 13px;
    border-top: 10px solid transparent;
    border-right: 10px solid #FFC340;
    border-bottom: 10px solid transparent;
}
	
</style>
<script>
	
	var dateSilder,person_dateSlider,characterList,attend_areacode,check_defualt,leftGrid,initDate,yh_code,calss_code;
	var areacode,code,pb_code,db_code,deptValue,deptText;
	var state = 'unchecked';
	var currentDateList = []; // 当前列头所展示的所有日期列表

	$(function (){
		initForm();
		initGrid();
        
		$('.rightTab').width($(window).width() - $('#tab').width());
        $('#dept_tree').height($(window).height()-85);
         
        // 给输入框绑定搜索树事件
        $("#tree_query").on('keyup', function () {
        	var $self = $(this);
            searchTree({
               tree: dept_tree,
               value: $self.val(),
               callback: function () {
                   $self.focus();
               }
           })
        });
        
        //审签状态
    	$('#pbsq').click(function (event) { 
		 	event.stopPropagation(); 
		    if($(".popup").is(':hidden')){
	   		　　$(".popup").show();　
	   		}else{
	   		　　$(".popup").hide();
	   		}
		}); 
		
    	//点击空白处隐藏弹出层
	    $(document).click(function(event){
	        var _con = $('.popup');
	        if(!_con.is(event.target) && _con.has(event.target).length === 0){
	           $('.popup').hide();
	        }
	    });
    	
	   //跳至本周/月
		$('#jump').click(function(){
			var onInitDate = new Date(initDate);
			var changeDate = new Date(dateSilder.getValue());
			if(onInitDate.getTime() != changeDate.getTime()){
				dateSilder.getOptions().activeDate = initDate;
				changeDateSlider(attend_pbrule.getValue());
				changeLeftGridColumns();
				queryGrid();
			}
		});
	   
	   
		 $("#print").click(function() {
	        	
		        if(!leftGrid.getAllData() || leftGrid.getAllData().length==0){
		        	$.etDialog.error("请先查询数据");
					return;
				}
		    
		        var heads={};
	        	var foots={};
	        	if(!$("#pbsq").is(":hidden")){
	        		heads={
	  			  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	  			  		  "rows": [
	  				          {"cell":0,"value":"排班周期："+$(".pb_qj").text(),"colSpan":"4","align":"left"}
	  			  		  ] 
	  		  		};
	  			
		  			foots={
		  		  		  "rows": [
		  			          {"cell":0,"value":"制表人："+$(".create_user_name").text(),"align":"left"},
		  			          {"cell":1,"value":"制表时间："+$(".create_date").text(),"align":"left","from":"right"/* ,"colSpan":"2" */},
		  			          {"cell":0,"value":"审签人："+$(".check_user_name").text(),"align":"left","br":"true"},
		  			          {"cell":1,"value":"审签时间："+$(".check_date").text(),"align":"left","from":"right"/* ,"colSpan":"2" */},
		  			          {"cell":0,"value":"备注："+$(".note").val(),"align":"left","br":"true"}
		  		  		  ] 
		  		  	};
	        	}
			  	
			  	var  columns=JSON.stringify(leftGrid.getPrintColumns());
			  	columns=columns.replace(/<br\/>/g,"");
			  	
		   		var printPara={
		   			title: attend_areacode.getText()+"排班表",//标题
		   			columns: columns,//表头
		   			class_name: "com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingService",
					method_name: "queryPbByDeptPrint",
					bean_name: "hrSchedulingService" ,
					heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
					foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
		   		};
		   		var selectedNode = dept_tree.getSelectedNodes();
				
				if(selectedNode.length>0 && selectedNode[0].id!="0"){
					printPara['dept_code'] =selectedNode[0].id;
				}
				var is_cq_dept= $("#check_defualt").prop("checked") ? 1 : 0 
		   		//执行方法的查询条件
		   		printPara['attend_areacode']=code;
		   		printPara['attend_classcode'] =calss_code.getValue();
		   		printPara['is_cq_dept']=is_cq_dept;
		   		printPara['begin_date']=currentDateList[0];
		   		printPara['end_date']=currentDateList[currentDateList.length - 1];
		   		
		    	officeGridPrint(printPara);
	        });
		 
	    $("#query").click(function(){
         	queryGrid();
        }); 
    });
        
	function initForm() {
		
		/* 排班规则 */
		attend_pbrule = $("#attend_pbrule").etSelect({
       		showClear: false,
            options: [{
                    id: 0,
                    text: '周'
                },
                {
                    id: 1,
                    text: '月'
                },
            ],
            onInit: function (value) {
			},
			onChange: function (value) {
				if(value == '0'){
					$('#jump').text('跳至本周')
				}else{
					$('#jump').text('跳至本月')
				}
				changeDateSlider(value);
				changeLeftGridColumns();
			}
        });
		 
        /* 倒班规则 */
        db_gz = $("#db_gz").etSelect({
        	showClear: false,
            options: [{
                    id: 1,
                    text: '一班'
                },
                {
                    id: 2,
                    text: '二班'
                },
                {
                    id: 3,
                    text: '三班'
                }
            ],
            onInit: function (value) {
			},
			onChange: function (value) {
				changeLeftGridColumns();
			}
        });
		
		/* 区域名称 */
		 attend_areacode = $("#attend_areacode").etSelect({
			// showClear: false,
             url: "../../queryDicAreaAttr.do?isCheck=false",
             onInit: function (value) {
            	 
            	 initDate = dateSilder.getValue();
            	 if(value!=""){
            		areacode=value.split(",");
                	code = areacode[0];     //区域
            		$("#pbsq").show();
            		pb_code = areacode[1];   //排班
     	   			db_code = areacode[2];   //倒班
     	   			attend_pbrule.setValue(pb_code);
    				db_gz.setValue(db_code);
          		 }else{
          			code="";
          			$("#pbsq").hide();
          		 }
            	 initTree(code);
				
             },
         	onChange : function(value) {
         		
         		if(value!=""){
            		areacode=value.split(",");
                	code = areacode[0];     //区域
            		$("#pbsq").show();
            		pb_code = areacode[1];   //排班
     	   			db_code = areacode[2];   //倒班
     	   			attend_pbrule.setValue(pb_code);
    				db_gz.setValue(db_code);
          		 }else{
          			code="";
          			$("#pbsq").hide();
          		 }
            	 initTree(code);
            	 calss_code.reload({
                     url: '../../queryCalssCode.do?isCheck=false&attend_areacode='+code
                 });
           	 	
 			}
         });
		
		 //医护属性
         yh_code = $("#yh_code").etSelect({
             url:"../../queryAttendFieldOption.do?isCheck=false",
             defaultValue: "none",
             onInit: function (value) {
 			},
 			onChange: function (value) {
 			}
         });
         //班次
         calss_code = $("#calss_code").etSelect({
             url:"../../queryCalssCode.do?isCheck=false&attend_areacode="+attend_areacode.getValue().split(',')[0],
             defaultValue: "none",
             onInit: function (value) {
 			},
 			onChange: function (value) {
 			}
         });
		
		check_defualt = $('#check_defualt').etCheck({
			onChange: function (status) {
				state = status;
		  	}
		});
		
		/* 出勤科室日期滚动条 */
  		dateSilder = $("#dateSlider").etDateSlider({
  			view: 'weeks',
        	dateFormat: 'YYYY-MM-DD',
 			onChanged : function() {
 				changeLeftGridColumns();
 				queryGrid();
 			}
 		});
	};
        
	/* 左表 基础列头 */
   	var leftcolumns = [ {
        display: '所属科室',
        name: 'dept_name',
        width: 80
    },{
        display: '职工姓名',
        name: 'emp_name',
        width: 80
    }];
   	var rightColumns = [{
         display: '备注',
         name: 'note',
         width: 180
     }];
	
   	/* 改变排班规则 -》 改变日期滚动数据 */
    var changeDateSlider = function (value) {
    	if (value == 0) {
    		dateSilder.option({
        		view: 'weeks',
        		dateFormat: 'YYYY-MM-DD',
        	});
    	} else {
    		dateSilder.option({
        		view: 'months',
        		dateFormat: 'YYYY-MM',
        	});
    	}
    };
    
    var rowDatas = function(rowdata){
		var dataIndex = rowdata.dataIndx.split('_');   
		var index = dataIndex[0].substring(1);
		var deptID = 'd' +index+ 'id_' + dataIndex[1];
		var dt = 'd' +index+ '_' + dataIndex[1];
		var dValue = rowdata.rowData[deptID];
		var dText =  rowdata.rowData[dt];
		if(rowdata.cellData != undefined){
			if(dValue && rowdata.rowData.dept_id!=dValue){
				return '<span class="queryDept" style="color:red;display: inline-block;width: 100%;height: 100%;" dText= "'+dText + '" dValue= "'+dValue + '">'
				+rowdata.cellData+ '</span>';
			}else{
				return '<span class="queryDept" style="display: inline-block;width: 100%;height: 100%;" dText= "'+dText + '" dValue= "'+dValue + '">'
				+rowdata.cellData+ '</span>';
			}
		}
	}; 
    
   	/* 生成 星期的列 */
    var genegrateWeeksColumns = function (date,value) {
    	var columns = [];
    	var capitalMap = ['一', '二', '三', '四', '五', '六', '日']
    	currentDateList = []
    	for (var i = 0; i < 7; i++) {
   			var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD');
   			var mouth_date = theDate.substring(5);
    		currentDateList.push(theDate);
    		
    		columns.push({
    			display: mouth_date + '<br/>'+ '周' + capitalMap[i],
    			columns: [
   			      	{
       					display: '',
       					name: 'c1_' + theDate,
       					width:'60',
       					render:rowDatas
       				},
       				
       				{
       					display: '',
       					name: 'c2_' + theDate,
       					width:'60',
       					hidden : (value == '1') ? true : false,
       					render:rowDatas
       				},
       				{
       					display: '',
       					name: 'c3_' + theDate,
       					width:'60',
       					hidden : (value == '1'|| value == '2') ? true : false,
       					render:rowDatas
       				}	
    			]
    		})
    	}
    	return columns
    };
   /* 生成月份的列 */
	var genegrateMonthsColumns = function (date,value) {
		var columns = [];
		var daysInMonth = moment(date, 'YYYY-MM').daysInMonth();
		currentDateList = [];
    	for (var i = 0; i < daysInMonth; i++) {
    		var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD');
    		var mouth_date = theDate.substring(5);
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
    			display: mouth_date +'<br/>'+ sWeek,
    			columns: [
    				{
    					display: '',
    					name: 'c1_' + theDate,
    					width:'60',
    					render:rowDatas
    				},
    				{
    					display: '',
    					name: 'c2_' + theDate,
    					width:'60',
    					hidden : (value == '1') ? true : false,
						render:rowDatas
    				},
    				{
    					display: '',
    					name: 'c3_' + theDate,
    					width:'60',
    					hidden : (value == '1'|| value == '2') ? true : false,
						render:rowDatas
    				}
    			]
    		})
    	}
    	return columns
    };

    /* 改变表格的列 */
	var changeLeftGridColumns = function () {
	   
		var options = dateSilder.getOptions();
   		var backColumns = [];
   		var value = db_gz.getValue();
   		if (options.view === 'weeks') {
   			backColumns = genegrateWeeksColumns(options.activeDate,value)
   		} else {
   			backColumns = genegrateMonthsColumns(options.activeDate,value)
   		}

        var newColumns = leftcolumns.concat(backColumns).concat(rightColumns);
        leftGrid.option('columns', newColumns);
        leftGrid.refreshView();
	};
   
    function initTree(code) {
		 // 加载部门树
        dept_tree = $("#dept_tree").etTree({
            async: {
                enable: true,
                url: 'queryDeptTreeByArea.do?isCheck=false&attend_areacode='+code,
            },
            callback: {
                onClick: function (e, id, node) {
                	queryGrid();
                },
                onAsyncSuccess:function(e, id, node){
                	/* var firstChildrenNode = dept_tree.getNodes()[0];
                	dept_tree.selectNode(firstChildrenNode); */
                }
            },
        });
    };
    
	/* 查询考勤科室排班表 */
	var queryGrid = function () {
     	
		var date=dateSilder.getValue();
     	var d = moment(date, 'YYYY-MM'); 
     	var firstDate = d.startOf("month").format("YYYY-MM-DD"); 
     	var lastDate = d.endOf("month").format("YYYY-MM-DD");
		var params = [];
		var selectedNode = dept_tree.getSelectedNodes();
		
		if(selectedNode.length>0 && selectedNode[0].id!="0"){
        	params.push( { name: 'dept_code', value: selectedNode[0].id });
		}
		params.push( { name: 'attend_areacode', value: code });
        params.push( { name: 'begin_date', value: currentDateList[0] });
        params.push( { name: 'end_date', value: currentDateList[currentDateList.length - 1] });
        params.push( { name: 'emp_code', value: $("#emp_code").val() });
        params.push( { name: 'yh_code', value: yh_code.getValue() });
        params.push( { name: 'attend_classcode', value: calss_code.getValue() });
        params.push( { name: 'is_cq_dept', value: $("#check_defualt").prop("checked") ? 1 : 0 });
		
        leftGrid.loadData(params,'queryPbByDept.do'); //右侧人员排班查询
        //leftGrid.refreshDataAndView();
	};
        
	var initGrid = function() {
		var paramObj = {
			width :  'auto',
			height : '100%',
			inWindowHeight: true,
			freezeCols : 2,
			columns : leftcolumns,
			dataModel: {
                getData: function (res) {
                	if(res.error){
                		$.etDialog.error(res.error);
                		return;
                	};
                	
                	if(res.check_user_name){
                		$('.check_user_name').text(res.check_user_name);
                	}else{
                		$('.check_user_name').text('');
                	}
                	
                	if(res.check_date){
                		$('.check_date').text(res.check_date);
                	}else{
                		$('.check_date').text('');
                	}
                	
                	if(res.create_user_name){
                		$('.create_user_name').text(res.create_user_name);
                	}else{
                		$('.create_user_name').text('');
                	}
                	
                	if(res.pb_qj){
                		$('.pb_qj').text(res.pb_qj);
                	}else{
                		$('.pb_qj').text('');
                	}
                	
                	if(res.create_date){
                		$('.create_date').text(res.create_date);
                	}else{
                		$('.create_date').text('');
                	}
                	
                	if(res.note){
                		$('.note').val(res.note);
                	}else{
                		$('.note').val('');
                	}
                	
                	$('#pbsq').text(res.state_name); 
                	
                  	return res;
                }
			}
		};
		leftGrid = $("#maGrid").etGrid(paramObj);
		
		/* 单元格悬浮事件  */
        $("#maGrid").on('mouseenter', '.queryDept', function () {
        	deptText = $(this).attr('dText');         
            deptValue= $(this).attr('dValue');
            if(deptText != 'undefined'){
            	$(this).parent('td')['0'].title = deptText;  
            }else{
            	$(this).parent('td')['0'].title = ''; 
            }
        });
	};
</script>

<body>
	<div class="container">
	   	<div class="left border-right">
			<div class="search-form">
           		<label>区域名称：</label>
		        <select id="attend_areacode" style="width: 120px;"></select>
            </div>
            <div class="search-form">
                <label>快速定位：</label>
                <input id="tree_query" class="text-input" type="text" style="width: 120px;">
            </div>
            
            <div id="dept_tree"></div>
		</div>
		
		<div class="rightTab">
			<div class="pbrule">
       			<label>排班规则：</label><select id="attend_pbrule" style="width: 100px;"></select>
       			<label>班次显示：</label><select id="db_gz" style="width: 100px;"></select>
       			<label>职工信息：</label><input id="emp_code" class="text-input" type="text" style="width: 120px;">
       			<label>医护属性：</label><select id="yh_code" style="width: 100px;"></select>
       			<label>班次名称：</label><select id="calss_code" style="width: 100px;"></select>
       			<input id="check_defualt" type="checkbox" />
				<label for="check_defualt">按出勤科室</label>
       		</div>
            <div class="tab_content">
           		<!-- h3>考勤科室排班表</h3-->
           		<div class="dateTable">
	 				<div id="dateSlider" title="点击日期查询"></div>
	 			<!-- 	<div id="jump"></div> -->
	 				<div id="print">打印</div>
	 				<div id="query">查询</div>
	 				<div id="pbsq">未审签</div>
	 				<!-- 气泡框 -->
				    <div class="popup" style="display:none">
				    	<ul>
				    		<li>
				    			<span>排班周期：</span>
			    				<span class="pb_qj"></span>
			    			</li>
				    		<li>
				    			<span>制单人：</span>
			    				<span class="create_user_name"></span>
			    			</li>
			    			<li>
				    			<span>修改时间：</span>
			    				<span class="create_date"></span>
			    			</li>
			    			<li>
				    			<span>审签人：</span>
			    				<span class="check_user_name"></span>
			    			</li>
			    			<li>
				    			<span>审签时间：</span>
			    				<span class="check_date"></span>
			    			</li>
			    			<li>
				    			<span>备注：</span>
			    				<textarea class="note" rows="0" cols="20" style="resize:none;margin: 0px; height: 27px; width: 190px;" disabled></textarea>
			    			</li>
				    	</ul>
				    </div>
	 			</div>
           		<div id="maGrid"></div>
           	</div>
		</div>
	</div>
</body>
</html>
