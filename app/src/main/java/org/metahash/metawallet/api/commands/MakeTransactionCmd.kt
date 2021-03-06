package org.metahash.metawallet.api.commands

import io.reactivex.Observable
import org.metahash.metawallet.api.Api
import org.metahash.metawallet.api.ServiceRequestFactory
import org.metahash.metawallet.api.base.BaseCommand
import org.metahash.metawallet.api.base.BaseCommandWithMapping
import org.metahash.metawallet.data.models.CreateTxResponse
import org.metahash.metawallet.data.models.TXSTATUS

class MakeTransactionCmd(
        private val api: Api
) : BaseCommandWithMapping<CreateTxResponse, CreateTxResponse>() {

    var to = ""
    var value = ""
    var fee = ""
    var nonce = ""
    var data = ""
    var pubKey = ""
    var sign = ""

    var baseProxyUrl = ""

    override fun serviceRequest(): Observable<CreateTxResponse> {
        return api
                .makeTransaction(baseProxyUrl,
                        ServiceRequestFactory.getRequestData(
                        ServiceRequestFactory.REQUESTTYPE.MAKETRANSACTION,
                        ServiceRequestFactory.getTransactionParams(to, value, fee, nonce, data, pubKey, sign)))
    }

    override fun afterResponse(response: Observable<CreateTxResponse>): Observable<CreateTxResponse> {
        return response
                .map {
                    val result = if (it.isSuccessful()) {
                        it.copy(status = TXSTATUS.OK)
                    } else {
                        it.copy(status = TXSTATUS.ERROR)
                    }
                    result
                }
    }
}