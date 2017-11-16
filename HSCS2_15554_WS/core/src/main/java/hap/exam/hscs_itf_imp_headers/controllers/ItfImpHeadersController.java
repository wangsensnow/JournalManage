package hap.exam.hscs_itf_imp_headers.controllers;

import com.hand.hap.code.rule.exception.CodeRuleException;
import com.hand.hap.code.rule.service.ISysCodeRuleProcessService;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hap.exam.hscs_itf_imp_headers.dto.ItfImpHeaders;
import hap.exam.hscs_itf_imp_headers.service.IItfImpHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ItfImpHeadersController extends BaseController {
    @Autowired
    ISysCodeRuleProcessService codeRuleProcessService;
    @Autowired
    private IItfImpHeadersService service;

    @RequestMapping(value = "api/public/hscs/itf/imp/headers/test")
    public ResponseData test(HttpServletRequest request, HttpServletResponse response) {
        String str = null;
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        try {
            str = codeRuleProcessService.getRuleCode("Test");
        } catch (CodeRuleException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        return null;
    }

    @RequestMapping(value = "/hscs/itf/imp/headers/query")
    @ResponseBody
    public ResponseData query(ItfImpHeaders dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext, dto, page, pageSize));
    }

    @RequestMapping(value = "/hscs/itf/imp/headers/submit")
    @ResponseBody
    public ResponseData update(@RequestBody List<ItfImpHeaders> dto, BindingResult result, HttpServletRequest request) {
        getValidator().validate(dto, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hscs/itf/imp/headers/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request, @RequestBody List<ItfImpHeaders> dto) {
        service.batchDelete(dto);
        return new ResponseData();
    }
}