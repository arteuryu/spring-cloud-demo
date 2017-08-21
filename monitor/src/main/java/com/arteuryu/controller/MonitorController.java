package com.arteuryu;

import com.arteuryu.domain.service.MonitorService;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by yuerte969 on 20/8/2017.
 */
@RestController
@RequestMapping("/monitor")
@EnableAutoConfiguration
public class MonitorController {
    @Autowired
    private MonitorService monitorService;
    @RequestMapping("/ugcAlert")
    public List<Map<String,Object>> queryUgcAlert(){
        return monitorService.queryUgcAlert();
    }
}
