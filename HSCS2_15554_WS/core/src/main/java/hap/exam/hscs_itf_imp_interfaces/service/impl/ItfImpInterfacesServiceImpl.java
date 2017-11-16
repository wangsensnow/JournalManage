package hap.exam.hscs_itf_imp_interfaces.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hap.exam.hscs_itf_imp_interfaces.dto.ItfImpInterfaces;
import hap.exam.hscs_itf_imp_interfaces.service.IItfImpInterfacesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItfImpInterfacesServiceImpl extends BaseServiceImpl<ItfImpInterfaces> implements IItfImpInterfacesService{

}