package com.hcy.dto;

import com.hcy.entity.Presale;
import com.hcy.entity.Sku;
import lombok.Data;

/**
 * Description：
 * Author: 黄成勇
 * Date:  2022/2/19 17:15
 */
@Data
public class GoodsForConfirmOrder {
    private Sku sku;
    private Presale presale;
}
