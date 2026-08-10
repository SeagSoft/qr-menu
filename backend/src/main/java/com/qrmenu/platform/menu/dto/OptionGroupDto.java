package com.qrmenu.platform.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroupDto {
    private Long id;
    private String name;
    private int minSelect;
    private int maxSelect;
    private List<ProductOptionDto> options;
}
