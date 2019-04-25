package com.nsimtech.rastersclient.service.`interface`

import java.util.*

interface IBaseOperation<T,TKey> {
    /**
     * GET /[T]s/[id]
     */
    fun getById(id: TKey): T;

    /**
     * POST /[T]s
     */
    fun create(item: T): T;

    /**
     * PUT /[T]s/[id]
     */
    fun update(id: T);

    /**
     * DELETE /[T]s/[id]
     */
    fun delete(id: TKey);
}