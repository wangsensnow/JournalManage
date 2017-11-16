package hap.exam.itf_defination.controllers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.excel.dto.ColumnInfo;
import com.hand.hap.excel.dto.ExportConfig;
import com.hand.hap.excel.service.IExportService;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.CodeValue;
import com.hand.hap.system.dto.ResponseData;
import com.hand.hap.system.mapper.CodeValueMapper;
import com.sun.istack.logging.Logger;
import hap.exam.hscs_itf_imp_headers.service.IItfImpHeadersService;
import hap.exam.hscs_itf_imp_interfaces.dto.ItfImpInterfaces;
import hap.exam.hscs_itf_imp_interfaces.service.IItfImpInterfacesService;
import hap.exam.hscs_itf_imp_lines.service.IItfImpLinesService;
import hap.exam.hscs_itf_mapping_headers.service.IItfMappingHeadersService;
import hap.exam.hscs_itf_mapping_lines.service.IItfMappingLinesService;
import hap.exam.hscs_itf_mapping_values.service.IItfMappingValuesService;
import hap.exam.itf_defination.dto.ItfDefinationHeaders;
import hap.exam.itf_defination.service.IItfDefinationHeadersService;
import hap.exam.itf_defination_lines.dto.ItfDefinationLines;
import hap.exam.itf_defination_lines.service.IItfDefinationLinesService;
import hap.exam.utils.ExcelUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

@Controller
public class ItfDefinationHeadersController extends BaseController {
    protected static Logger LOG = Logger.getLogger(ItfDefinationHeadersController.class);
    @Autowired
    ISysCodeRuleProcessService codeRuleProcessService;
    @Autowired
    private IItfMappingLinesService iItfMappingLinesService;
    @Autowired
    private IItfMappingHeadersService iItfMappingHeadersService;
    @Autowired
    private IItfMappingValuesService iItfMappingValuesService;
    @Autowired
    private IItfDefinationHeadersService service;
    @Autowired
    private CodeValueMapper codeValueMapper;
    @Autowired
    private IItfDefinationLinesService linesService;
    @Autowired
    private IExportService exportService;
    @Autowired
    private IItfImpHeadersService itfImpHeadersService;
    @Autowired
    private IItfImpLinesService iItfImpLinesService;
    @Autowired
    private IItfImpInterfacesService iItfImpInterfacesService;

    @RequestMapping(value = "/hscs/itf/defination/headers/query")
    @ResponseBody
    public ResponseData query(ItfDefinationHeaders dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hscs/itf/defination/headers/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ItfDefinationHeaders> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hscs/itf/defination/headers/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ItfDefinationHeaders> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }

    @RequestMapping(value = "/hscs/itf/defination/headers/removeHeaderAndLines")
    @ResponseBody
    public ResponseData removeHeaderAndLines(HttpServletRequest request, String headerId) {
        ItfDefinationHeaders itfDefinationHeaders = new ItfDefinationHeaders();
        itfDefinationHeaders.setHeaderId(new Long(headerId));
        service.deleteByPrimaryKey(itfDefinationHeaders);
        linesService.deleteByHeaderId(new Integer(headerId));
        return new ResponseData();
    }

    //插入一条数据
    @RequestMapping(value = "/hscs/itf/defination/headers/insert")
    @ResponseBody
    public String insert(HttpServletRequest request, ItfDefinationHeaders headers, ItfDefinationLines lines) {
        LOG.info(headers.toString());
        //初始化数据库必输字段。
        headers.setObjectVersionNumber(new Long(1));
        headers.setModuleCode("所属模块");

        CodeValue codeValue = new CodeValue();
        codeValue.setCodeId(new Long(10025));
        List<CodeValue> codeValues = codeValueMapper.selectCodeValuesByCodeId(codeValue);

        IRequest iRequest = createRequestContext(request);
        service.insert(iRequest, headers);
        lines.setHeaderId(headers.getHeaderId());

        return String.valueOf(headers.getHeaderId());
    }

