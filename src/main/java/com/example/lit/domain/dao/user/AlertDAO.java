package com.example.lit.domain.dao.user;

import com.example.lit.domain.vo.user.AlertDTO;
import com.example.lit.domain.vo.user.AlertVO;
import com.example.lit.mapper.user.AlertMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlertDAO {
    private final AlertMapper alertMapper;

    public void insert(AlertVO alertVO){ alertMapper.insert(alertVO);}

    public void alertFollow(AlertVO alertVO){ alertMapper.alertFollow(alertVO);}

    public List<AlertDTO> getList(Long userNumber){ return alertMapper.alertList(userNumber); }

    public int getAlertNumber(AlertVO alertVO) {return alertMapper.getAlertNumber(alertVO); }

    public void remove(int alertNumber) { alertMapper.remove(alertNumber);}


}
