<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
    <jsp:param value="hr,dialog,datepicker,select,validate,grid,tab,dateSlider,pageOffice" name="plugins" />
</jsp:include>
<style>
body {
	margin: 0;
	font-size: 12px;
}

#add,#clear,#copy,#extend,#clearPb,#close,#addAll,#query,#print,#check,#uncheck{
	margin: 12px;
	box-sizing: border-box;
	height: 26px;
	padding-left: 10px;
	padding-right: 10px;
	border: 1px solid #aecaf0;
	background: #a1cef4;
	outline: none;
	border-radius: 2px;
	cursor: pointer;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
}

#jump,#save{
    padding: 4px 8px;
    width: 50px;
    float: left;
    border: 1px solid #aecaf0;
    background: #a1cef4;
    outline: none;
    border-radius: 2px;
    cursor: pointer;
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
#pbfc{
    padding: 4px 8px;
    border: 1px solid red;
    cursor: pointer;
    width: 38px;
    float: left;
    margin-left: 5px;
 }
.container {
	width:100%;
  	height:100%;
}

.togglebtn {
	position: absolute;
	background: url(/CHD-HRP/lib/ligerUI/skins/icons/toggle2.png) no-repeat 4px 4px;
	height: 24px;
	width: 24px;
	cursor: pointer;
	left: auto;
	top: 0px;
	right: 0px;
	border-radius: 50%;
	background-color: #ffffff;
	border: 1px solid #ddd;
}
.bgi {
	background-image: url(/CHD-HRP/lib/ligerUI/skins/icons/toggle1.png);
}
#dateSlider{
  	font: bold 4em/273px "微软雅黑", Verdana, Arial, Helvetica, sans-serif;
    float: left;
}

.popup{
   	position: absolute;
    top: 84px;
    /* left: 792px; */
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

#details{
	position: absolute;
    background: #F5F5F5;
    border: 1px solid #979797;
}

#details .title{
    padding: 2px 0;
    border-bottom: 1px solid #a3c0e8;
}

#details .close{
	display: inline-block;
    padding: 0px 4px;
    cursor: pointer;
    border: 1px solid #a1cef4;
}

.dateTable{
	height: 38px;
	clear: both;
    display: block;
    overflow: hidden;
}

