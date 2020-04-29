//定义原生使用占位符的方法，格式化数据
String.prototype.format = function () {
    // 数据长度为空，则直接返回
    if (arguments.length == 0){
        return this;
    }
 
    // 使用正则表达式，循环替换占位符数据
    for (var result = this, i = 0; i < arguments.length; i++){
        result = result.replace(new RegExp("\\{" + i + "\\}", "g"), arguments[i]);
    }
    
    return result.replace(",undefined", '');
}


var optionData = {
		//是否
		whether: [
			{id: 0, text: '否' }, 
			{id: 1, text: '是'}
			],
		//字段类型
		fieldType: [
			{id: 'VARCHAR2', text: '字符型' }, 
			{id: 'NUMBER', text: '数值型'}, 
			{id: 'DATE', text: '日期型'}, 
			{id: 'TIMESTAMP', text: '时间戳'},
			{id: 'CLOB', text: '大文本'}
			],
		//字段列数
		fieldWidth: [
			{id : '1', text : "1列" }, 
			{id : '2', text : "2列" }, 
			{id : '3', text : "3列" } , 
			{id : '4', text : "4列" }
			],
		//对齐方式
		alignMode: [
			{id: 'left', text: '左对齐' }, 
			{id: 'right', text: '右对齐'}, 
			{id: 'center', text: '居中对齐'}
			],
		//表连接方式
		joinMode: [
			{id: 'inner join', text: "内连接" }, 
			{id: 'left join', text: "左连接" }, 
			{id: 'right join', text: "右连接" }
			],
		//排序方式
		sortMode: [
			{id: 'ASC', text: '升序' }, 
			{id: 'DESC', text: '降序'}
			],
		//条件连接符
		condition: [
			{id: '=', text: '等于' }, 
			{id: '>', text: '大于'}, 
			{id: '<', text: '小于'},
			{id: '>=', text: '大于等于'},
			{id: '<=', text: '小于等于'},
			{id: '<>', text: '不等于'},
			{id: 'like', text: '相似'},
			{id: 'not like', text: '非相似'},
			{id: 'in', text: '包括'},
			{id: 'not in', text: '不包括'}
			],
		//条件连接方式
		connMode: [
			{id: 'AND', text: '与' }, 
			{id: 'OR', text: '或'}
			],
			//条件连接方式
		connMode2: [
			{id: 'ON', text: 'ON' }, 
			{id: 'AND', text: 'AND' }, 
			{id: 'OR', text: 'OR'}
			],
		//组件类型
		componentType: [
			{id: '01', text: "下拉框" }, 
			{id: '02', text: "文本框" }, 
			{id: '03', text: "日期框" }, 
			{id: '04', text: "单文件上传" },
			{id: '05', text: "多文件上传" },
			{id: '06', text: "多行文本框" },
			{id: '07', text: "整数框" },
			{id: '08', text: "浮点框" },
			],
		//取值方式
		valueMode: [
			{id: '01', text: '输入项' }, 
			{id: '02', text: '关联项' }, 
			{id: '03', text: '系统项' }
			],
		//取值系统项
		valueSystemItem: [
			{id: '01', text: 'SESSION取值'}, 
			{id: '02', text: '代理主键'}, 
			{id: '03', text: '序列主键'}, 
			{id: '04', text: '当前时间'}, 
			{id: '05', text: '当前日期'}, 
			{id: '06', text: '五笔码'}, 
			{id: '07', text: '拼音码'}
			],
		//数据库常用函数
		/*TO_CHAR(d|n[,fmt])转化为字符串
		 *TO_NUMBER(X,[,fmt])转换为数字
		 *TO_DATE(X,[,fmt])转换为日期
		 *NVL(X,V)空值处理
		 *AVG(X)平均值
		 *SUM(X)求和
		 *MIN(X)最小值
		 *MAX(X)最大值
		 *COUNT(X)数据统计*/
		dbFunc: [
			{id: 'TO_CHAR(%s,%s)', text: 'TO_CHAR(d|n[,fmt])'},
			{id: 'TO_NUMBER(%s,%s)', text: 'TO_NUMBER(X,[,fmt])'},
			{id: 'TO_DATE(%s,%s)', text: 'TO_DATE(X,[,fmt])'},
			{id: 'NVL(%s,%d)', text: 'NVL(X,V)'},
			{id: 'AVG(%s)', text: 'AVG(X)'},
			{id: 'SUM(%s)', text: 'SUM(X)'},
			{id: 'MIN(%s)', text: 'MIN(X)'},
			{id: 'MAX(%s)', text: 'MAX(X)'},
			{id: 'COUNT(%s)', text: 'COUNT(X)'}
		],
		//查询方式
		conSign: [
			{id: '11', text: '等于'},
			{id: '12', text: '大于'},
			{id: '13', text: '大于等于'},
			{id: '14', text: '小于'},
			{id: '15', text: '小于等于'},
			{id: '16', text: '不等于'},
			{id: '17', text: '相似'},
			{id: '18', text: '非相似'},
			{id: '19', text: '包括'},
			{id: '20', text: '不包括'}
		],
		//代码表设置外部引用
		codeItem:[
  	            { id: 'field_col_code', text: '代码项编码' },
	            { id: 'field_col_name', text: '代码项名称' },
	            { id: 'super_col_name', text: '上级代码' },
	            { id: 'is_last_text', text: '是否末级' },
	            { id: 'is_innr_text', text: '是否内置' },
	            { id: 'is_stop_text', text: '是否停用' },
	            { id: 'note', text: '备注' }
	          ]
}

