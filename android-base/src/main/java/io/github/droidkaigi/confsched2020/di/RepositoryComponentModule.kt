package io.github.droidkaigi.confsched2020.di

import android.content.Context
import com.dropbox.android.external.store4.MemoryPolicy
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import dagger.Module
import dagger.Provides
import io.github.droidkaigi.confsched2020.data.api.DroidKaigiApi
import io.github.droidkaigi.confsched2020.data.api.GoogleFormApi
import io.github.droidkaigi.confsched2020.data.api.response.StaffResponse
import io.github.droidkaigi.confsched2020.data.db.AnnouncementDatabase
import io.github.droidkaigi.confsched2020.data.db.ContributorDatabase
import io.github.droidkaigi.confsched2020.data.db.SessionDatabase
import io.github.droidkaigi.confsched2020.data.db.SponsorDatabase
import io.github.droidkaigi.confsched2020.data.db.StaffDatabase
import io.github.droidkaigi.confsched2020.data.db.entity.StaffEntity
import io.github.droidkaigi.confsched2020.data.firestore.Firestore
import io.github.droidkaigi.confsched2020.data.repository.RepositoryComponent
import io.github.droidkaigi.confsched2020.model.Staff
import io.github.droidkaigi.confsched2020.model.StaffContents
import io.github.droidkaigi.confsched2020.model.repository.AnnouncementRepository
import io.github.droidkaigi.confsched2020.model.repository.ContributorRepository
import io.github.droidkaigi.confsched2020.model.repository.SessionRepository
import io.github.droidkaigi.confsched2020.model.repository.SponsorRepository
import io.github.droidkaigi.confsched2020.model.repository.StaffRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Module
class RepositoryComponentModule {
    @Provides @Singleton
    fun provideRepository(
        repositoryComponent: RepositoryComponent
    ): SessionRepository {
        return repositoryComponent.sessionRepository()
    }

    @Provides @Singleton
    fun provideSponsorRepository(
        repositoryComponent: RepositoryComponent
    ): SponsorRepository {
        return repositoryComponent.sponsorRepository()
    }

    @Provides @Singleton
    fun provideAnnouncementRepository(
        repositoryComponent: RepositoryComponent
    ): AnnouncementRepository {
        return repositoryComponent.announcementRepository()
    }

    @Provides @Singleton
    fun provideStaffRepository(
        repositoryComponent: RepositoryComponent
    ): StaffRepository {
        return repositoryComponent.staffRepository()
    }

    @Provides @Singleton
    fun provideContributorRepository(
        repositoryComponent: RepositoryComponent
    ): ContributorRepository {
        return repositoryComponent.contributorRepository()
    }

    @Provides @Singleton
    fun provideRepositoryComponent(
        context: Context,
        droidKaigiApi: DroidKaigiApi,
        googleFormApi: GoogleFormApi,
        sessionDatabase: SessionDatabase,
        sponsorDatabase: SponsorDatabase,
        announcementDatabase: AnnouncementDatabase,
        staffDatabase: StaffDatabase,
        contributorDatabase: ContributorDatabase,
        firestore: Firestore
    ): RepositoryComponent {
        return RepositoryComponent.factory()
            .create(
                context = context,
                droidKaigiApi = droidKaigiApi,
                googleFormApi = googleFormApi,
                sessionDatabase = sessionDatabase,
                sponsorDatabase = sponsorDatabase,
                announcementDatabase = announcementDatabase,
                staffDatabase = staffDatabase,
                contributorDatabase = contributorDatabase,
                firestore = firestore
            )
    }
}
