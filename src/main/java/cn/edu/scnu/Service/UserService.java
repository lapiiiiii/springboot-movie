package cn.edu.scnu.Service;

import cn.edu.scnu.entity.User;
import cn.edu.scnu.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
