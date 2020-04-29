<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,datepicker,upload,grid,validate" name="plugins" />
</jsp:include>
<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
<script>
        var attend_code, emp_id, sex, dept_name, duty, photo_file;;
        var application_date, birth_date, workage, attend_xjbegdate, formValidate,attend_xjenddate,attend_xjdays,startDateTime,endDateTime;
        var photo;
        var user_hide_data = {};
        
        function initValidate(){
        	formValidate = $.etValidate({
                items: [
                    { el: $("attend_xjyear"), required: true },
                    { el: user_hide_data.emp_id, required: true },
                ]
            });
        }
        
        function initDict(){
        	
        	
        	attend_xxjreg_date = $("#attend_xxjreg_date").etDatepicker({
                defaultDate: true,
            });
        	
        	photo_file  = $("#photo").etUpload();
            photo_file.setValue("${photo}");
        	
            //休假申请编码
            attend_xjapply_code = $("#attend_xjapply_code").etSelect({
            	url : "queryApplyCode.do?isCheck=false&attend_xjapply_code=" + "${attend_xjapply_code}",
   			 	defaultValue: "${attend_xjapply_code}",
   			 	onChange: function (value) {
	   			  	ajaxPostData({
	                  	url: 'queryApplying.do?isCheck=false',
	                  	data: {
	                	  	attend_xjapply_code: value
	                  	},
	                  	success: function (res) {
	                      	user_hide_data = {
	                          	emp_name: res.emp_name || '',
	                          	emp_id: res.emp_id || '',
	                            attend_code: res.attend_code || '',
	                          	attend_xjbegdate_ampm: res.attend_xjbegdate_ampm ,
	                          	attend_xjbegdate: res.attend_xjbegdate || '',
	                          	attend_xjenddate: res.attend_xjenddate || '',
	                          	sex_name: res.sex_name || '',
	                          	birthday: res.birthday || '',
	                          	sex_name: res.sex_name || '',
	                          	worktime: res.worktime || '',
	                          	workage: res.workage || '',
	                          	worktime: res.worktime || '',
	                          	residue_days: res.residue_days || ''
	                      	};
	                      	$("#emp_name").val(res.emp_name || '');
	                      	$("#dept_name").val(res.dept_name || '');
	                      	$("#sex_name").val(res.sex_name || '');
	                      	$("#birthday").val(res.birthday || '');
	                      	$("#workage").val(res.workage || '');
	                      	$("#sex_name").val(res.sex_name || '');
	                      	$("#duty_name").val(res.duty_name || '');
	                      	$("#worktime").val(res.worktime || '');
	                      	photo_file.setValue(res.photo)
	                      	$("#birthplace").val(res.birthplace || '');
	                      	$("#attend_xj_reason").val(res.attend_xj_reason || '');
	                      	$("#attend_xjdays").val(res.attend_xjdays || '');
	                      	$("#attend_xjenddate").val(res.attend_xjenddate || '');
	                      	$("#attend_xjbegdate").val(res.attend_xjbegdate || '');
	                      	$("#residue_days").val(res.residue_days);
	                      	$("#xjdays").val(res.xjdays);
	                      	$("#attend_ed").val(res.attend_ed );
	                      	$("#attend_name").val(res.attend_name || '');
	                      	$("#attend_xjyear").val(res.attend_xjyear || '');
	                      	$("#attend_xjreg_date").val(res.attend_xjreg_date || '');
	                      	$("#attend_xjbegdate_ampm_name").val(res.attend_xjbegdate_ampm_name || '');
	                      	$("#attend_xjenddate_ampm_name").val(res.attend_xjenddate_ampm_name || '');
	                  	},
	   			  		})
   			 		}
   			 });
            
            application_date = $("#application_date").etDatepicker();
            birth_date = $("#birth_date").etDatepicker();
            workage = $("#workage").etDatepicker();
            
            //申请回院上班时间
            attend_xxj_backtime = $("#attend_xxj_backtime").etDatepicker({
            	defaultDate : '${attend_xxj_backtime}',
            		onChange: function (date) {

            			if( endDateTime.getValue() !=""){
        	    			validTime = isValidDateTime($("#attend_xjbegdate").val(), "${attend_xjbegdate_ampm}", $("#attend_xjenddate").val(), "${attend_xjenddate_ampm}",attend_xxj_backtime.getValue(),endDateTime.getValue());
        	 			};
        	 			if(validTime && $("#attend_xjbegdate").val() !="" && "${attend_xjbegdate_ampm}" !="" && attend_xxj_backtime.getValue() !="" && endDateTime.getValue() !=""){
        	 				computTime();
        	 			};
            		}
            } );
            
          //申请回院上班时间上下午
        	endDateTime = $("#attend_xxjbegdate_ampm").etSelect({
        		showClear: false,
                options: [
                    { id: '0', text: '上午' },
                    { id: '1', text: '下午' },
                ],
                defaultValue: "${attend_xxjbegdate_ampm}",
				onChange:function(value){
					
					if( attend_xxj_backtime.getValue() !=""){
    	    			validTime = isValidDateTime($("#attend_xjbegdate").val(), "${attend_xjbegdate_ampm}", $("#attend_xjenddate").val(), "${attend_xjenddate_ampm}",attend_xxj_backtime.getValue(),endDateTime.getValue());
    	 			};
    	 			if(validTime && $("#attend_xjbegdate").val() !="" && "${attend_xjbegdate_ampm}" !="" && attend_xxj_backtime.getValue() !="" && endDateTime.getValue() !=""){
    	 				computTime();
    	 			};
                }  
            });
          
        	/* 计算时间 */
    		var computTime = function(){
    			ajaxPostData({
                	url: 'countXJDays.do?isCheck=false',
                	data:{
                		beginDate : $("#attend_xjbegdate").val(),
            			endDate : attend_xxj_backtime.getValue(),
            			beginTime : "${attend_xjbegdate_ampm}",
            			endTime : endDateTime.getValue(),
            			attend_code :user_hide_data.attend_code!=undefined ? user_hide_data.attend_code : "${attend_code}",
                		emp_id : user_hide_data.emp_id!=undefined ? user_hide_data.emp_id : "${emp_id}"
                	},
               		success:function(data){
               			//总休假天数
						var daysRemain = parseFloat(data.days);
						//休假天数
						var attendXjdays = parseFloat($("#attend_xjdays").val());
						if(daysRemain > attendXjdays){
							$.etDialog.warn("实际休假天数不能大于申请表休假天数!");
							$("#attend_xxjdays").val('');
						}else{
							$("#attend_xxjdays").val(daysRemain);
							if("${attend_ed_is}" == "1"){
								var attendEd = parseFloat($("#attend_ed").val());
								if($("#xjdays").val()==$("#attend_xjdays").val()){
									$("#residue_days").val(attendEd - parseFloat($("#xjdays").val()-$("#attend_xjdays").val()) - daysRemain);
								}else{
									$("#residue_days").val(attendEd+daysRemain-parseFloat($("#xjdays").val()-$("#attend_xjdays").val())  );
								}
								
							}
						}
             		}
    			});
    		};
    		
    		//检验时间是否合法
    		function isValidDateTime(beginDate, beginTime, endDate, endTime,backDate,backTime) {
    			var bDate = Date.parse(beginDate);
    			var eDate = Date.parse(endDate);
    			var backDates = Date.parse(backDate);
    			
    			if(backDates < bDate) {
    				$.etDialog.warn("申请回院上班时间必须大于开始时间");
    				return false;	
    			}else if(backDates > eDate) {
    				$.etDialog.warn("申请回院上班时间必须小于结束时间");
    				return false;
    				
    			}else if(backDates == bDate){
    				if(backTime == '1'){
    					return true;
    				}else{
    					if(beginTime == '1'){
    						endDateTime.clearItem();
    						$.etDialog.warn("申请回院上班时间必须小于开始时间");
    						return false;
    					}else{
    						return true;
    					}
    				}
    			}else if(backDates == eDate){
    				if(endTime == '1'){
    					return true;
    				}else{
    					if(backTime == '1'){
    						endDateTime.clearItem();
    						$.etDialog.warn("申请回院上班时间必须小于结束时间");
    						return false;
    					}else{
    						return true;
    					}
    				}
    			}else {
    				return true;
    			}
    		};	
          
          
        	$("#save").click(function() {
            	var endTime = endDateTime.getValue();
    			var endDate = attend_xxj_backtime.getValue();
            	if(attend_xjapply_code.getValue() == ""){
       	       	 	$.etDialog.warn("请选择休假编码!");
       	       	 	return;
              	}else if(endDate =='' || endDate ==null || endDate =='underfined'){
              		$.etDialog.warn("请选择回院时间");
       	       	 	return;
              	}else if(endTime =='' || endTime ==null || endTime =='underfined'){
              		$.etDialog.warn("请选择上午回院还是下午回院");
       	       	 	return;	
            	}else{
   	    			validTime = isValidDateTime($("#attend_xjbegdate").val(), "${attend_xjbegdate_ampm}", $("#attend_xjenddate").val(), "${attend_xjenddate_ampm}",attend_xxj_backtime.getValue(),endDateTime.getValue());
    	 			if(validTime){
    	 				ajaxPostData({
                            url: 'updateTerminateleave.do',
                            data: {
                            	attend_xxjapply_code: $("#attend_xxjapply_code").val(),
                            	attend_xjapply_code:attend_xjapply_code.getValue(),
                   	 		   	attend_xxj_backtime : $("#attend_xxj_backtime").val(),
                   	 			attend_xxjbegdate_ampm:  endDateTime.getValue(),
                   	 			attend_xxjdays :$("#attend_xxjdays").val(),
                   	 			attend_xxj_note:$("#attend_xxj_note").val(),
                            },
                            success: function (responseData) {
                              	$.etDialog.success(
	       							'修改成功',
	       							 function (index, el) {
	       								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	       		                            
	       		                            var parentFrameName = parent.$.etDialog.parentFrameName;
	       		                            var parentWindow = parent.window[parentFrameName];
	       		                            parentWindow.query(); 
	       		                            parent.$.etDialog.close(curIndex);
	       							    }
	       							)
                              },
                		})
    	 			};
            	}
            })
            
            $("#close").click(function() {
    			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
    			parent.$.etDialog.close(curIndex);
    		})
        };
        
        $(function () {
        	initDict();
            
            if('${attend_xjstate}' != 0 ){
           	   btn=document.getElementById('save');
           	   btn.disabled=true;
            }
        })
    </script>
