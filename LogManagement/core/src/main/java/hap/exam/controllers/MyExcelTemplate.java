package hap.exam.controllers;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.HashMap;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellStyle.*;

/**
 * @Author:WangSen
 * @Date:Created in 9:59-2017/11/9
 * @Description:
 */
public class MyExcelTemplate {
    private Workbook wb;
    private Map<String, CellStyle> styles;

    Workbook getWbInstance() {
        wb = new XSSFWorkbook();
        styles = createStyles();
        return exditExcel(wb);
    }

    public Workbook exditExcel(Workbook wb) {

        Sheet sheet = wb.createSheet();
        sheet.setDisplayGridlines(false);
        //先将行创建出来，之后直接取就行了。
        for (int i = 0; i <= 32; i++) {

            if (i >= 12 && i <= 18) {
                sheet.createRow(i).setHeightInPoints(50);
            } else {
                sheet.createRow(i);
            }
        }
        //将sheet锁定
        sheet.protectSheet("");
        setColmnWidth(sheet);
        editTitle(sheet);

        for (int i = 4; i <= 11; i++) {
            Row rowx = sheet.getRow(i);
            for (int j = 1; j <= 9; j++) {
                Cell cellx = rowx.createCell(j);
                switch (j) {
                    case 1:
//                        if (i != 11) {
//                            cellx.setCellFormula("IF(WEEKDAY($C$" + (i + 1) + ",2)=7,\"日\",NUMBERSTRING(WEEKDAY($C$" + (i + 1) + ",2),1))");
//                            cellx.setCellStyle(styles.get("base"));
//                        }
                        cellx.setCellStyle(styles.get("base"));
                        switch (i) {
                            case 4:
                                cellx.setCellValue("一");
                                break;
                            case 5:
                                cellx.setCellValue("二");
                                break;
                            case 6:
                                cellx.setCellValue("三");
                                break;
                            case 7:
                                cellx.setCellValue("四");
                                break;
                            case 8:
                                cellx.setCellValue("五");
                                break;
                            case 9:
                                cellx.setCellValue("六");
                                break;
                            case 10:
                                cellx.setCellValue("日");
                                break;
                            default:
                        }
                        break;
                    case 2:
                        if (i != 11) {
                            if (i > 4) {
                                cellx.setCellFormula("$C$5+" + (i - 4));
                            }
                        }
                        break;
                    case 7:
                        if (i != 11) {
                            cellx.setCellFormula("$C$" + (i + 1) + "+7");
                        }
                        break;
                    default:
                        cellx.setCellStyle(styles.get("content"));
                        if (i == 11 && j == 4) {
                            cellx.setCellFormula("$e$5+$e$6+$e$7+$e$8+$e$9+$e$10+$e$11");
                        }
                        break;
                }
            }
        }
        mergeCells(sheet);

        //编辑13-19行：
        for (int i = 12; i <= 18; i++) {
            Row rowx = sheet.getRow(i);
            sheet.addMergedRegion(new CellRangeAddress(i, i, 1, 2));
            sheet.addMergedRegion(new CellRangeAddress(i, i, 3, 9));
            for (int j = 1; j <= 9; j++) {
                rowx.createCell(j).setCellStyle(styles.get("content"));
            }
        }
        sheet.getRow(12).getCell(2).setCellValue("计划（Plan）/执行（Do）/检查（Check）/改善（Action）\n" +
                "检查，计划和执行发现偏差时，分析原因，制定改善方案");
        sheet.getRow(12).getCell(1).setCellValue("PDCA总结");
        sheet.getRow(13).getCell(1).setCellValue("难题解决");
        sheet.getRow(14).getCell(1).setCellValue("技术设计");
        sheet.getRow(15).getCell(1).setCellValue("中短期目标");
        sheet.getRow(16).getCell(1).setCellValue("新技术探索");
        sheet.getRow(17).getCell(1).setCellValue("项目情况");
        sheet.getRow(18).getCell(1).setCellValue("其他吐槽");

        //剩余行的编写
        CellStyle style = styles.get("notes");

        Cell cell = sheet.getRow(20).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("v1.0");
        cell = sheet.getRow(21).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("说明1：只需修改第一个日期C4单元格（日期格式，不是数字），后面的日期将自动更新");
        cell = sheet.getRow(22).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("说明2: 移动或复制此模板，每天抽出几分钟，整理一下，每周一晚8点之前反馈上一周情况");
        cell = sheet.getRow(23).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("说明3：帮助记录一周的工作情况，并让你的组长知道你的进步与成长");
        cell = sheet.getRow(24).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("说明4：工作时间合计仅仅记录有效工作时间，需要客观真实，除去吃饭、休息等时间，以方便公平公正的统一标准统计");
        cell = sheet.getRow(25).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("说明5：其中技术设计/难题解决/新技术探索，是升S的硬性技术指标，需要大家有意识、有目标定向的在平常工作中做好积累，而不是临时抱佛脚");

        cell = sheet.getRow(27).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("v2.0");
        cell = sheet.getRow(28).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("改善1：项目名称统一放到卡片头部，此处填写主要项目名称，如果跨项目，可以备注中说明");
        cell = sheet.getRow(29).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("改善2：区分实际/计划两个栏位，大家每周末需要根据项目安排及自身安排，合理制定下周计划，提高大家计划/执行/检查/改善意识");
        cell = sheet.getRow(30).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("改善3：增加PDCA总结，大家必须填写PDCA总结栏位");
        cell = sheet.getRow(31).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("改善4：每周单独一个Excel，并且要求Excel文件名称及页签名称中日期部分均为实际周一所在的日期");
        cell = sheet.getRow(32).createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("P.S. 我们仍然只需要修改第一个日期C4单元格（日期格式，而不是数字），后面的日期将自动更新，包括实际/计划栏位抬头日期显示，以及其他星期内日期");

        colourTheWb(sheet);
        setTheWbBorder(sheet);
        setUnLockedCell(sheet);
        setTheCellFormat(sheet);
        setComment(sheet);
        return wb;
    }

