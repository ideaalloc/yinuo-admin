package com.yinuo.admin.service;

import com.yinuo.admin.model.vo.StaffVo;
import com.yinuo.admin.repository.dao.StaffDao;
import com.yinuo.admin.repository.po.Staff;
import com.yinuo.admin.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2016-05-10
 */
@Service
public class StaffService {
    static final Logger LOGGER = LoggerFactory.getLogger(StaffService.class);

    @Autowired
    StaffDao staffDao;

    @Transactional
    public void addStaff(String username, String password, String authority,
                         Long organization, String nickname, String phone, String email) {
        LOGGER.info("Adding staff with username {}", username);
        final String passwordHash = new BCryptPasswordEncoder().encode(password);
        final long uid = IdUtil.generateId();
        staffDao.insertStaff(new Staff(uid, username, passwordHash,
                organization, nickname, phone, email, null, null));
        staffDao.insertAuthority(uid, authority);
    }

    @Transactional
    public Boolean exists(String username) {
        return staffDao.countStaff(username) > 0;
    }

    @Transactional
    public StaffVo getStaff(String username) {
        final Staff staffPo = staffDao.findByUsername(username);
        if (staffPo == null) {
            return null;
        }
        final String createTime = String.valueOf(staffPo.getCreateTime().getTime() / 1000);
        return new StaffVo(String.valueOf(staffPo.getId()), staffPo.getUsername(), staffPo.getNickname(), staffPo.getEmail(), createTime);
    }
}
