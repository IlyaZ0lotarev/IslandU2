package com.javarush.island.zolotarev.island.api.view;

import com.javarush.island.zolotarev.island.entity.map.Island;

public interface View {

    void show(long tick, Island island);
}
