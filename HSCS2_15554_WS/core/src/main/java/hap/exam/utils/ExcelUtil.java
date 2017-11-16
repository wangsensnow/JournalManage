package hap.exam.utils;

import com.hand.hap.generator.service.impl.DBUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yihao.xing@hand-china.com
 * @version 1.0
 * @name  Excel上传工具类
 */
public class ExcelUtil {
    public static Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 判断某一列的数据是否存在null
     * @param sheet:sheet对象
     * @param num：当前列数(序号，从0开始)
     * @param rows：有效行数
     */
    public static Boolean getCellNUmbersNull(HSSFSheet sheet, int num, int rows){
        //如果flag默认为true
        boolean flag = true;
        for (int i = 1 ; i <= rows; i++){
            HSSFRow sheetRow = sheet.getRow(i);
            if (i == 1){
                if (sheetRow == null){
                    flag = false;
                    break;
                }
            }else {
                if (sheetRow == null){
                    continue;
                }else {
                    HSSFCell cell = sheetRow.getCell(num);
                    if(cell != null){
                        continue;
                    }else {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 判断某一列的数据是否全部相同(确保cell不为空)
     * @param sheet:sheet对象
     * @param num：当前列数(序号，从0开始)
     * @param rows：有效行数
     */
    public static Boolean getCellEqual(HSSFSheet sheet,int num,int rows){
        Set set = new HashSet();
        //如果flag为true表示全部相同，否则存在不同的值
        boolean flag = true;
        for (int i = 1 ; i <= rows; i++){
            HSSFRow sheetRow = sheet.getRow(i);
            HSSFCell cell = sheetRow.getCell(num);
            if (i == 1 ){
                set.add(cell.getStringCellValue());
            }else {
                if (! set.add(cell.getStringCellValue())){
                    continue;
                }else {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }


    /**
     * 判断某一列的数据是否全部相同以及是否存在空值(将上面两种效验整合为一个方法)
     * @param sheet:sheet对象
     * @param num：当前列数(序号，从0开始)
     * @param rows：有效行数
     */
    public static Boolean getCellNumbersEqual(Sheet sheet,int num,int rows){
        Set set = new HashSet();
        //如果flag为true表示全部相同，否则存在不同的值
        boolean flag = true;
        for (int i = 1 ; i <= rows; i++){
            Row sheetRow = sheet.getRow(i);
            Cell cell = sheetRow.getCell(num);
            if (cell != null){
                if (i == 1){
                    set.add(cell.getStringCellValue());
                }else {
                    if(! set.add(cell.getStringCellValue())){
                        continue;
                    }else {
                        flag = false;
                        break;
                    }
                }
            }else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 对Excel每列数据进行格式化
     * @param cell 当前单元格
     * @return
     */
    public static String formatCellValueToString(HSSFCell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#.00");//小数
        DecimalFormat df1 = new DecimalFormat("0");//整数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){//日期
                    cellValue = sdf.format(cell.getDateCellValue());
                }else{//数值
                    Long longVal = Math.round(cell.getNumericCellValue());//将其转换为整型
                    Double value = cell.getNumericCellValue();
                    if (Double.parseDouble(longVal+".0") == value){//整型
                        cellValue =df1.format(cell.getNumericCellValue());
                    }else {
                        cellValue =String.valueOf(cell.getNumericCellValue());
                    }
                }
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    /**
     * 对Excel每列数据进行格式化(对于HSSF/XSSF)
     * @param cell 当前单元格
     * @return
     */
    public static String formatCellValueToStringUtil(Cell cell) {
        String cellValue = "";
        DecimalFormat df = new DecimalFormat("#.00");//小数
        DecimalFormat df1 = new DecimalFormat("0");//整数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){//日期
                    cellValue = sdf.format(cell.getDateCellValue());
                }else{//数值
                    Long longVal = Math.round(cell.getNumericCellValue());//将其转换为整型
                    Double value = cell.getNumericCellValue();
                    if (Double.parseDouble(longVal+".0") == value){//整型
                        cellValue =df1.format(cell.getNumericCellValue());
                    }else {
                        cellValue =String.valueOf(cell.getNumericCellValue());
                    }
                }
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    /**
     * 校验文件模板是否存在空行，去除空行且获取实际有效行数(适用HSSFSheet)
     * @param sheet:sheet参数
     * @param colNum:所有列
     * @param rows:获取当前有效行数
     */
    public static int getInviliadRows(HSSFSheet sheet,int colNum,int rows){
        for (int i = rows; i>=1;i--){
            int col = 0;
            HSSFRow sheetRow = sheet.getRow(i);
            while (col < colNum){
                HSSFCell cell = sheetRow.getCell(col);
                if (cell == null){
                    ++col;
                    continue;
                }else {
                    if ("".equals(ExcelUtil.formatCellValueToString(cell))){
                        ++col;
                        continue;
                    }else {
                        break;
                    }
                }
            }
            if (col == colNum){
                //当空行存在有效行数据中间
                if (i >= 0 && i < rows){
                    sheet.shiftRows(i+1,rows,-1);
                }
                //当空行存在于最后
                if (i == rows){
                    if (sheetRow != null){
                        sheet.removeRow(sheetRow);
                    }
                }
                rows -= 1;
            }
        }
        log.debug("****************"+rows);
        return rows;
    }

    /**
     * 校验文件模板是否存在空行，去除空行且获取实际有效行数(通用方法1)
     * @param sheet:sheet参数
     * @param colNum:所有列
     * @param rows:获取当前有效行数
     */
    public static int getInviliadRowsItf(Sheet sheet,int colNum,int rows){
        for (int i = rows; i>=1;i--){
            int col = 0;
            Row sheetRow = sheet.getRow(i);
            while (col < colNum){
                Cell cell = sheetRow.getCell(col);
                if (cell == null){
                    ++col;
                    continue;
                }else {
                    if ("".equals(ExcelUtil.formatCellValueToStringUtil(cell))){
                        ++col;
                        continue;
                    }else {
                        break;
                    }
                }
            }
            if (col == colNum){
                //当空行存在有效行数据中间
                if (i >= 0 && i < rows){
                    sheet.shiftRows(i+1,rows,-1);
                }
                //当空行存在于最后
                if (i == rows){
                    if (sheetRow != null){
                        sheet.removeRow(sheetRow);
                    }
                }
                rows -= 1;
            }
        }
        log.debug("****************"+rows);
        return rows;
    }

    /**
     * 校验文件模板是否存在空行，去除空行且获取实际有效行数(通用方法2)
     * @param sheet:sheet参数
     * @param colNum:所有列
     * @param rows:获取当前有效行数
     * @param startNum:获取当前起始行数
     */
    public static int getInviliadAllRows(Sheet sheet, int colNum, int rows, int startNum) throws Exception{
        for (int i = rows-1; i>=startNum;i--){
            int col = 0;
            Row row = sheet.getRow(i);
            if (row != null){
                while (col < colNum){
                    Cell cell = row.getCell(col);
                    if (cell == null){
                        ++col;
                        continue;
                    }else {
                        if ("".equals(ExcelUtil.formatCellValueToStringUtil(cell))){
                            ++col;
                            continue;
                        }else {
                            break;
                        }
                    }
                }
                if (col == colNum){
                    //当空行存在有效行数据中间
                    if (i >= 0 && i < rows){
                        sheet.shiftRows(i+1,rows,-1);
                    }
                    //当空行存在于最后
                    if (i == rows){
                        if (row != null){
                            sheet.removeRow(row);
                        }
                    }
                    rows -= 1;
                }
            }else {
                if (i >= 0 && i < rows){
                    sheet.shiftRows(i+1,rows,-1);
                }
                continue;
            }

        }
        log.debug("****************"+rows);
        return rows;
    }

    /**
     * 删除某一行
     * @param sheet:sheet参数
     * @param rowIndex:获取当前行索引
     */
    public static void removeRow(HSSFSheet sheet, int rowIndex) {
        int lastRowNum=sheet.getLastRowNum();
        if(rowIndex>=0&&rowIndex<lastRowNum){
            sheet.shiftRows(rowIndex+1,lastRowNum,-1);//将行号为rowIndex+1一直到行号为lastRowNum的单元格全部上移一行，以便删除rowIndex行
        }
        if(rowIndex==lastRowNum){
            HSSFRow removingRow=sheet.getRow(rowIndex);
            if(removingRow!=null){
                sheet.removeRow(removingRow);
            }
        }
    }

    /**
     * 下载Excel防止文件名乱码
     * 判断浏览器类型，firefox浏览器做特殊处理，否则下载文件名乱码
     * @param request
     * @param response
     * @param excelname Excel名称
     */
    public static void compatibleFileName(HttpServletRequest request, HttpServletResponse response, String excelname) throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        response.setContentType("application/vnd.ms-excel");
        String fileName = excelname;
        String codedFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        if (agent.contains("firefox")) {
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xls");
        } else {
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
        }
    }

    /**
     * 判断Excel版本格式
     * 如果为true，则代表2003的excel，否则为2007版本Excel
     * @param name
     * @return
     * @throws Exception
     */
    public static boolean isExcelFormat(String name){
        boolean flag = true;
        //name.endsWith(".xls")//字符串自带的末尾匹配
        //name.matches("^.*xls$")//匹配任意以xls结尾
        //name.matches("[.]*.xls")//和前者一样
        if (name.endsWith(".xls")){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    /**
     * 日期转化为String
     *
     * @param date
     * @return date string
     */
    public static String formatDate(Date date) throws Exception {
        if (null == date) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.US);
        return sdf.format(date);
    }

    /**
     * 格式化string为Date
     *
     * @param datestr
     * @return date
     */
    public static Date parseDate(String datestr) throws  Exception {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        String fs = "yyyy-MM-dd";
            /*if (datestr.indexOf(':') > 0) {
                fs = "yyyy-MM-dd HH:mm:ss";
            } else {
                fs = "yyyy-MM-dd";
            }*/
        SimpleDateFormat sdf = new SimpleDateFormat(fs, Locale.UK);
        return sdf.parse(datestr);
    }

    /**
     * 根据表名获取当前所有必输字段
     * @param tableName 表名
     * @param session 通过session工厂打开session类
     * @throws Exception
     */
    public static List<String> getAllColumn(String tableName, SqlSession session) throws  Exception{
        //SqlSession session = sessionFactory.openSession();
        Connection conn = DBUtil.getConnectionBySqlSession(session);
        DatabaseMetaData meta = conn.getMetaData();
        //获取数据库所有必输字段
        List<String> allColumn  = DBUtil.getNotNullColumn(tableName, meta);
        log.info("*****************"+allColumn);
        return allColumn;
    }

    /**
     * 判断当前字段是否必输
     * @param allColumn 表中所有字段
     * @param fieldName 需要效验的字段名
     * @return true为必输，false为非必输
     * @throws Exception
     */
    public static boolean getNotNullColumn(List<String> allColumn,String fieldName) throws  Exception{
        boolean flag = false;
        for (String column : allColumn){
            column = column.replaceAll("_","").toLowerCase();
            if (fieldName.equals(column)){
                flag = true;
                break;
            }
        }
        log.info("*****************"+fieldName+"为必输项{}"+flag);
        return flag;
    }

}
