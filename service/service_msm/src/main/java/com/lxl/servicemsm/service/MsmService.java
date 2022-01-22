package com.lxl.servicemsm.service;

import java.util.Map;

public interface MsmService {
    boolean send(String phone,  Map<String,Object> param);
}
