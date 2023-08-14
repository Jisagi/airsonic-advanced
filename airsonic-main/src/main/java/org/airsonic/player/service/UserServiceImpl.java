/*
 This file is part of Airsonic.

 Airsonic is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Airsonic is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Airsonic. If not, see <http://www.gnu.org/licenses/>.

 Copyright 2023 (C) Airsonic Authors
 */
package org.airsonic.player.service;

import org.airsonic.player.dao.UserDao;
import org.airsonic.player.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User getSysAdmin() {
        List<User> admins = userDao.getAllUsers().stream()
                .filter(User::isAdminRole)
                .collect(Collectors.toList());

        return admins.stream()
                .filter(user -> User.USERNAME_ADMIN.equals(user.getUsername()))
                .findAny()
                .orElseGet(() -> admins.iterator().next());
    }
}
