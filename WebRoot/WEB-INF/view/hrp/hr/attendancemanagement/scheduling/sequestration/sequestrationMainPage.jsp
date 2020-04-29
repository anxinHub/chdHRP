<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="select,tree,hr,validate,datepicker,dialog,grid,form"
		name="plugins" />
</jsp:include>
<style type="text/css">
.mode {
	border: 1px solid #aecaf0;
	width: 546px;
	height: 120px;
	margin-left: 200px;
}

td.tip span {
	font-weight: bold;
	color: red;
}
</style>
<script>
        var method_grid, tree, func_type_select,  main_col_code, tran_freq
        var active_form;
        var exec_select_hour,
        exec_select_minutes,
        exec_select_seconds,
        exec_select_month,
        exec_select_day,
        exec_select_season,
        exec_select_week,
        exec_select_times;
        var normal_form_validate, active_form_validate,yesorno;

        $(function () {
            init();
        });
        function init() {
     
            initSelect();
         
            initValidate();
         	  query();
          	 
        }
      
        //查询周封存
        function query(){
        	//周封存
        	ajaxPostData({
        		url: 'querySequestration.do',
               
                success: function (response) {
                	console.log(response);
                	for (var i=0;i<response.Rows.length;i++)
                	{if(response.Rows[i].attend_pbrule==0){
        				if(response.Rows[i].attend_pbisfile==1){
        					$("input[name='yesorno']:eq(0)").prop("checked", "checked");
                  			if(response.Rows[i].attend_pbfile_set=="D"){
                  				 //changeRunTime(response.Rows[i].attend_pbfile_set);
                  				 	tran_freq.setValue(response.Rows[i].attend_pbfile_set);
                  			$("#exec_select_times").val(response.Rows[i].attend_pbfle_val);
                  			}else if(response.Rows[i].attend_pbfile_set=="WW"){
                  				 changeRunTime(response.Rows[i].attend_pbfile_set);
                  				tran_freq.setValue(response.Rows[i].attend_pbfile_set);
                  				exec_select_sWeek.setValue("1");
                  				exec_select_weekDay.setValue(response.Rows[i].attend_pbfle_val); 
                  			}
        				}else{
                  			if(response.Rows[i].attend_pbfile_set=="D"){
                  				 //changeRunTime(response.Rows[i].attend_pbfile_set);
                  				 	tran_freq.setValue(response.Rows[i].attend_pbfile_set);
                  			$("#exec_select_times").val(response.Rows[i].attend_pbfle_val);
                  			}else if(response.Rows[i].attend_pbfile_set=="WW"){
                  				 changeRunTime(response.Rows[i].attend_pbfile_set);
                  				tran_freq.setValue(response.Rows[i].attend_pbfile_set);
                  				exec_select_sWeek.setValue("1");
                  				exec_select_weekDay.setValue(response.Rows[i].attend_pbfle_val); 
                  			}
        					exec_select_weekDay.disabled();
        					exec_select_sWeek.disabled();
                      			tran_freq.disabled(); 
                      			 document.getElementById("exec_select_times").disabled=true;
                        		$("input[name='yesorno']:eq(1)").prop("checked", "checked");
        				}
        				}else{
            				if(response.Rows[i].attend_pbisfile==1){
                      			$("input[name='yesornoM']:eq(0)").prop("checked", "checked");
                      			if(response.Rows[i].attend_pbfile_set=="DM"){
                      				tran_freqM.setValue(response.Rows[i].attend_pbfile_set);
                      				//changeRunTimeM(response.Rows[i].attend_pbfile_set);
                      			$("#exec_select_timesM").val(response.Rows[i].attend_pbfle_val);
                      			}else if(response.Rows[i].attend_pbfile_set=="Mon"){
                      				tran_freqM.setValue(response.Rows[i].attend_pbfile_set);
                      				changeRunTimeM(response.Rows[i].attend_pbfile_set);
                      				exec_select_season.setValue("1");
                      			 	exec_select_day.setValue(response.Rows[i].attend_pbfle_val);
                      			}
                      		} else{
                      			if(response.Rows[i].attend_pbfile_set=="DM"){
                      				tran_freqM.setValue(response.Rows[i].attend_pbfile_set);
                      				//changeRunTimeM(response.Rows[i].attend_pbfile_set);
                      			$("#exec_select_timesM").val(response.Rows[i].attend_pbfle_val);
                      			}else if(response.Rows[i].attend_pbfile_set=="Mon"){
                      				tran_freqM.setValue(response.Rows[i].attend_pbfile_set);
                      				exec_select_season.setValue("1");
                      				changeRunTimeM(response.Rows[i].attend_pbfile_set);
                      			 	exec_select_day.setValue(response.Rows[i].attend_pbfle_val);
                      			};
                      			tran_freqM.disabled();
                      			exec_select_season.disabled();
                    			exec_select_day.disabled();
                	           document.getElementById("exec_select_timesM").disabled=true;
                	           $("input[name='yesornoM']:eq(1)").prop("checked", "checked");
                      		}
            		 }
                       
                     }
                  	
                },
            });
        }
        function initSelect() {
        /* 	hos_name = $("#hos_name").etSelect({
                url: '../../queryHosInfoSelect.do?isCheck=false',
                defaultValue: '${sessionScope.hos_id}'
            }); */
   
        	//切换周是否封存
        	$('input[type=radio][name=yesorno]').change(function(){
        		if (this.value == '0') {
        			tran_freq.disabled();
        			exec_select_weekDay.disabled();
        	           exec_select_sWeek.disabled();
        	           document.getElementById("exec_select_times").disabled=true;
        	           $("#exec_select_times").val("");
        	    /*        tran_freq.setValue("");
        	       	exec_select_weekDay.setValue("");
        	        exec_select_sWeek.setValue(""); */
                }else{
                	tran_freq.enabled();
                	exec_select_weekDay.enabled();
       	           exec_select_sWeek.enabled();
       	        $(".select-clear-button").css({display:"none"});
       	        document.getElementById("exec_select_times").disabled=false;
                }
        		
        	});
        	//切换月是否封存
        	$('input[type=radio][name=yesornoM]').change(function(){
        		if (this.value == '0') {
        			exec_select_season.disabled();
        			exec_select_day.disabled();
        			tran_freqM.disabled();
        			 document.getElementById("exec_select_timesM").disabled=true;
        			 $("#exec_select_timesM").val("");
        		/* 		exec_select_day.setValue("");
        				tran_freqM.setValue("");
        				exec_select_weekDay.setValue(""); */
                }else{
                	exec_select_season.enabled();
        			exec_select_day.enabled();
        			tran_freqM.enabled();
        			$(".select-clear-button").css({display:"none"});
        			//document.getElementById("tran_freqM").display='none';
        			 document.getElementById("exec_select_timesM").disabled=false;
               }
        		
        	});
        	//周封存设置
        	tran_freq = $("#tran_freq").etSelect({
        		  showClear: false,
                options: [
                    { id: 'D', text: '按排班后固定天数封存' },
                    { id: 'WW', text: '按固定日期封存' },
                 
                ],
                onInit: function (val) {
                    changeRunTime(val);
                },
                onChange: function (val) {
                    changeRunTime(val);
                }
            });
        	//月封存设置
            tran_freqM= $("#tran_freqM").etSelect({
            	showClear: false,
                options: [
                          { id: 'DM', text: '按排班后固定天数封存' },
                          { id: 'Mon', text: '按固定日期封存' },
                       
                      ],
                      onInit: function (val) {
                          changeRunTimeM(val);
                      },
                      onChange: function (val) {
                          changeRunTimeM(val);
                      }
                      
                  });
            // 生成静态选择数据
            var simpleNumberOptions = function (times, isAdd) {
                var options = [];
                var startNum = 0;

                if (isAdd) {
                    startNum = 1;
                    times += 1;
                }
                if (times) {
                    for (var i = startNum; i < times; i++) {
                        var pre = i < 10 ? '0' : '';
                        var value = pre + i;

                        options.push({
                            id: value,
                            text: value
                        })
                    }
                }
                return options;
            };
            // 执行时机
            
            // 每月
            exec_select_season = $("#exec_select_season").etSelect({
                options: [
                    { id: '1', text: '每月' },
                 
                ],
                showClear: false
            });
            // 每周
            exec_select_sWeek = $("#exec_select_sWeek").etSelect({
                options: [
                    { id: '1', text: '每周' },
                 
                ],
                showClear: false
            });
            // 月
            exec_select_month = $("#exec_select_month").etSelect({
                options: simpleNumberOptions(12, true),
                showClear: false
            });
            // 天
            var dayOptions = simpleNumberOptions(31, true);
            dayOptions.push({
                id: 'last_day',
                text: '最后一天'
            })
            exec_select_day = $("#exec_select_day").etSelect({
                options: dayOptions,
                showClear: false
            });
            // 周
            exec_select_weekDay = $("#exec_select_weekDay").etSelect({
                options: [
                    { id: '1', text: '周一' },
                    { id: '2', text: '周二' },
                    { id: '3', text: '周三' },
                    { id: '4', text: '周四' },
                    { id: '5', text: '周五' },
                    { id: '6', text: '周六' },
                    { id: '7', text: '周日' },
                ],
                showClear: false
            });
       
            $("#save").click( function (){
               /*  if (!normal_form_validate.test()) {
                    return;
                } */
             
                var data = {
                		attend_pbisfile : $('input[name="yesorno"]:checked ').val(),
                		attend_pbfile_set : tran_freq.getValue(),
                		attend_pbisfileM: $('input[name="yesornoM"]:checked ').val(),
                		attend_pbfile_setM: tran_freqM.getValue(),
                };
           
                // 周封存设置规则
                var tran_freq_value = tran_freq.getValue();
                data.tran_freq = tran_freq_value;
                // 月封存设置规则
                var tran_freq_valueM = tran_freqM.getValue();
                data.tran_freqM = tran_freq_valueM;
                
                if(tran_freq_valueM=="DM"){
                	data.attend_pbfle_valM =$("#exec_select_timesM").val();
                }else{
                	data.attend_pbfle_valM=exec_select_day.getValue();
                }
                if(tran_freq_value=="D"){
                	data.attend_pbfle_val =$("#exec_select_times").val();
                }else{
                	data.attend_pbfle_val=exec_select_weekDay.getValue();
                }
               
        
			              　　var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/ 
			            　
			           
                if(data.attend_pbisfile==1  ){
                	 if (!normal_form_validate.test()) {
                         return;
                     }
               	if( data.tran_freq=="D"&&data.attend_pbfle_val==""){
                	   $.etDialog.error('周封存按排班后固定天数封存请填写封存天数');
                       return;
                }
              	if( data.tran_freq=="WW"&&data.attend_pbfle_val=="")	{
              	   $.etDialog.error('周封存按固定日期封存请填选择封存时间');
                   return;
                }
               　var nubmer1 =  data.attend_pbfle_val;
   			
	         　　if (!re.test(nubmer1)) {
	         　　　   $.etDialog.error('周封存请填写数字');
	            return;
	         　　　　
	         　　}
                }
                if(data.attend_pbisfileM==1){
                	 if (!normal_form_validate.test()) {
                         return;
                     }
                   	if( data.tran_freqM=="DM"&&data.attend_pbfle_valM==""){
                 	   $.etDialog.error('月封存按排班后固定天数封存请填写封存天数');
                        return;
                 }
               	if( data.tran_freqM=="Mon"&&data.attend_pbfle_valM=="")	{
               	   $.etDialog.error('月封存按固定日期封存请填选择封存时间');
                    return;
                 }
	             var nubmer =  data.attend_pbfle_valM;
	        			
			            　　if (!re.test(nubmer)) {
			            　　　   $.etDialog.error('月封存请填写数字');
			               return;
			            　　　　
			            　　}
                }
              /*   var ParamVo = [];
                $(data).each(function () {
                ParamVo.push(
                		attend_pbisfile:
                		attend_pbfile_set:
                		attend_pbfle_val:	
                
                );}) */
                ajaxPostData({
                    url: 'addSequestration.do?isCheck=false',
                    data: data,
                    success: function () {
                      
                    },
                    delayCallback: true
                })
            })
        }

        function initValidate() {
            normal_form_validate = $.etValidate({
                items: [
                    { el: $("#tran_freq"), required: true },
                    { el: $("#tran_freqM"), required: true },
                   /*  { el: $("#exec_select_times"), required: true },
                    { el: $("#exec_select_timesM"), required: true },
                    { el: $("#exec_select_weekDay"), required: true },
                    { el: $("#exec_select_day"), required: true }, */
                ]
            });
        }
        
        function getRunTimeInput(rate) {
            var ipts = [];

            switch (rate) {
                case 'D':
                    ipts = [ 'exec_select_times' ];
                    break;
              
                    break;
                case 'WW':
                    ipts = [ 'exec_select_sWeek','exec_select_weekDay' ];
                    break;
             
              
               
            }
            return ipts;
        }
        
        function getRunTimeInputM(rate) {
            var ipts = [];

            switch (rate) {
                case 'DM':
                    ipts = [ 'exec_select_timesM' ];
                    break;
                case 'Mon':
                    ipts = [ 'exec_select_season', 'exec_select_day' ];
                    break;
               
            }
            return ipts;
        }
        function changeRunTime(rate) {
      /*       $(".exec_select_times").hide();
            $(".exec_select_weekDay").hide();
            $(".exec_select_sWeek").hide();

            var showIptArr = getRunTimeInput(rate);

            showIptArr.forEach(function (ipt) {
                $("." + ipt).show();
            }) */
            if(rate=="WW"){
            	$(".exec_select_times").hide();
            	$(".exec_select_sWeek").show();
            	$(".exec_select_weekDay").show();
            }else{
            	$(".exec_select_times").show();
            	$(".exec_select_sWeek").hide();
            	$(".exec_select_weekDay").hide();
            	
            }
        }
        function changeRunTimeM(rate) {
       /*      $(".exec_select_timesM").hide();
            $(".exec_select_day").hide();
            $(".exec_select_season").hide();
            $(".exec_select_month").hide();

            var showIptArrM = getRunTimeInputM(rate);

            showIptArrM.forEach(function (ipt) {
                $("." + ipt).show();
            }) */
            if(rate=="Mon"){
            	$(".exec_select_timesM").hide();
            	$(".exec_select_season").show();
            	$(".exec_select_day").show();
            }else{
            	$(".exec_select_timesM").show();
            	$(".exec_select_season").hide();
            	$(".exec_select_day").hide();
            	
            }
        }
    

    </script>
