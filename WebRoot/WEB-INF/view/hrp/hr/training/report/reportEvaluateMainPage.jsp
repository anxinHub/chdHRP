<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,datepicker" name="plugins" />
	</jsp:include>
	<style type="text/css">
		* {
			margin: 0;
			padding: 0;
		}  
	    .main .title1 {
            font-size: 18px;
		    font-weight: 700;
		    text-align: center;
        } 
        .main .hos_name{
       	    margin-left: 91px;
    		font-size: 14px;
        }
           @media print {
            	.btn_group{
            		display:none
            	}
            	#treeView{
            		display:none
            	}
            }   
            
	 	button {
        	margin: 0px 5px;
       		box-sizing: border-box;
       		height: 26px;
       		padding-left: 10px;
       		padding-right: 10px;
       		border: 1px solid #aecaf0;
       		background: #c1dbfa;
       		outline: none;
       		border-radius: 2px;
       		cursor: pointer;
       		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
       		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
         	text-align: center
        }
       	table {
       		width: 80%;
    		margin: 0 auto;
		    border-collapse:collapse;
		}
		th {
		    width: 100px;
    		font-size: 14px;
		}
   		td input{
	    	height: 38px;
		    outline: none;
		    width: 100%;
		    border: none;
        	box-sizing: border-box;
    		padding-left: 5px;
		}
		.ztree {
			height: 100%;
		}
    </style>
    <script>
        var grid,year_month,dept_code,train_type,train_way,id_exe,user_hide_data = {};

        $(function () {
            initTree();
            initForm();
            
            $("#search-tree").on('keyup', function(){
    			var $self = $(this);
    			searchTree({
    				tree: tree,
    				value: $self.val(),
    				callback: function(){
    					$self.focus();
    				}
    			});
    		});
        })
      function initForm(){
        /* 	$("#btn_print").click(function (){
        		document.body.innerHTML=document.getElementById('title1').innerHTML+'<br/>'+document.getElementById('hos_name').innerHTML+'<br/>'+document.getElementById('table').innerHTML;;

        		window.print();
        	}) */
        	$("#query").click(function () {
          		 var selectedNode = tree.getSelectedNodes()[0];
          		 if(selectedNode == null){
     	         	
 	         		$.etDialog.error('请选择需要保存的培训计划！');
 	           	 	return;
 	    	 }
          	  ajaxPostData({
                  url: 'queryReportEvaluate.do',
                   data: {
                	  plan_id: selectedNode.id
                   },
                   success: function (res) {
                	   
                	   user_hide_data = {
                               dept_name: res.Rows[0].emp_name || '',
                               dept_id: res.Rows[0].dept_id || '',
                               dept_no: res.Rows[0].dept_no || '',
                              
                           };

                	   document.getElementById("dept_name").value=res.Rows[0].dept_name;
                	   document.getElementById("train_object").value=res.Rows[0].train_object;
                	   document.getElementById("train_date").value=res.Rows[0].train_date;
                	   document.getElementById("train_site").value=res.Rows[0].train_site;
                	   document.getElementById("teacher").value=res.Rows[0].teacher;
                	   document.getElementById("emp_num").value=res.Rows[0].emp_count;
                	   document.getElementById("train_content").value=res.Rows[0].train_content;
                	   
                	   if(res.Rows[0].emp_count!=0)	{
	                	   document.getElementById('tr2').style.display = '';
	                	   document.getElementById('tr3').style.display = '';
	                	   document.getElementById('tr4').style.display = '';
                	
                	   
	                	   document.getElementById("sign_plan_num").value=res.Rows[0].emp_count;
	                	   document.getElementById("in_late_num").value=res.Rows[0].s2;
	                	   document.getElementById("sign_fact_num").value=res.Rows[0].s1+res.Rows[0].s2+res.Rows[0].s3+res.Rows[0].s4;
	                	   document.getElementById("out_early_num").value=res.Rows[0].s3;
	                	   document.getElementById("absent_num").value=res.Rows[0].emp_count-res.Rows[0].s1-res.Rows[0].s2-res.Rows[0].s3-res.Rows[0].s4;
                	   
	                	   //到会率
	                	   if((res.Rows[0].emp_count/(res.Rows[0].s1+res.Rows[0].s2+res.Rows[0].s3+res.Rows[0].s4)) == "Infinity" || (res.Rows[0].emp_count/(res.Rows[0].s1+res.Rows[0].s2+res.Rows[0].s3+res.Rows[0].s4)) == 0) {
	                		   document.getElementById("sign_rate").value = 0;
	                	   }else{
	                		   document.getElementById("sign_rate").value=formatNumber(((res.Rows[0].s1+res.Rows[0].s2+res.Rows[0].s3+res.Rows[0].s4)/res.Rows[0].emp_count),2, 1)*100 + '%';
	                	   }
                	   
                	   }else{
                		   document.getElementById('tr2').style.display = 'none';
                    	   document.getElementById('tr3').style.display = 'none';
                    	   document.getElementById('tr4').style.display = 'none';
                	   }
                	 
                	   
                	   if(res.Rows[0].emp!=0)
                	   {
                		document.getElementById('tr5').style.display = '';
                    	document.getElementById('tr6').style.display = '';
                    	document.getElementById('tr7').style.display = '';
                    	document.getElementById('tr8').style.display = '';
                	   document.getElementById("exam_plan_num").value=res.Rows[0].emp_count;
                	   document.getElementById("highest_score").value=res.Rows[0].highest_score;
                	   document.getElementById("exam_fact_num").value=res.Rows[0].e1+res.Rows[0].e2;
                	   document.getElementById("lowest_score").value=res.Rows[0].lowest_score;
                	   
                	  //参考率
                	   if((res.Rows[0].emp_count/(res.Rows[0].e1+res.Rows[0].e2)) == "Infinity" || res.Rows[0].emp_count/(res.Rows[0].e1+res.Rows[0].e2) == 0) {
                		   document.getElementById("exam_rate").value = 0;
                	   }else{
                		   document.getElementById("exam_rate").value=formatNumber(((res.Rows[0].e1+res.Rows[0].e2)/res.Rows[0].emp_count),2, 1) *100+ '%';
                	   }
                	   
                	   
                	   document.getElementById("ave_score").value=formatNumber(res.Rows[0].ave_score,2,1);
                	   document.getElementById("pass_num").value=res.Rows[0].e1;
                	   
                	  //合格率
                	   if((res.Rows[0].e1/res.Rows[0].emp_count) == "Infinity" || (res.Rows[0].e1/res.Rows[0].emp_count) == 0) {
                		   document.getElementById("pass_rate").value = 0;
                	   }else{
                		   document.getElementById("pass_rate").value=formatNumber((res.Rows[0].e1/res.Rows[0].emp_count),2, 1)*100 + '%';
                	   }
                	   
                	   
                	   
                	   }else{
                		document.getElementById('tr5').style.display = 'none';
                       	document.getElementById('tr6').style.display = 'none';
                       	document.getElementById('tr7').style.display = 'none';
                       	document.getElementById('tr8').style.display = 'none';
                	   }
                	  
                	   
                	
                	    if(res.Rows[0].emp_num!=0) {
                		   
	                		document.getElementById('tr9').style.display = '';
	                    	document.getElementById('tr10').style.display = '';
	                    	document.getElementById('tr11').style.display = '';
	                    	document.getElementById('tr12').style.display = '';
	                    	document.getElementById("bexam_plan_num").value=res.Rows[0].emp_num;
	                  	    document.getElementById("bhighest_score").value=res.Rows[0].bhighest_score;
	                  	    document.getElementById("bexam_fact_num").value=res.Rows[0].b1+res.Rows[0].b2;
	                  	    document.getElementById("blowest_score").value=res.Rows[0].blowest_score;
	                  	   
	                  	  //参考率
	                	   if(res.Rows[0].emp_num/(res.Rows[0].b1+res.Rows[0].b2) == "Infinity" || res.Rows[0].emp_num/(res.Rows[0].b1+res.Rows[0].b2) == 0) {
	                		   document.getElementById("bexam_rate").value = 0;
	                	   }else{
	                		   document.getElementById("bexam_rate").value=formatNumber(((res.Rows[0].b1+res.Rows[0].b2)/res.Rows[0].emp_num),2, 1) *100+ '%';
	                	   }
                  	   
	                  	   document.getElementById("bave_score").value=formatNumber(res.Rows[0].bave_score,2,1);
	                  	   document.getElementById("bpass_num").value=res.Rows[0].b1;
                  	   
                  			//合格率
	                	   if((res.Rows[0].e1/res.Rows[0].emp_count) == "Infinity" || (res.Rows[0].b1/res.Rows[0].emp_num) == 0) {
	                		   document.getElementById("bpass_rate").value = 0;
	                	   }else{
	                		   document.getElementById("bpass_rate").value=formatNumber((res.Rows[0].b1/res.Rows[0].emp_num),2, 1)*100 + '%';
	                	   }
                	   }else{
                		   document.getElementById('tr9').style.display = 'none';
                          	document.getElementById('tr10').style.display = 'none';
                          	document.getElementById('tr11').style.display = 'none';
                          	document.getElementById('tr12').style.display = 'none';  
                	   }   
                   }
                  
          	  })
          		
          	})
        	$("#save").click(function () {
        		
        		
        		 var selectedNode = tree.getSelectedNodes()[0];
    	    	 if(selectedNode == null){
    	         	
    	         		$.etDialog.error('请选择需要保存的培训计划！');
    	           	 	return;
    	    	 }
         	  ajaxPostData({
                 url: 'addReportEvaluate.do',
                  data: {
               	   dept_id:user_hide_data.dept_id,
               	   dept_no:user_hide_data.dept_no,
                   train_object: document.getElementById("train_object").value,
                   train_date: document.getElementById("train_date").value,
                   train_site: document.getElementById("train_site").value,
                   teacher: document.getElementById("teacher").value,
                   emp_num: document.getElementById("emp_num").value,
                   train_content: document.getElementById("train_content").value,
                   
                   
                   sign_plan_num: document.getElementById("sign_plan_num").value,
                   in_late_num: document.getElementById("in_late_num").value,
                   sign_fact_num: document.getElementById("sign_fact_num").value,
                   out_early_num: document.getElementById("out_early_num").value,
                   absent_num: document.getElementById("absent_num").value,
                   sign_rate: document.getElementById("sign_rate").value.replace("%","")/100,
                   
                   
                   exam_plan_num: document.getElementById("exam_plan_num").value,
                   highest_score: document.getElementById("highest_score").value,
                   exam_fact_num: document.getElementById("exam_fact_num").value,
                   lowest_score: document.getElementById("lowest_score").value,
                   exam_rate: document.getElementById("exam_rate").value.replace("%","")/100,
                   ave_score: document.getElementById("ave_score").value,
                   pass_num: document.getElementById("pass_num").value,
                   pass_rate: document.getElementById("pass_rate").value.replace("%","")/100,
                   
                   exam_plan_num2: document.getElementById("bexam_plan_num").value,
                   highest_score2: document.getElementById("bhighest_score").value,
                   exam_fact_num2 : document.getElementById("bexam_fact_num").value,
                   lowest_score2: document.getElementById("blowest_score").value,
                   exam_rate2: document.getElementById("bexam_rate").value.replace("%","")/100,
                   ave_score2: document.getElementById("bave_score").value,
                   pass_num2: document.getElementById("bpass_num").value,
                   pass_rate2: document.getElementById("bpass_rate").value.replace("%","")/100,
                   
                   else_note: document.getElementById("else_note").value,
               	   plan_id: selectedNode.id
                  },
                  success: function (res) {
                     
                  }
                 
         	  })
         		
         	})
         		$("#update").click(function () {
         			/* $("input").attr("disabled",false); */
         			$("input").removeAttr("readonly");
         		})
        }
        function initTree(){
    		tree = $("#mainTree").etTree({
    			async: {
    				enable: true,
    				url: '../examine/getTrainPlanTreeJson.do?isCheck=false'
    			},
    			data : {
    				simpleData : {
    					enable : true,
    					idKey : "id",
    					pIdKey : "pId"
    	    		},
    				keep : {
    					leaf : true
    				},
    				key : {
    					children : "nodes"
    				}
    			},
    			treeNode : {
    				open : true
    			},
    			callback: {
    				onNodeCreated : function(event, treeId, node) {
    					tree.expandNode(node, true, false, false);
    					if (node.nodes && node.level === 0 && node.nodes.length === 0) {
    						treeObj.hideNode(node);
    					}
    				},
    				onClick : function(event, treeId, node) {
    					$("input").attr("readonly","readonly");
    					
    					if(node.level == 1){
    						$("#title1").text(node.name)
    						
    		ajaxPostData({
                  url: 'queryReportEvaluateTable.do?isCheck=fasle',
                   data: {
                	  plan_id: node.id
                   },
                   success: function (res) {
                	   if(res.Total!=0){
                		   user_hide_data = {
                                   dept_name: res.Rows[0].emp_name || '',
                                   dept_id: res.Rows[0].dept_id || '',
                                   dept_no: res.Rows[0].dept_no || '',
                                  
                               };

                    	   document.getElementById("dept_name").value=res.Rows[0].dept_name;
                    	   document.getElementById("train_object").value=res.Rows[0].train_object;
                    	   document.getElementById("train_date").value=res.Rows[0].train_date;
                    	   document.getElementById("train_site").value=res.Rows[0].train_site;
                    	   document.getElementById("teacher").value=res.Rows[0].teacher;
                    	   document.getElementById("emp_num").value=res.Rows[0].emp_num;
                    	   document.getElementById("train_content").value=res.Rows[0].train_content;
                    	   
                    	   if(res.Rows[0].sign_plan_num!=0)
                    	   {
                    	   document.getElementById('tr2').style.display = '';
                    	   document.getElementById('tr3').style.display = '';
                    	   document.getElementById('tr4').style.display = '';
                    	
                    	   
                    	   document.getElementById("sign_plan_num").value=res.Rows[0].sign_plan_num;
                    	   document.getElementById("in_late_num").value=res.Rows[0].in_late_num;
                    	   document.getElementById("sign_fact_num").value=res.Rows[0].sign_fact_num;
                    	   document.getElementById("out_early_num").value=res.Rows[0].out_early_num;
                    	   document.getElementById("absent_num").value=res.Rows[0].absent_num;
                    
	                		   document.getElementById("sign_rate").value=formatNumber(((res.Rows[0].sign_fact_num)/res.Rows[0].sign_plan_num),2, 1)*100 + '%';		     
                    	  // document.getElementById("sign_rate").value=formatNumber(res.Rows[0].sign_rate, 2,1)*100+"%" ;
                    	   }else{
                    		   document.getElementById('tr2').style.display = 'none';
                        	   document.getElementById('tr3').style.display = 'none';
                        	   document.getElementById('tr4').style.display = 'none';
                    	   }
                    	 
                    	   
                    	   if(res.Rows[0].exam_plan_num !=0)
                    	   {
                    		document.getElementById('tr5').style.display = '';
                        	document.getElementById('tr6').style.display = '';
                        	document.getElementById('tr7').style.display = '';
                        	document.getElementById('tr8').style.display = '';
                    	   document.getElementById("exam_plan_num").value=res.Rows[0].exam_plan_num;
                    	   document.getElementById("highest_score").value=res.Rows[0].highest_score;
                    	   document.getElementById("exam_fact_num").value=res.Rows[0].exam_fact_num;
                    	   document.getElementById("lowest_score").value=res.Rows[0].lowest_score;
                    	   document.getElementById("exam_rate").value=formatNumber(((res.Rows[0].exam_fact_num)/res.Rows[0].exam_plan_num),2, 1)*100 + '%';		                	   
                    	   //document.getElementById("exam_rate").value=formatNumber(res.Rows[0].exam_rate, 2,1)*100+"%";
                    	   document.getElementById("ave_score").value=formatNumber(res.Rows[0].ave_score);
                    	   document.getElementById("pass_num").value=res.Rows[0].pass_num;
                		   document.getElementById("pass_rate").value=formatNumber(((res.Rows[0].exam_fact_num)/res.Rows[0].exam_plan_num),2, 1)*100 + '%';		                	   

                    	   }else{
                    		document.getElementById('tr5').style.display = 'none';
                           	document.getElementById('tr6').style.display = 'none';
                           	document.getElementById('tr7').style.display = 'none';
                           	document.getElementById('tr8').style.display = 'none';
                    	   }
                    	  
                    	   
                    	
                    	    if(res.Rows[0].exam_plan_num2!=0)
                    	   {
                    		   
                    		 document.getElementById('tr9').style.display = '';
                        	document.getElementById('tr10').style.display = '';
                        	document.getElementById('tr11').style.display = '';
                        	document.getElementById('tr12').style.display = '';
                        	 document.getElementById("bexam_plan_num").value=res.Rows[0].exam_plan_num2;
                      	   document.getElementById("bhighest_score").value=res.Rows[0].highest_score2;
                      	   document.getElementById("bexam_fact_num").value=res.Rows[0].exam_fact_num2;
                      	   document.getElementById("blowest_score").value=res.Rows[0].lowest_score2;
                      	 document.getElementById("bexam_rate").value=formatNumber(((res.Rows[0].exam_fact_num2)/res.Rows[0].exam_plan_num2),2, 1)*100 + '%';		            
                      	  // document.getElementById("bexam_rate").value=formatNumber(res.Rows[0].exam_rate2, 2,1)*100+"%";
                      	   document.getElementById("bave_score").value=formatNumber(res.Rows[0].ave_score2);
                      	   document.getElementById("bpass_num").value=res.Rows[0].pass_num2;
                      	 document.getElementById("bpass_rate").value=formatNumber(((res.Rows[0].exam_fact_num2)/res.Rows[0].exam_plan_num2),2, 1)*100 + '%';		            
                      	  // document.getElementById("bpass_rate").value=formatNumber(res.Rows[0].pass_rate2, 2,1)*100+"%";
                    	   }
                    	   else{
                    		   document.getElementById('tr9').style.display = 'none';
                              	document.getElementById('tr10').style.display = 'none';
                              	document.getElementById('tr11').style.display = 'none';
                              	document.getElementById('tr12').style.display = 'none';  
                    	   }
                    	    document.getElementById("else_note").value=res.Rows[0].else_note;
                    	  
                    	
                    	   
                	   }else{
                		 

                    	   document.getElementById("dept_name").value='';
                    	   document.getElementById("train_object").value='';
                    	   document.getElementById("train_date").value='';
                    	   document.getElementById("train_site").value='';
                    	   document.getElementById("teacher").value='';
                    	   document.getElementById("emp_num").value='';
                    	   document.getElementById("train_content").value='';
                    	   
                    	 
                    		  document.getElementById('tr2').style.display = 'none';
                       	   document.getElementById('tr3').style.display = 'none';
                       	   document.getElementById('tr4').style.display = 'none';
                    	
                    	   
                    	   document.getElementById("sign_plan_num").value='';
                    	   document.getElementById("in_late_num").value='';
                    	   document.getElementById("sign_fact_num").value='';
                    	   document.getElementById("out_early_num").value='';
                    	   document.getElementById("absent_num").value='';
                    	   document.getElementById("sign_rate").value='' ;
                    	 
                    	 
                    	   
                    	  
                           document.getElementById('tr5').style.display = 'none';
                           document.getElementById('tr6').style.display = 'none';
                           document.getElementById('tr7').style.display = 'none';
                           document.getElementById('tr8').style.display = 'none';
                    	   document.getElementById("exam_plan_num").value='';
                    	   document.getElementById("highest_score").value='';
                    	   document.getElementById("exam_fact_num").value='';
                    	   document.getElementById("lowest_score").value='';
                    	   document.getElementById("exam_rate").value='';
                    	   document.getElementById("ave_score").value='';
                    	   document.getElementById("pass_num").value='';
                    	   document.getElementById("pass_rate").value='';
                    	 
                    	  
                    	   
                    	
                    	   
                    		   
                    	    document.getElementById('tr9').style.display = 'none';
                            document.getElementById('tr10').style.display = 'none';
                            document.getElementById('tr11').style.display = 'none';
                            document.getElementById('tr12').style.display = 'none';  
                        	document.getElementById("bexam_plan_num").value='';
                      	   document.getElementById("bhighest_score").value='';
                      	   document.getElementById("bexam_fact_num").value='';
                      	   document.getElementById("blowest_score").value='';
                      	   document.getElementById("bexam_rate").value='';
                      	   document.getElementById("bave_score").value='';
                      	   document.getElementById("bpass_num").value='';
                      	   document.getElementById("bpass_rate").value='';
                    	    
                    	    document.getElementById("else_note").value='';
                    	  
                    	
                	   }
                	  
                	   
                	   
                	   
                	   
                	   
                   }
                  
          	  })
    					}
    				}
    			},
    		});
    	}

      
    </script>
