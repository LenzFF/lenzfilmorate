package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class UserService extends AbstractService<User> {

    @Autowired
    public UserService(Storage<User> userStorage) {
        this.storage = userStorage;
    }

    private User getUserOrThrowException(Long userId) {
        User user = storage.getById(userId);
        if (user == null) {
            log.info("Пользователь не найден - {}", userId);
            throw new NotFoundException("Пользователь не найден");
        }
        return user;
    }

    @Override
    public User get(long id) {
        User user = getUserOrThrowException(id);
        return user;
    }

    @Override
    public void create(User data) {
        validate(data);
        super.create(data);
    }

    @Override
    public void update(User data) {
        getUserOrThrowException(data.getId());
        validate(data);
        super.update(data);
    }

    @Override
    public void delete(long id) {
        getUserOrThrowException(id);
        super.delete(id);
    }

    public void makeFriends(long userId, long friendId) {
        User user = getUserOrThrowException(userId);
        User friend = getUserOrThrowException(friendId);

        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
    }


    public void removeFromFriends(long userId, long friendId) {
        User user = getUserOrThrowException(userId);
        User friend = getUserOrThrowException(friendId);

        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
    }

    public List<User> getUsersFriends(Long userId) {
        User user = getUserOrThrowException(userId);
        return getUserListFromIdSet(user.getFriends());
    }


    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = getUserOrThrowException(userId);
        User friend = getUserOrThrowException(friendId);

        Set<Long> commonFriendsIds = new HashSet<>(user.getFriends());
        commonFriendsIds.retainAll(friend.getFriends());

        return getUserListFromIdSet(commonFriendsIds);
    }

    private List<User> getUserListFromIdSet(Set<Long> friendsId) {
        List<User> users = new ArrayList<>();
        for (Long id : friendsId) {
            users.add(storage.getById(id));
        }
        return users;
    }

    @Override
    protected void validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) user.setName(user.getLogin());
        if (user.getLogin() == null || user.getBirthday() == null)
            throw new ValidationException("Ошибка валидации пользователя");

        if (!(user.getBirthday().isBefore(LocalDate.now()) &&
                user.getEmail().contains("@") &&
                !user.getEmail().isEmpty() &&
                !user.getLogin().isEmpty() &&
                !user.getLogin().contains(" "))) {
            log.info("Ошибка валидации пользователя - {}", user);
            throw new ValidationException("Ошибка валидации пользователя");
        }
    }
}