    /**
     * 编辑第四行表格标题部分。
     *
     * @param sheet
     */
    public void editTitle(Sheet sheet) {
        CellStyle style = createStyles().get("base");
        Row row4 = sheet.createRow(3);
        Cell cell = row4.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("星期");

        cell = row4.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("日期");

        cell = row4.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("工作时间");

        cell = row4.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("合计");

        cell = row4.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("工作内容");

        cell = row4.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("备注");

        cell = row4.createCell(7);
        cell.setCellStyle(style);
        cell.setCellValue("日期");

        cell = row4.createCell(8);
        cell.setCellStyle(style);
        cell.setCellValue("计划内容");

        cell = row4.createCell(9);
        cell.setCellStyle(style);
        cell.setCellValue("备注");
    }

    /**
     * 合并单元格并实现合并的单元格的样式。
     *
     * @param sheet
     */
    public void mergeCells(Sheet sheet) {

        //对项目标题部分样式的设计
        CellRangeAddress region = new CellRangeAddress(1, 1, 1, 6);
        Row row = sheet.createRow(1);
        row.setHeightInPoints((short) (19));


        Cell cell = null;
        for (int i = 1; i <= 6; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(styles.get("title"));
        }
        CellStyle style = createStyles().get("title");
        style.setLocked(false);
        row.getCell(1).setCellStyle(style);
        sheet.addMergedRegion(region);
        //对实际（日期）部分单元格的合并和编辑样式
        CellRangeAddress region2 = new CellRangeAddress(2, 2, 1, 6);
        row = sheet.createRow(2);
        for (int i = 1; i <= 6; i++) {
            cell = row.createCell(i);
            if (i == 1) {
                cell.setCellFormula("\"实际(\"&TEXT($C$5,\"yyyy-mm-dd\")&\")\"");
            }
            cell.setCellStyle(styles.get("title"));
        }
        sheet.addMergedRegion(region2);
        //对“计划”标题部分合并单元格
        sheet.addMergedRegion(CellRangeAddress.valueOf("$h$3:$j$3"));
        row = sheet.getRow(2);
        for (int i = 7; i <= 9; i++) {
            cell = row.createCell(i);
            if (i == 7) {
                cell.setCellFormula("\"计划(\"&TEXT($C$5+7,\"yyyy-mm-dd\")&\")\"");
            }
            cell.setCellStyle(styles.get("title"));
        }
        //对12行 合计 单元格合并：
        sheet.addMergedRegion(CellRangeAddress.valueOf("$b$12:$c$12"));
        row = sheet.getRow(11);
        for (int i = 1; i <= 2; i++) {
            cell = row.createCell(i);
            if (i == 1) {
                cell.setCellValue("合计");
            }
            cell.setCellStyle(styles.get("base"));
        }

        //对12行计划部分进行合并
        sheet.addMergedRegion(CellRangeAddress.valueOf("$h$12:$j$12"));
        row = sheet.getRow(11);
        for (int i = 7; i <= 9; i++) {
            cell = row.createCell(i);
            cell.setCellStyle(styles.get("base"));
        }
    }

