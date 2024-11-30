package com.ssafy.apt.model.service;

import java.util.List;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy.aop.ExecutionTimeLoggerAspect;
import com.ssafy.apt.model.AptDto;
import com.ssafy.apt.model.AptStatDto;
import com.ssafy.apt.model.DongStatDto;
import com.ssafy.apt.model.GugunStatDto;
import com.ssafy.apt.model.mapper.StatMapper;

@Service
public class StatServiceImpl implements StatService {

  private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeLoggerAspect.class);
  
  private final StatMapper mapper;
  private final SqlSessionFactory sqlSessionFactory;

  
  public StatServiceImpl(StatMapper mapper, SqlSessionFactory sqlSessionFactory) {
    this.mapper = mapper;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public List<GugunStatDto> getGugunAll() throws Exception {
    return mapper.getGugunAll();
  }

  @Override
  @Transactional
  public List<DongStatDto> getDongStatAll() throws Exception {
//    Cache cache = sqlSessionFactory.getConfiguration().getCache(StatMapper.class.getName());
//
//    if (cache != null) {
//        // 캐시에서 값을 가져오기
//        List<DongStatDto> cachedData = (List<DongStatDto>) cache.getObject("getDongStatAll");
//        if (cachedData != null) {
//            logger.info(">>> DATA loaded from myBatis Cache");
//            return cachedData;
//        }
//    }
//
//    // 캐시에 데이터가 없으므로 DB에서 가져옴
//    List<DongStatDto> data = mapper.getDongStatAll();
//
//    // 캐시에 데이터 저장
//    if (cache != null) {
//        cache.putObject("getDongStatAll", data);
//        logger.info(">>> DATA Stored in myBatis Cache");
//    }
  
    return mapper.getDongStatAll();
  }

  @Override
  @Transactional
  public List<DongStatDto> getDongStatByGugun(String shortDongcode) throws Exception {
    mapper.logRegionalQuery(shortDongcode + "00000");
    return mapper.getDongStatByGugun(shortDongcode);
  }

  @Override
  @Transactional
  public List<AptStatDto> getAptStatByDong(String shortDongcode) throws Exception {
    mapper.logRegionalQuery(shortDongcode + "00000");
    return mapper.getAptStatByDong(shortDongcode);
  }

  @Override
  @Transactional
  public AptStatDto getAptStat(String aptSeq) throws Exception {
    mapper.logRegionalQuery(aptSeq.substring(0, aptSeq.indexOf('-')) + "00000");
    return mapper.getAptStat(aptSeq);
  }

  @Override
  public List<AptDto> getAptInfoAll() throws Exception {
    return mapper.getAptInfoAll();
  }

}
