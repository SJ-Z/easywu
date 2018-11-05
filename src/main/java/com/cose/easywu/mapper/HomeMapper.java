package com.cose.easywu.mapper;

import com.cose.easywu.po.Banner;
import com.cose.easywu.po.Type;

import java.util.List;

public interface HomeMapper {

    List<Banner> selectAllBanner();
    List<Type> selectAllType();

}
