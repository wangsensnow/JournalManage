package hap.exam.hscs_itf_imp_interfaces.controllers;

import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hap.exam.hscs_itf_imp_interfaces.dto.ItfImpInterfaces;
import hap.exam.hscs_itf_imp_interfaces.service.IItfImpInterfacesService;
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
public class ItfImpInterfacesController extends BaseController{

@Autowired
private IItfImpInterfacesService service;


@RequestMapping(value = "/hscs/itf/imp/interfaces/query")
@ResponseBody
public ResponseData query(ItfImpInterfaces dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
    @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request) {
    IRequest requestContext = createRequestContext(request);
    return new ResponseData(service.select(requestContext,dto,page,pageSize));
}

@RequestMapping(value = "/hscs/itf/imp/interfaces/submit")
@ResponseBody
public ResponseData update(@RequestBody List<ItfImpInterfaces> dto, BindingResult result, HttpServletRequest request){
getValidator().validate(dto, result);
if (result.hasErrors()) {
ResponseData responseData = new ResponseData(false);
responseData.setMessage(getErrorMessage(result, request));
return responseData;
}
    IRequest requestCtx = createRequestContext(request);
    return new ResponseData(service.batchUpdate(requestCtx, dto));
}

@RequestMapping(value = "/hscs/itf/imp/interfaces/remove")
@ResponseBody
public ResponseData delete(HttpServletRequest request,@RequestBody List<ItfImpInterfaces> dto){
    service.batchDelete(dto);
    return new ResponseData();
}
}