</style>
<script>
	var status,year, begin_date, end_date, attend_areacode, attend_pbrule, dateSilder,leftGrid,details_assess,currentRowData,initDate,paramLeftObj;
	var cq_dept = 0;
	var areacode,code,pb_code,db_code,yh_code;  //区域切换使用
    var currentDateList = []; // 当前列头所展示的所有日期列表
    var classSource = [];  //班次回调数据
    var deptSource = [];  //科室回调数据
    var deptId,deptText,deptColCode,RowIndex,empId,bColCode;//科室页面使用
	var is_sq,is_fc;  //审签封存状态
    
    $(function () {
    	loadLeftGrid();
    	initForm();
    	loadClassDept();
    	$('.popup').css('left',$('#dateSlider').width() + $('#jump').width()+ $('#save').width()+ $('#pbfc').width()+ $('#pbsq').width() + 105 + 'px');
    	
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
        
     	// 折叠按钮
		$('.togglebtn').click(function () {
			$('.togglebtn').toggleClass('bgi');
			$('.table-layout').toggle();
			leftGrid.refresh();
		})

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
        
    });
    
    /* 查询排班 */
    var queryGrid = function(){
		var params = [];
   		params = [
            { name: 'attend_areacode', value: code },
            { name:'begin_date',value: currentDateList[0] },
            { name:'end_date',value: currentDateList[currentDateList.length - 1] },
        ];
    	
   		leftGrid.loadData(params,'queryPB.do');
    };
    
    /* 根据日期获取是否为工作日 */
	var getWeekByXiuBan = function(date){
		
		var sWeeks = "";
		ajaxPostData({
            url: '../attend/queryOfficialHoliday.do?isCheck=false&year_month='+date,async: false,	
            success: function (data) {
            	if(data.Rows.length > 0 ){
            		switch (data.Rows[0].attend_date_state){ 
        			case  "0": 
        				sWeeks="<span style='color:blue'>休</span>";  
        				break;  
        			case  "1": 
        				sWeeks="<span style='color:red'>班</span>";  
        				break;  
        			default:  
        				break;  
        		}
            	} 
            }
        });
		
		return sWeeks;
	}
    
    
    /* 加载表格中班次和科室数据 */
    var classData,deptData;
    var loadClassDept = function(){
    	
    	/* 班次 */
        ajaxPostData({
    		url: "../../queryCalssCode.do?isCheck=false&attend_areacode="+ attend_areacode.getValue().split(',')[0],
    		async: false,
			success: function (res) {
    			classData=res;
    			classSource = [];
    			$.each(res,function(index,value){
    				classSource.push({label:value.text,id:value.id});
    			})
    		}
    	});
        
        /* 科室 */
        ajaxPostData({
        	url: "../../queryCalssDept.do?isCheck=false&attend_areacode="+ attend_areacode.getValue().split(',')[0],
        	async: false,
   			success: function (res) {
    			deptData=res;
    			deptSource = [];
    			details_assess.addOptions(res);
    			$.each(res,function(index,value){
    				deptSource.push({label:value.text,id:value.id})
    			});
    		}
    	});
    };

   /* 表格基础列头  */
    var leftColumns = [
        {
            display: '科室名称',
            name: 'dept_name',
            width: 80,
            editable : false,
        },{
            display: '职工姓名',
            name: 'emp_name',
            width: 80,
            editable : false,
        },
    ];
    var rightColumns = [
        {
            display: '备注',
            name: 'note',
            width: 180
        },
        {
            display: '是否引入',
            name: 'id',
            width: 80,
            hidden : true,
            editable : false,
        }
    ];
   	
   
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
    
   	/* 班次返回对应的科室信息  */
    var rowDatas = function(rowdata){
		var RowIndx = rowdata.rowIndx;   //行下标
		var dataIndex = rowdata.dataIndx.split('_');   
		var index = dataIndex[0].substring(1);
		var deptID = 'd' +index+ 'id_' + dataIndex[1];
		var dText = 'd' +index+ '_' + dataIndex[1];
		var deptValue = rowdata.rowData[deptID];
		var cID = 'c' +index+ 'id_' + dataIndex[1];
		var bValue = rowdata.rowData[cID];
		var empid = rowdata.rowData.emp_id;  //职工id
		if(rowdata.cellData != undefined){
			if(deptValue && rowdata.rowData.dept_id!=deptValue){
				return '<span class="queryDept" style="color:red" dText= "'+dText + '" deptValue= "'+deptValue + '" bValue= "'+bValue + '" empid= "'+empid + '" RowIndx= "'+RowIndx + '" deptID= "'+deptID + '" bcName="' + rowdata.dataIndx + '">'
				+rowdata.cellData+ '</span>';
			}else{
				return '<span class="queryDept"  dText= "'+dText + '" deptValue= "'+deptValue + '" bValue= "'+bValue + '" empid= "'+empid + '" RowIndx= "'+RowIndx + '" deptID= "'+deptID + '" bcName="' + rowdata.dataIndx + '">'
				+rowdata.cellData+ '</span>';
			}
		}
	};  
    
   /* 生成 星期的列 */
    var genegrateWeeksColumns = function (date,value,cq_dept) {
    	var columns = [];
    	var capitalMap = ['一', '二', '三', '四', '五', '六', '日']
    	currentDateList = []
    	for (var i = 0; i < 7; i++) {
   			var theDate = moment(date).add(i, 'day').format('YYYY-MM-DD');
   			var mouth_date = theDate.substring(5);
    		currentDateList.push(theDate);
    		
    		columns.push({
    			display: mouth_date + '<br/>'+ '周' + capitalMap[i] + getWeekByXiuBan(theDate),
    			columns: [
   			      	{
       					display: '',
       					name: 'c1_' + theDate,
       					width:'60',
       					editor: {
       						type: 'select',
       						keyField: 'c1id_' + theDate,
       						source:classSource,		
       					},
       					render:rowDatas
       				},
       				{
       					display: '',
       					name: 'c2_' + theDate,
       					width:'60',
       					hidden : (value == '1') ? true : false,
       					editor: {
       						type: 'select',
       						keyField: 'c2id_' + theDate,
       						source:classSource,		
       					},
       					render:rowDatas
       				},
       				{
       					display: '',
       					name: 'c3_' + theDate,
       					width:'60',
       					hidden : (value == '1'|| value == '2') ? true : false,
       					editor: {
       						type: 'select',
       						keyField: 'c3id_' + theDate,
       						source:classSource,		
       					},
       					render:rowDatas
       					
       				},
       				{
       					display: '科室1',
       					name: 'd1_' + theDate,
       					width:'60',
       					hidden : cq_dept == '0' ? true : false,
       					editor: {
       						type: 'select',
       						keyField: 'd1id_' + theDate,
       						source:deptSource,	
       					}
       				},
       				{
       					display: '科室2',
       					name: 'd2_' + theDate,
       					width:'60',
       					hidden : cq_dept == '0' ? true : false,
       					editor: {
       						type: 'select',
       						keyField: 'd2id_' + theDate,
       						source:deptSource,	
       					}
       				},
       				{
       					display: '科室3',
       					name: 'd3_' + theDate,
       					width:'60',
       					hidden : cq_dept == '0' ? true : false,
       					editor: {
       						type: 'select',
       						keyField: 'd3id_' + theDate,
       						source:deptSource,	
       					}
       				},
    			]
    		})
    	}
    	return columns
    };
   /* 生成月份的列 */
	var genegrateMonthsColumns = function (date,value,cq_dept) {
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
    			display: mouth_date +'<br/>'+ sWeek + getWeekByXiuBan(theDate),
    			columns: [
    				{
    					display: '',
    					name: 'c1_' + theDate,
    					width:'60',
    					editor: {
    						type: 'select',
    						keyField: 'c1id_' + theDate,
							source:classSource,
    					},
    					render:rowDatas
    				},
    				{
    					display: '',
    					name: 'c2_' + theDate,
    					width:'60',
    					hidden : (value == '1') ? true : false,
    					editor: {
    						type: 'select',
    						keyField: 'c2id_' + theDate,
							source:classSource,
    					},
    					render:rowDatas
    				},
    				{
    					display: '',
    					name: 'c3_' + theDate,
    					width:'60',
    					hidden : (value == '1'|| value == '2') ? true : false,
    					editor: {
    						type: 'select',
    						keyField: 'c3id_' + theDate,
							source:classSource,
    					},
    					render:rowDatas
    				},
    				{
    					display: '科室1',
    					name: 'd1_' + theDate,
    					width:'60',
    					hidden : cq_dept == '0' ? true : false,
    					editor: {
    						type: 'select',
    						keyField: 'd1id_' + theDate,
    						source:deptSource,
    					}
    				},
    				{
    					display: '科室2',
    					name: 'd2_' + theDate,
    					width:'60',
    					hidden : cq_dept == '0' ? true : false,
    					editor: {
    						type: 'select',
    						keyField: 'd2id_' + theDate,
    						source:deptSource,
    					}
    				},
    				{
    					display: '科室3',
    					name: 'd3_' + theDate,
    					width:'60',
    					hidden : cq_dept == '0' ? true : false,
    					editor: {
    						type: 'select',
    						keyField: 'd3id_' + theDate,
    						source:deptSource,
    					}
    				},
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
   			backColumns = genegrateWeeksColumns(options.activeDate,value,cq_dept)
   		} else {
   			backColumns = genegrateMonthsColumns(options.activeDate,value,cq_dept)
   		}

        var newColumns = leftColumns.concat(backColumns).concat(rightColumns);
        leftGrid.option('columns', newColumns);
        leftGrid.refreshView();
	};
    
    var initForm = function () {
        /* 排班修改日期滚动条 */
        dateSilder = $("#dateSlider").etDateSlider({
        	view: 'weeks',
        	dateFormat: 'YYYY-MM-DD',
        	onChanged: function () {
        		changeLeftGridColumns();
        		queryGrid();
        	}
        });
        
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
            defaultValue: '${attend_pbrule}',
            onInit: function (value) {
			},
			onChange: function (value) {
				if(value == '0'){
					$('#jump').text('跳至本周')
				}else{
					$('#jump').text('跳至本月')
				};
				changeDateSlider(value);
				changeLeftGridColumns();
				$('.popup').css('left',$('#dateSlider').width() + $('#jump').width()+ $('#save').width()+ $('#pbfc').width()+ $('#pbsq').width() + 105 + 'px');
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
            defaultValue: '${db_gz}',
            	onInit: function (value) {
			},
			onChange: function (value) {
				changeLeftGridColumns();
			}	
      });
        
       	/* 区域名称 */
        attend_areacode = $("#attend_areacode").etSelect({
   			url: '../../queryDicAreaAttr.do?isCheck=false',
   			showClear: false,		
	   		onInit: function (value) {
	   			areacode = value.split(',');   //区域截取
	   			code = areacode[0];     //区域
	   			pb_code = areacode[1];  //排班
	   			db_code = areacode[2];  //倒班
	   			yh_code = areacode[3];  //医务护理
	   			
				attend_pbrule.setValue(pb_code);
				db_gz.setValue(db_code);
				initDate = dateSilder.getValue();
			},
        	onChange : function(value) {
        		areacode = value.split(',');
	   			code = areacode[0];
	   			pb_code = areacode[1];
	   			db_code = areacode[2];
	   			yh_code = areacode[3];
	   			
				attend_pbrule.setValue(pb_code);
				db_gz.setValue(db_code);
				loadClassDept();
				queryGrid();
			}
        });
       	
        /* 弹框科室 */
       	details_assess = $("#details_assess").etSelect({
       		backEndSearch: false,
       		onChange:function(value){
       			/* console.log("改变值：" + value);
       			console.log("初始值：" + deptId); */
                
       			var str = deptText.split('_');
                var num = str[0].substring(1);   /* 1/2/3 */
                var date = str[1];   //排班日期
                
                currentRowData[deptColCode] = value;
  				currentRowData[deptText] = details_assess.getText();

   				
  				var saveDept = [];
  				saveDept.push({
  					dept_id : currentRowData.dept_id,
					dept_name : currentRowData.dept_name,
					emp_id : currentRowData.emp_id,
					emp_name : currentRowData.emp_name,
					id : currentRowData.id,
					note : currentRowData.note,
					rowIndx : currentRowData._rowIndx,
  				});
  				saveDept[0]['c' + num + '_'+ date] = currentRowData['c' + num + '_'+ date];
  				saveDept[0]['c' + num + 'id_'+ date] = currentRowData['c' + num + 'id_'+ date];
  				saveDept[0]['d' + num + '_'+ date] = currentRowData['d' + num + '_'+ date];
  				saveDept[0]['d' + num + 'id_'+ date] = currentRowData['d' + num + 'id_'+ date];
  				
  				leftGrid.updateRow(saveDept[0].rowIndx, saveDept[0])
   		    },
   		 	onClear:function(){
   		 		currentRowData[deptColCode] = '';
				currentRowData[deptText] = '';
   		 	},
   		 	onDelete:function(value){
   		 		currentRowData[deptColCode] = '';
				currentRowData[deptText] = '';
		 	}
        });
        
         $("#query").click(function(){
         	queryGrid();
         });
         
         $("#save").click(function () {
        	var saveData = leftGrid.getAllData();
        	//console.log(saveData);
        	
       		if (saveData ==null  || saveData.length == 0) {
				$.etDialog.error('排班人员不能为空');
				return;
			};
			
			var areacode=attend_areacode.getValue().split(',')[0];
			if (!areacode) {
				$.etDialog.error('区域不能为空');
				return;
			}
			
			var beginDate=currentDateList[0];
			var endDate=currentDateList[currentDateList.length - 1];
			
			if (!beginDate || !endDate) {
				$.etDialog.error('排班日期不能为空');
				return;
			}
			
			if (attend_pbrule.getValue()== "") {
				$.etDialog.error('排班规则不能为空');
				return;
			} 
			
			if (db_gz.getValue()== "") {
				$.etDialog.error('倒班规则不能为空');
				return;
			} 
            
       		ajaxPostData({
                url: 'savePB.do',
                data: {
                	pb_qj : $('.pb_qj').text(),
                	attend_areacode : areacode,
    				pb_gz: attend_pbrule.getValue(),
    				db_gz: db_gz.getValue(),
    				begin_date : beginDate,
    				end_date : endDate,
                	note : $('.note').val(), //审签备注
                 	paramVo : JSON.stringify(saveData)
                },
                success: function (res) {
                	//queryGrid();
            		if(res.state=="true"){
                    	$('.pb_qj').text(res.pb_qj);
                    	$('.create_user_name').text(res.create_user_name);
                    	$('.create_date').text(res.create_date);
            		}
                },
            }) 
        });
        $("#add").click(function () {
            if (code == null || code =="") {
                $.etDialog.error('请选区域');
                return;
            } else {
                parent.$.etDialog.open({
                    url: 'hrp/hr/attendancemanagement/attend/schedulingEmpPage.do?isCheck=false&attend_areacode=' + code,
            			width : $(window).width()-200,
    					height : $(window).height()-100,
                    frameNameObj: {
                        'add': window.name
                    },
                    title: '添加人员'
                });
            }
        });
        
        //批量设置
		$("#addAll").click(function(){
			/* 获取勾选行数据 */
			 var data =leftGrid.selectGet();
			 
            if (data.length == 0) {
                $.etDialog.error('请选择行');
                return;
            }
			
            if (code == null|| code == "") {
				$.etDialog.error('请选区域');
				return;
			} else {
				parent.$.etDialog.open({
					url : 'hrp/hr/attendancemanagement/attend/addAllPage.do?isCheck=false',
					width : 600,
					height : 400,
					frameName :window.name,
					title : '批量设置'
				});
			}
		});
    	$("#clear").click(function() {

			var leftGridData = leftGrid.selectGet();
			if (leftGridData.length === 0) {
				$.etDialog.error('请选择行');
				return;
			}
			
			var areacode=attend_areacode.getValue().split(',')[0];
			if (!areacode) {
				$.etDialog.error('区域不能为空');
				return;
			}
			
			var beginDate=currentDateList[0];
			var endDate=currentDateList[currentDateList.length - 1];
			
			if (!beginDate || !endDate) {
				$.etDialog.error('排班日期不能为空');
				return;
			}
			
			
			var ParamVo = [];
			var rows = [];
			var empIds= [];
	     	  $(leftGridData).each(function () {
	               var rowdata = this.rowData;
	               empIds.push(rowdata.emp_id);
	              /*  if(rowdata.id.toString() != ""){
	            	    empIds = empIds+rowdata.emp_id+',';
	            	}else{
		            	rows.push(this);
	            	} */
	           });
	     	  
	     	 if(empIds.length==0){
				$.etDialog.error('请选择要删除的职工');
				return;
	     	 }
	     	 
	         $.etDialog.confirm('确定删除?', function (index) {
        		
        			// empIds = empIds.substring(0, empIds.lastIndexOf(','));
		        	 ajaxPostData({
			         	 url: 'deleteSchedulingEmp.do',
			         	 data: {
			         		pb_qj : $('.pb_qj').text(),
			         		attend_areacode : areacode,
			         		pb_gz: attend_pbrule.getValue(),
			         		begin_date : beginDate,
		         			end_date : endDate,
                        	emp_id : empIds.toString()
                         },
			             success: function (res) {
			            	 if(res.state=="true"){
			            		 leftGrid.deleteRows(leftGridData);	 
			            	 }
			             }
			         }) 
		        	
		       /*  if(rows!=null && rows.length>0){
		        	leftGrid.deleteRows(rows);
		        } */
		        $.etDialog.close(index);
	         });
		});
    	
    	$("#clearPb").click(function(){
			$.etDialog.confirm('确定清除所有排班数据?', function (index) {
       			var allData = leftGrid.getAllData();
     			allData.forEach(function (item) {
     				var keys = Object.keys(item);
     				keys.forEach(function (key) {
     					if (key.indexOf('1')> 0 || key.indexOf('2')> 0 || key.indexOf('3')> 0) {
     						item[key] = ''
     					}
     				})
     				leftGrid.updateRow(item._rowIndx, item);
     			})
     			$.etDialog.close(index);
	       	});
			
		});
		$("#copy").click(function() {
			
		 	var data = leftGrid.selectGet();
			if (data.length === 0) {
				$.etDialog.error('请选择人员');
				return;
			}
		  		
			var areacode=attend_areacode.getValue().split(',')[0];
			if (!areacode) {
				$.etDialog.error('区域不能为空');
				return;
			}
			
			var curBeginDate=currentDateList[0];
			var curEndDate=currentDateList[currentDateList.length - 1];
			if (!curBeginDate || !curEndDate) {
				$.etDialog.error('排班日期不能为空');
				return;
			}
			
			if (attend_pbrule.getValue()== "") {
				$.etDialog.error('排班规则不能为空');
				return;
			}
			
			if (db_gz.getValue()== "") {
				$.etDialog.error('倒班规则不能为空');
				return;
			} 
			
			$.etDialog.open({
                url: 'copyWeekPage.do?isCheck=false&attend_pbrule='+attend_pbrule.getValue()+'&begin_date='+curBeginDate+'&end_date='+curEndDate,
                title: '复制上周/月',
                width: 450,
                height: 350,
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                   var iframeWindow = window[el.find('iframe').get(0).name];
                   var selDate=iframeWindow.getDate();
                   var sel_begin_date=selDate[0];
                   var sel_end_date=selDate[1];
                   if (!sel_begin_date || !sel_end_date) {
	       				$.etDialog.error('请选择要复制的日期');
	       				return;
      			   }
              
                   if(attend_pbrule.getValue()=="0"){
                	   //按周排班
                	   if(sel_begin_date!=getDateAddDay(sel_end_date,-6)){
                		   $.etDialog.error('不能跨周复制排班数据');
  	       					return;
               			}
                	   
                   }else{
                	   //按月排班
                	   if (sel_begin_date.substring(0,7)!=sel_end_date.substring(0,7)) {
	   	       				$.etDialog.error('不能跨月复制排班数据');
	   	       				return;
        			   } 
                   }
                  
                    var param = [];
                    $(data).each(function () {
                        var rowdata = this.rowData;
                        param.push(rowdata.emp_id);
                    });
                        
                    //排班规则周
                   	ajaxPostData({
        				url : 'copyWeek.do?isCheck=false',
        				data:{
        					
        					pb_qj : $('.pb_qj').text(),
	        				attend_areacode : areacode,
	        				pb_gz: attend_pbrule.getValue(),
	        				db_gz: db_gz.getValue(),
	        				sel_begin_date : sel_begin_date,
	        				sel_end_date : sel_end_date,
	        				begin_date : curBeginDate,
	        				end_date : curEndDate,
	        				emp_id : param.toString()
        				},
	       				success : function(responseData) {
		       				 var rows=responseData.Rows;
		       				 if(rows.length>0){
		       					rows.forEach(function (item1, index1) {
			       			         data.forEach(function (item2, index2) {
			         			         if(item1.emp_id==item2.rowData.emp_id){
				          			         leftGrid.updateRow(item2.rowIndx, item1);
				          			         return false;
			         			         }
			       			         });
			       		         });
		       				 	$.etDialog.success('复制成功，保存后生效');
		       				 	$.etDialog.close(index);
		       				 }else{
		       					$.etDialog.error('没有查询到排班数据');
		       				 }
       					}
        			});
                }
			});
		});
		
		$("#extend").click(function() {
			var areacode=attend_areacode.getValue().split(',')[0];
			if (!areacode) {
				$.etDialog.error('区域不能为空');
				return;
			}
			
			var curBeginDate=currentDateList[0];
			var curEndDate=currentDateList[currentDateList.length - 1];
			if (!curBeginDate || !curEndDate) {
				$.etDialog.error('排班日期不能为空');
				return;
			}
			
			if (attend_pbrule.getValue()== "") {
				$.etDialog.error('排班规则不能为空');
				return;
			}
			
			if (db_gz.getValue()== "") {
				$.etDialog.error('倒班规则不能为空');
				return;
			} 
			
			$.etDialog.open({
                url: 'extendPage.do?isCheck=false&attend_pbrule='+attend_pbrule.getValue()+'&begin_date='+curBeginDate+'&end_date='+curEndDate,
                title: '继承上周/月',
                width: 450,
                height: 350,
                btn: ['确定', '取消'],
                btn1: function (index, el) {
                   var iframeWindow = window[el.find('iframe').get(0).name];
                   var selDate=iframeWindow.getDate();
                   var is_bh=iframeWindow.getCheck();
                   var sel_begin_date=selDate[0];
                   var sel_end_date=selDate[1];
                   if (!sel_begin_date || !sel_end_date) {
	       				$.etDialog.error('请选择要继承的日期');
	       				return;
      			   }
              
                   if(attend_pbrule.getValue()=="0"){
                	   //按周排班
                	   if(sel_begin_date!=getDateAddDay(sel_end_date,-6)){
                		   $.etDialog.error('不能跨周继承排班数据');
  	       					return;
               			}
                	   
                   }else{
                	   //按月排班
                	   if (sel_begin_date.substring(0,7)!=sel_end_date.substring(0,7)) {
	   	       				$.etDialog.error('不能跨月继承排班数据');
	   	       				return;
        			   } 
                   }
                  
                    var param = [];
                /*     $(data).each(function () {
                        var rowdata = this.rowData;
                        param.push(rowdata.emp_id);
                    });
                         */
                         $.etDialog.confirm('是否继承?', function (index1) {
         					ajaxPostData({
                				url : 'extend.do',
                				data:{
                					
                					pb_qj : $('.pb_qj').text(),
        	        				attend_areacode : areacode,
        	        				pb_gz: attend_pbrule.getValue(),
        	        				db_gz: db_gz.getValue(),
        	        				sel_begin_date : sel_begin_date,
        	        				sel_end_date : sel_end_date,
        	        				begin_date : curBeginDate,
        	        				end_date : curEndDate,
        	        				is_bh: is_bh 
                				},
         					    success: function (res) {
         							$.etDialog.close(index); // 关闭弹窗
         							queryGrid();
         						}
         					})
         					$.etDialog.close(index1); // 关闭弹窗
        	    			});
              /*       //排班规则周
                   	ajaxPostData({
        				url : 'extend.do?isCheck=false',
        				data:{
        					
        					pb_qj : $('.pb_qj').text(),
	        				attend_areacode : areacode,
	        				pb_gz: attend_pbrule.getValue(),
	        				db_gz: db_gz.getValue(),
	        				sel_begin_date : sel_begin_date,
	        				sel_end_date : sel_end_date,
	        				begin_date : curBeginDate,
	        				end_date : curEndDate,
	        				
        				},
	       				success : function(responseData) {
		       				 var rows=responseData.Rows;
		       				 if(rows.length>0){
		       					rows.forEach(function (item1, index1) {
			       			         data.forEach(function (item2, index2) {
			         			         if(item1.emp_id==item2.rowData.emp_id){
				          			         leftGrid.updateRow(item2.rowIndx, item1);
				          			         return false;
			         			         }
			       			         });
			       		         });
		       				 	$.etDialog.success('复制成功，保存后生效');
		       				 	$.etDialog.close(index);
		       				 }else{
		       					$.etDialog.error('没有查询到排班数据');
		       				 }
       					}
        			}); */
                }
			});
			
			
			
		})
		$("#check").click(function() {

            if ($(".pb_qj").text()=="") {
                $.etDialog.error('请先保存排班数据');
                return;
            } 
           
           $.etDialog.confirm('确定审签?', function () {
               ajaxPostData({
                   url: 'auditCountersign.do',
                   data: {
                   	pb_qj: $(".pb_qj").text(),
                   	note: $(".note").val(),
                   	state: 1
                   },
                   success: function (res) {
                   	 if(res.state=="true"){
                   		$("#pbsq").text("已审签");
                   		$(".check_user_name").text(res.check_user_name);
                   		$(".check_date").text(res.check_date);
                   	 }
                	 if(res.is_fc==1){
                    		$("#pbfc").text("已封存");
                    		$(".check_user_name").text(res.check_user_name);
                    		$(".check_date").text(res.check_date);
                    	 }
                   }
               })
           });
            
        });
        $("#uncheck").click(function() {

        	 if ($(".pb_qj").text()=="") {
                  $.etDialog.error('请先保存排班数据');
                  return;
             } 
             
             $.etDialog.confirm('确定销审?', function () {
                 ajaxPostData({
                     url: 'auditCountersign.do',
                     data: {
                     	pb_qj: $(".pb_qj").text(),
                     	note: $(".note").val(),
                     	state: 0
                     },
                     success: function (res) {
                     	 if(res.state=="true"){
                     		$("#pbsq").text("未审签");
                     		$(".check_user_name").text("");
                     		$(".check_date").text("");
                     	 }
                     	 if(res.is_fc==1){
                      		$("#pbfc").text("已封存");
                      		$(".check_user_name").text("");
                      		$(".check_date").text("");
                      	 }
                     }
                 })
             });
        });
        
        
        $("#print").click(function() {
        	
        	if(!leftGrid.getAllData() || leftGrid.getAllData().length==0){
	        	$.etDialog.error("请先查询数据");
				return;
			}
	    
			var heads={
			  		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
			  		  "rows": [
				          {"cell":0,"value":"排班周期："+$(".pb_qj").text(),"colSpan":"4","align":"left"}
			  		  ] 
		  	};
			
			var foots={
		  		  "rows": [
			          {"cell":0,"value":"制表人："+$(".create_user_name").text(),"align":"left"},
			          {"cell":1,"value":"制表时间："+$(".create_date").text(),"align":"left","from":"right"/* ,"colSpan":"2" */},
			          {"cell":0,"value":"审签人："+$(".check_user_name").text(),"align":"left","br":"true"},
			          {"cell":1,"value":"审签时间："+$(".check_date").text(),"align":"left","from":"right"/* ,"colSpan":"2" */},
			          {"cell":0,"value":"备注："+$(".note").val(),"align":"left","br":"true"}
		  		  ] 
		  	};
		  	
		  	var  columns=JSON.stringify(leftGrid.getPrintColumns());
		  	columns=columns.replace(/<br\/>/g,"");
		  	
	   		var printPara={
	   			title: attend_areacode.getText()+"排班表",//标题
	   			columns: columns,//表头
	   			class_name: "com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingService",
				method_name: "queryPBprint",
				bean_name: "hrSchedulingService" ,
				heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
				foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 */
	   		};
	    	
	   		//执行方法的查询条件
	   		printPara['attend_areacode']=code;
	   		printPara['begin_date']=currentDateList[0];
	   		printPara['end_date']=currentDateList[currentDateList.length - 1];
	   		
	    	officeGridPrint(printPara);
        });
	};
	
    var loadLeftGrid = function () {
    	paramLeftObj = {
       		width : 'auto',
       		height : '100%',
			inWindowHeight: true,
   			checkbox : true,
   			freezeCols : 2, //冻结两列
   			usePager:false,
   			columns : leftColumns,
   			editable : true,
   			cellDblClick: cellDblClick, 
            dataModel: {
                getData: function (res) {
                	if(res.error){
                		$.etDialog.error(res.error);
                		return;
                	};
                	
                	is_sq=res.state;
                	is_fc=res.is_fc;
                	
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
                	
                	if(res.db_gz){
                		db_gz.setValue(res.db_gz); //倒班规则
                	}
                	$('#pbsq').text(res.state_name);   //审签状态
                	$('#pbfc').text(res.fc_name); 
                  	return res;
                }
            }
        };

        leftGrid = $("#leGrid").etGrid(paramLeftObj);
        
        /* 单元格点击事件  */
        $("#leGrid").on('click', '.queryDept', function (event) {
        	event.stopPropagation();
            var bcName = $(this).attr('bcName');      //班次名称
            bColCode= $(this).attr('bValue');         //班次id值
            deptText = $(this).attr('dText');         
            deptId= $(this).attr('deptValue');        //科室id名
            deptColCode = $(this).attr('deptID');     //科室id值
            RowIndex = $(this).attr('RowIndx');       //行下标
            empId = $(this).attr('empid');            //职工id
            currentRowData = leftGrid.getDataInPage()[RowIndex];
            
           	evt = window.event || event;
       		evt.target = evt.srcElement ? evt.srcElement : evt.target;
       		// 获取弹窗定位
       		var tdOffset = $(evt.target).parent('td').offset();
       		var distance_x = tdOffset.left + $(evt.target).parent('td').outerWidth();
       		var distance_y = tdOffset.top;
       		// 设定弹窗top定位的最大值
       		if(tdOffset.top + $('#details').outerHeight() + 45 > $('body').height()) {
       			distance_y = $('body').height() - $('#details').outerHeight() - 45;
       		}
       		$("#details").show();
       		$('#details').css({
       			left: distance_x,
       			top: distance_y
       		});
       		
       		$("#details").show(0, function() {
				details_assess.setValue(deptId);
       		});
       		
       		if(is_sq == 1 || is_fc == 1){
        		details_assess.disabled();
        	}else{
	        	details_assess.enabled();
        	}
       		
       		// 点击空白处关闭弹窗
       		$(document).click(function(event){
    	        var _con = $('#details');
    	        if(!_con.is(event.target) && _con.has(event.target).length === 0){
    	           $('#details').hide();
    	        }
    	    });
       		// 关闭按钮
       		$('.close').click(function() {
       			$('#details').hide();
       			$('.close').unbind('click');
       		});
        });
    };
    
    cellDblClick = function (event, ui){

    	if(is_sq == 1 || is_fc == 1){
    		details_assess.disabled();
    		return false;
    	}
    	details_assess.enabled();
    	return true;
    }
	
	</script>
