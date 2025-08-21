package com.lokendra.teamcollab.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeamService {
    private final UserService userService;

    @Cacheable(value = "teamUsers", key = "#userId + '-' + #teamId")
    public boolean isTeamsUser(Long userId, Long teamId) {
        var user = userService.getUserById(userId).orElse(null);
        return user.getTeam().getId().equals(teamId);
    }

    // @CacheEvict(value = "teamUsers", allEntries = true)
    // public void removeUserFromTeam(Long userId, Long teamId) {
    //     teamRepository.deleteByUserIdAndTeamId(userId, teamId);
    // }
}