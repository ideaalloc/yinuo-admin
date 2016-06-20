package com.yinuo.admin.repository.dao;

import com.yinuo.admin.repository.po.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2016-05-20
 */
public interface StaffDao {
    Staff findByUsername(String username);

    List<String> findAuthoritiesByUid(Long uid);

    Integer countStaff(String username);

    void insertStaff(Staff staff);

    void insertAuthority(@Param("uid") Long uid, @Param("authority") String authority);
}