    /*
    * 设置列的宽度。
    * */
    public void setColmnWidth(Sheet sheet) {
        sheet.setColumnWidth(0, 256 * 1);
        sheet.setColumnWidth(1, 256 * 5);
        sheet.setColumnWidth(2, 256 * 7);
        sheet.setColumnWidth(3, 256 * 11);
        sheet.setColumnWidth(4, 256 * 5);
        sheet.setColumnWidth(5, 256 * 344 / 8);
        sheet.setColumnWidth(6, 256 * 137 / 8);
        sheet.setColumnWidth(7, 256 * 7);
        sheet.setColumnWidth(8, 256 * 196 / 8);
        sheet.setColumnWidth(9, 256 * 137 / 8);
    }


    /**
     * 给整个表设置背景色
     * 注意：在给单元格涂颜色的时候，使用的并不是全局变量styles中的CellStyle对象，而是创建了新的CellStyle对象，
     * 因此，当修改这些单元格的样式的时候并不会改变全局变量styles中的CellStyle对象。
     *
     * @param sheet
     */
    public void colourTheWb(Sheet sheet) {
        for (int i = 1; i <= 18; i++) {
            if (i == 2) {
                for (int j = 1; j <= 6; j++) {
                    XSSFCellStyle style = (XSSFCellStyle) createStyles().get("title");
                    byte[] colorRgb = {(byte) (0xff & 255), (byte) (0xff & 192), (byte) 0};
                    setCellCustomColor(style, colorRgb, i, j, sheet);
                }
                for (int j = 7; j <= 9; j++) {
                    XSSFCellStyle style = (XSSFCellStyle) createStyles().get("title");
                    byte[] colorRgb = {(byte) (0xff & 0), (byte) (0xff & 176), (byte) 240};
                    setCellCustomColor(style, colorRgb, i, j, sheet);
                }
            }
            if (i == 3) {
                for (int j = 1; j <= 6; j++) {
                    XSSFCellStyle style = (XSSFCellStyle) createStyles().get("base");
                    byte[] colorRgb = {(byte) (0xff & 255), (byte) (0xff & 192), (byte) 0};
                    setCellCustomColor(style, colorRgb, i, j, sheet);
                }
                for (int j = 7; j <= 9; j++) {
                    XSSFCellStyle style = (XSSFCellStyle) createStyles().get("base");
                    byte[] colorRgb = {(byte) (0xff & 0), (byte) (0xff & 176), (byte) 240};
                    setCellCustomColor(style, colorRgb, i, j, sheet);
                }
            }
            if (i >= 4 && i <= 18) {
                for (int j = 1; j <= 2; j++) {
                    XSSFCellStyle style = (XSSFCellStyle) createStyles().get("base");
                    byte[] colorRgb = {(byte) (0xff & 197), (byte) (0xff & 217), (byte) 241};
                    setCellCustomColor(style, colorRgb, i, j, sheet);
                }
            }
            if (i >= 4 && i <= 10) {
                XSSFCellStyle style = (XSSFCellStyle) createStyles().get("base");
                byte[] colorRgb = {(byte) (0xff & 197), (byte) (0xff & 217), (byte) 241};
                setCellCustomColor(style, colorRgb, i, 7, sheet);
            }

            if (i == 11) {
                for (int j = 1; j <= 9; j++) {
                    XSSFCellStyle style = null;
                    if (j == 1) {
                        style = (XSSFCellStyle) createStyles().get("base");
                        style.setAlignment(ALIGN_CENTER);
                    } else {
                        style = (XSSFCellStyle) createStyles().get("content");
                    }
                    byte[] colorRgb = {(byte) (0xff & 197), (byte) (0xff & 217), (byte) 241};
                    setCellCustomColor(style, colorRgb, i, j, sheet);
                }
            }

            if (i == 12 || i == 15 || i == 17) {
                for (int j = 2; j <= 9; j++) {
                    XSSFCellStyle style = null;
                    style = (XSSFCellStyle) createStyles().get("content");
                    byte[] colorRgb = {(byte) (0xff & 255), (byte) (0xff & 255), (byte) 0};
                    setCellCustomColor(style, colorRgb, i, j, sheet);
                }
            }
        }
    }

