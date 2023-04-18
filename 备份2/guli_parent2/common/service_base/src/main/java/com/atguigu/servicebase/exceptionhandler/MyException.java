package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MyException extends RuntimeException {
   Integer code;
   String message;
}
