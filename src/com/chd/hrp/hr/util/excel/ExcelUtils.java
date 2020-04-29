/*
 *
 *                  Copyright 2017 Crab2Died
 *                     All rights reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Browse for more information ：
 * 1) https://gitee.com/Crab2Died/Excel4J
 * 2) https://github.com/Crab2died/Excel4J
 *
 */

package com.chd.hrp.hr.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {

    /**
     * 单例
     */
    static private ExcelUtils excelUtils = new ExcelUtils();

    private ExcelUtils() {
    }

    public static ExcelUtils getInstance() {
        return excelUtils;
    }

    /*----------------------------------------读取Excel操作无映射--------------------------------------------------*/
    /*  一. 操作流程 ：                                                                                            */
    /*      *) 按行读取Excel文件,存储形式为  Cell->String => Row->List<Cell> => Excel->List<Row>                    */
    /*  二. 参数说明                                                                                               */
    /*      *) excelPath        =>      目标Excel路径                                                              */
    /*      *) InputStream      =>      目标Excel文件流                                                            */
    /*      *) offsetLine       =>      开始读取行坐标(默认0)                                                       */
    /*      *) limitLine        =>      最大读取行数(默认表尾)                                                      */
    /*      *) sheetIndex       =>      Sheet索引(默认0)                                                           */

    public List<List<String>> readExcel2List(String excelPath, int offsetLine, int limitLine, int sheetIndex)
            throws Exception {

        Workbook workbook = WorkbookFactory.create(new File(excelPath));
        return readExcel2ObjectsHandler(workbook, offsetLine, limitLine, sheetIndex);
    }

    public List<List<String>> readExcel2List(InputStream is, int offsetLine, int limitLine, int sheetIndex)
            throws Exception {

        Workbook workbook = WorkbookFactory.create(is);
        return readExcel2ObjectsHandler(workbook, offsetLine, limitLine, sheetIndex);
    }

    public List<List<String>> readExcel2List(String excelPath, int offsetLine)
            throws Exception {

        Workbook workbook = WorkbookFactory.create(new File(excelPath));
        return readExcel2ObjectsHandler(workbook, offsetLine, Integer.MAX_VALUE, 0);
    }

    public List<List<String>> readExcel2List(InputStream is, int offsetLine)
            throws Exception {

        Workbook workbook = WorkbookFactory.create(is);
        return readExcel2ObjectsHandler(workbook, offsetLine, Integer.MAX_VALUE, 0);
    }

    public List<List<String>> readExcel2List(String excelPath)
            throws Exception {

        Workbook workbook = WorkbookFactory.create(new File(excelPath));
        return readExcel2ObjectsHandler(workbook, 0, Integer.MAX_VALUE, 0);
    }

    public List<List<String>> readExcel2List(InputStream is)
            throws Exception {

        Workbook workbook = WorkbookFactory.create(is);
        return readExcel2ObjectsHandler(workbook, 0, Integer.MAX_VALUE, 0);
    }

    private List<List<String>> readExcel2ObjectsHandler(Workbook workbook, int offsetLine,
                                                        int limitLine, int sheetIndex) throws Exception {

        List<List<String>> list = new ArrayList<List<String>>();
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        long maxLine = sheet.getLastRowNum() > ((long) offsetLine + limitLine) ?
                ((long) offsetLine + limitLine) : sheet.getLastRowNum();
        for (int i = offsetLine; i <= maxLine; i++) {
            List<String> rows = new ArrayList<String>();
            Row row = sheet.getRow(i);
            for (Cell cell : row) {
                String val = ExcelUtils.getCellValue(cell);
                rows.add(val);
            }
            list.add(rows);
        }
        return list;
    }

    /*-----------------------------------------无模板无注解导出----------------------------------------------------*/
    /*  一. 操作流程 ：                                                                                           */
    /*      1) 写入表头内容(可选)                                                                                  */
    /*      2) 写入数据内容                                                                                       */
    /*  二. 参数说明                                                                                              */
    /*      *) data             =>      导出内容List集合                                                          */
    /*      *) header           =>      表头集合,有则写,无则不写                                                   */
    /*      *) sheetName        =>      Sheet索引名(默认0)                                                        */
    /*      *) isXSSF           =>      是否Excel2007以上                                                         */
    /*      *) targetPath       =>      导出文件路径                                                              */
    /*      *) os               =>      导出文件流                                                                */

    public void exportObjects2Excel(List<?> data, List<String> header, String sheetName, boolean isXSSF,
                                    String targetPath) throws Exception {

        exportExcelNoModuleHandler(data, header, sheetName, isXSSF).write(new FileOutputStream(targetPath));
    }

    public void exportObjects2Excel(List<?> data, List<String> header, String sheetName, boolean isXSSF,
                                    OutputStream os) throws Exception {

        exportExcelNoModuleHandler(data, header, sheetName, isXSSF).write(os);
    }

    public void exportObjects2Excel(List<?> data, List<String> header, String targetPath) throws Exception {

        exportExcelNoModuleHandler(data, header, null, true)
                .write(new FileOutputStream(targetPath));
    }

    public void exportObjects2Excel(List<?> data, List<String> header, OutputStream os) throws Exception {

        exportExcelNoModuleHandler(data, header, null, true).write(os);
    }

    public void exportObjects2Excel(List<?> data, String targetPath) throws Exception {

        exportExcelNoModuleHandler(data, null, null, true)
                .write(new FileOutputStream(targetPath));
    }

    public void exportObjects2Excel(List<?> data, OutputStream os) throws Exception {

        exportExcelNoModuleHandler(data, null, null, true).write(os);
    }

    private Workbook exportExcelNoModuleHandler(List<?> data, List<String> header,
                                                String sheetName, boolean isXSSF) throws Exception {

        Workbook workbook;
        if (isXSSF) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        Sheet sheet;
        if (null != sheetName && !"".equals(sheetName)) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }
        
        sheet.setDefaultColumnWidth(15);

        int rowIndex = 0;
        if (null != header && header.size() > 0) {
            // 写标题
            Row row = sheet.createRow(rowIndex);
            for (int i = 0; i < header.size(); i++) {
                row.createCell(i, Cell.CELL_TYPE_STRING).setCellValue(header.get(i));
            }
            rowIndex++;
        }
        for (Object object : data) {
            Row row = sheet.createRow(rowIndex);
            if (object.getClass().isArray()) {
                for (int j = 0; j < Array.getLength(object); j++) {
                    row.createCell(j, Cell.CELL_TYPE_STRING).setCellValue(Array.get(object, j).toString());
                }
            } else if (object instanceof Collection) {
                Collection<?> items = (Collection<?>) object;
                int j = 0;
                for (Object item : items) {
                    row.createCell(j, Cell.CELL_TYPE_STRING).setCellValue(item.toString());
                    j++;
                }
            } else {
                row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(object.toString());
            }
            rowIndex++;
        }
        return workbook;
    }
    
    /**
     * 获取单元格内容
     *
     * @param c 单元格
     * @return 单元格内容
     */
    public static String getCellValue(Cell c) {
        String o;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                o = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                o = String.valueOf(c.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                o = String.valueOf(c.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                o = String.valueOf(c.getNumericCellValue());
                o = matchDoneBigDecimal(o);
                o = ExcelUtils.converNumByReg(o);
                break;
            case Cell.CELL_TYPE_STRING:
                o = c.getStringCellValue();
                break;
            default:
                o = null;
                break;
        }
        return o;
    }
    
    /**
     * 科学计数法数据转换
     *
     * @param bigDecimal 科学计数法
     * @return 数据字符串
     */
    private static String matchDoneBigDecimal(String bigDecimal) {
        // 对科学计数法进行处理
        boolean flg = Pattern.matches("^-?\\d+(\\.\\d+)?(E-?\\d+)?$", bigDecimal);
        if (flg) {
            BigDecimal bd = new BigDecimal(bigDecimal);
            bigDecimal = bd.toPlainString();
        }
        return bigDecimal;
    }
    
    public static String converNumByReg(String number) {
        Pattern compile = Pattern.compile("^(\\d+)(\\.0*)?$");
        Matcher matcher = compile.matcher(number);
        while (matcher.find()) {
            number = matcher.group(1);
        }
        return number;
    }
}
