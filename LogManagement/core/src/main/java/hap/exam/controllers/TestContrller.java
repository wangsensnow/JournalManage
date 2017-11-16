package hap.exam.controllers;

import com.hand.hap.system.controllers.BaseController;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @Author:WangSen
 * @Date:Created in 15:42-2017/11/8
 * @Description:
 */
@Controller
public class TestContrller extends BaseController {

    @RequestMapping(value = "api/public/exportExcel",method = RequestMethod.POST)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition", "attachment;filename=sen.xlsx");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        CellRangeAddress region = new CellRangeAddress(1, 1, 1, 6);
        sheet.addMergedRegion(region);

        MyExcelTemplate template = new MyExcelTemplate();
        template.getWbInstance().write(response.getOutputStream());
//        LoanCalculator.getWb().write(response.getOutputStream());


        workbook.close();
    }
}