</head>

<body>
  <div class="container">
      <div class="left border-right" id="treeView">
            <div class="search-form">
                <label>快速定位</label>
                <input class="text-input" type="text" id="search-tree">
            </div>
            <div id="mainTree"></div>
      </div> 
        
         <div class="main" style="width: 83%;overflow: auto;">
            <div class="btn_group">
                 <button id="query"  >生成</button>
                 <button id="update"  >修改</button>
                 <button id="save" >保存</button>
                <button id="btn_print" onclick="window.print()" ctrl="true" >打 印</button>
            </div>
            <div class="title1" id="title1"></div>
            <div class="hos_name" id="hos_name"> 编制单位 ：${hos_name}</div>
            <div class="content" style="text-align: center;" id="table">
              	<table class="table-layout" border="1">
					<tr>
				  		<th rowspan="3">培训项目</th>
						<td>培训部门：</td>
						<td><input id="dept_name" readonly/></td>
						<td >培训对象：</td>
						<td><input  id="train_object" readonly/></td>
					</tr>
					<tr>
						<td>培训时间：</td>
						<td><input id="train_date" readonly/></td>
						<td>培训地点：</td>
						<td><input id="train_site" readonly/></td>
					</tr>
					<tr>
						<td>授课人：</td>
						<td><input id="teacher" readonly/></td>
						<td>参加人数：</td>
						<td><input  id="emp_num" readonly/></td>
					</tr>
					<tr>
			  			<th>培训内容</th>
				   		<td  colspan="4" ><input  id="train_content"  readonly></td>
				  	</tr>
				  	<tr id="tr2" style="display:none">
				  		<th rowspan="3">签到评价</th>
						<td>应到人数：</td>
						<td><input  id="sign_plan_num" readonly/></td>
						<td >迟到人数：</td>
						<td><input  id="in_late_num" readonly/></td>
					</tr>
					<tr id="tr3" style="display:none">
						<td>实到人数：</td>
						<td><input  id="sign_fact_num" readonly/></td>
						<td>早退人数：</td>
						<td><input  id="out_early_num" readonly/></td>
					</tr>
					<tr id="tr4" style="display:none">
						<td>缺席人数：</td>
						<td><input  id="absent_num" readonly/></td>
						<td>到会率：</td>
						<td><input  id="sign_rate" readonly/></td>
					</tr>
					<tr  id="tr5" style="display:none">
				  		<th rowspan="4">考核评价</th>
						<td>应考核人数：</td>
						<td><input  id="exam_plan_num" readonly/></td>
						<td>最高分：</td>
						<td><input  id="highest_score" readonly/></td>
					</tr>
					<tr id="tr6" style="display:none">
						<td>实际考核人数：</td>
						<td><input  id="exam_fact_num" readonly/></td>
						<td>最低分：</td>
						<td><input  id="lowest_score" readonly/></td>
					</tr>
					<tr id="tr7" style="display:none">
						<td>参考率：</td>
						<td><input  id="exam_rate" readonly/></td>
						<td>平均分：</td>
						<td><input  id="ave_score" readonly/></td>
					</tr>
					<tr id="tr8" style="display:none">
						<td>合格人数：</td>
						<td><input  id="pass_num" readonly/></td>
						<td>合格率：</td>
						<td><input  id="pass_rate" readonly/></td>
					</tr>
					<tr id="tr9" style="display:none">
				  		<th rowspan="4">补考评价</th>
						<td>应考核人数：</td>
						<td><input  id="bexam_plan_num" readonly></td>
						<td>最高分：</td>
						<td><input  id="bhighest_score" readonly/></td>
					</tr>
					<tr id="tr10" style="display:none">
						<td>实际考核人数：</td>
						<td><input  id="bexam_fact_num" readonly/></td>
						<td >最低分：</td>
						<td ><input  id="blowest_score" readonly/></td>
					</tr>
					<tr id="tr11" style="display:none">
						<td>参考率：</td>
						<td><input  id="bexam_rate" readonly/></td>
						<td>平均分：</td>
						<td><input  id="bave_score" readonly/></td>
					</tr>
					<tr id="tr12" style="display:none">
						<td>合格人数：</td>
						<td><input  id="bpass_num" readonly/></td>
						<td>合格率：</td>
						<td><input  id="bpass_rate" readonly/></td>
					</tr>
					<tr>
				  		<th>其他评价</th>
				   		<td  colspan="4" ><input  id="else_note" readonly/></td>
				  	</tr>
				 </table>
            </div>
        </div>
    </div>
</body>

</html>
