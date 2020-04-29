<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select,tree,hr,validate,datepicker,dialog,grid,form" name="plugins" />
    </jsp:include>
    <script>
        var attdent_year,attdent_cycle_beg,hos_name,normal_form_validate;
        //初始化
        $(function () {
        	 initSelect();//初始化页面信息
        	 initGrid();
             initValidate();
             query();//初始化查询考勤周期
        });
        
        function initGrid() {
            // 基础表格参数
            var main_toolbar = {
                items: [
                    { type: "button", label: '查询', icon: 'print', listeners: [{ click: queryData }] },
                    { type: "button", label: '清除', icon: 'delete', listeners: [{ click: remove }] }
                ]
            };
            var main_columns = [
                {
                    display: '考勤年份', name: 'attend_year', width: 90,
                    
                },
                {
                    display: '考勤月份', name: 'attend_month', width: 90
                },
                {
                    display: '开始日期', name: 'begin_date', width: 120
                },
                {
                    display: '结束日期', name: 'end_date', width: 120
                },
                {
                    display: '是否结账', name: 'hr_flag', width: 80,
                    render : function(ui){
                		if(ui.rowData.hr_flag == 0){
                			return "否";
                		}else{
                			return "是";
                		}
                	}
                },
                {
                    display: '结账人', name: 'hr_user', width: 80
                },
                {
                    display: '结账日期', name: 'hr_date', width: 80
                }
            ];
            var main_obj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                toolbar: main_toolbar,
                columns: main_columns,
                dataModel: {
                    url: 'queryHrPeriod.do?isCheck=false',
                }
            };
            grid = $("#mainGrid").etGrid(main_obj);
        }
        //查询考勤期间
        function queryData(){
        	var param = [ 
            	{ name: 'attdent_year', value: attdent_year.getValue() }
			];
            grid.loadData(param);
        }
        
        
        //查询
        function query(){
        	ajaxPostData({
        		url: 'queryCycle.do',
                data:  {hos_id : hos_name.getValue()},
                success: function (response) {
                	//0,代表自然月,2,代表未做过保存操作。为2时数据不存在返回的信息
                  	if(response.attdent_cycle_style == 0 || response.attdent_cycle_style == 2 ){
                  		$(":radio[name='attdent_cycle_style'][value='0']").prop("checked", "checked");
                  	}
                  	//非自然月
                  	if(response.attdent_cycle_style == 1){
                  		$(":radio[name='attdent_cycle_style'][value='1']").prop("checked", "checked");
                  		attdent_cycle_beg.enabled();
                		$("#attdent_cycle").show();
                		attdent_cycle_beg.setValue(response.attdent_cycle_beg);
                		$("#attdent_cycle_end").val(response.attdent_cycle_end);
                  	}
                },
                delayCallback: true
            });
        }
        var remove = function () {
            var selectData = grid.selectGet();
         /*    if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            } */
        	var ParamVo = [];
            $(selectData).each(function () {
                var rowdata = this.rowData;
                ParamVo.push(rowdata);
            });
            $.etDialog.confirm('确定清除?', function () {
            ajaxPostData({
                 url: 'deleteCycle.do',
                data: { paramVo: JSON.stringify(ParamVo) },
                success: function () {
                 	queryData();
                    grid.deleteRows(selectData);
                }
            }) });
        };
        
        
        function initSelect() {
        	
        	attdent_year = $("#attdent_year").etDatepicker({
        		view: "years",
        		minView: "years",
        		dateFormat: "yyyy",
        		defaultDate : true,
        	
            })
            
        	hos_name = $("#hos_name").etSelect({
        			url: '../../queryHosInfoSelect.do?isCheck=false',
        			defaultValue: '${sessionScope.hos_id}'
            });
   
            // 生成静态选择数据
            var simpleNumberOptions = function (times, isAdd) {
	            var options = [];
	            var startNum = 2;
	
	            if (isAdd) {
	                startNum = 2;
	                times += 1;
	            }
				
	            if (times) {
					for (var i = startNum; i < times; i++) {
	                    //var pre = i < 10 ? '0' : '';
	                    //var value = pre + i;
	                    var value = i ; 
	                    options.push({
	                        id: value,
	                        text: value
	                    });
	                }
				}
				return options;
			};
            // 执行时机
            
            // 天
            var dayOptions = simpleNumberOptions(28, true);
          
            attdent_cycle_beg = $("#attdent_cycle_beg").etSelect({
                options: dayOptions,
                showClear: false,
            	onChange: function () {
            		$("#attdent_cycle_end").val(attdent_cycle_beg.val()-1)
            	}
            });
            attdent_cycle_beg.disabled();
            
         	//保存
            $("#save").click( function (){
	            if(hos_name.getValue()==null || hos_name.getValue() ==""){
	            	$.etDialog.error('请选择单位');
	            	return;
	            }
                var cysle_style = $('input[name="attdent_cycle_style"]:checked ').val();
                ajaxPostData({
                    url: 'addCycle.do',
                    data:  {
           	 			hos_id : hos_name.getValue(),
           	 		    attdent_cycle_style : cysle_style,
           	 		    attdent_cycle_beg : cysle_style == 0 ? "" : attdent_cycle_beg.getValue(),
           	 		    attdent_cycle_end : cysle_style == 0 ? "" : $("#attdent_cycle_end").val(),
                    },
                    success: function () {
                       
                    },
                    delayCallback: true
                })
            });
            
            //生成考勤周期
            $("#create").click( function (){
	            if(hos_name.getValue()==null || hos_name.getValue() ==""){
	            	$.etDialog.error('请选择单位');
	            	return;
	            }
                
                ajaxPostData({
                    url: 'createCycle.do',
                    data:  {
           	 			hos_id : hos_name.getValue(),
           	 		    attdent_cycle_style : $('input[name="attdent_cycle_style"]:checked ').val(),
           	 		    attdent_cycle_beg :attdent_cycle_beg.getValue(),
           	 		    attdent_cycle_end:$("#attdent_cycle_end").val(),
                    },
                    success: function () {
                    	queryData();
                    },
                    delayCallback: true
                })
            })
        }

        function initValidate() {
            normal_form_validate = $.etValidate({
                items: [
                    { el: $("#tran_freq"), required: true },
                ]
            });
        }
   
        function makeDis(month){
        	if(month=='0'){
        		attdent_cycle_beg.disabled();
        	  	$('#attdent_cycle').hide();
        	  	
        	} else if(month=='1'){
        		attdent_cycle_beg.enabled();
        		$("#attdent_cycle").show();
        		var beg_val = attdent_cycle_beg.val() - 1;
        		$("#attdent_cycle_end").val(beg_val); 
        	}
        }

    </script>