    //插入一条数据
    @RequestMapping(value = "/hscs/itf/defination/headers/update")
    @ResponseBody
    public String update(HttpServletRequest request, ItfDefinationHeaders headers) {
        IRequest iRequest = createRequestContext(request);
        service.updateByPrimaryKey(iRequest, headers);
        return "success";
    }

    // http://localhost:8080/hscs/itf/defination/headers/exportExcel
    @RequestMapping(value = "/api/public/hscs/itf/defination/headers/exportExcel")
    public void createXLSForStudent(HttpServletRequest request, @RequestParam String config,
                                    HttpServletResponse httpServletResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        IRequest requestContext = createRequestContext(request);
        try {

            JavaType type = objectMapper.getTypeFactory().constructParametrizedType(ExportConfig.class,
                    ExportConfig.class, ItfDefinationHeaders.class, ColumnInfo.class);

            ExportConfig<Function, ColumnInfo> exportConfig = objectMapper.readValue(config, type);
            exportService.exportAndDownloadExcel("hap.exam.itf_defination.mapper.ItfDefinationHeadersMapper.select",
                    exportConfig, request, httpServletResponse, requestContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/api/public/hscs/itf/defination/headers/templateExcel")
    public void createTemplateExcel(HttpServletRequest request,
                                    HttpServletResponse response, @RequestParam String myMsg) {
        try {
            JSONObject jsonObject = JSONObject.fromObject(myMsg);
            String headerId = jsonObject.getString("headerId");
            String fromSystemValue = jsonObject.getString("fromSystemValue");
            String itfName = jsonObject.getString("itfName");
            String textFormat = jsonObject.getString("textFormat");


            //从数据库中获取列名
            ItfDefinationLines itfDefinationLines = new ItfDefinationLines();
            itfDefinationLines.setHeaderId(Long.valueOf(headerId));
            itfDefinationLines.setColumnType("源数据列");
            itfDefinationLines.setEnableFlag("Y");
            itfDefinationLines.setShowFlag("Y");
            IRequest iRequest = createRequestContext(request);
            List<ItfDefinationLines> itfDefinationLinesList = linesService.select(iRequest, itfDefinationLines, 1, 100);
            Collections.sort(itfDefinationLinesList);

            if("CSV".equals(textFormat)){
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate ld = LocalDate.now();
                String fileName = fromSystemValue + "_" + itfName + "_" + ld.format(dateTimeFormatter) + ".csv";
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                response.setHeader("X-Frame-Options", "SAMEORIGIN");
                response.setContentType("application/vnd.ms-excel;charset=utf-8");

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(),"utf-8"));

                bw.append("来源系统模板").append(",").append("接口名称");
//                bw.write("来源系统模板");
//                bw.write(",");
//                bw.write("接口名称");
                for (int i = 0; i < itfDefinationLinesList.size(); i++) {
                    bw.write(",");
                    ItfDefinationLines itfDefinationLines1 = itfDefinationLinesList.get(i);
                    bw.write(itfDefinationLines1.getTitleText());
                }
                bw.newLine();
                for (int i = 0; i < 10; i++) {
                    bw.write(fromSystemValue);
                    bw.write(",");
                    bw.write(itfName);
                    bw.newLine();
                }
                //设置bom信息
                bw.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
                bw.flush();
                bw.close();
                return;
            }

            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet("接口模板");
            // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
            HSSFRow row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

            HSSFCell cell = row.createCell(0);
            cell.setCellValue("来源系统模板*");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("接口名称*");
            cell.setCellStyle(style);



            for (int i = 0; i < itfDefinationLinesList.size(); i++) {
                ItfDefinationLines line = itfDefinationLinesList.get(i);
                cell = row.createCell(i + 2);
                if (line.getRequiredFlag().equals("Y")) {
                    cell.setCellValue(line.getTitleText() + "*");
                } else {
                    cell.setCellValue(line.getTitleText());
                }
                cell.setCellStyle(style);
            }


            for (int i = 0; i < 10; i++) {
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                cell.setCellValue(fromSystemValue);
                cell.setCellStyle(style);
                cell = row.createCell(1);
                cell.setCellValue(itfName);
                cell.setCellStyle(style);
            }

            LocalDate ld = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String fileName = fromSystemValue + "_" + itfName + "_" + ld.format(dateTimeFormatter) + ".xls";
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            //表示允许在同一域名中，加载该url。
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    @RequestMapping(value = "/api/public/hscs/itf/defination/headers/uploadExcel", method = RequestMethod.POST)
    @ResponseBody
    public String uploadExcel(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam MultipartFile files) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        HttpSession session = request.getSession();


        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        HSSFWorkbook hwb = new HSSFWorkbook(files.getInputStream());
        HSSFSheet sheet = hwb.getSheetAt(0);

        //文件验证：
        //如果文件为空
        if(files == null){
            return "empty";
        }
        if(hwb.getNumberOfSheets()>1){
            return "sheet大于1";
        }
        if(!ExcelUtil.getCellNumbersEqual(sheet,0,sheet.getLastRowNum())||!ExcelUtil.getCellNumbersEqual(sheet,1,sheet.getLastRowNum())){
            return "EXCEL中来源系统/接口名称不唯一";
        }

            //初始化头对象
        ItfImpInterfaces itfImpInterfaces = new ItfImpInterfaces();

        HSSFRow row2 = sheet.getRow(1);
        HSSFCell cell2 = row2.getCell(0);
        itfImpInterfaces.setSourceSystem(cell2.getStringCellValue());

        String userName = (String) session.getAttribute("userName");
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        String batchNumStr = userName+dateTimeFormatter.format(localDate);

        //如果此批号被占用则验证错误。
        ItfImpInterfaces itfTest = new ItfImpInterfaces();
        itfTest.setBatchNum(batchNumStr);
        List list = iItfImpInterfacesService.select(createRequestContext(request),itfTest,0,100);
        if(list.size()!=0){
            return "此批次号已被占用锁定，请稍候上传数据！";
        }
        itfImpInterfaces.setBatchNum(batchNumStr);


        cell2 = row2.getCell(1);
        itfImpInterfaces.setInterfaceName(cell2.getStringCellValue());

        //验证接口是否冻结
        ItfDefinationHeaders itfDefinationTest = new ItfDefinationHeaders();
        ItfDefinationHeaders itfDefinationTest1 = new ItfDefinationHeaders();
        String str = cell2.getStringCellValue();
        itfDefinationTest.setInterfaceName(cell2.getStringCellValue());
        List<ItfDefinationHeaders> list1 = service.select(createRequestContext(request),itfDefinationTest,1,100);
        itfDefinationTest1=service.selectByPrimaryKey(createRequestContext(request),itfDefinationTest);
        System.out.println(list1.toString());
            try {
            if("N".equals(list1.get(0).getFrozenFlag())){
                return "该接口未冻结！";
            }
        } catch (Exception e) {
            return "不存在此接口";
        }

        row2 = sheet.getRow(0);
        itfImpInterfaces.setModuleCode("ModuleCode");
        itfImpInterfaces.setLineCount(new Long(row2.getLastCellNum()-1));
        itfImpInterfaces.setProcessMessage("ProcessMessage");

        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i);
            Class<ItfImpInterfaces> c = ItfImpInterfaces.class;

            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                //插入头数据
                if (i == 0) {
                    if (j >= 2) {
                        HSSFCell cell = row.getCell(j);
                        Method m = c.getMethod("setValue"+(j-1),String.class);
                        m.invoke(itfImpInterfaces,cell.getStringCellValue());
                        if(j == row.getLastCellNum()-1){
                            iItfImpInterfacesService.insertSelective(createRequestContext(request),itfImpInterfaces);
                        }

                    }
                }
//                else {
//                    if (j >= 2) {
//                        HSSFCell cell = row.getCell(j);
//
//                        Method m = c.getMethod("setValue" + (j - 1), String.class);
//                        m.invoke(itfMappingValues,cell.getStringCellValue());
//                        itfMappingValues.setHeaderId(itfMappingHeader.getHeaderId());
//                        if(j==row.getLastCellNum()-1){
//                            iItfMappingValuesService.insertSelective(createRequestContext(request),itfMappingValues);
//                        }
//                    }
//                }
                //插入行数据
            }
        }
        return "success";
    }

}