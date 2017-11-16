package hap.exam.hscs_itf_mapping_headers.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hap.exam.hscs_itf_mapping_headers.dto.ItfMappingHeaders;
import hap.exam.hscs_itf_mapping_headers.service.IItfMappingHeadersService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfMappingHeadersServiceImpl extends BaseServiceImpl<ItfMappingHeaders> implements IItfMappingHeadersService{

}