    /**
     * 给某个单元格设置颜色
     *
     * @param colorRgb
     * @param row
     * @param column
     * @param sheet
     */
    public void setCellCustomColor(XSSFCellStyle style, byte[] colorRgb, int row, int column, Sheet sheet) {
        XSSFColor xssfColor = new XSSFColor();
        xssfColor.setRGB(colorRgb);
        style.setFillForegroundColor(xssfColor);
        style.setFillPattern(SOLID_FOREGROUND);
        sheet.getRow(row).getCell(column).setCellStyle(style);
    }

    /**
     * 设置整个表的边框
     * 注意要放在创建完所有单元格对象之后调用
     *
     * @param sheet
     */
    public void setTheWbBorder(Sheet sheet) {
        CellStyle style1 = createStyles().get("notes");
        style1.setBorderLeft(BORDER_MEDIUM);
        for (int i = 3; i <= 18; i++) {
            CellStyle style = createStyles().get("notes");
            style.setBorderRight(BORDER_MEDIUM);
            sheet.getRow(i).createCell(0).setCellStyle(style);
            CellStyle style2 = null;
            sheet.getRow(i).createCell(10).setCellStyle(style1);
            if (i >= 3 && i <= 10) {
                if (i == 3) {
                    style2 = sheet.getRow(i).getCell(6).getCellStyle();
                } else {
                    style2 = createStyles().get("content");
                }
                style2.setBorderRight(BORDER_MEDIUM);
                sheet.getRow(i).getCell(6).setCellStyle(style2);
            }

            if (i == 11) {
                CellStyle style4 = sheet.getRow(11).getCell(6).getCellStyle();
                style4.setBorderRight(BORDER_MEDIUM);
                sheet.getRow(11).getCell(6).setCellStyle(style4);
                CellStyle style5 = sheet.getRow(11).getCell(9).getCellStyle();
                style5.setBorderRight(BORDER_MEDIUM);
                sheet.getRow(11).getCell(9).setCellStyle(style5);

                for (int j = 1; j <= 9; j++) {
                    CellStyle style3 = null;
                    style3 = sheet.getRow(i).getCell(j).getCellStyle();
                    style3.setBorderBottom(BORDER_MEDIUM);
                    sheet.getRow(i).getCell(j).setCellStyle(style3);
                }
            }
            if (i == 18) {

                for (int j = 1; j <= 9; j++) {
                    CellStyle style3 = null;
                    if (j == 1) {
                        style3 = sheet.getRow(i).getCell(j).getCellStyle();
                    } else {
                        style3 = createStyles().get("content");
                    }
                    style3.setBorderBottom(BORDER_MEDIUM);
                    sheet.getRow(i).getCell(j).setCellStyle(style3);
                }
                CellStyle style3 = sheet.getRow(i).getCell(9).getCellStyle();
                style3.setBorderRight(BORDER_MEDIUM);
                sheet.getRow(i).getCell(9).setCellStyle(style3);
            }
        }


    }

    public Map<String, CellStyle> createStyles() {
        Map<String, CellStyle> maps = new HashMap<String, CellStyle>();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setAlignment(ALIGN_LEFT);
        cellStyle.setVerticalAlignment(VERTICAL_CENTER);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 10);
        cellStyle.setWrapText(false);
        font.setBold(true);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        maps.put("base", cellStyle);

