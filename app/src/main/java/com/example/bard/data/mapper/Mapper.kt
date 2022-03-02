package com.example.bard.data.mapper

import com.example.bard.data.source.local.entity.DsWordEntity
import com.example.bard.domain.model.AddContent

fun mapWordEntityToAddContent(data: List<DsWordEntity>) = data.map { AddContent(it.word, it.meaning) }