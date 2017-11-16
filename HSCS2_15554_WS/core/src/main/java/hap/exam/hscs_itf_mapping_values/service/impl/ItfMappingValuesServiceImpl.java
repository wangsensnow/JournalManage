package hap.exam.hscs_itf_mapping_values.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hap.exam.hscs_itf_mapping_values.dto.ItfMappingValues;
import hap.exam.hscs_itf_mapping_values.service.IItfMappingValuesService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfMappingValuesServiceImpl extends BaseServiceImpl<ItfMappingValues> implements IItfMappingValuesService{

}