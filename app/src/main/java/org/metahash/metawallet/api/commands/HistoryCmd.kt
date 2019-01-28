package org.metahash.metawallet.api.commands

import io.reactivex.Observable
import org.metahash.metawallet.WalletApplication
import org.metahash.metawallet.api.Api
import org.metahash.metawallet.api.ServiceRequestFactory
import org.metahash.metawallet.api.base.BaseCommand
import org.metahash.metawallet.data.models.HistoryResponse

class HistoryCmd(
        private val api: Api)
    : BaseCommand<HistoryResponse>() {

    var address = ""

    override fun serviceRequest(): Observable<HistoryResponse> {
        return api
                .getWalletHistory(getTorrentAddress(WalletApplication.dbHelper.getCurrencyIdByAddress(address)),
                        ServiceRequestFactory.getRequestData(
                                ServiceRequestFactory.REQUESTTYPE.WALLETHISTORY,
                                ServiceRequestFactory.getHistoryParams(address)))
    }
}