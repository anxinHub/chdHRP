<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
<jsp:param value="select,datepicker,dialog,ligerUI,grid" name="plugins" />
</jsp:include>
<script type="text/javascript">
     var start_year;
     var end_year ;
     var flag = 0 ;
     $(function (){
        loadDict()//加载下拉框
        loadHead();
        setTimeout(function () {
        	query();
        }, 100);
     });  
     
   //查询
     function  query(){
    	 var startYear = start_year.getValue() ;
       	 
   		var endYear =  $("#end_year").val();
   		
   		if(!startYear){
    		$.etDialog.error('开始年限不能为空');
     	}
     	if(!endYear){
    		 $.etDialog.error('结束年限不能为空');
   		}
   		if(startYear > endYear){
   			
   			$.etDialog.error('开始年限不能大于结束年限');
   		}
     	var parms=[];
         //根据表字段进行添加查询条件
     	parms.push({name:'year',value:"${year}"}); 
     	parms.push({name:'start_year',value:startYear}); 
     	parms.push({name:'end_year',value:endYear});

     	//加载查询条件
     	grid.loadData(parms,"getGrowRate.do?isCheck=false");
      }

     function loadHead(){
     	grid = $("#maingrid").etGrid({
            columns: [ 
                     { display: '指标编码', name: 'index_code', align: 'center',width:'15%',
 					 		},
 				 	 { display: '指标名称', name: 'index_name', align: 'center',width:'20%',
 					 		},
 					 { display: '科室编码', name: 'dept_code', align: 'center',width:'15%',
 					 		},
 				 	 { display: '科室名称', name: 'dept_name', align: 'center',width:'20%',
 					 		},
 					 {display: '平均增长比例', name: 'grow_rate', align: 'center', width:'27%',
 								render:function(ui){
 									return formatNumber(ui.rowData.grow_rate, 2, 1) + "%";
 								}
 							},
                      ],
                      dataModel:{
         	           	 method:'POST',
         	           	 location:'remote',
         	           	 url:'',
         	           	 recIndx: 'year'
        	             }, 
        	             usePager:false,width: '100%', height: '100%',checkbox: true,
        	             addRowByKey:false,
       	             toolbar: {
       	   	               items: [
       	   						{ type: "button", label: '确定',icon:'check',listeners: [{ click: updateGrowRate}] },
       	   						{ type: "button", label: '关闭',icon:'closethick',listeners: [{ click: this_close}] },
       	   	           	]}
         });

     }
     //更新 增长比例
     function updateGrowRate(){
    	var data = grid.selectGet();
 		if (data.length == 0) {
 			$.etDialog.error('请选择行');
 		} else {
 			  var ParamVo =[];
 			  var err = '' ;
 	          $(data).each(function (){	
             	if(this.rowData.grow_rate){
             		ParamVo.push(
             				this.rowData.year   +"@"+ 
             				this.rowData.index_code +"@"+ 
             				this.rowData.dept_id +"@"+
             				this.rowData.grow_rate
        				) 
             	}else{
             		err += this.rowData.dept_code + this.rowData.dept_name+this.rowData.index_code + this.rowData.index_name +"、" ;
             	}
 	          })
 	          
 	          if(err != ''){
 	        	 $.etDialog.error('以下指标科室指标数据:【'+err.substring(0,err.length-1)+'】增长比例不存在!');
 	          }else{
 	        	 ajaxPostData({
 	                 url: "updateGrowRate.do?isCheck=false",
 	                 data: { ParamVo: ParamVo.toString() },
 	                 success: function(responseData) {
 	                 	parent.query();
 	                 	this_close() ;
 	                 }
 	             });
 	          }
 		 }
     }
     
     function this_close(){
    	 var curIndex = parent.$.etDialog.getFrameIndex(window.name);
         parent.$.etDialog.close(curIndex);
     }
     
	function loadDict(){
		getData("../../../../../queryBudgYearFive.do?isCheck=false&start_year="+"${start_year}", function (data) {
			start_year = $("#start_year").etDatepicker({
				defaultDate: "${start_year}",
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[0].text,
				maxDate: data[4].text,
				todayButton: false,
				onChanged:function(){
					query() ;
				}
			});
		});
		/*
		getData("../../../../../queryBudgYear.do?isCheck=false", function (data) {
			end_year = $("#end_year").etDatepicker({
				defaultDate: "${end_year}",
				view: "years",
				minView: "years",
				dateFormat: "yyyy",
				minDate: data[data.length - 1].text,
				maxDate: data[0].text,
				todayButton: false,
				onChanged:function(){
					query() ;
				}
			});
		});
		*/
    }
	//ajax获取数据
	function getData(url, callback) {
		$.ajax({
			url: url,
			dataType: "JSON",
			type: "post",
			success: function (res) {
				if (typeof callback === "function") {
					callback(res);
				}
			}
		})
	};
  </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div class="main">
		<table class="table-layout">
			<tr>
				<td class="label"><font>开始年限<span style="color:red">*</span>:</font></td>
				<td class="ipt">
					<input type="text" id="start_year" />
				</td>
				<td class="label"><font>结束年限<span style="color:red">*</span>:</font></td>
				<td class="ipt">
					<input type="text" id="end_year" disabled="disabled"  value="${end_year}" validate="{required:true,maxlength:20}" />
                    
				</td>
			</tr>
			<!-- <tr>
				<td class="label"><font>平均增长比例:</font></td>
				<td class="ipt">
					<table>
						<tr>
							<td>
							<input type="text" id="grow_rate" />
							</td>
							<td class="ipt">%</td>
						</tr>
					</table>
				</td>
				
			</tr> -->
		</table>
	</div>
	<div id="maingrid"></div>
    </body>
</html>