</head>

<body>

	<div>
	<!-- 	<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label no-empty">单位信息：</td>
				<td class="ipt"><select id="hos_name" style="width: 180px;"
					disabled></select></td>
			</tr>
		</table> -->
		<fieldset id="debit" class="mode">
			<legend>周封存</legend>
			<div>
				<table class="table-layout" style="width: 100%;">

					<tr>
						<td class="label">是否自动封存：</td>
						<td><input type="radio" name="yesorno" checked value='1' /><label>是</label>
							<input name="yesorno" type="radio" value="0" /><label>否</label></td>
					</tr>
					<tr>
						<td class="label">封存设置：</td>
						<td class="ipt"><select id="tran_freq" style="width: 180px;"></select>
						</td>
						<td class="ipt"><span class="exec_select_times"
							style="display: none;"> <input id="exec_select_times"
								type="text" style="width: 120px;"> 天
						</span> <span class="exec_select_sWeek" style="display: none;"> <select
								id="exec_select_sWeek" style="width: 80px;"></select>
						</span> <span class="exec_select_weekDay" style="display: none;">
								<select id="exec_select_weekDay" style="width: 70px;"></select>
						</span></td>
					</tr>

				</table>
			</div>
		</fieldset>
		<fieldset id="debit" class="mode">
			<legend>月封存</legend>
			<div>


				<table class="table-layout" style="width: 100%;">



					<tr>
						<td class="label">是否自动封存：</td>
						<td><input type="radio" name="yesornoM" checked value='1' /><label>是</label>
							<input name="yesornoM" type="radio" value="0" /><label>否</label>
						</td>
					</tr>
					<tr>
						<td class="label">封存设置：</td>
						<td class="ipt"><select id="tran_freqM" style="width: 180px;"></select>
						</td>
						<td class="ipt"><span class="exec_select_timesM"
							style="display: none;"> <input id="exec_select_timesM"
								type="text" style="width: 120px;"> 天
						</span> <span class="exec_select_season" style="display: none;"> <select
								id="exec_select_season" style="width: 80px;"></select>
						</span> <span class="exec_select_month" style="display: none;"> <select
								id="exec_select_month" style="width: 60px;"></select> 月
						</span> <span class="exec_select_day" style="display: none;"> <select
								id="exec_select_day" style="width: 100px;"></select> 日
						</span></td>
					</tr>
				</table>
			</div>
		</fieldset>
		<table class="table-layout">
			<tr>
				<td colspan="10" class="tip"><span style="margin-left: 60px">举例说明：<span /><br />
						<span style="margin-left: 80px">①周封存设置
							按排班后固定天数封存,如设置2天,即本周结束后两天内可以修改排班,超过两天不允许修改排班<span /><br /> <span
							style="margin-left: 80px">②周封存设置
								按固定日期封存,如设置每周周二,即下周二之前可以修改排班,超过下周二不允许修改排班<span /><br /> <span
								style="margin-left: 80px">③月封存设置
									按排班后固定天数封存,如设置2天,即排班月份结束后两天内可以修改排班,超过两天不允许修改排班<span /><br />
									<span style="margin-left: 80px">④月封存设置
										按固定日期封存,如设置每月5号,即下月5号之前可以修改本月排班可以修改排班,超过下月5号不允许修改排班<span /><br /></td>
			</tr>


		</table>
	</div>

	<div class="button-group btn">
		<button id="save">保存</button>
		<!-- <button id="dele">清除</button> -->
	</div>
</body>

</html>