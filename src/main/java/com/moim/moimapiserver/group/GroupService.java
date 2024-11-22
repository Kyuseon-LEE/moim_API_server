package com.moim.moimapiserver.group;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class GroupService {

    @Autowired
    private GroupMapper groupMapper;

    public Map<String, Object> createGroup(GroupDto groupDto) {
        Map<String, Object> response = new HashMap<>();
        try {
            log.info("Attempting to insert group data: {}", groupDto);

            int result = groupMapper.insertNewGroup(groupDto);
            log.info("Insert result: {}", result);

            if (result > 0) {
                response.put("result", "success");
            } else {
                response.put("result", "fail");
            }
        } catch (Exception e) {
            log.error("Error during group creation", e);
            response.put("result", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }


}
