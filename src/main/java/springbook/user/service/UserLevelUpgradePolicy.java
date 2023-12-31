package springbook.user.service;

import springbook.user.domain.User;

public interface UserLevelUpgradePolicy {
    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
    void upgradeLevels() throws Exception;
    void add(User user);
}
