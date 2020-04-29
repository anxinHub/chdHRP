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
	<jsp:param value="dialog,select,grid,hr,datepicker" name="plugins" />
</jsp:include>
<script>
	var areacode1,areacode2,areacode3, dept1,dept2,dept3, attend_pbdate;
	var grid;
	var dateAllArr =[];

	var parentFrame = parent.$.etDialog.parentFrameName;
	var parentWindow = parent.window[parentFrame];
	var startDate = parentWindow.currentDateList[0];
	var endDate = parentWindow.currentDateList[parentWindow.currentDateList.length - 1];
	var db_gz = parentWindow.db_gz.getValue();    //倒班规则
	
	var loadClassDept = function(){
    	/* 班次 */
       /*  ajaxPostData({
    		url : "../../queryCalssCode.do?isCheck=false&attend_areacode=" + '${attend_areacode}',
    		success: function (res) {
    			areacode1.addOptions(res);
    			areacode2.addOptions(res);
    			areacode3.addOptions(res);
    		}
    	}); */
        
        /* 科室 */
       /*  ajaxPostData({
        	url : "../../queryCalssDept.do?isCheck=false&attend_areacode="+ '${attend_areacode}',
    		success: function (res) {
    			dept1.addOptions(res);
    			dept2.addOptions(res);
    			dept3.addOptions(res);
    		}
    	}); */
	};

	var initFrom = function() {

		/* 日期 */
		attend_pbdate = $("#attend_pbdate").etDatepicker({
			range : true,
			dateFormat : "yyyy-mm-dd",
			defaultDate : [ startDate, endDate ],
			onShow: function(picker) {
				picker.update({
					minDate : new Date(startDate),
					maxDate : new Date(endDate),
				})
			},
		});
		
		/*  班次  */
		areacode1 = $("#areacode1").etSelect({
			defaultValue : "none",
			backEndSearch: false,
			onChange : function(value){
				
			}
		});
		areacode2 = $("#areacode2").etSelect({
			defaultValue : "none",
			backEndSearch: false,
			onChange : function(value){
				
			}
		});
		areacode3 = $("#areacode3").etSelect({
			defaultValue : "none",
			backEndSearch: false,
			onChange : function(value){
			}
		});
		
		/* 科室  */
		dept1 = $("#dept1").etSelect({
			defaultValue : "none",
			backEndSearch: false,
			onChange : function(value){
			}
		});
		dept2 = $("#dept2").etSelect({
			defaultValue : "none",
			backEndSearch: false,
			onChange : function(value){
			}
		});
		dept3 = $("#dept3").etSelect({
			defaultValue : "none",
			backEndSearch: false,
			onChange : function(value){
			}
		});
		
		if(db_gz == 1){
			areacode1.addOptions(parentWindow.classData);
			dept1.addOptions(parentWindow.deptData);
			$('.class2').hide();
			$('.class3').hide();
		}else if(db_gz == 2){
			areacode1.addOptions(parentWindow.classData);
		 	areacode2.addOptions(parentWindow.classData);
		 	dept1.addOptions(parentWindow.deptData);
			dept2.addOptions(parentWindow.deptData);
			$('.class1').show();
			$('.class2').show();
			$('.class3').hide();
		}else{
			areacode1.addOptions(parentWindow.classData);
			areacode2.addOptions(parentWindow.classData);
    		areacode3.addOptions(parentWindow.classData);
    		dept1.addOptions(parentWindow.deptData);
			dept2.addOptions(parentWindow.deptData);
			dept3.addOptions(parentWindow.deptData);
			$('.class1').show();
			$('.class2').show();
			$('.class3').show();
		}
		

		$("#save").click(function() {
			var introduceParam = [];
			var params = [];
			var selectedDataRow = parentWindow.leftGrid.selectGet();

			var date = parentWindow.dateSilder.getValue();
			var dates = moment(date).year() + '-' + (moment(date).month() + 1);
			var daysInMonth = moment(dates, 'YYYY-MM').daysInMonth();
	        
	        getDayAll(attend_pbdate.getValue()[0],attend_pbdate.getValue()[1]);
			
			/* 批量设置 */
			selectedDataRow.forEach(function(item) {
				introduceParam.push({
					dept_id : item.rowData.dept_id,
					dept_name : item.rowData.dept_name,
					emp_id : item.rowData.emp_id,
					emp_name : item.rowData.emp_name,
					rowIndx : item.rowIndx
				});
			});
			
			for (var i = 0; i < daysInMonth; i++) {
				var theDate = moment(dates).add(i, 'day').format('YYYY-MM-DD');
				
				for(var k = 0;k < dateAllArr.length; k++){
					for(var j = 0; j<introduceParam.length; j++){
						/* if (dateAllArr[k] == theDate) { */
							if(db_gz == 1){
								introduceParam[j]['c1_' + dateAllArr[k]] = areacode1.getText();
								introduceParam[j]['c1id_' + dateAllArr[k]] = areacode1.getValue();
								introduceParam[j]['d1_' + dateAllArr[k]] = dept1.getText();
								introduceParam[j]['d1id_' + dateAllArr[k]] = dept1.getValue();
							}else if(db_gz == 2){
								introduceParam[j]['c1_' + dateAllArr[k]] = areacode1.getText();
								introduceParam[j]['c1id_' + dateAllArr[k]] = areacode1.getValue();
								introduceParam[j]['c2_' + dateAllArr[k]] = areacode2.getText();
								introduceParam[j]['c2id_' + dateAllArr[k]] = areacode2.getValue();
								introduceParam[j]['d1_' + dateAllArr[k]] = dept1.getText();
								introduceParam[j]['d1id_' + dateAllArr[k]] = dept1.getValue();
								introduceParam[j]['d2_' + dateAllArr[k]] = dept2.getText();
								introduceParam[j]['d2id_' + dateAllArr[k]] = dept2.getValue();
							}else{
								introduceParam[j]['c1_' + dateAllArr[k]] = areacode1.getText();
								introduceParam[j]['c1id_' + dateAllArr[k]] = areacode1.getValue();
								introduceParam[j]['c2_' + dateAllArr[k]] = areacode2.getText();
								introduceParam[j]['c2id_' + dateAllArr[k]] = areacode2.getValue();
								introduceParam[j]['c3_' + dateAllArr[k]] = areacode3.getText();
								introduceParam[j]['c3id_' + dateAllArr[k]] = areacode3.getValue();
								introduceParam[j]['d1_' + dateAllArr[k]] = dept1.getText();
								introduceParam[j]['d1id_' + dateAllArr[k]] = dept1.getValue();
								introduceParam[j]['d2_' + dateAllArr[k]] = dept2.getText();
								introduceParam[j]['d2id_' + dateAllArr[k]] = dept2.getValue();
								introduceParam[j]['d2_' + dateAllArr[k]] = dept2.getText();
								introduceParam[j]['d2id_' + dateAllArr[k]] = dept2.getValue();
							}
						/* } */
					}
				}
			};

			introduceParam.forEach(function(item, index) {
				parentWindow.leftGrid.updateRow(item.rowIndx, item);
			});
			$.etDialog.success('设置成功，保存后生效！')

		});
		$("#close").click(function() {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		})

	};
	
	/* 获取日期区间值 */
	function getDayAll(beginDate, endDate){
        var ab = beginDate.split("-");
        var ae = endDate.split("-");
        var db = new Date();
        db.setUTCFullYear(ab[0], ab[1]-1, ab[2]);
        var de = new Date();
        de.setUTCFullYear(ae[0], ae[1]-1, ae[2]);
        var unixDb=db.getTime();
        var unixDe=de.getTime();
        dateAllArr = [];
        for(var k=unixDb;k<=unixDe;){
            dateAllArr.push((new Date(parseInt(k))).format().toString());
            k=k+24*60*60*1000;
        }
        return dateAllArr;
    };
    
   	//日期时间段
	Date.prototype.format=function (){
        var s='';
        s+=this.getFullYear()+'-';            // 获取年份。
        s+= p(this.getMonth()+1)+"-";         // 获取月份。
        s+= p(this.getDate());                // 获取日。
        return(s);                            // 返回日期。
    };
    
  	//创建补0函数
    function p(s) {
        return s < 10 ? '0' + s: s;
    };

	$(function() {
		loadClassDept();
		initFrom();
	})
