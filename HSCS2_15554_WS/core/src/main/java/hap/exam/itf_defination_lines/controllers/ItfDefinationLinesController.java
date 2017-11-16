package hap.exam.itf_defination_lines.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hap.exam.itf_defination_lines.dto.ItfDefinationLines;
import hap.exam.itf_defination_lines.service.IItfDefinationLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItfDefinationLinesController extends BaseController {

    @Autowired
    private IItfDefinationLinesService service;


    @RequestMapping(value = "/hscs/itf/defination/lines/query")
    @ResponseBody
    public ResponseData query(ItfDefinationLines dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hscs/itf/defination/lines/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ItfDefinationLines> dto, BindingResult result, HttpServletRequest request) {

        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hscs/itf/defination/lines/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ItfDefinationLines> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
    /**
     * 新建接口行。
     */
        @RequestMapping(value = "/hscs/itf/defination/lines/insert")
        @ResponseBody
        public ResponseData createLines(HttpServletRequest request,@RequestBody List<ItfDefinationLines> lists){
            IRequest iRequest = createRequestContext(request);
            for (int i = 0; i < lists.size(); i++) {
                lists.get(i).setObjectVersionNumber(new Long(1));
                service.insert(iRequest,lists.get(i));
            }
            return new ResponseData();
        }

    /**
     * 获取值集字段
     * @param request
     * @param code
     * @return
     */
        @RequestMapping(value = "/hscs/itf/defination/lines/getFlexSetColumn")
        @ResponseBody
        public ResponseData getFlexSetColumn(HttpServletRequest request,String code){
           List list = service.selectFlexSetColumn(code);
            return new ResponseData(list);
        }
}