</head>

<body>
	<div class="container">
		<div id="tab" style="width: 100%;">
           	<div class="tab_content">
           		<table class="table-layout" id="children_block">
					<tr>							
						<td class="label "><font size="2" color="red">*</font>区域名称：</td>
						<td class="ipt"><select id="attend_areacode" style="width: 140px;"></select></td>
						<td class="label"><font size="2" color="red">*</font>排班规则：</td>
						<td class="ipt"><select id="attend_pbrule" style="width: 100px;"></select></td>
						<td class="label"><font size="3" color="red">*</font>班次显示：</td>
						<td class="ipt"><select id="db_gz" style="width: 100px;"></select></td>
					</tr>
					<tr>
						<td colspan="6">
							<button id="add">添加人员</button>
							<button id="clear">删除人员</button>
							<button id="check">审签</button>
							<button id="uncheck">销审</button>
							<button id="addAll">批量设置</button>
							<button id="copy">复制上周/月</button>
							<button id="extend">继承上周/月</button>
							<button id="clearPb">清除排班</button>
							<button id="print">打印</button>
							<button id="query">查询</button>
						</td>
					</tr>
				</table>
				
				<div class="togglebtn"></div>
				
		 		<div>
		 			<div class="dateTable">
		 				<div id="dateSlider" title="点击日期查询"></div>
		 				<div id="jump">跳至本周</div>
		 				<div id="save">保存</div>
		 				<div id="pbfc">未封存</div>
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
				    				<textarea class="note" rows="0" cols="20" style="resize:none;margin: 0px; height: 27px; width: 190px;"></textarea>
				    			</li>
					    	</ul>
					    </div>
		 			</div>
					<div id="leGrid"></div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 科室详情弹窗 -->
	<div id="details" class="dialog" style="display:none">
		 <div class="title">
			<span style="padding-left: 6px;">出勤科室</span>
			<span class="close">X</span>
		</div>
		<select id="details_assess" style="width: 170px;"></select>
	</div>
</body>
</html>