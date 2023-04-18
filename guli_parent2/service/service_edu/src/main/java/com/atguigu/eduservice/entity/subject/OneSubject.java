package com.atguigu.eduservice.entity.subject;

import lombok.Data;
import org.apache.poi.hssf.record.DVALRecord;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    String id;
    String title;
   List<TwoSubject> children= new ArrayList<>();
}
