package com.kyo.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
