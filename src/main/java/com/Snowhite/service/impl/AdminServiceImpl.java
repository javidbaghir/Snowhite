package com.Snowhite.service.impl;

import com.Snowhite.domain.Admin;
import com.Snowhite.exception.AdminAlreadyExistException;
import com.Snowhite.exception.PasswordDoNotMatchException;
import com.Snowhite.repository.AdminRepository;
import com.Snowhite.service.AdminService;
import com.Snowhite.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordService passwordService;

    @Transactional
    @Override
    public Admin registerAdmin(Admin admin) {

        Admin getUsername = adminRepository.findByUsername(admin.getUsername());

        if (getUsername != null) {
            throw new AdminAlreadyExistException();
        } else {

            if (!admin.getPassword().equals(admin.getPasswordConfirm())) {
                throw new PasswordDoNotMatchException();
            } else {

//            String hashedPassword = passwordService.hashPassword(admin.getPassword());
//            admin.setPassword(hashedPassword);

                Admin saveAdmin = adminRepository.save(admin);

                return saveAdmin;
            }
        }
    }
}
