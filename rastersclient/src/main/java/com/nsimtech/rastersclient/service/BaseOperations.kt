package com.nsimtech.rastersclient.service

import com.nsimtech.rastersclient.service.`interface`.IBaseOperation

class BaseOperations<T, TKey> : IBaseOperation<T, TKey>{
    /**
     * GET /[T]s/[id]
     */
    override fun getById(id: TKey): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * POST /[T]s
     */
    override fun create(item: T): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * PUT /[T]s/[id]
     */
    override fun update(id: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * DELETE /[T]s/[id]
     */
    override fun delete(id: TKey) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}