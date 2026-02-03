package umc.study.service.TempService;

import org.springframework.stereotype.Service;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.TempHandler;

@Service
public class TempCommandQueryImpl implements TempQueryService {

    @Override
    public void CheckFlag(Integer flag) {
        if(flag == 1)
            throw new TempHandler(ErrorStatus._TEMP_EXCEPTION);
    }
}
