package cn.edu.scnu.Service;


import cn.edu.scnu.entity.Movie;
import cn.edu.scnu.mapper.MovieMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MovieService extends ServiceImpl<MovieMapper, Movie> {
}
