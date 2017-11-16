package hap.exam.hscs_itf_mapping_headers.controllers;

import org.springframework.stereotype.Controller;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.dto.ResponseData;
import hap.exam.hscs_itf_mapping_headers.dto.ItfMappingHeaders;
import hap.exam.hscs_itf_mapping_headers.service.IItfMappingHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import java.util.List;

    @Controller
    public class ItfMappingHeadersController extends BaseController{

    @Autowired
    private IItfMappingHeadersService service;


    @RequestMapping(value = "/hscs/itf/mapping/headers/query")
    @ResponseBody
    public ResponseData query(ItfMappingHeaders dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
        @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(service.select(requestContext,dto,page,pageSize));
    }

    @RequestMapping(value = "/hscs/itf/mapping/headers/submit")
    @ResponseBody
public ResponseData update(@RequestBody List<ItfMappingHeaders> dto, BindingResult result, HttpServletRequest request){
getValidator().validate(dto, result);
if (result.hasErrors()) {
ResponseData responseData = new ResponseData(false);
responseData.setMessage(getErrorMessage(result, request));
return responseData;
}
        IRequest requestCtx = createRequestContext(request);
        return new ResponseData(service.batchUpdate(requestCtx, dto));
    }

    @RequestMapping(value = "/hscs/itf/mapping/headers/remove")
    @ResponseBody
    public ResponseData delete(HttpServletRequest request,@RequestBody List<ItfMappingHeaders> dto){
        service.batchDelete(dto);
        return new ResponseData();
    }
    }