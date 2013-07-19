package springWebGallery.db.dao;

import springWebGallery.models.User;
import springWebGallery.models.auxiliaryDatastructures.UserStats;

public interface UserDAO {
    public User findByUserLogin(String userLogin);

    public UserStats statsByUserId(int userId);

    public boolean checkIsLoginAvailable(String userLogin);

    public void insert(User user);

    public void update(User user);

    public boolean deleteUserByLogin(String userLogin);

    public int getLastUsedUserID();
}
