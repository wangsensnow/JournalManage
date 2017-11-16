package hap.exam.hscs_itf_imp_lines.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hap.exam.hscs_itf_imp_lines.dto.ItfImpLines;
import hap.exam.hscs_itf_imp_lines.service.IItfImpLinesService;
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
public class ItfImpLinesController extends BaseController{

@Autowired
private IItfImpLinesService service;


@RequestMapping(value = "/hscs/itf/imp/lines/query")
@ResponseBody
public ResponseData query(ItfImpLines dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(service.select(requestContext,dto,page,pageSize));
}

@RequestMapping(value = "/hscs/itf/imp/lines/submit")
@ResponseBody
public ResponseData update(@RequestBody List<ItfImpLines> dto, BindingResult result, HttpServletRequest request){
getValidator().validate(dto, result);
if (result.hasErrors()) {
ResponseData responseData = new ResponseData(false);
responseData.setMessage(getErrorMessage(result, request));
return responseData;
}
    IRequest requestCtx = createRequestContext(request);
    return new ResponseData(service.batchUpdate(requestCtx, dto));
}

@RequestMapping(value = "/hscs/itf/imp/lines/remove")
@ResponseBody
public ResponseData delete(HttpServletRequest request,@RequestBody List<ItfImpLines> dto){
    service.batchDelete(dto);
    return new ResponseData();
}
}