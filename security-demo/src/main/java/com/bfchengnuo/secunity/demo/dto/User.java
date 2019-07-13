package com.bfchengnuo.secunity.demo.dto;

import com.bfchengnuo.secunity.demo.validator.MyValidator;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Created by 冰封承諾Andy on 2019/7/8.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public interface UserSimpleView {}
    public interface UserDetailView extends UserSimpleView {}

    @JsonView(UserSimpleView.class)
    private String id;

    @JsonView(UserSimpleView.class)
    @NotBlank(message = "用户名不能为空")
    private String userName;

    @JsonView(UserDetailView.class)
    @MyValidator
    private String pwd;
}
