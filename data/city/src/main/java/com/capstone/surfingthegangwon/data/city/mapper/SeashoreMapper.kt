package com.capstone.surfingthegangwon.data.city.mapper

import com.capstone.surfingthegangwon.data.city.dto.SeashoresBasicRes
import com.capstone.surfingthegangwon.data.city.dto.SeashoresBasicResItem
import com.capstone.surfingthegangwon.domain.city.Seashores

// 단일 아이템 매핑
fun SeashoresBasicResItem.toDomain(): Seashores =
    Seashores(
        beachCode = beachCode,
        name = name,
        seashoreId = seashoreId
    )

// 리스트 매핑
fun SeashoresBasicRes.toDomainList(): List<Seashores> =
    this.map { it.toDomain() }