</head>

<body style="overflow:hidden;">
    <div class="flex-wrap">
		<table class="flex-item-1 table-layout" border="0">
            <tr>
        <input id="attend_xj_add2" type="hidden" value="${attend_xj_add2 }"/>
        	 <input id="emp_id"  value="${emp_id}"  type="hidden" >
        		 	  <input id="attend_code" value="${attend_code}"  type="hidden" >
                <td class="label">休假申请编码：</td>
                <td class="ipt">
                  <select id="attend_xjapply_code" style="width:180px;"></select>
                        
                </td>

               <td class="label">登记日期：</td>
                <td class="ipt">
                    <input id="attend_xjreg_date" type="text" value="${attend_xjreg_date}" disabled>
                </td>
                  <td class="label">职工名称：</td>
                <td class="ipt">
                    <input id="emp_name" type="text" value="${emp_name}"  disabled>
                </td>
            </tr>
            <tr>
               <td class="label">部门名称：</td>
                <td class="ipt">
                    <input id="dept_name" type="text" value="${dept_name}" disabled/></select>
                </td>

                <td class="label">职务：</td>
                <td class="ipt">
                    <input id="duty_name" type="text" value="${duty_name}" disabled />
                </td>
 				 <td class="label">出生年月：</td>
                <td class="ipt">
                    <input id="birthday" type="text" value="${birthday}" disabled />
                </td>
              
            </tr>
            <tr>
                <td class="label">性别：</td>
                <td class="ipt">
                  <input id="sex_name" type="text"  value="${sex_name}" disabled />
                </td>

              

                <td class="label" style="width:90px;">参加工作年月：</td>
                <td class="ipt">
                    <input id="worktime" type="text" value="${worktime}" disabled/>
                </td>

                <td class="label">工龄：</td>
                <td class="ipt">
                    <input id="workage" type="text" value="${workage}" disabled />
                </td>
            </tr>
            <tr>
                 <td class="label">籍贯：</td>
                <td class="ipt">
                    <input id="birthplace" type="text" value="${birthplace}" disabled />
                </td>
                <td class="label">休假年份：</td>
                  <td class="ipt">
                    <input id="attend_xjyear" type="text" value="${attend_xjyear}" disabled/>
                </td>
             	<td class="label">休假类型：</td>
                    <td class="ipt">
                      <input id="attend_name" type="text" value="${attend_name}" disabled/>
                </td>
            </tr>
            <tr>
             <td class="label">休假总额：</td>
                <td class="ipt">
                    <input id="attend_ed"  type="text" value="${attend_ed}" disabled>
                </td>
                    <td class="label">已休天数：</td>
                <td class="ipt">
                    <input id="xjdays"  type="text" value="${xjdays}" disabled>
                </td>
                    <td class="label">休假余额：</td>
                <td class="ipt">
                    <input id="residue_days" style="color: red;" type="text" value="${residue_days}" disabled>
                </td>
            </tr>
            <tr>
                <td class="label">开始时间：</td>
                <td class="ipt">
                    <input id="attend_xjbegdate" type="text" value="${attend_xjbegdate}" style="width:89px;" disabled>
                   <input id="attend_xjbegdate_ampm_name" type="text" value="${attend_xjbegdate_ampm_name}" style="width:89px;" disabled>
                </td>
                <td class="label">结束时间：</td>
                <td class="ipt">
                    <input id="attend_xjenddate" type="text" value="${attend_xjenddate}" style="width:89px;" disabled>
                    <input id="attend_xjenddate_ampm_name" type="text" value="${attend_xjenddate_ampm_name}" style="width:89px;" disabled>
                </td>
                <td class="label">休假天数：</td>
                <td class="ipt">
                    <input id="attend_xjdays" type="text" value="${attend_xjdays}" disabled>
                </td>
            </tr>
            <tr>
                <td class="label">休假原因：</td>
                <td class="ipt" colspan="3">
                    <input id="attend_xj_reason" style="width: 93%;" value="${attend_xj_reason}" type="text" disabled>
                </td>
            </tr>
        </table>
        <div class="upload-photo-form">
                    <div id="photo"></div>
                    <span>照片</span>
                </div>

    </div>
    <div class="main flex-wrap">
        <table class="flex-item-1 table-layout">
         <tr>
      <td class="label">销假申请编号：</td>
                <td class="ipt">
                    <input id="attend_xxjapply_code" value="${attend_xxjapply_code }" type="text" disabled />
                        
                </td>

                <td class="label">登记日期：</td>
                <td class="ipt">
                    <input id="attend_xxjreg_date" value="${attend_xxjreg_date }" type="text" disabled>
                </td>
    </tr>
    <tr>
        <td class="label">申请休假结束时间：</td>
                <td class="ipt">
                    <input id="attend_xxj_backtime" type="text" style="width:89px;" >
                    <select id="attend_xxjbegdate_ampm" style="width:89px;"></select>
                </td>
                <td class="label">休假天数：</td>
                <td class="ipt">
                    <input id="attend_xxjdays" type="text" value="${attend_xxjdays}" disabled>
                </td>
    </tr>
    <tr>
         <td class="label">备注：</td>
                <td class="ipt" colspan="3">
                    <input id="attend_xxj_note" style="width: 93%;" value="${attend_xxj_note }" type="text" >
                </td>
    </tr>
    </table>
    </div>
    <div class="button-group btn">
        <button id="save" >保存</button>
        <button id="close" >关闭</button>
    </div>
    <div id="mainGrid"></div>
</body>

</html>