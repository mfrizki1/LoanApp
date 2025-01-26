package id.calocallo.loanapp

import android.app.Application
import id.calocallo.loanapp.core.data.RetrofitProvider
import id.calocallo.loanapp.data.network.LoanService
import id.calocallo.loanapp.data.network.RemoteLoanDataSource
import id.calocallo.loanapp.data.network.RetrofitRemoteLoanDataSource
import id.calocallo.loanapp.data.repository.DefaultLoanRepository
import id.calocallo.loanapp.data.repository.LoanRepository
import id.calocallo.loanapp.presentation.LoanListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class LoanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val appModule = module {
            single<LoanService> { RetrofitProvider.retrofit().create(LoanService::class.java) }
            single<RemoteLoanDataSource> { RetrofitRemoteLoanDataSource(get()) }
            single<LoanRepository> { DefaultLoanRepository(get()) }
            viewModel { LoanListViewModel(get()) }

            /*single {
                Room.databaseBuilder(
                    context = androidContext(),
                    klass = FavoriteBookDatabase::class.java,
                    name = "favorite_books_db"
                ).build()
            }
            single { get<FavoriteBookDatabase>().favoriteBookDao() }
            */


            /*viewModel { BookDetailViewModel(get()) }
            viewModel { SelectedBookViewModel(get()) }*/
        }

        startKoin {
            androidLogger()
            androidContext(this@LoanApplication)
            modules(appModule)
        }

    }
}