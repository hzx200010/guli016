package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.educenter.utils.MD5;
import com.atguigu.servicebase.exceptionhandler.MyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        //判断是否为空
        if(member.getMobile()==null||member.getPassword()==null){

            throw new MyException(20001,"失败");
        }

        //判断手机号
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",member.getMobile());
        Integer integer = baseMapper.selectCount(queryWrapper);
if (integer == 0) {
    throw new MyException(20001,"失败");
}
//判断密码

        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        String password = ucenterMember.getPassword();
        if(!MD5.encrypt(member.getPassword()).equals(password)){
            throw new MyException(20001,"失败");
        }
        //判断用户是否禁用
        if(ucenterMember.getIsDisabled()) {
            throw new MyException(20001,"失败");
        }
        //登入成功
        //生成JWT
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return  jwtToken;

    }
//注册
    @Override
    public void register(RegisterVo registerVo) {
if (registerVo.getMobile()==null||registerVo.getPassword()==null||
        registerVo.getCode()==null||registerVo.getNickname()==null){
    throw new MyException(20001,"失败");
}
        //判断redis的验证码1234
     //   String code = redisTemplate.opsForValue().get(registerVo.getMobile());
        if(!registerVo.getCode().equals("1234")){
            throw new MyException(20001,"失败");
        }
        QueryWrapper<UcenterMember> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("mobile",registerVo.getMobile());

        Integer count = baseMapper.selectCount(QueryWrapper);
        if (count > 0) {
            throw new MyException(20001,"失败");
        }
        //数据添加数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(registerVo.getMobile());
        member.setNickname(registerVo.getNickname());
        member.setPassword(MD5.encrypt(registerVo.getPassword()));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http");
        baseMapper.insert(member);
    }

    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        return ucenterMember;
    }

    @Override
    public Integer countRegisterDay(String day) {

        return baseMapper.countRegisterDay(day);
    }


}