//数据表构建页面
var sc = { 
		//系统标签
		sysLabel: [
			{id: 'l01', text: 'SESSION取值', val: 'SESSION取值'}, 
			{id: 'l02', text: '代理主键', val: '代理主键'}, 
			{id: 'l03', text: '序列主键', val: '序列主键'}, 
			{id: 'l04', text: '当前时间', val: '当前时间'}, 
			{id: 'l05', text: '当前日期', val: '当前日期'}, 
			{id: 'l06', text: '五笔码', val: '五笔码'}, 
			{id: 'l07', text: '拼音码', val: '拼音码'}
		],
		sqlEle: function(value){
			var v_checked = value && value.is_custom ? 'checked="checked"' : '';
			var v_is_proc_checked = value && value.is_proc_jfunc == 1 ? 'checked="checked"' : '';
			var v_is_jfunc_checked = value && value.is_proc_jfunc == 2 ? 'checked="checked"' : '';
			var sql_code = value ? value.sql_code : "";
			var sql_name = value ? value.sql_name : "";
			var sql_statement = value ? value.sql_statement : "";
			return '<div class="sqlEle"> '+
			  '<table cellpadding="0" cellspacing="0" class="l-table-edit">'+
  			  '  <tr>'+
  			  '    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">编码：</td>'+
  			  '    <td align="left" class="l-table-edit-td">'+
  			  '		 <div class="l-text" style="width: 200px;">'+
  			  '        <input class="l-text-field" style="width: 200px;" name="sql_code" type="text" id="sql_code" ltype="text" value="'+sql_code +'" validate="{required:true,maxlength:20}" />'+
  			  '      </div>'+
  			  '    </td>'+
  			  '    <td align="left"></td>'+
  			  '    <td align="right" class="l-table-edit-td" style="padding-left: 20px;">名称：</td>'+
  			  '    <td align="left" class="l-table-edit-td">'+
  			  '		 <div class="l-text" style="width: 200px;">'+
  			  '        <input class="l-text-field" style="width: 200px;" name="sql_name" type="text" id="sql_name" ltype="text" value="'+sql_name+'" validate="{required:true,maxlength:200}" />'+
  			  '      </div>'+
  			  '    </td>'+
  			  '    <td align="left" class="l-table-edit-td">'+
  			  '     <input id="is_custom" type="checkbox" name="is_custom" '+v_checked+'/>'+
  			  '      <label>自定义</label>'+
  			  '    </td>'+
  			  '    <td align="left"></td>'+
  			  '    <td align="left" class="l-table-edit-td">'+
			  '     <input id="is_proc" type="radio" name="is_proc_jfunc" value="1" '+v_is_proc_checked+' />'+
			  '      <label>存储过程</label>'+
			  '    </td>'+
			  '    <td align="left"></td>'+
			  '    <td align="left" class="l-table-edit-td">'+
  			  '     <input id="is_jfunc" type="radio" name="is_proc_jfunc" value="2" '+v_is_jfunc_checked+' />'+
  			  '      <label>JAVA方法</label>'+
  			  '    </td>'+
  			  '    <td align="left"></td>'+
  			  '    <td align="left" class="l-table-edit-td">'+
  			  '      <input type="button" value="清空" id="sqlClearUpBtn" class="l-button l-button-test"/>'+
  			  '      <input type="button" value="删除" id="sqlDeleteBtn" class="l-button l-button-test"/>'+
  			  '    </td>'+
  			  '  </tr>'+
  			  '</table>'+
  			  '<table cellpadding="0" cellspacing="0" class="l-table-edit" style="width:100%;heigth:100px; bottom: 143px;">'+
  			  '  <tr>'+
  			  '    <td align="left" class="l-table-edit-td">'+
  			  '      <textarea class="liger-textarea" id="sql_statement" name="sql_statement" style="width:100%;height:100px;" validate="{required:true}">'+sql_statement+'</textarea>'+
  			  '    </td>'+
  			  '  <tr />'+
  			  '</table>'+
  			'</div>';
		}

}

