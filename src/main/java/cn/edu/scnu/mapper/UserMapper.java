package cn.edu.scnu.mapper;

import cn.edu.scnu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 更新用户VIP状态及时间（支持续费）
     * @param userId       用户ID
     * @param duration     套餐时长（天）
     * @return 影响行数
     */
    @Update("UPDATE `user` SET " +
            "user_type = 1, " + // 设置为VIP用户
            "total_renew_times = total_renew_times + 1, " + // 续费次数+1
            "last_renew_time = NOW(), " + // 更新最后续费时间
            "vip_start_time = CASE " + // 处理首次开通和续费场景
            "   WHEN vip_end_time > NOW() THEN vip_start_time " + // 若VIP未过期，保持原开始时间
            "   ELSE NOW() " + // 否则从当前时间开始
            " END, " +
            "vip_end_time = CASE " +
            "   WHEN vip_end_time > NOW() THEN DATE_ADD(vip_end_time, INTERVAL #{duration} DAY) " + // 续费场景：延长时长
            "   ELSE DATE_ADD(NOW(), INTERVAL #{duration} DAY) " + // 首次开通：从当前时间计算
            " END " +
            "WHERE id = #{userId}")
    int updateUserVipStatus(@Param("userId") Integer userId, @Param("duration") Integer duration);
}