</script>
</head>

<body>
	<div class="container">
		<div class="center" style="padding: 43px">
			<table class="table-layout">
				<tr>
					<td class="label">日期：</td>
					<td class="ipt"><input id="attend_pbdate" type="text"></td>
				</tr>
				<tr class="class1">
					<td class="label">班次1：</td>
					<td class="ipt"><select id="areacode1" style="width: 180px;"></select></td>
					
					<td class="label">科室1：</td>
					<td class="ipt"><select id="dept1" style="width: 180px;"></select></td>
				</tr>
				
				<tr class="class2">
					<td class="label">班次2：</td>
					<td class="ipt"><select id="areacode2" style="width: 180px;"></select></td>
					
					<td class="label">科室2：</td>
					<td class="ipt"><select id="dept2" style="width: 180px;"></select></td>
				</tr>
				
				<tr class="class3">
					<td class="label">班次3：</td>
					<td class="ipt"><select id="areacode3" style="width: 180px;"></select></td>
					
					<td class="label">科室3：</td>
					<td class="ipt"><select id="dept3" style="width: 180px;"></select></td>
				</tr>
			</table>
			<div class="button-group btn" style="margin-top: 20px;">
				<button id="save">确定</button>
				<button id="close">关闭</button>
			</div>
		</div>
	</div>
</body>

</html>