        cellStyle = wb.createCellStyle();
        cellStyle.setBorderLeft(CellStyle.BORDER_MEDIUM);
        cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        cellStyle.setBorderRight(CellStyle.BORDER_MEDIUM);
        cellStyle.setBorderBottom(CellStyle.BORDER_MEDIUM);
        cellStyle.setAlignment(ALIGN_LEFT);
        cellStyle.setVerticalAlignment(VERTICAL_CENTER);
        font = wb.createFont();
        font.setBold(true);
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 16);
        cellStyle.setWrapText(true);
        cellStyle.setFont(font);
        maps.put("title", cellStyle);

        cellStyle = wb.createCellStyle();
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setAlignment(ALIGN_LEFT);
        cellStyle.setVerticalAlignment(VERTICAL_CENTER);
        font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        font.setBold(false);
        cellStyle.setWrapText(true);
        cellStyle.setFont(font);
        cellStyle.setLocked(false);
        maps.put("content", cellStyle);

        cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(ALIGN_LEFT);
        cellStyle.setVerticalAlignment(VERTICAL_CENTER);
        font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);
        font.setBold(false);
        cellStyle.setFont(font);
        cellStyle.setWrapText(false);
        maps.put("notes", cellStyle);

        return maps;
    }

    /**
     * 设置部分不被锁定和要锁定的的单元格
     */
    public void setUnLockedCell(Sheet sheet) {
        CellStyle style = sheet.getRow(4).getCell(2).getCellStyle();
        style.setLocked(false);
        sheet.getRow(4).getCell(2).setCellStyle(style);
        style = sheet.getRow(11).getCell(4).getCellStyle();
        style.setLocked(true);
        sheet.getRow(11).getCell(4).setCellStyle(style);
    }

    public void setTheCellFormat(Sheet sheet) {
        Cell cell = null;
        Cell cell2 = null;
        CellStyle style = null;
        CellStyle style2 = null;
        for (int i = 4; i <= 10; i++) {
            cell = sheet.getRow(i).getCell(2);
            cell2 = sheet.getRow(i).getCell(7);
            style = cell.getCellStyle();
            style2 = cell2.getCellStyle();
            style.setDataFormat(wb.createDataFormat().getFormat("dd"));
            style2.setDataFormat(wb.createDataFormat().getFormat("dd"));
            cell.setCellStyle(style);
            cell2.setCellStyle(style2);
        }
    }

    public void setComment(Sheet sheet) {
        Drawing drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = wb.getCreationHelper();
        // 定义注释的大小和位置，详见文档
//        HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 4, 2));
        // 设置注释内容
//        comment.setString(new HSSFRichTextString("senn:此处为日期，而非数字，固定为星期一"));
//        XSSFClientAnchor xssfClientAnchor = new XSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 4, 2);
        ClientAnchor anchor = factory.createClientAnchor();
        Comment comment1 = drawing.createCellComment(anchor);
        RichTextString str1 = factory.createRichTextString("此处为日期，而非数字，固定为星期一");
        comment1.setString(str1);
        comment1.setAuthor("senn");
        sheet.getRow(4).getCell(2).setCellComment(comment1);

        anchor = factory.createClientAnchor();
        comment1 = drawing.createCellComment(anchor);
        str1 = factory.createRichTextString("只需要改变星期一的日期，即单元格C4");
        comment1.setString(str1);
        comment1.setAuthor("senn");
        sheet.getRow(3).getCell(2).setCellComment(comment1);

        anchor = factory.createClientAnchor();
        comment1 = drawing.createCellComment(anchor);
        str1 = factory.createRichTextString("只记录有效工作时间，除去休息时间");
        comment1.setString(str1);
        comment1.setAuthor("senn");
        sheet.getRow(3).getCell(4).setCellComment(comment1);

        anchor = factory.createClientAnchor();
        comment1 = drawing.createCellComment(anchor);
        str1 = factory.createRichTextString("仅仅做简单的标注说明，无需过多描述。");
        comment1.setString(str1);
        comment1.setAuthor("senn");
        sheet.getRow(3).getCell(5).setCellComment(comment1);

        anchor = factory.createClientAnchor();
        comment1 = drawing.createCellComment(anchor);
        str1 = factory.createRichTextString("仅仅做简单的标注说明，无需过多描述。");
        comment1.setString(str1);
        comment1.setAuthor("senn");
        sheet.getRow(3).getCell(8).setCellComment(comment1);

        anchor = factory.createClientAnchor();
        comment1 = drawing.createCellComment(anchor);
        str1 = factory.createRichTextString("必填项");
        comment1.setString(str1);
        comment1.setAuthor("senn");
        sheet.getRow(12).getCell(1).setCellComment(comment1);
    }

}
