package io.github.droidkaigi.confsched2020.data.repository.internal

import com.dropbox.android.external.store4.MemoryPolicy
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import io.github.droidkaigi.confsched2020.data.api.DroidKaigiApi
import io.github.droidkaigi.confsched2020.data.api.response.StaffResponse
import io.github.droidkaigi.confsched2020.data.db.StaffDatabase
import io.github.droidkaigi.confsched2020.data.db.entity.StaffEntity
import io.github.droidkaigi.confsched2020.model.repository.StaffRepository
import io.github.droidkaigi.confsched2020.model.Staff
import io.github.droidkaigi.confsched2020.model.StaffContents
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStaffRepository @Inject constructor(
    private val api: DroidKaigiApi,
    private val staffDatabase: StaffDatabase
) : StaffRepository {

    val store = StoreBuilder.fromNonFlow<Unit, StaffResponse> { api.getStaffs() }
        .persister(
            reader = { readFromLocal() },
            writer = { _: Unit, output: StaffResponse -> staffDatabase.save(output) }
        )
        .cachePolicy(MemoryPolicy.builder().build())
        .build()

    override suspend fun refresh() {
        val response = api.getStaffs()
        staffDatabase.save(response)
    }

    fun readFromLocal() = staffDatabase
        .staffs()
        .map { StaffContents(it.map { staffEntity -> staffEntity.toStaff() }) }

    fun staffsStore(): Flow<StoreResponse<StaffContents>> {
        return store.stream(StoreRequest.cached(key = Unit, refresh = true))
    }

    override fun staffs() = staffDatabase
        .staffs()
        .map { StaffContents(it.map { staffEntity -> staffEntity.toStaff() }) }
}

private fun StaffEntity.toStaff(): Staff = Staff(id, name, iconUrl, profileUrl)
