package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.AlumniFeeling;
import lombok.Data;

@Data
public class FeelingWithUser extends AlumniFeeling {
    //作者头像
    String avatar;
    String author;
}
