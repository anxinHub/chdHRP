 //是否类型的模拟复选框的渲染函数
    function checkboxRender(rowdata, rowindex, value, column)
    {
        var iconHtml = '<div class="chk-icon';
        if (value) iconHtml += " chk-icon-selected";
        iconHtml += '"';
        iconHtml += ' rowid = "' + rowdata['__id'] + '"';
        iconHtml += ' gridid = "' + this.id + '"';
        iconHtml += ' columnname = "' + column.name + '"';
        iconHtml += ' column_id = "' + column.id + '"';
        iconHtml += '></div>';
        return iconHtml;
    }
    
    //是否类型的模拟复选框的点击事件
    $("div.chk-icon").live('click', function (e){
    	
    	var grid = $.ligerui.get($(this).attr("gridid")),
    	checked;
    	var columnname = $(this).attr("columnname");
    	
    	
    	if($(this).hasClass("total")){//判断当前点击行是否是列头
    		
    		if(!$(this).hasClass("chk-icon-selected")){//判断是否全选，若是则把所有数据都全选
    			$(this).addClass("chk-icon-selected");
    			checked = true;
    		}else{//若反选则数据全反选
    			$(this).removeClass("chk-icon-selected");
    			 checked = false;
    		}
    		
    		$("div.chk-icon").each(function(){
    			if($(this).hasClass("total")) return true;
            	var grid2 = $.ligerui.get($(this).attr("gridid"));
                var rowdata2 = grid2.getRow($(this).attr("rowid"));
            	var columnname2 = $(this).attr("columnname");
            	if(columnname == columnname2){
            		grid2.updateCell(columnname2, checked, rowdata2);
            	}
            });
    	}else{//非列头行点击
    		var rowdata = grid.getRow($(this).attr("rowid"));//获取当前行对象
    		if(rowdata[columnname] == undefined || rowdata[columnname] == ''){//为空点击时
    			checked = true;//选中
    		}else{//不为空点击时
    			checked = false;//不选中
    		}
    		grid.updateCell(columnname, checked, rowdata);//修改当前行当前列选中状态
    		
    		var allData = grid.rows;
    		//判断是否是一级
            if(rowdata.super_kpi_code == 'TOP'){
            	
            	//----------------------------1级指标处理  begin-----------------------
            	if(checked){//选中一级,子级全选
                	$.each(allData,function(index,content){
                    	if(content.super_kpi_code == rowdata.kpi_code){
                    		grid.updateCell(columnname, checked, content);
                    	}
                    });
                }else{//清空一级,取消全选
                	$.each(allData,function(index,content){
	                    if(content.super_kpi_code == rowdata.kpi_code){
	                    	grid.updateCell(columnname,checked, content);
	                    }
	                });
                }
            	//----------------------------1级指标处理 end--------------------------
            //非一级
             }else{
            	//----------------------------非1级指标处理  begin----------------------
            	 if(checked){
            		 //非一级,选中子级就选中父级 begin
            		 $.each(allData,function(index,content){
            			 if(rowdata.super_kpi_code == content.kpi_code){
            				 grid.updateCell(columnname,checked, content);
            			 }
            		 });
            		//非一级,选中子级就选中父级 end
            	 }else{
            		 
            		//如果是当前父级下最后一个子级,取消选中父级 begin
                  	var count = 0 ;
                 	$.each(allData,function(index,content){//判断选中状态
    					if(rowdata.super_kpi_code == content.super_kpi_code){
    						var flag = content[columnname];
    						if(flag != undefined && flag != ''){//不为空,就是选中
    							count++;//当前父级下还有其他选中的子级
    						}
    					}	                     		 
                    });
                         	 
    	            if(count == 0){//count == 0时,当前父级没有其它选中的子级
    	            	$.each(allData,function(index,content){
    						if(rowdata.super_kpi_code == content.kpi_code){
    							grid.updateCell(columnname,checked, content);
    						}	                     		 
    		            });
    	            }
    	            
    	          //如果是当前父级下最后一个子级,取消选中父级 end
                 }
            	//--------------------------非1级指标处理  end-------------------------
             }
            
    		var p_column = $(this).attr("column_id").split(";");//获取当前(医院或科室或职工)id、变更号
    		v_colunm = columnname;//获取当前(医院或科室或职工)变更号字段名称,用于保存时获取(医院或科室或职工)选中状态
    		colunm_id = p_column[0];//当前(医院或科室或职工)id,用于保存操作时查询(医院或科室或职工)
    		colunm_no = p_column[1];//当前(医院或科室或职工)变更号,用于保存操作时查询(医院或科室或职工)
    		
    		if(edit_id == 1){//edit_id=1,单条模式	edit_id=2,批量模式
    			var jsonObjs = gridManager.getUpdated();
    			save(jsonObjs);
    		}
    	}
    });
    
    