//页面设计器模板类型
var template_type_date = [{id:'01',pid:0,text:'表格'},{id:'02',pid:0,text:'表单'},{id:'03',pid:0,text:'图表'}]//tree暂定这个数据
//页面设计器ligerui grid属性
var ligerUiGridApi = {
		checkbox :'复选框',
		delayLoad:'初始化不加载 ',
      }
//页面设计器ligerui tree属性
var ligerUiTreeApi = {
		checkbox:'复选框',
		single:'是否单选',
      }

var getGridColumns = function(paramObj){
	return genUIFittings.getGridColumns(paramObj);
}

var getSearchField = function(paramObj){
	return genUIFittings.getSearchField(paramObj);
}

var getFromField = function(paramObj){
	return genUIFittings.getFromField(paramObj);
}

//生成Ui表格列，form字段
var genUIFittings = {
		data: {
			ui: "et",//et|liger
			group_id: null,
			hos_id: null,
			gridTables: [],//表格数据表，可多个
			formTable: '', //搜索Form数据表, 表单Form数据表
			design: '', //查询设计器编码
			allColumn: [],
			viewColumn: [],
			searchField: {},
			formField: {}
		},
		getFromField: function(param){
			$.extend(this.data, param);
			var e = this;
			var url = e.getUrl("/form/"+e.data.formTable+".data");
			e.loadServerData(url, function(data){
				e.data.formField = data[e.data.ui];
			});
			return e.data.formField;
		},
		getSearchField: function(param){
			$.extend(this.data, param);
			var e = this;
			var url = e.getUrl("/search/"+e.data.formTable+".data");
			e.loadServerData(url, function(data){
				e.data.searchField = data[e.data.ui];
			});
			return e.data.searchField;
		},
		getGridColumns: function(param){
			$.extend(this.data, param);
			this.getAllColumn();
			this.getViewColumn();
			this.filterViewColumn();
			return this.data.allColumn;
		},
		getUrl: function(path){
			var pathName=window.document.location.pathname;  
			var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
			return projectName + "/lib/hrp/hr/data/"+this.data.group_id+"/"+this.data.hos_id+path;
		},
		getAllColumn : function(){
			var e = this;
			e.data.allColumn = [];
			$.each(e.data.gridTables, function(i, n){
				var url = e.getUrl("/grid/"+n+".data");
				e.loadServerData(url, function(data){
					e.data.allColumn = e.data.allColumn.concat(data[e.data.ui]);
				});
			})
		},
		getViewColumn: function(){
			var e = this;
			var url = e.getUrl("/query/"+e.data.design+".data");
			e.loadServerData(url, function(data){
				e.data.viewColumn = data;
			})
		},
		filterViewColumn: function (){
			var e = this;
			if(e.data.allColumn.length == 0 || e.data.viewColumn.length == 0){return;}
			$.each(e.data.allColumn, function(i, v){
				$.each(e.data.viewColumn, function(ii, vv){
					if(vv.col_code.toUpperCase() == v.name.toUpperCase() && vv.is_display == true){
						v.hide = false;
						v.hidden = false;
						return false;
					}else{
						v.hide = true;
						v.hidden = true;;
					}
				})
			})
		},
		loadServerData:	function (url, callback){
			var ajaxOptions = {
	            url: url,
	            async: false,
	            dataType: 'json',
	            success: function (data){
	            	callback(data);
	            }
	        };
			$.ajaxSetup({cache: false});
	        $.ajax(ajaxOptions);
	    }
}


//人力模板打印
var hrTemplatePrint = function(paramObj){
	var printPara=$.extend({
		class_name:"com.chd.hrp.hr.service.QueryService",
		method_name:"queryBaseInfoPrintTemp",
		bean_name: "queryService",
        template_code:'',
        isPrintCount:false,//更新打印次数
        isPreview:true,//预览窗口，传绝对路径
        use_id: 0,
        design_code: ''
	}, paramObj); 
	officeFormPrint(printPara);
}