</head>

<body>
      <table  class="table-layout" >
        	<tr class="center">
	            <td class="label no-empty">单位信息：</td>
	            <td class="ipt">
	                <select id="hos_name" style="width:180px;" ></select>
	            </td>
	            
	            <td class="label">周期方式：</td>
                <td class="ipt">
                	<input type="radio" name="attdent_cycle_style"  checked value='0' onclick="makeDis('0')"/><label>自然月&nbsp;&nbsp;</label>  
                	<input name="attdent_cycle_style"  type="radio"  value="1" onclick="makeDis('1')" /><label>非自然月&nbsp;&nbsp;</label>  
                </td>
                
                <td id="attdent_cycle" style="display:none">
                	考勤周期为上月
                    <input id="attdent_cycle_beg" type="text"  style="width:60px;"> 日<span>至本月</span>
                    <input id="attdent_cycle_end" type="text" disabled style="width:60px;"> 日
                </td>
                <td><button id="save">保存</button></td>
        	</tr>
            
    		<tr >
    			<td></td>
    			<td align="left">
    				<button id="create">生成考勤期间</button>
    			</td>
    		</tr>
    </table>
    
    <hr/>
    
    <div class="main">
    	<table class="table-layout">
         	<tr>
         		<td class="label"> 考勤年份：</td>
                <td class="ipt">
                	<input type="text" id="attdent_year">
                </td>
            </tr>    
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>