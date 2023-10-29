package co.kr.parkjonghun.whatishedoingwithandroid.data.mapper

import co.kr.parkjonghun.whatishedoingwithandroid.data.model.S1
import co.kr.parkjonghun.whatishedoingwithandroid.domain.model.Sample

fun S1.toDomainModel(): Sample = Sample(stuff = s)