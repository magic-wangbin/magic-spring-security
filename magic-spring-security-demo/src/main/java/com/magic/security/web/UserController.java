package com.magic.security.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.magic.security.dto.request.UserQueryCondition;
import com.magic.security.dto.response.User;
import com.magic.security.validator.annotacion.UserValidator;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    /**
     * 分页条件查询.
     */
    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> userList(UserQueryCondition userQueryCondition,
                               //分页
                               @PageableDefault(page = 2, size = 17, sort = "userName,asc") Pageable page
    ) {

        //打印请求结果
        System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));

        System.out.println(ReflectionToStringBuilder.toString(page, ToStringStyle.MULTI_LINE_STYLE));
        List<User> list = new ArrayList<>();
        list.add(new User());
        list.add(new User());
        return list;
    }

    @JsonView(User.UserDetailView.class)
    @GetMapping("/{id:\\d+}")
    public User getUserId(@PathVariable("id") Long id) {
        User user = new User();
        user.setUserId("100");
        user.setPassword("123345");
        user.setBirthday(new Date());
        if (String.valueOf(id).equals(user.getUserId())) {
            return user;
        }
        return null;
    }

    @JsonView(User.UserDetailView.class)
    @GetMapping("/validate/{userId}")
    public User validateUserId(@PathVariable Long userId, @UserValidator @RequestParam String id) {

        User user = new User();
        user.setUserId("100");
        user.setPassword("123345");
        user.setBirthday(new Date());
        if (String.valueOf(userId).equals(user.getUserId())) {
            return user;
        }
        return null;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user, BindingResult errors) {

        System.out.println(user.getPassword());
        System.out.println(user.getBirthday());

        user.setUserId("100");
        return user;
    }

    @PutMapping("/{userId:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {

        System.out.println(ReflectionToStringBuilder.toString(errors, ToStringStyle.MULTI_LINE_STYLE));

        errors.getAllErrors().stream().forEach(error -> {
            System.out.println("update->" + error.getDefaultMessage());
        });

        user.setUserId("100");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void deleteUser(@PathVariable("id") Long id) {
        System.out.println("delete->" + id);
    }
}