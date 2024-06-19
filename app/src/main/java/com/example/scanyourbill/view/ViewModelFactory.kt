package com.example.scanyourbill.view


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scanyourbill.TransactionThisMonthViewModel
import com.example.scanyourbill.data.repository.BillRepository
import com.example.scanyourbill.data.repository.TransactionRepository
import com.example.scanyourbill.data.repository.UserRepository
import com.example.scanyourbill.data.repository.WalletRepository
import com.example.scanyourbill.view.main.MainViewModel
import com.example.scanyourbill.di.Injection
import com.example.scanyourbill.view.login.LoginViewModel
import com.example.scanyourbill.view.scanbill.BillViewModel
import com.example.scanyourbill.view.transaction.TransactionViewModel
import com.example.scanyourbill.view.wallet.WalletViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository,
    private val billRepository: BillRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(transactionRepository) as T
            }
            modelClass.isAssignableFrom(WalletViewModel::class.java) -> {
                WalletViewModel(walletRepository) as T
            }
            modelClass.isAssignableFrom(TransactionThisMonthViewModel::class.java) -> {
                TransactionThisMonthViewModel(transactionRepository) as T
            }
            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> {
                TransactionViewModel(transactionRepository) as T
            }
            modelClass.isAssignableFrom(BillViewModel::class.java) -> {
                BillViewModel(billRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: buildFactory(context).also { INSTANCE = it }
            }
        }

        private fun buildFactory(context: Context): ViewModelFactory {
            val userRepository = Injection.provideUserRepository(context)
            val transactionRepository = Injection.provideTransactionRepository(context)
            val walletRepository = Injection.provideWalletRepository(context)
            val billRepository = Injection.provideBillRepository(context)
            return ViewModelFactory(userRepository, transactionRepository, walletRepository, billRepository)
        }
    }
}
