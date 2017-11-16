package hap.exam.hscs_itf_imp_headers.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hap.exam.hscs_itf_imp_headers.dto.ItfImpHeaders;
import hap.exam.hscs_itf_imp_headers.service.IItfImpHeadersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfImpHeadersServiceImpl extends BaseServiceImpl<ItfImpHeaders> implements IItfImpHeadersService{

}