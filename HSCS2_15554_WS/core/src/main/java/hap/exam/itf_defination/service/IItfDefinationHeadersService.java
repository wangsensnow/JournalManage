package hap.exam.itf_defination.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hap.exam.itf_defination.dto.ItfDefinationHeaders;

public interface IItfDefinationHeadersService extends IBaseService<ItfDefinationHeaders>, ProxySelf<IItfDefinationHeadersService>{
    Long seletHeaderIdByName(String name);
}